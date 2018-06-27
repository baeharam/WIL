안드로이드에는 여러가지 뷰 클래스가 있는데 기초적인 뷰와 많이 쓰이는 속성들에 대해 알아보자.

# TextView

가장 많이 사용하는 뷰로 문자열을 화면에 출력해주는 뷰이다.

* text : 직접 문자열을 입력하거나 문자열 리소스를 이용할 수 있는데 리소스를 활용하는 것이 좋다.
* typeface : 문자열의 폰트를 지정하는데 외부폰트를 사용하려면 assets 폴더에 복사한 후 지정하면 된다.
* autoLink : 문자열을 분석해서 URL이 포함되어 있으면 자동적으로 하이퍼링크를 생성한다.
* maxLines : 최대 출력하고자 하는 줄의 수를 지정한다. (~줄까지만 출력한다는 것)
* ellipsize : maxLines로 지정한 줄만큼 출력할 수 있는데 줄임표시를 하기 위해 사용된다.

# ImageView

이미지를 출력하는 방법은 많지만 ImageView를 활용하는 것이 가장 편하다고 한다.

* maxWidth/maxHeight : 이미지가 굉장히 클 경우 wrap_content로도 모든 이미지를 표시할 수 없기 때문에 전부 표시할 때 사용된다.
* adjustViewBounds : 이미지의 크기를 변경할 때 가로/세로 비율을 유지할지 지정한다. (true/false)
* tint : 이미지 위에 다른 색상을 입힐 때 사용한다.

# EditText

사용자에게 데이터를 입력받을 때 사용하는 뷰로 TextView의 subclass이므로 TextView의 속성을 사용할 수 있다.

* lines : 처음 화면에 보일 때부터 특정 줄 만큼 보이게 하는 속성으로 고정값이다.
* maxLines : 처음에 한 줄만 보이며 개행을 했을 때 지정한 값까지만 줄이 늘어나게 된다.
* inputType : 가장 중요한 속성으로 숫자를 입력할지 문자를 입력할지 아니면 비밀번호를 입력하는 것에 따라서 키보드의 형태와 표시가 달라지기 때문에 사용되고 또한 한 줄/여러 줄 입력을 강제할 때 쓰인다.
* gravity : 입력할 글의 위치를 조정한다.

# Button

Button은 TextView의 subclass로 해당 속성을 사용할 수 있고 subclass로 CompoundButton이 있으며 CompoundButton은 CheckBox, RadioButton, ToggleButton으로 구성된다.

## CheckBox

* isChecked() : 해당 CheckBox가 체크된 상태이지를 반환한다.
* setChecked() : CheckBox의 체크 상태를 바꾸기 위한 함수이며 true가 체크된 상태이다.
* toggle() : 이 함수를 사용하면 CheckBox의 상태와 상관없이 반대로 바뀐다.

예를 들어, 다음 코드와 같이 나타낼 수 있는데 체크를 할 경우 텍스트가 바뀌는 형태이다.

```java
checkBox =  (CheckBox)findViewById(R.id.checkbox);
checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			checkBox.setText("is Checked");
		}else{
			checkBox.setText("is unChecked");
		}
	}
});
```

## RadioButton

알다시피 CheckBox와는 다르게 여러개중 한가지만 선택할 수 있다. 이를 위해 RadioGroup과 RadioGroup으로 묶인 RadioButton 클래스가 제공된다.

* check() : 매개변수로 체크하고자 하는 RadioButton의 id값을 주면 체크된다.
* clearCheck() : RadioGroup의 RadioButoon 체크 상태를 해제한다.
* getCheckedRadioButtonId() : 체크된 RadioButton의 id 값을 획득한다.