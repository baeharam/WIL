---
layout: post
category: Android
title: "[안드로이드]Logcat을 이용한 로그 작성"
---

[안드로이드 Logcat 공식문서](https://developer.android.com/studio/debug/am-logcat)

> [Android Monitor](https://developer.android.com/tools/help/android-monitor.html)는 디버그 메시지를 표시하는 logcat Monitor를 포함합니다. logcat Monitor는 가비지 수집이 발생하는 시점과 같은 시스템 메시지뿐 아니라, [Log](https://developer.android.com/reference/android/util/Log.html) 클래스를 사용하여 앱에 추가할 수 있는 메시지도 표시합니다. 메시지를 실시간으로 표시하기도 하지만, 이전 메시지를 볼 수 있도록 기록을 유지하기도 합니다.

여기서 Android Monitor는 3.0에서 지워지고 Android Profiler로 대체되었다. 어쨌던 logcat Monitor를 활용하면 로그를 확인할 수 있는 것은 물론 작성할 수도 있다. 자세한 내용은 문서에 나와있으니 그걸 보도록 하고 여기선 Log 클래스를 이용해서 작성하는 법을 알아보자.

<br>

# logcat 메시지 형식

로그 메시지에는 연관된 태그와 우선순위가 있는데 태그는 로그 메시지가 발생하는 컴포넌트를 가리킨다고 보면 되고 우선순위는 로그가 어떤 종류인지와 자세한 정도를 나타낸다. 우선순위가 낮을수록 자세하며 아래 목록은 우선순위가 낮은 것부터 나열한 것이다.

* **V** - Verbose
* **D** - Debug
* **I** - Info
* **W** - Warning
* **E** - Error
* **A**- Assert

여기서 Assert를 제외하고는 모두 Log 클래스를 이용해서 로그 메시지를 작성할 수 있다.

<br>

# 로그 메시지 작성

우선순위에 따라서 호출하는 메소드가 달라지며 각 메소드의 이름은 우선순위를 소문자로 바꾼 것이다. 예를 들어, 디버그의 경우 `Logcat.d(태그, 메시지)` 로 작성할 수 있다.

태그를 생성할 때는 문서에서 다음과 같이 나와있으므로 상수로 작성하자.

> 첫 번째 매개변수에서 사용할 클래스에 `TAG` 상수를 선언하는 것을 규칙으로 삼는 것이 좋습니다. 

```java
private static final String TAG = "MyActivity";
...
Log.d(TAG, "로그 메시지");
```

![image](https://user-images.githubusercontent.com/35518072/43573575-03596aea-967d-11e8-8a2b-e2cc86855932.png)

상단의 Debug는 우선순위를 구분하는 필터이고 하단의 `D/MyActivity: 로그 메시지` 라고 되어있는 부분을 보면 D는 Debug를 의미하고 해당 태그와 로그 메시지가 출력된다는 것을 알 수 있다.

