## reject vs throw

`async` 블록, 혹은 Promise의 콜백함수 내부에서 에러를 던져야 하는 상황을 보자. 이 상황에서 취할 수 있는 방식은 2가지이다.

* `throw` 를 통해 에러를 던진다.
* `reject` 함수로 Promise 객체가 reject 된 뒤에 에러가 전달된다.

만약 블록/함수 안에 비동기로 실행되는 코드가 없다면, 둘 다 문제가 없다. 모두 reject되며 동일한 에러가 전달되고 `catch` 블록으로 잡을 수 있다. 하지만, 비동기 콜백함수가 있다면 어떨까? 가장 기본적인 `setTimeout()` 을 보면 확인할 수 있다.

* `throw` 의 경우

```javascript
new Promise(() => {
  setTimeout(() =>{
    throw 'Error';
  }, 0)
}).catch(console.log);
```

에러가 잡히지 않는 것을 볼 수 있다. 이는 Promise가 reject되기 전에 에러가 던져졌기 때문이다.

* `reject` 의 경우

```javascript
new Promise((_, reject) => {
  setTimeout(() =>{
    reject('Error');
  }, 0)
}).catch(console.log);
```

바로 에러가 잡히는 것을 확인할 수 있다. 이는 Promise가 reject되고 에러 메시지를 받기 때문이다. 따라서, 비동기 로직을 사용한다면 `reject` 를, 단순한 동기 로직을 사용한다면 `throw` 를 사용하도록 하자.

<br>

## 참고

* [JavaScript Promises - reject vs. throw](https://stackoverflow.com/questions/33445415/javascript-promises-reject-vs-throw)