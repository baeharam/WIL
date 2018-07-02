---
layout: post
category: Android
title: "[안드로이드]ScrollView"
---

# ScrollView

스크롤이 가능하게 하는 view group으로 FrameLayout의 자식 클래스이며 자식을 하나밖에 가지지 못한다. 따라서 여러개의 view를 포함시키고 싶다면 `LinearLayout`을 사용해야 한다.

Scroll view는 수직 스크롤만 지원하고 수평 스크롤은 지원하지 않기 때문에 수평 스크롤을 사용하기 위해선 [HorizontalScrollView](https://developer.android.com/reference/android/widget/HorizontalScrollView.html)를 사용해야 한다.

또한 [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)나 [ListView](https://developer.android.com/reference/android/widget/ListView.html)는 좋지 않은 UI 퍼포먼스를 보여줄 수 있기 때문에 scroll view에 추가하는 일이 없도록 하자.

수직 스크롤에서 scroll view 대신 [NestedScrollView](https://developer.android.com/reference/android/support/v4/widget/NestedScrollView.html)를 사용하는 것이 더 flexible 하고 scrolling pattern을 많이 제공한다는데 그럼 scroll view는 왜 있는 거지..아 찾아보니 외부 라이브러리고 22.1.0 버전에 추가된 것이라 호환성이 떨어질 것 같다.