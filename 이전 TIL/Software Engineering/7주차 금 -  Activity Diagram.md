# Software Engineering Week 7 (2)

* **왜 activity diagram을 사용하는가?**
  * Want to express sequence of actions
  * Standardized diagram for clear communication.
* **Example 1, basic**
  * <img src="http://www.c-jump.com/CIS75/Week10/images/activity_process_order.png">
  * Initial/Final activity가 존재.
  * Rounded rectangle: Activity
  * Diamond-like: Decision, Alternative
  * Bar

    * Fork, control flow is spawned. alternative가 아니라 2개로 갈라지는 것.
    * Join, 2개의 control flow가 모두 도착해야 합쳐진다.

  * Single timeline을 가지며 그 흐름은 top to bottom이다.
  * Vertical이 아닌 Horizontal로 그려도 상관없다. 말했다시피 clear communication을 위함!!
  * Use case diagram과는 독립적인 것으로 sequence of action을 나타내는 것이다.
  * Exceptional cases에 대한 action또한 표현할 수 있다. (예를 들면 decision으로)
    * Merge는 Join과 같은 개념이 아닌 flow control을 simplify하기 위함이다.
* **Example 2, Subactivity and Object Node**
  * <img src="http://img.it610.com/image/product/677326009ca345acb794ec469803e479.jpg">
  * <img src="http://img.it610.com/image/product/acb5b31484794a939737d74b44c72636.jpg">
  * Example 1에서는 data flow를 표현하지 못했지만 여기선 rectangle이 data container를 나타내기 때문에 explicit하게 나타낼 수 있다는 장점이 있다.
  * 이렇게 subactivity diagram으로 분리시키면 이해하기가 쉬워지며 data flow를 나타낼 수 있다.
  * 위 그림에선 없지만 data를 받는 port 표시 또한 존재한다.
* **DFD (Data Flow Diagram)**
  * <img src="https://user-images.githubusercontent.com/35518072/56075548-33495b80-5dff-11e9-8d00-515537110352.png" width="300px">
  * 데이터의 흐름만을 나타내기 위한 그림으로 시간축이 존재하지 않는다.
  * 프로세스들끼리 직접적으로 데이터를 주고받을 수 없다.
  * 프로세스는 원 모양으로, 데이터 컨테이너는 사각형 모양으로 표시하며 프로세스가 데이터를 read/write/generate 한다.
  * 시간축이 존재하지 않기 때문에 time-insensitive 하며 temporal order를 강조하지 않는다고 할 수 있다.
  * Activity diagram은 시간축이 존재하기 때문에 DFD와 같은 static structure를 표현하지 못한다.
* **Example 3, Partition**
  * <img src="http://www.jot.fm/issues/issue_2004_07/column4/images/fig1.gif">
  * Partition은 swimlane이라고도 하며 **"who does what"**을 보여주기 위한 목적, 즉 프로세스를 실행하는 주체가 누구인지에 대한 정보를 제공해준다.
  * 위의 예시를 보면 Fulfillment(이행), Customer Service(고객 서비스), Finance(금융)의 3가지 partition으로 나뉘었으며 프로세스가 각각의 partition으로 쪼개진 것을 볼 수 있다.
  * 만약 행하는 주체가 1명이라면 partition을 나눌 필요가 없다.
* **Example 4, Signals (Signal based activity)**
  * <img src="http://img.it610.com/image/product/48ac1a616ff64da190391bc465936ce1.jpg">
  * 특정한 신호(signal)가 발생함에 따라서 activity가 실행되는 경우도 존재한다.
  * 위 예시에선 비행기 타기 2시간 전이라는 time signal이 존재하며 그 signal에 따라서 가방을 챙긴다.
  * 또한 택시가 도착하는 외부 signal이 존재하여 join을 통해 가방을 챙겼고, 택시가 도착했으면 공항으로 떠나는 activity를 실행하게 된다.
  * 여기서 주목할 점은 signal이 외부에서 올 수 있다는 점이다.
* **Example 5, Send/Receive Signals**
  * <img src="http://img.it610.com/image/product/bb766361ce10492bb64ca584f9e796b8.jpg">
  * 위 예시에선 signal을 보내기도 하고 받기도 한다.
  * 여행 일정표(itinerary)를 예약하면, 보내는 signal이 보내지고 거기서 fork되서 여행 일정표가 확인되는 signal과 time signal로 나뉘어진다.
  * 여기서 주목할 점은 fork가 되었지만 join하지 않고 먼저 final activity에 도착하는 activity가 dominate하게 된다는 것이다.
  * 즉, 여행 일정표가 확인되면 예약을 하지만 48시간을 기다려도 확인이 안되면 취소하게 된다.
* **Practice, Vending Machine**
  * Main Scenario
    * 고객이 기계에 온다.
    * 고객이 기계에 동전을 넣는다.
    * 고객이 물품을 고른다.
    * 기계가 물품을 준다.
    * 기계가 잔돈을 반환한다.
  * Extension
    * 고객이 언제 "반환" 버튼을 누르던지 기계는 모든 동전을 반환한다.
    * 고객은 신용카드를 사용할 수 있으며 기계는 신용카드의 유효성을 판단한다.
    * 고객이 1분내로 어떤 버튼도 누르지 않으면 기계는 모든 동전을 반환한다.
    * 동전이 남아있지 않거나 신용카드를 사용한 경우라면 동전을 반환하지 않는다.
  * 특별한 정답이 있는 것은 아니니 표현방식(notation)만 지켜서 sequence of actions를 activity diagram으로 나타내도록 하자.