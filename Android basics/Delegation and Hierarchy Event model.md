# 델리게이션 이벤트 모델

뷰에서 발생하는 이벤트를 처리하기 위한 모델로 처리 방식은 이벤트가 발생했을 때 이벤트 리스너가 이벤트 핸들러와 연결시켜서 처리한다.

* **이벤트 소스** : 이벤트가 발생한 뷰 객체
* **이벤트 핸들러** : 이벤트 처리내용을 가지는 객체
* **리스너** : 이벤트 소스와 이벤트 핸들러를 연결하는 작업

이제까지 계속 공부를 하면서 쭉 봐왔던 "setOnXXXListener"와 같은 구문들은 이벤트 소스와 이벤트 핸들러를 리스너로 연결하는 부분이다.

```java
vibrateCheckView.setOnCheckedChangeListener(new MyEventHandler());
```

vibrateCheckView에 해당하는 뷰에서 이벤트가 발생했을 때 처리하는 부분을 MyEventHandler 객체에 맡겼다.

```java
class MyEventHandler implements CompoundButton.OnCheckedChangeListener{
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        // 이벤트 처리
    }
}
```

# 하이어라키 이벤트 모델

사용자의 직접적인 터치 이벤트를 처리하기 위한 모델로 터치 이벤트와 키 이벤트로 나뉜다.

## 터치 이벤트

액티비티에 콜백 함수를 재정의하는 것만으로 이벤트 처리가 가능하다.

```java
@Override
public boolean onTouchEvent(MotionEvent event){
    return super.onTouchEvent(event);
}
```

onTouchEvent 메서드가 호출되는 터치 이벤트는 3가지 타입이 있다.

* ACTION_DOWN : 화면에 터치된 순간의 이벤트
* ACTION_UP : 터치를 떼는 순간의 이벤트
* ACTION_MOVE : 터치한 후 이동한 순간의 이벤트

또한 터치 이벤트가 발생한 순간의 좌푯값을 얻을 수도 있다.

* getX(), getY() : 뷰 내의 좌푯값
* getRawX(), getRawY() : 화면 내의 좌푯값

## 키 이벤트

보통 안드로이드는 화면에 나오는 키보드인 소프트 키보드를 사용하는데 이러한 키보드에 대한 이벤트 처리는 불가능하고 홈/뒤로가기/오버뷰 버튼과 같은 하드웨어 키보드에 대한 이벤트 처리를 할 수 있다. 그러나 홈/오버뷰는 일반 앱에서 제어할 수 없기 때문에 보통 뒤로가기 버튼 처리가 대부분이다. 예를 들어, "정말 종료하시겠습니까?" 메시지가 대표적이다.

터치이벤트와 비슷하게 액티비티의 콜백 함수를 재정의하는 것만으로 이벤트 처리가 가능하다.

```java
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
    return super.onKeyDown(keyCode, event);
}
```

콜백함수는 3가지가 있다.

* onKeyDown : 키가 눌린 순간의 이벤트
* onKeyUp : 키를 떼는 순간의 이벤트
* onKeyLongPress : 키를 오래 누르는 순간의 이벤트

onKeyDown 말고도 뒤로가기 전용 처리 메서드가 있는데 onBackPressed()라는 메서드이다.