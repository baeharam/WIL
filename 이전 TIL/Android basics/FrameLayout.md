# FrameLayout

FrameLayout은 레이아웃에 포함된 뷰들을 같은 영역에 겹쳐서 배치할 때 사용하며 레이아웃 자체의 특별한 속성은 없다.

<img src="https://user-images.githubusercontent.com/35518072/41954923-d0bf5214-7a18-11e8-9175-3364c132bb9b.JPG" height="400px">

위 그림은 크기를 다르게 TextView를 구성한 것인데 코드 순서대로 가장 마지막에 있는 뷰가 제일 위에 나온다. FrameLayout을 사용하는 목적은 여러개의 뷰를 겹치게 한 다음 한 순간에 하나의 뷰만 보이기 위함이며 visibility 속성으로 구현할 수 있다.

# TabHost

안드로이드 UI에서 가장 많이 사용되는 것이 탭 화면인데 이걸 편하게 제공해주는 것이 TabHost 클래스이다. 구조는 3개의 부분으로 나눌 수 있다.

* TabHost : 탭 전체 영역을 지칭
* TabWidget : 탭 버튼이 들어갈 영역을 지칭
* FrameLayout : 탭 버튼 클릭 시 나올 화면 영역을 지칭

<img src="https://user-images.githubusercontent.com/35518072/41955295-6b24704a-7a1a-11e8-8121-4745683903ff.JPG" height="400px">

위쪽의 3가지 아이콘이 있는 부분이 TabWidget이며 "Tab 1"이라고 나온 본문 영역이 FrameLayout이고 이 전체를 묶어서 TabHost라고 지칭하는 것이다. 이렇게 레이아웃을 만들기 위해선 TabWidget과 FrameLayout이 수직으로 배치되어있기 때문에 LinearLayout을 사용해야 한다.

여기서 중요한 것은 TabWidget과 FrameLayout의 id값을 반드시 지정해야 한다는 것인데, 그것도 TabWidget은 `@android:id/tabs`로 FrameLayout은 `@android:id/tabcontent`로 지정해주어야 한다.

# TabHost를 위한 코드

탭의 버튼과 버튼이 눌렸을 때의 본문을 표현하기 위해서 XML 뿐만 아니라 JAVA로도 작업을 해주어야 한다. 이 때 각 버튼을 indicator라고 하며 indicator에 따른 본문을 Tab content라고 한다. 또한, Indicator에 해당하는 Tab content를 하나로 묶어서 TabSpec이라고 한다. 이제 TabSpec을 통해서 버튼과 버튼에 해당하는 본문을 보여주는 기능을 추가해보자.

```java
// TabHost 객체 생성하고 세팅
TabHost host = (TabHost)findViewById(R.id.host);
host.setup();

// TabHost.TabSpec 객체 생성하여 식별자 문자열 전달
TabHost.TabSpec spec = host newTabSpec("tab1");
// 버튼 구성
spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.tab_icon1, null));
// 본문 구성
spec.setContent(R.id.tab_content1);
// TabHost 객체에 추가
host.addTab(spec);
```

버튼을 구성할 때 코드가 좀 복잡하기 때문에 각각의 메소드를 살펴볼 필요가 있다.

```java
// Specify a label and icon as the tab indicator
public TabHost.TabSpec setIndicator (CharSequence label, Drawable icon)
```

[setIndicator](https://developer.android.com/reference/android/widget/TabHost.TabSpec#setIndicator)는 label과 icon을 인자로 받아 indicator로 설정하는 역할을 한다.

```java
/*
Return a drawable object associated with a particular resource ID and styled for the specified theme. Various types of objects will be returned depending on the underlying resource -- for example, a solid color, PNG image, scalable image, etc.
*/
Drawable getDrawable (Resources res, int id, Resources.Theme theme)
```

[getDrawable](https://developer.android.com/reference/android/support/v4/content/res/ResourcesCompat#getdrawable)은 id와 theme, resource 객체를 인자로 받아 여러가지 형태의 객체를 리턴시키는 역할을 한다.

# R.java

R.java 파일은 `res` 폴더 하위에 있는 리소스들을 코드 영역에서 식별하기 위해 사용된다. 각각의 리소스들은 int형 변수로 자동으로 저장되어 식별할 수 있다. 여기서 이걸 얘기하는 이유는 앱 뿐만 아니라 안드로이드 라이브러리에도 R.java가 있기 때문이다. 보통 id를 등록할 때 `@+id/name`과 같이 등록하지만 안드로이드 라이브러리에 등록된 것을 이용할 때는 `@android:id/tabs`와 같이 나타낸다. 이는 코드부분에서 `android.R.id.tabs`와 같이 쓸 수 있다. 

그럼 id값을 설정할 때 R.java를 써야하는지 android.R.java를 써야하는지 헷갈릴 수 있는데 이는 코드부분에서 findViewById로 id를 얻는 곳이 어디냐에 따라 다르다. 만약 개발자가 해당 id도 알아서 만든다면 R.java를 이용하면 되지만 해당 id를 얻는 곳이 라이브러리 내의 코드라면 android.R.java를 이용해야 한다. 이런 이유로 TabWidget과 FrameLayout의 id는 라이브러리 내의 코드를 이용하기 때문에 `android.R.id.tabs`와 같이 사용하는 것이다.
