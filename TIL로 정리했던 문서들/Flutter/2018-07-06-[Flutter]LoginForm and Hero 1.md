---
layout: post
title: "[Flutter]LoginForm과 Hero를 이용한 화면전환-1"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

**다음 글은 유튜브 [Kodeversitas](https://www.youtube.com/watch?v=efbB8-x9T2c) 님의 강의를 보고 정리하는 포스팅입니다.**



# main.dart

먼저 메인 함수에서 따로 StatelessWidget을 상속하는 클래스를 선언하여 runApp에서 실행하는 방법으로 한다.

```dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
    @override
    Widget build(BuildContext context){
        return MaterialApp(
        	title: "haram",
            debugShowCheckedModeBanner: false,
            theme: ThemeData(
            	primarySwatch: Colors.lightBlue,
                fontFamily: 'Nunito'
            ),
            home: LoginPage() // 나중에 구현할 페이지
        )
    }
}
```

생소한 것들 몇가지가 있어서 한번 보고 넘어가자. [debugShowCheckedModeBanner](https://docs.flutter.io/flutter/material/MaterialApp/debugShowCheckedModeBanner.html) 속성은 원래 플러터 앱을 실행하게 되면 checked mode라는 디버그 모드로 실행되기 때문에 오른쪽 상단에 배너가 생기고 개발자에게 도움을 주기 위해 여러가지 진단을 같이 하므로 느려진다. 그러므로 별도의 진단이 필요하지 않다면 checked mode 값을 false로 설정하여 릴리지 모드로 실행하는 것이 더 빠르다.

`theme` 부분에 primarySwatch라는 속성이 있는데 [MaterialColor](https://docs.flutter.io/flutter/material/MaterialColor-class.html) 객체로 이 객체는 색깔 뿐만 아니라 해당 색깔의 shade에 따른 다른 색깔들을 지정한다. 이렇게 하나의 색깔에 연관된 색깔들을 swatch라고 부르니 primarySwatch가 뜻하는 것은 색깔/swatch라는 것을 알 수 있다.

<br>

# login_form.dart 

```dart
class LoginPage extends StatefulWidget {
    @override
    _LoginPageState createState() => _LoginPageState();
}

class LoginPage extends State<LoginPage> {
    
    @override
    Widget build(BuildContext context){
        // 이제 구현...
    }
}
```

일단 기본적인 형태는 이런데 `build` 함수 안에 위젯이 많이 들어가기 때문에 따로 분리하도록 하자. 여기서 배운 점은 위젯을 각각 변수로 구분하여 포함시켜서 가독성을 높인 것이었다.

<br>

# logo

여기서부턴 모두 `build` 함수 안에 들어가는 내용이다. 먼저 가장 위에 로고를 넣어보는데 hero animation을 로고에 넣을 거기 때문에 감싸도록 하자.

```dart
final logo = Hero(
	tag: 'hero',
    child: CircleAvatar(
    	backgroundColor: Colors.transparent,
        radius: 48.0,
        child: Image.asset('images/logo.png')
    )
);
```

[CircleAvatar](https://docs.flutter.io/flutter/material/CircleAvatar-class.html) 는 사용자를 나타내는 원이며 radius는 그 크기를 가리킨다. 결과를 보면...

<img src="https://user-images.githubusercontent.com/35518072/42379532-dcdc3860-8165-11e8-8ce5-dd178d947c34.png" width="300px">

깔끔하게 잘 나오는 것을 확인할 수 있다. 결과가 나오는 코드부분은 다음과 같다.

```dart
return Scaffold(
	backgroundColor: Colors.white,
    body: Center(
    	child: ListView(
        	shrinkWrap: true,
            padding: EdgeInsets.only(left:24.0, right:24.0),
            children: <Widget>[
                logo
            ]
        )
    )
);
```

ListView를 이용해서 로고, 로그인 폼, 마지막 패스워드를 잊어버렸을 경우의 메시지를 띄울 것이다. 여기서 ListView 안에 `shrinkWrap`이라는 속성은 스크롤의 범위를 컨텐츠에 맞출 것인지 아니면 최대 허용 크기까지 확장할 것인지 정하는 것으로 기본값은 false이다.  이걸 true값으로 해줘야 컨텐츠에 맞춰지기 때문에 위와 같이 나타날 수 있게 되며 만약 따로 정해주지 않거나 false로 하면 아래와 같이 나타난다.

<img src="https://user-images.githubusercontent.com/35518072/42404657-2498b038-81c6-11e8-85fd-1f1ea0951c37.png" width="300px">

<br>

# email, password

이메일과 패스워드를 입력하는 부분을 구현해보자.

```dart
final email = TextFormField(
	keyboardType: TextInputType.emailAddress,
    autofocus: false,
    initialValue: 'haram@gmail.com',
    decoration: InputDecoration(
    	hintText: 'Email',
        contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0),
        border: OutlineInputBorder(
        	borderRadius: BorderRadius.circular(32.0)
        )
    )
);
```

`keyboardType`은 사용자가 어떤 형태의 정보를 입력하는질 정해주는 속성이고  `decoration` 부분을 [InputDecoration](https://docs.flutter.io/flutter/material/InputDecoration-class.html) 객체로 꾸미는데 이 클래스는 텍스트 필드를 꾸미는 데 이용되는 클래스이다. 각 속성의 이름을 보면 직관적이기 때문에 쉽게 알 수 있다.

```dart
final password = TextFormField(
    autofocus: false,
    obscureText: true
    initialValue: 'haram@gmail.com',
    decoration: InputDecoration(
    	hintText: 'Password',
        contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0),
        border: OutlineInputBorder(
        	borderRadius: BorderRadius.circular(32.0)
        )
    )
);
```

password는 기본 폼 스타일이 같지만 `obscureText`라는 속성 값을 true로 설정해줘야 비밀번호가 보이지 않게 입력된다. `keyboardType`은 따로 설정할 필요없다. 마지막으로 로그인 폼 사이와 로고 사이에 여백을 주기 위해서 [SizedBox](https://docs.flutter.io/flutter/widgets/SizedBox-class.html) 클래스를 이용하는 특정 크기를 가진 box라고 생각하면 된다. 높이만 지정해주어 여백을 늘리는 기법으로 간편하게 사용될 수 있는 것 같다.

```dart
return Scaffold(
	backgroundColor: Colors.white,
    body: Center(
    	child: ListView(
        	shrinkWrap: true,
            padding: EdgeInsets.only(left:24.0, right:24.0),
            children: <Widget>[
                logo,
                SizedBox(height: 48.0),
                email,
                SizedBox(height: 48.0),
                password
            ]
        )
    )
);
```

<img src="https://user-images.githubusercontent.com/35518072/42404736-bbb2393a-81c6-11e8-856b-1f6dfbb6ab94.png" width="300px">

이제 로그인 버튼과 패스워드를 잊어버릴 때의 라벨은 다음시간에 추가해보자.