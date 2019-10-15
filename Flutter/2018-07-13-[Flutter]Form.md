---
layout: post
title: "[Flutter]Form을 이용한 데이터 받아오기와 제약걸기"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

이제까지 TextField나 TextFormField를 사용할 때 입력한 텍스트를 받아오기 위해서 TextEditingController 위젯을 사용했지만 Form에 해당되는 FormState 타입의 key 값을 이용해서도 해당 데이터를 받아올 수 있다.

# GlobalKey 선언

[GlobalKey](https://docs.flutter.io/flutter/widgets/GlobalKey-class.html)는 모든 앱을 통틀어 유일한 key값으로 타입을 지정할 수 있다.

```dart
GlobalKey<FormState> formKey = GlobalKey<FormState>();
```

위와 같이 선언해주면 [FormState](https://docs.flutter.io/flutter/widgets/FormState-class.html) 타입의 key값이 지정된 것이며 이 key 값이 지정된 Form 위젯을 식별할 수 있게 된다.

<br>

# onSaved, validator 지정하기

TextFormField 위젯에는 위와 같은 속성들이 있는데 각각 FormState 객체의 save 메소드와 validate 메소드가 불릴 때 동작한다. onSaved는 어떠한 곳에 데이터를 저장할 때 쓰이고 validator는 해당 TextFormField에 입력에 대한 제약조건을 줄 때 쓰인다.

```dart
ListTile(
			leading: Text("아이디"),
			title: TextFormField(
			initialValue: "",
			onSaved: (value) => saveData(value),
			validator: (value) => value.isEmpty?"반드시 입력해야 합니다!":null,
		),
```

코드의 일부만 가져왔는데, 이해하기엔 충분하다. onSaved는 입력한 값인 value값을 받아서 saveData 메소드에 넘겨주고 그 메소드에서 데이터를 처리한다. validator 또한 입력한 값인 value를 받지만 해당 값이 비어있을 경우 "반드시 입력해야 합니다!"라는 메시지를 출력하고 비어있지 않을 경우 아무 것도 출력하지 않는다. 이게 이해하기 힘들었는데 경고메시지라는 것을 이제 알았다...참고로 validator는 다른 함수 시그니쳐가 존재하며 리턴값은 String이다.

<br>

# 데이터 가져와서 처리하기

이제 마지막으로 제출 버튼이 있다고 생각하고 데이터를 가져온다음 처리해보자. 위에서 GlobalKey로 formKey라는 변수를 지정했었기 때문에 위젯 트리에서 해당되는 Form 위젯의 State를 가져올 수 있다.

```dart
void Submit() {
    FormState form = formKey.currentState();
    if(form.validate()) {
        form.save(); // onSaved에 해당되는 것 실행
        form.reset(); // 입력 칸 비우기
    }
}
```

currentState() 메소드의 코드 주석을 보니 왜 GlobalKey를 사용해야 하는지 알것 같다.

> The [State] for the widget in the tree that currently has this global key.

지정된 GlobalKey를 가진 위젯의 State값을 가져오는 거였다.

<br>

# 예제

시연한 사진만 보도록 하자.

<img src="https://user-images.githubusercontent.com/35518072/42676568-a27248d8-86b3-11e8-95d1-764618d78cba.png" width="300px">

아이디만 입력하고 비밀번호를 입력하지 않은 상태에서 버튼을 눌렀더니 경고메시지가 떠있는 것을 볼 수 있다. 앞으로 컨트롤러 말고도 Form을 많이 활용하도록 하자.