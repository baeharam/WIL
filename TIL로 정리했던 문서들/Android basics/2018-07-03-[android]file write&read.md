---
layout: post
category: Android
title: "[안드로이드]외부 저장소의 파일을 읽고 쓰기"
---

# \<uses-permission> 설정

외부 저장소의 파일을 읽고 쓰기 위해선 퍼미션을 부여해야 하기 때문에 AndroidManifest.xml 파일에 `<uses-permission>`을 설정해주어야 한다.

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```



# 퍼미션 체크와 요청

다른 EditText나 Button 객체가 있다고 가정하고 파트별로 나눠서 보자. 먼저 해당 퍼미션에 대한 권한이 부여되어있는지 체크를 해야 한다.

```java
boolean fileReadPermission, fileWritePermission;

if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    fileReadPermission = true;
if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    fileWritePermission = true;
```

만약 해당 퍼미션에 대한 권한이 부여되지 않았을 경우 사용자에게 권한을 요청해야 한다.

```java
if(!fileReadPermission || !fileWritePermission) {
    AcitvityCompat.requestPermissions(this, new String[]{
        Manifest.permission.READ_EXTERNAL_PERMISSION,
        Manifest.permission.WRITE_EXTERNAL_PERMISSON
    }, 200);
}
```

<img src="https://user-images.githubusercontent.com/35518072/42194114-577bbdcc-7ead-11e8-9bb4-ecbda85803d8.png" height="400px">



# 퍼미션 요청에 대한 결과 확인

사용자가 해당 퍼미션에 대한 요청을 수락했는지 거부했는지 확인해야 한다. 위에서 퍼미션 요청에 대한 다이얼로그에 대해 사용자가 선택을 하면 닫히면서 자동으로 onRequestPermissionsResult 함수가 실행되기 때문에 오버라이딩 해서 따로 처리를 해주어야 한다.

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode==200 && grantResults.length>0){
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            fileReadPermission=true;
        if(grantResults[1]==PackageManager.PERMISSION_GRANTED)
            fileReadPermsision=true;
    }
}
```



# 퍼미션 결과에 따른 처리

사용자가 해당 퍼미션 권한을 허용했다면 외부 저장소의 파일을 읽고 쓰면 되고 허용하지 않았다면 그에 따른 처리를 해주면 된다.

```java
@Override
public void onClick(View v){
    String content = contentView.getText().toString();
    
    if(fileReadPermission && fileWritePermission){
        FileWriter writer;
        try{
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";
            File dir = new File(dirPath);
            // 폴더가 없을 경우 생성
            if(!dir.exists()){
                dir.mkdir();
            }
            
            File file = new File(dir + "/myfile.txt");
            // 파일이 없을 경우 생성
            if(!file.exists()){
                file.createNewFile();
            }
            
            // 해당 파일에 사용자가 입력한 내용 쓰기
            writer = new FileWriter(file, true);
            writer.write(content);
            writer.flush();
            writer.close();
            
            // 파일에 쓴 결과 확인
            Intent intent = new Intent(this, ReadFileActivity.class);
            startActivity(intent);
        }
    }else{
        // 퍼미션 요청 거부할 경우 toast로 다이얼로그 띄워주자.
        showToast("permission 부여 거부되어 기능 실행 불가!");
    }
}

// 자체 toast 함수 생성
private void showToast(String message){
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    toast.show();
}
```

위 코드에서 [Environment](https://developer.android.com/reference/android/os/Environment) 클래스를 이용하는 부분은 외부 저장소의 루트 경로를 가져오는 것이다.