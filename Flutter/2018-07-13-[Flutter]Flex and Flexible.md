---
layout: post
category: flutter
title: "[Flutter]Flexible과 Flex"
---

# Flexible

Row, Column 혹은 Flex 위젯의 child를 제어하는 위젯으로 main axis를 기준으로 남은 공간들을 채울 수 있게 해주는 유연성(flexibility)을 제공한다. 그러나 Expanded 위젯과는 다른 점이, 남은 공간들을 채우라고 **강제하지는 않는다**는 것이다.

Flexible은 **반드시 Row, Column 또는 Flex의 하위 위젯**이어야 하고 Flexible로부터 감싸고 있는 Row, Column 도는 Flex까지의 경로는 반드시 StatelessWidget이나 StatefulWidget을 포함해야 한다. (RenderObjectWidget 같은 것들이 안된다는 말)

속성 중에 fit은 남은 공간을 채우는 방식에 관한 것이고 flex는 어느 정도 그 공간을 차지할 것인지에 관한 것이다.

<br>

# Flex

1차원 배열로 children을 나타내는 위젯으로 main axis 기준으로 나열한다. 그렇기 때문에 **direction이라는 속성으로 반드시 값을 정해주어야 한다.** 하지만 main axis를 미리 알고 있다면 Row나 Column을 쓰는 것이 낫다. 또한 스크롤이 되지 않기 때문에 스크롤을 허용하려면 ListView를 쓰도록 하자. 마지막으로, child가 1개라면 Flex,Row 또는 Column을 쓰는 대신에 Align이나 Center를 쓰자.

<br>

# 예제

설명만 하면 와닿지 않으니 시연을 해보도록 하자. Flex와 Flexible을 같이 써서 빨강/파랑/하양을 수직으로 배열하는 코드이다.

```dart
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(home:MyApp()));

class MyApp extends StatelessWidget {
	@override
  Widget build(BuildContext context) {
    return Scaffold(
	    appBar: AppBar(
		    title: Text("Transform"),
		    centerTitle: true,
		    backgroundColor: Colors.deepOrangeAccent,
	    ),
	    body: Flex(
		    direction: Axis.vertical,
		    children: <Widget>[
		    	Flexible(
				    flex: 1,
				    child: Container(
					    color: Colors.red,
				    ),
			    ),
			    Flexible(
				    flex: 1,
				    child: Container(
					    color: Colors.blue,
				    ),
			    ),
			    Flexible(
				    flex: 1,
				    child: Container(
					    color: Colors.white,
				    ),
			    )
		    ],
	    ),
    );
  }
}
```

<img src="https://user-images.githubusercontent.com/35518072/42667552-3fdec542-8687-11e8-971e-cb9c6a82a0c5.png" width="300px">

Flex에서 direction을 Axis.vertical로 지정했기 때문에 수직으로 배열되었고 Flexible에서 flex 값을 모두 1로 지정해주어서 동일한 비율을 차지하게 되었다. 따로 시연해볼 필요는 없지만 여기서 하얀 컨테이너의 flex 값만 2로 바꿔주면 좀 더 커지고 나머진 동일한 비율을 유지하며 작아진다.

<br>

## 출처

* https://docs.flutter.io/flutter/widgets/Flex-class.html
* https://docs.flutter.io/flutter/widgets/Flexible-class.html