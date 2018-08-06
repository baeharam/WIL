---
layout: post
category: Network
title: "[소켓 프로그래밍]TCP 서버/클라이언트"
---

[윤성우의 TCP/IP 소켓 프로그래밍](http://www.orentec.co.kr/teachlist/TCP_IP_1/teach_sub1.php)을 공부하며 정리한 내용이며 사진은 [여기](https://www.codeproject.com/Articles/586000/Networking-and-Socket-programming-tutorial-in-C)를 참고했습니다.

# Flow Chart

클라이언트와 서버가 통신할 때 어떤 함수들이 호출되며 어떤 과정으로 이루어지는지 이해하기 위해선 flow chart를 이용하면 편하다.

<img src="https://www.codeproject.com/KB/IP/586000/network4_thumb_4_.png">

* `socket` : 서버가 소켓을 생성한다.
* `bind` : 소켓에 주소정보를 할당한다.
* `listen` : 클라이언트의 요청을 받기 위한 "연결 요청 대기 상태"로 들어간다.
* `connect` : 클라이언트가 연결요청을 한다.
* `accept` : 클라이언트의 연결요청을 수락한다. 이 때 데이터 교환을 위한 소켓이 생성된다.
* `send/recv` : 데이터 교환이 이루어진다.
* `close` : 생성한 소켓들이 소멸된다.

<br>

# listen

저번에 `bind` 함수까지 했으니 이제 `listen`함수를 할 차례이다. 이 함수는 클라이언트가 연결요청을 하기 전에 "연결요청 대기상태"로 만드는 역할을 하는데 이게 무슨 말인지 알아야 한다. 서버는 여러개의 클라이언트로부터 요청을 받을 수 있다. 그 때 어딘가에 그 요청을 대기시켜놓을 대기 큐를 만드는데, 그 큐를 준비해놓은 상태가 바로 "연결요청 대기상태"라고 하는 것이다. 함수의 매개변수를 보면서 이해해보자.

```c
#include <sys/socket.h>
int listen(int sock, int backlog);
// 성공시 0, 실패시 -1 리턴
```

* sock : 연결요청 대기상태에 두고자 하는 소켓의 파일 디스크립터이다.
* backlog : 연결요청 대기 큐의 크기정보를 전달하며 웹서버 같은 잦은 요청의 서버일 경우 최소 15이상을 전달해야 한다.

<br>

# accpet

클라이언트가 `connect`로 연결요청을 하고 대기 큐에 들어가게 됐을 때 대기 큐에서 대기중인 클라이언트의 요청을 수락하는 함수이다. 데이터를 주고받기 위해 소켓을 생성해야 하는데 여기선 직접 생성할 필요없이 이 함수가 만들어준다.

```c
#include <sys/socket.h>
int accept(int sock, struct sockaddr* addr, socklen_t* addrlen);
// 성공시 생성된 소켓의 파일 디스크립터, 실패시 -1 리턴
```

* sock : 서버소켓의 파일 디스크립터
* addr : 클라이언트의 주소값 전달, 함수호출이 끝나면 클라이언트의 주소정보 채워짐
* addrlen : addr에 전달된 주소의 변수크기를 바이트 단위로 전달, 변수에 저장한 다음 주소 값을 전달해야 한다.

<br>

# connect

클라이언트가 서버에게 연결요청을 하는 함수로, 연결요청이 접수된다는 것이 `accept` 함수의 호출이라고 인식하기 쉽다. 하지만 연결요청 대기 큐에 등록한다는 것이라는 걸 꼭 알아야 한다.

```c
#include <sys/socket.h>
int connect(int sock, struct sockaddr *servaddr, socklen_t addrlen);
// 성공시 0, 실패시 -1 리턴
```

* sock : 클라이언트 소켓의 파일 디스크립터
* servaddr : 연결요청 할 서버의 주소정보를 담은 주소 값
* addrlen : servaddr의 변수 크기를 바이트 단위로 전달

클라이언트는 서버와 다르게 `bind`함수를 호출하지 않으며 `connect`함수를 통해서 커널이 자동으로 호스트 ip와 임의의 포트번호를 통해 할당된다.