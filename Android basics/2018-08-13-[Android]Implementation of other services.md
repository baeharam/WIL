---
layout: post
category: Android
title: "[안드로이드]여러가지 형태의 서비스 구현"
---

# IntentService

가장 기본적인 서비스는 이전시간에 살펴봤으니 인텐트 서비스가 무엇인지 알아보자. 인텐트 서비스는 서비스가 UI 쓰레드가 아닌 다른 쓰레드에서 백그라운드 작업을 수행하려면 `java.lang.Thread` 객체를 생성해야 하는 것과는 다르게 **UI 쓰레드에서 자동으로 작업 쓰레드를 생성**한다. 따라서 긴 작업을 할 경우에 이 클래스를 사용하는 것이 좋다. 하지만 여러개의 **쓰레드가 병렬로 실행되는 것이 아니라 순차적으로 실행**된다는 것을 주의하자. 마지막으로 **쓰레드의 종료는 작업이 모두 끝난 후 자동**으로 되기 때문에 개발자는 신경쓸 필요가 없다.

구현은 `IntentService` 클래스를 상속하고 `onHandleIntent()` 메소드만 구현해주면 되기 때문에 굉장히 간단하다. 또한 메소드의 이름을 보면 알 수 있듯이 인자로 받는 인텐트를 통해 백그라운드 작업을 수행한다.

```java
public class MyIntentService extends IntentService {

    // super에 던지고 있는 건 서비스 이름
    public MyIntentService() {
        super("MyIntentService");
    }

    // 서비스 시작
    // 기본적인 서비스와 구현이 완벽히 동일함.
    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Log.d("MyIntentService", "인텐트 서비스 동작 중 " + i);
        }
    }
}
```

계속해서 인텐트 서비스를 실행한다는 것은 작업 큐에 쌓이는 것을 의미하며 위 코드에 대해선 for문이 5번 돌아갔으면 그 다음 작업을 실행하는 방식으로 진행된다. 인텐트 서비스도 실행하기 위해선 `startService()`를 사용한다.

```java
public void onStartIntentService(View view) {
    Intent intent = new Intent(this, MyIntentService.class);
    startService(intent);
}
```

![image](https://user-images.githubusercontent.com/35518072/44037157-9172cc2a-9f4e-11e8-8acb-fb36431a6b2e.png)

<br>

# Foreground Service

서비스는 메모리가 부족하게 될 경우 시스템이 강제적으로 종료할 수도 있다. 하지만 포그라운드 서비스의 경우 사용자가 능동적으로 인식하고 있기 때문에 중단할 후보로 고려되지 않는다. 그렇기 때문에 현재 포그라운드 서비스가 실행되고 있다는 알림을 제공해야 한다. 예를 들자면, 뮤직 플레이어의 경우는 포그라운드 서비스로 제공되어야 한다.

구현을 함에 있어서는 Android 8.0인 Oreo부터 알림채널이라는 것이 도입되었기 때문에 살짝 복잡하다. Oreo 부분에 대해서 따로 처리를 해주어야 한다.

```java
public void onStartForegroundService(View view) {
    Intent intent = new Intent(this, MyService.class);
    intent.setAction("startForeground");
    // Build.VERSION_CODES.O는 Oreo버전인 API LEVEL 26을 뜻함
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent);
    } else
        startService(intent);
}
```

실행할 때부터 `startForegroundService()`와 `startService()`로 나뉨을 볼 수 있다. 두 메소드 모두 `onStartCommand()`를 호출하는 것은 동일하기 때문에 `intent.setAction("startForeground")`를 통해서 포그라운드 서비스를 구분해야 한다.

```java
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    if("startForeground".equals(intent.getAction())){
        startForegroundService();
    } ...
}
```

위와 같이 넘어온 인텐트의 액션을 받아서 포그라운드 서비스에 해당하는 메소드를 호출한다.

```java
private void startForegroundService() {
    // 알림창 생성, 채널은 "default"로 설정
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setContentTitle("포그라운드 서비스");
    builder.setContentText("포그라운드 서비스 실행 중");

    // 인텐트 생성해서 PendingIntent를 이용해 알림창의 인텐트 설정
    Intent notificationIntent = new Intent(this, service.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    builder.setContentIntent(pendingIntent);

    // Oreo일 경우 채널 설정 해주어야 하는 부분
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
    }
    
    // 포그라운드 서비스 실행
    startForeground(1, builder.build());
}
```

주석에 설명 되어있지만 [PendingIntent](https://developer.android.com/reference/android/app/PendingIntent) 클래스는 좀 생소했다. 특정 컴포넌트의 인텐트를 생성해서 자신이 실행하는 것이 아닌 다른 컴포넌트에게 넘겨서 나중에 실행하도록 하는 개념이다. 그러니까 여기선 알림창을 누를 경우 원래의 어플리케이션 화면으로 가도록 설정한 인텐트라고 할 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/44037078-6abb41f2-9f4e-11e8-998e-13ee0a4780ef.png" width="300px">

<br>

# Bind Service

서비스를 바인딩 한다는 것은 어떤 컴포넌트와 서비스가 연결된다는 것을 뜻하며 여러가지 단계로 구성된다.

* IBinder 인터페이스를 구현해야 하는데, 보통 IBinder를 이미 구현한 Binder 클래스를 상속해서 사용한다.
* `onBind()`에서 Binder를 상속한 클래스의 객체를 리턴해야 한다. (결국 IBinder의 객체)
* ServiceConnection 객체를 생성하고 `onServiceConnected()`와 `onServiceDisconnected()`를 오버라이딩 해야 한다.
* `onStart()`에서 바인딩을 시키고 `onStop()`에서 바인딩 된 상태일 경우 메모리 누수를 위해 해제해야 한다.

## IBinder 구현은 Binder 클래스로 하자.

```java
// MyService 클래스 내부
private IBinder binder = new MyBinder();
public class MyBinder extends Binder {
    public MyService getService() {
        return MyService.this;
    }
}
```

MyService의 객체를 리턴하는 `getService()` 메소드를 가지고 있다.

## `onBind()`에서 IBinder 객체를 리턴하자.

```java
// MyService 클래스 내부
@Override
public IBinder onBind(Intent intent) {
    return binder;
}
```

## ServiceConnection 객체를 생성하고 오버라이딩 하자.

```java
private MyService myService;
private boolean bound;

private ServiceConnection connection = new ServiceConnection() {
    // 서비스가 연결되는 경우
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MyService.MyBinder binder = (MyService.MyBinder) iBinder;
        myService = binder.getService();
        bound = true;
    }

    // 서비스 연결이 끊어지는 경우 (예기치 않은 종료)
    @Override
    public void onServiceDisconnected(ComponentName componentName) {}
};
```

## `onStart()`와 `onStop()`에서 바인딩을 시작하고 해제하자.

```java
@Override
protected void onStart() {
    super.onStart();
    Intent intent = new Intent(this, MyService.class);
    // 바인딩 시작
    bindService(intent, connection, BIND_AUTO_CREATE);
}

@Override
protected void onStop() {
    super.onStop();
    // 바인딩 되어있을 경우 해제
    if(bound) {
        unbindService(connection);
        bound = false;
    }
}
```

<br>

## 출처

* [서비스 공식 문서](https://developer.android.com/guide/components/services?hl=ko)
* [될 때까지 안드로이드 : 서비스, 인텐트 서비스](https://www.youtube.com/watch?v=8PJ12A1C35M&t=615s)
* [될 때까지 안드로이드 : 포그라운드 서비스](https://www.youtube.com/watch?v=rxK_M9VfX2o&t=2s)
* [될 때까지 안드로이드 : 바인드 서비스](https://www.youtube.com/watch?v=XcHF1OZHHWU)