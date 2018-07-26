---
layout: post
category: Android
title: "[안드로이드]LayoutInflater"
---

**LayoutInflator는 XML을 그에 해당하는 뷰 객체로 만들기 위한 클래스로 이 클래스의 객체를 얻는 방법은 다양하다.**

# Activity 클래스인 경우

Activity에 해당하는 클래스인 경우 메소드 하나로 객체를 생성할 수 있다.

```java
LayoutInflater inflater = getLayoutInflater();
```

<br>

# getSystemService를 사용할 수 있는 경우

Context 클래스의 getSystemService 메소드를 사용할 수 있는 경우에 한해서 객체를 생성할 수 있다.

```java
LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
```

<br>

# from 메소드

Context 객체를 넘겨줌으로서 생성할 수 있다.

```java
LayoutInflater inflater = LayoutInflater.from(context);
```

<br>

# View 팩토리 메소드

View 클래스를 통해 생성과 동시에 사용할 수 있다.

```java
View view = View.inflate(context, R.layout.recylcerview, false);
```

<br>

# inflate 메소드

결국 LayoutInflater 객체를 생성하는 이유가 inflate 메소드를 사용해서 XML 파일을 뷰 객체로 만들려는 것이다.

* **resourece** : XML 레이아웃 파일의 ID 값이다.
* **root** : `ViewGroup`으로 생성될 객체의 부모 뷰가 될 수도 있고 그게 아니라면 `LayoutParams`의 값을 지정하는데 사용될 수 있다.
* **attachToRoot** : true면 root의 자식 뷰가 되고 false면 아무 일도 일어나지 않는다.

사용법은 굉장히 간단한데, 생성한 LayoutInflater 객체를 통해서 위의 속성들을 넘겨주면 된다.

```java
// inflater라는 객체를 생성했다고 치자.
View view = inflater.inflate(context, R.layout.inflateExample, false);
```

<br>

## 출처

* https://b.jy.is/android-layoutinflater/
* https://developer.android.com/reference/android/view/LayoutInflater