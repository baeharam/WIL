---
layout: post
category: Network
title: "[소켓 프로그래밍]인터넷 주소의 초기화와 할당"
---

[윤성우의 TCP/IP 소켓 프로그래밍](http://www.orentec.co.kr/teachlist/TCP_IP_1/teach_sub1.php)을 공부하며 정리한 내용입니다.

# inet_addr

`sockaddr_in`에서 주소정보를 저장하기 위해선 32비트 정수로 표현해야 하는데 보통 ip주소를 나타낼 때는 192.168.10.12와 같이 점이 찍힌 십진수로 나타낸다. 그럼 소켓을 생성할 때마다 이걸 변환을 해주어야 한다는 뜻인데, 편하게도 이걸 네트워크 바이트 순서의 32비트 정수로 변환해주는 함수가 존재한다.

```c
#include <arpa/inet.h>
in_addr_t inet_addr(const char *string);
// 성공 : 빅 엔디안으로 변환된 32비트 정수 값 리턴
// 실패 : INADDR_NONE 리턴
```

예를 들어 1.2.3.4를 집어넣어 변환했다고 하면 빅 엔디안을 작용하기 때문에 0x4030201이라는 주소값이 나온다.

<br>

# inet_aton

`inet_addr`과 똑같지만 구조체 변수인 `in_addr`을 이용한다는 점에서 다르다. 활용도는 `inet_aton`이 더 높다고 한다.

```c
#include <arpa/inet.h>
int inet_aton(const char *string, struct in_addr *addr);
// 성공 : 1 리턴
// 실패 : 0 리턴
```

string에 변환될 주소를 넣고 addr에 in_addr형 변수의 주소값을 넣어주면 된다. 즉, sockaddr_in의 sin_addr 주소값을 넣어주는 것과 동일하다.

<br>

# inet_ntoa

`inet_aton`과 반대기능의 함수로 네트워크 바이트 순서의 ip주소를 쉽게 이해할 수 있는 문자열 주소로 변환시켜준다. 주의할 점은 함수 내부적으로 메모리를 할당해 변환된 주소를 저장하기 때문에 한번 더 함수가 호출되면 이전값은 사라지게 된다. 따라서, 계속 사용하기 위해선 다른 변수에 저장해야 한다.

```c
#include <arpa/inet.h>
char* inet_ntoa(struct in_addr addr);
// 성공 : 변환된 문자열의 주소 값 리턴
// 실패 : -1 리턴
```

<br>

# 주소정보 초기화 및 할당

`sockaddr_in`의 변수초기화를 보았고 이제 초기화된 주소정보를 소켓에 할당하기 위해선 `bind`함수를 사용해야 한다.

```c
#include <sys/socket.h>
int bind(int sockfd, struct sockaddr *myaddr, socklen_t addrlen);
// 성공 : 0 리턴
// 실패 : -1 리턴
```

여기서 `sockfd`는 소켓의 파일 디스크립터를 말한다.

```c
// 변수 생성
int serv_sock;
struct sockaddr_in serv_addr;
char *serv_port = "9190";

// 서버 소켓(리스닝) 생성
serv_sock = socket(PF_INET, SOCK_STREAM, 0);

// 주소정보 초기화
memset(&serv_addr, 0, sizeof(serv_addr));
serv_addr.sin_family = AF_INET;
serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
serv_addr.sin_port = htons(atoi(serv_port));

// 주소정보 할당
bind(serv_sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr));
```

여기서 몇가지를 배웠다.

1. memset을 사용해 serv_addr의 멤버들을 전부 0으로 초기화하는 이유는 sockaddr_in 구조체의 멤버인 sin_zero를 0으로 초기화하기 위함이다.
2. INADDR_ANY는 IP주소를 직접 입력하는 수고를 덜기 위해 지정된 상수로 서버 프로그램의 구현에서 많이 선호된다. 클라이언트 프로그램에선 서버에 접속하는 것이기 때문에 IP주소를 써주어야 한다.