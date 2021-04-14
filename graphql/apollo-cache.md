## 아폴로 캐시 동작 원리

### keyFields

캐시의 identifier 는 기본적으로 `__typename + id` 이지만 이걸 커스텀 할 때 사용하는 배열이다. 해당 쿼리에서 어떤 필드를 커스텀하게 사용할 것인지를 결정할 수 있고 `false` 로 할 경우 **정규화를 무효화한다.**

### [keyArgs](https://www.apollographql.com/docs/react/caching/cache-field-behavior/#specifying-key-arguments)

기본적으로 캐시는 특정 필드를 쿼리할 때 조합되는 유일한 매개변수 조합에 대한 각각의 값을 독립적으로 저장한다. 이 말은, 매개변수 중에서 특정 값이 원하는 결과를 결정할 수 있는 것을 알면 이해할 수 있다. 예를 들어 보자. 쿼리를 날려서 `monthForNumber` 라는 필드를 가져오는데 매개변수로 `number` 라는 걸 넘긴다고 하면 `monthForNumber` 를 계산하는 매개변수는 `number` 이다. 근데 이 때 추가적인 매개변수로 `accessToken` 을 넘긴다고 해도, 이 토큰은 `monthForNumber` 를 계산하는데 필요가 없다. 따라서, 여기서의 `keyArgs` 는 `["number"]` 가 되는 것이다. 이렇게 하면 토큰이 다르다 해도 매개변수 조합에 포함이 되지 않기 때문에 불필요한 데이터 중복을 피할 수 있다.

배열 뿐만 아니라 더 복잡한 동작을 정의하고 싶다면, 함수를 사용할 수도 있다.

