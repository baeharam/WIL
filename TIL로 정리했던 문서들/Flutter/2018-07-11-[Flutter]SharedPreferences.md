---
layout: post
title: "[Flutter]SharedPreferences"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

SharedPreferences는 파일에 읽고 쓰는 것과는 다르게 작은 설정 값들을 key-value 형태로 저장할 때 사용한다. shared_preferences 패키지를 설치해야 하며 이 패키지가 안드로이드/iOS의 해당 shared preferences를 가져온다. 사용방법은 굉장히 간단한데 먼저 해당 인스턴스를 얻고 읽거나 쓰는 형태이다. 인스턴스를 얻을 때 리턴 타입이 Future이기 때문에 async를 써주는 것만 주의하자.

<br>

# 데이터 저장하기

```dart
// String을 받아서 저장하는 경우
void _saveData(String message) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString("data", message);
}
```

<br>

# 데이터 획득하기

```dart
String _loadData() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    return preferences.getString("data");
}
```

