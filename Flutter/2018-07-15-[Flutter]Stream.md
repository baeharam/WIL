---
layout: post
title: "[Flutter]StreamController를 이용한 사진 받아오기"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# Photo 클래스

[jsonplaceholder](https://jsonplaceholder.typicode.com/photos)에서 사진의 url과 title을 받아와서 저장하려 하기 때문에 Photo 클래스를 만들어주어야 한다.

```dart
class Photo {
    final String title;
    final String url;
    
    Photo.fromJson(Map map) : title = map['title'], url = map['url'];
}
```

<br>

# StreamController

스트림을 사용하기 위해서, 컨트롤러를 생성해야 한다. 컨트롤러는 스트림을 생성해주며 리스너들로 하여금 어떤 기능을 할지 정해준다.

```dart
StreamController<Photo> controller;
List<Photo> list = []; // 사진을 받아오면 넣어 줄 List

@override
void initState() {
    super.initState();
    controller = StreamController.broadcast(); // 한 스트림에 많은 리스너들 등록
    controller.stream.listen((p) => setState(() => list.add(p)));
}
```

broadcast 타입으로 스트림을 생성한 뒤에 그 스트림에 "subscription(구독)"을 하게 하는 역할이 바로 listen이다. listen은 콜백함수를 매개변수로 받는데 여기선 p라는 이벤트가 발생했을 때 위젯트리를 다시 그리는 setState() 메소드를 호출하여 해당 이벤트를 list에 추가한다.

<br>

# 데이터 받아와서 가공하기

이제 json 데이터를 받아오도록 하자. 메소드 이름은 load이다.

```dart
load() async {
    String url = "https://jsonplaceholder.typicode.com/photos";
    var client = http.Client();
    var req = http.Request('get', Uri.parse(url));
    var res = await Client.send(req);
}
```

여기까지가 HTTP request를 통해서 json 데이터를 받아오는 코드이다. res가 StreamedResponse 객체를 리턴하기 때문에 이제 가공해주어야 한다.

```dart
res.stream
    .transform(utf8.decoder)
    .transform(json.decoder)
    .expand((e) => e)
    .map((map) => Photo.fromJson(map))
    .pipe(controller);
```

transform을 이용해서 utf8 코드를 해석하고 json 데이터를 해석해서 Stream 객체를 리턴한다. expand는 각 데이터들을 연속적인 데이터들로 collection 형태로 만들고 이 collection을 Photo 객체로 바꾼다음 컨트롤러에 집어넣으면 컨트롤러는 listen하고 있다가 list에 그걸 저장하게 된다.