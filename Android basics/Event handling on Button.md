# 버튼 클릭시 이벤트 처리하기

[JIZARD](http://jizard.tistory.com/9) 님에게 모든 출처가 있습니다.

먼저 해당 버튼의 id를 `btn`이라고 하자. 총 4가지 방법이 있다.

# 1. OnClickListener를 따로 만들어 구현

```java
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Button button = (Button)findViewById(R.id.btn);
        // button.setOnClickListener(listner); 도 된다.
        findViewById(R.id.btn).setOnClickListener(listener);
    }

    // OnClickListener 객체 생성
    Button.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            // 이벤트 처리
        }
    };
}
```

# 2. OnClickListener를 익명으로 생성

```java
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 이벤트 처리
                    }
                }
        );
    }
}
```

# 3. OnClickListner를 implements하여 구현

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        // 이벤트 처리
    }
}
```

# 4. XML에서 onClick 속성 이용

이건 좀 특이한 방법인데 XML 코드인 onClick 속성에 함수를 추가하여 사용하며 버튼이 아니더라도 레이아웃을 포함한 다양한 뷰와 뷰그룹에 사용될 수 있다.

```xml
<Button
        android:id="@+id/btn1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="btn_click"/>
```

위와 같이 onClick 속성 값을 함수이름으로 정해주면 java의 함수와 연결된다.

```java
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btn_click(View v){
        // 이벤트 처리
    }
}
```
