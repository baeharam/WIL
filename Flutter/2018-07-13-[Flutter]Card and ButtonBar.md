---
layout: post
title: "[Flutter]Card와 ButtonBar"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# Card

[Card](https://docs.flutter.io/flutter/material/Card-class.html)는 이름 그대로 카드를 디자인해주며 모서리가 약간 둥글게 되어있다. 관련된 정보를 나타내주는 Material sheet이며 눈여겨볼 속성은 아래와 같다.

* **elevation** : 그림자를 생성할 수 있으며 다른 위젯의 그림자보다 여기 쓰는 것이 더 좋은것 같다.
* **shape** : 카드의 모양, 정확히는 border의 모양을 지정할 수 있으며 [ShapeBorder](https://docs.flutter.io/flutter/painting/ShapeBorder-class.html) 객체를 받는다. ShapeBorder 객체에는 BeveledRectangleBorder, BoxBorder, CircleBorder 등이 있으며 각자 모서리의 모양을 지정해준다.

<br>

# ButtonBar

바로 [ButtonBar](https://docs.flutter.io/flutter/material/ButtonBar-class.html)를 하는 이유는 예제를 쓸 때 같이 사용하기 위함이다. ButtonBar는 버튼을 수평으로 배치하는 위젯이며 보통 Dialog 위젯에 쓰인다.

* **mainAxisSize** : 저번에 말했듯이 여유 공간을 차지하는 크기를 지정하는데 여기선 수평 배치이므로 수평에서 여유 공간을 얼마나 차지하는지 결정한다.

<br>

# 예제

처음으로 gist를 이용해보고 싶다는 생각이 들어서 코드는 gist로 작성했다. 길기만 하지 보면 굉장히 단순하다. 전체적인 틀을 Card로 구성하고 안에 Column을 넣어서 행을 나눈 뒤에 두 번째 행에 ButtonBar를 넣은 형태이다. 레퍼런스의 예제를 참고했고, 약간 Dialog 같이 보이기도 한다.

<script src="https://gist.github.com/baeharam/1ba0b8b4bb3f237bdf999aa0a740f6a9.js"></script>

<img src="https://user-images.githubusercontent.com/35518072/42671472-18b96a6c-869b-11e8-9fce-ddb2a2da6362.png" width="300px">

Column에서 mainAxisSize를 min으로 한 이유는 이렇게 하지 않으면 AppBar를 제외한 모든 부분이 Card가 되어 버리기 때문이다. 또한 Card의 모양으로 [StadiumBorder](https://docs.flutter.io/flutter/painting/StadiumBorder-class.html) 라는 걸 써봤는데 그냥 그런것 같다 ㅋㅋㅋ