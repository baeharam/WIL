# 진동 울리기

앱에서 진동을 울리게 하라면 퍼미션(Permission)이 설정되어 있어야 한다. AndroidManifest.xml 파일에서 다음과 같이 설정해주면 된다.

```xml
<uses-permisson android:name="android.permisson.VIBRATE"/>
```

퍼미션이 설정되면 이제 진동을 위해 Vibrator 클래스를 이용한다.

```java
Vibrator vib = (Vibrator)getSystemService(VIBRATER_SERVICE);
vib.vibrate(1000);
```

`getSystemService()` 함수로 진동에 해당하는 SystemService 객체를 받아오는데 SystemService는 시스템에서 제공하는 서비스이다. 받아와서 `vibrate()` 함수로 1초동안 진동을 울리게 된다. 만약 진동을 반복하고 싶을 경우 다음 함수를 이용하면 된다.

```java
vib.vibrate(new long[]{500,1000,500,1000}, -1);
```

위 함수는 현재 deprecated 상태로 VibrationEffect 객체를 인자로 전달받아 사용하는 함수가 API level 26에 추가되었다. 그러나 기기 호환이 되어야 하기 때문에 이걸 사용하는 것이 좋을 듯 하다. 어쨌든 기능을 설명하면 long 타입의 배열에서 0.5초 기다리고 1초 진동하는 방식이며 -1은 반복을 안한다는 말이다. 0으로 설정할 경우 무한히 반복되기 때문에 어느 순간 멈추어야 한다.

# 소리 울리기

소리를 울리게 할 수 있는 방법은 시스템 효과음을 사용하거나 카톡 알림음과 같이 자체적인 효과음을 사용할 수 있다. 먼저 시스템 효과음을 사용하는 방법은 다음과 같다.

```java
Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
ringtone.play();
```

먼저 효과음의 식별자를 Uri 타입으로 획득해야 하는데 효과음은 ALRAM, NOTIFICATION, RINGTON 등이 있다. Uri 값으로 식별되는 효과음을 재생할 수 있는 Ringtone을 얻어 `play()` 함수로 실행하면 된다.

만약, 개발자가 자체적인 효과음을 사용하고 싶다면 `res` 하위의 `raw`폴더와 MediaPlayer 클래스를 이용한다.

```java
MediaPlayer player = MediaPlayer.create(this, R.raw.파일명);
player.start();
```

