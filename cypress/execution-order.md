## 실행 순서

Cypress로 E2E 테스트를 작성하면서, 실행 순서가 어떻게 되는지 애매하게 알고 있었기 때문에 찾아보면서 확실해진 부분이 있어 정리해본다. 기본적으로, `cy` 를 사용해서 호출하는 모든 명령(command) 및 단언(assertion)들은 **모두 비동기로 동작한다.**

> ***Remember: Cypress commands are asynchronous and get queued for execution at a later time.***

공식 홈페이지에 있던 내용을 그대로 가져왔는데, 보다시피 모든 명령은 비동기로 동작하며 실행을 위해 큐잉(get queued)된다. 그렇게 큐잉된 명령들은 테스트 몸통(`it`) 이 끝나는 시점에 순서대로 가져와서 실행된다. 따라서, cypress에 **속하지 않은 함수** 들은 **큐잉되지 않고 바로 실행** 된다.

```typescript
describe("Cypress 동작방식", () => {
    it("실험", () => {
        cy.visit("https://www.google.com");
        cy.get("[name=q]").type("Cypress");
        alert("Hey! you must click OK in order to continue.");
    });
});
```

위와 같은 코드가 있다고 했을 때 "경고창"은 언제 실행될까? 정답은, 바로 실행된다이다. `alert` 는 cypress에 속하지 않았기 때문에 바로 실행되며 나머지는 큐잉된 뒤에 순서대로 실행된다. 즉, 다음과 같다.

```
alert --> visit --> get --> type
```

그렇다면 또 문제가 있는데, `then()` 과 같은 경우의 콜백함수는 어떻게 처리될까?

```typescript
cy.get('.nav').then(console.log);
cy.visit("https://google.com");
```

이 경우엔, `then()` 안쪽에 다른 큐가 생겨서 바깥쪽의 원리와 똑같이 작동한다고 한다.

```
get --> then --> [안쪽]console.log --> visit
```

이제 동작원리를 이해했으니 생각하면서 테스트 코드를 작성하도록 하자.



## 참고

* [Arnon Axelrod, Understanding Cypress’s command execution order and Chainables](https://medium.com/@arnonaxelrod/understanding-cypresss-command-execution-order-and-chainables-75079d82710a)
* [공식 문서, Introduction to Cypress](https://docs.cypress.io/guides/core-concepts/introduction-to-cypress.html#Cypress-Can-Be-Simple-Sometimes)

