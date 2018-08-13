---
layout: post
category: Android
title: "[안드로이드]서비스가 필요한 이유"
---

[될 때까지 안드로이드](https://www.youtube.com/watch?v=8PJ12A1C35M) 강의를 정리한 내용입니다.

# 쓰레드를 쓸 경우

자바의 Thread로 쓰레드를 생성하여 작업을 한다고 하자. 그럼 100초 동안 for문을 돌리는 작업을 했을 때 어떻게 동작할까?

```java
public void startThread(View view) {
    if(thread==null){
        thread = new Thread("My thread"){
            @Override
            public void run() {
                for(int i=0; i<100; i++){
                    try {
                        count++;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                    Log.d("My Thread", "스레드 동작 중 "+count);
                }
            }
        };
        thread.start();
    }
}
```

쓰레드를 시작하는 메소드이며 100초동안 작업을 진행한다. 만약 작업을 중단하고 싶다면 아래의 메소드를 호출하면 된다.

```java
public void stopThread(View view) {
    if(thread!=null){
        thread.interrupt();
        thread=null;
        count=0;
    }
}
```

하지만 이 메소드는 Thread 객체가 null이 아닐 경우에만 쓰레드를 중지한다. 따라서 뒤로 가기 버튼을 누르고 다시 앱으로 돌아올 경우 Thread 객체는 사라지기 때문에 **쓰레드를 중지시킬 수가 없다.** 또한 `startThread()` 메소드를 또 호출한다면 **동시에 2개의 쓰레드가 생기기 때문에** 의도하지 않았을 경우 별로 좋지 않다고 할 수 있다. 이런 배경에서 4대 컴포넌트 중 하나인 서비스(Service)의 필요성이 부각된다.

<br>

# 서비스를 쓴다면?

위 상황을 서비스를 통해 개선해보도록 하자. 서비스를 사용하기 위해선 따로 추가해주어야 하며 4대 컴포넌트 끼리의 커뮤니케이션은 인텐트를 활용하기 때문에 액티비티에서 서비스를 실행하려고 할 때 인텐트를 사용해야 한다.

기본적인 골격은 같지만 메인 액티비티에서 실행하는 것이 아니라 서비스로 해당 작업을 넘긴다.

```java
// 인텐트 생성하여 서비스 시작
public void onStartService(View view) {
    Intent intent = new Intent(this, MyService.class);
    // 실제 시작 메소드
    startService(intent);
}

// 인텐트 생성하여 서비스 종료
public void onStopService(View view) {
    Intent intent = new Intent(this, MyService.class);
    // 실제 종료 메소드
    stopService(intent);
}
```

이렇게 정해진 메소드들로 시작/종료를 하며 자세한 작업은 `Service`를 상속받는 클래스에서 한다.

```java
@Override
// 서비스 활성화
public int onStartCommand(Intent intent, int flags, int startId) {
    // 아까와 똑같은 작업
    if(thread==null) {
        thread = new Thread("My thread") {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        count++;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                    Log.d("My Service", "서비스 동작 중 " + count);
                }
            }
        };
        thread.start();
    }
    return START_STICKY;
}
```

위 메소드를 통해서 실제 서비스가 활성화가 되며 백그라운드에서 작업이 이루어진다. 쓰레드와는 다르게 2개의 쓰레드가 실행되지 않고 아래 메소드를 통해서 종료도 된다.

```java
@Override
// 서비스 종료
public void onDestroy() {
    super.onDestroy();
    if(thread!=null){
        thread.interrupt();
        thread=null;
        count=0;
    }
}
```

서비스 하나 만들어서 메소드만 오버라이딩 했을 뿐인데 문제가 해결되었다. 그렇다면 백그라운드 작업엔 쓰레드 대신 서비스를 사용하는 것이 맞는 걸까? 쓰레드는 언제 사용하고 서비스는 언제 사용해야 할까? 또 서비스의 생명주기는 어떻게 될까? 다음 포스팅에서 알아보도록 하자.