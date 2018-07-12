---
layout: post
category: flutter
title: "[Flutter]Matrix4 위젯으로 변형시키기"
---

[Matrix4](https://docs.flutter.io/flutter/vector_math/Matrix4-class.html)는 Transform을이용해서 child 위젯을 변형시킬 때 transform 속성 값으로 사용하는 위젯이다. 다양하게 변형시킬 수 있고 애니메이션을 줄 때 아주 많이 사용되기 때문에 반드시 알아야 한다.

# 회전

x, y, z축 기준으로 회전을 시킬 수 있는데 이게 어떤 방향인지 헷갈린다. 

<img src="https://i.stack.imgur.com/gbzQG.png" width="300px">

위 그림을 보면 회전축을 알 수 있는데 다른 부호는 참조하지 말자. Y축 같은 경우 아래쪽으로 갈수록 증가한다. 어쨌든 이 회전축을 기준으로 회전을 시킬 수 있다. 플러터 프로젝트를 만들면 생성되는 기본코드에 회전을 시켜보자.

```dart
class MyApp extends StatelessWidget {
	@override
  Widget build(BuildContext context) {
    return Transform(
	  transform: Matrix4.identity()..rotateX(0.5),
      child: Scaffold(
	    appBar: AppBar(
		    title: Text("Transform"),
		    centerTitle: true,
		    backgroundColor: Colors.deepOrangeAccent,
	    ),
      ),
    );
  }
}
```

X축 방향으로 0.5정도 회전시킨 것으로 Scaffold 자체를 회전시켰기 때문에 화면 아래부분이 안보이지만 앞으로 회전된 거라고 보면 된다.

<img src="https://user-images.githubusercontent.com/35518072/42612396-1f0c0ed0-85d6-11e8-877c-3f14e8404160.png" width="300px">

이제 Y축으로도 똑같은 비율만큼 회전을 시켜보면 아래와 같아진다.

<img src="https://user-images.githubusercontent.com/35518072/42612423-4295556e-85d6-11e8-829f-279677af01a6.png" width="300px">

Z축으로 돌릴 때가 살짝 헷갈렸는데 사용자를 바라보는 방향의 축이기 때문에 화면이 돌림판처럼 회전하는 형태가 될 것이다. 똑같은 비율로 적용해보자.

<img src="https://user-images.githubusercontent.com/35518072/42612462-7171386c-85d6-11e8-8697-1f65296dd345.png" width="300px">

<br>

# 원근법

원근법도 적용할 수 있는데 공부하다보니 잘 와닿지가 않았다. 회전을 시키지 않는 한 원근법이 적용됬는지 분간할 수가 없어서 X축 회전과 같이 적용해보자. setEntry() 메소드를 사용해야 한다.

```dart
class MyApp extends StatelessWidget {
	@override
  Widget build(BuildContext context) {
    return Transform(
	  transform: Matrix4.identity()..setEntry(3, 2, 0.001)..rotateX(0.3),
      child: Scaffold(
	    appBar: AppBar(
		    title: Text("Transform"),
		    centerTitle: true,
		    backgroundColor: Colors.deepOrangeAccent,
	    ),
      ),
    );
  }
}
```

<img src="https://user-images.githubusercontent.com/35518072/42612714-cc28f6c2-85d7-11e8-9498-966145e11f73.png" width="300px">

레퍼런스에 정확히 안 나와있어서 setEntry메소드가 어떤 방식으로 동작하는지는 알 수 없지만 4D Matrix의 행과 열의 값을 마지막 값으로 정해준다. 여기선 3행 2열의 값을 0.001로 설정하는 것이다.

<br>

# 좌표상 위치 선정

[Matrix4.translationValues](https://docs.flutter.io/flutter/vector_math/Matrix4/Matrix4.translationValues.html) 생성자는 x,y,z의 좌표를 받아 해당 위젯의 좌표상 위치를 이동시킬 수 있다. z축은 바라보는 방향에서 설정되는 것이기 때문에 사실상 확인은 불가능하다. x,y만 간단히 보자.

```dart
class MyApp extends StatelessWidget {
	@override
  Widget build(BuildContext context) {
    return Scaffold(
	    appBar: AppBar(
		    title: Text("Transform"),
		    centerTitle: true,
		    backgroundColor: Colors.deepOrangeAccent,
	    ),
	    body: Transform(
		  transform: Matrix4.translationValues(30.0, 50.0, 0.0),
	      child: Center(
		    child: Container(
			    width: 100.0,
			    height: 100.0
		    ),
	      ),
	    ),
    );
  }
}
```

간단한 컨네이너를 만들어서 Center로 감쌌지만 좌표상에서 이동을 시켰기 때문에 위치가 달라진 것을 알 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/42613613-d41eea12-85dc-11e8-92bd-3f0b2cce4a48.png" width="300px">



## 출처

* https://android.stackexchange.com/questions/91563/is-it-possible-to-repair-an-accelerometer
* https://medium.com/flutter-io/perspective-on-flutter-6f832f4d912e