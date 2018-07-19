[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# RDT 3.0

Underlying channel에 패킷 에러와 패킷 유실이 둘 다 존재할 경우 어떻게 해야 될까? 다시 전화통화를 생각해보자. 전화를 하는데 중간에 끊겨서 수신자가 목소리를 못 들었다면 일정시간이 지난 후에 반응을 할 것이다. 이처럼 우리 안에는 암묵적인 타이머가 존재하는데, 패킷 유실의 경우도 타이머를 사용하면 된다. 패킷을 보낼 때 타이머를 시작해서 종료될 때까지 피드백을 받으면 패킷유실이 발생하지 않은 것이고 못 받으면 패킷 유실이 발생한 것이다. 총 3가지 경우에서 타이머를 사용하게 된다.

## 패킷 유실

![image](https://user-images.githubusercontent.com/35518072/42916549-c679deda-8b40-11e8-95dd-2efb6b92c449.png)

중간에 패킷이 유실될 경우, 처음 설정해 놓은 타이머가 끝날 때까지 피드백이 오지 않았기 때문에 단순히 패킷을 다시 보내게 된다.

## 피드백 유실

![image](https://user-images.githubusercontent.com/35518072/42916576-f43b7810-8b40-11e8-8f3f-a75289f4cd2d.png)

피드백이 유실될 경우는 타이머가 끝날 때까지 피드백이 오지 않았기 때문에 여기서도 단순하게 패킷을 다시 보내게 되고 receiver는 받았던 패킷임을 감지하고 버린 후에 피드백만 다시 보내게 된다.

## 피드백 오기 전에 타임아웃

![image](https://user-images.githubusercontent.com/35518072/42916618-28aba6b0-8b41-11e8-8a0b-33a377e10bba.png)

중간에 피드백이 오는데 시간이 타이머보다 오래 걸려서 늦게 왔다면 sender는 다시 패킷을 보낼 수 밖에 없으므로 서로 계속해서 엇갈리다가 결국은 다시 맞게 된다.

## 문제점

지금가지 TCP의 reliable data transfer의 원리를 이해하기 위해 rdt라는 임의의 프로토콜을 디자인해보며 연습을 해보았는데, 패킷을 보내고 피드백을 받는 stop-and-wait 프로토콜이므로 패킷을 보내고 피드백을 받을 때까지의 시간이 낭비되는, 굉장히 비효율적인 프로토콜 디자인이라고 할 수 있다. 실제 효율적으로 디자인하기 위해선 한번에 패킷을 여러개 보내고 한번에 피드백을 받는 파이프라인 프로토콜을 사용해야 하며 이것이 실제적인 TCP의 구현이다.



# TCP 헤더의 구조

<img src="https://user-images.githubusercontent.com/35518072/42917235-ab23d340-8b43-11e8-9b01-edcd265bebae.png" width="400px">

* **포트번호 2개** : MUX/DEMUX 할 때 사용
* **Sequence number** : duplication tracking 하기 위해 사용
* **Acknowledgement number** : 통신을 하는 각 프로세스는 sender이면서 receiver이기 때문에 피드백도 해줘야 하는데 A랑 B가 통신을 하고 있다고 하면 A는 자신의 sequence number를 갖고 B에게 피드백을 해줄 B의 sequence number를 acknowledgement number로 갖는다. 
* **Checksum** : error detection을 위해 사용
* **Receive window** : flow control을 위해 사용하는 것으로 상대방이 받을 수 있는 범위 내에서 보내야 하기 때문에 간단하게, receive buffer의 빈공간이 얼마나 되는지 report해주는 것이라고 할 수 있다.



# TCP의 버퍼

<img src="https://user-images.githubusercontent.com/35518072/42917482-a2af676e-8b44-11e8-936a-c0f5e35b0ad7.png" width="500px">

각 프로세스는 TCP를 이용해 통신을 할 때 send buffer와 receive buffer라는 것을 가진다. Send buffer는 보낼 데이터를 담고 있다가 in-order 방식으로 segment로 바꾸어서 보내게 되며 receive buffer가 그것을 받아 위 계층으로 전달하게 된다. 그러나 underlying network layer에선 out of order로 될 텐데 어떻게 in-order delivery가 가능한 것일까? 그것을 가능하게 해주는 것이 바로 receive buffer와 sequence number이다. Receive buffer는 sequence number를 보면서 segment를 버퍼에 가지고 있다가 sequence number를 보고 순서대로 위 계층으로 전달하게 되는 것이다. 그렇다면 send buffer가 필요한 이유는 무엇일까? 데이터가 중간에 유실될 때 재전송을 해야 하기 때문에 재전송을 위해 갖고 있는 것이라 보면 된다.



# Sequence number

Sequence number는 어떻게 정해질까? 예를 들어 A와 B가 TCP로 통신을 한다고 해보자. 보낼 데이터의 크기는 500,000바이트인데 segment가 수용할 수 있는 데이터(MSS=Maximum Segment Size)는 1000바이트이다. 그러면 총 500개의 segment로 쪼개져서 전송된다. 이 때 각 바이트에 번호가 매겨지는데 첫번째 segment는 0번부터 999번까지 번호가 매겨지고 두번째 segment는 1000번부터 1999번까지 번호가 매겨지는 방식이다. Sequence number는 각 segment의 바이트 번호의 첫번째 부분으로 정해지기 때문에 첫번째의 경우는 0, 두번째의 경우는 1000이라고 할 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/42918014-e9c93c90-8b46-11e8-9445-e2af24781a51.png" width="500px">



# Acknowledgement number

예를 들어 A가 B로부터 0~535번 바이트까지의 데이터와 900~1000번까지의 데이터를 받았다고 치면 A는 536~899까지의 데이터를 받기 위해 기다리게 된다. 따라서 acknowledgement number는 536번이 되며 이것이 의미하는 바는 536번 바이트의 데이터를 기다리고 있다는 뜻이다. 결국 536번 데이터까지는 받은 것이 되기 때문에 총 어디까지 받았는지를 감안하여 ACK를 보내므로 **cumulative ACK**를 사용한다고 한다.



# 1바이트의 데이터를 보낸다고 하자.

<img src="https://user-images.githubusercontent.com/35518072/42918423-be0f1c9e-8b48-11e8-9e89-729157a8450b.png" width="500px">

실제적인 예를 통해서 sequence number와 acknowledgement number가 어떻게 동작하는지 알아보자. A가 'C'라는 1바이트의 데이터를 보낸다고 가정한 상황에서 seq가 42라는 것은 42번 바이트라는 것이고 ack가 79라는 것은 B로부터 78번 바이트까지의 데이터를 받았다는 뜻이 되므로 이것으로부터 B의 seq와 ack를 유추할 수 있다. Seq가 42였는데 1바이트를 보냈으므로 당연히 B의 ack는 43이 되고 ack가 78이었으므로 seq는 79가 된다. 하지만 데이터가 2바이트였다면? B의 ack는 자연스럽게 44가 될 것이다.



# 타이머 값

타이머의 값을 정할 때 어떻게 정할 것인지가 굉장히 중요하다.

* **너무 작을 경우** : 유실이 안됬는데도 다시 보낼 가능성이 높음
* **너무 클 경우** : 유실에 대한 재전송 보장은 되지만 반응이 너무 느림

그럼 어떻게 정해야 할까? 두 프로세스간의 통신이므로 RTT를 안다면 도움이 된다는 것이 포인트. **Segment를 보내고 피드백을 받을 때마다 RTT를 계산하는 sample RTT라는 것이 존재**하는데 매번 보낼 때마다 queueing delay 때문에 들쑥날쑥할 수밖에 없다. 따라서 똑똑하신 분들이 이걸 일정하게 유지하는 계산법을 만들어냈고 실제 타이머값은 이런 RTT보다 높아야 하기 때문에 이것도 적당히 높게 설정을 해야 한다. 이러한 값도 역시 적당히 높이 잡아놓았기 때문에 사용하면 된다.



# 타이머의 동작 방식

각 소켓에는 타이머가 1개만 존재하며, SEND_BASE라고 불리는 send buffer의 첫 segment에 물려있다. 타이머가 시작하지 않았다면 시작하고, 피드백을 받기 전에 타이머가 종료될 경우 현재 타이머에 맞물린 segment만 재전송하게 된다. 예를 들어 타이머가 sequence number 0을 가리키고 있었다고 해보자. 만약 receiver로부터 ACK 50이 왔다고 하면 sequence number 49까지의 데이터는 잘 받았다는 것이므로 send buffer에 굳이 0~49까지의 바이트 데이터가 있을 필요가 없다. 따라서 send buffer에서 사라지며 타이머는 sequence number 50을 가진 segment에 맞물리게 되는 것이다. 또한 기존의 SEND_BASE도 50을 가리키게 된다.



# TCP에서 Reliability를 제공하는 예시

## 패킷 유실

![image](https://user-images.githubusercontent.com/35518072/42921641-e7304576-8b57-11e8-82fb-0d54dd83b12e.png)

타이머는 seq 92에 물려있었는데 피드백이 오지 않았기 때문에 물려있는 상태에서 종료되게 된다. 따라서 seq 92의 segment를 다시 보내게 되고 ACK 100이 오게 되면 SEND_BASE를 100으로 바꾸고 다시 타이머가 재시작하게 된다.

## 피드백 오기전에 타임아웃

![image](https://user-images.githubusercontent.com/35518072/42921725-445eb89a-8b58-11e8-9396-fb7955e282bf.png)

Seq 92의 segment와 seq 100의 segement가 연속으로 전송될 때 ACK를 받기도 전에 타이머가 종료되는 경우이다. ACK는 당연히 100과 120이 되겠지만 둘 다 받지 못하기 때문에 seq 92에 맞물려 있는 segment를 재전송하지만 두번째 타이머가 시작할 때 ACK 100과 120을 받기 때문에 SEND_BASE가 92->100->120으로 이동한다.  Receiver는 duplication임을 알고 다시 ACK 120을 보내서 이번엔 타이머가 종료되기 전에 받는다. 이후 SEND_BASE는 120으로 변경되고 다시 타이머가 시작하게 된다.

## Cumulative ACK

![image](https://user-images.githubusercontent.com/35518072/42921847-11e14c6a-8b59-11e8-87c9-2a3cef5dd46e.png)

ACK가 2개 오는데 그 중 1개가 유실될 경우에 마지막에 오는 ACK가 결국 cumulative ACK이기 때문에 중간에 ACK가 유실돼도 정해진 시간 안에 도달하게 되면 위 그림에서 119번까지는 잘 받은 것이 되므로 다른 ACK는 신경 안써도 된다. 이것이 TCP의 장점 중 하나인데, 중간에 ACK가 유실되도 cumulative ACK를 사용하기 때문에 마지막 하나만 잘 받으면 상관없는 것이다.



# Performance Optimization

TCP의 ACK 구현에서 권고사항이 몇가지 있는데 그 중 1가지만 살펴보면 다음과 같은 내용이 있다.

> *Delayed ACK. Wait up to 500 msec for arrival of another in-order segment. If next in-order segment does not arrive in this interval, send an ACK.*

예를 들어, sequence number 100을 기다리고 있는데 sender로부터 해당 segment를 보냈다고 하고 데이터의 크기를 50바이트라고 하면 원하는 데이터를 받았으므로 sender에게 ACK 150을 바로 보내는 것이 맞다. 그런데 바로 보내지 말고 0.5초정도 기다리고 보내라는 것이 권고사항이다. 왜 그럴까? Segment를 1개씩만 보내는 방식이 아니라 파이프라인 방식이며 cumulative ACK를 사용하기 때문에 마지막 ACK만 보내도 되기 때문이다. 굳이 segment를 받을 때마다 ACK를 보낼 필요가 없다는 얘기이다.



# Fast-Retransmit

패킷 유실을 감지하기 위해 타이머를 사용하는데 실제적으로 타이머 시간을 상당히 높게 잡기 때문에 유실을 감지하고 재전송을 하는데까지 소모하는 시간이 많다. 따라서 유실을 더 빨리 감지할 수 없을까? 하고 나온 기법이다.

<img src="https://user-images.githubusercontent.com/35518072/42922441-7fff4a24-8b5b-11e8-9771-f3b6219bbb96.png" width="400px">

위 그림을 보자. Segment 5개를 보내는데 두번째 segment가 유실됬을 경우 receiver의 입장에선 application layer로 in-order delivery를 해야하기 때문에 120, 135, 141번 sequence number를 가진 segments를 receive buffer에 넣어두게 된다. 그리고 계속해서 ACK 100을 보낸다. Sender 입장에선 계속해서 ACK 100이 중복되기 때문에 이상함을 감지할 수 있다. 이걸 이용해서 **처음 받은 ACK를 제외하고 해당 ACK가 3번 중복될 경우 패킷 유실일 가능성이 아주 높다고 판단하여 재전송을 하는 방식을 fast-retransmit이라고 한다.** (정확히는 처음 ACK제외이므로 4번이다.)