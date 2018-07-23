---
layout: post
category: flutter
title: "[Flutter]Tic Tac Toe를 하면서 배운 것들"
---

[MTechViral](https://www.youtube.com/watch?v=u1KD6Kz0PIQ)님의 강의를 보면서 배웠던 위젯이나 함수들을 정리한 내용입니다.

# Navigator.canPop

> Whether the navigator that most tightly encloses the given context can be popped. 

현재 context를 감싸고 있는 가장 가까운 navigator가 pop될 수 있는지를 알려주는 메소드이다.

<br>

# List.generate

> Generates a list of values.
>
> Creates a list with `length` positions and fills it with values created by calling `generator` for each index in the range `0` .. `length - 1` in increasing order.

길이와 generator 메소드가 주어지는데, 아래와 같이 사용할 수 있다.

```dart
List<int>.generate(3, (index) => index*index); // 0*0, 1*1, 2*2이므로 [0, 1, 4]
```

<br>

# SliverGridDelegateWithFixedCrossAxisCount

GridView.builder를 사용해서 그리드 레이아웃을 만들 때 `gridDelegate` 속성 값으로 사용하는 위젯이며 타일의 레이아웃을 제어하는 역할을 한다.

> Creates a delegate that makes grid layouts with a fixed number of tiles in the cross axis. 
>
> All of the arguments must not be null. The `mainAxisSpacing` and `crossAxisSpacing` arguments must not be negative. The `crossAxisCount` and `childAspectRatio` arguments must be greater than zero. 

교차축 타일의 레이아웃을 지정하며 위에서 명시하는 속성들 모두 값이 있어야 한다.

<br>

# VoidCallback

> Signature of callbacks that have no arguments and return no data. 

매개변수와 리턴값이 없은 콜백함수의 모양을 typedef로 정의해놓은 것이다.

<br>

# RaisedButton의 color, disabledColor

* color : 버튼이 활성화 되기 전의 색깔을 지정한다.
* disabledColor : 버튼이 활성화 되고 나서의 색깔을 지정한다.