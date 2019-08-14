---
layout: post
category: Android
title: "[안드로이드]AsyncTask"
---

# 여분의 스레드를 사용하자.

> 앱이 사용자 상호작용에 응답하여 리소스를 많이 소모하는 작업을 수행하는 경우, 이 단일 스레드 모델은 애플리케이션을 제대로 구현하지 않으면 낮은 성능을 보일 수 있습니다. **특히, 모든 것이 UI 스레드에서 발생한다면, 네트워크 액세스나 데이터 베이스 쿼리 등의 긴 작업을 수행하면 전체 UI가 차단됩니다.** 스레드가 차단되면 드로잉 이벤트를 포함하여 모든 이벤트가 발송되지 않습니다. 사용자가 보기에는 애플리케이션이 중단된 것처럼 보입니다. 더 나쁜 경우, UI 스레드가 몇 초 이상 차단되어 있으면 (현재 약 5초) 사용자에게 악명 높은 "[애플리케이션이 응답하지 않습니다](http://developer.android.com/guide/practices/responsiveness.html)"(ANR) 대화상자가 표시됩니다. 그러면 사용자가 여러분의 애플리케이션을 종료할 수도 있고, 불만족한 경우 앱을 제거할 수도 있습니다. 

위 설명은 안드로이드 공식문서에서 스레드에 대해 설명할 때 말한 것을 발췌한 것이다. 안드로이드 어플리케이션을 실행하면 메인 액티비티가 메모리로 올라가 프로세스가 되고 메인 스레드가 자동으로 생성된다. 이 메인 스레드는 주요 컴포넌트가 실행되고 UI가 그려지기 때문에 UI 스레드라고도 불린다.

따라서, 위에서 하는 말은 UI 스레드에서 시간이 오래걸리는 인터넷 엑세스나 디비 쿼리 작업을 할 경우 ANR(응답없음)이 발생할 수 있으니 여분의 스레드를 사용해서 UI작업과 분리하라는 말이다. 이제 어떻게 사용하는지에 대해 알아볼텐데, 여기서 다룰 것은 가장 쉬운 AsyncTask 클래스이고 좀 더 다양하게 활용할 수 있는 것은 출처의 2번째 링크를 참고하자.

<br>

# 작동방식

<img src="https://content-static.upwork.com/blog/uploads/sites/3/2016/08/24161340/AsyncTask.png">

여러개의 메소드로 이루어져 있으며 각 메소드의 역할만 이해한다면 쉽게 사용할 수 있다.

* `onPreExecute()` : 작업을 시작하기 전에 로딩중과 같은 걸 보여주는 부분으로 UI 스레드에서 호출된다.
* `doInBackground()` : 백그라운드 스레드에서 실행되는 것으로 디비 쿼리나 긴 작업을 하기 위한 용도로 생성되는 스레드이다. 여기서 `publishProgress()`를 통해 진행 상황을 전달할 수 있다.
* `onProgressUpdate()` : UI 스레드에서 호출되며 `publishProgress()`로부터 받은 값을 이용해 진행상황을 표시한다.
* `onPostExecute()` : UI 스레드에서 호출되며 화면을 업데이트하기 위한 용도로 사용된다. `doInBackground()`로부터 데이터를 전달받는다.

<br>

# 구현

AsyncTask 클래스를 상속받는 클래스를 생성해야 하며 제네릭으로 3개의 wrapper class를 전달해주어야 한다.

1. 백그라운드에서 할 작업에 대한 타입
2. 진행상황을 보여주기 위한 타입
3. 작업이 끝나고 결과를 받기 위한 타입

```java
private class MyTask extends AsyncTask<Integer, Integer, Integer>{}
```

예를 들면, 위와 같이 정수에 대해 작업을 할 경우 Integer로 설정하면 된다. 나머지 메소드들은 오버라이딩만 해서 구현해주면 되니까 굳이 코드를 첨부하진 않겠다. 까먹으면 출처를 참고하자.





## 출처

* [프로세스 및 쓰레드](https://developer.android.com/guide/components/processes-and-threads)
* [안드로이드 백그라운드 잘 다루기](https://academy.realm.io/kr/posts/android-thread-looper-handler/)
* [upwork](https://www.upwork.com/hiring/mobile/why-you-should-use-asynctask-in-android-development/)