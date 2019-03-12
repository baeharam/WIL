# Software Engineering Week 3 (1)

* 소프트웨어의 속성은 스스로 정의할 수도 있다.
* **Performance**
  * Correctness의 관점으로 굉장히 중요하다.
  * 여기서의 리소스는 시간에 관련된 것을 의미한다.
  * Electrical Power, Time, Memory, Network Bandwidth, Human Resource
  * 리소스는 한번만 사용할 수 있는 것. (something consumable)
  * 소프트웨어 관점에서의 리소스를 말하는 것.
  * 같은 양의 리소스에서 얼마나 효율적으로 사용할 수 있는가, Efficiency
  * 리소스를 더 추가했을 때 더 나은 결과를 낼 수 있는가, Scalability
    * 병목지점(bottleneck)이 있으면 scalability를 쉽게 성취할 수 없다.
    * 학사시스템에서 사용자가 증가할수록 scalable해야 하는 경우는 수강신청 경우밖에 없기 때문에 보통은 scalable하지 않게 만든다.
  * To evaluate performance quality
    * Measurement가 상당히 힘든데 그 이유는 사용자의 요청이 얼마나 될지 모르기 때문이다.
    * 소프트웨어를 모니터 할 때도 로깅을 하기 때문에 퍼포먼스가 다시 변한다. (예를 들어 printf가 system call을 포함하기 때문이다.)
    * 서비스를 런칭하기 전에 미리 예측해야 한다. 따라서 베타 서비스를 시작한다.
    * Analysis를 통해서 코드의 구조를 확인한다. (Worst Case of Execution Time = WCST 측정, 임베디드)
    * Simulation을 사용하면 여러가지의 시나리오를 확인할 수 있다.
* **Usability**
  * 정량화 할 수 있는가? 
    * 사용자가 5초 안에 망설임 없이 사용할 수 있는가?
    * 클릭 횟수
    * Brain Scanner를 달아서 ㅋㅋㅋㅋ brain workload 측정
  * Microsoft에선 Windows에서 뭐하는지를 측정할 수 있다.
  * OneNote에선 메모를 해야 하는데 어디다 저장을 하는지 묻고 있다.
  * 무조건 쓰게 하고 로그인 해야 한다.
  * Consistency(일관성)와 Predictability(예측가능성)에 의존한다.
  * 서비스를 주기 위해서 UI를 바꾸게 되었는데 usability에 대한 모델링이 먼저 있어야 한다.
* **Verifiability**
  * 시스템을 테스트하기 쉬운가
  * Regression test suite: 소프트웨어를 시간이 지남에 따라 고치거나 업데이트 하는데 이에 따른 테스트 케이스를 만들어서 테스트를 해야 하며 이것들을 전부 모아줘야 한다.
  * 모든 나중의 커밋들은 이전의 테스트 케이스를 해쳐서는 안된다. 만약 해치게 되면 버그를 고치다 버그를 다시 만드는게 된다. 그래서 어떠한 업데이트에 대해서 변화를 기록해야 하며 이후의 업데이트에 대해선 해당 변화를 고려해야 한다.
  * Intangible software를 a little bit tangible하게 만드는 것이다. (redundancy를 줌으로써)
  * 의도적인 redundancy를 줘서 consistency를 체크한다음 소프트웨어가 쉽게 바꿀 수 없게 한다.
  * Assertion: Executable specfication in the code, functionality perspective에선 문제가 없다.
  * Secure coding: `fopen()`을 호출하면 같은 block에서 `fclose()`를 호출해야한다. (또한 가장 바깥쪽의 block) 이것은 기능을 제한시킨다.
  * 은행 같은 곳에서는 `malloc`을 못쓰게 하며 리눅스 커널에선 재귀함수 호출이 불가하다.
* **Maintainability**
  * 소프트웨어의 변화는 항상 일어나며 소프트웨어 개발은 변화의 연속이라고 할 수 있다.
  * Modification
    * Perfective change, 소프트웨어를 더욱 완벽하게 만드는 것이다.
    * Corrective change, 소프트웨어의 버그를 고치는 것이다.
    * Adaptive change, 소프트웨어를 새로운 요구조건(requirements)에 맞춰서 변화시키는 것이다.
  * 소프트웨어를 완벽하게 만드는 것이 실력이며 90%까지는 쉽다.
  * Reparabiliy, **모듈화가 잘 된 설계**라면 버그를 isolate시켜서 쉽게 판단할 수 있다.
  * 대부분의 못 고치는 버그는 모듈화가 잘못된 경우이다. (조심하자!!)
  * Evolvability, 항상 나중에 일어날 변화를 생각해야 한다.
  * 예를 들어, MySQL DB를 사용한다고 했을 때 이걸 다른 DB로 옮길 미래를 고려해야 한다는 것이다.
* **Software Process**
  * 좋은 소프트웨어 시스템은 좋은 소프트웨어 프로세스로 이어진다.
  * 물론 단순히 좋은 소프트웨어 시스템을 공예(crafting)로 만들 수 있지만 이것은 공학(engineering)이 아니다.
  * Timeliness, 일정을 맞출 수 있는가? Visibility, 모든 프로세스가 눈에 보이는가?