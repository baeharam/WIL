---
layout: post
category: flutter
title: "[Flutter]GridView.count와 Stepper"
---

# GridView.count

그리드 레이아웃을 만들려고 할 때 보통 많이 사용하는 것이 [GridView.count](https://docs.flutter.io/flutter/widgets/GridView/GridView.count.html) 생성자이다. 여러가지 속성이 있는데 흔히 쓰는 것들만 본다면,

* crossAxisCount(필수) : 몇개의 열(column)을 가질 것인지 결정한다.
* scrollDirection : 그리드 레이아웃의 방향, 즉 스크롤이 되는 방향을 결정한다.
* primary : 충분한 컨텐츠가 없어도 스크롤할건지를 결정하는 것으로 true값이 기본이다.
* mainAxisSpacing : 스크롤 방향으로 정한 축 기준으로 그리드에 속하는 위젯끼리의 간격이다.
* crossAxisSpacing : 스크롤 방향으로 정한 축의 반대기준으로 위젯끼리 간격이다.
* childAspectRatio : 위젯들의 크기비율을 결정하는 것으로 이 값이 클수록 위젯이 작아진다.

6개의 위젯을 가진 간단한 그리드 레이아웃을 만들어보자.

```dart
GridView.count(
		    scrollDirection: Axis.vertical,
		    primary: false,
		    crossAxisCount: 2,
		    mainAxisSpacing: 10.0,
		    crossAxisSpacing: 20.0,
		    childAspectRatio: 2.0,
		    children: <Widget>[
		    	Container(
				    alignment: Alignment.center,
				    decoration: BoxDecoration(
					    color: Colors.red
				    ),
				    child: Text("그리드뷰1")
			    ),
			    Container(
				    alignment: Alignment.center,
				    color: Colors.blue,
				    child: Text("그리드뷰2")),
			    Center(child: Text("그리드뷰3")),
			    Center(child: Text("그리드뷰4")),
			    Center(child: Text("그리드뷰5")),
			    Center(child: Text("그리드뷰6")),
		    ],
	    )
```

<img src="https://user-images.githubusercontent.com/35518072/42731568-bad846c8-884a-11e8-9072-f1558ea207ec.png" width="300px">

<br>

# Stepper

[Stepper](https://docs.flutter.io/flutter/material/Stepper-class.html)는 여러개의 단계를 분류해서 진행상황을 보여주는 material design의 위젯이다.

* currentStep(필수) : 현재 어떤 단계에 있는지를 나타낸다.
* onStepCancel : cancel 버튼을 눌렀을 때에 대한 콜백이다.
* onStepContinue : continue 버튼을 눌렀을 때에 대한 콜백이다.
* onStepTapped : step이 눌렸을 때에 대한 콜백으로 index가 인자로 전달된다.
* steps : Step 객체로 구성되며 각 단계에 해당하는 List<Step\> 위젯이다.
* type : 단계를 수직으로 구성할 건지 수평으로 구성할 건지 결정한다.

하루의 삶을 Stepper로 나타내보자.

```dart
Stepper(
		    currentStep: 1,
		    onStepContinue: () {},
		    onStepTapped: (int) {},
		    onStepCancel: (){},
		    steps: <Step>[
		    	Step(
				    title: Text("아침을 먹자"),
				    content: Text("아침")
			    ),
			    Step(
				    title: Text("공부하자"),
				    content: Container(
					    alignment: Alignment.center,
					    color: Colors.orangeAccent,
					    child: Text("공부는 너무너무 재미있다.")
				    ),
				    isActive: true
			    ),
			    Step(
				    title: Text("점심을 먹자"),
				    content: Text("점심")
			    ),
			    Step(
				    title: Text("공부하자"),
				    content: Text("공부")
			    ),
			    Step(
				    title: Text("저녁을 먹자"),
				    content: Text("저녁")
			    )
		    ],
	    )
```

Step을 만들 때 title과 content는 필수인 것을 알아두자.

<img src="https://user-images.githubusercontent.com/35518072/42731639-69d92862-884c-11e8-8d25-a1973719be8e.png" width="300px">