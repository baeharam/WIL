[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# 소켓

사실상 application process간의 통신이고 OS 내부를 건드리는 것이 아니다. OS에서 제공하는 인터페이스를 사용하는 것으로 printf를 이용해 화면에 출력하는 것이 이에 속한다. 따라서 소켓은 OS가 제공해주는 API 중에 하나라고 할 수 있다.

![image](https://user-images.githubusercontent.com/35518072/42719998-89d39352-875a-11e8-8f43-94c66f067f2c.png)

다시 생각해보면 OS가 제공해주는 것만 사용할 수 있는데 OS 안에는 application layer 밑에 있는 layer들이 구현되어 있다. 결국 application layer와 OS간의 인터페이스므로 바로 밑의 transport layer에서 제공해주는 것을 사용할 수밖에 없다. 현재 OS의 transport layer에는 TCP/UDP가 구현되어 있으므로 소켓을 사용할 때는 TCP 소켓이나 UDP 소켓을 사용해야 한다. TCP 소켓은 Socket stream이라고 불리고 UDP 소켓은 Socket datagram이라고 불린다.

<br>

# 소켓 프로그래밍

<img src="https://user-images.githubusercontent.com/35518072/42720067-ae67019e-875b-11e8-86d3-80f77d901fc3.png" width="500px">

먼저 큰 그림으로 보면 서버가 TCP 소켓을 생성하고 포트에 바인딩을 시키는데 웹서버면 80번 포트에 바인딩을 시킨다. 또 클라이언트가 요청할 것을 인지해야 하므로 listen 용도로 쓰고 준비가 됬으면 accept를 하게 된다. 이렇게 accept가 되면 서버는 클라이언트로부터 connection이 들어올 때까지 block이 된다.

클라이언트는 TCP 소켓을 생성하고 웹브라우저를 사용한다면 웹서버에 connect를 시킨다. 이제 TCP connecton이 형성되고 서버소켓과 클라이언트 소켓간의 read/write를 하게 될 경우 통신이 이루어지게 되는 것이다. 그리고 끝나면 close를 호출하게 된다.

<br>

# Function: socket

```c
int socket(int domain, int type, int protocol)
```

소켓을 생성하는 함수로 중요한 것은 type인데 TCP 소켓인지, UDP 소켓인지를 결정하는 것이다. `SOCK_STREAM`으로 쓰면 TCP이고 `SOCK_DGRAM`으로 쓰면 UDP 소켓이 된다. 함수가 소켓을 생성하면 해당 자료구조가 만들어질 것이고 리턴값은 소켓의 ID가 된다. 그 ID값을 가지고 소켓을 계속해서 지칭하게 된다.

# Function: bind

```c
int bind(int sockfd, struct sockaddr* myaddr, int addrlen)
```

방금 생성한 소켓을 사용해서 특정 address에 binding 시키는 함수이다. 특정 포트에 바인딩하겠다는 것이다.

# Function: listen

```c
int listen(int sockfd, int backlog)
```

방금 생성한 소켓을 listen 용도로 사용할 것이며 혹시라도 동시에 request가 여러개 들어올 경우 최대 몇개까지 담아놓고 처리하겠다는 것이다.

# Fucntion: accept

```c
int accept(int sockfd, struct sockaddr* cliaddr, int* addrlen)
```

다 준비가 끝났으니까 클라이언트로부터 연결을 기다리겠다는 것인데 중요한 매개변수가 2번째 매개변수로 서버 소켓이 block 되어있다가 클라이언트로부터 연결 요청이 오면 cliaddr 변수로 클라이언트의 IP주소와 포트 번호를 알게 된다.

# Function: connect

```c
int connect(int sockfd, struct sockaddr* servaddr, int addrlen)
```

클라이언트는 connect 함수로 서버에 요청을 할 때 binding을 시키지 않는데, 특정 포트를 써야 할 이유가 없기 때문이다. 웹서버같은 경우는 이전에 설명한 이유로 80번 포트를 써야 하지만 클라이언트는 남아도는 아무 포트나 사용하면 된다.

# Function: write

```c
int write(int sockfd, char* buf, size_t nbytes)
```

어떤 메시지를 쓸지 소켓 ID랑 넘겨주면 서버/클라이언트 소켓에 해당하는 곳에 쓰게 된다.

# Function: close

```c
int close(int sockfd);
```

데이터 교환이 끝났으면 사용했던 소켓을 release 시켜주어야 한다. 그래야 다른 프로세스가 사용할 수 있게 된다. 

# Function: cleanExit

```c
#include <signal.h>
void cleanExit() {exit(0);}
// socke code 안에서는
signal(SIGTERM, cleanExit);
signal(SIGINT, cleanExit);
```

하나 팁은 프로세스가 제대로 작동을 안할 때 Ctrl+c를 누르게 되는데 이러면 바인딩된 포트가 한동안은 release가 안되어서 문제가 발생할 수 있다. 그래서 위 코드로 `signal.h` 헤더파일을 사용해서 방지해줄 수 있다.