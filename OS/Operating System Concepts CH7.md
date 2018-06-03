# Operating System Concepts 7장

### Deadlocks

기본적으로 process와 resource의 관계가 꼬여서 무한대로 기다리는 상황을 말한다.

* process p1,p2가 있고 resource r1,r2가 있는 상황
* p1은 r1을 할당받았고 r2를 요청한다.
* p2는 r2를 할당받았고 r1을 요청한다.

결국 이러한 상황이라면 p1, p2는 각각 r2, r1을 기다려야 한다. 따라서 deadlock이 발생한다고 할 수 있다.

### Deadlock handling

Deadlock을 해결하는 방법은 3가지 방법이 있다.

* **예방** : deadlock이 발생하기 전에 예방하는 것으로 resource allocation을 제약하는 prevention과 deadlock의 상태를 감시하는 avoidance가 있다.
* **치료** : detection을 해서 process를 1개씩 죽임으로 deadlock을 없애는 recovery가 있다.
* **무시** : 컴퓨터가 멈추면 종료를 하는 것처럼 모든 process를 포기하는 것으로 사용자에게 맡기는 것인데, 그 이유는 prevention을 하면 성능 저하가 발생하고 recovery를 하면 어쩌피 process를 죽이는 것이므로 그냥 다 죽일 수 있기 때문이다.



## 7.2 Deadlock Characterization

### Deadlock인 상황의 필수 조건

deadlock인 상황이 되었다는 것은 다음 4가지 조건을 모두 만족했다는 것이다.

* **Mutual exclusion** : 최소 1개 이상의 resource가 한 process에 의해 독점으로 점유되고 있다.
* **Hold and Wait** : 한개 이상의 resource를 점유하고 있는 상태에서 다른 process에 할당된 resource를 요구하는 process가 존재한다.
* **No-preemption** : resource는 자발적으로 놓지 않는다. (예를 들면, 프린터작업이 중간에 안 끝나는 것)
* **Circular Wait** : hold-wait 관계가 cycle을 이루는 것으로 총 n개의 process가 있다면 1번째 process가 2번째 process의 resource를 기다리고 2번째는 3번째를, ..., n-1번째는 n번째를 기다리게 되서 결국 n번째는 1번째를 기다리게 되는 상황을 말한다.

### Resource allocation graph

Graph는 vertex와 edge로 나타내어지는데 여기서 vertex와 edge는 다음과 같다.

* $V=P \cup R$ (P : process의 집합, R : resource의 집합)
* E는 request edge (P→R) 와 assignment edge (R→P) 로 나뉜다.

맨 위에서 살펴봤던 기본적인 deadlock 경우를 다시 보자. 이걸 resource allocation graph로 표현하면 cycle이 생기며, 모든 resource가 종류별로 1개의 instance만 있다고 가정하면 cycle이 있다는 것은 deadlock을 의미한다. 하지만 2개 이상의 instance가 존재할 경우 cycle이 존재한다고 하더라도 deadlock이 해결될 수 있기 때문에, instance가 1개인 경우에서만 cycle이 deadlock을 발생시킨다고 볼 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/39856631-9ff588ce-546b-11e8-9d98-7c275e6790d4.PNG" height="300px">

다음 그림이 바로 제일 처음에 제시했던 기본적인 deadlock 발생 상황인데 이 그림은 resource r1,r2의 instance가 2개씩 존재한다. 따라서 p1과 p3에 의해 cycle이 생성되도 p2와 p4가 resource를 사용할 수 있기 때문에 cycle은 있지만 deadlock이 발생하지 않는 상황이라고 할 수 있다.



## 7.3 Methods for Handling Deadlocks

### Deadlock prevention

Deadlock을 예방하는 prevention 방법에는 4가지가 있는데 그 이유는 deadlock의 4가지 필수 조건 중 1개만 완화해도 되기 때문이다.

* **Mutual exclusion 조건 완화 (독점 점유 완화)**
  * resource sharing, resource 추가하는 방법이 있다.
  * 문제점 : program 수행중에 갑자기 resource sharing을 한다거나 resource를 추가할 수는 없기 때문에 근본적으로 불가능한 경우가 있다고 할 수 있다.
* **Hold & Wait 조건 완화**
  * 수행전에, 필요한 모든 resource를 한꺼번에 확보해놓고 시작하는 것으로, 부분적인 확보는 없으며 확보하거나 하지 않거나이다. (all or nothing)
  * Process가 자신이 쓸 resource가 r1,r2,r3라면 미리 이러한 resource들을 확보해놓는 것이다.
  * 문제점 1 : 안 쓰면서도 확보하는 경우가 생기기 때문에 resource utilization이 저하된다.
  * 문제점 2 : 한꺼번에 확보해야 하므로 process가 아예 확보를 못하는 경우가 생겨 starvation 발생확률이 높아진다.
  * 이러한 단점들 때문에 prevention은 하면 안된다.
* **No-preemption 조건 완화**
  * 사용목적이 끝나지 않았더라도 잠시 놓게 하자는 것으로, 사용중인 resource를 중간에 다른 process가 사용가능하게 하도록 release 하는 방법이다.
  * 문제점 : 복원이 가능한 경우, 즉 작업이 무효화되지 않는 경우에만 가능하다. 예를 들어, 프린터를 사용하는데 반만 출력할 수 없는 경우엔 사용을 못하는 것이다.
* **Circular wait 조건 완화**
  - 모든 resource에 일련번호를 부여하여 작은 번호부터 순서대로 request 하도록 제약을 하는 방법이다.
  - process P1과 P2가 모두 R1,R2를 사용하는데 P1은 R1, R2 순서로 사용하고 P2는 R2,R1 순서로 사용한다고 하면, 보통은 resource를 요청한 대로 할당을 하지만 일련번호를 부여하면 달라진다.
  - R1이 1번, R2가 2번이라고 하면 P1이 R1을 요청했을 때 1번이므로 할당이 된다. 그러나 P2가 R2를 요청하면 2번이므로 일련번호 순서가 아니라서 할당이 안된다. 
  - 일련번호를 부여하는 가장 좋은 방법은 일반적인 사용순서로 정의하는 것이다.

|                          | Dining Philosopher                                           | Traffic deadlock                                             |
| ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1. Mutual exclusion 완화 | 공용 chopstick을 1개 추가하거나 자리를 1개 없애는 방법이 있다. | 고가도로나 지하도로를 만드는 방법이 있다.                    |
| 2. Hold and Wait 완화    | 2개의 chopstick을 한꺼번에 요구하는 것으로 1개의 chopstick을 들고 다른 것을 요청할 수 없다. | 교차로를 점유한 상태에서 기다리지 않도록 해야 한다. 즉, 갈 수 있는 공간이 미리 확보 되어 있어야 한다. |
| 3. No-preemption 완화    | 다른 철학자가 chopstick을 요구한다면 쓸 수 있도록 해야 한다. | 후진하여 교차로를 다른 차가 쓸 수 있도록 해야 한다.          |
| 4. Circular wait 완화    | Chopstick에 일련번호를 부여하면, 1번과 2번 비교, 2번과 3번 비교하면서 작은 번호의 chopstick 부터 가져가지만 결국 마지막에 5번과 1번을 비교하므로 기다리게 되서 cycle이 발생하지 않는다. | 일련번호를 교차로에 부여하여 일방통행 시키는 것으로 차선이 좁아 후진하지 못하는 경우에 이걸로 해결할 수 있다. |

### Deadlock avoidance

Deadlock prevention의 단점은 resource를 쓸 수 있음에도 deadlock이 발생하기 때문에 못 쓰게 하는 것으로 **low device utilization** 이라는 부작용이 생긴다는 것이다.

Deadlock avoidance는 다음과 같은 additional information을 필요로 한다.

* 현재 사용가능한 resource의 종류와 갯수
* 각 process에 현재 할당된 resource의 갯수
* 각 process에 대하여 앞으로 필요한 request의 최대치

이 개념은 deadlock 상황을 만들지 말자는 것으로 안정된 상황을 유지하는 것이다. 따라서 시스템 상황에 관련된 용어인 system state가 등장한다.

###System state

1. Safe state : 현재로서는 deadlock이 아니다.
2. Unsafe state : deadlock이 발생할 가능성이 있다.

State의 변화가 발생하는 시점은 process의 request (P →R) 에 대한 승인을 할 것인가? 말 것인가?를 결정하는 때로, 승인되었을 때의 상황을 보고 safe state면 승인하고 unsafe state면 승인하지 않는 것이다. Safe, unsafe의 기준은 deadlock 발생 가능성이 있는지 없는지이다.

### Safe state

Safe state란 safe sequence가 존재하는 state를 말하며 **safe sequence** 란 다음과 같다.

**"Sequence 중에 임의의 process $P_i$의 request가 현재 available resource들과 그 이전의 모든 process $P_j(j<i)$가 갖고 있는 resource의 합으로서 만족할 수 있는 경우"**

한번에 이해하기 힘들기 때문에 예제를 면밀히 보면서 이해할 필요가 있다.

|       | 최대필요량 | 현재사용량 |
| ----- | ---------- | ---------- |
| $P_0$ | 10         | 5          |
| $P_1$ | 4          | 2          |
| $P_2$ | 9          | 2          |

현재 available resource가 3개 남아있는 system 상황이라고 했을 때 sequence가 $P_1 → P_0 → P_2$라고 하면 이러한 sequence가 safe한지 알 수 있는 방법은 모든 process가 안전하게 종료될 가능성이 있는지를 보는 것이다.

* $P_1$을 수행할 경우 available resource 중 2개를 쓰게 하여 끝내고 2를 받아서 5가 된다.
* $P_0$를 수행할 경우 available resource 중 5개를 쓰게 하여 끝내고 5를 받아서 10이 된다.
* $P_2$를 수행할 경우 available resource 중 7개를 쓰게 하여 끝내고 2를 받아서 12가 된다.

결국 모든 process가 안전하게 종료될 수 있기 때문에 이 sequence는 safe sequence라고 하고 있고 safe state라고 할 수 있다. 다음 알고리즘은 은행에 비유할 수 있다.

* $P_1$이 은행에서 총 4억을 빌려야 하는데 지금 2억을 빌렸고 은행에는 3억이 있는 상황에서 은행이 2억을 빌려주면 4억으로 뭘 해서 다시 은행에 갚을 가능성이 있으므로 빌려주는 것이다.
* 그 다음 은행에 5억이 있게 되고 $P_0$가 은행에서 총 10억을 빌려야 하는데 지금 5억을 빌렸고 은행이 5억만 더 빌려주면 나중에 갚을 가능성이 있으므로 빌려준다.
* 마지막으로 은행에 10억이 있게 되고 $P_2$가 은행에서 총 9억을 빌려야 하는데 지금 2억 빌렸고 은행이 7억만 더 빌려주면 나중에 갚을 가능성이 있으므로 빌려준다.

하지만 만약 $P_2$가 은행에 1억을 빌려달라고 하면 은행에는 2억이 있게 되고 2억을 $P_1$에 빌려주면 해결할 수 있지만 $P_1$을 해결하고 돌려받은 총 4억으로 $P_0$를 종료시킬 수 없다. 이렇게 process의 request 중에서는 safe하지 않은 request도 있기 때문에 request를 받아들였을 때의 상황을 보고 받아들여야 하는 것이다.

### Resource allocation graph를 사용한 deadlock avoidance

Resource allocation graph를 사용할 때는 모든 resource의 instance는 1개만 있다고 가정한다.

1. Claim edge : 앞으로 요구하게 될 resource로서 점선으로 표시된다.
2. 시간이 지나면 실제로 resource를 요구하게 되기 때문에 claim edge는 request edge로 바뀐다.
3. 또한 요구를 하고 resource를 할당 받기 때문에 request edge는 assignment edge로 바뀐다. (역방향)
4. 마지막으로 resource 사용이 끝나면 다시 claim edge로 바뀐다.

Resource allocation graph에서 deadlock avoidance의 조건은 다음과 같다.

**"Request edge는 그에 대한 assignment edge가 graph 상에서 cycle을 이루지 않을 때에만 승인되도록 한다."**

여기서 cycle을 이룬다는 것은 unsafe state가 된다는 의미로 deadlock이 발생할 수도 있는 상황이라는 것이다. 이러한 상황은 추가 request만으로 deadlock이 발생할 수 있는 경우가 있다.

![default](https://user-images.githubusercontent.com/35518072/39982385-25591dc0-578f-11e8-81ce-c3e135b96c0d.PNG)

다음 그림에서 deadlock avoidance를 보면 

* 처음에 $P_1$이 $R_1$을 요구했고 assignment edge가 됬을 때 cycle이 안 만들어지므로 $R_1→P_1$의 assignment edeg가 형성되었다.
* $P_2$가 $R_2$를 요구하지만 assignment edge인 $R_2→P_2$가 형성되면 claim edge $P_1→R_2$와 request edge $P_2→R_1$을 포함해서 cycle이 만들어지는 unsafe state가 되기 때문에 요구를 받아들이지 않는다.
* 만약 요구를 받아들여서 $R_2→P_2$의 assignment edge가 형성되면 아래와 같이 된다.

![default](https://user-images.githubusercontent.com/35518072/39982876-b8a89c1c-5790-11e8-9eb6-94259d36d803.PNG)

$P_1$이 $R_2$를 요구만해도 deadlock이 발생하는 unsafe state이므로, 아예 요구를 거부하는 것이 deadlock avoidance인 것이다. Deadlock과 unsafe state가 헷갈릴 수 있는데 다음과 같이 알아두면 된다.

* Deadlock : 실선으로만 이루어진 cycle
* Unsafe state : 점선을 포함한 cycle

### 시험 검토

Deadlock avoidance란 request를 받아들였을 때 safe state인지 판단해서 받아들일지 말지를 결정하는 것으로 각 resource에 single instance만 존재할 경우 resource allocation graph에서 cycle의 존재 유무만 파악하면 풀 수 있다. 시험 문제를 풀어보자.

| process | max  | allocate | need |
| ------- | ---- | -------- | ---- |
| p1      | 12   | 2        | 10   |
| p2      | 6    | 3        | 3    |
| p3      | 10   | 3        | 7    |

현재 available한 resource의 개수가 4개라고 하면 p2에 3개를 주면 p2가 종료되서 회수할 수 있으므로 7개가 되고 p3도 종료시킬 수 있으므로 10개, 마지막으로 p1을 종료시킬 수 있어서 모두 종료되므로 safe sequence가 존재한다고 할 수 있다. 따라서 safe state 조건을 만족한다.

여기서 p3가 1개 더 요청한다고 해보자. p3의 allocate는 4가 되므로 need는 6이 되고 available resource는 3이 된다. 여기서 내가 실수했던 부분은 allocate,need의 값을 변경시키지 않고 단순하게 빌려줬다는 개념으로 생각했떤 것이다. 제대로 풀어보면, p2를 종료시킬 수 있으므로 available은 6이 되고 p3의 need가 6이므로 종료시킬 수 있어서 10이 되고 결국 p1도 종료시킬 수 있어서 위와 같이 safe state 조건을 만족한다. 따라서, p3의 request는 받아들일 수 있다.

하지만 만약에 resource 당 instance의 개수가 1개가 아니라 multiple이라면 어떻게 할까? 바로 Banker's algorithm이 여기서 등장한다.

### Banker's algorithm

여러개의 변수를 정의하여 알고리즘을 설명하기 때문에 다음 변수들의 의미를 필수적으로 알아두도록 하자. 일단, n은 process의 수이고, m은 resource의 수이다.

* **Available[m]** : 현재 사용가능한 resource의 개수
* **Max[n, m]** : process 당 필요한 최대 resource 개수
* **Allocation[n, m]** : process 당 현재 사용중인 resource 개수
* **Need[n, m]** : process 당 추가적으로 필요한 resource 개수 (Max - Allocation)
* **Work[m]** : 회수해서 늘어나게 된 resource의 개수로 available의 값을 유지한다.
* **Finish[n]** : process 당 종료 가능한 값 (boolean으로, 모두 true면 safe state이다.)

변수 말고도 부등호의 의미를 새로 정의하는데 Need <= Work의 의미는 Need의 모든 요소가 Work의 모든 요소보다 작거나 같다라는 의미이다. 이제 알고리즘이 어떻게 진행되는지 보도록 하자.

1. **초기화**

$Work = Available$  
$Finish[i] = false (i=0,1,...,n-1)$

2. **종료시킬 수 있는 process 종료하는 과정**

$Finish[i]==false$ (process가 아직 끝나지 않았다면)  
$Need_i \le Work$ (process가 종료되기 위해 필요한 resource의 개수가 현재 가지고 있는 resource보다 작거나 같다면 종료가 가능하다.)  
$Work = Work + Allocation_i$ (process에 할당되었던 resource를 회수한다.)  
$Finish[i] = true$ (process 종료시켰다고 표시)

그리고 이 작업을 모든 process에 대해서 반복한다.

3. **safe sequence 존재하는지 확인**

만약, 2번까지 수행했는데 $finish[i]$가 false인 process가 존재한다면, 모두 종료되지 않아서 unsafe state이고 존재하지 않는다면 모두 종료되어서 safe state가 되는 것이다.

**예제를 한 번 풀어보자.**

|      | Allocation | Max     | Available | Need    |
| ---- | ---------- | ------- | --------- | ------- |
|      | (A,B,C)    | (A,B,C) | (A,B,C)   | (A,B,C) |
| P0   | (0,1,0)    | (7,5,3) | (3,3,2)   | (7,4,3) |
| P1   | (2,0,0)    | (3,2,2) |           | (1,2,2) |
| P2   | (3,0,2)    | (9,0,2) |           | (6,0,0) |
| P3   | (2,1,1)    | (2,2,2) |           | (0,1,1) |
| P4   | (0,0,2)    | (4,3,3) |           | (4,3,1) |

A,B,C는 각각 resource의 instance를 의미한다. 현재 Work가 (3,3,2)인데 Need<=Work인 process를 보면 P1과 P3가 종료될 수 있다.

* P1을 종료시키면 Work가 기존 (3,3,2) + (2,0,0) = (5,3,2)가 된다.
* P3를 종료시키면 Work가 기존 (5,3,2) + (2,1,1) = (7,4,3)이 된다.
* 이제 P0,P2,P4의 Need<=Work이기 때문에 전부 종료시킬 수 있다.
* 따라서 safe state 조건을 만족한다. (= safe sequence가 존재한다.)

### Resource-request algorithm

유효한 request를 받아들이기 위해 Banker's algorithm을 하기전에 수행해야 하는 알고리즘이다.

1. **$Request_i \le Need_i$** : maximum Need의 범위 내에서 요청하는지 확인한다, 이 부분은 의미가 없을 수도 있는게, process가 request를 할 때 자기가 필요한 것보다 더 요청하는 것이 말이 안되기 때문이다.
2. $Request_i \le Available$ : 현재 사용가능한 개수보다 요청하는 resource의 개수가 더 많으면 무조건 reject해서 wait 시킬 수 밖에 없다.
3. 위 2개 조건을 만족하면 받아들였다고 가정하고 다음과 같이 계산한다.
   * $Available = Available - Request_i$ (요청을 받아들이면 현재 사용가능한 resource에서 요청한 만큼 제거해야 한다.)
   * $Allocation_i = Allocation_i + Request_i$ (process에 할당되는 resource의 개수가 요청한 만큼 더해진다.)
   * $Need_i = Need_i - Request_i$ (process가 종료되는데 필요한 resource의 개수가 줄어든다.)



#7.6 Deadlock Detection

현재까지 배운 deadlock 예방 방법은 prevention과 avoidance로 둘 다 resource의 활용도가 떨어지기 때문에 deadlock을 미리 예방하지 말고 발생했을 때 회복시키는 방법을 사용하고자 하는 것이 목적이다.

* **Resource가 single instance인 경우**

**wait-for graph** 라고 불리는 것을 사용하는데, resource allocation graph의 변형으로 resource node를 제거하고 풀린 edge들로 결합시킨 것을 말한다. 즉, resource가 빠진 <u>process들과의 관계</u>가 된다. 만약 여기서 cycle이 존재할 경우 deadlock이며 존재하지 않을 경우 **"현재 deadlock이 아닌 상황"** 이라고 말할 수 있다.

<img src="https://upload.wikimedia.org/wikipedia/commons/7/71/Wait-for_graph_example.png">

> [출처](https://upload.wikimedia.org/wikipedia/commons/7/71/Wait-for_graph_example.png)

위의 그래프 처럼 생긴 것이 wait-for graph이며 resource node가 존재하지 않고 cycle이 존재함으로 deadlock이라고 할 수 있다. <u>edge가 의미하는 것은, P1→P2라고 하면 P2의 resource를 P1이 wait하고 있는 상황이라고 볼 수 있다.</u> (일단, z,x,y는 무시하도록 하자.)

* **Resource가 multiple instance인 경우**

Banker's algorithm을 변형하는 것으로 해결하는데 원래 Banker's algorithm이 Need에 관심이 있었다면, 변형된 알고리즘은 <u>Request에 관심이 있다.</u> 즉, 현재 Request를 받아들였을 때 deadlock이 발생하는가를 보는 것으로 원래는 deadlock이 발생할 수도 있는 상황인 unsafe state에 대해서 체크했지만 이건 현재 상황만 보는 것이다. 따라서 원래 알고리즘에서 "$Need_i \le Work$" 부분이 "$Request_i \le Work$"로 바뀌게 된다.

### Deadlock detection algorithm 적용시점 결정 요인

Deadlock detection은 computation을 해야하기 때문에 극단적으로, process가 resource를 request 할 때마다 하게 되면 상당한 overhead가 발생한다. 따라서 얼마나 적용할 것인가를 고려해 볼 필요가 있다. 대표적으로 다음 2가지 기준을 적용할 수 있다.

* 발생빈도를 예측해서 그 정도로 적용한다.
* 영향을 받는 process의 수를 보고 그만큼 적용한다.



# 7.7 Recovery from Deadlock

Deadlock detection을 통해 deadlock이 발견되었을 경우, OS가 처리하는 방법은 2가지이다.

* 사용자(operator)에게 알려서 수작업으로 처리하도록 한다.
* Automatic recovery, OS가 알아서 처리한다.

### Breaking deadlock (automatic recovery)

OS가 알아서 처리하는 방법도 2가지로 나뉠 수 있다.

* **Process termination** : deadlock이 풀릴 때까지 1개(또는 일부 몇개)의 process를 중단시킨다. (detection을 계속 하면서, 풀렸는지 확인한다.)
* **Resource preemption** : 1개(또는 일부 몇개)의 resource를 강제로 preemption 시킨다. 물론, 수행의 부작용이 나타날 수 있지만 일부라도 회복시킬 수 있다는 측면을 봐야 한다.

### Process termination

* Deadlock에 걸린 모든 process를 abort(강제 종료) 시키는 방법으로 clear(명확)하지만 expense(지금까지 수행한 것을 다 날려야 함)가 부담이 될 수 있다. (하나의 방법이 될 수 있는 이유는 시스템의 상태를 회복시키는 것이기 때문에)
* **Partial termination** : Deadlock cycle이 없어질 때까지, 한번에 한 개씩 abort 시키는 것으로 어떤 process를 abort 시킬지 결정하는 문제에 대해 생각해야 한다.

### Abort 할 때의 process 결정 factor

* process의 priority (낮은 걸 abort)
* 현재 수행된 계산량 기준
* resource 사용량 (deadlock에 연관될 확률이 높은 것이 resource 사용량이 많은 process이므로 사용량이 많은 것을 abort)
* 앞으로의 resource 요구량
* interactive(대화형) or batch(백그라운드) (batch process를 abort)
* 전체 process의 수

다음의 6개 요인은 abort할 process를 결정할 때 정해져 있는 것이 아니라 고려해야 할 것들이며 괄호안에 있는 내용이 활용될 수 있는 예시이다.

### Resource preemption

* Victim 선정 : 어느 process의 어떤 resource를 preemption 시킬지를 정해야 한다.
* Rollback(복원) : preemption 이후의 복원 방법에 대해 고려해야 한다.
* Starvation : 적합한 용어는 아니지만 매번 같은 process가 victim으로 선정되는 현상을 말한다, 즉 deadlock이 발생할 때마다 똑같은 process를 abort 시키는 것이다.