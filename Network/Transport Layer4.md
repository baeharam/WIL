[한양대학교 네트워크 강의](http://www.kocw.net/home/search/kemView.do?kemId=1169634)와 [Top-down approach](http://www.bau.edu.jo/UserPortal/UserProfile/PostsAttach/10617_1870_1.pdf)를 정리 및 참고한 것입니다.

# Congestion

네트워크 상황을 알 수 있는 방법은 직관적이지 않다. 물론 라우터가 알려줄 수는 있지만 패킷 포워딩을 하는 것이 핵심 역할이다. 이렇게 네트워크 상황을 알기는 쉽지 않기 때문에 짐작을 해야 한다. 비공식적인 정의는 다음과 같다.

> too many sources sending too much data too fast for network to handle

이러한 network congestion이 발생하면 2가지 문제점이 생긴다.

* 라우터의 버퍼가 오버플로우 돼서 패킷 유실이 발생한다.
* 라우터의 버퍼가 꽉 차서 queueing delay가 발생한다.



# Congestion이 발생하는 상황 1

* 2개의 sender가 있고 라우터가 무한대 크기의 버퍼를 가지고 있음 (비현실적)
* 패킷 유실이 없으므로 재전송 없음 (비현실적)
* badnwidth를 R이라고 함

<img src="https://user-images.githubusercontent.com/35518072/43119901-3b6d8726-8f53-11e8-8b58-c3a63b1f39bf.png" width="600px">

A와 B는 R의 bandwidth를 가진 회선을 공유한다. 여기서 sending rate를 $\lambda_{in}$이라 하고 receiving rate를 $\lambda_{out}$이라고 하면 throughput과 delay를 알 수 있다. Throughput이란 단위 시간당 전송되는 데이터의 양을 말한다. Sending rate가 증가할수록 receiving rate는 당연히 증가할텐데 bandwidth가 R이기 때문에 최대 R/2까지 증가하고 유지된다. 이는 회선을 공유하기 때문에 당연한 것이다. 하지만 delay의 경우는 라우터의 버퍼가 무한대이기 때문에 sending rate는 최대 R/2이겠지만 delay는 무한정으로 증가한다.

![image](https://user-images.githubusercontent.com/35518072/43120209-60ee17e4-8f54-11e8-8938-515fea058450.png)

이런 비현실적인 상황에서도 데이터를 굉장히 빨리 보내는 상황에선 delay가 상당히 늘어나게 된다. 다음으로는 조금 더 현실적인 상황을 생각해보자.



#Congestion이 발생하는 상황 2

* 라우터가 제한된 버퍼 사이즈를 가짐
* Sender는 타임아웃이 되면 TCP처럼 재전송을 한다.

<img src="https://user-images.githubusercontent.com/35518072/43120426-1b8f135a-8f55-11e8-86d9-4cd1f42027b9.png" width="600px">

위 조건에선 패킷 유실이 발생할 수 있으므로 재전송을 하는 경우가 있다. Sending rate의 최댓값은 R/2인데 transport layer에서 재전송할 데이터가 있을 경우 보낼 데이터 중에 일부분을 재전송(중복) 데이터로 채워야 하기 때문에 receiving rate는 떨어지고 throughput을 손해본다고 할 수 있다.

여기서 좀 더 현실적으로 보면 congestion 때문에 버퍼(큐)에서 기다리는 패킷들이 많아지면 delay가 생기기 때문에 타임아웃이 패킷유실을 확인하기 전에 발생할 수 있다. 그러면 패킷유실이 되지 않았는데도 의미없은 재전송 데이터를 보내는 경우가 생기므로 더욱 안 좋다고 할 수 있다.

![image](https://user-images.githubusercontent.com/35518072/43120708-2cb34a1a-8f56-11e8-9352-686a5eea5f4b.png)

위 그래프와 같이 재전송 데이터를 포함한 sending rate인 $\lambda'_{in}$에 따라 receiving rate가 줄어듦을 알 수 있다.



#Congestion이 발생하는 상황 3

보다 현실적인 모델을 보자. 이번엔 라우터가 여러개있는 경우이다.

<img src="https://user-images.githubusercontent.com/35518072/43120976-17a32824-8f57-11e8-9da0-64672630f2af.png" width="600px">

A는 C에게 데이터를 보내고 있는데 sending rate가 점점 올라간다고 해보자. 라우터 R1의 버퍼는 A의 데이터로 전부 차게 된다. 하지만 D도 R1을 이용해서 B에게 데이터를 보내고 있다. 이 경우, D의 데이터는 R1의 버퍼가 가득 찼기 때문에 버려지게 된다.

네트워크 용어로 **업스트림(Up stream)**과 **다운스트림(Down stream)**이란 용어가 있는데 클라이언트와 서버가 통신한다고 했을 때 클라이언트 쪽은 업스트림에 해당하고 서버쪽은 다운스트림에 해당한다. 여기선 D에서 B로 데이터가 전송되기 때문에 D방향이 업스트림이고 B방향이 다운스트림인데, R1에서 데이터가 전부 유실되기 때문에 R4를 거쳐서 R1에 도달했지만 아무 의미없게 되는 것이다. 업스트림에서 경쟁을 통해 여러 자원들을 사용했지만 아무 역할을 하지 못하고 유실되었기 때문에 재전송을 해야되고 결국 throughput이 줄어들게되는 문제가 발생한다.

이제까지 배웠던 TCP는 RDT를 제공하기는 하지만 congestion을 고려하지 않고 데이터를 받을 때까지 전송하기 때문에 throughput 문제가 발생하는 것이다. 그렇다면 congestion을 고려해서 데이터를 보내야 하는데 congestion을 어떻게 고려해야 할까? 2가지 방법을 생각할 수 있다.

* **네트워크를 보는 것이 아니라 양쪽 TCP 소켓이 네트워크 상황을 짐작하는 방법**
* 네트워크의 라우터들이 네트워크 상황을 알려주는 방법

2번재 방법이 정확하겠지만 오버헤드가 크고 라우터의 실질적인 역할이 패킷포워딩이기 때문에 보통 1번째 방법을 사용한다. 이제 congestion control에 대해 제대로 알아보자.



# TCP congestion control

양쪽의 end system이 네트워크 상황을 짐작하는 방법은 간단하게 피드백을 활용한다.

* **Segment를 보냈는데 ACK가 제한시간안에 왔다.**

네트워크 상황이 괜찮다는 말이기 때문에 원래의 window size를 조금씩 증가시킨다. 갑자기 확 증가시키면 congestion이 발생할 수 있으므로 조금식 올리는데 이걸 **additive increase**라고 한다.

* **Segment를 보냈는데 ACK가 제한시간안에 오지 않았다. (비상사태)**

Congestion이 발생했을 수도 있는 비상상황이기 때문에 window size를 한번에 반으로 줄인다. 이걸 확 줄여버리기 때문에 **multiplicative decrease**라고 한다.

위 2가지 경우를 고려해서 congestion control을 하며 아래의 그래프 처럼 이루어진다.

![image](https://user-images.githubusercontent.com/35518072/43121739-c0f76f82-8f59-11e8-865c-68578eca52d2.png)

위와 같이 congestion window size는 네트워크 상황에 따라서 dynamic 하게 변화한다. 이걸로부터 sending rate를 추측할 수 있는데, congestion window size를 cwnd라고 하면 보낸 데이터에 대해서 ACK를 받아야 다시 보내는 것이 TCP이기 때문에 다음에 근접한다고 할 수 있다.
$$
Sending  Rate \approx \frac{cwnd}{RTT}
$$


# 시작할 때의 congestion control

TCP connection이 이루어지고 데이터를 보내려고 할 때 처음의 congestion window size는 몇으로 잡아야 할까? 네트워크 상황을 모르기 때문에 단순하게 1개부터 시작한다.

<img src="https://user-images.githubusercontent.com/35518072/43122279-805a0154-8f5b-11e8-98d5-db473d9347d9.png" width="300px">

이렇게 처음에는 1개부터 시작하는 것을 slow start라고 하며 경험적으로 설정된 threshold(한계값) 까지는 exponential하게 증가하다가 그 이후부터 아래 그래프처럼 linear하게 증가하게 된다.

<img src="https://user-images.githubusercontent.com/35518072/43122341-a1336d8e-8f5b-11e8-90b5-84f396d4634c.png">

위 그래프를 보면 ssthresh라는 것이 있는데 slow start threshold로 여기서부턴 congestion이 발생할 수 있으니 조심해야 하는 것을 뜻한다. 처음엔 1, 2, 4, 8로 exponential하게 증가하지만 ssthresh가 8로 설정되어있기 때문에 여기서 12까진 linear하게 증가한다. Congestion이 발생할 수 있기 때문에 조심해서 증가시키는 것이다. 하지만 12에서 congestion이 발생해서 cwnd를 한번에 내렸다. 이 경우에는 패킷 유실로 판단할 수 있으며 패킷 유실은 2가지로 알 수 있다.

* **3 duplicate ACKs**
* **ACK를 받지 못하고 타임아웃**

하지만 2가지 경우에 각각 네트워크 상황을 짐작하는 방법이 다르다. 3 duplicate의 경우는 특정 패킷에 대한 ACK가 중복되서 오는 것이기 때문에 그 패킷이 운이 좀 나쁘다고 생각할 수 있지만 타임아웃의 경우는 보낸 패킷들에 대한 ACK가 안오는 상황, 즉 3 duplicate 조차 오지 않는 상황이기 때문에 훨씬 심각한 것으로 보게 되는 것이다.