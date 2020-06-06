OAuth를 사용해서 구현된 서비스의 경우, 특정 API를 요청하기 위해서 액세스 토큰을 서버에게 주어야 한다. "나는 인증된 사용자" 라는 사실을 알려주는 것이다. 실제로 이 인증방식은 아래와 같은 프로세스로 진행된다.

```
+--------+                               +---------------+
|        |--(A)- Authorization Request ->|   Resource    |
|        |                               |     Owner     |
|        |<-(B)-- Authorization Grant ---|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(C)-- Authorization Grant -->| Authorization |
| Client |                               |     Server    |
|        |<-(D)----- Access Token -------|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(E)----- Access Token ------>|    Resource   |
|        |                               |     Server    |
|        |<-(F)--- Protected Resource ---|               |
+--------+                               +---------------+

```

[RFC6750, The OAuth 2.0 Authorization Framework: Bearer Token Usage](https://tools.ietf.org/html/rfc6750) 를 참고한 그림이다. Authorization Grant는 어플리케이션을 사용할 수 있도록 승인하는 사용자이며 여기선 구글이다. 승인을 받으면 그 승인을 가지고 인증서버에 요청하여 액세스 토큰을 받은 뒤에 그걸로 웹 서버에게 요청해서 리소스를 가져오는 방식이다.

웹 서버에 요청할 때는 아래와 같은 포맷으로 HTTP 헤더에 넣는다.

```
Authorization: Bearer <토큰>
```

여기서 `Bearer` 가 왜 붙는지가 궁금했는데, 위에 링크한 RFC 문서를 봐도 딱히 이유는 안나오는 것 같고, 그 뜻이 "소지자" 이기 때문에 붙었다고 생각한다. "나는 토큰의 소지자이니 서비스를 제공해달라" 라고 요청하는 느낌인 것이다.

## 참고

* https://stackoverflow.com/a/25843058/11789111