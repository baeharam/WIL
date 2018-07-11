---
layout: post
category: flutter
title: "[Flutter]RichText"
---

텍스트를 나타내는 또 다른 위젯으로 [RichText](https://docs.flutter.io/flutter/widgets/RichText-class.html)라는 위젯이 있다. 다양한 스타일을 가진 텍스트들을 나타낼 수 있으며 [TextSpan](https://docs.flutter.io/flutter/painting/TextSpan-class.html)을 하위트리로 사용해서 나타내며 1줄에 표시할 수도 있고 `\n`을 이용해서 여러줄에 표시할 수도 있다. 단, RichText에 하위에 있는 텍스트는 반드시 스타일이 지정되어 있어야 함을 주의하자. RichText의 목적은 다양한 스타일을 나타내는 것이기 때문에 스타일이 같다면 Text  위젯을 사용하는 것이 더 좋다고 한다. 간단하게 코드와 결과를 보자.

```dart
return Scaffold(
	    appBar: AppBar(
		    title: Text("RichText"),
		    centerTitle: true,
		    backgroundColor: Colors.lightGreen,
	    ),
	    body: Center(
	      child: new RichText(
			    text: new TextSpan(
				    text: 'Hello\n',
				    style: TextStyle(color: Colors.orangeAccent, fontSize: 40.0),
				    children: <TextSpan>[
					    new TextSpan(text: 'flutter\n', 
                                     style: TextStyle(
                                         fontSize: 40.0, color: Colors.blue)
                                    ),
					    new TextSpan(text: 'world!', 
                                     style: TextStyle(
                                         fontSize: 40.0, color: Colors.red)
                                    ),
				   ],
			  ),
	      ),
	 ),
);
```

body 부분에 Center에 넣었는데 RichText 바로 하위 TextSpan에선 style을 지정하고 있는 것을 볼 수 있지만 TextSpan의 children에선 style을 지정하지 않을 수도 있다. TextSpan이 계속 하위로 쭉 이어질 수 있기 때문에 이 위젯을 사용하는 것 같다.

<img src="https://user-images.githubusercontent.com/35518072/42548517-5d175d58-8502-11e8-9a9b-b7bae4a8b4dc.png" width="300px">