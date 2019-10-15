---
layout: post
title: "[Flutter]위젯(Widgets)"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# 모든 컴포넌트는 위젯이다.

아직 Android 밖에 해보지 못해서 iOS가 어떻게 동작하는지는 모르지만 안드로이드에선 XML을 통해 레이아웃을 구성하고 기능을 자바로 구현하는게 보통이다. 하지만 Flutter에선 모든 컴포넌트가 위젯이며 모든 레이아웃과 기능은 dart로 구현한다. 따라서 따로 XML을 구분할 필요가 없으며 좀 더 가독성있고 명확하게 구현할 수 있다.

위젯은 안드로이드에서 뷰 또는 뷰그룹과 비슷하다고 할 수 있는데 뷰 그룹 안에 뷰를 여러개 넣을 수 있기 때문이다. (뷰그룹도 뷰니까) 플러터도 마찬가지로 위젯안에 위젯을 넣을 수 있는 컴포넌트들이 존재한다. Container, Material이나 Column 등이 Text, Button 같은 위젯들을 포함할 수 있다.

![image](https://user-images.githubusercontent.com/35518072/42202134-4ee39ca2-7ed5-11e8-96d4-d48ceb896ac2.png)

다음 사진은 기본적인 위젯 중 일부를 나타낸 것이며 [Widgets Catalog](https://flutter.io/widgets/) 를 확인하면 플러터에서 지원해주는 모든 위젯들을 확인할 수 있다.



# Stateless and Stateful

플러터에는 다양한 위젯의 하위 클래스가 존재하지만 대표적인 예가 stateless 위젯과 stateful 위젯이다. 먼저 2가지 용어를 이해할 필요가 있는데 간단하게 말해서 stateless란 상태가 변하지 않는 것이고 stateful이란 상태가 변하는 것이다. 플러터 홈페이지에서 아주 쉽게 설명해주고 있다.

* Stateless 위젯은 internal state가 없고 Icon, IconButton 그리고 Text와 같은 위젯들이 있다.
* Stateful 위젯은 동적이다. 사용자는 stateful 위젯과 상호작용 하거나 시간에 따라 변하는 경우(data feed에 의해 UI가 업데이트 되는 것과 같은)가 있다. 예시로는 Checkbox, Radio, Slider 등이 있다.

자세한 내용은 [여기](https://flutter.io/tutorials/interactive/#stateful-stateless)에서 확인하자.



# Stateless 위젯의 기본 구현

플러터를 실행하기 위해서 `main()` 함수에서 `runApp()` 메소드를 통해 실행해야 한다. 만국 공용어인 "Hello World"를 Text 위젯으로 보여주는 화면을 구현해보자.

```dart
void main(){
    runApp(
    	new Center(
        	new Text(
            	"Hello World",
                textDirection: TextDirection.ltr
            )
        )
    );
}
```

Center 위젯을 생성한 후 그 안에 Text 위젯을 생성한 예제로, Text 위젯을 화면 중앙에 배치시킨 것이다. Text 위젯은 필수속성으로 표시할 텍스트를 가져야 하고 플러터는 텍스트가 어떤 방향으로 표시되는지 모르기 때문에 textDirection 속성으로 표시하거나 Directionality 위젯으로 감싸야 한다.([참고](https://stackoverflow.com/a/49689947))

<img src="https://user-images.githubusercontent.com/35518072/42202759-8f298194-7ed7-11e8-9282-349c755db2ce.png" height="400px">

