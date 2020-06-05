# Software Engineering Week 4 (2)

* **V-Model**
  * <img src="https://insights.sei.cmu.edu/assets/content/F1%20-%20Traditional%20V%20Model.jpg" width="500px">
  * Waterfall 모델의 다른 representation이며 devision과 integration으로 나뉘어진다.
  * Waterfall과 마찬가지로 문제를 중간에 발견해도 번복해서는 안되며 번복(redo)하기 위해선 문제가 발생한 시초부터 다시 진행해야 한다.
  * 따라서 flexible하지는 않지만 한 단계에 대해서 확실히 완료하기 때문에 reliable하다고 할 수 있다.
  * 각 step에서 results가 documentation되며 다시 integration(verification) 할 때 해당 document를 사용해서 decision of implementation을 진행한다.
  * Requirement와 해당하는 model을 associate하고 traceability가 좋으면 redo 할 때 어디를 해야 할지 알 수 있게 된다.
  * Redo 할 때는 그것이 design의 문제인지 implementation의 문제인지에 따라 어디서부터 redo 해야 하는지가 달라진다.
  * V-Model은 abstraction level을 나누어서 conceptual model에서 concrete model까지 modeling과 design작업을 하고 다시 역으로 construction과 verification을 한다.
  * Project가 uncertain 하면 이런 모델은 working하지 않으면 현재나 컨트롤러를 각각 다르게 생산하는 회사에게 very attractive하다.
  * 소프트웨어의 경우 frequency of change의 정도에 따라 적용할 수 있는지가 달라진다.
* **Drawbacks of Waterfall model**
  * All requirements를 모두 말하는 것이 굉장히 어렵다.
  * 중간에 변화가 발생하면 발생한 근원으로 다시 가야한다.
  * Working products (중간 작업산물)가 없다.
  * Framework activity의 workload가 다른데 모든 인력이 투입되서 menpower의 utilization이 떨어진다.
* **Incremental Process Model**
  * <img src="https://2.bp.blogspot.com/-Wz89LbcPUjM/WBF8hV2fM5I/AAAAAAAACuI/NiesLv2k2Ug9aif5ukvtt-kUJKwZfRjOQCLcB/s1600/ipm.jpg">
  * Project를 sub-project로 나눠서 waterfall 모델을 적용시킨 모델이다.
  * Incremental 하게 작업하기 때문에 working products를 볼 수 있다.
  * sub-project에 변화가 발생해도 global하게 propagate되진 않는다.
  * Concurrent하게 작업을 진행할 수 있다.
  * 잘하는 팀이 작업을 끝냈을 경우 menpower를 다른 작업에 사용할 수 있다.
* **Drawbacks of Incremental Process Model**
  * Each sub-project is still waterfall model
  * Project가 independent pieces로 쉽게 구분될 수 있을 때만 사용할 수 있다.
  * 나눴던 sub-projects를 integrating하는데 overhead가 크다.
  * 각 incremental 단계에서 program을 구현했는데 동작하지 않을 수 있다.
  * Project가 완전히 sub-project로 쪼개지는 경우가 없을 수 있다.
  * 첫번째 iteration이 끝나면 low quality라도 어떤 면에서 모든 특징들을 포함하고 있어야 하기 때문에 가짜 DB를 쓰는 등의 mocking을 쓰지만 risky하고 misleading이 될 수 있다.
* **Spiral Model**
  * <img src="https://csharpcorner-mindcrackerinc.netdna-ssl.com/UploadFile/2cb323/software-development-life-cycle/Images/Spiral%20model%20.jpg">
  * Multiple circuits을 구성하여 각 circuit마다 all different activities를 포함하게 한다.
  * Communication부터 Deployment까지 한 사이클을 거친다음 customer의 feedback을 받고 다시 circuit을 도는 과정을 반복한다.
  * Risk를 일찍 만나기 때문에 more reliable하고 predictable하다. → Rapid and incremental development가 가능하다.
  * Frequent and solid communication with the stakeholders가 굉장히 중요한데, 원하는 것을 알아야 해당 기능을 추가할 수 있기 때문이다.
  * Prototype 또한 communication을 보다 쉽게 만들어주기 때문에 중요하다.
  * Anchor point milestone은 하나의 circuit이 끝나는 지점을 말해준다.
  * Military system과 같은 high-risk project를 위해 사용된다.
* **Drawbacks of Spiral model**
  * Frequent communication 으로 인해 고객이 많은 것을 요구할 수 있다.
  * Prototyping을 해주게 되면 그 프로토타입만 쓴다고 하는 사람들이 있다.
  * Rigorous한 대신 management cost가 많이 드는 expensive한 모델이다.
* **SPI(Software Process Assessment and Improvements)**
  * <img src="http://itfaat.com/wp-content/uploads/2016/10/maturity_ladder_img.png">
  * Activities of software process의 quality에 대한 평가표준
  * 여러가지 framework가 존재하며 정부가 이런 걸 많이 요구한다.
  * Maturity level에 따라 해당 project의 소프트웨어 프로세스가 얼마나 체계적인가를 보여준다.