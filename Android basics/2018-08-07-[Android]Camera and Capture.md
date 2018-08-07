---
layout: post
category: Android
title: "[안드로이드]카메라를 이용해 이미지 캡쳐하기"
---

<u>하울님 강의를 정리한 내용입니다.</u>

# 권한 및 필요조건 부여

카메라를 사용하고 캡쳐한 걸 이용해 저장하기 위해선 그에 따른 퍼미션과 필요조건 부여가 필요하다. 퍼미션 부여는 알고 있었지만 `<uses-feature>`라는 부분도 있다는 걸 처음 알았다. 이건 해당 앱에서 어떤 기능을 필요로 하는지를 명시하는 것으로 `android:required` 속성 값으로 true를 주느냐 false를 주느냐에 따라서 실행하고 못하고가 달라진다. 즉, 카메라 기능이 반드시 필요한 앱이라면 true를 주고 카메라 기능이 굳이 없어도 되면 false를 주면 된다. false를 줄 경우엔 카메라 기능이 동작하지 않아도 실행이 된다.

따라서, 카메라와 쓰기 권한을 부여해야 한다. `AndroidManifest.xml` 파일에 다음과 같이 적어준다.

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-feature android:name="android.hardware.camera"/>
```

<br>

# provider 추가

캡쳐한 사진을 저장하기 위해선 content provider 컴포넌트를 사용해야 하므로 `<provider>`또한 추가해주어야 한다.

```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="com.haram.camera.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths"></meta-data>
</provider>
```

`authorities` 속성에는 앱의 패키지 명을 적어주어야 한다. 또한 아래쪽의 `<meta-data>`를 보면 xml폴더의 파일을 가리키고 있는데 없으면 추가해주어야 한다.

![image](https://user-images.githubusercontent.com/35518072/43775339-4b9f4e06-9a87-11e8-98e1-6a511205339d.png)

위와 같이 추가해주면 기본 설정은 끝났다고 할 수 있다.

<br>

# 권한 요청하기

처음 앱을 켰을 때 카메라와 쓰기 권한을 요청해야 하므로 해당 메소드를 만들자. 설명은 주석에 자세히 달겠다.

```java
private void requirePermission() {

    // 요구할 퍼미션들의 배열 생성
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    
    // 허가되지 않은 퍼미션을 저장할 리스트 생성
    List<String> listPermissionsNeeded = new ArrayList<>();

    for(String permission : permissions) {
        // 권한이 부여가 안됬을 때 권한을 모집하는 부분
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            listPermissionsNeeded.add(permission);
        }
    }

    // 허가되지 않은 퍼미션이 있을 경우
    if(!listPermissionsNeeded.isEmpty()) {
        // 권한 요청하는 부분
        ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                1);
    }
}
```

<br>

# 권한 확인하고 실행하기

`requirePermission()`으로 권한을 요청한 뒤에 권한을 확인하고 원하는 기능을 실행하는 부분이다. 따로 xml 파일은 첨부하지 않겠지만 버튼을 눌렀을 때 카메라가 실행되고 찍으면 그걸 저장해서 보여주는 앱이다. 버튼을 만들었다고 치고 해당 버튼을 눌렀을 때 권한을 확인하고 실행할 것이다.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 권한 요청
    requirePermission();

    // 버튼에 리스너 달기
    Button button = findViewById(R.id.camera_button);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 권한 부여되었는지 확인
            boolean camera = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;

            boolean write = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;

            // 권한이 부여되었으면
            if(camera && write) {
                // 사진찍는 인텐트 코드 넣기
                takePicture();
            // 권한이 부여되지 않았으면 경고 메시지 출력
            } else {
                Toast.makeText(MainActivity.this, "카메라 및 쓰기 권한 부여 안됨!", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
```

<br>

# 사진을 찍자

권한이 부여될 경우 `takePicture()` 메소드가 호출되므로 이 코드를 살펴보자. 먼저 동작방식은 아래와 같다.

* 캡쳐를 위한 인텐트를 생성한다.
* 파일을 생성한다.
* 해당 파일을 Uri 객체로 변환한다.
* 인텐트에 변환한 Uri 객체를 인자로 전달한 뒤 `startActivityForResult()` 를 호출하여 카메라를 실행한다.

```java
private void takePicture() {
    // 인텐트 생성
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    try{
        // 파일 생성
        File photoFile = createImageFile();
        // Uri 객체로 변환
        Uri photoUri = FileProvider.getUriForFile(this, "com.haram.camera.fileprovider", photoFile);
        // 인텐트에 인자로 전달
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        // 카메라 실행
        startActivityForResult(intent, 0);
    } catch(IOException e){ // 파일 생성 관련
        e.printStackTrace();
    }
}
```

<br>

# 파일 생성은?

위에서 파일을 생성할 때 `createImageFile()` 메소드를 호출해서 파일을 생성했으니 어떻게 생성했는지 알아보자.

* 현재 날짜와 시간에 대한 문자열 생성
* 원하는 파일이름 포맷을 설정해서 날짜와 시간이랑 합침
* 이미지 관련 디렉토리 받아옴
* 정한 이름 포맷에 .jpg 붙여서 해당 디렉토리에 파일 생성

```java
private File createImageFile() throws IOException {
    // 날짜와 시간에 대한 문자열
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    // 원하는 이름 포맷
    String imageFileName = "JPEG_" + timeStamp + "_";
    // 디렉토리 받아옴
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    // 파일 생성
    File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
    );

    // 전역변수로 설정한 mCurrentPhotoPath에 경로 저장
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
}
```

<br>

# 캡쳐한 이미지 보여주자

사진을 찍을 때 `startActivityForResult()` 메소드를 호출했는데 기존에 다른 액티비티를 호출할 때 사용했던 `startActivity()`와는 다르게 해당 액티비티를 통해 어떤 결과값을 받아오고 싶을 때 호출하는 메소드이다. 카메라를 실행할 때 호출했으니 찍은 사진을 결과값으로 받아올 수 있다. 왜? 방금 위에서 `mCurrentPhotoPath`에 이미지 파일의 경로를 저장하지 않았는가? 코드를 보자.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    // startActivityForResult() 실행할 때 requestCode로 0 보냈음
    if(requestCode == 0) {
        // ImageView 객체 생성
        ImageView imageView = findViewById(R.id.camera_image);
        // 해당 뷰에 비트맵으로 이미지 보여줌
        imageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
    }
}
```

여기서 `BitmapFactory.decodeFile()`은 딱 봐도 객체를 찍어내는 팩토리 메서드 패턴을 사용했다는 걸 알 수 있고 이미지 파일의 경로만 넘겨주면 `Bitmap` 객체를 생성해준다는 것을 알 수 있다.

<br>

## 출처

* [Take Photos](https://developer.android.com/training/camera/photobasics)
* [하울의 코딩 채널](https://www.youtube.com/watch?v=H1caFACYsY4)

