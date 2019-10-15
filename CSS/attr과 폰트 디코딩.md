## attr()

`::after` , `::before` 와 같은 가상 엘리먼트의 `content` 속성에 가변적으로 값을 지정하고 싶을 때 사용한다. 마크업의 `data-` 를 이용해서 컨텐츠를 지정하고 해당 방식 아래와 같이 사용한다.

```html
<div class="test" data-title="test title"></div>
```

```css
.test::after { content: attr(data-title); }
```

## 폰트 디코딩 에러

CSS에서 `@font-face` 를 지정하여 커스텀 폰트를 사용할 때 폰트가 디코딩이 제대로 안될 때가 있어 찾아보니 `format` 으로 속성값을 지정해줘야 한다고 한다. 여기서 또 배운 것은 폰트 확장자가 `woff` 인 것이 호환성이나 용량 측면에서 `otf` 나 `ttf` 보다는 낫다고 한다. 아래와 같이 할 수 있다.

```css
@font-face {
  font-family: "SeoulHangangL";
  src: url("../fonts/SeoulHangangL.woff") format('woff');
}
```

