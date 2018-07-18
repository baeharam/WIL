[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# DNS(Domain Name System) 기본

실제로 사람이 쓰기 위해 만든 것으로 원래는 프로세스간의 소켓 통신이다. 따라서 IP주소와 포트번호를 통해 통신을 하는데 웹서버의 경우 포트번호가 거의 80번으로 고정되어 있기 때문에, www.naver.com:80이라고 했을 때, 포트번호를 제외한 나머지 부분이 IP주소로 바뀌게 된다. 이걸 담당해주는 것이 DNS라는 시스템이다.

가장 기본적으로 DNS를 구현하는 방법은 호스트와 IP주소를 서버 한곳에 저장한다음 요청하게 하는 것인데 많은 문제점들이 있다. 먼저 해당 서버에 가까운 사람만 빠르게 이용할 수 있고, 사람이 몰릴 경우 굉장히 느려진다. 또한 그 서버가 마비될 경우, 전세계의 인터넷이 마비된다. (실제 ip주소를 안다면 상관없지만 드물기 때문에)

그래서 이러한 문제들 때문에, 계층화와 분산화를 시켜놓았다. 

![image](https://user-images.githubusercontent.com/35518072/42742046-a6c6e218-88f2-11e8-822a-a90b8d9a8986.png)

먼저 계층화부터 보자면 위 그림과 같이 생겼고 위에서부터 살펴보자.

* **Root 서버**

전세계에 12~13개 정도가 존재하며 같은 copy를 가지고 있다. 바로 밑에 있는 top-level domain들의 주소를 관리하고 있다. 

* **Top-level domain(TLD) 서버**

자기 하위에 있는 서버를 관리하는 애들로 com, org, edu 등이 있다.

* **Authoritative DNS 서버**

모든 기관은 그 기관에 속한 호스트들에 대한 IP 매핑을 시켜주기 위해 DNS서버를 가져야 한다. 예를 들어, 한동대학교에서 가장 유명한 호스트는 histnet.handong.edu인데 그 호스트에 대한 IP주소가 authoritative DNS 서버에 존재하는 것이다. 이외에도 i7.handong.edu라던가 seal.handong.edu라던가 모두 웹서버에 해당하는 컴퓨터 이름이라고 볼 수 있고 DNS 서버에 존재한다.

IP주소를 외우지 않고 DNS를 이용해 이름으로 접속할 수 있다는 장점 외에도, 분리의 관점에서 볼 때 IP주소가 아무리 바뀌어도 도메인 이름이 바뀌지 않는다는 장점이 있다. 예를 들어, 이제까지 몇십년동안 네이버의 도메인 이름은 바뀌지 않았지만 네이버에 해당하는 웹서버(호스트)의 IP는 많이 바뀌었다. 따라서, 사람들은 도메인 이름이 바뀌지 않는 한 같은 이름으로 접속할 수 있는 것이다.



# Local Name Server

모든 기관은 캐싱과 비슷한 역할을 하는 local name server가 존재해서 해당 기관에서 특정 도메인을 요청한다면 제일 먼저 local name server에 요청하게 된다. 하지만 local name server에 없는 도메인을 요청할 경우, DNS 계층으로 가서 찾아야 하는데 항상 root 서버부터 찾는 것이 아니라 아는 정보를 최대한 활용해서 갈 수 있는 최대 하위 계층부터 찾아가게 된다. 예를 들어, 서울대에서 한동대학교에 접속한다고 해보자, hisnet.handong.edu를 칠텐데 top-level domain인 edu를 알기 때문에 root 서버부터 찾지 않고 edu부터 찾아나가는 방식인 것이다.

<img src="https://user-images.githubusercontent.com/35518072/42742543-5e411686-88f6-11e8-8d7e-b53e112048da.png" width="400px">

위 그림은 cis.poly.edu가 gaia.cs.umass.edu에 접속하기 위한 과정을 보여주는데 먼저 local DNS 서버에게 물어본 뒤에 없으면 아예 top-level domain에 대한 데이터도 없으면 root서버부터 authoritative 서버까지 쭉 접근해서 마지막에 해당 ip 주소를 가져오게 된다. 그런데 역시 웹 캐싱에서와 같이 consistency(일관성) 문제가 발생할 수 있기 때문에 해결책이 필요하다. 그래서 DNS 서버에선 호스트와 IP주소 뿐만 아니라 TTL(Time To Live), 즉 살아있는 시간이라는 필드를 하나 더 추가해서 보통 하루동안만 보관하고 지워지게 된다.



# DNS records

실제 DNS 서버에 record(행)가 저장될 때 총 4개의 필드로 구성되는데 위에서 봤던 호스트, IP주소, TTL을 제외하고 type이라는 것이 존재한다. (name, value, type, TTL)

* **type =  A : 호스트 이름 + IP 주소인 경우**
* **type = NS : 도메인 이름 + 해당 도메인 호스트 이름**

먼저 호스트와 도메인의 차이점을 분명히 할 필요가 있는데 호스트는 요청한 웹페이지를 가지고 있는 웹서버(컴퓨터) 자체를 지칭하는 말이고 도메인은 여러개의 호스트가 속한 네트워크를 지칭하는 말이다. 물론 도메인도 계층이 분류되고 누군가는 관리를 해야 하기 때문에 도메인을 관리하는 호스트 또한 존재한다.

예를 들어, root 서버를 보면 top-level domain을 관장하는데 .com이란 도메인을 관리하는 해당 호스트가 존재할 것이다. (.com, 호스트이름, NS, TTL) 이런식으로 하나의 record가 있을텐데, .com 중에서 어떤 호스트인지를 모르기 때문에 .com을 관장하는 호스트에 접근을 해야 한다. 접근하기 위해선 ip주소가 필요하므로 root 서버는 (호스트이름, ip주소, A, TTL) record도 같이 가지고 있어야 한다. 쉽게 이해하기 위해선 전체 과정을 한번 봐야 한다. 아까와 동일하게 서울대 학생이 한동대의 hisnet.handong.edu에 접속한다고 해보자.

* 서울대의 local DNS 서버에 hisnet.handong.edu 호스트가 존재하는 지 확인 -> 없음
* .edu를 관장하는 호스트가 존재하는지 확인 -> 없음
* root 서버에 가서 .edu의 호스트와 그 호스트의 IP주소를 가져온다.
* .edu의 IP주소를 통해 handong.edu 호스트와 IP주소를 가져온다.
* handong.edu의 IP주소를 통해 authoritative DNS 서버에 가서 hisnet.handong.edu 호스트의 IP주소를 가져온 후 접속한다.



# 기관을 만든 예

예를 들어, 어떤 스타트업을 만들고 haram.com이라는 도메인을 생성했다고 하자. 그러면 위에서 말했다시피 authoritative DNS 서버를 가져야 하므로 www.haram.com에 접속했을시에 해당 호스트이 ip주소와 타입 A, TTL을 등록할 것이다. 하지만 추가적인 정보를 줘야 할 곳이 하나 더 있는데 바로 .com을 관장하는 호스트이다. 사람들로 하여금 www.haram.com에 접속할 수 있게 하기 위해선 www.haram.com을 관장하는 도메인의 호스트 ip주소를 알아야 한다. 그렇기 때문에 다음과 같이 .com을 관장하는 호스트에 record 값을 추가해주어야 한다.

* (haram.com, dns.haram.com, NS, TTL) : 도메인과 도메인의 호스트 이름
* (dns.haram.com, 7.7.7.7, A, TTL) : 도메인 호스트와 IP주소

이렇게 record를 추가해주어야 haram.com의 호스트 ip주소를 통해 haram.com의 authoritative DNS 서버에 접근하게 되고 www.haram.com 호스트를 찾게 되서 접속할 수 있는 것이다.



# DNS는 UDP를 사용한다.

처음 나도 DNS가 TCP를 사용할 줄 알았는데 의외로 UDP를 사용한다고 해서 놀랐다. 아직 배우진 않았지만 TCP를 사용하게 되면 connection을 하기 전에 하는 작업이 expensive하다고 한다. 또한 DNS를 하는 목적이 해당 호스트의 IP주소를 가져오기 위함이기 때문에 아주 간단한 작업이다. 웹브라우징을 할 때는 웹서버에서 웹페이지를 보여주기 위한 KB/MB 단위의 파일을 가져와야 하므로 신뢰성 있는 작업이 필요하지만 IP주소를 가져오는 것은 DNS서버에서 40B 크기 정도 되는 것만 가져오면 되므로 패킷이 유실될 가능성이 적고, 유실된다 하더라도 다시 보내면 그만인 것이다. 이런 이유로 DNS는 UDP를 사용한다.