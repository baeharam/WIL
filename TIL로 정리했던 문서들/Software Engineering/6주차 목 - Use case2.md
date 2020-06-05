# Software Engineering Week 6 (2)

* **Generic Structure of UC**
  * Without any structure, it is fine, but template is good.
  * <u>Name</u>: Summary, "Verb Noun" phrase like commit message of git
  * <u>Description</u>: high-level 1 paragraph
  * <u>Actors</u>: list of actors that trigger any UC
  * <u>Pre-conditions</u>
  * <u>Post-conditions</u>
  * <u>Base scenario</u>(sunny-day scenario): no alternative, no error and everything is alright.
  * <u>Alternative scenarios</u>: branching scenarios
  * <u>Additional information</u>: for the clear communication
* **EX) Home Security System**
  * <img src="https://user-images.githubusercontent.com/35518072/55774223-41317080-5acf-11e9-9bc9-42bc09a5d60e.PNG">
  * 폭넓은 지식을 가진 사람이 requirements를 더욱 잘 작성한다.
  * Discussion session을 통해 brain-storming을 하고 UC에 대한 여러 sentences를 만든다.
  * 이후 redundancy를 제거하면서 특정한 UC를 하나 선택하여 drafting을 통해 생각나는 대로 작성한다.
    * Drafting을 한 rough한 scenarios는 template을 통해 체계적으로 만들 수 있다.
  * 최대한 linear하게 base-line(main) scenario를 작성하고 나서 나중에 exceptional(alternative) cases를 작성하게 되면 훨씬 더 concise하게 작성할 수 있다.
    * Exceptional cases ↑↑ Robustness ↑↑
    * 추가적인 세부사항들 또한 달 수 있다.
  * Requirements의 priority를 정해서 priority가 낮은 것은 따로 뺄 수 있다.
  * <u>Open issues</u>
    * 비밀번호 없이 사용하거나 짧은 비밀번호를 통해 사용할 수 있는가?
    * 제어패널이 추가적인 텍스트 메시지를 나타낼 수 있는가?
    * 비밀번호의 1번째 키가 눌린 후에 모든 비밀번호가 눌릴 때까지 시간이 얼마나 주어지는가?
    * 시스템이 확실히 활성화되기 전에 비활성화 시킬수 있는 방법이 있는가?
    * 위의 이슈들을 보면서 ambiguity를 제거하거나 improvement idea를 캐치할 수 있으며 사업적으로 가치가 있는지 business reading을 할 수 있다.