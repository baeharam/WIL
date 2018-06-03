## char*에서 문제가 되었던 UB

### Undefined Behavior

코딩아워를 진행하다가 특정 함수에서 char* 타입의 변수를 초기화하지 않고 프로그램을 실행시키니까 완전히 함수로 안들어가지는 상황이 발생했다. 결국 설명은 못해드렸지만 [stackoverflow](https://stackoverflow.com/q/49555350/9437175)에 물어봐서 무엇이 문제인지 알아낼 수 있었다. 문제가 됐던 코드는 다음과 같다.

```c
char *m_strncpy(char *str1, int count) {
    int i;
    char *new_str;                  // new_str is not initialized, it doesn't
                                    //  point anywhere
    for (i = 0; i < count; i++) {
        new_str[i] = str1[i];       
        // dereferencing an uninitialized pointer is undefined
        // behaviour, anything can happen.
    }
    return new_str;
}
```

초기화하지 않은 new_str 포인터로 값을 참조하고 있는 상황이다. undefined behaviour이기 때문에 어떤 것도 일어날 수 있다고 답변하신 분이 말씀하셨다. 그래서 new_str을 어떤 값으로 초기화해주거나 동적할당을 해주면 해결된 것이었다! 그래서 undefined behaviour가 어떤 것들이 있는지 궁금해서 찾아봤는데 [What Every C Programmer Should Know About Undefined Behavior](http://blog.llvm.org/2011/05/what-every-c-programmer-should-know.html) 에서 어떤 것들이 있는지 확인할 수 있다. undefined behaviour가 문제가 되는 이유는 컴파일러마다 대응하는 방식이 다르기 때문이다. 따라서 초기화 문제나, 특히, 포인터에 관한 이슈에 주의한다면 undefined behaviour를 그나마 줄일 수 있을 것이다. 다음은 [C99 표준](http://www.open-std.org/jtc1/sc22/wg14/www/docs/n1124.pdf)에서 정의하고 있는 UB의 개념이다.

>behavior, upon use of a nonportable or erroneous program construct or of erroneous data, for which this ISO imposes no requirements
>
>NOTE	Possible undefined behavior ranges from ignoring the situation completely with unpredictable results, to behaving during translation or program execution in a documented manner characteristic of the environment (with or without the issuance of a diagnostic meesage), to terminating a translation or execution (with the issuance of a diagnostic message).
>
>EXAMPLE	An example of UB is the behavior on <u>integer overflow</u>

UB는 아무것도 안하거나, 개발 환경에서 정의된 방식으로 그 결과를 도출할 수도 있다는 뜻이다. 이제 어느정도 알았으니 꼭 기억하고 UB를 발생시키지 않는 코드 습관을 들이도록 하자.