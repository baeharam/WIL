# Android 앱의 구성 요소

Android 앱을 구성하는 필수 요소에는 4가지가 있으며 각각 다른 목적과 생명주기를 가진다.

* **Activity**

UI가 있는 단일 화면으로, 예를 들어 이메일 앱에선 새 이메일 목록을 표시하는 activity가 있고 이메일을 작성하는 activity가 존재하는 것이다. 여러개의 activity들이 같이 작동하여 UI를 형성하지만 독립적인 개념이다. 따라서 만약 이메일 앱에서 사용자가 사진을 찍어 보내려고 하면 카메라의 독립적인 activity를 실행시켜 작동하게 할 수 있는 것이다.

* **Service**

백그라운드에서 실행되는 컴포넌트로 UI를 제공하지는 않는다. 예를 들어, 사용자가 다른 앱에 있는 동안 백그라운드에서 음악을 재생하는 것이 이에 속한다. 

* **Broadcast receivers**

이벤트를 처리하는 컴포넌트로, 안드로이드의 Intent를 받아서 처리한다. Pub/Sub 형태로 바인딩되며, 특정 intent가 발생하면 이를 subscribe하는 broadcast receiver가 이를 받아서 처리한다. 예를 들어, 화면이 꺼졌다거나 배터리 잔량이 부족하다거나 사진을 캡처했다는 것을 알리는 broadcast가 있다. 상태표시줄 알림을 생성하여 사용자에게 broadcast 이벤트가 발생했다고 알릴 수 있다.

* **Contents provider**

앱의 데이터 집합을 관리하는 개념으로 파일 시스템이나 데이터베이스, 웹 등에 저장할 수 있는 모든 데이터를 관리한다. 다른 앱들은 contents provider가 허용하면 데이터를 읽거나 수정할 수 있다. 예를 들어, Android 시스템은 contents provider에게 연락처 정보를 관리하게 하는데 다른 앱들에게 이 정보에 대한 권한을 허용하면 연락처 정보를 읽거나 수정할 수 있는 것이다.

## 출처

* [조대협님 블로그](http://bcho.tistory.com/1038?category=584380)
* [안드로이드 공식문서](https://developer.android.com/guide/components/fundamentals)