---
layout: post
category: flutter
title: "[Flutter]구글 로그인을 활용한 Firebase 인증"
---

앱을 만들 때 중요한 것은 각 사용자에 맞는 설정을 제공해야 한다는 점이다. 이런 점에서 authentication이 필요한 경우가 있을 때가 있다. Firebase에선 여러가지 플랫폼(구글, 페이스북, 트위터, 깃헙 등)을 통해 인증을 지원하며 오늘은 플러터에서 구글 로그인으로 어떻게 인증을 할 수 있는지 알아보자.

<br>

# 구글 로그인 패키지

플러터에서 구글 로그인을 간편하게 하는 패키지를 제공하며 [google_sign_in](https://pub.dartlang.org/packages/google_sign_in#-readme-tab-)을 다운받아서 등록하면 된다. 먼저 해당 객체를 생성하도록 하자.

```dart
import 'package:google_sign_in/google_sign_in.dart';
final GoogleSignIn _googleSignIn = GoogleSignIn();
```

이제 사용자로 하여금 구글에 로그인 할 수 있도록 하는 메소드를 호출하자.

```dart
GoogleSignInAccount googleSignInAccount = await _googleSignIn.signIn();
```

리턴 타입이 Future<GoogleSignInAccount\> 이므로 await 키워들 써주어야 하며 당연히 감싸고 있는 메소드는 async가 붙어 있다. 이제 로그인 한 아이디와 패스워드 정보를 가진 객체를 가져오자.

```dart
GoogleSignInAuthentication googleSignInAuthentication
    = await googleSignInAccount.authentication;
```

<br>

# Firebase에 인증

해당 아이디와 패스워드를 가진 객체까지 가져왔으니 Firebase에 인증을 해야하므로 인증을 할 수 있는 객체를 먼저 생성해야 한다.

```dart
import 'package:firebase_auth/firebase_auth.dart';
final FirebaseAuth _auth = FirebaseAuth.instance;
```

이제 아이디와 패스워드 정보를 가진 객체를 이용해, 구글의 계정 정보로 인증을 해주는 메소드를 호출해주기만 하면 끝이다.

```dart
FirebaseUser user = await _auth.signInWithGoogle(
	idToken: googleSignInAuthentication.idToken,
	accessToken: googleSignInAuthentication.accessToken
);
```

<br>

# 결과

내 아이디로 로그인을 시도해본 결과 잘 나오는 것을 알 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/42688898-2cca0f64-86d9-11e8-9e0f-f81716034a82.png">