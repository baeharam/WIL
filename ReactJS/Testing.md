* **테스트의 종류**
  * Unit Test
  * Integration Test
  * E2E Test (Browser Test)
* **JS 테스팅 프레임워크와 라이브러리**
  * 테스트를 자동화시켜주고 어떤 점이 잘못되었는지 상세하게 알려준다.
  * Jest는 Test Runner, Enzyme은 Testing Utilities ([참고](https://stackoverflow.com/a/49788980/11789111))
* **Enzyme**
  * `mount` : 자식 컴포넌트까지 모두 렌더링을 한다.
  * `shallow` : 현재 컴포넌트만 렌더링을 한다.
  * `toMatchSnapshot()` : 이전 렌더링 결과와 현재 렌더링 결과가 같은지 스냅샷 테스팅을 한다.