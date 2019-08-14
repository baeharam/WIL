# TextView에 string을 직접 입력하지 말자.

설명출처 : [hardcoded string “row three”, should use @string resource](https://stackoverflow.com/questions/8743349/hardcoded-string-row-three-should-use-string-resource)

TextView에 글자를 추가할 때 xml 파일에 직접 입력하는 것으로 배웠는데 계속 경고가 나와서 찾아봤다. 결론은 `res/values/strings.xml` 파일에 추가를 하고 참조하는 방식이 좋다는 것이었다.

공부를 하던 예제는 "돌아가기"라는 버튼이었다. 위의 방식대로 버튼을 추가하기 위해선 다음과 같이 하면된다.

우선 `strings.xml` 파일에 아래와 같이 입력하고

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="back">돌아가기</string>
</resources>
```

레이아웃이 적용되는 xml파일에 아래와 같이 입력한다.

```xml
<TextView android:text="@string/back" />
```