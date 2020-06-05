---
layout: post
category: Android
title: "[안드로이드]Content Provider를 이용해 사진 가져오기"
---

# Content Provider

안드로이드의 4대 컴포넌트 중 하나인 content provider는 간단하게 말해서 프로세스 간 통신(IPC)을 이용하여 데이터 저장소에 접근하는 방법을 제공해준다고 보면 된다. 예를 들어, 스마트폰에 저장된 사진을 보여주고 싶을 때 해당 content provider를 이용해 접근할 수 있다.

<img src="https://i.ytimg.com/vi/-4GgzqMVrYc/maxresdefault.jpg">

위 그림처럼 왓츠앱이나 스카이프에서 전화번호부 앱의 데이터를 사용하기 위해선 데이터를 요청해서 받아야 한다. 이 때 데이터를 요청하기 위해 사용되는 것이 content resolver이며 content resolver가 content provider의 URI를 이용해 데이터 요청을 하면 content provider는 Cursor 객체를 반환시킨다. 따라서, 실제 데이터를 사용할 때는 Cursor 객체를 이용해서 사용한다. Cursor에 대한 설명은 [여기](http://arabiannight.tistory.com/entry/368)에서 자세히 설명하니 참고하자.

<br>

# Cursor를 가져오자

여기서도 xml은 굳이 언급하지 않고 중요한 부분들만 정리하겠다. 레이아웃은 GridView를 사용했으며 저장된 사진을 가져와서 그리드뷰로 보여주는 걸 구현할 것이다. 저장된 사진에 접근하기 위해 content resolver를 통해 해당 content provider에 접근해야 한다.

```java
GridView gridView = findViewById(R.id.photo_list);
Cursor cursor = getContentResolver().query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        MediaStore.Images.ImageColumns.DATE_TAKEN+ " DESC"
);
```

* GridView의 객체 생성
* getContentResolver()로 content resolver 생성
* `query()`로 사진들이 저장된 content provider에 접근해서 Cursor 객체 받아옴

여기서 `MediaStore.Images.Media.EXTERNAL_CONTENT_URI` 는 content provider에 해당하는 URI를 나타내며 나머지 옵션들을 `null`로 한 이유는 모든 데이터를 가져오기 위함이다. 마지막 옵션은 `DESC`를 보면 알 수 있듯이 날짜에 따라 최신 것부터 가져온다는 것을 알 수 있다.

<br>

# CursorAdapter를 상속한 클래스를 만들자

GridView에 여러가지 뷰를 뿌려주기 위해선 AdapterView를 사용해야 하며 AdapterView를 사용하기 위해선 Adapter가 필요하다. 여기선 Cursor를 사용하므로 CursorAdapter를 상속한 클래스를 만들어주어야 한다.

```java
public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    // GridView 객체를 생성한다.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_photo, viewGroup, false);
    }
    
    // 생성한 GridView가 넘어오고 cursor의 값을 이용해 이미지 경로를 알아낸다.
    // 그 이미지 경로로 GridView의 이미지 표시하는 뷰에 이미지를 뿌려준다.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.photo_image);

        String uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

        imageView.setImageURI(Uri.parse(uri));
    }
}
```

<br>

# 클릭이벤트를 주자

여기서 끝나면 심심하니 각 아이템에 대해서 클릭했을 시에 그 이미지의 경로를 토스트로 출력해주는 코드를 작성해보자. 원래 기본적인 뷰의 이벤트 리스너를 설정할 땐 `setOnClickListener()`를 사용했지만 어댑터 뷰의 리스너를 설정할 땐 `setOnItemClickListener()`를 이용해야 한다.

```java
gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 어댑터로부터 Cursor 객체 받아오기
        Cursor cursor = (Cursor) adapterView.getAdapter().getItem(i);
        // 위에서와 같이 Cursor 객체를 이용해 이미지 경로 받아오기
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        // 이미지 경로를 토스트로 뿌려주기
        Toast.makeText(MainActivity.this, "사진 경로 : "+path, Toast.LENGTH_SHORT).show();
    }
});
```

<br>

# 사진 뿌려주기를 개선하자

위에서 CursorAdapter를 상속하는 클래스를 만들 때 사진을 뿌려주는 부분은 계속 `Uri.parse()`를 이용해 문자열을 URI 객체로 변환한 다음 나타냈다. 하지만 이는 UI가 버벅거리는 현상을 불러 일으키고 메모리를 많이 잡아먹게 된다. 따라서 이를 개선하기 위해 Glide 라이브러리를 사용할 수 있다. 마지막 한 줄만 바꿔주면 된다.

```java
// 오래 걸림
imageView.setImageURI(Uri.parse(uri));

// 개선
Glide.with(context).load(uri).into(imageView);
```

<br>

# 결과

에뮬레이터에서 돌리면 실제 사진을 보여줄 수 없으므로 직접 스마트폰으로 캡쳐했다.

<img src="https://user-images.githubusercontent.com/35518072/43826630-bb32aa6e-9b32-11e8-86f2-1c322dfb09e6.jpg" width="300px">

<br>

## 출처

* https://www.youtube.com/watch?v=-4GgzqMVrYc
* https://www.youtube.com/watch?v=v5tGtDhpzZg&t=101s