## memset 함수

[BOJ 11062번](https://www.acmicpc.net/problem/11062) 문제를 풀다가 많은 분들이 memset 함수를 사용하여 전역변수를 -1로 초기화 하시길래, 정의와 원리를 조금 더 면밀히 알아보았다.

먼저 memset함수는 주어진 크기의 bytes를 전부 1byte씩 char로 변환된 int형 변수로 초기화 하는 함수이다. 무슨 말인지는 다음 정의와 코드를 보자.

```c++
#include <cstring>
void* memset(void* dest, int ch, std::size_t count);
```

보다시피 void*가 리턴 타입이고 dest, ch, count 3개의 변수를 지닌다.  
[Dietmar Kuhl](https://stackoverflow.com/a/13327186/9437175) 라는 분이 stackoverflow에서 간단하게 짜주신 코드를 보면서 어떤 역할을 하는지 알아보자.

```c++
void* memset(void* dest, int ch, std::size_t, count){
    char *p = (char*)dest;
    for(std::size_t i=0; i!=count; ++i)
        p[i] = ch;
    return dest;
}
```

void*로 온 dest를 char\*로 캐스팅해서 count만큼의 bytes를 도는 것을 볼 수 있다.  
char\*로 캐스팅하는 이유는 1byte씩 접근하여 초기화 하기 위함이다. 하지만 처음 이걸 사용할 때 아래와 같이 사용했기 때문에 꽤 낭패를 본 적이 있다.

```c++
memset(array2D, 100, sizeof(array2D));
```

머릿속으로는 array2D라는 2차원 배열의 모든 원소가 100으로 초기화 될 것 같지만 전혀 이상한 값이 들어가게 된다. 그 이유는 이 함수가 1byte씩 초기화를 시키는데 int형이 char형으로 캐스팅되는 과정에서 손실이 발생할 수 있고 손실이 없더라도 이상한 bytes의 조합으로 인해 값이 이상해지기 때문이다. 하나만 더 살펴보자.

```c++
int a[2][2];
memset(a,3,sizeof(a));
```

다음 코드를 보면 2차원 배열인 a의 각 원소를 3으로 초기화한다. char의 최댓값인 255를 넘지 않기 때문에 overflow도 발생하지 않을 것이다. 그러나 컴파일하면 a\[0]\[0]=50529027이라는 해괴한 값이 나온다. 그 이유는 int형이 4byte이기 때문에 a의 크기는 2\*2\*4=16byte이다. 하지만 1byte씩 00000011이라는 값으로 초기화하기 때문에 원래 4byte로 수를 표현하는 2차원 배열 a의 값은 이상한 조합의 숫자가 되는 것이다. 

그러면 왜 0과 -1은 가능한 것일까? 그 이유는 간단하다. 0과 -1의 bit는 전부 0과 1이기 때문.

```c++
int a = 0; // 00000000 00000000 00000000 00000000
char b = 0; // 00000000
int c = -1; // 11111111 11111111 11111111 11111111
char d = -1; // 11111111
```

위 코드처럼 되어있기 때문에 1byte씩 00000000 혹은 11111111로 초기화를 시켜줘도 결국 int형 배열의 모든 bit는 0이나 1이되어서 0이나 -1이 되는 것이다. [Why does “memset(arr, -1, sizeof(arr)/sizeof(int))” not clear an integer array to -1?](https://stackoverflow.com/a/7202474/9437175) 을 참고하면 도움이 될 것이다.



