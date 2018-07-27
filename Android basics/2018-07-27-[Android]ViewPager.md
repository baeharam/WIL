---
layout: post
category: Android
title: "[안드로이드]ViewPager"
---

# 개요

![image](https://user-images.githubusercontent.com/35518072/43311802-614a3f14-91c6-11e8-9cff-57e51eeb4991.png)

ViewPager는 갤러리 앱과 같이 여러개의 뷰를 페이지 단위로 구분하여 슬라이딩 시키는 기능을 제공한다. 아래 예제를 보자.

<img src="https://user-images.githubusercontent.com/35518072/43311458-7f3a1a0e-91c5-11e8-8f56-efab966f1b02.gif" width="300px">

ViewPager에서 Fragment를 사용하기 때문에 이전 시간에 Fragment에 대해서 정리를 했던 것이다. 여기서 또한 Bundle이라는 것이 나오는데, 정리를 따로 하려다가 간단한 것 같아서 정리는 안 하겠다. HashMap의 형태를 띄는 클래스로 액티비티 간에 데이터를 전달할 쓰인다. key와 value로 이루어져있으며 key는 String이지만 value는 어떤 값도 전달할 수 있다. 일단 이 정도만 알아두고 ViewPager를 사용해보도록 하자.

<br>

# ViewPager 추가

RecyclerView나 Fragment에서나 다 메인이 되는 xml파일에 추가를 해주었다. 따라서 여기서도 `activity_main.xml` 파일에 추가를 해주기로 하자.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/view_pager">
    </android.support.v4.view.ViewPager>

</LinearLayout>
```

<br>

# 아이템 XML 파일 생성

ViewPager에서 각 화면에 나타날 아이템의 레이아웃 xml파일을 생성해야 한다. 위에서 미리 봤지만 ImageView 하나로 간단하게 구성됨을 알 수 있다. `item.xml` 파일로 아래 코드이다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:background="#000000">
    
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/item_image_view"
        android:scaleType="centerCrop"/>

</LinearLayout>
```

<br>

# Fragment의 서브클래스 생성

ViewPager에 나타날 각각의 화면은 Fragment로 구성되기 때문에 해당 서브클래스를 생성해주어야 한다. 이전시간에 해봤듯이 `onCreateView()` 메소드를 오버라이딩 해서 뷰 객체를 리턴해 주면 된다.

```java
@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.item, container, false);
     ImageView imageView = view.findViewById(R.id.item_image_view);
     imageView.setImageResource(getArguments().getInt("position"));

     return view;
}
```

여기선 `item.xml`을 뷰 객체로 inflate 시킨다음 그 뷰 객체를 통해서 표시되는 ImageView 객체를 찾았다. 마지막에 해당 이미지의 리소스를 세팅해주면 되는데 생소한 메소드가 보인다. 여기서 `getArguments()` 는 `setArguments()`로 지정한 Bundle 객체의 값을 얻는 메소드이다. 그 후에 얻은 Bundle 객체의 값으로 `getInt("position")` 을 호출하면 해당하는 값의 리소스가 이미지로 설정되는 것이다. 갑자기 무슨말인가 싶은데 이전에 메소드가 하나 더 있다.

```java
public class ItemFragment extends Fragment {

    // 재 사용을 통한 뷰페이저 최적화
    public static ItemFragment newInstance(int position) {
        
        Bundle args = new Bundle();
        args.putInt("position", position);
        
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
	// 바로 직전 메소드
}
```

ItemFragment의 인스턴스를 따로 생성해주는 static 메소드가 있으며 ViewPager를 최적화 해준다는 기능을 한다고 한다. 계속 알아보니 **static factory method 패턴**과 연관이 있는 것 같은데 factory method 패턴에 새로운 객체를 생성하지 않고 기존 객체를 리턴시키는 기법이다. 하지만 직접 해봤더니 에러가 발생했고 그 이유는 FragmentPagerAdapter가 항상 새로운 Fragment 객체를 원하기 때문이었다. 그래서 지금 static을 사용하는 이유는 아직 이해되지가 않는다. 나중에 이해되면 다시 적자.

<br>

# 어댑터 생성

Fragment 객체를 ViewPager에 띄워줄 어댑터를 생성해야 한다. FragmentPagerAdapter를 상속하는 클래스를 생성해야 하며 RecyclerView를 했을 때와 비슷하게 3개의 메소드를 오버라이딩 해야 한다. 코드가 간단하니 보도록 하자.

```java
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    final private int images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3};

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ItemFragment.newInstance(images[i]);
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
```

차례대로 보면 아래와 같다.

* 이미지 리소스를 `images` 배열에 담았다.
* FragmentManager 객체를 받아서 FragmentPagerAdapter에 전달한다.
* `newInstance()` 메소드를 사용해서 해당 이미지 리소스에 맞는 Fragment 객체를 생성한다.
* 총 Fragment 객체의 수를 알아낸다.

<br>

# ViewPager 어댑터 설정

나머지를 다 구현했으니 이제 ViewPager 객체를 생성한 뒤에 어댑터만 설정해주면 끝이난다.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
    }
}
```

ViewPager 객체는 XML에 만들었던 ID를 통해 가져오면 된다.

<br>

## 출처

* [하울의 코딩 채널](https://www.youtube.com/watch?v=3kL_Zv-s8pg&list=PLmdU__e_zPf_Pv5S3OT8lpTJa7pxwYNUL&index=6)
* [안드로이드 레퍼런스](https://developer.android.com/reference/android/support/v4/view/ViewPager)