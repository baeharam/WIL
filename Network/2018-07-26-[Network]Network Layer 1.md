---
layout: post
category: Network
title: "[네트워크]Network Layer 1"
---

[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# 개요

지금까지는 message를 다루는 application layer와 segment를 다루는 transport layer를 다뤘고 라우터가 어떻게 동작하며 packet이 어떻게 효율적인 경로로 전달되는지는 다루지 않았다. Network layer에서 그 얘기를 다루게 되는데 여기서 중요한 것은 라우터와 IP(Internet Protocol)이다. 라우터에는 application/transport layer가 구현되어 있지 않기 때문에 message/segment 단위의 데이터를 전달할 수 없지만 network layer까지는 구현되어 있으므로 packet 단위부터 전달할 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/43243757-125d8aca-90e3-11e8-9a1e-0106409c49b5.png" width="500px">

따라서 end system끼리 통신을 할 때 라우터를 거쳐서 하게 되는데 message는 segment로, segment는 packet으로 변환되어 전달되고 라우터는 각 packet의 헤더부분을 확인해서 어떤 라우터한테 전달해야 할지 계속 체크해가면서 가장 효율적인 경로를 찾는다.

<br>

# Forwarding과 Routing

라우터의 가장 중요한 2가지 기능이다.

* **Forwarding** : 패킷의 헤더를 보고 forwarding table을 참조해서 어디로 보낼지를 확인한 후 다음 목적지로 보내는 것
* **Routing** : forwarding table을 만드는 알고리즘

![image](https://user-images.githubusercontent.com/35518072/43243929-ca35bec4-90e3-11e8-93f5-25d7d29a368d.png)

위 그림에선 forwarding table이 각 패킷의 헤더마다 존재하는 것으로 되어있지만 그러면 table의 크기가 너무 커지기 때문에 **실제로는 address range를 설정해서 해당 range에 속하는 output link로 전달되는 방식이다.** 각 라우터마다 forwarding table이 있어서 forwarding이 되면 각자의 table을 참고해서 다시 forwarding을 하게 된다. Forwarding table을 생성하는 routing은 다익스트라 알고리즘과 같은 방법을 사용한다.

<br>

# Longest Prefix Matching

실제로 forwarding table은 아래 그림과 같이 앞에 주소를 적어놓고 나머지는 와일드카드로 설정해서 prefix만 맞다면 그 link로 보내는 방식을 취한다.

![image](https://user-images.githubusercontent.com/35518072/43244275-1ca24af0-90e5-11e8-86f0-184b5e72c4cf.png)

하지만 만약 패킷의 목적지 주소가 `11001000 00010111 00011000 10101010` 이었다면 1번으로 갈까 2번으로 갈까? 2가지 모두 만족시키지만 가장 길게 매칭되는 longest prefix matching 방식을 사용하기 때문에 여기선 1번 link로 가게 된다. 길게 매칭되는 곳에 보내는 이유는 상식적으로 볼 때, 가장 잘 맞는 목적지이기 때문에 당연한 것으로 볼 수 있다.

<br>

# Router Architecture

![image](https://user-images.githubusercontent.com/35518072/43244546-1b4dcf02-90e6-11e8-9ef8-c81ef84b1fff.png)

Forwarding table을 생성하기 위해선 알고리즘을 이용하는 routing 작업을 거쳐야 하는데 하나의 라우터 혼자서 할 수 있는 작업이 아니라 여러개의 라우터가 사용자의 데이터와 routing 작업 정보들을 교환하면서 하는 것이다. 이런 routing이 일어나는 영역을 **control plane**이라고 한다. 또한 라우터들끼리 전달하는 것이 사용자의 데이터이기 때문에 의미있는 정보를 교환한다고 하여, input/output port를 통해서 데이터 교환 작업이 일어나는 영역을 **data plane**이라고 한다.

## Input ports

![image](https://user-images.githubusercontent.com/35518072/43245461-5247a5a2-90e9-11e8-93cf-960d363def9d.png)

가장 왼쪽 박스가 진입하는 phsical link를 종료시키는 기능을 한다. 또한 다른 쪽의 link layer와 상호작용 할 수 있도록 해주는 것이 중앙에 있다. 가장 중요한 것은 가장 오른쪽 박스의 역할인데, forwarding table을 보고 해당 outgoing link를 찾는 lookup 기능을 한다. (port는 소켓 프로그래밍에서의 port가 아니다.)

## Switching Fabric

라우터의 input ports와 output ports를 연결하며 라우터 안에 완전히 포함되어 있다. (네트워크 라우터 안의 네트워크) 패킷을 알맞은 경로로 보내는 역할을 하는데, memory/bus/crossbar 등 여러가지 방식이 있다.

## Output Ports

Switching fabric으로부터 패킷을 받아 저장한 뒤에 link/physical layer의 기능을 수행하여 알맞은 outgoing link를 통해 전송한다. 만약 양방향이라면 output port와 그에 해당하는 input port가 동일한 line card 상에서 쌍을 이룬다.

## Routing Processor

Routing 프로토콜을 수행하며 routing table과 link state를 유지한다. 또한 해당 라우터의 forwarding table을 생성하고 나중에 배울 네트워크 관리 기능도 수행한다.

라우터에 대해선 일단 이정도만 파악해도 괜찮다.

<br>

# IPv4 datagram format

<img src="https://user-images.githubusercontent.com/35518072/43245766-6b03fde2-90ea-11e8-915b-8073c5b16ae2.png" width="400px">

* **Version** : IP의 버전, 현재는 4이다.
* **Header length** : 헤더의 길이
* **Type of service** : 별로 의미있게 쓰이지 않아 현재로서는 몰라도 된다.
* **Time-to-live** : 패킷이 거쳐갈 수 있는 라우터의 최대 개수로, 혹시라도 라우팅 알고리즘이 잘못될 경우 사이클이 생길 수 있기 때문에 그걸 방지하는 필드이다.
* **Upper layer** : 네트워크 계층에서 receiver가 packet을 받으면 데이터 부분만 빼서 상위 계층인 transport layer로 올리는데 TCP인지 UDP인지 모르기 때문에 그걸 적어놓은 필드이다.
* **32-bit Source/Destination IP address** : 가장 중요한 출발지와 목적지의 32 비트 주소이다.

이렇게 IP packet에 헤더가 붙을 경우 오버헤드로 추가되는 크기는 얼마나 될까? Transport layer에서 20바이트가 추가되고 network layer에서 20바이트가 추가되므로 application message에서 전송한 message에 총 40바이트의 오버헤드 크기가 증가하여 전송된다. 보통 네트워크의 패킷 크기를 보면 40바이트인 패킷들이 상당히 많은데, message가 있다면 40바이트가 넘어야 하는데 왜 그런 것일까? 그 이유는 서버에 요청을 한 경우엔 ACK만 보내기 때문에 따로 전송할 메시지가 없기 때문이다.

<br>

# IP Address (IPv4)

* 유일한 32-bits 주소체계를 사용한다.
* 사람이 말하기엔 너무 길기 때문에 8비트씩 끊어서 10진수로 표현한 뒤에 점(.)으로 구분한다.

```
11000001 00100000 11011000 000010001
193.32.216.9
```

하지만 호스트마다 IP주소가 아무런 규칙 없이 정해진다면 라우터가 가져야 하는 forwarding table이 굉장히 커지게 된다. 앞 부분이 같아야 와일드카드를 사용해서 크기를 줄일 수 있는데 같지 않는 경우가 더 많아지기 때문이다. 그래서 IP주소를 계층화시키는 방법을 사용했는데 32비트 중에서 앞의 24비트를 네트워크 주소로 정하고 마지막 8비트를 호스트의 주소로 정하는 방법이다. 위의 IP주소는 아래와 같이 나타난다.

```
193.32.216.9/24
```

`/24`는 앞의 24비트가 네트워크 주소임을 뜻한다. 여기서 이제 서브넷마스크라고 하는 중요한 개념이 나온다.

<br>

# 서브넷 마스크

서브넷 마스크는 해당 호스트가 속한 네트워크를 구분하는 역할을 해주며 32비트로 이루어져 있다. IP주소의 서브넷(=네트워크)을 구분해주기 위해 1로 설정된 비트의 개수를 위에서 말했던 `/24`가 가리키는 것이다. 따라서, 위 IP주소와 서브넷 마스크로 네트워크를 알아내려면 AND 연산을 해주면 된다.

```
11000001 00100000 11011000 000010001 (193.32.216.9)
11111111 11111111 11111111 000000000 (255.255.255.0)
```

해당 IP주소의 네트워크는 193.32.216.XXX에 속했다는 것을 확인할 수 있다. 이렇게 되면 위에서 발생했던 forwarding table이 커지는 문제점은 네트워크에 해당하는 공통된 prefix를 이용해 줄일 수 있게 된다.

<br>

# Classful Addressing

서브넷 마스크를 사용하기 전에는 classful addressing이라는 주소체계를 사용했는데 네트워크 개수 기준으로 클래스를 분류해서 사용했다. 

* Class A : 0으로 시작하며 앞 8개의 비트를 서브넷으로 사용
* Class B : 10으로 시작하며 앞 16개의 비트를 서브넷으로 사용
* Class C : 110으로 시작하며 앞 24개의 비트를 서브넷으로 사용

Class A를 보면 제일 앞의 0을 제외하고 7개의 비트를 서브넷으로 사용할 수 있는데 결국 네트워크라는 건 하나의 단체가 사용하는 것이기 때문에 최대 127개의 단체만 class A를 사용할 수 있게 된다. 하지만 각 단체가 가질 수 있는 호스트의 개수는 $2^{24}$ 로 굉장히 크다는 것을 알 수 있다. 어떤 단체가 사용하고 싶은 호스트의 개수가 2000개라고 하면 나머지 $2^{24}-2000$개는 낭비되는 것이 된다.

또한 class C의 경우는 뒤쪽의 8비트, 즉 254(2비트는 다른데 사용된다고 함)개의 호스트만 사용할 수 있기 때문에 2000개를 사용하고자 하는 단체는 너무 부족하게 된다. Class B도 class A와 마찬가지로 문제점이 있다. 이런 문제점을 보완하기 위해 나온 방식이 위에서 설명한 서브넷 마스크를 이용하는 CIDR라는 방법이다.

<br>

# Classless Inter-Domain Routing (CIDR)

> 클래스 없는 도메인 간 라우팅 기법

서브넷 마스크와 헷갈릴 수 있는데 서브넷 마스크는 CIDR(사이더)에서 사용하는 서브넷 확인용 마스크일 뿐이다. 위키백과를 보면 CIDR이 나온 이유를 다음과 같이 설명한다.

- 급격히 부족해지는 [IPv4](https://ko.wikipedia.org/wiki/IPv4) 주소를 보다 효율적으로 사용하게 해준다.
- 접두어를 이용한 주소 지정 방식을 가지는 계층적 구조를 사용함으로써 [인터넷](https://ko.wikipedia.org/wiki/%EC%9D%B8%ED%84%B0%EB%84%B7) 광역 라우팅의 부담을 줄여준다.

Classful addressing 방식을 사용하게 되면 IP주소가 낭비되기 때문에 CIDR을 사용해서 보다 효율적으로 IP주소를 사용할 수 있다. 또한 접두어를 기준으로 네트워크를 구분하는 계층적 방식을 사용하기 때문에 라우터는 forwarding table을 확인하고 CIDR가 이미 적용된 네트워크에 맞춰서 패킷을 forwarding 하게 된다. 따라서, 당연하게도 forwarding table은 아래와 같이 CIDR를 통해서 네트워크 주소와 그에 해당하는 prefix만 표시한 entry가 들어가게 된다.

![image](https://user-images.githubusercontent.com/35518072/43303594-5d8e0bc8-91ab-11e8-9899-5644da1d8145.png)

물론 네트워크가 다른 경우가 있으므로 대부분의 prefix 값은 다를 수 있다. 또한 위에서 봤듯이 3번째 주소값과 4번째 주소값 모두 매칭이 되지만 가장 길게 매칭되는 `201.10.6.0/23` 네트워크로 전송된다.

<br>

# 서브넷

IP주소를 network prefix이 서브넷과 나머지 host로 구분한다는 것은 알았는데 정확히 서브넷이라는 것은 무엇일가? 그림으로 이해하면 아주 간단하다.

![image](https://user-images.githubusercontent.com/35518072/43303955-06bc4b14-91ad-11e8-98d2-6f3355e1dacd.png)

서브넷은 **"라우터를 거치지 않고 서로 통신할 수 있는 호스트의 집합"**이라고 정의할 수 있는데 생각해보면 당연하다. 동일한 서브넷 주소로 전송된 패킷은 그 서브넷 안에서 여러개의 호스트들 간 이동이 가능하다. 위 그림에서 보면 `223.1.1.1`, `223.1.1.2`, `223.1.1.3` 호스트 3개는 모두 동일한 서브넷인 `223.1.1.XXX`에 있다는 것을 알 수 있다.

<br>

# IP주소와 서브넷의 관계

지금까진 IP주소를 보통 호스트(디바이스)의 주소라고 배웠는데, **대부분은 맞지만 엄밀하게 말하자면 네트워크가 연결되는 인터페이스의 주소라고 해야 한다.** 데스크탑이라면 거의 인터페이스가 하나라서 호스트의 주소를 IP주소라고 말할 수도 있지만 라우터의 경우 여러개의 인터페이스를 가지기 때문에 인터페이스의 주소를 IP주소라고 말하는 것이 맞다.

그렇다면 한번, 아래 그림에서 서브넷이 몇개인지 확인해보자.

<img src="https://user-images.githubusercontent.com/35518072/43304342-bd63521c-91ae-11e8-9816-b0da5168af87.png" width="500px">

위에서 **서브넷**을 얘기할 때 라우터를 거치지 않고 통신할 수 있는 호스트의 개수라고 했는데 다시 한번 IP주소의 개념을 정의했으니 **호스트의 개수가 아니라 디바이스의 개수**라고 해야 맞다. 따라서 위 그림에서 서브넷의 개수를 본다면 내가 처음에 생각했을 땐 호스트가 속한 서브넷의 개수라고 생각했기 때문에 라우터를 보지 않았고, 3개인 것 같았다. 

그러나 라우터가 가지는 IP주소가 3개이고 각 IP주소는 라우터를 거쳐야 다른 디바이스와 통신을 할 수가 있기 때문에 6개라고 할 수 있다. 먼저 라우터끼리의 통신은 별도의 라우터를 거치지 않으므로 하나의 서브넷을 공유하기 때문에 3개라고 할 수 있다. 그 다음 호스트의 입장에선 각 라우터에서 호스트와 통신하는 경우가 3가지 이므로 총 6개라고 할 수 있는 것이다.