# RelativeLayout

특정 뷰를 기준으로 뷰의 위치를 지정하는 Layout으로 상대적인 위치를 지정하는 속성값들을 가진다.

## 상대위치 속성

* android:layout_above : 기준 뷰의 윗부분에 배치한다.
* android:layout_below : 기준 뷰의 아랫부분에 배치한다.
* android:layout_toLeftOf : 기준 뷰의 왼쪽에 배치한다.
* android:layout_toRightOf : 기준 뷰의 오른쪽에 배치한다.

예를 들면 `android:layout_toRightOf="@id/icon"`과 같이 쓸 수 있다. 뷰마다 특정 id값이 정해지기 때문에 뷰를 기준으로 잡기 위해선 id를 사용해야 하는 것이다.

## align 속성

상대위치를 지정할 경우 해당 뷰를 기준으로 정렬해야 할 때가 많다.

* android:layout_alignTop : 기준 뷰와 윗부분을 정렬
* android:layout_alignBottom :  기준 뷰와 아랫부분을 정렬
* android:layout_alignLeft : 기준 뷰와 왼쪽을 정렬
* android:layout_alignRight :  기준 뷰와 오른쪽을 정렬
* android:layout_alignBaseline : 기준 뷰와 텍스트 기준선을 정렬

aling 속성도 동일하게 id 값을 기준으로 `android:layout_alignBaseline="@id/name"` 과 같이 사용한다.

## alingParentXXX 속성

뷰를 화면의 왼쪽, 오른쪽, 위쪽, 아래쪽 등 특정 위치에 지정하고 싶을 때가 있다. 간격을 주면 해결할 수 있지만 스마트폰의 크기가 다양하기 때문에 완벽한 해결방법은 아니다. 따라서 다음 속성을 사용한다.

* android:layout_alignParentTop : 부모의 윗부분에 뷰의 상단을 위치
* android:layout_alignParentBotton : 부모의 아랫부분에 뷰의 하단을 위치
* android:layout_alignParentLeft : 부모의 왼쪽에 뷰의 왼쪽을 위치
* android:layout_alignParentRight :  부모의 오른쪽에 뷰의 오른쪽을 위치
* android:layout_centerHorizontal :  부모의 가로 방향 중앙에 뷰를 위치
* android:layout_centerVertical :  부모의 세로 방향 중앙에 뷰를 위치
* android:layout_centerInParent : 부모의 가로세로 중앙에 뷰를 위치

## 출처

* 깡샘의 안드로이드 프로그래밍