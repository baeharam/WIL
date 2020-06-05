# Operating System Concepts 2장

## 2.1 운영체제 서비스(OS Services)

OS Serveces란 하드웨어를 잘 사용하게 해주는 시스템 소프트웨어로서의 OS가 어떤 것을 지원하느냐에 대한 services를 말한다.

* **UI(User Interface)**

UI에는 *CLI(Command Line Interface)*와 *GUI(Graphic User Interface)*가 있는데 GUI는 icon-based와 마우스를 이용해서 동작한다. 또한 *Batch Interface*라는 명령어 집합이라는 인터페이스가 존재한다.(Shell, .bat파일 등)

* **프로그램 실행(Program execution)** : program을 memory에 load하고 execution 시킨다.
* **입출력 연산(I/O operation)**

User가 직접 I/O device를 control 하지 않고, OS가 system call로 지원하는데 그 이유는 protection과 efficiency를 위해서이다.

* **파일 시스템 조작(File system management)** : Data를 체계적으로 분류/저장하는 방법을 지원 (Tree형태)
* **통신(Communication)** : process 간의, computer 간의 communication 지원 (패킷전송, 원격제어 등)
* **오류 탐지(Error detection)**

CPU/memory/I/O device/network 등의 error를 감지하는 기능, 정상적이지 않은 기능에 대한 처리 기능이다.

이제 여기서부터는 multi-user/system view.

* **자원 할당(Resource allocation)** : CPU/memory/I/O device/storage 등을 어떻게 효율적으로 분배?
* **사용자 계정 관리(Accounting)**
* **보호/보안(Protection/Security)**



## 2.2 OS User Interface

* **Command Interpreter**

한 줄에 한 명령어를 처리하는 프로그램으로 command(명령어)에는 UNIX의 경우 cp, rm, mv 등이 있고 MS-DOS의 경우 copy, del, move 등이 있다.

* **GUI**

Mouse로 동작하며 files, programs, dirctories, system functions 등이 모두 icons로 표시된다.



## 2.3 System Calls

System call은 운영체제가 제공하는 서비스에 대한 인터페이스를 제공하며 OS에 이미 만들어진 function을 의미한다. High level language인 C/C++과 같은 언어의 API로 작성되어 컴파일러를 거쳐 system call이 호출된다. **API(Application Programming Interface)**를 지원한다는 것은 실제 system call과 유사한 이름과 인터페이스를 갖는다는 의미이며 해당 API를 지원하는 system에서 실행 가능하다. API의 장점은 system call을 직접 호출하지 않고서도 해당 API를 지원하는 system에서 쓸 수 있다는 것이다. 따라서 **이식성(Portability)**가 존재하는데 assembley 언어는 CPU가 지원해야 하는 하드웨어 종속적인 것에 비해 C언어는 컴파일러만 있으면 된다는 예로 확인할 수 있다.

* System call에서 parameter 전달 방법
  * 레지스터에 저장한다. (단, 양이 제한된다)
  * Memory 상의 table이나 block에 저장하고, 시작위치를 레지스터에 넣어 저장한다.
  * System stack에 push해서 전달한다. (단, 역순이다)



## 2.4 Types of System calls

System calls : an interface between a process and the OS.

* **종류**
  1. Process control(시스템 제어)
  2. File management(데이터 관리)
  3. Device management(각종 I/O device 관리)
  4. Information maintenance(시스템 상황 유지)
  5. Communication(네트워크 연결 & process간 통신)
  6. Protection(보호)
* **Process control**
  * load, excute
  * create, termination (process 생성/종료, C의 exit같은 함수)
  * process attribute 관리 (속성)
  * memory allocation/free (메모리 관리), function call은 stack dynamic이며 dynamic allocation은 heap dynamic이다.
* **File manipulation**
  * create, delete
  * open, close (연결과정, 버퍼를 유지한다)
  * read(fscanf/fread), write(fprintf/fwrite), reposition(읽고 쓰는 위치 설정, fseek)
  * attribute 관리 (속성 : read only, write only...)
* **Device management**
  * request/release (준비과정, 이걸 사용할 것이다라고 알려주는 것)
  * attach : 사용 가능한 상태로 전환 (request가 받아들여지는 경우)
  * read/write/reposition(버퍼)
* **Information maintenance**
  * 시스템 상황을 계속 체크하는 것이므로 time, system data, process, file, device 등의 정보 알아야 된다.
* **Communication**
  * connection 설정, 삭제 (DB도 하나의 예)
  * message 전송, 수신 (패킷단위)
  * remote device 사용 (원격제어)



## 2.5 System Programs

OS를 System program의 집합으로 보는 관점으로, OS를 설치하고 할 수 있는 것들을 OS라고 보자는 것이다.

* **File management**
* **Status information** : 오른쪽 마우스의 속성, time/memory, disk 사용량/사용자 현황/configuration 정보 등이 존재한다.
* **File modification** : 메모장이나 vi같은 것으로 파일 수정이 가능하다. (기본적인 Text Editor기능)
* **PL(Programming Language) support** : 개발하기 위해 사용하는 PL에 대한 compiler, assembler, interpreter, debugger 등이 지원된다. (요즘은 VS, eclipse 같은 통합 IDE 사용)
* **Program loading and execution** : loader와 linker라 불리는 linkage editor를 제공한다.
* **Communication** : Email, rlogin(UNIX의 원격 로그인), FTP(파일 전송 프로토콜) 등을 제공한다.
* **Background services** : system program services라고 불리는 것이 boot time에 실행된다.
* **그 외 공통적인 application programs** : Web browser, MS Office 등



## 2.6 OS Design and Implementation

* **설계 목표**
  * User goals : convenient to use, ease to learn and use, reliable(graceful degradation, fault tolerant..), safe/fast
  * System goals : easy to design/implement/maintain, flexible(user의 조작 자유, 수동), reliable, error-free, efficiency(user의 조작 불가, 자동, flexible과 상충)
* **Mechanisms와 Policies**
  * Mechanisms determine *"how to do something"*
  * Policies determine *"what will be done"*
  * mechanisms와 policies를 분리함으로써 flexibility를 추구하는 것이 목표. 즉, policies의 변화에 따른 mechanisms의 변화를 최소화하여 종속성을 감소시키고 독립성을 증가시킨다. 예를 들어 CPU protection을 구현할 때 mechanism을 timer로 하고 policy를 60초 기다리는 것이라고 했을 때 policy를 30초로 변경해도 mechanism에는 아무 영향을 끼치지 않는다.
* **Implementation**
  * Assembley 언어 : 하드웨어를 직접제어해서 coding tenique이 좋다면 최적화가 용이하지만 하드웨어 종속적이다.
  * High-level 언어 : 컴파일러를 사용하기 때문에 하드웨어 독립적이라 portability가 좋고 coding이 효율적이며 source code가 compat하다. Easier to understand and debug. 또한 DS와 알고리즘의 활용이 용이하고 단점이 될 수도 있지만 컴파일러 기술 발전에 따라 성능이 향상된다.
  * 일부 bottle neck 부분만 assembly로 짜고 나머지는 high-level로 짜는 관점이 있다.



## 2.7 OS Structure

* **Layered approach**
  * 운영체제가 여러 개의 층으로 나뉘어지는 접근으로 최하위 층은 하드웨어이고 최상위 층은 user interface이다. 
  * 장점 : simplify debugging and system verification (error의 영향이 인접 layer까지만 제한되기 때문에)
  * 단점 : 기능에 따라 위치 적절성(우선순위)의 문제가 생길 수 있기 때문에 각 layer의 내용을 정의하는데 세심한 주의가 필요하고 program 수행이 layer의 각 단계를 거치면서 발생하는 overhead때문에 오히려 단일 layer가 더 효율적일 수 있다.
* **Micro kernel**(user mode > kernel mode)
  * 기존의 monolithic kernel은 system 기능이 모두 system call로 kernel mode에서 수행했지만 micro kernel은 hw 종속적인 부분만 kernel mode로 하고, 다른 기능은 system 기능으로 user mode에서 실행한다는 개념이다.
  * 장점 : user space에서 새로운 service를 추가할 때 kernel의 수정을 최소화하기 때문에 OS의 확장이 용이하고 다른 hw보다 쉽게 적용이 가능하다. 또한 kernel을 거의 건드리지 않기 대문에 more security&reliability하다.
* **Modules** (loadable kernel modules)
  * kernel에 core component를 두고 추가 service는 boot time 또는 실행시간에 해당 module로 링크 시키는 개념이다. 즉, OS kernel을 module로 구성해서 dynamic linking/loading 시키는 것.
  * module로 구성하게 되면 모든 service가 쓰이는게 아니기 때문에 불필요한 service를 안쓰게 된다.



## 2.9~2.10 OS Debugging/Generation

* **OS Debugging**
  * error 수정 : hw/sw의 error를 발견하고 수정한다.
  * performance tunning : bottle neck(성능을 좌우하는 민감한 부분)을 제거하여 성능을 개선한다.
* **OS Generation**
  * OS를 주어진 컴퓨터의 configuration에 맞추어 조정하는 과정이다.
  * install 과정에서 자동 설정(check)하거나 user와의 interaction을 한다.
  * 원래 CPU/memory/device 등에 따라 customize 해야 했으나 발전하면서 generation 과정을 굉장히 자동화시켜 놓았다.