# Operating System Concepts 8장

## 8.1 Background

CPU가 memory에 있는 process를 수행하는데 memory에 올리고 내리는 것을 어떻게 할 것인가에 대한 것들이 8장에서 다룰 내용이다.

### CPU의 memory access

CPU는 memory는 직접 access 할 수 있지만 보조기억장치는 직접 access 할 수 없고 I/O 를 통해서만 가능하다. CPU가 memory에 access 할 때, 다음 작업들을 수행한다.

* **Instruction fetch** : memory에서 명령어를 가져온다.
* **Operand fetch** : memory에서 명령어로 처리할 데이터를 가져온다.
* **연산결과를 저장할 address** 를 가져온다.

예를 들어 더하기 명령어를 가져왔다면 operand fetch로 가져온 데이터에 더하기 연산을 하여 연산결과를 저장할 memory address에 저장하는 것이 된다.

위에서 말했다시피 CPU는 직접 access할 수 있는게 한정되어 있는데, memory와 register이다. 당연히 register가 속도 면에서 훨신 빠르고 이 때문에 memory access의 속도를 개선하기 위해 memory의 일부를 register로 가져와서 수행하는 cache를 활용한다.

### 각 process는 자신이 access 할 수 있는 memory space가 제한되어 있다.

process는 자신의 memory space를 유지하여 multiple process를 가질 경우 다른 process으로부터 접근할 수 없기 때문에 망가질 우려가 없다. 이러한 memory space를 관리하는 것에는 2개의 register가 이용된다.

* **Base register** : the smallest legal physical memory address로 memory의 시작 주소를 가지는 register
* **Limit register** : the size of the range로 memory space의 크기를 나타낸다.

다음 2개의 register는 OS에 의해서 관리되며 **context switching 해야 할 경우 2개의 register 내용만 바꿔주기 때문에** 좋은 아이디어라고 할 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/40465418-85d35d36-5f5b-11e8-8bdf-a9dbf2b0e08a.JPG" height="350px">

다음 그림을 보면 base/limit register가 어떻게 사용되는지 알 수 있는데, CPU가 memory address로 memory의 process를 수행하려고 할 때, 2번의 검사를 거친다.

1. **base register의 값보다 크거나 같은가?** (base register의 값이 process의 memory space 시작 주소니까 당연히 크거나 같아야 한다.)
2. **base register의 값 + limit register의 값보다 작은가?** (process가 차지하는 총 memory space의 크기에 시작 주소를 더한 값보다는 작아야 한다.)

결론적으로, base/limit register를 관리한다는 것은 process가 접근할 수 있는 memory 주소의 상한과 하한을 관리한다는 말과 동일하다고 볼 수 있다.

### Address binding (주소공간의 할당)

Binding이란 어떠한 것의 속성이 결정되는 것을 말하며 binding time이란 그 속성이 결정되는 시점을 말한다. 따라서 address binding이란 address의 속성이 결정되는 것이며 2가지 시점으로 결정될 수 있다.

* **Static binding** : 실행시간 이전에 결정되고 실행시간에 변하지 않는 것으로 compile/load time에 결정되며 efficiency가 높고 flexibility가 낮다. (빠른 binding)
* **Dynamic binding** : 실행시간에 결정되는 것으로 execution time에 결정되며 flexibility가 높고 efficiency가 낮다. (늦은 binding)

여기서 말하는 flexibility는 상황 변화에 따라 자유롭게 변하는 것을 의미한다.

### Address binding time

Memory 상에 올려질 program의 address 결정 시점으로 3가지로 나뉜다.

* **Compile time binding** : compile 할 때 <u>absolute code</u>를 생성하는 binding으로 절대 주소를 사용한다. 초창기 컴퓨터가 사용했으며 location의 변경이 필요할 경우 compile을 다시 해야 된다는 단점이 있다.
* **Load time binding** : load 할 때 결정되는데 compiler가 <u>relocatable code</u>를 생성하는 bniding으로 상대 주소를 사용하며 위치변경이 가능한데, load 할 때 process의 memory address는 변할 수 있다.
* **Execution time binding** : 현대의 대부분의 OS가 지원하는 binding으로 실행시간에 process 또는 process를 구성하는 일부 segment의 위치를 변경할 수 있지만 <u>special H/W</u>가 필요하다.

<u>Compile/Load time binding은 static binding</u>이며 <u>Execution time binding은 dynamic binding</u>이다.

### 여러가지 용어와 특징

* **Logical address** : CPU에 의해 생성되는 address로 program을 짤 때 address를 다루는 곳에 사용된다.
* **Physical address** : memory의 실제 주소 값이다.

Compile time, load time binding에서는 logical address와 physical address가 동일하지만 execution time binding에서는 동일하지 않다.

Logical address는 virtual address라고도 부르며 program 상에서 연상되는 주소를 의미한다. <u>Virtual address와 physical address 간의 runtime mapping</u>이 이루어지는데, 그 작업을 해주는 장치가 **MMU(Memory Management Unit)** H/W이다.

MMU의 단순한 예시에는 **relocation register** 가 있는데, base register와 유사하며 이걸 사용하면 virtual address와 physical address의 mapping이 가능해진다. 

<img src="https://user-images.githubusercontent.com/35518072/40466152-2b043994-5f5f-11e8-8bdf-503607c6add1.JPG" height="350px">

다음 그림은 relocation register의 역할을 설명하는데 user program이 logical address를 346을 가진다면 MMU인 relocation register가 가진 base register의 주소값, 14000을 더해서 physcal address인 14346에 mapping 된다.

### Dynamic loading

실행 중에 program을 memory에 loading 하겠다는 것으로 program의 일부 모듈(fuction이나 procedure로 routine이라고 불림)이 call 될 때 비로소 loading 하겠다는 것이다.

* **장점** : program의 실행에서 모든 routine이 사용되는 것이 아니므로 사용하지 않는 routine은 memory에 올리지 않는 것으로 **memory utilization을 높일 수 있다.**  (=일부 memeory를 다른 작업에 사용 가능)
* **조건** : 모든 routine은 disk 상에 있는데, **relocatable format** 으로 유지하여 필요할 때마다 다른 위치로 올릴 수 있어야 한다. 
* Dynamic loading은 program에 용량이 크고 사용빈도가 적은 routine이 있을 경우 유리하다.

### Dynamic linking and Shared library

Linking 이란 함수와 함수와의 관계, 혹은 global 변수와의 관계를 해결하는 것으로 **외부의 call을 해결하는 것이며**, dynamic linking이란 linking 작업을 실행 시간으로 늦추는 것으로, **Dynamic Linking Library(DLL)** 를 따로 두어서 program에서 DLL이 call 될 때, linking 시키는 것이다. 

DLL의 장점은 3가지가 있다.

* 필요할 때 불러서 쓸 수 있다.
* 여러 process 들이 library code 1개 copy를 공유하는 형태가 가능하며 이걸 shared library 라고 하고 동일한 code를 공유해야 하므로 read-only code로 공유한다.
* Library만 version 변경하면 전체 program이 자동으로 version 변경 가능하다.

### Stub

Stub이란 **library를 call 하는 위치에 두는 small piece of code** 로 다음 2가지를 내장한다.

* 아직 load 되지 않은 어떤 library를 load하는 방법 (처음으로 load)
* Memory에 있는 어떤 library routine을 찾아가는 방법 (다른 process에 의해 load된 것)

Stub을 통해서 dynamic linking을 **한번 수행하면, 단순 call처럼 되어서 추가 overhead가 발생하지 않는다.**

> linking이 compiler가 만들어낸 object module 들을 연결하는 것이고 loading은 실행하기 위해서 memory에 load하는 것이다. 따라서, dynamic loading은 실행할 때 system library나 필요한 다른 routine을 memory에 같이 load하는 것이고, dynamic linking은 실행할 때, 다른 routine을 연결하는 것이다.



## 8.2 Swapping

process의 내용을 일시적으로 memory에서 disk(backing store)의 가장 빠른 부분으로 꺼낼 수 있도록 하는 것으로, 예시로, 높은 priority process의 실행을 위하여 낮은 priority process를 잠시 swap out 시키고 끝났을 때 swap back하는 것이 있다.

### Swap back 될 때

* memory 상의 동일 위치로 swap back 되는 경우 : static address binding
* memory 상의 위치 변경 가능하여 swap back 되는 경우 : dynamic address binding (dynamic relocation이 가능하다.)

### 장/단점

* 장점 : memory 용량의 제약을 극복한다.
* 단점 : disk로 swapping 하는 시간으로 인한 속도 저하가 발생할 수 있다.

따라서 용량과 속도는 서로 상충된다고 볼 수 있다.



##8.3 Contiguous memory allocation

memory의 연속적인 공간에 올리는 것으로 memory 상에서 각 process가 (한 개의) 연속적 공간에 allocate 되는 것을 말한다.  예를 들어, process의 크기가 840KB라면 그 이상의 memory space가 있어야 하는 것이다.

memory의 일부는 OS가 차지하고 있으며, bounding 관리하는 것이 OS의 memory 관리 역할이다.

##Memory mapping과 protection

<img src="https://user-images.githubusercontent.com/35518072/40597940-b0f2200c-627f-11e8-98bb-a12a59735568.JPG" height="350px">

* **Memory mapping** : CPU가 logical address를 생성하면 limit register(length)와 비교해서 더 작으면 넘어가고 아니면 addressing error가 발생한다. 그 다음으로 base register의 값을 가지고 있는 relocation register의 값을 더하면 physical address를 알 수 있게 된다.
* **Protection** : memory mapping에서 physical address로 변환할 때, logical address가 유효한 address인지 검사하기 때문에 protection이 된다.

## Memory allocation

1. **Fixed size partitons**

Partition 당 1개의 process를 allocation 하는 것으로 partition의 수는 <u>degree of multi-programming을 control</u> 할 수 잇다. Partition은 정해진 크기로 memory가 나눠지는 것이다.

2. **Variable size of partitions**

초기에 user processes에게 모든 memory space는 available 하며, 이러한 available memory는 hole이라고 불리는 하나의 large block으로 여겨진다. 이러한 hole 중에서 process를 어디로 놓을 것인가는 OS가 수행하는 방법에 대한 문제이다.

### Dynamic storage(memory) allocation

process를 할당하기에 제일 적합한 hole을 결정하는 것으로 3가지 방법이 있다.

1. **First fit : allocate the first that is big enough (process가 들어가기에 충분한 첫번째 hole에 넣는다.)**

장점 : Search가 빠르다 / 단점 : 몰려서 allocation 되는 clustering이 발생한다.

2. **Best fit : allocate the smallest hole that is big enough (process가 들어가기에 충분한 크기이면서 가장 작은 것)**

장점은 거의 없고 단점은 2가지 있다.

* hole이 연결된 linked-list 전체를 search 해야 해서 속도가 느리다.
* 가장 작은 조각 (fragmentation) 을 남기기 때문에 (produce the smallest leftover hole) 너무 작아 쓸모없지만 available space로 관리해서 search time이 길어진다. (이론적으론 사용가능, 실질적으로는 안 씀)

3. **Worst fit : allocate the largest hole (process를 가장 큰 크기의 hole에 넣는다)** 

Best fit과 같이 쓸모 없는 fragmentation을 남기지는 않지만 다음 단점들이 존재한다.

* linked-list 전체를 search 해야 해서 속도가 느리다는 점은 best fit과 같다.
* 가장 큰 fragmentation을 남기기 때문에 (produce the largest leftover hole) 큰 hole을 우선 없애게 되어 size가 큰 process가 수행할 공간이 없어질 수 있다.

### Fragmentation

이론적으로는 available 하나, 크기가 작아서 사용되지 않는 memory space로 contiguous allocation 때문에 발생하는 문제이다. 왜냐하면 fragmentation의 합이 process의 크기보다 커도 사용불가하기 때문이다.

* **50-percent rule (경험적 법칙)**

allocate 된 양의 50%가 fragmentation으로 생긴다는 통계적 법칙으로 실제 memory 사용은 2/3 정도라는 것.

* **External fragmentation** : 기존의 fragmentation으로, 전체 available space는 process가 load되기에 충분한지만 contiguous하지 않은 경우이다.
* **Internal fragmentation** : allocate 된 것으로 처리된 공간 중에 안쓰는 공간

External fragmentation의 다음과 같은 문제들 때문에 일부러 internal fragmentation을 유도하기도 한다.

1. 어떤 hole이 지나치게 작으면 이 hole의 정보를 유지하는 overhead가 그 hole 자체보다 클 수 있다.
2. 쓸모없는 hole이 available space에 포함되기 때문에 search도 비효율적이다.

external fragmentation을 방지하기 위해 쓸모없을 정도로 작은 공간은 allocation 할 때 포함시키는 것이다.

### External fragmentation의 solution

1. **Memory Compaction**

memory space의 작은 조각들을 합쳐서 큰 조각으로 만드는 것으로 모든 free space를 1개의 large block이 되도록 memory 내용의 위치를 변경하여 재구성한다. (현대의 컴퓨터에선 memory의 크기가 커졌고, 실행 중에 하기가 어렵기 때문에 <u>별로 사용되지 않는다.</u>)

2. **Non-contiguous allocation**

process의 내용을 작은 조각으로 나눠서 allocation 하는 것으로 2가지 방법이 있다.

* Segmentation : 논리적 단위로 분할하는 것으로 함수나 데이터 단위로 분할 할 수 있다.
* Paging : 물리적 단위로 분할하는 것으로 바이트 단위로 분할 할 수 있다.

Paging은 memory space의 크기로 분할하기 때문에 명확해서 관리할 때 편하지만 논리적으로 꼬일 수가 있고 Segmentation은 완화되기는 했지만 memory space의 크기로 분할하는 paging에 비해 <u>여전히 fragmentation이 발생한다.</u> 현대의 컴퓨터는 segmentation을 더 많이 쓴다.



##8.4 Segmentation

**process의 내용을 논리적 단위(segment)로 분할하여 allocation 하는 것으로 internal fragmentation을 해결하지만 external fragmentation이 발생한다.**

<img src="https://user-images.githubusercontent.com/35518072/40598527-20aa941c-6283-11e8-861d-6bad9ebe0ce7.JPG" height="350px">

다음 그림을 보면서 segmentation이 어떻게 작동하는지 보자.

* CPU는 $<s,d>$로 표기된 logical address를 생성하는데 s는 segment number이고 d는 displacement로 offset(base로부터의 상대적 거리)을 의미한다.
* s를 보고 segment table로 찾아간 뒤 s에 해당하는 limit register와 base register를 찾는다.
* offset이 limit register의 값보다 작은지 확인한 후 작으면 통과한다.
* 마지막으로 base register의 값과 offset을 더해서 physical address를 찾아내 allocation 한다.

이제 예제를 보면서 연습해보자.

<img src="https://user-images.githubusercontent.com/35518072/40598623-cbbda560-6283-11e8-9db8-253a6d8a579c.JPG" height="350px">

현재 process는 5개의 segment로 분리되어 있다. 이 때, main program segment를 memory에 할당하는 과정을 진행해보자. 먼저, main program의 segment number는 2이다.

* CPU가 main program segment에 대해서 <2, 220> 이라는 logical address를 생성했다고 가정하자. segment table에서 2에 해당하는 limit register와 base register의 값을 보자.
* limit register는 400으로 d에 해당하는 220과 비교하면 d < limit register 이므로 통과한다.
* 이제, base register의 값인 4300과 220을 더한 physical address에 해당하는 4520에 main program segment를 할당하게 된다.



## 8.5 Paging

**process의 내용을 규격화된 물리적 단위(page)로 분할하여 allocation 하는 것으로 external fragmentation 문제를 해결하지만 internal fragmentation이 발생한다. **

### Paging의 원리

* Physical memory를 **page frame**이라고 불리는 고정크기의 block으로 분할하며 process가 들어갈 자리이다.
* Logical memory를 **page**라고 불리는 동일크기의 block으로 분할하며 process의 규격화된 조각이다.

paging에서의 logical address는 **page number를 가리키는 p**와 **page offset을 가리키는 d**로 나타내는데, page number는 page table의 index이며, page offset은 page의 해당 frame에서의 위치를 말한다. 따라서, segmentation의 notation과 비슷하게 $<p, d>$로 나타낸다.

또한 page size는 2의 제곱수로 나타내는데, page size가 1024라면 offset의 bit 수는 10 bits가 필요하다고 볼 수 있다. 따라서 page size에 따라 logical address가 정해진다고 보면 된다. 물론 역으로 offset을 보고도 page size를 알 수 있는데, offset이 $n$ bits라면 page size는 $2^n$이 된다.

Logical address는 bits로 표시하는데, page number, page offset 순서이므로 MSD(Most Significant Digit)가 page number이고, LSD(Least Significant Digit)가 page offset이다.

<img src="https://user-images.githubusercontent.com/35518072/40762010-61c1f384-64d9-11e8-9793-cd71b06059b7.JPG" height="500px">



위 그림은 CPU가 logical address에 access할 때 p를 보고 page table로 가서 page의 해당 frame number를 가져온 뒤에 그 frame number와 logical address에 있던 page offset을 이어붙여서 해당 frame의 physical address로 mapping 시키고 있는 것을 묘사하고 있다. 실제로 한 번 연습을 해보자.

<img src="https://user-images.githubusercontent.com/35518072/40762330-7d2c2da4-64db-11e8-8e05-c50c58494d52.JPG" height="500px">

위 그림에서 page size는 4 byte이며 physical memory size는 32 byte이기 때문에 5 bit로 physical memory의 address를 전부 표현할 수 있다. 예를들어 알파벳 k의 physical address를 찾아낸다고 하자. k는 logical address 10에 있다. 10은 2진수로 **01010**이고 page size가 4 bytes이므로 하위 2bits인 **10**(=2)은 page offset이며, 상위 2bits가 page number로 **010**(=2)이므로, page table에 가서 frame number를 보면 1임을 알 수 있다.

frame number는 2진수로 **001**(=1)이고 page offset인 10을 붙이면 physical address는 **00110**(=6)이 되며 physical memory에서 해당 address를 보면 k가 있는 것을 알 수 있다. 하지만 이렇게 2진법으로 직접 계산해서 mapping 하는 것은 직관적이지 않을 수 있기 때문에 2진법의 원리인 나눗셈을 통해서 직관적으로 구하는 연습을 하자.

Logical address인 10을 2로 나누면 몫이 2, 나머지가 2가 나오며 이 말의 의미는 page number가 2, page offset이 2라는 뜻이다. 따라서, 바로 page table에서 index가 2인 frame number 1을 알아낼 수 있고 page size인 4를 곱해서 page offset 2를 더하면 physical address 6에 바로 접근할 수 있게 되어 훨씬 쉽다! 

쉬우니까 이번엔 physical memory에 있는 h를 역으로 계산해서 logical address를 알아내보자. h의 physical address는 27인데, 27을 2진수로 나타내면 **11011**(=27)이고 page size가 4 bytes 임으로 하위 2 bits인 **11**(=3)이 page offset이며 상위 3 bits인 **110(=6)**이 frame number이다. Page table에서 frame number인 6에 해당하는 page number가 **001(=1)**이므로 page offset인 **11**(=3)과 붙이면 logical address인 **00111**(=7)이 나오게 된다.

### Paging의 특징

* 규격화된 block이기 때문에 external fragmentation이 발생하지 않는다.
* 하지만, 규격화된 block이라도 마지막 page로 인해 internal fragmentation이 발생할 수 있는데 그 이유는 마지막 page의 크기가 page size보다 작을 경우 주어진 frame에 완벽히 맞춰질 수 없기 때문이다.

### Page size

Page size가 클 수록 3가지 특징이 있다.

* Page table size가 작아진다. (큰 단위로 나눠지기 때문에)
* Internal fragmentation이 커진다. (마지막 page의 크기가 더 작아지기 때문에)
* Disk I/O는 한꺼번에 많이 전송할수록 유리하다. (예시로 C언어의 fread/fwrite로 block 단위 I/O가 있으며, page가 하나의 block이기 때문에 클수록 유리하다. page와는 별개로 disk에서 seek/rotation time이 존재하기 때문에 여러번 읽을수록 overhead가 많이 발생해서 한번에 읽는 것이 좋음)

반대로 page size가 작을수록일 경우에도, 장/단점이 존재하기 때문에 클수록 좋은지, 작을수록 좋은지는 논란의 여지가 있다. 참고로 오늘날의 page size는 보통 4~8KB이다.

### Frame table

page table은 process당 1개가 존재하고, frame table은 system당 1개가 존재한다. frame table이란 OS가 physical memory frame의 상태 정보를 유지하고 있는 table인데 다음 2가지 정보가 있다.

* 각 frame이 available한지에 대한 정보
* 각 frame에 어떤 process의 어떤 page가 올라가는지에 대한 정보

위 정보를 알아야 process를  어떤 위치에 올릴지 알 수 있다. 따라서 paging을 채택한 system은 context switching time이 증가하는데, table들의 정보를 수정해야하는 overhead가 발생하기 때문이다. 이런 이유로 속도 개선을 위한 방법들이 나온다.

### 속도 개선을 위한 H/W Support

* **Page Table Base Register (PTBR)** : page table의 위치를 가리키는 register로 context switching을 할 때, 각 process의 해당 page table을 바꿀 필요가 없이 PTBR만 바꿔주면 된다.
* **Translation Look-aside Buffer (TLB)** : a special fast lookup H/W cahce로 register 급으로 빠르다.

paging에서 memory access는 PTBR에 해당되는 memory의 page table에 접근하는 page table access와 memory access로 총 2번이 발생하는데 (page table도 memory에 있으므로) 만약 page table의 일부를 TLB에 갔다놓으면 원하는 데이터가 TLB에 있을 때 기존의 memory access 2번 하는 것보다 빨라질 수 있다. 그러나 TLB에 원하는 데이터가 없을 경우 다시 memory access를 해야 하기 때문에 더 느릴 수도 있다.

따라서, **원하는 내용이 TLB에 있는 비율을 봐야 하는데 이걸 hit ratio라고 한다.** 예를 들어 다음과 같은 환경의 paging이 있다고 하자.

* hit ratio : 80%
* TLB access time : 20ns
* memory access time : 100ns

이 때, **effective access time (=통계적으로 1번의 access에 대한 기대시간)** 을 구하는 것이 핵심인데, TLB를 안 쓰고 memory access를 2번 한다면 100+100으로 총 200ns가 걸린다. 그렇다면 TLB를 쓸 경우 200ns보다 더 빨라질까?

* TLB에 원하는 데이터 있을 경우(성공) : 0.8 * (20+100) = 96
* TLB에 원하는 데이터 없을 경우(실패) : 0.2 * (20+100+100) = 44

96+44=140<200으로 TLB를 도입하면 시간적으로 이익이 있다는 것을 알 수 있다.

### Protection

page frame 마다 **protection bits**로 속성을 설정할 수 있다.

* read only / read-write
* execution only

속성의 개수에 따라 bit의 개수가 달라지며, 설정에 위배되는 access를 막아서 page frame을 보호한다.

### Valid-Invalid bits

page table entry마다 해당 entry의 유효여부를 표시하는 bit로 memory에 page table이 있을 때, 어디까지가 유효한 page table인지 파악할 수 있으며 중간에 invalid로 설정된 entry의 경우 dynamic loading에서 아직 load되지 않은 page에 해당하는 것으로 볼 수 있다.

일부 system에서는 **PTLR(Page Table Length Register)**을 사용하기도 하는데, 이걸 이용하면 유효한 page table의 size를 관리할 수 있으며 logical address의 유효범위를 check 할 수 있다. (logical address에 해당하는 page number가 PTLR의 값보다 크다면 유효하지 않은 것.)

### Shared page

physical memory의 동일 내용을 여러 process에서 공유 가능한 것으로 page table에서 frame을 같은 곳으로 설정하면 구현할 수 있다.

<img src="https://user-images.githubusercontent.com/35518072/40780472-6c843072-6513-11e8-91f5-f455c38d04dc.JPG" height="500px">



위 그림에서 process 3개가 있으며 모두 같은 에디터 프로그램을 사용하고 있는 것을 볼 수 있고 데이터만 다르다는 것을 알 수 있다. 이 때, 만약 공유하려는 page가 **reentrant code (= non-self-modifying code)** 라면, read-only 접근 code이므로 가장 오른쪽의 physical memory 그림처럼 같은 frame으로 mapping 되어 공유가 가능해진다. 여기서 말하는 reentrant code는 실행 중에 변하지 않는 code를 말한다.



# 8.6 Structure of the Page Table

### Multi-level paging

paging을 할 때 해당 process의 크기가 굉장히 크다면 그에 따른 page table의 크기도 커지며 실제로 사용하지 않는 page에 대한 entry도 page table에 만들어지기 때문에 공간낭비가 심하고 memory에서 그 page table을 관리하기가 힘들어진다. Multi-level paging은 이러한 문제를 해결하는 방법 중 1가지이다.

**"Page table의 size가 큰 경우 여러 단계의 table로 구성할 수 있다. 즉, page table의 table을 구성할 수 있다."**

예를 들어 원래 logical address는 page number p와 displacement offset인 d로 구성되어 있는데 이 때 page number가 속해있는 page table의 주소까지 logical address에 포함시키는 기법을 **"2-level paging"**이라고 한다.

![default](https://user-images.githubusercontent.com/35518072/40900013-a02e2b28-6804-11e8-85f2-b0d9688b2b68.JPG)

다음 그림은 2-level paging이 적용된 32-bit 크기의 logical address이며 page number가 p1과 p2로 나뉜 것을 볼 수 있다. p1은 page table에 대한 table의 index이며, p2는 원래 page table에서 page number이다. 즉, p1을 통해서 page table에 access 할 수 있고 p2를 통해서 physical memory에 access 할 수 있는데 p1과 p2가 모두 table이므로 memory에 있어서 **결과적으로는 memory를 3번 access 하는 형태가 된다.**

이렇게 page number를 2개로 쪼개면 page table을 contiguous 하게 저장하지 않아도 되는데, 그 이유는 page table의 table에서 page table의 index를 보고 찾아가면 되기 때문이다. 아래 그림에서 2-level paging의 동작방식을 설명해주는데 page table의 table을 **outer page table**이라고 부르며, 기존의 page table을 **inner page table**이라고 부른다.

![default](https://user-images.githubusercontent.com/35518072/40900254-b2886260-6805-11e8-851a-70bf5c8c845c.JPG)

p1을 통해서 outer page table을 보고 page table의 index를 찾은 다음 해당 page table을 보고 page number를 찾아서 page frame을 계산한다. 그리고 해당 frame에 page를 할당하고 있다.

수업에서 나온것은 아니지만 각 table의 크기에 대해서 보면 책에서 page size를 4KB라고 줬는데 page table의 table도 하나의 page로 취급되어 메모리에 저장되기 때문에 그 크기가 4KB이다. 보통 page table의 entry 1개당 4 byte로 말하기 때문에 page table의 table에서 entry의 개수는 총 1K(=1024)개 존재한다는 것을 알 수 있다. 또한 page size는 4KB이기 때문에 4096 bytes = $2^{12}$ bytes 이고 memory는 byte 단위로 구분하므로 offset으로 12-bits가 필요하다. 그 다음으로 page table의 entry 개수가 1024개 = $2^{10}$ 개이므로 p2는 10-bits가 필요하고 남은 10-bits는 p1을 나타내게 된다.

### 속도개선을 위한 방법

memory를 3번 access하기 때문에 속도가 느릴 수 밖에 없다. 그렇기 때문에 개선방법에 대해 생각을 해보아야 하는데, outer page table의 일부를 TLB로 구성할 경우 기존 paging의 개선과 비슷하게 3번의 memory access 중 1번이 TLB access로 바뀌기 때문에 hit ratio가 충분하다면 속도가 개선된다는 것을 알 수 있다. 물론 TLB access를 했는데 원하는 page number가 없다면 다시 memory access를 하여야 하므로, 3번의 memory access와 1번의 TLB access가 발생하여 굉장히 느리다.

따라서 effective access time을 계산해야 하는데 hit ratio가 충분히 높지 않을 경우 기존 memory access를 3번 하는 것보다 느릴 수 있기 때문이다. 또한 기존에 2번이었던 memory access가 1번 증가했으니 계산이 조금 다르게 될 것이다. 위에서 기존에 계산했던 것을 이용해서 연습해보자.

- hit ratio : 80%
- TLB access time : 20ns
- memory access time : 100ns

먼저 TLB에 access 하여 성공했을 경우는 TLB access time + page table에 대한 memory access time + physical memory access time이므로 0.8*(20+100+100) = 176이다.

TLB access에 실패했을 경우는 TLB access time + page table의 table에 대한 memory access time + page table에 대한 memory access time + physical memory access time이므로 0.2*(20+100+100+100)=64이다.

따라서, TLB를 안 썼을 경우 300인데 TLB를 쓰게 되면 240으로 속도가 빨라진다는 것을 알 수 있다.