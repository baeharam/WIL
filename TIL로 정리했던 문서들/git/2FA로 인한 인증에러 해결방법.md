## 해결방법

2FA(이중 인증)를 적용한 후에, 저장소를 클론하려고 했더니 다음과 같은 형태의 에러가 발생하였다.

```bash
Username for 'https://github.com': your_user_name
Password for 'https://your_user_name@github.com': 
remote: Invalid username or password.
fatal: Authentication failed for 'https://github.com/your_user_name/repo_name.git/'
```

Github 공식문서를 확인해보면 아래와 같이 안내한다.

> After 2FA is enabled you will need to enter a personal access token instead of a 2FA code and your GitHub password.

2FA를 활성화한 후에는, 개인 액세스토큰으로 인증을 해야 한다는 점이다. 따라서 아래와 같은 프로세스를 통해서 해결할 수 있다.

1. [개인 엑세스 토큰](https://github.com/settings/tokens) 을 발급받아서 복사한다.
2. 비밀번호 입력란에 개인 엑세스 토큰을 넣는다.

## 참고

* [Stackoverflow의 답변](https://stackoverflow.com/a/34919582)