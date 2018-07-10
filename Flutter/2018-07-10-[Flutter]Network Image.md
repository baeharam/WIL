---
layout: post
category: flutter
title: "[Flutter]인터넷에서 이미지 가져오기"
---

플러터 쿡북에서는 3가지 방법을 소개한다.

# Image.network

```dart
Image.network(
  'http://img.insight.co.kr/static/2018/03/27/700/7jsk7z45q41t8x743dfh.jpg',
)
```

이건 그냥 기본적인 방법이고 자연스럽게 띄우고 싶은 경우가 있을 때는 FadeInImage 위젯을 사용하면 된다. 

<br>

# FadeInImage

```dart
FadeInImage.memoryNetwork(
  placeholder: kTransparentImage,
  image: 'http://img.insight.co.kr/static/2018/03/27/700/7jsk7z45q41t8x743dfh.jpg',
);
```

placeholder를 지정하여 로딩 중에 띄울 이미지를 선정하는 것으로 패키지를 사용할 수도 있꼬 사용지 지정 이미지를 로드할 수도 있다. 위 코드는 transparent_image 패키지를 사용하여 이미지를 페이드 인 시킨 것이다.

<br>

# CachedNetworkImage

```dart
CachedNetworkImage(
  imageUrl: 'http://img.insight.co.kr/static/2018/03/27/700/7jsk7z45q41t8x743dfh.jpg',
);
```

이미지를 오프라인에서 사용하고 하기 위해 이미지를 캐싱해야 할 때가 있을 수 있다. 그 때 사용하는 것이 CachedNetworkImage로 cached_network_image 패키지를 설치해야 한다. 기본효과는 FadeInImage에서 사용하는 효과와 비슷하게 페이드 인되면서 이미지가 나타났다.

<img src="https://user-images.githubusercontent.com/35518072/42482733-42de7ca2-8424-11e8-8d10-d391cc41dac3.gif" width="300px">

<br>

## 출처

* https://flutter.io/cookbook/images/network-image/
* https://flutter.io/cookbook/images/fading-in-images/
* https://flutter.io/cookbook/images/cached-images/

