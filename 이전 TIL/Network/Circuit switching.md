# Circuit Switching

Edge라고 부르는 여러개의 단말은 access network와 core network로 연결되는데 이 network들을 연결하여 경로를 만들어줄 때 사용하는 것이 switching technology이며 2가지 방법이 있다. 그 중 첫번째인 Circuit Switching에 대해 알아보자.

# 정의와 과정

Circuit Switching은 end system간의 연결에서 switching device(=switch)를 사용하며 각 switch간에 link로 연결된다. 각 link는 여러개의 circuit을 가질 수 있으며 end system간에 통신이 되고 있는 경우 다른 end system은 끼어들 수 없다. 쉽게 말해서 통화를 할 때 통화를 하고 있는 상대에게 전화를 걸면 통화중이라고 나오는데, circuit을 점유하고 있는 상태로 다른 사람이 끼어들 수가 없다는 것이다.

<img src="http://content.webclasses.net/images/in_VirtualCircuit.gif">

위와 같이 각 link에 정해진 circuit으로 path가 형성되고 간섭 없이 통신이 되기 때문에 보장된 통신이 가능하다. 또한 fixed rate로 전송하기 때문에 QoS가 보장된다. 그러나 그 전에 circuit을 setup하는 과정이 필요하다.

Circuit Switching은 3가지 단계로 이루어진다.

* **Circuit establishment(call setup)** : data를 보내기 전에 end-to-end circuit이 정해져야 한다.
* **Data transfer** : data가 해당 circuit을 통해 전송된다.
* **Circuit release(disconnect)** : data 전송이 완료되면 연결이 종료되고 resource는 반환된다.



# 멀티플렉싱

각 end 간의 link는 여러개의 circuit으로도 구성될 수 있으며, N개의 circuit으로 구성될 경우 해당 link의 bandwidth는 1/N이 된다. 예를 들어, link bandwidth가 1 Mbps인데 4개의 circuit으로 구성되었다면 각 link는 250 kbps 만큼 갖게 된다. 이 때 멀티플렉싱(Multiplexing)의 개념이 나오는데 **하나의 link로 여러개의 signal이 동시에 전송되는 것을 말한다.** 

> Set of techniques that allows simultaneous transmission of multiple signals across a single data link

멀티플렉싱에는 2가지 기법이 존재한다.

* **FDM(Frequency Division Multiplexing)** : 주파수 분할 방식으로 시간축을 공유하면서 다른 주파수로 circuit이 만들어지는데 주파수를 겹치지 않기 위해 변조(modulation)를 사용한다.
* **TDM(Time Division Multiplexing)** : 시간 분할 방식으로 하나의 대역폭을 시간 슬롯(time slot)으로 나누어 채널에 할당한다.

## FDM (주파수 분할 방식)

![default](https://user-images.githubusercontent.com/35518072/41910233-0e94a372-7984-11e8-9acb-d381f337883b.JPG)

상단 왼쪽의 전화기 3대를 A1,A2,A3라고 하고 하단 오른쪽의 전화기 3대를 B1,B2,B3라고 하자. 3명의 사람이 전화를 걸 때 사람의 목소리는 동일한 주파수 대역을 사용하기 때문에 그걸 0~4라고 하면 Modulator를 통하여 다른 주파수 대역으로 바꾸고 하나의 Higher-bandwidth link로 연결하여 전송한다. 전송되면 Bandpass filter를 통해 A1,A2,A3에 해당되는 주파수 대역을 뽑아내며 Demodulation을 통해 원래의 주파수 대역으로 변경하여 B1,B2,B3에 사람의 목소리가 들리게 되는 것이다.

##TDM (시간 분할 방식)

![default](https://user-images.githubusercontent.com/35518072/41910192-ee5cc33c-7983-11e8-992b-f3cd5793575b.JPG)

A,B,C라는 전화기로부터 전화를 건다고 하면 T라는 시간 단위로 각각의 음성을 분할한다. 이걸 멀티플렉서를 이용하여 쪼개진 시간단위를 각각의 frame으로 합치게 되면 각각의 frame은 3개의 time slot을 가지며 그 time solot은 T/3의 시간을 가지게 된다. 만약 시간단위 T로 분할된 음성을 1bit라고 하면 64kbps의 전송속도를 가지기 때문에 1/64k sec의 시간간격을 가진다고 볼 수 있다. 그러나 하나의 link로 frame 3개를 전달하게 되기 때문에 64kbps*3의 속도를 가져야 high rate로 전달할 수 있다. 물론 각 frame에서 A,B,C는 64kbps로 전송된다.



## 장점

* QoS(Quality of Service)를 보장한다.
  * 데이터가 고정된 비율로 전송된다.
  * delay가 거의 없다.

## 단점

* Resource의 사용이 비효율적일 수 있는데 resource를 전적으로 할당받기 때문에 traffic이 bursty 할 경우 (전송에 쉬는부분이 있을 경우) 쓰지 않는 resource가 있을 수 있다.
* Circuit establishment delay가 있다.



## 예제

640,000 bits 크기의 파일을 host A에서 host B로 전송할 때 circuit-switched network를 사용하면 얼마나 걸릴까?

* 모든 link는 1.536 Mbps이다.
* 각 link는 TDM을 사용하며 24개의 time slot을 갖는다.
* Circuit establishment delay는 500 msec이다.

모든 link가 1.536 Mbps인데 24개의 time slot을 갖기 때문에 1.536 Mbps/24 = 64kbps 의 속도로 분할된 파일을 전송한다. 총 640kbits를 전송하므로 640kbits/64kbps = 10s의 시간이 걸리고 circuit establishment delay가 0.5s이므로 10.5 s의 시간이 걸린다.