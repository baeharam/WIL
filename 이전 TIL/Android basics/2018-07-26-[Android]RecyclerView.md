---
layout: post
category: Android
title: "[안드로이드]RecyclerView"
---

익숙해지기 위해 정리하는 것으로 아직 어떤 방식으로 작동하는지 완벽하게 이해하진 못했다. RecyclerView는 여러가지 목록을 나타내는 뷰를 정의하기 위해 사용했던 ListView의 발전된 형태이다. ListView를 사용하면 뷰홀더 패턴을 따로 사용해서 목록을 구현해줘야 표시해야 할 수많은 데이터가 있는 경우에 매끄러운 스크롤을 가능하게 할 수 있다.

하지만 RecyclerView를 사용하면 뷰홀더를 강제로 생성하기 때문에 뷰홀더 패턴이 적용되고 각 아이템들이 어떻게 배치되는지에 대한 레이아웃도 지정할 수 있다.

# 라이브러리 추가

RecyclerView를 사용하기 위해선 support 라이브러리이기 때문에 따로 추가해주어야 한다. File>Project Structure에 들어가면 dependency를 추가해주는 부분이 있다.

![image](https://user-images.githubusercontent.com/35518072/43259343-cd7d3274-9110-11e8-80f9-fe22a0ee9176.png)

오른쪽 상단의 + 버튼을 눌러서 recyclerview를 검색하면 해당 라이브러리가 나오니 추가해주면 된다.

<br>

# RecyclerView의 XML 구현

RecyclerView가 표시될 XML을 구현해야 한다. 여기선 간단히 `activity_main.xml`에 LinearLayout으로 구현했다. id를 추가해주어야 함을 잊지말자.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recycler_view">
    </android.support.v7.widget.RecyclerView>

</LinearLayout>
```

<br>

# 아이템 XML 구현

RecyclerView에 표시될 아이템의 XML을 구현해야 한다. `image_recycle.xml`에 ConstraintLayout과 ImageView를 통해 간단하게 구현했다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageview"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:scaleType="centerCrop"
        android:src="@drawable/image1"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```

<br>

# MainActivity.java

RecyclerView에서 중요한 레이아웃과 어댑터를 설정해주는 부분이다.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter());
    }
}
```

`setLayoutManager`와 `setAdapter`를 통해 레이아웃과 어댑터를 설정하는데 레이아웃은 LinearLayout 말고도 GridLayout과 같은 여러가지 레이아웃이 있다. 어댑터는 따로 클래스를 만들어서 객체를 생성하였다.

<br>

# RecyclerViewAdapter.java

어댑터를 구현하는 부분이기 때문에 가장 중요하다. 먼저 어댑터를 구현하기 위해선 상속해야 할 클래스가 있다. 레퍼런스엔 아래와 같이 나와있다.

![image](https://user-images.githubusercontent.com/35518072/43260384-506109b0-9114-11e8-9fe3-5eb3ba4de83e.png)

즉, `RecyclerView.ViewHolder`를 상속하는 클래스를 정의해주든지, 아니면 `RecyclerView.ViewHolder`를 그대로 사용하든지 해야 하는 것이다.

아이템 XML을 구현할 때 이미지뷰를 사용했기 때문에 여러개의 아이템을 위해서 이미지 리소스를 참조하는 배열을 하나 구현했다.

```java
private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6};
```

이제 어댑터를 구현할 때 반드시 구현해야 할 3가지 메소드를 살펴보자.

## onCreateViewHolder

```java
@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

     View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_recycle, viewGroup, false);


     return new Cell(view);
}
```

뷰홀더를 생성하는 메소드로 먼저 아이템에 해당하는 뷰 객체를 생성한 뒤에 우리가 정의한 뷰홀더 클래스의 생성자로 넘겨주고 있다. 그럼 해당 클래스를 정의해보자.

```java
private static class Cell extends RecyclerView.ViewHolder {

     ImageView imageView;

     private Cell(View view) {
         super(view);
         imageView = view.findViewById(R.id.imageview);
     }
}
```

`ImageView` 객체 하나를 가지고 다. 또한 생성자의 매개변수로 받은 뷰 객체를 통해서 아이템 XML에 정의된 `ImageView` 객체의 ID에 접근하고 있다. 그 ID를 받아서 표시할 뷰인 `imageView`에 할당해주는 것이 사용자 정의 뷰홀더 클래스의 역할이다.

## onBindViewHolder

```java
@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
     ((Cell)viewHolder).imageView.setImageResource(images[i]);
}
```

매개변수로 받는 뷰홀더를 사용자 정의 뷰홀더로 캐스팅 시킨후에 현재 아이템 인덱스로 바인딩시켜주는 역할을 한다. 즉, 화면에 표시해주는 것이다.

## getItemCount

말 그대로 아이템의 개수를 리턴해주는 메소드로 위에서 정의한 `images` 배열의 length 값을 리턴해주면 배열에 추가할 때 따로 변경할 필요가 없다.

```java
@Override
public int getItemCount() {
     return images.length;
}
```

<br>

# 결과

<img src="https://user-images.githubusercontent.com/35518072/43260588-e644af0e-9114-11e8-8cc3-e4577462f75f.png" width="300px">

<br>

## 출처

* [하울의 코딩 채널](https://www.youtube.com/watch?v=wCnp7a-knSU&index=5&list=PLmdU__e_zPf_Pv5S3OT8lpTJa7pxwYNUL)
* [RecyclerView 레퍼런스](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#createviewholder)
* [안드로이드 RecyclerView 사용법 가이드](http://dreamaz.tistory.com/345)