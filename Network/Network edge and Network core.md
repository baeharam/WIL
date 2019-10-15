[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)를 정리한 것입니다.

# Network Edge

네트워크를 연결하는 각각의 구성요소를 말하며 application이나 host라고 부른다. Network edge의 서비스는 2가지로 나눌 수 있는데 connection-oriented service와 connectionless service이다.

## Connection-oriented service

TCP(Transmission Control Protocol)라는 service가 이에 해당하고 다음과 같은 3가지 특징을 가진다.

* Reliable, in-order byte-stream data transfer : 신뢰할 수 있고 온 순서대로 보낸다.
* Flow control : receiver가 받아들일 수 있는 속도로 보낸다. (슈퍼컴퓨터와 286과의 통신 생각)
* Congestion control : receiver가 슈퍼컴퓨터가 되었다고 해도 중간의 네트워크 회선 bandwidth가 아주 작다면 조절을 해야 한다.

웹브라우저가 웹서버에게 웹페이지를 요청한다고 하면 신뢰성이 있어야 하므로 TCP를 사용할 것이다.

## Connectionless service

UDP(User Datagram Protocol)라는 service가 이에 해당하고 TCP의 특징과는 완전히 반대의 성격을 갖는다. 받는 것에 상관없이 마음대로 보낼 수 있는 것이다. 굳이 reliable 하지 않아도 되면 TCP는 컴퓨팅 리소스나 네트워크 리소스가 많이 들기 때문에 UDP를 쓰는 것이 낫다. (편지에서 등기로 보내냐 일반으로 보내냐의 차이점 비유)



# Network Core

Network core란 edge들을 연결하는 중간 장치들을 말하는 것으로 대표적인 예로는 라우터가 있다. 그러면 라우터는 데이터를 어떻게 전송할까? Circuit switching과 Packet switching이라는 2가지 방식이 있다.

## Circuit switching

출발지와 목적지의 길을 미리 예약해놓고 특정 사용자만이 사용할 수 있도록 만들어놓은 것으로 유선전화망이 이에 속한다. 즉, 정해진 bandwidth에 대해 동시에 사용할 수 있는 사용자 수를 정해놓는 것이다. 예를 들어, 영희와 철수가 전화를 하고 있고, 슬기와 예리가 전화를 하고 있다고 하자. Circuit switching을 사용하는 전화망은 2개의 circuit만 허용하기 때문에 주영이가 전화를 하려고 시도했으나 기다려야 하는 상태가 되는 것이다.

## Packet switching

사용자가 보내는 패킷을 그때 그때 받아서 forwarding 해주는 것으로 들어온 순서대로 보내게 된다. 인터넷에선 packet switching을 사용하는데, 전화는 쉬지않고 연결되어있는 반면에 인터넷에서 뉴스를 본다고 생각하자. 뉴스를 클릭하면 웹브라우저에서 웹서버에 패킷을 보내게 된다. 그런데 계속해서 그 뉴스를 클릭하는 것이 아니라 뉴스를 클릭하고 보는 시간이 있기 때문에 전화와는 다르다고 할 수 있다.

만약 N명의 사용자를 받아들이는 라우터가 있고 outgoing link의 bandwidth가 1Mbps라고 한다면, 사용자가 데이터를 보낼 때의 속도가 100Kbps 일 경우 circuit switching은 10명밖에 받을 수가 없다. 그러나 packet switching의 경우, 한번에 10명이 데이터를 보내지 않는 이상 제약이 없다. 복잡하게 계산할 수 있는데, 한번에 10명이 패킷을 보낼  확률은 0.0004이므로 아주 낮다고 할 수 있다. 인터넷의 경우 계속 패킷을 보내지도 않는데 10명만 사용하게 한다면 리소스 낭비가 심하기 때문에 packet switching을 사용하는 것이 훨씬 나은 것이다.

## Packet switching의 4가지 delay

Packet switching에는 4가지 종류의 지연이 일어난다.

* **Processing delay** : 라우터가 패킷을 보고 목적지가 어딘지, 내용이 무엇인지 점검하는 시간이다.
* **Queueing delay** : 회선의 bandwidth가 허용할 수 있는 패킷의 양보다 더 많은 패킷이 올 경우 버퍼(혹은 큐)라는 곳에 쌓이게 되고 여기서 기다리는 시간을 말한다.
* **Transmission delay** : 패킷은 비트로 이루어져 있으므로 패킷이 다음 라우터로 전송되기 위해선 일단 현재 라우터에서 나가야 하기 때문에 패킷의 모든 비트가 나가는 시간을 말한다. 패킷의 크기를 L이라고 하고 bandwidth를 R bps라고 한다면 L/R로 정의된다.
* **Propagation delay** : 다음 라우터로 전송되는 시간으로 전송되는 속도는 사실상 빛의 속도이므로 회선의 길이에 따라 달라지기 때문에 회선의 길이 / 빛의 속도로 정의할 수 있다.

## Delay 개선하기

딜레이는 작으면 작을수록 좋기 때문에 줄이기 위한 노력을 한다.

* Processing delay : 더 좋은 라우터를 사면 된다. (라우터 성능 개선)

* Transmission delay : 케이블의 bandwidth를 늘리면 된다. (케이블 공사) KT나 SKT에서 속도를 빠르게 하기 위해 가끔씩 대규모 공사를 하는 것이 이에 속한다

* Queueing delay

  사용자에 따라 큐에 들어가는 패킷의 양이 달라진다. 새벽 2시같은 경우 더 없을 거고, 한창 낮시간에는 많을 것이다. 따라서 조절을 할 수가 없다. 추석 때 밀리는 것과 동일. 심지어 **큐의 크기도 무한대가 아니기 때문에 더 많은 패킷이 들어올 경우, 패킷이 계속 버려지게 된다.** (패킷 유실 발생 = packet loss) 인터넷에서 발생하는 packet loss의 90% 이상이 큐의 크기를 넘어버려서 발생한다. 어떤 패킷이 사용자가 몰리는 시간에 전송되었다고 하면 여러개의 라우터를 거쳐서 목적지에 도달할텐데, 큐가 차있는 라우터에 도착했을 경우 packet loss가 발생한다. 하지만 TCP라는 서비스는 reliable하다고 했는데 어떻게 그것을 보장하는가? **패킷을 재전송해서 무조건 전송될 수 있도록 한다.** 그런데 이 재전송에도 2가지 방식이 있다.

  * 처음 시작부터 재전송
  * 이전 라우터에서 재전송

  Network edge에서 TCP가 서비스를 관장하기 때문에 라우터는 전달하는 역할만 한다. 따라서 처음부터 재전송하게 된다. 이 방식이 현재의 인터넷 디자인이다. 왜 이렇게 했을까? 라우터는 전달만 빠르게 해주는 것이 목적이기 때문에 그 기능에 극대화되어있는 것이다. 따라서 다른 기능적인 메커니즘은 edge에 모아두는 것이다. 라우터는 단순전달만 하기 때문에 dumb core라고도 불린다.