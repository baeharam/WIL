---
layout: post
title: "[Flutter]Animation에 관련된 위젯들"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

앱의 접근성을 좋게하고 비주얼적으로 효과를 주기 위해서 애니메이션은 필수다. 그래서 플러터에서 사용되는 애니메이션을 정리하고자 한다. 튜토리얼조차 양이 방대하기 때문에 먼저 가장 기초적으로 어떤 위젯(클래스)들이 있는지 살펴보자.

# 중요한 개념들

* Animation 객체는 현재 애니메이션이 시작했는지, 멈췄는지 아니면 정방향/역방향 이동인지 현재 상태를 알 수 있지만 화면에서 어떤 것들이 나타나는지는 모른다.
* AnimationController는 Animation을 관리한다.
* CurvedAnimation은 비선형으로 구성된 커브들을 관리한다.
* Tween은 애니메이션이 적용되는 객체의 데이터 범위를 보충하는데, 예를 들어 빨간색~파란색이나 0~255로 범위를 지정할 수 있다.
* 애니메이션의 상태변화를 감지하기 위해선 Listener나 StatusListener를 사용해야 한다.

<br>

# Animation\<T>

[Animation](https://docs.flutter.io/flutter/animation/Animation-class.html) 은 추상클래스이며 위에서 봤다시피 상태를 알 수 있다. 가장 많이 사용되는 타입은 double형이다. Animation 객체는 정해진 지속시간에서 2개의 값 사이의 숫자들을 발생시킨다. 결과는 직선, 커브, 혹은 어떤 단계적인 기능이나 따로 고안한 것이 될 수 있다. Animation 객체가 어떻게 제어되는지에 따라서 역방향으로도 적용될 수 있고, 심지어는 중간에 방향을 바꿀수도 있다.

물론 double형만 사용하는 것이 아니라 Color나 Size를 사용할 수도 있다. 또한 현재 상태를 안다고 했는데 `.value` 멤버에 접근하면 바로 볼 수 있다.

<br>

# CurvedAnimation

커브에 관련된 애니메이션으로 [Curves](https://docs.flutter.io/flutter/animation/Curves-class.html) 클래스가 다양한 커브들을 이미 정의해놓았기 때문에 다음과 같이 사용할 수 있다.

```dart
final CurvedAnimation curve =
    CurvedAnimation(parent: controller, curve: Curves.easeIn);
```

CurvedAnimation과 AnimationController모두 Animation\<double> 타입이므로 서로 상호적으로 교환할 수 있다. 커브를 적용할 객체를 감싸야 하는데 AnimationController 객체를 감싸서는 안된다.

<br>

# AnimationController

하드웨어가 새로운 프레임에 대한 준비가 될 때마다 새로운 값을 생성하는 특별한 객체이다. 기본적으로 0.0~1.0 범위가 지정되어있다. Animation<double\> 타입이므로 Animation 객체가 필요한 곳에서 사용될 수 있지만 애니메이션을 제어하기 위해선 추가적인 메소드가 필요하다. 예를 들어, 시작하기 위해선 `.forward()` 메소드를 사용해야 한다. 

값을 생성하는 것은 화면이 갱신될 때에 따라 다른데, 보통 1초에 60개의 숫자가 생성된다. 각 숫자가 생성되고 나면, Animation 객체들은 자신한테 속한 Listener 객체들을 호출한다.

AnimationController를 생성할 때 중요한 점은 `vsync` 속성인데 화면에 속하지 않는 애니메이션이 불필요한 자원을 사용하는 것을 막아준다. Stateful 객체에서 사용할 때는 클래스를 정의할 때 [SingleTickerProviderStateMixin](https://docs.flutter.io/flutter/widgets/SingleTickerProviderStateMixin-class.html)을 `with` 키워드와 함께 추가해주면 된다. 그러나 만약, AnimationController 객체를 여러개 사용한다면 [TickerProviderStateMixin](https://docs.flutter.io/flutter/widgets/TickerProviderStateMixin-class.html)을 사용해야 한다.

기본 범위가 0.0~1.0이지만 `fling()` 메소드나 CurvedAnimation으로 인해 범위를 초과할 수도 있으니 알고 있자.

<br>

# Tween

애니메이션을 AnimationController의 기본범위인 0.0~1.0이 아닌 다른 데이터 타입과 다른 범위로 지정하고 싶다면 [Tween](https://docs.flutter.io/flutter/animation/Tween-class.html)을 사용하면 된다. 예를 들어, 아래와 같이 지정할 수 있는 것이다.

```dart
final Tween doubleTween = Tween<double>(begin: -200.0, end: 0.0);
```

Tween은 stateless 객체이며 `begin`과 `end` 속성 값만 지닌다. 유일한 목적은 input 범위를 output 범위와 매핑시키는 것이며 보통 input 범위가 0.0~1.0이지만 필수적이진 않다.

Tween은 [Animatable<T\>](https://docs.flutter.io/flutter/animation/Animatable-class.html)을 상속하는데, Animatable은 Animation처럼 double을 output으로 내지 않아도 된다. 예를 들어, ColorTween은 2개의 색깔에 대한 단계를 나타낸다.

```dart
final Tween colorTween =
    ColorTween(begin: Colors.transparent, end: Colors.black54);
```

Tween 객체는 어떤 상태도 저장하지 않는 대신에 `evaluate(Animation<double\> animation)` 메소드를 제공한다. 이 메소드는 현재 애니메이션의 value값으로 매핑시키는 함수이다.

<br>

# Tween.animate

`.animate()` 메소드를 사용하면 Tween 객체를 이용해서 Animation 객체를 생성할 수 있다.

```dart
final AnimationController controller = AnimationController(
    duration: const Duration(milliseconds: 500), vsync: this);
Animation<int> alpha = IntTween(begin: 0, end: 255).animate(controller);
```

단, 인자로 AnimationController를 넘겨야 한다는 점은 알아두자.

