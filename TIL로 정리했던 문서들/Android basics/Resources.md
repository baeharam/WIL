# 리소스

앱의 리소스들은 모두 `res` 폴더 하위에 있어야 하며 폴더를 임의로 지정하거나 만들 수 없고 리소스별 폴더명이 지정되어 있다.

* drawable : 이미지와 관련된 xml
* layout : 화면 UI를 정의한 xml
* values : 문자열/색상/크기 등 여러가지 값
* menu : 액티비티의 메뉴를 구성하기 위한 xml
* xml : 특정 폴더가 지정되어 있지 않은 기타 xml
* anim : 애니메이션을 위한 xml
* raw : 바이트 단위로 직접 이용되는 이진 파일
* mipmap : 앱 아이콘 이미지

해당 리소스 파일을 추가하는 수간 R.java 파일에 int형 변수로 추가된다.

# 애니메이션 리소스

`anim`이라는 폴더에 들어가는 리소스로 루트 태그는 `<set>` 이며 하위에 다음 태그들이 있다.

* `scale` : 크기 변경 애니메이션
* `rotate` : 회전 애니메이션
* `alpha` : 투명도 조정 애니메이션
* `translate` : 이동 애니메이션

또한 애니메이션을 xml로 정의할 때 지정하는 공통 속성은 다음과 같다.

* duration : 지정한 애니메이션을 얼마 동안 지속하는지에 대한 설정
* startOffset : 애니메이션을 시작한 후 얼마 후부터 애니메이션 효과를 적용할 것인지에 대한 설정

둘 다 밀리세컨드 단위이다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <scale
        android:fromXScale="0.0"
        android:toXScale="1.0"
        android:fromYScale="0.0"
        android:toYScale="1.0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:startOffset="0"
        android:duration="2000"/>
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:startOffset="0"
        android:duration="2000"/>
</set>
```

해당 코드는 크기가 바뀌면서 투명도도 바뀌는 애니메이션에 대한 xml파일이다.

애니메이션을 적용하기 위해선 자바 코드도 지정을 해주어야 한다. [AnimationUtils](https://developer.android.com/reference/android/view/animation/AnimationUtils)라는 클래스를 사용하며 리스너 인터페이스의 3가지 메서드를 재정의함으로 구현한다.

```java
Animation anim = AnimationUtils.loadAnimation(this, R.anim.in);
anim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
      Animation anim=AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
            anim.setFillAfter(true);
            imageView.startAnimation(anim);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
});

imageView.startAnimation(anim);
```

마지막에 해당 애니메이션이 적용될 뷰를 가지고 `startAnimation` 메서드를 호출하면 적용된다.

# values 폴더와 스타일 리소스

문자열, 배열, 색상, 크기 등의 값이라고 표현되는 리소스는 `values` 폴더 하위에 위치하며 다른 리소스와는 다르게 태그 이름값으로 식별되어 사용한다.

* strings.xml : 문자열 리소스를 여러개 담는 파일. `<string>` 태그로 등록
* colors.xml : 색상 리소스를 여러개 담는 파일. `<color>` 태그로 등록
* styles.xml : 스타일을 여러개 담는 파일 `<style>` 태그로 등록
* arrays.xml : 배열을 여러개 담는 파일. `<string-array>`와 `<integer-array>`로 등록
* dimens.xml : 크기를 여러개 담는 파일. `<dimen>` 태그로 등록

여기서 봐야 할 것은 스타일 리소스인데, 여러 속성을 하나의 스타일로 묶어 필요한 곳에 적용하면 중복을 피할 수 있다는 장점이 있다.

```xml
<style name="myStyle">
	<item name="android:textColor">#FF0000FF</item>
	<item name="android:textSize">20dp</item>
    <item name="android:textStyle">bold</item>
</style>
```

다음과 같이 정의하면 적용하고 싶은 곳에 참조만 시켜주면 된다.

```xml
<TextView
	...
     style="@style/myStyle"
          />
```

스타일을 정의할 때는 다른 스타일을 상속받아 재정의 할 수도 있다.

```xml
<style name="mySubStyle" parent="myStyle">
	<item name="android:textStyle">italic</item>
</style>
```

# 테마 리소스

처음 보는 리소스 유형인데 스타일 리소스에 들어가는 하나의 형태로 위에서 본 스타일이 뷰에 한정된 것이라면 테마 리소스는 액티비티 혹은 앱 전체에 적용되는 스타일이라고 보면 된다. 잘 아는 테마라고 하는 것이다. 모듈을 만들 때 기본적으로 생성되는 파일은 다음과 같은 설정으로 되어있다.

```xml
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>
```

라이브러리에서 제공하는 테마를 상속받아 AppTheme라는 이름의 스타일로 정의한 것이다. 이렇게 테마를 정의하면 AndroidManifest.xml 파일에 설정해야 한다.

```xml
<application
        ...
        android:theme="@style/AppTheme"...
```

`<application>` 태그에 지정되어 있기 때문에 모든 액티비티의 테마가 AppTheme로 적용된다. 만약 특정 액티비티에만 적용하고 싶다면 `<activity>` 태그에만 적용해주면 된다.