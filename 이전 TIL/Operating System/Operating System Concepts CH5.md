# Operating System Concepts 5장

## 5.1 기본 개념(Basic Concepts)

### 용어정리

* CPU burst : CPU 연산을 하는 구간
* I/O burst : I/O 연산을 하는 구간

모든 program은 CPU burst와 I/O burst의 반복이며 program의 시작과 끝은 항상 CPU burst이다.

짧은 CPU burst가 많은 process는 계속 I/O로 전환되기 때문에 I/O bound job이며 긴 CPU burst가 적은 process는 CPU 연산을 길게 하기 때문에 CPU bound job이다.

### Ready queue의 구현 형태

구현은 OS의 Algorithm에 따라 다르지만 전형적인 data structure가 존재한다.

* FIFO queue : 먼저 들어온 job부터 준다.
* Priority queue : 정해진 기준에 따라 준다. (heap으로 구현한다)
* tree / linked list

모두 내부적으로는 queue를 구현한다.

### CPU scheduling 결정 시점

1. <u>Process가 running에서 waiting으로 state transition이 일어날 때</u>

I/O 연산이나 child process의 status change를 기다리기 위해 wait() 함수를 호출하는 경우이다.

2. <u>Process가 running에서 ready로 state transition이 일어날 때</u>

자기 자신은 계속 수행할 수 있지만 보다 더 긴급한 process를 수행하기 위해서나 공평한 수행을 위한 경우이다. 예를 들어 interrupt와 time sharing에서 자기에게 할당된 시간인 time slice가 종료되는 것들이 있다.

4. <u>Process 종료</u> (1가지 더 있지만 고려하지 않는다.)

여기서 가장 중요한 것은 각 번호에 따라 scheduling에 이름이 정해지는 것을 기억해야 한다는 것이다.

* **non-pre-emptive scheduling** : 1번과 4번에 의해서만 scheduling되는 방식을 말하며 자신이 resource를 다 쓰기 전까지는 resource를 놓지 않는다.
* **pre-emptive scheduling** : 1번, 2번, 4번에 의해서 scheduling되는 방식으로 자신이 resource가 필요해도 양보한다.

### Preemptive scheduling

* Time sharing system에 적용한다. (자신의 주기가 다 되면 cpu를 놓는 것)
* 어떤 상황에서 멈출지 모르기 때문에 data consistency 유지를 위한 cost가 필요하다.
* process가 공유하는 kernel data 같은 경우에는 어떤 process가 연산을 반만 하다가 놓을 수도 있으므로 위와 같이 inconsistency 방지 대책이 필요한데 이걸 <u>Critical section problem</u>이라고 한다.
* Real-time system에 부적합한데, 빠른 응답시간이 필요하기 때문!!

### Dispatcher

ready에서 running 상태로 오는 과정에 관여하고 dispatch에 걸리는 시간을 **dispatch latency** 라고 하며 dispatcher의 기능은 다음을 포함한다.

* context switching
* user mode로 전환
* user program의 적절한 위치로 Jump





## 5.2 스케줄링 기준(Scheduling Criteria)

스케줄링 알고리즘은 다양하며 목적에 따라 어떤 알고리즘을 사용할 것인지 결정해야 하는데 그러기 위해선 비교를 해야 한다. 비교를 정하는 기준에 따라 어떤 알고리즘이 가장 좋은지에 대해 상당한 차이를 보일 수 있다. 다음이 스케줄링 알고리즘을 비교하는 기준이다.

1. **Maximize 대상**
   * CPU utilization
   * Throughput : 일정 시간동안 완료된 process 수
2. **Minimize 대상**
   * <u>Turn-around time</u> : process의 submission 부터 completion 까지 모든 waiting time을 포함하는 시간, 따라서 이 시간이 적을수록 좋은 데 실질적인 것은 waiting time이다. 그 이유는 실행 시간은 프로그램 논리에 의한 것이기 때문에 os가 관여할 수 없다.
   * <u>Waiting time</u> : ready queue에서 CPU 할당을 기다리는 시간, 알고리즘으로 좌우될 수 있는 시간이다.
   * <u>Response time</u> : submission 부터 first response가 올 때까지의 시간, interactive/real-time system 등에서 중요한 요소이다.

### Optimize(어떤 척도를 최적화 시킬 것인가?)

* Average measure (평균 강조)
* Minimum measure (최소 강조, 최악의 경우를 의미한다.)
* Maximize measure (최대 강조, 안 쓰일 것 같지만 현실에서 은근히 이 척도가 많음)

무엇을 추구하느냐가 아니라 어떤 척도를 추구하느냐의 문제인 것이다. 예를 들어, maximum response time을 minimize하는 것은 **minimum measure** 이다. 그 이유는 최악의 경우에도 response time이 이 정도라고 말하는 것과 같기 때문!



## 5.3 스케줄링 알고리즘(Scheduling Algorithm)

### 1. FCFS(First Come First Served) Scheduling

FCFS는 non-preemptive scheduling으로 원하는 CPU burst를 다 쓰고 CPU를 놓는 scheduling이다.

| Process | burst time(ms) |
| ------- | -------------- |
| p1      | 24             |
| p2      | 3              |
| p3      | 3              |

> 다음 process들이 동시에 도착하고 순서는 p1-p2-p3라고 한다면 waiting time은 먼저 p1은 기다리지 않으니 0이고 p2는 p1의 burst time인 24만큼 기다리고 p3는 p1+p2의 burst time인 27만큼 기다린다. 따라서 (0+24+27)/3을 해서 17이 답이 된다.

매우 간편하지만 너무 긴 작업을 먼저하고 있는 경우 짧은 작업을 먼저 하고 싶어도 불가능하다. 이러한 FCFS의 단점을 **Convoy effect** 라고 하는데 짧은 process들이 긴 process를 호위하고 있는 것 같아 붙여진 이름이다. **짧은 burst를 갖는 많은 process가 1개의 긴 burst를 갖는 process의 수행 종료를 기다리는 현상** 을 말하며 average waiting time이 증가하게 된다.

### 2. SJF(Shortest Job First) Scheduling

엄밀하게 말하면 process의 next CPU burst를 보고 선택하기 때문에  Short next CPU burst scheduling이라고도 불린다. 

* 이것 또한 non-preemptive scheduling으로 더 짧은 process가 들어와도 양보하지 않는다.
* 정확한 burst를 알기 어렵기 때문에 구현이 어렵고 추정(estimation)해야 한다.

추정한다는 것은 $t_n$이 $n$번째 burst이고 $\tau_n$이 $n$번째의 예측값이라면 이 용어들의 의미는 $\tau_n$만큼 예측했는데 실제 burst는 $t_n$이라는 뜻이다. 따라서 $\tau_{n+1}=\alpha t_n + (1-\alpha)\tau_n$에서 $\alpha$값을 설정하여 예측값을 재조정한다. 계속해서 예측값을 조정하기 때문에 $\alpha=0$ 이라면 $\tau_{n+1}=\tau_n$이므로 최근의 burst는 아무 영향을 주지 않고 $\alpha=1$ 이라면 $\tau_{n+1}=t_n$ 이므로 최근의 burst로만 추정한다. 보통 $\alpha=\frac{1}{2}$를 많이 사용하며 이 때 *exponential average(지수 평균)* 을 보여준다. 

### FCFS vs SJF

| process | burst time(ms) |
| ------- | -------------- |
| p1      | 6              |
| p2      | 8              |
| p3      | 7              |
| p4      | 3              |

FCFS는 그냥 순서대로 계산하면 되기 때문에 $\frac{0+6+14+21}{4}=\frac{41}{4}$ 가 된다.

SJF는 burst time을 보고 짧은 것 부터 순서를 매기면 p4-p1-p3-p2이므로 $\frac{0+3+9+16}{4}=\frac{28}{4}$ 가 된다. 추가설명을 하자면 p4는 안 기다리고, p1은 p4의 burst인 3만큼, p3는 p4+p1의 burst인 9만큼, 마지막으로 p2는 p4+p1+p3의 burst인 16만큼 기다린다.

### 3. SJF의 변형 : Preemptive SJF (=SRT)

원래의 SJF는 non-preemptive로 아무리 더 짧은 process가 들어와도 CPU를 양보하지 않는다. 하지만 변형한 것으로 preemptive scheduling을 이용하는 SJF가 있다. 

이 방법을 사용하기 위해선 남은 수행시간을 봐야하기 때문에 *Shortest Remaining Time(SRT)*라고도 한다. 남은 시간을 절대적으로 비교해서 양보하는 개념이다. 

그렇다면 SRT가 SJF보다 항상 좋을까? Context switching time 때문에 시간이 더 걸릴 수 있다. 한번 예제로 이해해보자. 좀 헷갈리기 때문에 자세히 보아야 한다.

| process | arrival time | burst |
| ------- | ------------ | ----- |
| p1      | 0            | 8     |
| p2      | 1            | 4     |
| p3      | 2            | 9     |
| p4      | 3            | 5     |

####**1) SJF**

뭔가 글로 이해하기가 어려워서 찾아봤더니 표를 재배치하는 것이 훨씬 쉽게 푸는 것이었다. (Gantt Chart 쓰는 것이 제일 편함..) 제일 먼저 시작하는 p1을 제외하고 나머지를 burst 오름차순으로 정렬하면,

| process | arrival time | burst |
| ------- | ------------ | ----- |
| p1      | 0            | 8     |
| p2      | 1            | 4     |
| p4      | 3            | 5     |
| p3      | 2            | 9     |

로 다시 나오고 여기서 계산해보면 다음과 같이 진행된다.

1. p1은 제일 먼저 들어오니 waiting time = 0
2. p2는 p1이 끝나고 시작되니까 8인것 같지만 도착시간이 있으므로 waiting time = 8-1
3. p4는 p1,p2가 끝나고 시작되니까 12인것 같지만 도착시간이 있으므로 waiting time = 12-3
4. p3는 p1,p2,p4가 끝나고 시작되니까 17인것 같지만 도착시간이 있으므로 waiting time = 17-2
5. ​

따라서 $\frac{0+(8-1)+(12-3)+(17-2)}{4}=\frac{31}{4}$ 가 된다.

####2. SRT

이건 정말 이해하기가 어려웠다. FCFS나 SJF는 계산이 되게 간단한데 이건 arrival time이랑 burst를 잘 보면서 계산해야 한다. 위의 테이블을 한번 더 보자.

| process | arrival time | burst |
| ------- | ------------ | ----- |
| p1      | 0            | 8     |
| p2      | 1            | 4     |
| p3      | 2            | 9     |
| p4      | 3            | 5     |

이번엔 burst가 짧은 것이 기준이긴 하지만 더 짧은 burst가 나타나면 주는 preemptive scheduling이다. Remaining time이 기준이므로 정렬하지 않았고 테이블을 천천히 보면서 계산해보도록 하자. 아래가 과정이다.

1. p1이 가장 먼저 도착해서 1초가 지나면 p1은 7초의 burst가 남게 되지만 p2가 도착했고 p2의 burst가 더 짧기 때문에 p2가 자원을 선점한다.
2. 1초가 지나서 2초가 되면 p3가 도착하지만 여전히 p2는 3초 남았고 p3는 9초 남았다. 이건 p4의 경우에도 똑같고 결국 p2가 끝날 때까지 p3, p4가 도착하므로 일단 p2를 끝내야 한다.
3. p2는 arrival time인 1부터 burst만큼 진행하므로 1→5까지 이어진다.
4. 그 다음 짧은 burst가 p4이므로 p2가 끝난 5부터 burst인 5만큼 진행하므로 5→10까지 이어진다.
5. 그 다음 짧은 건 7초가 남은 p1이니 다시 시작해서 10→17로 이어진다.
6. 마지막은 burst time이 9초인 p3이고 17→26이 되고 끝난다.

지금까지 한건 실행시간이기 때문에 waiting time을 구하기 위해선 다시 계산해봐야 한다.

1. **p2 =1→5만큼 실행되고 이전에 0→1이므로 1초 기다림**
2. **p4 = 5→10만큼 실행되고 이전에 0→5이므로 5초 기다림**
3. **p1 = 10→17만큼 실행되고 이전에 1→10이므로 9초 기다림 (주의!! 이미 0→1이 실행되었던 상태, p2에게 자원 선점권을 넘겨줬기 때문에)**
4. **p3 = 17→26만큼 실행되고 이전에 0→17이므로 17초 기다림**

여기서 arrival time인 (1,3,0,2)까지 고려해서 계산하면

$$
\frac{(1-1)+(5-3)+(9-0)+(17-2)}{4}=\frac{26}{4}
$$
SJF의 단점은 긴 작업이 계속 기다릴 가능성이 있다는 것인데 이걸 **<u>indefinite blocking 또는 starvation</u>**이라고 한다. 긴 burst를 갖는 process가 계속하여 경쟁에서 밀릴 가능성이 있으며 수행이 됨을 보장할 수 없다는 뜻이다.

### Priority scheduling (일반적인 개념)

high priority process에 CPU를 할당하는데 만약 equal priority라면 FCFS를 적용한다.

| process | burst | priority |
| ------- | ----- | -------- |
| p1      | 10    | 3        |
| p2      | 1     | 1        |
| p3      | 2     | 4        |
| p4      | 1     | 5        |
| p5      | 5     | 2        |

priority 기준으로 다시 테이블을 정렬해서 계산하면 p2-p5-p1-p3-p4 순서가 된다. 따라서, p2는 0, p5는 1, p1은 6, p3은 16, p4는 18만큼 기다리기 때문에 $\frac{0+1+6+16+18}{5}=\frac{41}{5}$ 이 된다.

이 스케줄링의 단점은 SJF의 단점과 마찬가지로 낮은 priority의 process는 계속해서 높은 priority의 process에게서 양보를 받기 때문에 starvation이 발생한다.

이런 문제에 대한 대안으로 **aging(노화)**것이 있는데 waiting time에 따라 점차 priority를 증가시키는 개념이다.

###3. Round-Robin(RR) Scheduling

* 짧은 주기로 번갈아 수행해주며 다른 process에게도 기회를 주는 개념이다. Especially for time-sharing에 이용되기 때문에 average waiting time을 줄이기 보다는 공평한 수행이 목적이다.
* FCFS + preemption 으로 볼 수 있는데 일정량의 resource를 썼을 때 preemptive 되므로 process 간 state transition이 발생한다.
* **Quantum** (time slice, 시간주기)이라는 용어가 있는데 RR Scheduling에서 process간 resource를 사용할 수 있는 시간을 말한다.

### Time quantum

* too small (너무 작을 경우) : context switching overhead가 증가한다.
* too large (너무 클 경우) :  FCFS에서 긴 burst의 process를 계속 기다리는 문제(convoy effect)와 유사함

| process | burst time |
| ------- | ---------- |
| p1      | 24         |
| p2      | 3          |
| p3      | 3          |

주어진 time quantum이 4라고 하면, 처음 p1을 4만큼 수행하고 20이 남는다. 아직 p1은 끝나지 않은 상태에서 p2를 실행하는데 burst time이 quantum보다 작기 때문에 끝나고 p3도 동일하게 끝난다. 이후 완전히 끝나지 않은 p1의 20을 quantum인 4로 5번 반복하여 종료한다.
$$
RR(quantum=4)=\frac{6+4+7}{3}=\frac{17}{3}
$$

### RR은 Starvation이 발생할까?

process의 수를 n개, quantum을 q라고 할 때 다음이 성립한다.

* 실행주기는 $n \times q$ 이내이다. process의 burst time 중에는 quantum보다 작은 경우가 있기 때문!
* 따라서 process의 입장에서 본다면 자기 자신을 제외하고 계산하므로 $(n-1)\times q$ 이내의 waiting time이 보장된다고 할 수 있다.
* 이 말은 <u>유한 시간 내에 수행이 보장 된다는 것이므로</u> starvation은 발생하지 않는다. → starvation은 긴 burst time의 process로 인해 수행 가능성이 보장되지 않는 문제점이었음.

### Quantum과 context switching

time quantum은 context switching time 보다 충분히 크게 설정해야 한다. 만약, quantum $\le$ context switching이라고 한다면 process가 작업을 실행하는 시간보다 process의 전환에 의한 시간이 더 소모되기 때문에 CPU 활용도가 굉장히 떨어지게 된다. 

### 4. Multi-level queue Scheduling

Ready queue를 여러개의 level로 두는 idea로 queue가 1개일 경우 각 작업의 특성을 반영할 수가 없기 때문에 작업의 특성별로 별도의 queue를 유지하는 개념이다.

* Each queue 간에 priority가 존재하는데 이건 내부적인 queue의 priority가 아닌 queue와 queue간의 priority를 말하는 것이며 각 queue에는 자신만의 scheduling alogrithm이 존재한다.
* 작업의 특성에 따라 queue에 process를 할당하며, 각 queue마다 CPU의 할당을 다르게 한다.

<img src="https://user-images.githubusercontent.com/35518072/38790498-9e0935da-417c-11e8-9faa-79e937fae7cb.PNG" height="400px" />

위 그림에서 만약 batch process가 running 상태에 있을 때 interactive editing process가 ready queue에 들어왔다면 batch process는 preempt되고 priority가 더 높은 interactive editing process에게 CPU를 넘겨준다.

### 5. Multi-level feedback queue Scheduling

여러개의 queue를 두는 것은 mult-level queue scheduling과 동일하지만 queue간의 process 이동을 허용하며 queue간의 priority는 다음과 같이 정해진다.

* high priority queue에 I/O bound process가 오도록 하는데 그 이유는 CPU burst time이 짧은 것부터 처리해야 average waiting time이 줄어들기 때문이다.
* low priority queue에는 long CPU burst의 process (=CPU bound process)가 오도록 유도한다.

이렇게 priority 기준을 정하면 ready queue로 들어온 process는 high priority queue에 정해진 time quantum안에 수행이 완료되지 못할 경우, quantum이 더 큰 다음 queue로 넘어가고 그렇게 계속해서 넘어가면 마지막엔 FCFS가 적용된 queue로 넘어가게 된다. 

<img src="https://user-images.githubusercontent.com/35518072/38790692-dc1fd6f2-417d-11e8-990a-96b5037d4173.PNG" height="400px" />

위 그림을 보면 quantum이 8로 정해진 queue에 들어온 process는 8 안에 실행을 완료하지 못할 경우 quantum이 16인 queue로 가게 되고 마지막엔 FCFS로 가게 되는 것을 볼 수 있다. 일단 process를 실행해보고 CPU burst time이 짧은 process들을 high priority로 보내는 방법이다.

다음 Scheduling을 정의할 때는 고려사항들이 몇가지 있다.

* **queue의 개수**
* **각 queue의 scheduling algorithm**
* **priority upgrade 방법** (process가 priority를 높이는 방법) : **aging** 에 의해 high priority로 이동할 수도 있음.
* **priority demote 방법** : 위에서 설명한 대로 time quantum안에 process가 수행완료하지 못할 경우
* **process의 queue 결정 방법**




## 5.5 Multi-Processor Scheduling

multi-processor를 사용하는 이유는 load sharing(작업 분담)을 통해 효율을 높여 빠르게 하기 위함이다. 이 파트에서는 homogeneous한 경우만 고려하는데, 동일 종류의 CPU만 있다고 가정하는 것이다. 즉, processor는 ready queue에 있는 어떤 process도 수행가능하다는 것을 말한다.

### Scheduling 형식

#### (1) Asymmetric multi-processing

CPU가 하는 일이 대등하지 않은 경우이다.

* 1개의 processor가 scheduling에 관한 모든 실행을 담당한다.
* 나머지 processor는 단순히 user code를 실행한다.
* 장점 : Data sharing을 하게 되면 processor의 동시 접근으로 인한 data 일관성 문제가 발생할 수 있지만 1개의 processor만 scheduling에 관여하므로 그런 문제가 줄어든다. (=Simple 하다.)

#### (2) Symmetric multi-processing (SMP)

각 CPU는 스스로 scheduling을 하는데, 자신이 수행할 process를 직접 가져오는 것이며, 2가지 방법이 있다.

* 각 processor마다 queue를 구성하거나, (대개 이 방법을 사용한다.)
* 1개의 common ready queue를 둔다. (여러개의 CPU가 동시에 접근할 경우 data의 중복/손실 문제 발생가능)

> 이건 공중전화를 한 번 생각해보자. 공중전화가 여러 개이고 각 공중전화별로 기다리는 줄이 있다면, 각 CPU마다 개별 ready queue를 구성한 것이며, 만약 한 줄 서기 운동과 같이 공중전화는 여러개인데, 줄이 1개만 있다면 1개의 common ready queue를 둔 것이다.



### Processor Affinity

Multi-processor system에서 process가 동일 procesor에서 실행되도록 하는 개념으로, CPU를 놓고 다시 해당 process를 수행할 때도, 똑같은 CPU로 수행하는 개념이다. (= 친화도)

#### 필요성

* Cache는 register 급의 속도로 memory의 일부분을 사용하는 것인데, 만약 다른 CPU를 사용하게 될 경우 cache가 재구성되어야 하기 때문에 **cache overhead **가 발생할 수 있다.

* **Non-uniform memory access (NUMA)** 특성을 갖는 architecture에서 processor 마다 자신이 fast access가 가능한 memory의 process를 실행하도록 하기 위해 필요하다. 아래 그림을 참고하하면 되는데, memory에 load 되는 것이 process이기 때문에 더 빠르게 접근할 수 있는 process에 processor affinity를 맞추는 개념이다.

  <img src="https://user-images.githubusercontent.com/35518072/39107824-c49f0c96-46ff-11e8-8fb9-f907bfc8e466.PNG" height="300px">

* 하지만 문제가 있을 수 있음 → process가 들어왔을 때 processor affinity가 적용된 processor에게 가게 되는데, 그 processor에 기다리는 process가 많을 경우 **<u>load balancing 이 안 맞을 수 있다.</u>**

#### 형식

* **Soft affinity** : 가급적 동일 processor에서 실행되도록 하는 것인데, load balancing이 너무 안 좋을 경우 다른 processor에서도 실행 가능하게 하는 것이다.
* **Hard affinity** : 엄격하게 동일 processor 에서만 실행하는 것으로, 무조건 전담 processor에서만 실행한다.



### Load Balancing

Multi-processor system에서 모든 processor가 fully 활용되도록 workload를 분배하는 것이 목적이다. 예를 들어, 1개의 common ready queue를 두는 방법이 있다. 이 방법은 process가 쉬고 있는 CPU를 할당받기 때문에 load balancing이 알아서 된다. 하지만, 현대의 대부분의 OS가 여러개의 queue를 두기 때문에, queue의 길이에 따라서 쉬는 CPU를 할당할 것인지에 대한 고려사항이 있으므로 load balancing이 필요하다.

#### Load balancing의 2가지 형식

* **Push migration (system 관점)** : 각 processor의 load를 system이 주기적으로 check하고, 불균형(imbalance)이 발생하면 process를 옮기는 것이다.
* **Pull migration (processor 관점)** : Idle(쉬고 있는) processor가 스스로 busy processor의 waiting task를 가져와 실행하는 방법이다.



###Load balancing vs Processor affinity

* <u>극단적인 load balancing 추구</u> → affinity가 무시되어서, 단위 processor당 처리 시간이 길어진다!
* <u>극단적인 affinity 추구</u> → process의 전담 processor에 많은 process가 wait 하고 있을 경우 waiting time이 너무 길어진다.
* 위의 2가지 문제점 때문에 절충이 필요한데, (1) 엄격한 processor affinity를 적용하거나 (2) processor affinity를 적용하면서 waiting process가 너무 많아질 경우 load balancing을 활용하는 방법이 있다.



### Memory stall

Multicore processor는 1개의 chip에 여러개의 processor core를 두는 것으로, 일반 multi-processor보다 chip간의 communication이 적기 때문에 빠르고, less power(전력 소모가 적음)의 장점이 있다.

위 개념은 이미 알았던 내용이고, 이제 **memory stall** 이라는 것을 알아야 하는데 **processor가 memory에 접근할 때 data가 사용 가능할 때까지 소모되는 시간** 을 말한다. 예를 들어 cache에 data가 없으면 memory에 access하는 <u>cache missing</u> 과 같은 것이 있다.

→ 이걸 개선하기 위해서 각 core 당 여러개(2개)의 **hardware thread** 를 가지는 형태를 생각하는데, 한 thread가 memory를 wait하는 동안, core는 다른 thread로 전환하여 작업을 수행하는 것을 말한다. 

> 예를 들어, *Ultra SPARC T1 CPU*에서 chip당 8개의 core를 가지며, core당 4개의 hardware thread를 가져서 32개의 logical processor처럼 동작하게 하는 것이 있다. (하지만 실제로 이렇지는 않음)



## 5.6 Real-Time CPU Scheduling

### 용어

* **Soft real time system** : 가능한 빠른 응답을 요구하는 것으로 시간제약에 유연함이 있다. (계산기)
* **Hard real time system** : Deadline 이전에 service 되는 것을 보장해야 한다. (자동차, 항공기, 내비)

→ <u>그렇다면 어떻게 빠르게 만들 것인가?</u> 바로 절차에 의한 시간인 latency를 줄이는 것이다.

* **Event latency** : event가 발생한 시점부터 service가 이루어질 때까지의 시간

* **Latency requirement** : deadline 조건을 얼마나 주는 것이냐로 응용에 따라 다르다.

  > antilock brake system은 3~5 milliseconds 안에 응답해야 하지만, airliner의 rader를 controlling하는 embeded system은 몇 초까지는 괜찮다.



### Real time system의 성능에 영향을 주는 2가지 Latency와 해결법

* **Interrupt latency (= interrupt handling time)** : kernel data를 update 할 때, process의 동시접근을 막기 위해서 interrupt를 disable 시키는데, 이 시간을 최소화 시켜야 한다.

  > 정의 : the period of time from the arrival of an interrupt at the CPU to the start of the routine that services the interrupt

* **Dispatch latency (= context switching time + 부수절차)** : resource preemption, 즉 kenrel에 있는 어떤process라도 preemption을 시키는 것으로 process가 resource를 다 쓸 때까지 기다리지 않아도 되기 때문에 context switching이 빨라진다.

→ Real time system의 scheduler는 preemptive, priority based scheduling을 한다고 할 수 있다. 예를 들어, process는 그보다 high priority의 process가 CPU를 원하면 바로 preemptive 된다. 물론, 이렇게 간단하게 되는 것은 아니고 실제로 계산해주어야 한다.

### Hard real time system(엄격한 시간 제한)

* deadline 내에 수행을 보장해야 한다.
* **Admission-control algorithm** : 정시 완료를 보장할 수 있는가를 판단해서 보장할 수 있을 때만 admit하고 보장할 수 없으면 reject하는 것이다.
* process에 대한 실행 시간, 실행 주기, deadline을 알아야 한다.

### (1) Rate-monotonic Scheduling algorithm

* <u>periodic tasks</u> using <u>static priority policy</u> with <u>preemption</u>
* 각 작업은 실행 주기 값에 반비례하여 priority가 결정된다.
  * 실행주기가 짧은 process → high priority
  * 실행주기가 긴 process  → low priority

> p1 : 실행 시간 (20), 주기/deadline(50) → CPU utilization (20/50 = 0.40 = 40%)  
> p2 : 실행 시간 (35), 주기/deadline(100) → CPU utilization (35/100 = 0.35 = 35%)
>
> → 총합 = 93.75% < 100%, 수행 가능한 것으로 보임

1. **일반적인 priority를 적용한 경우, p2>p1이라고 하자.**

p2가 먼저 35만큼 실행되고 p1은 20만큼 실행되지만 p1의 deadline은 50이다 하지만 55 때 수행이 완료되므로 deadline인 50에 끝나지 못해서 scheduling에 실패한 것이다. 이것이 의미하는 바는, <u>CPU utilization이 100% 이하이더라도 priority에 따라 scheduling이 실패할 수도 있다는 것이다.</u> 

<img src="https://user-images.githubusercontent.com/35518072/39293785-c8550b20-4974-11e8-962a-605d04a95edf.PNG" height="150px">



2. **Rate-monotonic scheduling을 적용하면, 실행주기가 짧은 것이 high priority이므로 p1>p2이다.**

p1이 먼저 20만큼 실행되고 p2가 35이지만 30만큼 실행되면 다시 p1의 실행주기가 돌아와서 50에 p1을 다시 20만큼 실행하면 70이 되고 p2의 남은 실행시간인 5만큼 실행한다. p1은 2번이 실행될 동안의 실행주기인 100안에 2번 실행되었고 p2도 실행주기인 100 안에 1번 실행되었으므로 scheduling이 잘 되었다고 볼 수 있다. 그 이후도 똑같이 진행된다.

<img src="https://user-images.githubusercontent.com/35518072/39293873-101a71a2-4975-11e8-8804-b19b2ee138d9.PNG" height="150px">



* 위 예제를 보면 real-monotonic scheduling을 적용할 경우 모든 경우에 성공할 것 같지만, 다음의 경우를 보면 CPU utilization이 100% 이하라고 해도 수행이 보장된다고 볼 수 없다.

> p1 : 실행 시간 (25), 주기/deadline(50) → CPU utilization (25/50 = 0.50 = 50%)  
> p2 : 실행 시간 (35), 주기/deadline(80) → CPU utilization (35/80 = 0.4375 = 43.75%)
>
> → 총합 = 93.75% < 100%, 수행 가능한 것으로 보임

3. **Rate-monotonic scheduling은 모든 경우에 수행이 보장되는 것이 아니다.**

real-monotonic scheduling을 적용해보면 p1이 실행주기가 더 짧으므로 p1>p2이다. p1이 25만큼 실행되고 실행주기인 50까지 p2가 25만큼 실행하여 15가 남는다. p1이 50부터 25만큼 실행되서 75가 되고 p2가 남은 15를 실행하지만 85가 되므로, p2의 deadline인 80을 넘게 된다. → scheduling이 실패하는 것을 알 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/39293975-6781047e-4975-11e8-868f-42d7620d2323.PNG" height="150px">



그러면 이 scheduling이 성공하기 위한 최대 CPU utilization은 어느 정도 될까? Worst case의 경우 CPU utilization은 다음과 같은 수식으로 표현된다.
$$
N \times (2^{\frac{1}{N}}-1)
$$
$N=2$ 일 때, 82.8%가 CPU utilization의 최대라고 한다. 따라서 위에서 93.75%가 나와도 실패하며, 이것보다 작아야 하는 것이다.



### (2) Earliest-Deadline-First (EDF) Scheduling

* 실행시간에 priority가 결정되는 **dynamic priority** 의 특징이 있다. (rate-monotonic은 static priority)
* Deadline이 더 가까운 process가 high priority를 갖으며, dynamic이기 때문에 새로운 process가 들어올 때마다 deadline을 비교해서 preemptive 여부를 결정한다.
* Preemptive scheduling은 새로운 proces를 받았을 때, priority를 비교해서 scheduling 하는 것이고 Non-preemptive scheduling은 현재 실행중인 process가 할당받은 CPU를 다 썼을 때 scheduling 하는 것이다.
* 이론적으로는 optimal하기 때문에, 100% CPU utilization으로 생각할 수 있지만 실제로는 **context switching time** 과 **interrupt handling time** 에 의한 **overhead** 가 있기 때문에 불가능하다.

> Rate-monotonic Scheduling의 마지막 예제를 다시 한번 생각해보자. EDF Scheduling에서는 가능할까?
>
> p1 : 실행 시간 (25), 주기/deadline (50)  
> p2 : 실행 시간 (35), 주기/deadline (80)

<img src="https://user-images.githubusercontent.com/35518072/39303120-fb53ca6e-498f-11e8-98fe-525ac06eb06d.PNG" height="150px">

1. p1의 deadline이 더 가깝기 때문에 25만큼 p1을 실행하고 p2를 실행하는데 50일 때 p1의 deadline인 100보다 p2의 deadline인 80이 더 작기 때문에 preemptive 하지 않는다.
2. p2를 실행시간 35만큼 다 실행하고 p1을 다시 25만큼 실행하는데 중간에 80에서 p1의 deadline인 100이 p2의 deadline인 160보다 작기 때문에 preemptive 하지 않는다.
3. 그 다음 100에서 p1의 deadline인 150이 p2의 deadline인 160보다 작기 대문에 p2를 100까지 15만큼 실행하다가 p1에게 preemptive 되서 p1을 25만큼 실행한다. 
4. 마지막으로 125에서 p1의 실행이 끝나고 p2의 남은 시간인 20만큼 실행하고 나머지 시간은 idle하게 된다.



## 5.8 Algorithm Evaluation

여러가지 Scheduling algorithm이 존재하는데, 무슨 기준(Criteria)으로 평가할 것인가? 예를 들어 2가지 기준으로 평가할 수 있다.

1. Maximum response time이 지정된 조건하에서 CPU utilization이 극대화되도록! (CPU utilization이 극대화 되면서 maximum response time이 굉장히 minimal 한 경우를 말함)
2. Turn around time이 총 실행 시간에 비례하도록!

### Deterministic Modeling

* Analytic evaluation으로 사전에 지정한 workload에 대하여 각 algorithm을 적용하여 분석한다.
* 장점 : Simple / Fast
* 단점 : 주어진 data에 대해서만 결과의 의미를 갖기 때문에 data의 신뢰성에 따라서 evaluation의 유효성이 결정된다.

### Queueing Modeling

* CPU burst, I/O burst의 분포를 근사치(approximation)로 추정한다.
* 각 queue에 대해서 arrival rate, service rate이 알려져 있을 때, 이것으로부터 다음을 계산한다.
  * System resource utilization
  * Average queue length
  * Average waiting time

### Simulation

* CPU burst time, arrival time, departure time을 확률 분포에 따라 생성해서 적용하는 방법이다.
* 신뢰도 vs Cost의 구도가 만들어지는데, data의 신뢰도를 높이기 위해선 cost가 올라가기 때문이다.

### Implementation

> The only completely accurate way to evaluate a scheduling algorithm is to code it up, put it in the operating system, and see how it works.

즉, 가장 정확한 평가방법은 scheduling algorithm을 구현해서 OS에 집어넣은 후 관찰하는 것인데 다음과 같은 어려움이 따른다.

* High cost (알고리즘의 coding과 OS로 하여금 support하게 modify하는 것)
* OS의 변화가 사용자 관점과 차이가 있을 수 있다. (즉, user가 그 변화에 관심이 없을 수도 있고, 단지 그들이 사용하고 있는 process들을 실행시키고 결과를 보고 싶을 뿐인 것)
* User가 변화에 적응하여 priority를 임의로 설정할 수 있다. 하지만 이것은 scheduling의 목적에 위반된 것이므로 test 환경과 실제 상황이 다를 수 있다는 것을 보여준다.