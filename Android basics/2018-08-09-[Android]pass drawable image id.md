---
layout: post
category: Android
title: "[안드로이드]이미지 리소스 id 다른 액티비티로 넘기기"
---

`getImageResource()`나 `getDrawableId()`같은 메소드가 없기 때문에 ImageView 객체를 통해서 해당 이미지의 drawable 리소스 id를 얻는 방법을 몰랐다.

하지만 [StackOverflow](https://stackoverflow.com/a/14474954/10047925)를 보고 해당 리소스 id로 태그를 설정해주면 된다는 것을 알았다.

```java
imageView0 = (ImageView) findViewById(R.id.imageView0);
imageView1 = (ImageView) findViewById(R.id.imageView1);
imageView2 = (ImageView) findViewById(R.id.imageView2);

imageView0.setTag(R.drawable.apple);
imageView1.setTag(R.drawable.banana);
imageView2.setTag(R.drawable.cereal);
```

위와 같이 태그를 설정해주면 `getTag()`로 얻어서 다른 액티비티에서 사용할 수 있다. 꿀팁!!