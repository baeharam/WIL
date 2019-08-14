---
layout: post
category: Network
title: "[소켓 프로그래밍]주소정보의 표현"
---

[윤성우의 TCP/IP 소켓 프로그래밍](http://www.orentec.co.kr/teachlist/TCP_IP_1/teach_sub1.php)을 공부하며 정리한 내용입니다.

# POSIX

소켓 프로그래밍에선 이미 정의된 수많은 자료형을 사용하는데 그걸 정의하는 것이 POSIX(Portable Operating System Interface)이다. POSIX가 무엇인지 직접 적고 싶으나 나보다 위키백과와 스택오버플로우의 인용문을 보는 것이 이해가 빠를 것으로 생각된다.

> 서로 다른 UNIX 계열 운영체제의 공통 API를 정리하여 이식성이 높은 유닉스 응용 프로그램을 개발하기 위한 목적으로 IEEE가 책정한 API 규격이다.
>
> *[위키백과](https://ko.wikipedia.org/wiki/POSIX)*

>[POSIX](http://en.wikipedia.org/wiki/POSIX) is a family of standards, specified by the [IEEE](http://www.ieee.org/portal/site), to clarify and make uniform the application programming interfaces (and ancillary issues, such as commandline shell utilities) provided by Unix-y operating systems. When you write your programs to rely on POSIX standards, you can be pretty sure to be able to port them easily among a large family of Unix derivatives (including Linux, but not limited to it!); if and when you use some Linux API that's not standardized as part of Posix, you will have a harder time if and when you want to port that program or library to other Unix-y systems (e.g., MacOSX) in the future. 
>
>(발번역 주의) POSIX는 UNIX 계열 운영체제의 API (그리고 쉘 유틸리티 같은 일부 이슈들)를 균일하게 만들고 명확하게 하기 위하여 IEEE(아이-트리플-이)에 의해 정해진 표준이다. POSIX 표준을 이용해서 프로그램을 작성하면 다른 UNIX 계열 운영체제(리눅스를 비롯해 다른 것들도)에 쉽게 포팅할 수 있다. 하지만 POSIX의 표준이 아닌 리눅스 API를 사용한다면 다른 UNIX 계열 운영체제의 라이브러리나 프로그램으로 포팅하기가 쉽지 않을 것이다.
>
>*[스택오버플로우](https://stackoverflow.com/a/1780614)*

<br>

# POSIX가 정의하는 자료형

## sys/types.h

* int8_t : signed 8-bit int
* uint8_t : usigned 8-bit int (unsigned char)
* int16_t : signed 16-bit int
* uint16_t : unsigned 16-bit int (unsigned short)
* int32_t : signed 32-bit int
* uint32_t : unsigned 32-bit int (unsigned long)

## sys/socket.h

* sa_family_t : 주소체계 (address family)
* socklen_t : 길이정보 (length of struct)

## netinet/in.h

* in_addr_t : IP주소정보, unit32_t로 정의되어 있음
* in_port_t : PORT번호정보, unit16_t로 정의되어 있음\

<br>

# sockaddr_in의 멤버 분석

bind 함수에 주소정보를 전달하는 용도로 사용되는 구조체이다.

```c
struct sockaddr_in
{
    sa_family_t sin_family; // 주소체계
    uint16_t sin_port; // 16비트 TCP/UDP 포트 번호
    struct in_addr sin_addr; // 32비트 ip주소
    char sin_zero[8]; // 사용안함
}
```

각 멤버에 대해 자세히 알아보자.

## sin_family

프로토콜 체계마다 적용하는 주소체계가 다른데, IPv4에선 4바이트 주소체계를, IPv6에선 16바이트 주소체계를 사용한다. 따라서 그 정보를 저장해야 한다.

* AF_INET : IPv4전용
* AF_INET6 : IPv6 전용
* AF_LOCAL : 로컬 통신을 위한 것

## sin_port/sin_addr

각각 16비트 포트 번호와 32비트 ip주소를 저장하기 위해 쓰이며 네트워크 바이트 순서로 저장해야 한다. `sin_addr`의 타입이 `struct in_addr`인데 멤버가 하나인 구조체이므로 32비트 정수자료형으로 인식해도 문제 없다.

```c
struct in_addr
{
    in_addr_t s_addr; //32비트 IPv4 주소
}
```

## sin_zero

단순히 `sockaddr_in`의 크기를 `sockaddr`과 일치시키기 위해 삽입된 멤버로 반드시 0으로 채워야 한다.

<br>

# bind 함수로의 전달

```c
struct sockaddr_in serv_addr;
// ...
if(bind(serv_sock, (struct sockaddr*) &serv_addr, sizeof(serv_addr))==-1)
    printf("error!");
// ...
```

두 번째 인자가 중요한데, `struct sockaddr*`로 캐스팅하고 있는 것을 볼 수 있다. 원래 bind함수에 전달해줘야 되는 것이 `sockaddr`의 주소이기 때문에 캐스팅하는 것인데 왜 `sockaddr`로 하지 않고 `sockaddr_in`으로 했을까? 그 이유는 해당 구조체의 멤버를 봐야 한다.

```c
struct sock_addr
{
    sa_family_t sin_family; // 주소체계
    char sa_data[14]; // 주소정보
}
```

원래대로라면 `sa_data`에 ip주소, 포트번호를 담고 나머지는 0으로 채워 전달해야 하지만 주소정보를 담기에 너무 불편하기 때문에 `sockaddr_in`을 이용해서 넘긴다음 캐스팅하는 것이 된다.



