[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# Flow Control

Sender가 있고 receiver가 있을 때 데이터를 얼마만큼의 속도로 보낼 것인가에 대한 고려가 flow control이며 "receiver의 능력이 얼마나 되는가?"에 대해 따져봐야 한다. 이건 굉장히 단순한데 receiver가 현재 receive buffer의 용량을 sender에게 말해주면 된다. Segment의 헤더에는 receive buffer라는 부분이 있고 그걸 통해 남은 용량을 말해주면 sender가 그것에 따라서 데이터를 얼마나 보낼지 결정할 수 있는 것이다.

이렇게 간단하지만 **문제점 한가지**가 존재한다. 예를 들어, receive buffer의 용량이 꽉찬 경우를 생각해보자. 남은 용량은 0이기 때문에 sender에게 남은 용량이 없다는 메시지를 헤더에 실어 보낸다. Sender는 메시지를 받고 더 이상 보내지 않는데 receiver에겐 sender에게 보낼 데이터가 없는 상황이다. 따라서 application layer로 데이터를 전송해서 receive buffer가 비어있는 경우에도 sender에게 보낼 수가 없기 때문에 **교착상태**에 빠지게 된다.

이러한 문제점 때문에 sender로 하여금 receive buffer가 꽉 찬 상태에서도 찔러보는 형태로 1byte의 segment를 날려 **피드백이 오게 한다.** 이후부터 피드백 헤더에 있는 receive buffer의 용량을 계속 확인하고 여유공간이 생기게 되면 데이터를 다시 보내게 된다.



# Nagle's algorithm

Application layer에서 사용자 프로세스가 데이터를 생산해서 segment의 데이터부분에 넣어 보내게 되는데 데이터를 얼마나 넣어서 보내야 될까? 

* 바로 바로 보내는 경우 : 헤더가 40바이트인데 데이터가 1바이트일 경우 너무 오버헤드이다.
* 꽉 채워서 보내는 경우 : 보내는 시간이 너무 오래 걸린다.

이걸 아주 간단히 해결하는 알고리즘이 존재한다.

1. **데이터가 몇 바이트든지 일단 보내고 다시 segment를 채운다.**
2. **보낸 segment로부터 ACK가 왔을 경우 segment가 다 채워지지 않아도 보낸다.**
3. **Segment를 꽉 채웠을 경우 보낸다.**

이 알고리즘은 sender의 입장에서 고려해야 할 사항이며 **receiver의 입장**에서 고려해야 할 사항도 있다. 간단히 얘기하면 데이터를 받을 때 segement 단위로 받자는 개념으로 receive buffer의 빈 공간이 **MSS(Maximum Segment Size)보다 작다면 헤더에 0(빈공간 없음)을 입력하고 보내자**는 것이다. 즉, 아예 segment를 받을 수 없으면 데이터를 받지 않겠다는 말이다.



# Connection Management

이제까지 배웠던 것은 TCP connection이 완료되고 sequence number를 tracking 하거나 timer를 이용해 packet loss를 감지하거나 하는 방법들이었는데 여기선 TCP connection이 어떻게 이루어지는지를 볼 것이다. 호스트 A와 B가 있다고 하고 TCP connection을 시도중이라고 하자.

A는 B에게 TCP request를 보내고 B는 받아들이는 의미로 accept를 보낸다. 따라서 A의 입장에선 TCP connection이 된 것으로 인지하지만 B입장에서는 accept에 대한 반응이 오지 않았기 때문에 connection이 되었는지 알 방도가 없다. 이런 이유로 TCP connection은 2-way hanshake로는 이루어질 수 없고 A도 accept에 대해 반응을 해주어서 3-way handshake로 완성될 수 있다.

## 3-way Handshake

<img src="https://user-images.githubusercontent.com/35518072/43060097-58f5a6ee-8e8a-11e8-9fd4-ad6ba094db91.png" width="500px">

3-way handshake는 특별한 비트를 이용해서 구현하는데 헤더에 있는 SYN이라는 비트를 사용한다. SYN은 synchronize라는 것으로 동기화시킨다는 의미이다. 

* SYN 비트가 1일 경우는 TCP connection을 할 경우밖에 없고 sequence number와 같이 보낸다.
* B는 A에게 SYN=1임을 봤기 때문에 TCP connection임을 알고 accept를 한다.
* 이후 A에게 SYN과 sequence number, 피드백을 보낸다.
* 3-way handshake이기 때문에 마지막으로 A가 SYN비트 0과 sequence number, 피드백을 보내고 끝이 난다.

## Closing a TCP connection

<img src="https://user-images.githubusercontent.com/35518072/43060682-ed5c06dc-8e8c-11e8-9b0e-d630b48b3470.png" width="500px">

TCP connection을 종료하기 위해선 또 하나의 segment를 보내는데 이번엔 헤더의 FIN이라는 비트 값을 이용한다. 이 값을 1로 설정해서 보내면 클라이언트의 입장에서 보낼 데이터가 없다는 것을 의미하며 서버는 ACK를 보낸다. 하지만 이걸로 끝나는 것이 아니라 서버 입장에선 보낼 데이터가 있을수도 있기 때문에 서버로부터도 FIN 값을 받아야 한다. 그러면 클라이언트는 ACK를 보내서 TCP connection이 종료되는 것이다.

하지만 위 그림을 보면 Timed wait이란 부분이 있어서 일정 시간이 지난 후에 종료가 되는데 그 이유가 무엇일까? 바로 마지막 ACK가 유실될 가능성을 대비한 것으로 ACK가 유실될 경우 클라이언트는 TCP connection을 끊었는데 서버에서는 ACK를 받지 못했다. 따라서 서버는 FIN을 다시 보내야 되는데 연결은 이미 끊긴 상황이 되는 것이다. 이런 이유 때문에 마지막 ACK 이후에는 어느 정도의 시간을 기다린 후에 TCP connection을 종료시킨다.

## Window Size

Window size는 TCP 헤더 중 하나로 예전에 설명할 때 flow control에서 receive buffer의 size에 따라 보내는 데이터의 크기가 달라져야 하기 때문에 그걸 결정하는 값이라 말했었다. 하지만 congestion control까지 들어가게 되면 네트워크의 상태까지 체크해야 하기 때문에 그런 부분까지 고려했을 때의 보낼 데이터 사이즈라고 하는 것이 더 정확하다고 볼 수 있다.

예를 들어, receive buffer의 여유공간이 100KB라고 하면 segment를 통해 report가 될 것이다. Sender는 window size가 10KB였는데 이론상 100KB까지 늘리게 된다. 하지만 네트워크의 상태가 100KB의 데이터를 수용할 수 없다면 그에 맞춰 송신할 데이터의 크기를 맞추는 것이 적합하다. 또한 요즘엔 메모리의 크기가 굉장히 커졌기 때문에 receive buffer의 크기보다는 네트워크의 상태에 맞추는 것이 많다고 한다.



