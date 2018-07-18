[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# 개요

Transport layer는 OS 내부에 구현이 되어 있으며 application간의 논리적 통신을 위한 서비스와 프로토콜을 제공한다. Application layer에선 통신하는 데이터를 message라고 부르지만 transport layer에선 message가 **segment**라고 불리는 전송 단위에 들어가게 된다. Segment는 message와 다른 header부분을 가지고 있다. 이후에 배우겠지만 segement는 packet으로 다시 들어가게 되며 packet 또한 header를 가지고 있다. 그래서 점점 계층이 내려갈수록 점점 데이터의 크기가 커지게 된다. 인터넷에서 제공하는 transport layer의 서비스는 TCP와 UDP가 있다. TCP에서 중요한 것은 **in-order delivery**로 패킷이 라우터를 거쳐갈 때 모든 패킷이 같은 경로로 가는 것이 아니므로 TCP에서 제공하는 기능이라고 보면 된다.



# Multiplexing/Demultiplexing

Application layer에서 사용자가 사용하는 여러개의 프로세스가 소켓을 이용해 다른 네트워크의 프로세스와 통신을 할텐데, 그러한 여러개의 프로세스가 보내는 **데이터를 하나로 묶어서 하위계층으로 내려보내는 것을 multiplexing**이라고 하며 이 역할을 transport layer가 하는데 그 이유는 소켓을 다루는 계층이기 때문이다. 당연하게도 **demultiplexing은 올라온 데이터에 대해서 transport layer에서 각 프로세스에 해당하는 소켓으로 전달**하는 역할을 한다.

<img src="https://user-images.githubusercontent.com/35518072/42743458-00f807c4-88ff-11e8-9556-b14a5b6c6315.png" width="500px">

위 그림처럼 multiplexing은 sender인 p3,p4가 속한 네트워크에서 이루어지고 demultiplexing은 p1,p2가 속한 네트워크에서 이루어지게 된다. Demultiplexing에선 전송한 데이터를 해당하는 각 소켓으로 보내줘야 하는데 이걸 구분해주는 것이 포트번호이다. 따라서 segment의 헤더부분은 아래 그림과 같이 source port와 destination port라는 부분을 가지고 있다.

![image](https://user-images.githubusercontent.com/35518072/42743572-18cd3300-8900-11e8-8c73-9676b77a0b59.png)



# UDP와 TCP의 Demultiplexing

![image](https://user-images.githubusercontent.com/35518072/42743710-38b4ec2a-8901-11e8-91bd-0845fd2abeb0.png)

UDP의 경우 위와 같이 destination port만 같으면 같은 소켓으로 쳐서 p2와 p1이 p3에게 데이터를 전송할 때 둘다 destination port가 6428이므로 같은 프로세스의 소켓으로 demux가 이루어진다. 그러나 TCP는 이보다 좀더 복잡하다. 먼저 4가지로 소켓을 구분한다.

* **source IP 주소**
* **source 포트번호**
* **destination IP 주소**
* **destination 포트번호**

![image](https://user-images.githubusercontent.com/35518072/42743737-8888a53e-8901-11e8-9600-0072620f3c6b.png)

위 그림을 보면 p1과 p2,p3가 80번 포트로 접속하려는 걸 보니 웹서버에 접속하려고 하는 것 같다. p1과 p2를 보면 소스의 ip주소와 포트번호가 둘다 틀리기 때문에 각각 p4, p6 프로세스의 소켓으로 demux 되고 p3도 p2와 같은 머신에서 나왔기 때문에 당연히 포트번호가 틀리다. 따라서 p5의 소켓으로 demux가 되는 것이다. 그러나 실제로 웹서버에 접속하는 모든 사용자에게 프로세스의 소켓을 할당하진 않고 **하나의 프로세스를 두어서 사용자마다 쓰레드를 생성**하여 demux를 시키는 방식을 사용한다.



# UDP(User Datagram Protocol)

실제 UDP가 아무일도 안한다고는 배웠지만 MUX/DEMUX 정도는 하고 있었다. 또한 application layer에서 특수한 성격을 지닌 application은 UDP를 사용하는데 예를 들면, 스트리밍 서비스 같은 경우 몇개의 패킷이 유실되도 상관이 없기 때문에 괜찮고 이전에 DNS도 UDP를 사용한다고 배웠다. 이제 UDP 헤더를 배울텐데 참고로 프로토콜의 헤더는 데이터의 크기를 쓸데없이 키우기 때문에 작을수록 좋다. 따라서, 프로토콜의 헤더를 이해하는 것이 작동방식을 이해하는데 도움이 된다.

![image](https://user-images.githubusercontent.com/35518072/42743899-25987a60-8903-11e8-99ba-d5d68ebc3788.png)

헤더에는 정말 필요한 부분만 있다. 먼저 MUX/DEMUX에 사용되는 포트번호 2개와 해당 segment의 길이를 나타내는 length, 그리고 마지막에 checksum이라는 것이 있는데 데이터가 전송될 때 에러가 발생했는지 없는지 알 수 있는 부분으로 receiver가 checksum을 보고 에러가 발생했다면 버리게 된다. 따라서, 사용자 입장에선 reliable하지 않다 하더라도 최소한 에러가 있는 메시지는 받지 않는 것이다.



# Reliable data transfer

TCP의 장점 중 하나가 신뢰성있는 데이터 전송을 제공한다는 점인데, **실제로는** 아래 그림 처럼 그 하위 계층에서는 패킷 유실이나 전송 에러 같은 것들이 발생할 수 있기 때문에 **unreliable** 하다고 할 수 있다. 따라서 TCP에서 **reliable data transfer를 제공하기 위해선** unreilable한 부분을 reliable 할 수있도록 에러든 유실이든 **극복을 해야 한다.**

![image](https://user-images.githubusercontent.com/35518072/42744016-22ebb9e8-8904-11e8-9902-1d7a9e730e69.png)



# RDT 1.0 : reliable channel

Reliable data transfer를 이해하기 위해서 RDT라는 간단한 프로토콜을 디자인해보자. 먼저 unreliable channel에서 전송에러와 패킷유실 모두 발생하지 않는다면, 단순히 보낼 메시지를 패킷에 넣고 sender는 보내고 receiver는 받기만 하면된다.

# RDT 2.0 : channel with bit erros

패킷 유실은 없는데 에러가 발생하는 경우엔 어떻게 디자인해야 할까? 예를 들어, 친구와 전화통화를 하고 있다고 해보자. 전화하는 통신 자체가 unreliable 하기 때문에 중간에 끊길 염려가 있다. 전화를 할 때에는 대답을 하면서 잘 들었다는 신호를 보내게 되는데 잘 들리지 않으면 "뭐라고?"와 같은 말을 함으로써 안 들린다는 신호를 준다. 바로 이런 피드백이 중요하다는 것이 핵심이다. 결과적으로 통신 에러를 극복하기 위한 메커니즘은 다음과 같다.

* 먼저 전송된 데이터에 에러가 있는지 checksum을 통해 error detection을 한다.
* 에러가 없다면 **acknowledgement(ACK)**로 sender에게 에러가 없다고 알린다.
* 에러가 있다면 **negative acknowledgement(NAK)**로 에러가 있기 때문에 re-transmission을 하게 된다.

이걸 [FSM(Finite State Machine)](http://satrol.tistory.com/entry/FSMFinite-State-Machine-%ED%8A%B9%EA%B0%95-1)이라고 불리는 모델로 나타내면 아래 그림과 같아지는데 FSM에 대한 설명은 링크에 아주 자세히 설명하고 있으니 혹시나 까먹으면 보고 넘어가도록 하자.

<img src="https://user-images.githubusercontent.com/35518072/42744707-56f81d8a-8909-11e8-9aef-e9bd58445df1.png" width="500px">

먼저 sender를 보면 위에서부터 message를 받아서 패킷을 보내고 난 뒤에는 receiver의 응답이 ACK인지 NAK인지를 기다린다. Receiver는 패킷을 받고 난뒤에 에러가 발생한 corrupt일 경우 NAK를 보내고 notcorrupt일 경우, ACK를 보내게 된다. 기다리던 sender가 ACK를 받을 경우 그 다음 패킷을 보내고 NAK를 받을 경우 re-transmission 하는 형태가 된다.

# RDT 2.0 has a fatal flaw!

만약 receiver로부터 피드백을 받았는데 sender가 받았을 때 피드백 자체에 에러가 있어서 ACK인지 NAK인지 확인이 안된다면 어떻게 해야 될까? 무슨 피드백인지 모를 경우 그냥 다시 보내는 것이 좋겠지만 receiver의 입장에서는 그게 다시보내는 건지 아니면 처음 온건지 알 수 없기 때문에 그걸 확인할 방법을 마련해주어야 한다. 

# RDT 2.1

따라서, 헤더 부분에 한 영역을 더 추가해야 하는데 바로 **sequence number**라는 것이다. 전송이 재전송인지 아닌지를 확인하기 위한 것으로 1비트만 사용하면 0과 1을 번갈아 사용하면서 receiver의 입장에서 체크를 할 수 있다. 과정은 아래와 같다.

* sender는 sequence number 0과 함께 패킷을 보낸고 ACK/NAK를 기다린다.
* receiver는 sequence number 0과 함께 패킷을 받고 ACK를 보낸고 다음 패킷을 기다린다.
* 중간에 ACK에 에러가 발생!
* sender는 ACK인지 NAK인지 확인할 수 없기 때문에 sequence number 0과 데이터를 re-transmission 한다.
* receiver는 sequence number 0을 보고 re-transmission을 보고 다시 ACK를 보내고 다음 패킷을 기다린다.

# RDT 2.2

NAK를 쓰지 않고 ACK와 sequence number만 사용해서 에러 처리를 할 수 있는데 만약 sender가 sequence number 0을 보냈으면 receiver도 0과 ACK를 같이 보내면 원래의 ACK 역할을 하게 되고 ACK와 1을 보내면 원래의 NAK 역할을 하게 되는 것이다. 그러면 sender는 보낸 데이터의 sequence number인지 확인을 하고 re-transmission을 여부를 결정하게 된다.

