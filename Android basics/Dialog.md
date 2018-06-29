# 토스트(Toast)

> A toast is a view containing a quick little message for the user.   
> The toast class helps you create and show those. 

잠깐 나타났다 사라지는 다이얼로그로 사용자에게 메시지를 알리면서 사용자의 행동을 전혀 방해하지 않는 모달리스 형식의 다이얼로그이다. 다음 함수들로 생성할 수 있다.

```java
makeText(Context context, int resId, int duration);
makeText(Context context, CharSequence text, int duration);
```

문자열이나 문자열에 해당되는 리소스를 띄우는 것으로 duration만큼 띄운다는 뜻인데 LENGTH_SHORT나 LENGTH_LONG으로 지정할 수 있다.

![2](https://user-images.githubusercontent.com/35518072/42016226-b0e35ad0-7ae5-11e8-9734-73e050a45215.JPG)



# 알림 창(AlertDialog)

메시지 확인을 강제하기 위해 알림 창을 띄우는 방법으로 특이하게 new 연산자로 생성할 수 없고 Builder 클래스를 이용하여 구성을 한 뒤에 `create()` 함수로 생성할 수 있다.

```java
// Builder 객체 생성
AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Builder 객체로 다이얼로그 구성
bulider.setIcon(android.R.drawable.ic_dialog_alert);
builder.setTitle("알림");
builder.setMessage("정말 종료 하시겠습니까?");
builder.setPositiveButton("OK",null);
builder.setNegativeButton("NO",null);

// 다이얼로그 생성하여 화면에 출력
alertDialog = builder.create();
alertDialog.show();
```

알림창에 버튼을 추가할 때는 positive/negative/neutral 로 추가를 할 수 있고 같은 성격의 버튼은 1개만 추가할 수 있다. 또한 다이얼로그 창이 닫히지 않게 하기 위해선 다음 2가지 방식을 사용할 수 있다.

```java
setCancelable(boolean cancelable); // 뒤로가기 터치했을 때 닫히는 여부 설정
setCanceledOnTouchOutside(boolean cancel); // 다이얼로그 창밖을 터치했을 때 닫히는 여부 설정
```

<img src="https://user-images.githubusercontent.com/35518072/42016243-c0bba5f2-7ae5-11e8-8f43-f8b29f5b72f5.JPG" height="400px">

다음 화면과 같이 나오는데 하단의 버튼을 눌렀을 때의 이벤트 또한 정할 수 있다.

```java
DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 이벤트 처리
        }
};
```

처음 코드에선 `setPositiveButton`의 2번째 인자를 `null`로 했지만 원래 이 부분은 이벤트 핸들러 객체를 전달하는 부분이기 때문에 다이얼로그에 해당되는 OnClickListener 객체를 생성하여 다음과 같이 전달해 주면 된다.

```java
builder.setPositiveButton("OK", dialogListener);
```

"OK"를 눌렀을 때 dialogListener에 작성한 이벤트가 실행된다.

# 목록

<img src="https://user-images.githubusercontent.com/35518072/42067502-7edf7cca-7b81-11e8-9f88-7ba1ebef89e6.JPG" height="400px">

일반 알림 창 말고도 여러개의 목록이 나열되어 선택해야 하는 창이 있다. 이것또한 AlertDialog가 기본적으로 제공해주는데 목록에 해당하는 각각의 문자열을 지정하는 방식에 따라 2가지 방법으로 나뉜다.

```java
setItems(int itemsId, DialogInterface.OnClickListener listener);
setItems(CharSequence[] items, DialogInterface.OnClickListener listener);
```

첫번째 함수는 문자열을 리소스로 가져오는 방법이고 두번째 함수는 문자열을 코드에서 직접 배열에 만들어 사용하는 방법이다.  만약 리소스를 가져오는 방법을 사용한다면 다음과 같이 쓸 수 있다.

```java
builder.setItems(R.array.dialog_array, dialogListener);
```

체크박스나 라디오버튼으로도 목록을 구현할 수 있는데 위 화면은 라디오버튼으로 구현한 경우이다.

```java
// 체크박스, 코드를 이용한 문자열
setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener);
// 라디오버튼, 코드를 이용한 문자열
setSingleChoiceItmes(CharSequence[] items, int checkedItem, DialogInterfcae.OnClickListener listener);
```

# 진행상태(Progress Dialog)

<img src="https://user-images.githubusercontent.com/35518072/42067667-5618979e-7b82-11e8-9e81-8e366ab23154.JPG" height="400px">

진행 상태 또한 나타낼 때가 많아서 아예 ProgressDialog라는 클래스를 제공하며 AlertDialog의 하위 클래스이기 때문에 기본 작성법은 똑같다. 그러나 API level 26에서 deprecated 되어서 대신 ProgressBar 클래스를 사용하는 것이 좋다고 한다. Deprecated 된 이유는 사용자와의 상호작용을 방해해서라고...

> This class was deprecated in API level 26. **ProgressDialog is a modal dialog, which prevents the user from interacting with the app.** Instead of using this class, you should use a progress indicator like ProgressBar, which can be embedded in your app's UI. Alternatively, you can use a notification to inform the user of the task's progress. 

아직은 정확하게 사용하는 방법을 모르니 일단 정리해보면 Builder 클래스와 비슷하게 사용한다.

```java
ProgressDialog progressDialog = new ProgressDialog(this);
progressDialog.setIcon(andorid.R.drawable.ic_dialog_alert);
progressDialog.setTitle("Wait...");
progressDialog.setMessage("서버 연동중입니다. 잠시만 기다리세요.");
progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
progressDialog.setMax(100);
progressDialog.show();
```

# 날짜 선택

<img src="https://user-images.githubusercontent.com/35518072/42067994-56bfc71a-7b84-11e8-95fd-a9a1055b6444.JPG" height="400px">

날짜를 선택할 수 있는 다이얼로그를 보여주는 [DatePickerDialog](https://developer.android.com/reference/android/app/DatePickerDialog)를 제공한다. 선택할 때의 이벤트 처리하는 리스너는 onDateSetListener이므로 다음과 같이 다이얼로그를 생성한다.

```java
DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
               showToast(year+":"+(monthOfYear+1)+":"+dayOfMonth);
        }
}, year, month, day);
```

year, month, day를 받아올 때는 자바의 [Calendar](https://docs.oracle.com/javase/9/docs/api/java/util/Calendar.html#get-int-)로 받아온다.

```java
Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);
```

# 시간 선택

<img src="https://user-images.githubusercontent.com/35518072/42068094-e6653a80-7b84-11e8-8d33-edf87cf7b152.JPG" height="400px">

마찬가지로 시간선택을 하는 [TimePickerDialog](https://developer.android.com/reference/android/app/TimePickerDialog)도 제공하며 사용법은 똑같다.

```java
Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);

TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showToast(hourOfDay+":"+minute);
      }
}, hour, minute, false);
```
# 커스텀 다이얼로그

다이얼로그를 직접 커스터마이징 해서 사용할 수 있는데 레이아웃 XML 파일을 만든다음 LayoutInflater 클래스를 이용하면 된다. 레이아웃 XML은 기존의 레이아웃을 만들 때처럼 만들면 된다.

<img src="https://user-images.githubusercontent.com/35518072/42068202-8dd3d97a-7b85-11e8-9dc0-65b6b5968674.png" height="400px">

LayoutInflater 클래스를 이용하여 커스터마이징한 다이얼로그를 AlertDialog의 본문에 지정하면 된다.

```java
LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
View view = inflater.inflate(R.layout.dialog_custom, null); // 레이아웃 파일 초기화
builder.setView(view); // 본문에 지정
```



