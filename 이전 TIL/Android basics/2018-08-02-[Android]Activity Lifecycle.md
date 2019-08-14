---
layout: post
category: Android
title: "[안드로이드]액티비티의 생명주기"
---

[안드로이드 액티비티 공식문서](https://developer.android.com/guide/components/activities?hl=ko)

앱이 실행될 때부터 사용자가 사용하는 시점에 따라 액티비티의 상태가 달라진다. 시작부터 종료될 때까지 액티비티의 생명주기가 어떻게 되는지 알아보자. 

<img src="https://developer.android.com/images/activity_lifecycle.png?hl=ko">

위 그림은 반드시 외워야 하며 이걸 통해서 액티비티가 어떻게 동작하는지 알 수 있다.

* `onCreate()` : 액티비티가 처음 생성되었을 때 호출되며, 뷰를 생성하거나 데이터를 바인딩하는데 이용되고 이전 액티비티의 상태를 포함한 객체가 전달된다.
* `onStart()` : 액티비티가 사용자에게 표시되기 직전에 호출된다.
* `onResume()` : 액티비티가 시작되고 사용자와 상호작용하기 직전에 호출된다.
* `onPause()` : 시스템이 다른 액티비티를 재개하기 직전에 호출된다. 이후 액티비티가 다시 앞으로 오면 `onResume()`이 호출되고 보이지 않게 되면 `onStop()`이 호출된다.
* `onStop()` : 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출된다. 이후 사용자와 다시 상호작용하면 `onRestart()`가 호출되고 사라지면 `onDestroy()`가 호출된다.
* `onDestroy()` : 액티비티가 소멸되기 전에 호출된다.
* `onRestart()` : 액티비티가 중단되었다가 다시 시작되기 전에 호출된다.