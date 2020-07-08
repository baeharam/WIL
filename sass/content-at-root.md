## @content와 @at-root

### @content

`{}` 안에 있는 내용을 그대로 가져와서 넣어주는 지시자(directive)로 믹스인과 많이 사용된다. 예를 들어, 배경색을 빨간색으로 바꿔주는 믹스인이 있다고 하면 다음과 같이 사용할 수 있다.

```scss
@mixin bgRed {
  background-color: red;
  @content;
}

.container {
  @include bgRed {
    width: 100px;
    height: 100px;
  }
}
```

이렇게 하면 동일한 스타일을 중복해서 쓰지 않고도 다양한 모양을 적용할 수 있다. 이런 상황 외에도 미디어 쿼리에 관한 믹스인을 만들어 사용할 수도 있다.

```scss
@mixin media($width) {
  @media only screen and (max-width: $width) {
    @content;
  }
}

.container {
  @include media(320px) {
    width: 50px;
    height: 50px;
  }
}
```

각 화면 사이즈에 맞게 미디어 쿼리를 좀 더 용이하게 적용할 수 있다.

### @at-root

부모 하위의 클래스 깊어감에 따라 중첩과 들여쓰기를 사용해서 스타일링을 하게 된다.

```html
<div class="grand-parent">
  <div class="parent">
    <div class="child">
    </div>
  </div>
</div>
```

여기서 `.is-active.grand-parent .parent .child` 라는 클래스를 만들고 싶다고 해보자. 아마도 이렇게 작성할 것이다.

```scss
.grand-parent {
  ...
  .parent {
    ...
    .child {
      ...
      .is-active#{&} {
        ...
      }
    }
  }
}
```

하지만 이렇게 하면 CSS로 컴파일 되었을 때 다음과 같이 나온다.

```css
.grand-parent .parent .child .is-active.grand-parent .parent .child {
  ...
}
```

총체적 난국같이 보이는 클래스명이 나오게 되는 것이다, 이를 해결하기 위해 등장한 것이 바로 `@at-root` 지시자이다. 최상위 레벨에 클래스를 작성할 수 있게끔 도와준다.

```scss
.grand-parent {
  ...
  .parent {
    ...
    .child {
      ...
      @at-root .is-active#{&} {
        ...
      }
    }
  }
}
```

```css
.is-active.grand-parent .parent .child {
  ...
}
```

아직까진 2경우 모두 활발하게 사용해본적은 없지만 확실히 잘 사용하면 쓸모있을 것 같다.

<br>

## 참고

* [Using Sass’ @at-root for Nesting Variants](https://medium.com/buddy-reno/using-sass-at-root-for-nesting-variants-874735eb5766)
* [Sass's `@content` Directive Use Cases](https://thoughtbot.com/blog/sasss-content-directive)

