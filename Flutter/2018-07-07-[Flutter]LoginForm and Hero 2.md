---
layout: post
title: "[Flutter]LoginForm과 Hero를 이용한 화면전환-2"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

**다음 글은 유튜브 [Kodeversitas](https://www.youtube.com/watch?v=efbB8-x9T2c) 님의 강의를 보고 정리하는 포스팅입니다.**



# loginButton

```dart
final loginButton = Padding(
			padding: EdgeInsets.symmetric(vertical: 16.0),
			child: Material(
				borderRadius: BorderRadius.circular(30.0),
				shadowColor: Colors.lightBlueAccent,
				elevation: 5.0,
				child: MaterialButton(
					minWidth: 200.0,
					height: 42.0,
					onPressed: () {
						// 나중에 구현..
					},
					color: Colors.blueAccent,
					child: Text('Log In', style: TextStyle(color: Colors.white),
                 )
			),
		),
);
```

* Padding 위젯으로 감싸서 수직으로 16.0의 패딩값을 준다.
* Material 위젯으로 감싸서 30.0만큼 border를 둥글게 만들고 그림자에 비치는 색깔을 설정한다.
* elevation을 5.0 주어서 child 위젯이 z축으로 떠보이게 만든다.
* child에 MaterialButton 위젯을 추가해서 기본적인 속성들을 설정하고, 여기서 중요한 것은 onPressed 메소드인데 hero animation과 연관된 메소드이기 때문에 나중에 다시 볼 것이다.

위와 같이 설정을 해주면 결과는...

<img src="https://user-images.githubusercontent.com/35518072/42404940-6b45bd8e-81c9-11e8-8ce0-52760736d207.png" width="300px">

<br>

# forgotLabel

이제 패스워드를 잊어버렸을 때 찾는 라벨을 만들어보자. 구현이 상당히 간단한데, 따로 기능조차 넣지 않는다. 여기선 [FlatButton](https://docs.flutter.io/flutter/material/FlatButton-class.html)을 사용할 것인데, text label을 위한 버튼이라고 나와있으며 elevation 값을 갖지 않는다.

```dart
final forgotLabel = FlatButton(
	child: Text('Forgot password?', style: TextStyle(color: Colors.black54)),
    onPressed(){}
)
```

`onPressed` 값을 비어있는 함수로라도 넣는 이유는 눌리는 효과를 주기 위함이다. 만약 아예 없거나 null 값이며 효과를 볼 수 없다. 결과를 보자.

<img src="https://user-images.githubusercontent.com/35518072/42404998-55c2beca-81ca-11e8-8ac6-9abd8bf790cc.png" width="300px">

이제 hero animation으로 전환될 홈 화면을 만들어보자.

<br>

# body

먼저 프로필 이미지, 환영메시지, 소개 순으로 보여줄 것이기 때문에 Column을 활용해야 한다. 배경색도 주고 싶으니 Container로 묶어서 decoration 속성값을 주도록 하자.

```dart
final body = Container(
    width: MediaQuery.of(context).size.width
	padding: EdgeInsets.all(28.0),
    decoration: BoxDecoration(
    	gradient: LinearGradient(
        	colors: [Colors.blue, Colors.blueAccent]
        )
    ),
    child: Column(
        children: <Widget>[
            // 채워나갈 것들..
        ]
    )
);
```

width로 [MediaQuery](https://docs.flutter.io/flutter/widgets/MediaQuery-class.html) 를 사용해서 현재 창의 너비값을 받아왔다. decoration에선 그라데이션을 주기 위해서 [LinearGradient](https://docs.flutter.io/flutter/painting/LinearGradient-class.html) 위젯을 활용했으며 필수값으로 색깔을 List형태로 주어야 하기 때문에 blue와 blueAccent 값을 주었다. 결과는 아래와 같다.

<img src="https://user-images.githubusercontent.com/35518072/42405146-215bdfec-81cd-11e8-9cc1-d8088a4ba8b8.png" width="300px">

<br>

# haram

홈 화면에는 사용자가 로그인에 성공했을 때 프로필을 띄워주는 이미지가 있어야 한다. 또한 이 이미지와 로그인 폼에서의 로고가 hero animation으로 연결될 것이다. home_page.dart 파일을 만들어서 HomePage 클래스를 생성한 뒤 build 함수에 작업하는 것으로 생각한다.

```dart
final haram = Hero(
	tag: 'hero',
    child: Padding(
    	padding: EdgeInsets.all(30.0),
        child: CircleAvatar(
        	radius:72.0,
            backgroundColor: Colors.transparent,
            backgroundImage: AssetImage('images/haram.png')
        )
    )
);
```

로고와 비슷하게 작업하지만 이미지 속성이 backgroundImage인 것이 다르다. 로고는 child 속성과 `Image.asset`을 사용했지만 여기선 [AssetImage](https://docs.flutter.io/flutter/painting/AssetImage-class.html) 클래스를 이용하는데 왜 그런지 확인해봤더니 CircleAvatar가 원을 나타내주지만 프로필 이미지가 사각형이기 때문에 배경 이미지로 활용해 원에 맞추는 것이었다. 로고 이미지는 원래 원 모양이기 때문에 그럴 필요가 없는 것이다. 확인해 보자.

<img src="https://user-images.githubusercontent.com/35518072/42405088-bdff97be-81cb-11e8-9cbd-5dd132fc7098.png" width="300px">

<br>

# welcom, introduce

단순히 텍스트를 띄워주는 것이므로 간단하게 구현된다. 패딩값을 주기위해 Padding으로만 감싸주도록 하자.

```dart
final welcome = Padding(
		padding: EdgeInsets.all(8.0),
		child: Text(
			'Welcome Haram',
			style: TextStyle(fontSize: 28.0, color: Colors.white),
	    ),
);
final introduce = Padding(
		padding: EdgeInsets.all(8.0),
		child: Text(
			'Flutter is fancy and funny!!',
			style: TextStyle(fontSize: 20.0, color: Colors.white),
	    ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42405232-c8a08640-81cd-11e8-9d7e-bd65913971e7.png" width="300px">

이제 다음 시간에 마지막으로 hero animation을 적용시켜보도록 하자.