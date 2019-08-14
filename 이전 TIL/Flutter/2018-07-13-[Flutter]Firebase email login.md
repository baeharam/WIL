---
layout: post
category: flutter
title: "[Flutter]계정생성을 통한 Firebase 인증"
---

# 계정 만들기

다른 플랫폼을 이용하지 않고서도 이메일과 비밀번호를 만드는 것으로 인증 과정을 거칠 수 있다. 메소드 하나만 사용하면 되기 때문에 굉장히 간편하다.

```dart
import 'package:firebase_auth/firebase_auth.dart';
final FirebaseAuth _auth = FirebaseAuth.instance;

FirebaseUser user = await _auth.createUserWithEmailAndPassword(
	email: "gkfkagkfka13@gamil.com",
    password: "test" // 물론 패스워드는 이렇게 하면 안됨...
);
```

<img src="https://user-images.githubusercontent.com/35518072/42689600-e4cf313c-86db-11e8-9b2d-4213cca4e436.png">

<br>

# 계정 인증하기

이 또한 메소드 하나만 쓰면 인증이 가능하며 살짝 에러처리만 해주자.

```dart
void _signInWithEmail() {
		_auth.signInWithEmailAndPassword(
			email: "gkfkagkfka3@gmail.com",
			password: "test12345"
		).catchError((error){
			print("뭔가 잘못됬습니다!");
		}).then((user){
			print("환영합니다!");
		});
}
```

> ...
> D/FirebaseApp( 7007): Notifying auth state listeners.
> D/FirebaseApp( 7007): Notified 0 auth state listeners.
> I/flutter ( 7007): 환영합니다!

정상적인 결과가 나오는 것을 확인할 수 있다! 만약 로그아웃을 하고 싶다면 `signOut()` 메소드를 쓰면 된다.