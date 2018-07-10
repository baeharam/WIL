---
layout: post
category: flutter
title: "[Flutter]다른 타입의 아이템을 가진 List"
---

List를 만들 때 여러가지 타입의 위젯으로 표현하고 싶을 때가 있을수도 있다. 그 때는 특별한 위젯을 사용하는 것이 아니라 사용자가 추상클래스를 정의하는 형태로 구현할 수 있다. 먼저 2가지 타입이 있다고 하고 대표적인 추상 클래스를 만들자.

```dart
abstract class ListItem {}
```

이제 2개의 타입으로 정해진 클래스가 상속하고 자기만의 표시할 방식을 지정하면 된다.

```dart
class HeadingItem extends ListItem {
    final String heading;
    HeadingItem(this.heading);
}
class MessageItem extends ListItem {
    final String message, body;
    MessageItem(this.message, this.body);
}
```

이제 ListItem 위젯을 1000개 정도 생성한 다음 2가지 타입을 구분하면서 List를 표시하면 된다. 1000개 정도 생성하기 위해선 List.generate 생성자를 사용하자.

```dart
List.generate(
	1000,
    (index) => index % 6 == 0 ? HeadingItem("Heading $index") : MessageItem("Message $index", "Body $index") 
)
```

6개의 아이템을 주기로 타입을 바꿔가면서 List를 생성하였다. 이걸 표시할 때는 is를 써서 HeadingItem인지 MessageItem인지 구분해주면 된다. 대략적인 코드만 써보면 다음과 같다.

```dart
// ListView.builder의 itemBuilder 속성 안쪽
itemBuilder: (BuildContext context, int index) {
    final item = items[index]; // List.generate로 만들어낸 아이템들
    if(item is HeadingItem) { /* ... */ }
    else if(item is MessageItem) { /* ... */ }
}
```

<img src="https://user-images.githubusercontent.com/35518072/42483564-34f35d10-8429-11e8-859c-1ac3e9ed5d6c.gif" width="300px">