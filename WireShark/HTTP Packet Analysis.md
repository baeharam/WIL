# Ethernet 2

![image](https://user-images.githubusercontent.com/35518072/42095391-70d24ba0-7bed-11e8-8159-1dd1fc60d3cd.png)

* Src : 자신의 network card의 MAC address이다.
* Dst : 라우터의 MAC address이다.

MAC address에서 첫 3쌍의 16진수는 해당 device를 만든 사람/회사를 말한다고 한다.

# Internet Protocol

![image](https://user-images.githubusercontent.com/35518072/42095851-c2a7df34-7bee-11e8-83c5-af40fa69bc7c.png)

* Src : 자신의 ip 주소이다.
* Dst : 현재로서는 HTTP로 접속하고 있는 사이트의 ip 주소이다 (찾아보니 구글이었음)

# Transmission Control Protocol

![image](https://user-images.githubusercontent.com/35518072/42096055-4ef5d6c6-7bef-11e8-8425-005730f73245.png)

* Source Port : 해당 기기에서 랜덤으로 생성되는 포트번호로 Destination port에 전송하는 포트.
* Destination Port : 현재 접속하고 있는 포트

# HyperText Transfer Protocol

![image](https://user-images.githubusercontent.com/35518072/42096599-b5c0232e-7bf0-11e8-8d89-42a65a042ad5.png)

* GET 방식으로 요청
* User-Agent : 웹서버에게 보내는 정보로 리눅스에선 어떤 웹브라우저와 OS, 프로세서 아키텍쳐를 사용하는지가 보였다.
* Host : 어떤 웹서버에 요청했는지 나온다.