---
layout: post
category: Android
title: "[안드로이드]Fragment"
---

>`Fragment`는 동작 또는 `Activity` 내에서 사용자 인터페이스의 일부를 나타냅니다. 여러 개의 프래그먼트를 하나의 액티비티에 조합하여 창이 여러 개인 UI를 구축할 수 있으며, 하나의 프래그먼트를 여러 액티비티에서 재사용할 수 있습니다. 

레퍼런스의 설명을 보면 알 수 있듯이 Fragment는 액티비티를 구성하는 하나의 모듈이라고 보면 된다. Fragment는 API 레벨 11에서 나왔으며 태블릿과 같은 큰 화면에서 보다 역동적이고 유연한 UI 디자인을 지원하기 위한 것이다. 한 액티비티에서 표시할게 많아질 경우 여러개의 레이아웃으로 쪼갠다 하더라도 어려움이 있었기 때문에 나온 것이 그 배경이었다.

<img src="https://developer.android.com/images/fundamentals/fragments.png">

예를 들어 뉴스 어플의 UI를 디자인한다고 해보자. 어플리케이션의 UI는 왼쪽에 기사 목록을, 오른쪽에 기사 내용을 표시한다. 태블릿 같은 경우는 화면이 크기 때문에 하나의 액티비티로 여러 UI를 구성하기 위해선 원래라면 복잡한 과정을 거쳐야 하지만 Fragment를 사용하면 2개의 Fragment로 나눈 다음 각자의 역할을 수행하게 할 수 있다. 반면에 오른쪽의 일반 스마트폰이라면 액티비티를 2개가 각각의 Fragment를 가지게 된다.

<br>

# Fragment UI 구성하기

Fragment를 액티비티에 추가할 것이기 때문에 백그라운드 기능이 아니라면 자체적인 UI를 구현해야 한다. 먼저 액티비티를 구현하듯이 XML파일을 생성하자. `fragment_a.xml`파일에 아주 기본적인 UI를 구현했다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" 						android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#00ffcc">
    </LinearLayout>

</android.support.constraint.ConstraintLayout>
```

해당 fragment의 레이아웃 xml파일을 생성한 뒤에 뷰 객체로 만들어서 액티비티에 추가해야 하는 것이 목적인데 2가지 방법이 있다.

<br>

# 액티비티에 Fragment 추가하기 : XML

먼저 xml을 이용하는 방법을 살펴보자. XML 파일에 `<fragment>` 영역을 만들고 `name` 값으로 Fragment 클래스를 상속해서 `onCreateView()` 메소드를 오버라이딩할 클래스 이름을 전달해주어야 한다. 말로 설명하면 복잡하니 코드를 보자. `activity_main.xml` 파일이다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <fragment
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.haram.fragment.FragmentA"
        android:id="@+id/fragment_a">
    </fragment>

</android.support.constraint.ConstraintLayout>
```

`name`을 보면 com.haram.fragment.FragmentA라고 되어있는데 여기서 FragmentA가 Fragment를 상속하는 클래스이다. 이제 해당 클래스를 확인하자.

```java
public class FragmentA extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }
}
```

`onCreateView()` 메소드를 오버라이딩 한 후에 inflate를 이용하여 뷰 객체를 리턴하고 있다. 즉, 액티비티 레이아웃을 생성할 때 레이아웃에서 지정된 각 fragment를 인스턴스화하며 각각에 대해 `onCreateView()` 메소드를 호출하여 각 fragment의 레이아웃을 검사하는 것이다. 반환받은 뷰 객체를 `<fragment>` 부분에 곧바로 삽입해서 액티비티에 표시되게 된다.

<br>

# 액티비티에 Fragment 추가하기 : FragmentManager

이 코드는 간단하기 때문에 바로 이해할 수 있다. 액티비티의 `onCreate()` 메소드 부분에 아래 코드를 써주면 된다.

```java
@Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     FragmentManager fm = getSupportFragmentManager();
     FragmentTransaction fragmentTransaction = fm.beginTransaction();
     fragmentTransaction.add(R.id.main, new FragmentA());
     fragmentTransaction.commit();
}
```

1. FragmentManager에 대한 참조 획득
2. FragmentTransaction 시작
3. Fragment가 추가될 ViewGroup의 id값을 main으로 정한 경우에 위와 같이 써주면 해당 ViewGruop에 추가된다. 여기선 ConstraintLayout의 id값을 main으로 정했다. 또한 두번째 인자로 Fragment 뷰 객체를 생성해주는 객체를 넘겨주면 된다.
4. 마지막에 `commit()` 메소드로 액티비티에 추가하게 된다.

너무 간단하니 따로 결과화면은 첨부하지 않겠다.

<br>

## 출처

* [개발자를 위한 레시피](http://recipes4dev.tistory.com/58)
* [안드로이드 가이드](https://developer.android.com/guide/components/fragments)

