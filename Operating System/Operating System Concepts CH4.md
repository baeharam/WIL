# Operating System Concepts 4장

## 4.1 개요

* Thread는 process의 약식이라고 볼 수 있으며 간결해서 실행 효율과 context-switch가 빠르다. 따라서 현대 OS는 대부분 multithread를 지원하며 현대의 많은 computer S/W는 multithreaded program이다.
* Thread는 **고유의 thread id, PC(program counter), register set, stack**을 가진다. 또한 한 process에 속한 여러 thread는 code section, data section, 기타 resource(open files and signal 등)을 공유한다.
* 3장에서 살펴본 것은 single thread이며 multiple thread를 사용할 경우 **can perform more than one task at a time** 이 가능하다.

### 응용 프로그램의 예

1. Web browser

Web browser는 이미지 또는 텍스트를 표시하는 하나의 thread와 network를 통해 데이터를 가져오는 또 다른 thread를 가질 수 있다. 

2. Word processor

Word processor는 그래픽을 표시하는 thread와 키보드로부터 입력을 받아들이는 thread, 백그라운드에서 철자법과 문법을 체크하는 thread를 가질 수 있다.

### Multithread programming의 장점

총 2가지 관점으로 나뉘는데, single thread가 아닌 것에 대한 장점과 multi-process로의 장점이다.

* **Responsiveness(응답성)**

Interactive application에서, 일부분이 block되거나 또는 긴 작업을 실행하더라도 프로그램의 실행이 계속되는 것을 허용함으로써 응답성을 증가시킨다. (예 : 웹 브라우저)

* **Resource sharing(자원 공유)**

process는 shared memory 또는 message passing을 통해서만 자원을 공유할 수 있지만 thread는 자동적으로 그들이 속한 process의 자원과 메모리를 공유한다.

* **Economy(경제성)**

process 생성을 위해 메모리와 자원을 할당하는 것은 비용이 많이 든다. Thread는 정식 process 생성 작업에 비해 효율적이다. 예를 들어 Solaris OS에서 creation은 30배, context-switch는 5배 더 빨랐다.

* **Scalability(규모 가변성)**

Multithread programming의 이점은 multiprocessor의 구조에서 더욱 증가할 수 있다는 것으로 single processor에서 구현할 경우 time sharing을 통해 실제 병렬이 수행하는 것처럼 보이지만 실제 병렬작업은 아니다. 하지만 서로 다른 thread를 서로 다른 process가 실행하도록 하면 실제 병렬 작업을 수행한다. 물론 single thread의 multicore도 실질적인 병렬수행이다.



## 4.2 Multi-core programming

1개의 computing core는 한번에 1개의 thread만 실행할 수 있다. 따라서, single computing core에서 multithreaded programming을 한다는 것은 time-sharing을 통해 가능한 것이다. 하지만 multicore라면 실질적인 병렬수행이 보장될 수 있다.

### Programming Challenges

실질적인 병렬 수행이며 programming 할 때 고려할 사항이 여러가지 있다.

* Dividing activities(작업 나누기) : 작업을 여러개의 concurrent tasks로 분할하여 각 core에서 실행하게 한다. 
* Balance(균형) : parallel 하게 실행되는 task가 의미가 있도록 균형이 잡혀야 된다. 전반적으로, process에 각자 균등한 기여도를 가져야 한다는 말이다.
* Data splitting(데이터 분리) : 각 core에서 실행한 task가 접근하고 조작하는 data 또한 개별 core에서 사용할 수 있도록 나누어져야 한다.
* Data dependency(데이터 종속성) : task에 대한 종속성으로 task가 접근하는 data는 둘 이상의 task 사이에 종속성이 없는지 검토되어야 한다.
* Test and dubggging(시험 및 디버깅) : 서로 다른 execution paths에 대해 결과를 검증해야 하기 때문에 debugging이 굉장히 복잡하다.

### Computing core를 여러개 두는 것으로 얻어지는 성능 개선 효과 : Amdahl의 법칙

$$
\frac{1}{s+(1-s)/N}
$$

여기서 s는 serially 수행되어야 하는 부분의 비용이다. 즉, 종속적인 작업들을 말한다. 또한 N은 core의 갯수이다. 만약 s=1이라면 위 식을 계산하면 1이 나온다. 즉, 원래 작업 순서대로 진행한다는 것이다. 하지만 이상적인 경우로 s=0이면 N이 나오기 때문에 최대 N배의 개선효과를 낼 수 있게 된다.

### Parallelism

* **Data parallelism**

Multiple core에서 어떤 동일 연산을 수행할 때, data 양이 많아서 각 computing core에 data를 나눠서 실행하도록 분배하는 개념이다.

* **Task parallelism**

각 computing core가 서로 다른 task를 수행하는 개념이다. (동일 data 혹은 다른 data)



## 4.3 다중 쓰레드 모델(Multithreading Models)

* User threads : kernel 위에서 지원되며 kernel의 개입 없이 관리된다. 따라서 kernel에 의존적이지 않은 형태로 thread 기능을 제공하는 라이브러리를 사용하여 thread를 생성한다. (Multi-threading programming)
* Kernel threads : OS에 의해 직접 지원되고 관리된다. 따라서 Thread의 생성은 system call을 통해서 하며 거의 모든 현대의 OS가 Kernel threads를 지원한다.

### Many-to-One Model

* 여러개의 user-level threads가 1개의 kernel thread에 mapping 된다. (kernel에서 1개로 생각한다.)
* Kernel에서는 multi-thread를 인지하지 못한다.
* 최대 1개의 thread만 kernel에 접근할 수 있기 때문에, multiple threads는 multicore system에서의 병렬 수행이 불가능하다.
* 장점은 생성이 빠르다는 것.
* 단점은 1개의 kernel thread가 support 하기 때문에 1개가 block 되면 전체가 block 되어 버린다.

### One-to-One Model

* User thread와 kernel thread가 1:1로 mapping 됨.
* 장점은 일부 thread가 block되어도 전체가 block 되지 않는다는 것이다.
* 단점은 user thread를 생성할 때마다 그에 따른 kernel thread를 생성해야 하므로 지나치게 많은 thread가 생성될 수 있고 scheduler에 의해서 관리되는 것이 아니기 때문에 모든 thread의 고유 data 생성과 context-switch로 인한 system overhead가 발생할 수 있다.

### Many-to-Many Model

* 여러개의 user threads가 그보다 같거나 적은 수의 kernel thread로 mapping 된다.
* 이 모델은 위 2개의 모델이 가진 단점을 어느 정도 극복한 모델로 동시 수행을 하는 것과 kernel thread의 생성을 제어한다.
* **변형(two-level model)** : one-to-one model과 many-to-many를 둘 다 지원하는 것이다. one-to-one model은 극단적인 concurrency를 위하는 경우에 사용된다.





## 4.4 쓰레드 라이브러리(Threads Library)

Library란 자주 쓰이는 기능을 모아놓은 것으로 programmer가 thread를 생성하고 운영할 수 있도록 API를 제공해주는 것이 threads library이다.

### 구현하는 2가지 방법

* Ke
* rnel support 없이 user space 상에 두는 libary
  * 모든 code, data structure가 user space에 있음.
  * function 실행이 system call이 아님
* OS 지원을 받는 Kernel level library
  * 모든 code, data structure가 kernel space에 있음.
  * function 실행이 system call로 이루어짐



## 4.5 묵시적 쓰레딩(Implicit threading) 

Programmer가 task를 나누고 process 간 communication(IPC)을 처리하는 부담을 줄일 수 있도록 언어 차원에서 자동으로 threading 기능을 지원하는 것이다.

### 쓰레드 풀(Thread pool)

Server가 일정 갯수의 thread를 미리 틀만 만들어 놓고, 각 thread는 작업이 주어지기를 기다린다. 즉, 만들어진 쓰레드 틀에 작업만 하는 개념이다.

1. Request를 처리할 때 새롭게 생성하지 않으므로 효율적이다. (fast)
2. Thread pool로서 system thread 갯수를 적정 수로 제한하기가 용이하다. (many-to-many가 편하다.)
3. Thread를 생성하는 과정과 실행될 task를 결정하는 과정이 분리됨으로서 다양한 task 수행전략이 가능하다. 예를 들어, *일정 시간 지연 후 실행*, *주기적 실행* 등이 있다.

### Thread pool에서 thread의 수 결정

다음과 같은 요인(고려사항)들이 있다.

* CPU 개수
* Memory 용량
* 동시 실행 요청 예상 thread 개수

### Directives(preprocessor 명령)

```c
#include <omp.h>
#pragma omp parallel // 시스템이 지원해야 됨
{
    // 이 구간은 병렬처리 하겠다는 뜻
}
```



## 4.4 쓰레드와 관련된 문제들(Threading issues)

### Multi-threaded program의 fork() 문제

어떤 multi-threaded program이 fork()를 수행하면 2가지 처리방식에 대해 생각해 보아야 한다.

* 모든 thread를 중복 생성 할 것인가?
* 아니면 new process는 single thread로 할 것인가?

### 신호처리(Signal handling)

Signal이란 UNIX에서 처음 사용한 용어로 process에게 어떤 event 발생을 알리는 것을 말하며 이에 대한 처리는 signal handler가 정해진 procedure를 통해 해결한다. Signal은 다음 2가지 형태로 전달될 수 있다.

* Synchronous signal : 예측 가능하며, 내부적 event에 의한 것이다. 예를 들어 *illegal memory access(포인터 문제)* 나 *division by zero(0으로 나누기)* 문제가 있다. 이 signal은 이벤트가 발생한 process에게 보내진다. 참고로, 몫 연산자인 /을 사용할 때는 방금 언급한 문제와 더불어서 원하는 값이 참값이 아닐 수도 있다는 생각을 하고 있어야 한다. 
* Asynchronous signal : 외부 event에 의한 것이며, 예로 Ctrl+c를 이용해서 콘솔을 종료시키는 *terminating a process* 가 있다. 이 signal은 다른 process에게 보내진다.

Signal handler 또한 2가지로 나뉜다.

* Default signal handler : OS가 기본적으로 제공하는 것이다.
* User defined signal handler : user가 정의한 내용으로 overriding이 가능하다.

### 쓰레드 취소(Thread Cancellation)

완료되지 않은 thread를 종료시키는 비정상 종료를 말하며 2가지 형식이 있다.

* Asynchronous cancellation : 종료가 필요한 시점에 한 thread가 target thread를 즉각적으로 종료시키는 것을 말하며 불안정한 상태를 야기할 수 있다.
* Deferred cancellation : target thread가 스스로, 또 주기적으로 자신이 종료해야 하는지 check 하고, 소정의 절차를 거쳐 종료과정(resource, shared data 처리)을 수행한다. 따라서 논리적 종료이며 보다 system이 안전하다.

### Thread Local Storage(TLS)

Thread는 해당 process의 data section을 공유하지만 상황에 따라, 어떤 data에 대하여 자신만의 copy를 유지할 필요가 있는데 그 때 사용하는 것이 바로 TLS이다. 예를 들어, transaction-processing system에선 각각의 transaction이 서로 다른 thread에서 이루어져야 하며 unique identifier를 가져야 하기 때문에 TLS를 사용한다.