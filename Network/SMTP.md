[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# SMTP(Simple Mail Transfer Protocol)

![image](https://user-images.githubusercontent.com/35518072/42729267-f086a546-880c-11e8-8bbd-45b84804e82a.png)

User agent는 아웃룩이나 썬더버드 같은 메일을 읽어들이는 프로그램들을 말하고 mail server는 사용하는 메일 계정 자체를 구동시켜주는 서버들을 얘기한다.Gmail이라던가 네이버 메일같은 것들이 에 속하며 실제 SMTP를 이용해 통신한다. 요즘 사용하는 모델은 아닌 것이, 단순히 HTTP를 이용해서 메일을 이용하기 때문이다. 실제로는 이게 전통적인 모습이다.

메일을 보낼 때 받는 사람의 컴퓨터가 24시간 켜져 있는 것이 아니기 때문에 확인할 때는 user agent로 확인을 하지만 받아주는 것은 mail server가 하는 것이다. Gmail을 사용한다면 해당 mail server가 받는 것이다. Mail server에는메시지들이 저장되는 message queue가 있으며 받을 메일이 저장되는 mailbox라는 것이 있다. Mailbox는 메일을 받았을 때 저장해두는 곳으로 사용자가 읽을 수 있는 곳이고 message queue가 보낼 메일들이 저장되는 큐인데, 받는 사람의 서버가 고장나있을 수도 있기 때문에 필요하다.

![image](https://user-images.githubusercontent.com/35518072/42729299-4f308dfe-880e-11e8-8132-e12a7c63b2bf.png)

앨리스가 밥한테 메일을 보낸다고 하면 다음과 같은 과정을 거친다.

* UA(User Agent)를 이용해서 메일을 작성한다.
* 작성한 메일을 mail server에 보내게 되고 message queue에 저장이 된다.
* 앨리스의 mail server는 밥의 mail server와 SMTP 통신을 하게 된다.
* 그리고 밥의 mail server에 있는 mailbox에 앨리스의 메일을 저장한다.
* 밥은 UA를 통해 메일 서버의 mailbox에 있는 앨리스의 메일을 확인한다.

실제로는 밥의 mail server까지만 SMTP를 사용하게 되며 밥의 mail server에서 UA와의 통신은 POP(Post Office Protocol)이나 IMAP(Internet Mail Access Protocol) 등을 사용한다.  하지만 요즘은 UA를 안쓰는 추세이기 때문에 HTTP를 사용한다고 보면 된다.

여기서 앨리의 mail server를 클라이언트, 밥의 mail server를 서버라고 하는데 그 이유는 앨리스의 mail server가 밥의 mail server에게 메일을 밀어넣기 때문이다. 두개의 mail server는 클라이언트의 명령을 통해 상호작용을 하며 HELO, MAIL FROM, RCPT 등이 있다.