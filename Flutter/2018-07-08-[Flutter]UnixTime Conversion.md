---
layout: post
title: "[Flutter]유닉스 시간을 readable format으로 바꾸기"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

[유닉스 시간](https://namu.wiki/w/%EC%9C%A0%EB%8B%89%EC%8A%A4%20%EC%8B%9C%EA%B0%84)은 1970년 1월 1일 0:0:0인 UTC에서 몇초가 지났는지 표시하는 방법으로 사람이 읽을 수 있는 포맷으로 바꾸기 위해선 1단계를 거쳐야 하며 좀 더 읽기 쉽게 1단계를 더 거칠 수 있다.

# DateTime 클래스를 이용

Dart에서 지원하는 dart:core라이브러리의 클래스로 날짜와 시간을 나타내며 여기 있는 메소드 중에서  [fromMillisecondsSinceEpoch](https://api.dartlang.org/stable/1.24.3/dart-core/DateTime/DateTime.fromMillisecondsSinceEpoch.html)를 이용하면 유닉스 시간을 DateTime 포맷으로 읽을 수 있다. 

> The constructed [DateTime](https://api.dartlang.org/stable/1.24.3/dart-core/DateTime-class.html) represents 1970-01-01T00:00:00Z + `millisecondsSinceEpoch` ms in the given time zone (local or UTC). 

주의할 점은 유닉스 시간이 초 단위이므로 밀리초를 만들기 위해서 1000을 곱한 뒤에 인자값으로 전달해주어야 한다는 것이다.

```dart
void main() {
  int unix_date = 1531012021040; // 이미 1000이 곱해진 시간
  DateTime date = DateTime.fromMillisecondsSinceEpoch(unix_date);
  print(date);
}
```

결과는 2018-07-08 10:07:01 .040이 나온다.

<br>

# intl 패키지의 DateFormat 이용

위와 같은 결과 값을 다양한 포맷으로 변환시켜주는 패키지가 intl 이며 그 패키지의 [DateFormat](https://www.dartdocs.org/documentation/intl/0.15.1/intl/DateFormat-class.html) 클래스를 사용해서 DateTime 객체로 변환된 값을 넘겨주면 좀 더 깔끔하게 나온다.

```dart
import 'package:intl/intl.dart';

void main() {
    // ...
    print(DateFormat.yMMMMD("en_US").add_jm().format(date));
}
```

여러가지를 썼지만 레퍼런스르 참조하면 다양한 메소드가 있으며 결과는 July 8, 2018 10:07 AM 이런 식으로 나온다.