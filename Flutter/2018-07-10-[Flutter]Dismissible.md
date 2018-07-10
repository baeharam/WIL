---
layout: post
category: flutter
title: "[Flutter]Dismissible 이용해서 아이템 제거하기"
---

앱을 이용하다 보면 여러개의 목록이 나열되어있을 때 단순 드래그를 통해 각 아이템들을 없앨 때가 있는데 그 때 사용하는 것이 [Dismissible](https://docs.flutter.io/flutter/widgets/Dismissible-class.html) 위젯이다. 제거할 수 있는 아이템들을 이 위젯으로 감싸면 그 기능을 수행할 수 있다. 먼저 아이템을 넉넉하게 만들자.

```dart
final items = List.generate(1000, (i) => "Item $i");
```

이제 리스트를 만들어서 Dismissible 위젯으로 감싸도록 하자.

```dart
// Scaffold의 body 안쪽
body: ListView.builder(
	itemCount: items.length;
    itemBuilder: (BuildContext context, int index) {
        final item = items[index];
        return Dismissible(
            key: Key(item),
        	backgroundColor: Container(color: Colors.greenAccent),
            child: ListTime(title: Text(item)),
            onDismissed: (direction) => items.removeAt(index)
        )
    }
)
```

여기서 key값을 가지는 것을 볼수 있는데 list item에 이 위젯을 사용할 경우 강제되는 것으로 다른 아이템들과 구분하기 위함이다. 또한 list item에 onDismissed를 사용할 때는 반드시 해당 아이템을 제거해주어야 한다. 레퍼런스에 다음과 같이 나와있다.

> If the Dismissible is a list item, it **must have a key** that distinguishes it from the other items and its [onDismissed](https://docs.flutter.io/flutter/widgets/Dismissible/onDismissed.html) callback **must remove the item** from the list. 

<img src="https://user-images.githubusercontent.com/35518072/42484447-0750b3d0-842e-11e8-898c-6964a4bb4062.gif" width="300px">