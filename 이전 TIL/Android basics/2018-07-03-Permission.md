---
layout: post
category: Android
title: "[안드로이드]Permission"
tage: "안드로이드"
---

# 설정과 사용

<img src="https://developer.android.com/images/training/permissions/request_permission_dialog.png?hl=ko">

안드로이드는 컴포넌트(액티비티/서비스...)를 이용한 앱과 앱 사이의 연동이 빈번한데 앱 A가 외부 앱 B를 사용한다고 한다면 퍼미션이 필요하다. 퍼미션이란 자신의 앱을 사용할 권한을 부여하는 것으로 해당 권한을 가지고 들어올 때만 실행되게 하는 설정이다.

```xml
<permission android:name="com.test.permission.SOME_PERMISSION"
            android:label="SOME permission"
            android:description="@string/permission"
            android:protectionLevel="normal"/>
```

다음과 같이 특정 액티비티를 퍼미션으로 보호하고 싶다면 AndroidManifest.xml 파일에 `<permission>` 태그를 추가해주면 된다. 속성은 여러가지가 있다.

* name : 퍼미션 이름
* label, description : 퍼미션에 대한 설명 (사용자에게 보이는 문자열)
* **protectionLevel** : 보호 수준

AndroidManifest.xml에 파일에 설정을 할 때 특정 컴포넌트에 설정하고 싶은 경우가 있는데 해당 컴포넌트가 적용된 태그에 설정해주면 된다. 이렇게 보호된 컴포넌트를 이용하는 앱, 즉 여기선 앱 A의 AndroidManifest.xml 파일에 `<uses-permission>`을 추가해주어야 사용할 수 있다.

```xml
<uses-permission android:name="com.test.permisssion.SOME_PERMISSION"/>
```



# 보호수준

보호수준은 4가지가 있으며 보통 상위 3가지를 사용한다.

* **normal** : 낮은 수준의 보호, `<uses-permission>`은 요구하지만 퍼미션 부여는 요구하지 않음
* **dangerous** : 높은 수준의 보호, `<uses-permission>`도 요구하고 퍼미션 부여도 요구함
* **signature** : 동일한 키로 서명된 앱만 실행
* signatureOrSystem : 시스템 앱이거나 동일한 키로 서명된 앱만 실행

여기서 퍼미션을 요구한다는 것은 안드로이드 6.0(Marshmallow)에서 변경된 사항으로 그 이전까지는 어떤 퍼미션이 사용되는지만 알려주지만 6.0 이상부터는 "dangerous"로 설정된 퍼미션을 사용할 경우 해당 권한을 부여할지 말지를 사용자가 결정하는 화면이 나타난다.

<img src="https://www.androidhive.info/wp-content/uploads/2016/11/android-working-with-marshmallow-permissions.png">



# 시스템의 퍼미션

외부 앱을 사용하지 않고 시스템 내의 컴포넌트를 사용하는데도 퍼미션이 설정된 경우가 있다. 시스템 자체에서 사용자에게 위험할 수 있는 경우에 퍼미션을 설정해 놓은 것으로 예를 들어, 외부 저장 공간에 파일을 읽거나 쓸 때는 `<uses-permission>`을 추가해주어야 한다.

시스템의 퍼미션은 [Manifest.permission](https://developer.android.com/reference/android/Manifest.permission)에서 확인할 수 있고 개별단위가 아닌 그룹단위로 알려 준다는 것을 기억하자. 방금의 예시에서 READ_EXTERNAL_STORAGE와 WRITE_EXTERNAL_STORAGE는 STORAGE 그룹으로 묶여있다.



# 안드로이드 6.0에서의 퍼미션 체크

위에서 말했다시피 6.0이상부터는 퍼미션 부여를 할지 말지 사용자가 결정할 수 있기 때문에 사용자가 거부하는 경우에 대한 대비를 해두어야 한다. 이 때 사용하는 것이 [checkSelfPermission](https://developer.android.com/reference/androidx/core/content/ContextCompat#checkselfpermission) 함수이며 다음 2가지 종류의 반환값으로 퍼미션이 부여됬는지 확인할 수 있다.

* PERMISSION_GRANTED : 퍼미션 부여된 상태
* PERMISSION_DENIED : 퍼미션 부여되지 않은 상태

퍼미션이 부여되었다면 다음과 같이 사용할 수 있다.

```java
if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
    // 처리
}
```

퍼미션이 부여되지 않았을 경우 퍼미션 허용을 요청해야 하기 때문에 사용자를 귀찮게 하지 않기 위해서 다이얼로그를 띄워주는 것이 좋다. 다이얼로그는 [requestPermissions](https://developer.android.com/reference/androidx/core/app/ActivityCompat#requestpermissions) 함수로 띄워줄 수 있다.

```java
ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
```

이 함수는 위와 같이 호출하는데 두번째 매개변수로 퍼미션 이름을 여러개 전달해서 여러개의 퍼미션 허용을 한꺼번에 요청할 수 있으며 세번째 매개변수는 개발자 임의의 숫자값으로 퍼미션 조정 결과를 확인할 때 사용한다.

해당 퍼미션에 대한 사용자의 조정이 끝나면 대화상자가 닫히면서 [onRequestPermissionResult](https://developer.android.com/reference/androidx/core/app/ActivityCompat.OnRequestPermissionsResultCallback.html#onrequestpermissionsresult)함수가 자동으로 호출되며 해당 결과에 따라 처리를 할 수 있다.

```java
@Override
public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
    if(requestCode==200 && grantResults.length>0){
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            //...
        if(grantResults[1]==PackageManager.PERMISSION_GRANTED)
            //...
    }
}
```





## 출처

* 안드로이드 공식 문서
* 깡샘의 안드로이드 프로그래밍
* https://www.androidhive.info/2016/11/android-working-marshmallow-m-runtime-permissions/

