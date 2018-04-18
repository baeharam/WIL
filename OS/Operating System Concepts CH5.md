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

### Priority scheduling (SJF의 일반적인 개념)

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
* low priority queue에는 long CPU burst의 process가 오도록 유도한다.

이렇게 priority 기준을 정하면 ready queue로 들어온 process는 high priority queue에 정해진 time quantum안에 수행이 완료되지 못할 경우, quantum이 더 큰 다음 queue로 넘어가고 그렇게 계속해서 넘어가면 마지막엔 FCFS가 적용된 queue로 넘어가게 된다. 

<img src="https://user-images.githubusercontent.com/35518072/38790692-dc1fd6f2-417d-11e8-990a-96b5037d4173.PNG" height="400px" />

위 그림을 보면 quantum이 8로 정해진 queue에 들어온 process는 8 안에 실행을 완료하지 못할 경우 quantum이 16인 queue로 가게 되고 마지막엔 FCFS로 가게 되는 것을 볼 수 있다. 일단 process를 실행해보고 CPU burst time이 짧은 process들을 high priority로 보내는 방법이다.

다음 Scheduling을 정의할 때는 고려사항들이 몇가지 있다.

* **queue의 개수**
* **각 queue의 scheduling algorithm**
* **priority upgrade 방법** (process가 priority를 높이는 방법) : **aging** 에 의해 high priority로 이동할 수도 있음.
* **priority demote 방법** : 위에서 설명한 대로 time quantum안에 process가 수행완료하지 못할 경우
* **process의 queue 결정 방법**



