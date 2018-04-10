# Operating System Concepts 3장

## 3.1 프로세스 개념(Process Concept)

* **process** : a program in execution (=job), system view에선 process이고 user view에선 job이다.
  * program : a passive entity (하드디스크에 있는 파일)
  * process : an active entity (현재 돌아가고 있는 프로그램)
* **process 실행 중 필요한 내용**
  * text section(code) : 프로그램 명령들, 메모리에 올라가 있다.
  * 레지스터, program counter(PC, 현재 수행 or 다음 수행하는 명령어 위치가 저장된 레지스터)
  * function call을 위한 stack dynamic의 stack, data를 위한 data section의 static, dynamic allocation을 위한 heap dynamic의 heap을 포함한다.

### 프로세스 상태(Process State)

![default](https://user-images.githubusercontent.com/35518072/38160861-cd5b61bc-34fe-11e8-8ad0-959a63e6a581.PNG)

* new : process가 생성되는 단계
* ready : processor(CPU)가 할당되기를 기다리는 상태
* running : process가 실행되고 있는 상태
* waiting : 어떤 event를 기다리고 있는 상태 (I/O라면 종료되기를 기다린다.)
* terminated : 실행을 종료하는 상태

### 프로세스 제어 블록(Process Control block, PCB)

* **process를 관리하기 위해 OS가 유지하는 정보** (현재 program의 수행 상태에 관한 일체의 정보) 로 multi-programming의 전환에서 현재 program state를 PCB에 저장한다.
* **PCB의 내용**
  * process state
  * program counter
  * CPU 레지스터 내용 (임시기억 보존, 데이터를 저장)
  * CPU Scheduling information (어떤 process에 CPU 할당할 것인지에 대한 정보)
  * memory management information (memory에 load해야 하므로)
  * accounting information : CPU 사용량, processs 정보 등
  * I/O status ; 할당된 device 목록, open된 file list

### 문맥 전환(Context Switching)과 쓰레드(Thread)

* **Context Switching** : CPU가 수행할 process의 전환, 실제 user에게 필요하지 않은 시간인 context를 switch 할 때의 시간이 걸리기 때문에 overhead가 발생한다.
* **Thread** : 수행의 흐름
  * single thread : 한 순간에 1개의 task 실행
  * multiple thread (multi-thread programming) : 여러개의 program을 병렬적으로 실행하기 위한 것, 언어에서 지원을 하여야 하며 CPU가 지원을 해서 mapping이 되어야 한다. (짧은 전환으로 user에게 병렬처럼 느껴질 수 있는 것이다.)





## 3.2 프로세스 스케줄링(Process Scheduling)

Multi-programming은 program을 여러개 수행하는 것을 허용하는 것이며 그 목적은 **CPU Utilization**을 최대화하기 위함이다. Time sharing은 짧은 주기로 CPU를 번갈아 사용하는 것이며 목적은 **Fair services**를 제공하기 위함이다. (Time sharing은 context switching으로 인한 overhead 때문에 CPU utilization이 저하된다.)

따라서 multi-programming과 time sharing을 가능하게 하기 위해서 여러 개의 process 중 어떤 것을 선택할지 결정하는 시스템이 있어야 한다.

### 스케줄링 큐(Scheduling Queue)

process 스케줄링을 가능하게 하기 위해서 scheduling queue라는 것을 사용하며 process에게 waiting 하도록 만드는 list이다. 일반적으로 priority queue로 구현되며 만약, process가 요청한 시간이 기준이라면 FIFO 형식의 queue가 된다. 하지만 모든 것이 FIFO로 해결되지는 않기 때문에 algorithm이 필요하다.

### 스케줄러(Schedulers)

process는 실행되는 동안 다양한 scheduling queue들 사이를 이동한다. process를 scheduling 하기 위해선 운영체제에서 어떤 방식으로든지 이들 queue에서 process들을 반드시 선택해야 하며 선택 절차는 적절한 scheduler에 의하여 실행된다.

* **Long-term scheduler (=Job scheduler)** : Job queue

long-term scheduler는 process들을 선택하여 실행하기 위해 memory에 load한다.

* **Short-term scheduler (=CPU scheduler)** : Ready queue

CPU 수행을 기다리는 상태로 실행 준비가 완료된 process를 대상으로 CPU를 할당한다.

Scheduling queue의 구현은 PCB를 원소로 하는 queue로 구현되는데 linked list를 사용한다. linked list의 한쪽 끝에서 원소의 insert(rear)가 이루어지고 반대쪽 끝에서 원소의 삭제(front)가 이루어진다. Linked list가 왼쪽에서 오른쪽으로 쭉 이어졌다고 해보자. rear에 삽입하기 때문에 왼쪽부터 삽입할 것 같지만 노드가 다음 노드의 주소만 가지기 때문에 왼쪽을 front로 하고 오른쪽을 rear로 해야 삭제를 구현할 때 용이하다. 만약 오른쪽을 front로 해서 삭제한다면 front의 이전 노드의 다음 노드 주소값을 NULL로 해주어야 하는데 노드는 말했다시피 다음 노드의 주소값은 가지지만 이전 노드의 주소값은 가지지 않는다. 따라서 오른쪽을 rear로 하는 것이 편하다.

![queues1](https://user-images.githubusercontent.com/35518072/38161279-254c183c-3507-11e8-89d9-1951241c1d79.gif)

> [출처](http://www.cs.grinnell.edu/~walker/courses/201.sp05/labs/lab-queues.shtml)

### Context Switching

Ready queue에서 선택되어 CPU를 할당 받은 후 running 상태에 있을 때 다음 상황에서 CPU를 놓게 된다.

* I/O 요청으로 인해 I/O queue로 전환
* subprocess(child process)를 생성하고 그것이 종료되기를 기다릴 때 (event waiting)
* Interrupt (time sharing 포함)
* program 종료

waiting은 CPU를 줘도 수행을 못하는 상황이다. 어떤 작업이 종료됨을 알아야 ready 상태로 가며 ready 상태는 CPU만 주면 수행할 수 있는 상태이다. 그도 당연한 것이 CPU Scheduler에 있기 때문이다.

### 세부사항 정리

* **Long-term scheduler(=Job scheduler)**

시스템 상황에 따라서 degree of multi-programming을 적절히 조절한다. 그렇다면 어떤 기준으로 조절할까? process의 평균 생성률과 평균 소멸율을 비슷하게 맞추는 것, 일정 갯수에 도달하면 process 1개가 종료될 때마다 1개를 생성하는 등의 예가 있다. 하지만 극단적으로 조절하게 되면 문제가 생기는데 1개의 process만 허용한다고 했을 때 resource utilization이 너무 감소하고 모든 process를 받아주면 resource 과부하가 발생한다. 

* **CPU bound process, I/O bound process**

CPU bound process는 CPU 연산이 길게 이어지는 program이며, I/O bound process는 I/O 연산이 빈번하게 발생하는 program이다. resource utilization의 극대화를 위해 적절한 조합이 필요한데, CPU, I/O bound process가 섞여 있어야 한다. 그 이유는 다음과 같다.

1. 모두 CPU bound process일 경우 I/O 활용도가 낮아진다.
2. 모두 I/O bound process일 경우 CPU 활용도가 낮아진다.

process state의 관점에서 봤을 때 CPU bound process만 계속 들어갈 경우 ready queue에만 계속 들어가며, I/O bound process만 계속 들어갈 경우 I/O queue에만 계속 들어간다.

* **구현이 어려움**

하지만 적절히 섞는 것은 구현이 어려운데, CPU bound process인지, I/O bound process인지 파악이 어렵기 때문이다. 명령어로 판단하거나 program을 실행시키고 파악하는 것은 불가능하다. 따라서 현대의 system에서는 long-term scheduler가 없거나 최소한의 기능만 유지하는 경향이 있다. 그 배경은 이러하다.

1. time sharing system으로 동작하기 때문에 모두 load하여 실행이 가능하다. 따라서 long-term scheduler가 없어도 resource 과부하가 발생하지 않고 전체적으로 느려진다.
2. 옛날엔 terminal의 수가 제약이 있었기 대문에 physical limitation이 가능했지만 인터넷이 발전하면서 불가능해졌고 사용자 스스로의 조정을 통해 degree of multiprogramming이 지나치게 늘어지는 현상을 해결한다.

* **medium-term scheduler**

swapping과 비슷한 개념으로 상황이 안 좋을 때 일부 작업을 잠시 memory에서 제거하여 degree of multiprogramming을 완화한다. 요즘 잘 쓰지 않는 이유는 memory가 비약적으로 발전했기 때문.

* **context switching**

현재의 state를 save하고 다른 state를 restore하는 개념으로 실질적으로 user에게 의미있는 실행시간이 아닌 pure overhead(절차에 의한 시간)이 발생하기 때문에 짧을수록 좋다. 따라서 개선을 해야 하는데 hw적 개선으로는 state를 저장하는 register set을 여러개 유지하는 방법이 있다. 단, 비용이 비싸다.



## 3.3 프로세스에 대한 연산(Operation on Processes)

process에 관한 OS의 연산을 말하며 create/terminate는 어떤 OS에도 있다.

* **Process creation** (다른 process를 수행하려고 하는 경우)

UNIX에선 fork() 명령어를, Windows에서는 CreateProcess() 명령어를 사용한다. 이 때 기존 process를 *parent process*라고 하며, 만들어진 process를 *child process*라고 한다. 

* **pid(process identifier)** : process의 고유번호를 말한다.
* **parent process가 child process를 생성할 때** (두 종류 다 병렬작업이다.)
  * parent process가 필요에 의해 child process를 생성하는 경우 (child process는 종속적이다)
  * parent process가 독립적으로 child process를 생성하는 경우 (child process는 독립적이다)

### Process 생성시 수행형태 2가지

1. parent process와 children process가 동시에 수행을 계속하는 경우
2. parent process가 children process 중 일부 또는 전부가 수행을 종료할 때까지 기다리는 경우(waiting)

### 생성된 process에 대한 address space 형식 2가지

1. child process가 parent process의 program과 data를 그대로 갖는 형식
2. child process가 새로운 program으로서 memory에 load되는 형식

### child process의 생성으로 인한 resource 제한

1. parent process의 resource의 subset 만을 공유하면 resource 과부하를 막을 수 있다.
2. child process가 OS로부터 resource를 직접 얻을 수도 있다.

### 명령어

* ps-el : UNIX의 process list 명령어

* fork() : 이 명령 위치 이후부터, 두개의 process가 동시 수행한다. pid_t 타입의 변수 pid가 있다고 했을 때 pid가 fork()로부터 리턴받은 값이 0이라면 child process이고 1이라면 parent process이다. 만약 parent process의 코드에 wait()이 들어간다면 child process의 status 변화(대개 종료인)를 기다리는 것을 의미한다. 이 때 parent process는 state transition이 일어나 waiting으로 가게 된다.

  * ```c
    pid_t pid;
    ...
    pid = fork();
    if(pid==0){} // child process
    else{ // wait이 들어갈수도 있음 } // parent process
    ```


* 그외의 것들 : Thread는 약식 process를 의미하며 API는 OS에 대해 high-level 언어적 차원에서 지원하는 것.





## 3.4 프로세스간 통신(Interprocess Communication)

### Interprocess Communication(IPC)

* **process cooperation의 목적**
  * Information sharing (A에서 생긴 정보를 B에서 이용하는 경우)
  * Computation speed-up : multiple processing core를 이용해서 예를 들어, I/O block일 때, 다른 process 사용을 가능하게 한다.
  * Modularity : 기능별로 서로 다른 process를 구성해서 독립성을 강화한다.
  * Convenience : 한 user가 동시에 여러개의 task를 가능하게 한다. 예를 들어, 프린트 하면서 문서편집을 하거나 크롬 브라우저에서 여러개의 탭을 연 경우가 이에 해당한다.
* **IPC의 구성**
  * Shared memory (process의 공유)
  * Message passing (3가지 issue가 있음)
    * Naming (상대 process의 명칭 지정)
    * Synchronization (보내는 쪽, 받는 쪽의 수행 시점 제어)
    * Buffering (중간의 임시기억장치)

### Shared memory

* memory의 특정 영역을 공유하는 것. (버퍼 공간이다)
* read/write의 문제는 process에서 해결하며 협력하는 process의 일반적인 패러다임인 producer-consumer problem에 대해 생각해보아야 한다.

### Producer-Consumer problem

* producer는 정보를 생산하는 process이고 consumer는 정보를 받는 process이다. 이 problem은 producer와 consumer를 정하는 문제로서 정보 입장에서 정해진다.
* EX) Assembler는 object module을 생산하고 loader는 소비한다, 웹 서버는 HTML 파일과 이미지를 생산하고,  이 자원을 요청한 클라이언트 웹 브라우저가 소비한다.(읽는다)
* Buffer(Shared memory)는 정보를 어떻게 저장할 것인지, Synchronization은 어떻게 시점을 제어할 것인지에 대한 문제이다.

### Buffer의 2가지 유형

* **Unbounded buffer** : programmer 입장에서 용량 상한을 생각하지 않는 size 제한이 없는 buffer
  * producer는 계속 쓸 수 있기 때문에 wait하지 않는다.
  * consumer는 buffer가 empty 상태일 때 wait하며, producer에서 정보를 주는 순간 받도록 싱크를 맞춘다
* **Bounded buffer** : fiexd-size buffer
  * buffer가 empty : consumer가 wait, 위처럼 producer가 정보를 주면 받도록 싱크조정
  * buffer가 full : producer가 wait, consumer가 정보를 받으면 주도록 싱크조정

### Shared memory의 구현(buffer의 구현과 같음)

* Circular queue의 개념을 사용하며 2개의 논리 포인터 in, out을 갖는다.

  ```c
  // Buffer full
  while(true){
      while((in+1) % BUFFER_SIZE) == out)
          ; // do nothing
      buffer[in] = nextProduced;
      in = (in+1) % BUFFER_SIZE;
  }

  // Buffer empty
  while(true){
      while(in == out)
          ; // do nothing
      nextConsumed = buffer[out];
      out = (out+1) % BUFFER_SIZE;
  }
  ```

### Message passing

* send()와 receive() operation이 있다.
* Fixed-size (규격화된 크기)
  * System level implementation이 간결하다.
  * programming이 어려워진다. (실제 상황은 가변이기 때문에)
* Variable size (가변 크기)
  * System level implementation이 복잡하다.
  * programming이 간결해진다.

### Logical communication link (IPC에 대한 이슈)

* direct / indirect communication
* synchronous(받음) / asynchronous(안받음) (서로의 시점에 영향을 받는가? 받는 경우가 많다.)
* automatic(자동 버퍼링) / explicit buffering(버퍼에 쓰고 읽음)

### Naming(보내고 받는 방식을 이름으로 명시)

1. **direct communication** : 상대 process를 직접 지정
   * 예를 들어 send(p, message), receive(q, message)는 p로 보내서 q로 받는 것이다.
   * Communication link
     * communication을 원하는 pair마다 link가 설정된다.
     * exactly two process간의 link가 설정된다.
     * 각 pair 간에 exactly 1 link만 존재한다.
   * Naming(addressing) 형식
     * Symmetry : send(P, message), receive(Q, message)
     * Asymmetry : send(P, message), receive(x, message) : 임의의 process인 x로부터 receive 하기 때문에 process와 message를 모두 알아낼 수 있다.
   * 단점 : modularity가 결여되어 있어서 서로 끼치는 영향이 높다. 예를 들어 어떤 process의 pid가 변경되면 관련된 모든 process의 해당 명령을 수정해야 한다.
2. **Indirect communication** : 어디다 놓는 개념
   * mailbox 개념 : 어떤 process가 mailbox를 create, 대개 받기 위해서 만듦
   * send(A, message), receive(A, message) 형태로 보내고 받는다.
   * Communication link
     * mailbox를 공유하는 process간 link (n:n 관계)
     * 2개 이상의 process간 communication 가능
     * mailbox가 여러 개일 때 두 process 간의 여러개 link 가능

### 여러개의 process가 하나의 mailbox로부터 수신할 때

* 둘 다 허용한다.
* 한 개의 process에게만 허용한다. (선착순)
* system이 process를 선택하여 지정한다.

### Synchronization(동기화)

blocking은 producer가 buffer full일 때 wait하는 것과 같이 synchronous한 것이고, non-blocking은 상대 process의 상황을 고려하지 않는 asynchronous한 것이다.

* **blocking send** : message가 상대 process(또는 mailbox)에 전달 될 때까지 기다린다.
* **nonblocking send** : message를 보내고 작업을 재시작한다. (OS지원)
* **blocking receive** : message를 받을 때까지 기다린다.
* **nonblocking receive** : message를 성공적으로 받거나 널(null)을 받는다. (있으면 받고 없으면 안받는 개념)

### Buffering(버퍼링)

message passing이 indirect이든, direct이든 process들에 의해 교환되는 message는 temporary queue에 들어가며 이 queue를 구현하는 방식은 3가지이다.

* **zero capacity** : queue의 최대길이가 0이다. 즉 버퍼를 사용하지 않는 것이다. 반드시 blocking send/receive이다.
* **bounded capacity** : queue full일 때, sender가 block된다.
* **unbounded capacity** : the sender never blocks. (queue full인 경우가 없으니까)

*zero capacity*를 "rendezvous(랑데뷰)"라고 부르는데, 그 이유는 send/receive가 모두 blocking이라 sender와 receiver간의 시점을 맞추어 결국 만나기 때문이다. (보내는 시점 = 받는 시점)



## 3.6 클라이언트 서버 환경에서 통신(Communication in Client-Servery Systems)

### Socket(소켓)

* an endpoint for communication
* network 상에서 communication 하는 두 process 간에서 (IP address, port number)로 주어지는 것이 socket이다. 따라서 이걸 통해 구별한다.

### RPC(Remote Procedure Call)

* network로 연결된 System 간의 procedure call (원격 시스템의 procedure를 호출하여 매개변수를 보내기도 하고 출력 혹은 다른 형태로 리턴 값을 받기도 한다.)

### RMI(Remote Method Invocation)

* RPC의 Java version, allow a thread to invoke a method on a remote object.

### Pipes(파이프)

* 초기 UNIX에서 쓰이던 IPC mechanism
* 구현할 때 고려할 사항
  * Unidirectional / Bidirectional
  * Bidirectional에서 한 순간에는 한 방향만 지원하는 half duplex냐 아니면 동시에 양방향을 지원하는 full duplex냐를 고려해야 함
  * parent-child 간에만 지원 vs 모든 process간 지원
  * system 내의 process 간에만 고려 vs network를 통한 다른 system에도 적용



## UNIX에서 process 생성 예제

```c
#include <unistd>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
    pid_t pid;
    pid = fork();
    if(pid==0) child();
    else parent();
}
```

getpid() : 현재 자신의 process id 값을 알아낸다.

wait() : child process의 <u>status변화</u>(대부분 종료)를 wait

### fork() 이후 program 상의 변수는 두 process에서 서로 다른 copy가 유지된다.

```c
int value = 5; // global variable
int main()
{
    pid_t pid;
    pid = fork();
    if(pid==0){
        value+=15;
        return 0; // terminate child
    }
    else{
        wait(NULL); // wait child status change
        printf("%d",value); // print 5 -> why? it is copy!!!
    }
}
```

global variable이라고 하더라도 두 개의 process가 변수를 공유하지 않기 때문에 child process에서 15를 더한 것과 상관없이 parent process의 value는 5가 된다.

### 연속된 fork()

```c
int i;
for(i=0; i<4; i++) fork();
printf("hello\n");
```

여기서 hello는 몇번 출력될까? 처음에 8번이라고 대답했다가 틀렸었는데 그 이유는 fork()가 한 번 불리면 2개의 process로 나뉜다. 그리고 각각의 process에서 다시 fork()가 불리면 2개의 process로 나뉜다. 따라서 n을 fork()가 불리는 횟수라고 한다면 hello는 $2^4$만큼 출력된다. 

물론 보통은 이렇게 짜지 않고 child/parent process가 서로 다른 역할을 하도록 한다.

### 예제 p.149 fig 3.33

parent의 pid를 2600, child의 pid를 2603이라고 가정하자.

```c
int main()
{
    pid_t pid, pid1;
    if(pid==0){
        pid1 = getpid();
    }
    else{
        pid1 = getpid();
    }
}
```

위 코드가 있을 때 pid==0인 경우 child process이므로 **pid=0이고 pid1=2603**이다. 하지만 parent의 경우는 pid가 0보다 큰 양수값이 들어가는데 바로 child process의 pid이다. 따라서 **pid=2603이고 pid1=2600**이 된다.