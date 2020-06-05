# scanf buffer clear

scanf함수는 C를 처음 배울 때 입력함수로 배우는 아주 기본적이고 대표적인 함수이다. 하지만 문자를 입력받을 때 입력버퍼에 개행이 있는 경우 생각대로 프로그램이 동작하지 않기 때문에 나도 많은 문제를 겪었고 최근에 친구가 알려달라는 코딩에서 이 문제가 발생했다. 그래서 이 이슈에 대해 정리하고 입력버퍼를 비우는 방법 중 어떤 것들이 있는지 알아보려고 한다.

```c
int main(void)
{
    int n;
    char c;
    scanf("%d",&n);
    scanf("%c",&c);
    return 0;
}
```

위에서 문제가 생기는 경우는 n을 입력받고 c를 입력받을 때이다. n에 1을 입력했다고 하면 1은 n에 들어가고 그 다음 개행문자인 '\n'이 입력버퍼에 들어가게 된다. 하지만 c를 입력받을 때 제일 먼저 입력버퍼를 검사하기 때문에 결국 c에는 '\n'이 들어간다. 따라서 c를 입력받기전에, 정확하게는 문자를 입력받기 전에 입력버퍼를 비워줘야 한다.

## buffer clear하는 여러가지 방법

### **fflush(stdin)** = 잘못된 방법

보통 구글링해서 "c언어 엔터 없애기" 이런 식으로 검색하면 나오는 가장 첫번째 방법이다. 물론 표준함수이긴 하지만(비표준인줄 알았다..) 매개변수의 타입 자체가 잘못 전달 되었기 때문에 VS같이 따로 정의를 하지 않은 컴파일러에선 **undefined behavior**가 발생한다. 근데 리눅스도 지원한다고 한다. 지원하지 않는 경우가 Cygwin.([참고](https://stackoverflow.com/a/34247021/9437175)) 어쨌든 왜 이런지 살펴보자. 먼저 정의다.

```c
int fflush(FILE* ostream);
```

> ostream points to an output stream or an update stream in which the most recent operation was not input, the fflush function causes any unwritten data for that stream to be delivered to the host environment to be written to the file; otherwise, <u>the behavior is undefined.</u>

즉, C 표준에서 정의하는 fflush 함수는 매개변수의 타입이 output stream이다. 따라서 당연히 input stream에 대해서는 undefined behavior가 발생하는 것이다. 그렇다면 이 함수는 원래 어떤 역할을 할까? 

```c
printf("Enter a number : ");
```

printf함수를 쓰면 콘솔에 출력되지 않을 때가 있는데 그 이유는 stdout에 대한 ouput이 OS에 의해 buffering 되고, default behavior가 개행 문자를 만났을 때만 출력하게 되어 있기 때문이다. 따라서 **fflush(stdout)**을 쓰면 stdout에 대한 buffer가 비워지고 printf로 출력하려던 메시지가 출력된다. 물론 다음과 같이 쓸 수도 있다.

```c
printf("Enter a number : \n");
```

하지만 개행문자를 출력하고 싶지 않을 수 있기 때문에 위 함수를 쓰면 해결된다. ([출처](https://stackoverflow.com/a/22902085/9437175))



### getchar()

```c
int getchar(void);
```

getchar() 함수는 stdin으로부터 문자를 읽어오는 함수이다. 성공하면 읽어온 문자를 리턴하고 아니면 EOF(End Of File)를 리턴한다. 따라서 EOF가 아닌 개행문자가 아닌문자를 읽어올 때까지 반복해주면 된다.

```C
while((c = getchar()) != '\n' && c != EOF){}
```

제일 위에서 설명했을 때 개행문자가 입력버퍼에 들어간 경우에서 getchar()를 사용하면 입력버퍼에서 개행문자를 읽어온다. 따라서 개행문자가 아닌 문자를 읽어올 때까지 반복해주면 입력버퍼에 있는 모든 개행문자는 제거된다. <u>단, 개행문자를 다 읽어오고 한번 더 읽어오기 때문에 그 점은 주의해야 한다.</u>



### scanf를 사용하지 말자

이러한 이슈 때문에 많은 걸 찾아봤는데 많은 의견들 중에 scanf 함수를 사용하지 말자는 의견이 있었다. 다음 [문서](http://c-faq.com/stdio/scanfprobs.html)를 보면 상대적으로 구조화되고 잘 짜여진 입력함수라서 입력이 실패했을 때 어디서 실패했는지는 알려주지만 어떻게 혹은 왜 실패했는지는 안 알려주기 때문에 에러를 검출하기가 굉장히 힘들다고 한다. 따라서 다른 대안을 제시하는데 바로 char* 타입으로 문자열 형식으로 받아서, 그러니까 개행까지 입력받아서 int형으로 parsing하는 방법이다.

* [atoi](http://en.cppreference.com/w/c/string/byte/atoi)

```c
int atoi(const char *str);
```

성공하면 그 값을 리턴하고 실패하면 0을 리턴한다. 하지만 입력된 문자열을 파싱했을 때의 값이 해당 타입의 범위를 넘어가면 **undefined value**가 리턴된다. 하지만 다음과 같은 경우에도 parsing 해주기 때문에 빠르게 필요한 경우가 아니라면 사용하지 않는 것이 좋다.

```c
printf("%i\n", atoi(" -123junk"));
```

-123 뒤에 "junk"라는 문자열이 이어지는데도 출력은 -123이 된다. 초기 부분만 parsing하고 나머지는 버리는 것인데 이렇게 되도록 하는 기능을 구현하지 않는 이상, 지양하도록 하자.

* [sscanf](http://en.cppreference.com/w/c/io/fscanf)

```c
int sscanf(const char *buffer, const char *format, ...);
```

buffer 문자열에 있는 값들을 format으로 정한 타입들 형식으로 변환한다. 상당히 많은 format을 지원하기 때문에 flexible하고 성공하면 변환이 성공한 buffer의 문자열 개수를 리턴한다. 물론 실패했으면 0을 리턴한다. (첫번째 문자열을 변환하기도 전에 실패했으면 EOF 리턴)

atoi 함수보다는 error reporting이 더 엄격하지만 여전히 타입의 범위를 넘어가면 undefined value로 변환된다.

* [strtol](http://en.cppreference.com/w/c/string/byte/strtol)

이걸 정리하다가 나도 처음 들어본 함수인데 사람들이 parsing으로 제일 많이 추천하는 함수이다.

```c
long strtol(const char *str, char **str_end, int base); // C99
```

C99표준 까지는 long 타입밖에 없지만 C99 이후로는 long long 타입까지 존재한다. 물론 int형으로 사용하기 위해선 캐스팅 해주어야 한다. 성공하면 변환된 문자열에 해당되는 int형이 리턴되고 타입의 범위를 넘어가면 range error가 발생한다. 실패하면 0을 리턴한다.

str은 정수로 변환할 문자열이고, str_end는 정수로 변경하지 못하는 문자열의 시작 위치이다. base는 문자열이 가지고 있는 숫자의 진수(2~32)이다. 만약 str_end가 NULL이라면 시작 위치를 구하지 않는다. 따라서 전체 문자열을 parsing 할건지, 아니면 부분만 parsing 할건지 정할 수 있다. 

그렇다면 결론은 무엇인가?  앞으로 scanf를 완전히 안 쓰진 않을 것 같지만 그래도 이런 이슈에 대해서 기억하고 있도록 하자. 최대한 개행 문자까지 읽어들여서 parsing하는 방법을 사용해보도록 하는게 좋을 것 같다.



