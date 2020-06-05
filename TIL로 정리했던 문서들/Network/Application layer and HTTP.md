[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# Carvan analogy

10개의 차가 톨게이트를 통과한다고 해보자. 이 때 각 차는 비트이고 10개의 차는 1개의 패킷으로 생각할 수 있다. 

<img src="https://user-images.githubusercontent.com/35518072/42719538-25a1beca-8752-11e8-8a53-177cba8ca99a.png">

* 차가 톨게이트를 지나는데 걸리는 시간 = 12초 (transmisson delay)
* 차의 속도 = 100km/h (propagation delay)
* 톨게이트 사이의 거리 = 100km

그럼 첫번째 톨게이트를 지나서 다음 톨게이트까지 가는데 걸리는 시간은 어떻게 될까? 먼저 10대의 차가 첫번째 톨게이트를 통과해야 하므로 12*10 = 120초가 걸린다. 또한 100km의 거리를 이동하는데 1시간안에 이동할 수 있으므로 60분이 걸린다. 120초는 2분이므로 결국 62분이 걸린다는 것을 알 수 있다. 

여기서 중요한 것은 사실상 propagation delay는 빛의 속도이므로 첫번째 라우터를 거치는 순간 다음 라우터에 도달하게 된다. 앞쪽 비트가 먼저 전송될 텐데 먼저 **두번째 라우터에 전송되었다고 해서 계속 진행되는 것이 아니라 패킷의 모든 비트가 두번째 라우터에 도달할 때까지 기다린다.**

<br>

# Network Layer

레이어라는 것은 개념적으로 나눠놓은 것이고 각 레이어는 다양한 프로토콜이 존재한다. Applicaton layer에서는 HTTP, Transport layer에서는 TCP/UDP, Network layer에서는 IP, Link layer에서는 WIFI, LTE 등이 중요한 프로토콜이다.

<br>

# Application Layer

웹브라우저와 웹서버가 통신을 할 때 라우터들을 거치겠지만 단순히 프로세스와 프로세스간의 통신이라고 생각하면 된다. 중간에 네트워크를 신경쓸 필요는 없다. 네트워크 계층이라는 것은 클라이언트와 서버에 존재하는 것이고 라우터에는 network layer까지만 존재한다. 

## 서버와 클라이언트

웹서버와 웹브라우저가 대표적인 예이며 서버는 permanent한 ip주소를 가져야 한다. 즉, 바뀌지 않고 고정된 주소를 가져야 하는데 클라이언트는 고정되지 않아도 된다. 당연한 것이, 서버가 고정되어 있어야 클라이언트가 찾아갈 수 있기 때문이다.

## Process Communication

결국, 서버 프로세스와 클라이언트 프로세스 간의 통신인데 OS에서 프로세스 간의 통신은 IPC(InterProcess Communication)방식을 통하여 OS가 제공하는 인터페이스를 이용해 통신을 한다. 서버와 클라이언트간의 통신은 다른 컴퓨터간의 IPC인 것이 다른 점이다. **이런 통신에도 OS가 인터페이스를 만들어 놓았는데 이걸 소켓(Socket)이라고 부른다.** 소켓을 이용해서 어떤 프로세스가write하면 다른 프로세스가 read하는 방식이다. 하지만 소켓을 통해서 통신을 한다고 해도 연결이 되야 하므로 의사표현을 해야 하기 때문에 소켓의 주소를 알아야 한다. 

이런 **소켓의 주소를 인덱싱하는 것이 IP주소와 포트의 조합**이다. IP주소는 인터넷 상에 존재하는 컴퓨터를 지칭하는 주소이다. 컴퓨터 상에는 프로세스가 많이 존재하고 특정 프로세스를 지칭해야 하므로 포트가 그 역할을 해주게 된다. 만약, 웹브라우저를 통해 네이버에 접속한다고 해보자. 이 과정은 웹브라우저로 웹서버 프로세스의 소켓에 연결하고 싶다는 의사표현과 같으므로 실제로는 도메인을 입력하는 것이 아니라 IP주소와 포트 번호를 입력해주어야 한다. 하지만, 외우기가 어려우므로 편리함을 위해 DNS를 이용하여 도메인을 IP주소와 포트 번호로 변환시켜 접속하게 된다.

보통 모든 웹서버들은 공통된 80번 포트를 쓰는데 왜 그럴까? 서버는 24시간 켜져 있어야 하고 항상 같은 IP주소를 가져야 하는데 DNS가 하는 일은 IP주소로만 변환을 시켜주고 포트번호는 따로 만들어주지 않는다. 그러면 수많은 웹서버들이 있을텐데 각 웹서버의 포트 번호가 달라지게 될 경우, 상당히 귀찮아질 수 있기 때문에 포트번호를 80번으로 고정한 것이다.

## 계층간의 관계

네트워크 계층에서 하위계층은 상위계층에게 서비스를 제공하는 역할을 한다. 따라서 application layer에선 transport layer에서 제공하는 기능들을 지원받는 것이다. 어떤 기능들을 지원할지 어플리케이션 개발자가 요청할 수 있다. Transport layer에 대한 기능의 희망사항은 다음과 같은 것들이 있다.

* **Data integrity** : 보내는 데이터가 유실되지 않고 목적지까지 도달했으면 좋겠다.
* Timing : 보내는 데이터가 예를 들면, 10 밀리세컨드 안에 도착했으면 좋겠다.
* Throughput : 보내는 데이터가 어느정도의 용량이 나왔으면 좋겠다.
* Security : 보내는 데이터가 안전했으면 좋겠다.

하지만 transport layer는 여기서 data integrity만 제공하고 나머지 기능은 제공해주지 않는다. 이 기능을 TCP라는 프로토콜이 제공해주며 UDP는 그마저도 제공해주지 않는다. 만약 다른 기능들이 필요하다면 어떻게 될까? Application layer 차원에서 생성해야 한다.

Application layer에는 다양한 프로토콜이 존재하며 해당 프로토콜이 사용하는 transport layer의 프로토콜이 정해져 있다.

<br>

# HTTP(HyperText Transfer Protocol)

Hypertext는 텍스트인데 중간 중간에 다른 텍스트를 지칭하는 링크가 있는 텍스트를 말한다. 단순히, 이런 텍스트들을 전송하는 프로토콜이다. 따라서 웹페이지에 접속하기 위해서 컴퓨터나 스마트폰을 통해 "내가 요청하는 hypertext를 주세요!" 하고 말하는 것이다. 이걸 HTTP request라고 하고 그에 따라 해당 웹페이지의 웹서버는 HTTP response를 하는 것이다.

![image](https://user-images.githubusercontent.com/35518072/42719838-727bfa3a-8757-11e8-9a7e-ada3253a6569.png)

그런데 http가 application layer 프로토콜이기 때문에 transport layer의 TCP 프로토콜을 사용하기 때문에 http request/response 이전에 TCP connection을 해주어야 한다. HTTP의 또 하나의 특징은 stateless 하다는 것으로 단순히 요청에 해당하는 파일을 찾은 다음 보내는 역할만 하고 기억하지 않는다는 것이다. 전혀 상대방에 대한 상태를 기억하지 않는다.

HTTP는 TCP connection을 사용하는 방식에 따라서 2가지로 나눌 수 있다.

* **persistent HTTP** : HTTP request/response를 완료한 후에 TCP connection을 끊지 않고 계속해서 재사용 하는 경우
* **non-persistent HTTP** : 위와 반대로 완료한 후에 끊는 경우

예를 들어, 네이버 메인페이지(index.html)에 접속한다고 하면, 웹서버에 먼저 TCP connection을 요청하게 되고 서로 연결이 된다. 이후 HTTP request/response를 통해서 index.html 파일을 가져오는데 다양한 이미지/영상에 대한 링크들이 존재하기 때문에 해당 파일에 대해서도 HTTP request/response 과정을 거쳐야 한다. 그러나 non-persistent HTTP 일 경우 다시 TCP connection을 해야 하는 것이고 persistent HTTP 일 경우 계속해서 재사용하는 것이다. 보통 웹브라우저에선 persistent HTTP를 사용한다. 참고로 TCP connection을 끊을 때는 클라이언트와 서버가 같이 끊는다는 걸 말한다. (모든 패킷이 전송된 상태에서)

그런데 persistent HTTP를 사용해도 웹페이지에 보여야 할 이미지가 4개라면 4번의 HTTP request/response를 거쳐야 한다. 비효율적이기 때문에 실제로는 4개의 이미지가 보여야 할 걸 알기 때문에 한번에 4개를 요청하는 HTTP request를 보내는 파이프라인 방식을 사용한다.

<br>

# Persistent HTTP 예제

![image](https://user-images.githubusercontent.com/35518072/42729380-66d9b630-8811-11e8-808a-9e6d1648069e.png)

RTT(Rount Trip Time)는 요청을 하고 받을 때까지의 시간이다. 처음에 TCP connection을 시도하기 때문에 한번의 RTT가 생기고 그 다음 HTTP를 이용해 request/response를 하기 때문에 다시한번 RTT가 생긴다. 이런 개념에서 예제를 풀어보자.

* TCP handshake, HTTP request = K bit long
* HTML object = L bits
* N reference objects, each = L bit long
* Link bandwidth = R bps
* Propagation delay = d seconds

클라이언트를 A, 서버를 B라고 하면 A가 B에게 TCP connection을 시도할 때 transmission delay + propagation delay를 계산해야 한다. Transmission delay는 K bit의 길이를 가진 TCP connection이 R bps로 나가야 하므로 K/R이 걸리고 propagation delay가 d이므로 K/R+d의 시간이 걸리게 된다. 

서버로부터 응답이 올 때도 K/R+d가 걸리기 때문에 TCP handshake는 총 2*(K/R+d)가 걸린다. 이제 HTTP request/response를 해야 하는데 request 때는 크기가 같으므로 K/R+d가 걸리고 response를 할 때는 HTML object의 크기가 L bits이므로 L/R+d만큼 걸린다. 하지만 N개의 링크가 존재함으로 HTTP request/response를 총 N번 더 해야 한다. Persistent HTTP이므로 다시 TCP connection을 하지 않기 때문에 아래와 같은 결과가 나온다.
$$
3\times (K/R+d) + (L/R+d)+N\times (K/R+d+L/R+d)
$$
하지만 여전히 N개의 링크가 존재할 때 N번의 HTTP 통신을 해야 하므로 개선시키기 위해선 위에서 말했던 파이프라인 방식을 사용해야 한다. 한번에 N개의 request를 보내고 N개의 response를 받으므로 위 수식의 가장 오른쪽 부분이 다음과 같이 변하게 된다.
$$
(N\times K/R+d)+(N\times L/R+d)
$$
뭐가 개선된 것일까? Propagation delay인 d가 1번씩만 붙는 다는 것을 알 수 있다. 원래 방법으로는 각각 N번의 propagation delay가 생기기 때문에 비효율적인 것이다.

<br>

# HTTP Request message

HTTP Request를 할 때는 여러가지 메시지로 구분이 된다.

* GET : 해당 웹페이지를 가져온다.
* HOST : 목적지의 웹페이지 주소가 무엇인지
* User-agent : 사용자가 사용하는 OS, 웹브라우저 등을 표시해서 서버가 그걸 보고 커스터마이징 된 웹페이지를 보낼 수 있다.
* Connection : close/keep alive로 persistent인지 non-persistent인지 구분한다.
* Accept-language : 사용자가 선호하는 언어로, 없으면 기본값을 보낸다.

실제로 GET정도만 알면 문제 없다.

# HTTP Response message

이 메시지도 여러가지로 구분이 되지만 status code, 즉 응답처리가 어떻게 되었는지만 알아보도록 하자.

* 200 OK : 요청이 잘 처리되어서 데이터가 잘 전달되었다.
* 404 Not Found : 요청한 데이터가 서버에 없는 경우이다.

이외에도 400 Bad Request라던가, 505 HTTP Version Not Supported 등이 있다.

<br>

# Cookie

HTTP는 위에서 언급했다시피 stateless하기 때문에 request/response를 하고나서 상태를 기억하지 않는다. **그러나 상태를 기억하고 싶을 때가 있기 때문에 그걸 사용하는 트릭을 cookie라고 부른다.** 아래 그림을 보자.

<img src="https://user-images.githubusercontent.com/35518072/42729524-c7503ed6-8815-11e8-9c97-7c0778e93586.png" width="500px">

먼저 클라이언트는 아마존에 접속하기 위해 HTTP request를 보냈는데 아마존 서버의 데이터베이스에서 사용자에게 쿠키가 없는 것을 확인했다. 쿠키가 없으므로 클라이언트에게 1678이라는 아이디로 HTTP response에 같이 보냈다. 이제 클라이언트는 아마존에 접속할 때마다 쿠키 필드를 만들어서 1678을 같이 보내게 되는 것이다. 1주일 뒤에 다시 방문했다고 하면 아마존은 1678이라는 아이디를 가진 클라이언트의 취향에 따라 커스터마이징을 해서 HTTP response를 보내게 된다.

<br>

# Web cahces(proxy server)

클라이언트와 서버 사이의 HTTP request/response가 일어날 때 중간에 매개체를 해주는 역할을 한다. 그리고 서버로부터 가져온 object를 가지고 있다가 다른 클라이언트가 요청을 할 경우 서버에서 다시 가져오지 않고 해당 object를 HTTP response로 보낸다. 이런 웹 캐시의 장점은 크게 3가지가 있다.

<img src="https://user-images.githubusercontent.com/35518072/42729599-1315b718-8818-11e8-966b-00c903c46101.png" width="400px">

* 캐싱을 하기 때문에 **사용자의 입장에서 훨씬 빠르게** 이용할 수 있다.
* 서버의 입장에서도 HTTP request를 덜 받기 때문에 **부하가 적어서 좋다.**
* 서비스를 하는 단체 입장에서도 외부로 가는 트래픽이 없기 때문에 **비용이 덜 들어서 좋다.** 예를 들어, 한동대학교 네트워크에서 proxy server를 사용하는데 SKT랑 계약을 맺고 있다고 하면 proxy server로 인해 외부로 나가는 트래픽이 적어져서 그만큼의 돈을 아낄 수 있는 것이다.

# Proxy server 예제

<img src="https://user-images.githubusercontent.com/35518072/42729617-baf4bcf4-8818-11e8-9946-3203d4acff07.png" width="400px">

위와 같은 경우를 생각해보자. 학교의 네트워크에 접속하여 인터넷을 이용하는데 15Mbps를 가진 회선을 사용한다면 사용자가 몰릴 경우 굉장히 느려질 수 있다. 이걸 해결하는 방법은 2가지인데 bandwidth를 늘리거나, local proxy server를 만드는 것이다. 여기서 계산하진 않겠지만 bandwidth를 늘리게 되면 돈도 많이 들 뿐더러 proxy server를 만드는 것보다 느리다. 그러면 proxy server는 좋기만 할까? 아니다. Consistency 문제가 발생하는데, 만약 서버의 웹페이지가 업데이트 됬다면 예전 웹페이지를 가지고 있는 proxy server는 사용자에게 이전 웹페이지를 보여주게 되는 것이다. 그러면 어떻게 해결할 수 있을까?

# Conditional GET

조건부 GET이라는 것을 활용하면 되는데, 서버에 HTTP request를 보냈는데 요청한 데이터가 최신버전인지 아닌지를 확인만 해주는 request message라고 보면 된다. 따라서 proxy server는 수시로 서버에게 conditional GET을 보내게 되고 consistency를 유지할 수 있도록 해주는 것이다.