---
layout: post
category: flutter
title: "[Flutter]파일에 쓰고 읽기"
---

전형적인 File I/O이다. 앱이 삭제되기 전까지는 지속해서 데이터를 저장할 수 있는 디렉토리에 읽고 쓴다. 이렇게 되면 앱이 꺼지고 다시 켜졌을 때에도 저장되있던 해당 데이터는 보존이 된다. 플러터에서 file i/o를 하기 위해선 먼저 이용할 디렉토리의 경로(path)를 알아야 하는데 안드로이드와 iOS의 디렉토리가 모두 다르다. 이를 독립적으로 하나의 패키지를 통해 할 수 있게 해주는데 [path_provider](https://pub.dartlang.org/packages/path_provider#-readme-tab-)라는 패키지이다. 메소드 하나로 플랫폼에 상관없이 임시/영구 디렉토리의 경로를 가져올 수 있다. 또한 file i/o를 위한 라이브러리 dart:io 도 당연히 import 시켜야 한다.

```dart
import 'dart:io';
import 'package:path_provider/path_provider.dart'
```

총 4개의 메소드를 정의해야 하는데 모두 리턴값이 Future<T\> 타입이다. 그러므로 dart:async도 import 해주어야 한다.

* **경로 가져오기**

```dart
// 경로니까 리턴값이 String, getter를 사용하며 Future 타입이므로 async 사용
Future<String> get _localPath async {
	final directory = await getApplicationDocumentsDirectory();
	return directory.path;
}
```

* **파일 가져오기**

```dart
Future<File> get _localFile async {
	final path = await _localPath;
	return File('$path/data.txt');
}
```

* **파일 쓰기**

```dart
Future<File> writeData(String message) async {
	try{
        // 파일 먼저 받아와야됨
		final file = await _localFile;
		return file.writeAsString(message);
	}catch(e) {
		return null;
	}
}
```

* **파일 읽기**

```dart
Future<String> readData() async {
	try{
		final file = await _localFile;
		String data = await file.readAsString();

		return data;
	}catch(e) {
		return "Nothing saved yet!";
	}
}
```

<br>

이제 위 메소드들을 사용해서 파일을 읽고 쓸 수 있게 된다. 딱히 시연해볼 필요는 없는 것 같고, 여기서 새로 알게 된 사실인데 setState()를 호출할 때 async를 쓸 수 없다는 것이다. 비동기적으로 받아올 데이터가 있으면 async로 먼저 받아오고 setState()를 호출해야 한다.

