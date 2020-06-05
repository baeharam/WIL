---
layout: post
category: Android
title: "[안드로이드]Parceable"
---

# 정의

> Parcel 클래스를 통해 저장을 하고 읽어들일 수 있게 하는 인터페이스로 Parcelable을 implements하는 클래스는 반드시 Parcelabel.Creator 인터페이스를 implements하는 타입의 static변수인 `CREATOR`를 가져야 한다.

안드로이드에는 **컴포넌트 사이에서 데이터를 주고받는 것을 IPC(Inter-Process Communication)**라고 하는데 이 때 이용하는 클래스 중에 하나가 바로 Parcel이다. 보통 컴포넌트 사이에서 IPC를 수행할 때 인텐트를 이용하는데, 인텐트로 HashMap이나 ArrayList를 전달하기 위해선 해당 클래스로 하여금 Serializable이나 Parcelable을 implements하게 해서 전송해야 한다. 이 때 Serializable로 전송할 경우 상당히 느리기 때문에 Parcelable을 활용하는 것이 더 좋다. 구현은 조금 더 복잡하지만 몇 가지만 알면 되니 **안드로이드에서 웬만하면 Serializable은 지양하자.** 

구글에선 Parcelable이 실제 직렬화는 아니고 직렬화된 포맷을 제공하는 것이라고 말하며 실제 직렬화를 할 때는 Serializable 대신 FlatBuffers나 Gson과 같은 라이브러리를 사용하라고 권유한다. 이제 레퍼런스의 의미가 어떤 것인지 살펴보자.

<br>

# 구현

먼저 해당 클래스로 Parcelable을 implements해야 하며 필수로 구현해야 할 몇 가지 메소드가 있다.

* `describeContents()` : Parcelable 객체에서 특별한 객체를 포함하고 있는지 표시하는 부분으로 보통 0을 리턴한다. (파일 디스크립터의 경우 CONTENTS_FILE_DESCRIPTOR)
* `writeToParcel()` : 이름 그대로 Parcel에 저장하는 메소드이다.
* `createFromParcel()` : Parcelable.Creator에서 구현하는 것으로 `writeToParcel()`에 의해 쓰여진 Parcel 객체로부터 Parcelable 객체를 생성한다.
* `newArray()` : Parcelable.Creator에서 구현하는 것으로 Parcelable 객체배열을 생성한다.

```java
public class DataModel implements Parcelable {

    private Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public DataModel(String path) {

        this.imageUri = Uri.fromFile(new File(path));
    }

    private DataModel(Parcel in) {
        imageUri = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(imageUri, i);
    }
}
```

현재 firebase 앱을 공부하고 있는 중에 만든 모델로 이 클래스의 ArrayList를 인텐트로 넘기기 위해 Parcelable을 implements 하였다. 위에서 설명한 바와 동일하다는 것을 알 수 있다.

<br>

# 참고

* [Parcelable](https://developer.android.com/reference/android/os/Parcelable)
* [Parcel](https://developer.android.com/reference/android/os/Parcel)
* [Serialization Performance](https://www.youtube.com/watch?v=IwxIIUypnTE) (좋은 강의)