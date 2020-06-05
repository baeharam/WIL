# Operating System Concepts 1장

## 1.1 운영체제가 할 일(What Operating Systems Do)

### 사용자 관점(User View)

사용자 관점에서의 운영체제는 사용자가 실행하는 작업을 최대화 하는 것이다. 이러한 경우 성능에 대해 약간의 고려를 하고 주로 사용의 용이성을 위해 설계되지만 다양한 하드웨어와 소프트웨어 자원이 어떻게 공유되느냐 하는 자원의 이용 측면에는 전혀 신경을 쓰지 않는다.

하지만 사용자가 대형 컴퓨터나 미니 컴퓨터에 연결된 터미널로 접근해 자원을 공유할 경우 자원 이용을 극대화하도록 설계되며 워크스테이션의 경우  자신의 전용자원을 갖지만 네트워킹과 서버 등을 공유하기 때문에 운영체제는 개인의 사용 용이성과 자원 이용 간에 적절한 조화를 이루도록 설계된다.

### 시스템 관점(System View)

시스템 관점에서는 2가지로 운영체제를 보는데 첫번째는 **자원 할당자(resource allocator)**로 보는 관점이다. 자원에 대해 상충될 수 있는 요청들이 있기 때문에 시스템을 효율적이고 공적하게 운영할 수 있도록 어느 프로그램과 사용자에게 자원을 할당할 지를 결정해야 한다. 위에서 본 여러 사용자가 메인프레임이나 미니컴퓨터에 동시에 접근하는 경우 특히 중요하다.

두번째는 **제어 프로그램(control program)**으로 보는 관점이다. 사용자의 프로그램 실행을 제어하고 I/O device의 제어와 작동에 관심을 가진다.

### 운영체제의 정의

운영체제는 사용자가 컴퓨터를 편리하게 사용할 수 있도록 하는 시스템 소프트웨어이다. 특별하게 정해진 정의는 없지만 응용프로그램이 하드웨어에서 동작할 수 있도록 해주며, I/O device에 대한 공통적인 연산을 필요로 할 때 자원을 제어하고 할당하는 기능을 하나의 소프트웨어로 통합한 것이 운영체제이다.



## 1.2 컴퓨터 시스템의 구성(Computer-System Organization)

### 컴퓨터 시스템 동작(Computer-System Operation)

* **Bootstrap program과 Background program**

컴퓨터가 구동을 시작하기 위해서는 실행할 초기 프로그램을 가지고 있어야 하는데 이걸 **bootstrap program**이라고 하며 보통 펌웨어라고 알려져 있는 컴퓨터 내의 읽기 전용 메모리(ROM)나 EEPROM에 저장된다. 이것은 CPU 레지스터로부터 장치 제어기, 메모리 내용 등을 포함한 시스템의 모든 면을 초기화한다. 또한 운영체제의 커널을 찾아 메모리에 적재(load)시켜야 하며 커널이 적재되면 system과 user에게 service가 제공되는데 몇몇의 services는 커널이 실행되는 동안 계속 돌아가는 system processes 혹은 system daemons가 되기 위해 메모리에 적재되는 system program에 의해 커널 외부에서 제공된다.

* **인터럽트(Interrupt)**

CPU가 인터럽트 되면, 하던 일을 중단하고 즉시 고정된 위치로 실행을 옮긴다. 이러한 고정된 위치는 인터럽트를 위한 Service Routine이 위치한 시작 주소를 가지고 있다. 그리고 **ISR(Interrupt Service Routine)**이 실행되며 끝나면 CPU는 다시 인터럽트 되었던 연산을 재개한다. ISR은 Asynchronous한 상황에 대해 처리방식을 지정해 놓은 루틴이며 이걸 모아놓은 것이 **Interrupt Vector**이다.

### 저장장치 구조(Storage Structure)

* **저장/적재 명령**

CPU가 실행하고자 하는 프로그램은 모두 메모리에 저장되어야 한다. 대부분의 프로그램을 주 메모리인 RAM에서 실행시키고 정적인 프로그램은 ROM에 저장된다. 모든 형태의 메모리는 워드의 배열을 제공하며, 각 워드는 고유의 주소를 가지고 있다. 상호작용은 특정 메모리 주소들에 대한 일련의 적재(load) 또는 저장(store) 명령을 통하여 이루어지며 적재 명령은 RAM으로부터 CPU 내부의 레지스터로 한 워드를 옮기는 것을 말한다. 반대로 저장 명령은 레지스터의 내용을 RAM으로 옮긴다. 명시적인 저장/적재 명령 외에 CPU는 실행을 위해 자동적으로 RAM으로부터 명령을 적재한다.

* **저장장치 계층 구조**

프로그램과 데이터가 RAM에 영구히 존재하기를 원하지만 RAM은 너무 작고 전원이 공급되지 않으며 내용을 잃어버리는 휘발성이기 때문에 불가능하다. 따라서 보조 저장장치를 제공하는데 보통 자기디스크이며 대부분의 프로그램이 메모리에 적재될 때까지 디스크에 저장된다. 저장장치는 하나의 계층으로 구성될 수 있는데 상위 수준으로 갈수록 fast/expensive/volatile의 특징이 강해지고 단위 용량당 가격이 비싸기 때문에 용량이 작아진다. 최상위 저장장치는 레지스터인데 너무 비싸기 때문에 레지스터 급으로 빠르게 이용하기 위해 캐시 메모리라 불리는 임시 저장소를 사용한다. 또한 빠른 기능과 영구 저장을 위해 **SSD(Solid State Disks)**를 사용하기도 한다.

* **I/O 구조**

운영체제는 **장치 제어기(Device Controller)**라고 불리는 것이 존재하며 각 device controller마다 **Device Driver**를 가지고 있어서 device controller의 동작을 이해하고 운영체제의 다른 부분들에게 device에 대한 일관된 인터페이스를 제공한다. 요즘은 알아서 I/O에 대해 인식을 하기 때문에 device driver를 설치 안해도 되는 경우가 있다. I/O 연산은 Interrupt 방식으로 이루어지기 때문에 대량의 데이터에 대해 높은 오버헤드가 발생할 수 있다. 이 문제를 해결하기 위해, **DMA(Direct Memory Access)**가 사용된다. CPU의 개입없이 device controller가 블록 단위의 데이터를 버퍼에서 메모리로 보낼 때마다 (1byte마다가 아닌) 인터럽트를 발생시키기 때문에 device controller가 작업을 하고 있는 동안 CPU는 다른 작업을 수행할 수 있다.



## 1.3 컴퓨터 시스템 구조(Computer-System Architecture)

###Multiprocessor의 이점

Processor는 CPU를 말하고 process는 현재 실행중인 프로그램을 말한다.  Single processor는 1개의 CPU이고 Multiprocessor는 여러개의 CPU이다.

* 단위시간당 처리량이 많아진다. (Increase Throughput)

Processor의 수를 증가시킴으로 보다 짧은 시간 동안에 보다 많은 일을 실행하기를 바라지만 processor간의 협력에서 약간의 overhead가 유발되기 때문에 무조건 single processor보다 빠른 것은 아니다.

* 경제적인 규모 (Economy of Scale)

Single processor를 여러개 두는 것보다 multiprocessor 하나를 갖으면 주변장치, 대용량 저장장치, 전원 공급 장치 등을 공유하고 있기 때문에 비용이 저렴하다.

* 신뢰성을 증가시킨다. (Increase Reliability)

기능들이 여러개의 processor에 분산되면 한 processor가 고장나도 시스템이 정지하는 것이 아니라 속도만 느려지게 된다. 이렇게 살아남은 하드웨어의 수준에 비례해 계속적인 서비스를 제공하는 능력을 **우아한 퇴보(Graceful Degradation)**라고 한다. 어떤 시스템은 어느 하나의 구성요소의 고장에도 불구하고 동작을 계속할 수 있기 때문에 우아한 퇴보를 넘어 **결함 허용(Fault Tolerant)**적이라고도 불린다.

### 여러가지 용어

* Multicore는 한 개의 chip에 여러개의 computing core가 집적되어 있는 것을 말한다. 별도의 chip으로 구성되는 multiprocessor에 비해 chip간의 communication이 적기 때문에 더 빠르며 전력 소모가 적은 장점이 있다.
* Blade server는 processor board, I/O board, network board 등을 각각 자신의 운영체제를 갖는 독립적인 board로 구성하는 서버이다.
* Clustered system은 여러개의 system을 합쳐서 고성능(대용량) system으로 활용하고자 하는 개념이다.



## 1.4 운영체제의 구조(OS Structure)

* **Multiprogramming**은 system view이며 CPU utilization을 높이기 위해서 여러개의 program을 실행시키는 개념이다. DMA에서 CPU가 다른 작업을 수행하는 경우가 한 예이다.
* **Time Sharinig**은 user view이며 multi-tasking을 위해서 시간을 쪼갬으로 작업을 전환시키는 개념이다. 비효율적이지면 user view이기 때문에 공평한(fair) 수행을 하기 위함이다.
* **Interactive System(대화형)**은 user가 system에 무언가를 요청했을 때 system이 답을 주는 것을 말하며 CLI나 카톡 같은 것들이 예이다. 이런 시스템이 유용해지기 위해선 *short response time*이 중요하다.
* **Job Scheduling**은 몇몇 작업이 메모리로 옮겨올 준비가 되었고, 그들 전부를 메모리에 보관할 공간이 불충분하다면, 시스템은 그들 중 몇개를 선택해야 할 때 사용되고 **CPU Scheduling**은 메모리에 있는 여러 개의 작업이 동시에 실행 준비가 되어 있으면, 그들 중 하나를 선택해야 할 때 사용된다.
* Time Sharing 시스템에서 운영체제는 적절한 응답 시간을 보장하기 위해서 작업들을 RAM에서 디스크로 적절하게 Swap-in 또는 Swap-out시킨다. 이 목적은 **가상 메모리(Virtual Memory)**를 사용하는 것이 더 일반적이다.



## 1.5 운영체제 동작(Operating System Operations)

### 이중 동작 모드(Dual-Mode Operation)

운영체제에는 적절한 동작을 보장하기 위해 운영체제의 코드의 실행과 사용자 정의 코드의 실행을 구분할 수 있어야 한다. 따라서 2개의 독립된 operation mode를 갖는데 user mode와 kernel mode이며 kenrnel mode는 system/supervisor/previliged mode라고도 불린다. Dual-mode의 목적은 다음과 같다.

* 민감한 연산(previleged instruction)을 반드시 kernel mode에서만 실행되게 하여 악영향을 끼치지 않도록 제어한다.
* 운영체제는 user program에서 자신을 대신하여 운영체제가 실행하도록 미리 지정되어 있는 작업들을 운영체제에게 요청할 수 있는 **System Call**을 제공하며 protection을 위하여 위험한 작업은 system call로 구현한다. 모든 I/O는 system call이며 programming은 수많은 system call을 호출한다.

운영체제가 CPU에 대한 제어를 유지할 수 있도록 보장해야 하는데 user program이 무한 루프에 빠지거나, system service를 호출하지 않아서 제어가 운영체제로 복귀되지 않는 경우는 허용할 수 없기 때문에 **Timer**를 사용한다. Timer는 지정된 시간 후 컴퓨터를 인터럽트 하도록 설정한다. 



## 1.6~마지막

### 여러가지 용어 정리

* Storage Management는 File System(폴더의 생성/삭제 등)을 지원한다.
* Caching의 목표는 속도이며 레지스터 급의 빠른 속도로 RAM을 사용하고자 하는 개념이다. RAM은 보조 저장장치의 캐시 역할을 한다고 볼 수 있다. (예로 RAM Disk가 있는데 잘 안쓰임)
* Protection은 user나 process로부터 system의 resource를 보호하는 것이다. 즉, programming이나 system file의 삭제 등에 대해 보호하는 것이다.
* Security는 system을 외부 또는 내부 공격으로부터 보호하는 것이며 명의도용과 불법사용, 바이러스, 웜 등과 같은 종류가 있다.
* Kernel data structure는 OS의 알고리즘을 구현하기 위한 DS이며 list/stack/queue/tree/hash/map이 있다.
* Computing environments의 전통적 환경은 desktop이었으나 network가 발전하고 web technology와 wireless network가 발전하면서 mobile computing 환경으로 변화하고 있다.
* Mobile computing의 범주는 Email, Web-browsing, Music, Video, Photo(camera), E-book 등이 있으며 화면 크기와 용량제한이 개선되면 용도가 증가할 것이다. 
* Distribute System은 여러 개의 system을 network로 연결한 것이다. (loosely complete system)
* Client-server computing은 compute-server가 계산하고 file-server가 저장하는 것이다.
* Peer to peer computing은 각 노드가 대등한 peer이며 작업에 따라 clinet가 되기도 하고 server가 되기도 함.
* Virtualization은 사람에게 느껴지는 것 자체를 의미한다. 어떤 OS를 다른 OS에서 application 처럼 사용하는 Vmware와 같은 프로그램이 있고 C/D 드라이브가 virtually 2개라는 것은 사용자에게 느껴지기에 2개라는 의미이다.
* Cloud computing은 인터넷 상의 server에서 cmputing, storage 및 application실행가지 지원되는 기술로 인터넷 상의 가상 컴퓨터를 쓰는 개념이다.
* Real-time Embeded System은 실시간으로 즉각 응답이 반영되어 적용되는 embeded system으로 대부분의 embeded system은 real-time이다. (가전, 자동차, 무기, IoT)
* 오픈소스 OS는 UNIX/Linux 등이 있다.