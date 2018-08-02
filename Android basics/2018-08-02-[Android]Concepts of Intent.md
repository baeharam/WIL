---
layout: post
category: Android
title: "[안드로이드]인텐트(Intent)"
---

[안드로이드 인텐트 공식문서](https://developer.android.com/guide/components/intents-filters?hl=ko)

> Intent는 일종의 메시지 객체이며, 이걸 사용해 다른 앱 컴포넌트로부터 작업을 요청할 수 있습니다.

인텐트는 액티비티와 액티비티, 혹은 앱과 앱 사이의 의사소통을 위한 것으로 보면 된다.

<br>

# 인텐트의 종류

인텐트에는 명시적/암시적 인텐트 2가지가 있으며 각각 실행하는 방법이 다르다.

* **명시적 인텐트** : 실제로 클래스 이름을 지정해서 컴포넌트를 시작하기 때문에 보통 앱 안에서 컴포넌트를 시작할 때 쓴다. 예를 들어, 백그라운드에서 파일을 다운로드하기 위해 서비스를 시작하는 것이 여기에 해당된다.
* **암시적 인텐트** : 특정 이름을 지정하진 않았지만 수행할 일반적인 작업을 선언하여 다른 앱의 구성요소가 이를 처리하도록 해준다. 예를 들면, 지도의 위치를 표시해주고자 하는 경우 그 기능을 가진 앱을 실행하여 표시할 수 있다.

명시적 인텐트는 시스템이 즉시 지정된 앱 컴포넌트를 시작하지만 암시적 인텐트는 조금의 과정을 거쳐야 한다. 이걸 이해하기 위해선 **인텐트 필터**에 대해 이해해야 한다. (공식문서로 대체...)

> 인텐트 필터란 앱의 매니페스트 파일에 들어 있는 표현으로, 해당 구성 요소가 수신하고자 하는 인텐트의 유형을 나타낸 것입니다. 예를 들어 액티비티에 대한 인텐트 필터를 선언하면 다른 여러 앱이 특정한 종류의 인텐트를 가지고 여러분의 액티비티를 직접 시작할 수 있습니다. 이와 마찬가지로, 액티비티에 대한 인텐트 필터를 전혀 선언하지 *않으면* 명시적 인텐트로만 시작할 수 있습니다. 

<img src="https://developer.android.com/images/components/intent-filters@2x.png?hl=ko">

위 그림과 같이 인텐트를 통해서 일반적인 작업을 알려줄 경우 시스템이 해당 인텐트와 맞는 인텐트 필터를 찾아 모든 앱을 검색한다. 찾으면 시작할 액티비티의 `onCreate()` 메소드를 호출하여 인텐트에 전달한다.

<br>

# 인텐트의 실행

Button 뷰를 생성했고 해당 버튼의 `onClick()` 메소드를 오버라이딩하여 인텐트를 실행한다고 해보자. 종류에 따라 다음과 같이 구현할 수 있다.

## 명시적 인텐트

```java
Intent intent = new Intent(MainActivity.this, CallActivity.class);
intent.putExtra("message", "Call Intent!");
startActivity(intent);
```

현재 액티비티가 `MainActivity`이고 시작할 다른 액티비티가 `CallActivity`인 경우 위와 같이 `.class`를 사용해서 명시적으로 지정한다. 또한 `putExtra()` 메소드를 통해 key-value 형태로 데이터를 전달할 수 있다.

## 암시적 인텐트

```java
Intent intent = new Intent();
intent.setAction(intent.ACTION_DIAL);
startActivity(intent);
```

다이얼 기능을 가진 일반적인 컴포넌트를 시작하는 것으로 기본 생성자를 통해 생성함을 볼 수 있다.