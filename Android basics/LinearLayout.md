# LinearLayout

LinearLayout은 안드로이드에서 제공하는 기본 Layout 클래스 중에 하나로 상당히 많이 활용된다고 한다. 

> A layout that arranges other views either horizontally in a single column or vertically in a single row. 

정의를 보면 알 수 있듯이 뷰를 수평/수직으로 배치하는 Layout이다.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button3"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="@string/button" />

    <Button
        android:id="@+id/button4"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="wrap_content"
        android:text="Button" />
</LinearLayout>
```

안드로이드 스튜디오에서 가져온 XML 코드로 최상위 태그가 LinearLayout으로 묶인 것을 볼 수 있으며 여러가지 속성이 있다.

* `android:orientation` : 자식 뷰들이 수직/수평으로 배치될지 선택하는 속성으로 필수적이다.
* `android:layout_weight` : 가중치를 부여하는 것으로 이걸 이용해서 영역을 분할할 수 있다. 여기선 수평 배치이기 때문에 수평으로 가중치가 형성되며 가중치가 부여되는 방향(width/height)의 값이 "0dp"여야 정확히 의도되는 결과가 표시된다.

<img src="https://user-images.githubusercontent.com/35518072/41890754-e57ff7f8-794b-11e8-8f95-e6f8add56b81.JPG" height="400px">

다음과 같이 `layout_width`가 0dp인 경우에 각 뷰의 `layout_weight`을 1로 지정하면 영역의 1/2씩 차지하게 되어 반으로 분할된다.

위 코드에는 없지만 유용한 속성들이 있다.

* `android:weightSum` : 최대 가중치의 값을 설정하는 것으로 일부 영역을 공백으로 남길 때 사용.
* `android:layout_gravity` : 자신이 속한 부모 Layout 내 자신의 정렬 위치 값을 지정한다.
* `android:gravity` : 뷰의 내용이 정렬되는 위치 값을 지정한다.

