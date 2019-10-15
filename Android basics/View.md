# View와 ViewGroup

안드로이드의 UI는 view와 viewgroup을 구성하여 만들어지며 view는 text box나 button과 같은 widget을 말하고 viewgroup은 layout을 말한다. 하지만 viewgroup인 layout도 하나의 view에 속할 수 있는데, viewgroup이 view를 포함할 수 있기 때문이다.

<img src="https://developer.android.com/images/viewgroup_2x.png">

다음과 같이 viewgroup에 여러개의 view가 속하며 viewgroup의 컴포넌트에는 또 다시 viewgroup이 올 수 있다. 따라서, viewgroup은 view를 포함하기도 하며 상속하기도 한다고 할 수 있는데 실제로 viewgroup이 view의 subclass이기 때문이다.

# layout_width, layout_height

다음 2가지 속성은 View의 필수 속성으로 폭과 높이를 설정하며 3가지 옵션을 가진다.

* `match_parent` : ViewGroup에 남아 있는 여유 공간을 채우는 것으로, button을 만들었을 경우 해당 button에 있는 내용크기가 아닌 ViewGroup의 크기로 결정되는 것.
* `wrap_content` : View 내용물의 크기게 따라 View의 크기가 결정된다.
* 크기 값 지정 : 크기를 고정된 값으로 직접 지정하고 싶을 때 사용하는데 px,dp와 같은 단위를 사용한다. px은 고정크기이며 dp는 상대크기로 보통 dp를 사용한다고 한다.

## 출처

[안드로이드 공식문서](https://developer.android.com/training/basics/firstapp/building-ui)

[Do it! 안드로이드 youtube 강의](https://www.youtube.com/watch?v=VWCD91lSm4g&index=15&list=PLG7te9eYUi7sq701GghpoSKe-jbkx9NIF)