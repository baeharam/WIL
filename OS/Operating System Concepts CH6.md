#Operating System Concepts 6장

## 6.1 Background

### Cooperating process

서로 다른 process가 하나의 응용 프로그램에서 영향(data, signal)을 주고 받는 것을 말하며 공유 data가 존재한다는 말과 같다. 공유 data가 존재하기 때문에 **data integrity(무결성)** 문제가 발생할 수 있다. p1이 data를 수정하고 있는 도중에 context switching으로 인해 p2가 data를 읽어버리면 문제가 생기며 이를 **Race Condition** 이라고 한다.

> 예를 들어 bounded buffer에서 data의 개수 정보를 count 공유변수로 유지하고 있었다고 하자.
>
> producer는 count=count+1의 연산을, consumer는 count=count-1의 연산을 수행할 것이다. 그러나 레지스터 관점에서 보면 작은 세부 연산이기 때문에 다음과 같이 나눠질 수 있다.
>
> producer : R1 ← count;	R1 ← R1+1;	count ← R1;  
> consumer : R2 ← count;	R2 ← R2-1;	count ← R2;
>
> producer가 R1 ← count; 연산을 수행할 때 count=5인 경우를 생각해보자. 이 때, context switching을 당하면 consumer가 3개의 연산을 모두 수행하고 count는 4가 된다. 하지만 다시 producer의 수행으로 돌아가기 때문에 count는 결국 6이 된다.

위 예제를 통해 볼 때, process가 공유변수를 가지고 cooperate 할 때의 문제점은 atomic 하지 않은 연산에 대해서 data integrity 문제가 발생할 수 있으므로 process가 공유 변수에 대해 어떠한 작업을 수행하고 있을 경우, 다른 process의 접근을 막아야 한다는 것을 알 수 있다.



##6.2 Critical Section Problem

* **Critical Section**

여러 process에서 공유하는 common variable(data)를 변경시키는 코드 부분

* **Critical Section Problem**

공유하는 data를 수정하는 부분을 여러 process가 가지고 있을 때, data의 integrity를 유지하는 문제를 말한다. → 이와 같은 문제 때문에 수행은 "Mutual Exclusive" 하게 이루어져야 한다. 한순간에 하나의 process만 critical section을 이용하게 하는 것이다. 이렇게 상호배타적으로 실행하여 critical section problem을 방지하는 것을 **"Mutual Exclusion"** 이라고 부른다.

* **Entry section** : critical section에 진입하기 위해 허가를 요청하는 코드 부분
* **Exit section** : critical section에서 빠져나왔음을 알리는 코드 부분

### Solution의 만족 조건

1. **Mutually exclusion** : 어떤 process가 critical section을 수행 중이면 다른 모든 process는 critical section을 수행 중일 수 없다.
2. **Progress** : critical section을 수행하고자 하는 process 들만이 경쟁에 참여하며, decision은 무한대로 미루어질 수 없다. 즉, critical section을 수행하는 process는 유한 시간안에 결정되어야 한다는 것이며 아무도 critical section에 접근하고 있지 않을 때 접근하고자 하는 process가 있다면 접근을 허용해야 한다는 뜻과 같다.
3. **Bounded waiting** : 어떤 process가 critical section을 요청한 후 승인될 때까지, 다른 process에게 허용될 수 있는 횟수는 제한되어야 한다. 즉, starvation이 발생하지 않아야 한다는 것이다.

### S/W Solution

2개의 process를 고려하는데 <u>i는 자신</u>을 지칭하고 <u>j는 상대방</u>을 지칭한다.

* **turn** : Critical section으로 진입할 순번 (차례)
* **flag 배열** : process가 critical section으로 진입할 준비가 되었다는 것

1. **turn이라는 공유변수를 두어 turn이 i이면 process i가 critical section을 수행하고, 종료할 때 turn을 j로 변경한다. (처음 초기화는 turn=i)**

```c
while(trun!=i)
    ;
// Critical section 수행
turn = j;
```

다음 solution의 문제점은 순서가 고정되기 때문에 상대 process가 수행되지 않으면 수행할 수 없기 때문에 2번 연속 수행 할 수 없다. 이게 바로 **progress 문제** 인데, 번갈아 수행하는 것이므로 상대방이 critical section을 사용하지 않는데도 기다려야 하는 현상이 발생하여서 유한 시간 안에 critical section을 수행할 process를 보장할 수 없게 된다.

2. **각 process가 자신이 critical section 진입을 표시하는 boolean flag를 둔다. (초기값은 false)**

Critical section에 진입할 때, flag를 true로 바꿔서 지금 critical section을 사용할 준비가 되었다고 하는 것이다.

```c
flag[i] = true; // (1)
while(flag[j]) // (2)
    ;
// Critical section 수행
flag[i] = false;
```

process i가 진입한다고 flag[i]를 true로 만들어 놓고, while문을 빠져나올 때까지 상대방이 기다리는 형태이다. 그러나 여전히 문제점이 존재하는데 만약, (1)과 (2) 사이에서 context switching이 발생하고 상대방이 (1)을 수행하면 둘 다 flag가 true가 되기 때문에 critical section에 진입한 process가 없는데도 계속해서 기다리게 되는 **deadlock 문제** 가 발생한다. 이 문제 또한 유한 시간 안에 process를 decision을 보장 못하므로 progress 문제이다.

그렇다면, deadlock 문제를 해결하기 위해서 다음과 같이 (1)과 (2)를 바꾸면 해결될까?

```c
while(flag[j]) // (1)
flag[i] = true; // (2)
    ;
// Critical section 수행
flag[i] = false;
```

만약 (1)과 (2) 사이에서 context switching이 발생하면 둘 다 critical section에 진입하는 허가를 얻기 때문에 **mutual exclusion 문제** 가 발생한다.

###1번과 2번의 결합, Peterson's algorithm

```c
do{
    flag[i] = true;
    turn = j;
    // 상대 process의 flag가 false이거나 = Critical section에 진입할 준비가 안되었거나
    // 순번(turn)이 자기 자신이라면, while문을 빠져나와 Critical section을 수행한다.
    while(flag[j] && turn == j)
        ;
    // Critical section
    flag[i] = false;
}while(true);
```

먼저 flag[i]를 true로 만들고 turn을 j로 지정한다. 이렇게 함으로써 다른 process가 critical section에 진입하기를 원한다면 진입 가능하다는 것을 보장한다.  만일 두 process 모두 동시에 진입하길 시도한다면 turn은 거의 동시에 i와 j로 지정되지만 결국 덮어씌워질 것이므로 flag의 최종값이 어떤 process가 먼저 critical section으로 진입할지를 결정하게 된다. 

그렇다면 Peterson's algorithm은 solution의 조건을 만족할까?

* **Mutual exclusion is preserved.**

만일 두 process 모두 자신의 critical section을 동시에 진입하려고 한다면 flag[i] == flag[j] == true로 지정되어야 한다. 동시에 true 값을 가질 수 있는 flag 배열과는 달리 turn 변수는 동시에 i 또는 j의 값을 가질 수 없으므로 (덮어씌워짐), 두 개중 하나의 process는 while문에 걸려있을 것이다. 따라서 상호배제(mutual exclusion)는 지켜진다.

* **The progress requirement is satisfied.**

$P_j$가 critical section에 들어갈 준비가 안되었다면 flag[j] == false 이므로, $P_i$는 while문을 탈출하여 자신의 critical section에 진입할 수 있게 된다. 따라서 진행(progress) 조건을 충족한다.

* **The bounded-waiting requirement is met.**

만일 flag[i], flag[j]가 모두 true라면 turn의 값에 따라 critical section에 들어갈 process의 순번이 결정된다. turn==i이면 $P_i$가, 반대로 turn\==j라면 $P_j$가 critical section에 들어간다. 그러나 $P_j$가 critical section을 빠져나올 때, flag[j]를 false로 지정하여 $P_i$로 하여금 적어도 한번은 진입할 수 있도록 보장해준다. 이 말은 turn을 상대방 위주로 해놓았기 때문에 대기시간은 한없이 길어지지 않아 한정대기(bounded-waiting) 역시 지켜짐을 알 수 있다.



## 6.4 Synchronization Hardware

S/W가 아닌 H/W Instruction을 사용하는 solution이 존재하는데 **H/W Instruction**이란 CPU 명령어로,  **atomically** 수행하며 중간에 개입되지 않고 단일 작업으로 수행하는 primitive 연산을 말한다. 다음 2개의 instruction이 있다.

* test_and_set
* compare_and_swap

어떤 **변수의 내용을 수정** 하고 **두 개 변수 내용을 교환 **하는 연산을 atomically 수행하는 개념이다.

### $t$ = test_and_set(&lock())

* lock 값을 true로 변경하고 이전값을 return 한다. lock은 true나 false이므로, 이 2가지 경우에서 결국 true로 바뀌는 것만 존재한다. 
* initial이 false인데, 한 process가 test_and_set을 통해 lock을 true로 바꾸면 그 process가 수행 가능하고 나머지 process들은 lock이 false가 될 때까지 기다린다.

```c
while(test_and_set(&lock))
    ;
// Critical section problem
lock = false;
```

### $t$ = compare_and_swap(&lock, 0, 1)

* lock의 초기값은 0이며 critical section에 진입하려는 process가 lock을 1로 만들고 진입한다.
* compare_and_swap은 lock을 1로 만들기 전의 값을 리턴한다.
* 다음으로 진입하려는 process는 lock이 0이 아니기 때문에 진입하지 못하고 처음 진입했던 process가 수행을 끝내고 lock을 0으로 만들면 진입한다.

```c
while(compare_and_swap(&lock, 0, 1))
    ;
// Critical section problem
lock = 0;
```

### 위 2개의 단점을 보완한 Algorithm

**위 두 solution은 bounded waiting을 보장하지 못한다. (= Starvation이 발생할 수 있다.)**  
→ waiting[n] 이라는 n개의 배열을 flag로 사용해서 waiting[i] 가 true면 process i가 critical section의 수행을 요청함을 표시하기로 하자.

* **Entry section**
  * 먼저 자신의 critical section에 대한 요청 표시를 한다. → waiting[i] = true
  * 현재 아무도 critical section을 수행하고 있지 않아서 lock을 false → true로 만드는 걸 성공시키거나
  * 누군가가 자신을 지정하여 수행을 승인하면 (자신의 waiting을 false로 해주면) critical section을 수행한다.
* **Exit section**
  * 무조건 lock을 false로 하는 것이 아니라 기다리는 다음 순서의 process에게 권한을 준다. (waiting을 false로 바꿔준다.)
  * 만일 기다리는 process가 없다면 비로소 lock을 풀어준다. (lock = false)

```c
do{
    waiting[i] = true;
    key = true; // lock이 true
    while(waiting[i] && key)
        key = test_and_set(&lock);
    waiting[i] = false;
    
    // Critical section 수행
    
    j = (i+1) % n;
    while((j!=i) && !waiting[j])
        j = (j+1) % n;
    
    if(j == i)
        lock = false;
    else
        waiting[j] = false;
}while(true);
```

1. 처음에 lock 값은 false로 초기화 되어있고 처음으로 critical section에 진입하려는 process 에서는 test_and_set에 의해 lock이 true가 되므로 critical section에 진입한다. 이 때, key 값은 계속해서 true이고 다른 process는 while문을 통과하지 못하므로 **상호배제(Mutual exclusion) 조건을 만족한다.**
2. Critical section을 수행한 process는 waiting 배열을 $i+1, i+2,..., n-1, 0,..., i-1$ 순서대로, 즉 원형 순서(Cycling ordering)로 조사한다. 조사하면서 waiting 값이 true인 process를 찾으면 그 process의 waiting 값이 false가 되고 critical section을 수행하게 된다. 하지만 waiting 값이 true인 process가 없다면 lock을 false로 만든다. (= lock을 놓아버린다)
3. 2번에서 waiting 값이 true인 process에게 최대 $n-1$ 번 안에는 critical section을 수행하게 하므로 **한정대기(Bounded waiting) 조건을 만족한다.** 또한, 기다리는 process가 없는 경우, 당연히 lock이 false 값이 되므로 critical section에 진입하고자 하는 process가 들어갈 수 있고 **진행(Progress) 조건도 만족한다.**





## 6.5 Mutex(Mutual Exclusion) Locks

**Critical section problem을 해결하는 단순화된 형태의 S/W Tool** 이며 critical section의 접근 가능 여부를 나타내는 available 변수와 다음 함수들을 사용한다.

* **acquire()** : available 변수가 true면 false로 변경하고 빠져나오고, false면 기다린다.
* **release()** : available 변수를 true로 만든다.

위 함수들은 반드시 atomically 수행되어야 한다.

```c
acquiere(){
    while(!available)
        ;
    available = false;
}
release(){
    available = true;
}
do{
    //acquire lock
    //Critical section 수행
    //release lock
}while(true);
```

### Busy waiting

**Lock이 풀리기를 계속 기다리는 과정으로 CPU 수행을 계속하고 있는 것** 을 말한다. 이 개념을 사용하는 mutex lock을 <u>Spin lock</u>이라고 부르며 single processor에선 lock이 풀리기 위해선 context switching을 해야하기 때문에풀릴 가능성이 없고 multi-processor 일 경우 풀릴 가능성이 있다.

process p1과 p2가 각각 자기 코드안에 공유 data 수정 부분이 있는 경우 발생하는 문제가 critical section problem이며 lock을 걸어 어떤 process가 data를 수정하고 있다면 다른 process의 수정을 막는 것이 해결법인데, 이런 측면에서 busy waiting은 lock에 의해 막혔을 때 기다리는 부분이다.

Lock이 풀리기를 기다리는 시간이 길수도 있기 때문에 context switching을 해서 다른 process에게 CPU를 양보하고 다시 오는 것이 효율적일 수 있다. 그러나 lock이 짧을 경우 context switching을 하는 시간보다 기다리는 것이 더 효율적일 수 있기 때문에 논란의 여지가 있다.



## 6.6 Semaphores

**OS가 지원하는 Synchronization tool** 로 mutex에 비해 robust하며 일반적인 시점 제어 문제를 해결한다. Semaphore의 정의는 **"an integer variable"** 이라고 생각하면 되며 그걸 S라고 하자. 이 때 Semaphore에 적용되는 2가지의 standard operations가 있다.

* Wait(s); → 1 감소, p-operation이라고도 부름
  * S값이 0이면 0보다 커질 때까지 wait한다. (0보다 작은 값은 가질 수 없다.)
  * 0보다 크면 1 감소시키고 빠져나온다.


* Signal(s); → 1 증가, v-operation이라고도 부름
  * S값을 1 증가시킨다. (신호를 주기 위함이다.)


System에서 Semaphore를 지원한다는 것은 이 2가지 연산이 atomic 연산이라는 것을 말한다. 장점은 Critical section problem이 간결하게 해결될 수 있다는 것이다.

### Example

* process p1이 A를 수행하고 process p2가 B를 수행하는데, 실행 순서가 A→B 라고 하면 B에서 wait하고 A가 수행을 종료했을 때 signal을 B에 주면 수행 시점 제어가 가능하다. (s의 초기값은 0이다.)
* Critical section problem을 해결하는 방법은 진입하기 전에 wait(s)를 해주고, 수행이 종료된 후 signal(s)를 해주면 semaphore 연산 자체가 critical section으로 수행하는 것처럼 보인다. (process 1개는 진입해야 하므로 s의 초기값이 1이고 wait에서 s를 0으로 바꾸고 진입한다.)
* 위와 같은 방법으로 critical section problem의 모든 문제를 해결하는 것 같지만 signal이 critical section의 접근을 풀어버리기 때문에 다시 경쟁해서 **bounded waiting의 문제는 해결되지 않는다.**

### Bounded/Busy waiting 문제를 해결하는 Semaphore의 구현

위에서 제시된 critical section의 2가지 문제인 wait(s) 에서의 **busy waiting 문제와 bounded waiting 문제를 해결** 하는 구현 방법이 존재한다.

* **Semaphore의 정의**

```c
typedef struct semaphore{
    int value;
    struct process *list; // linked list
}Semaphore;
```

정수값, process list를 유지하는 것이 하나의 semaphore이다.

* **wait(s)**

```c
{
    s->value--;
    if(s->value<0){ // 기다려야 하는 상황
        //s->list에 process 자신을 넣고 block 됨 (CPU 양보)
    }
}
```

여기서 list는 FIFO list (queue) 이기 때문에 bounded waiting 문제를 해결하고, 기다려야 하는 상황인 value<0에서 바로 block 되기 때문에 busy waiting 문제를 해결한다.

* **signal(s)**

```c
{
    s->value++;
    if(s->value<=0){ // 기다리는 process가 있다는 것
        //s->list로부터 process를 1개 꺼내어 wake up 시킨다.
        // value=-n인 경우가 나올 수 있는데 음수값이므로 n개의 process가 기다리고 있다는 것을 말한다.
    }
}
```

### Deadlock과 Starvation

<img src="https://user-images.githubusercontent.com/35518072/39567476-14030b00-4efa-11e8-9a00-f305ff39a654.PNG" height="200px">

* Critical section 1 에 대해 사용하는 semaphore를 S라고 하면 wait(S)와 signal(S)가 process $P_0$와 $P_1$ 에 사용된다.
* 그런데 프로그램을 짜다가 새로운 critical section을 추가할 경우가 생겼다면 critical section 2가 추가 되고 그에 대한 semaphore를 Q라고 하면 위와 같은 형태가 될 수 있다.
* wait과 signal은 atomic 연산이지만, 그 연산들 사이에서 context switching이 발생할 수 있다. $P_0$의 wait(S)가 수행되고 context switching이 일어났다고 하면 wait(Q)도 수행되어 S=Q=0이 된다.
* 이렇게 되면 그 다음 연산에서 wait(Q)와 wait(S)를 하게 되는데 둘 다 signal 연산을 수행할 수 없으므로 deadlock이 발생한다.
* deadlock과 starvation의 차이점은 deadlock은 최악의 상황이 확정되었다는 것이고 starvation은 최악이 올수도 있는 가능성이 있는 것이라고 하는데 다음 정의가 더 명확한 것 같다.

*"deadlock은 어떤 process도 critical section에 접근할 수 없는 것이고 starvation은 low priority process가 high priority process에 의해서 계속 밀려나서 접근을 못하는 것"*

### Priority Inversion

> Critical section problem과 연관하여 어떤 high priority process의 수행이 lower priority process의 수행에 영향을 받아 지연되는 현상 (critical section 때문에 의도와 반대로 되는 것)

예를들어, process L,M,H가 있고 priority 순서는 L<M<H라고 하자.

* L : 현재 critical section을 수행 중이다.
* H : Critical section을 요청하여 waiting 상태에 있다. (CPU를 가로채도 수행이 안된다.)
* M : Critical section과 무관한 작업을 수행 중이지만 L<M이므로 L의 수행을 preemption 시킬 수 있다.

이 때, M이 L의 수행을 preemption으로 지연시킬 수 있고 지연시키면 H의 수행이 계속 지연되게 된다. 결국, H의 수행이 자기보다 priority가 낮은 M에 의해 지연되는 것이며 이런 현상을 Priority inversion이라고 부른다. 그러면 이런 문제를 어떻게 해결할 수 있을까?

***"higher priority process가 요청한 어떤 resource를 사용하고 있는 process는 한시적으로, 요청한 process의 priority를 갖도록 하고, resource 사용이 끝나면 원래의 priority로 환원한다."***

이걸 **priority inheritance** 라고 하며 위의 예시에선 L의 priority가 H의 priority를 한시적으로 갖게 되는 것이다.



## 6.7 Classic Problems of Synchronization

### Bounded buffer problem

producer-consumer 간의 synchronization 문제로 buffer-full, buffer-empty에 대한 문제이다. 해결을 하기 위해선 총 3개의 semaphore를 사용한다.

* empty (counting semaphore) : 초기값 n → buffer의 빈 공간 갯수
* full (counting semaphore) : 초기값 0 → 현재 buffer의 data 갯수
* mutex (binary semaphore, 0/1) : 초기값 1 → mutual exclusion (읽고 쓰는 작업을 위해서)

여기서 empty와 full은 complement 관계인데, 그 이유는 semaphore의 논리가 0과 -1의 경계에서 정해지는 것이지 n에 대해 정해지는 것은 아니므로 empty가 n이면 full이 0이 되고 full이 n이면 empty가 0이 된다. 이게 무슨 말인가? producer와 consumer의 코드를 보자.

```c
// Producer
do{
    // produce an item in next_produced
    wait(empty);
    wait(mutex);
    // add next-produced to the buffer
    signal(mutex);
    signal(full);
}while(true);
```

위 코드는 producer로 empty가 0이면 (= 빈공간이 없으면) 기다리고, 0이 아니면 1개 줄이고 들어간다. mutex에 대한 wait과 signal은 critical section에 대해서 시점을 맞춰주는 것이다. 그 다음 signal에서 full은 data의 개수이므로 썼기 때문에 1 증가시킨다. empty와 full이 complement 관계인 이유는 빈 공간이 없으면 buffer full이란 의미이고 모두 비었으면 buffer empty이기 때문에 생각해보면 당연한 것이다.

```c
// Consumer
do{
    wait(full);
    wait(mutex);
    // remove an item from buffer to next_consumed
    signal(mutex);
    signal(empty);
    // consume the item in next_consumed
}
```

이제 consumer인데 full이 0이면 (= data의 개수가 0이면) 기다리고, 0이 아니면 증가시키고 들어간다. 마지막 signal에서 empty는 빈공간의 갯수이므로 읽었기 때문에 증가시킨다.

### Readers-Writers Problem

어떠한 공유 데이터에대해서 여러개의 Reader process와 여러개의 Writer process가 존재할 텐데 그 때의 시점 제어를 어떻게 할 것인가에 대한 문제이다. Reader와 writer가 각각 공유 data에 접근할 때 나머지를 전부 block해버리면 간단하지만, reader의 경우 다른 reader가 동시에 읽어도 data integrity 문제가 발생하지 않기 때문에 굳이 block할 이유가 없다. 물론 writer의 경우 reader나 다른 writer를 block하지 않을 경우 data integrity 문제가 발생하기 때문에 반드시 block 해주어야 한다.

이 문제를 해결하는 방법은 2가지가 있다.

* Writer 우선 : writing 하고 있으면 추가적인 read/write 불가능 → reader의 starvation이 발생한다.
* Reader 우선 : reading 하고 있으면 추가적인 read 가능 → writer의 starvation이 발생한다.

Writer와 Reader의 구조를 보자.

```c
//Writer
do{
    wait(rw_mutex);
    // writing is performed
    signal(rw_mutex);
}while(true);
```

Writer는 세마포어인 `rw_mutex`를 통해서 한 순간에 한 process만 write하게 하는데, writing이 수행중일 경우 다른 reader나 writer는 접근할 수 없는 것이다. `rw_mutex`의 초기값은 1로 하나의 process의 수행은 보장한다.

```c
//Reader
do{
    wait(mutex);
    read_count++;
    if(read_count==1)
        wait(rw_mutex);
    signal(mutex);
    // reading is performed
    wait(mutex);
    read_count--;
    if(read_count==0)
        signal(rw_mutex);
    signal(mutex);
}while(true);
```

Reader는 `read_count` 변수를 가지며 이 변수의 역할은 reading 중인 reader의 개수를 세는 것이다. 처음 `wait(mutex)`와 `signal(mutex)`로 헷갈릴 수 있는데 이건 `read_count`를 critical section으로 보기 때문에 그것에 대한 시점제어를 해주는 것이다.

처음 `read_count`가 1일 경우는 처음 read할 때를 말하며 그 때 writer가 write하고 있으면 기다린다. 그 후 writer가 끝내면 빠져나와서 `read_count`에 대한 lock을 풀어준다.

reading이 끝나고 `read_count`를 줄이고 0이라면 reader가 없는 것이므로 reader 혹은 writer가 접근할 수 있도록  signal을 해주고 마지막에 `read_count`에 대한 lock을 풀어주는 것은 똑같다.

이렇게 문제를 해결하여 mutual exclusion을 구현함과 동시에 병렬성을 극대화할 수 있다.

### Dining philosophers problem

원탁에 n명의 철학자들이 앉아 있고 각 철학자들 사이에 젓가락이 놓여 있다고 하자. 철학자들은 계속 생각을 하다가 배가 고프면 왼쪽과 오른쪽의 젓가락을 들어서 가운데 있는 음식을 먹을 수 있다. 이 때 synchronization 문제가  생긴다. 젓가락 1개가 2개의 process에게 점유될 수 없기 때문에 그걸 해결하는 문제인 것이다. 

```c
do{
    wait(chopstick[i]);
    wait(chopstick[(i+1) % n]);
    // eat for awhile
    signal(chopstick[i]);
    signal(chopstick[(i+1) % n]);
    // think for awhile
}while(true);
```

위와 같이 문제를 해결하는데, 왼쪽 젓가락과 오른쪽 젓가락을 전부 들 수 있을 때까지 기다린 후에서야 음식을 먹고, 음식을 다 먹은 후에는 둘 다 내려놓음으로 다른 철학자로 하여금 음식을 먹을 수 있게 하는 원리이다.

하지만 만약, 각각의 철학자가 왼쪽 젓가락을 들고 context switching이 일어난다면 모든 철학자가 오른쪽 젓가락을 기다리는 상황이 되기 때문에 deadlock이 발생한다.
