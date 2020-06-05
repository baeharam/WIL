# C 기본 파일 입출력

소켓 프로그래밍을 하면서 리눅스에서 파일 입출력을 같이 하게 되었는데 표준 파일 입출력도 제대로 모르는 것 같아서 다시 정리한다. [코딩도장](https://dojang.io/mod/page/view.php?id=607)을 참고했다.

# fprintf

서식을 지정해서 파일에 쓰는 함수이다. 성공하면 쓴 문자열의 길이를, 실패하면 음수를 리턴한다.

```c
// fprintf(파일포인터, 서식, 값1, 값2...)
fprintf(stdout, "%s\n", "hello world!");
```

# fscanf

서식을 지정해서 파일을 읽는 함수이다. 성공하면 읽어온 값의 개수를, 실패하면 EOF(-1)를 리턴한다.

```c
// fscanf(파일포인터, 서식, 변수의 주소1, 변수의 주소2...)
int a = 0;
fscanf(stdin, "%d", &a);
```

# fputs

서식없이 문자열을 그대로 쓰는 함수이다. 성공하면 음수가 아닌 값을, 실패하면 EOF(-1)를 리턴한다.

```c
// fputs(버퍼, 파일포인터)
fputs("hello world!", stdout);
```

# fwrite

서식없이 문자열을 쓰는 것은 fputs와 같지만 크기와 횟수를 지정할 수 있다. 성공하면 쓰기 횟수를, 실패하면 지정된 쓰기 횟수보다 작은 값을 리턴한다.

```c
// fwrite(버퍼, 쓰기크기, 쓰기횟수, 파일포인터)
fwrite("hello world\nhello world\n", sizeof("hello world"), 2, stdout);
```

# fgets

서식없이 문자열을 그대로 읽는 함수이다. 성공하면 읽은 문자열의 포인터를, 실패하면 NULL 리턴.

```c
// fgets(버퍼, 버퍼크기, 파일포인터)
char buffer[20];
fgets(buffer, 10, stdin);
```

fgets 함수에서 주의할 점은 더 읽을 크기가 남았다 하더라도 중간에 `\n`이 끼어있으면 딱 그때까지만 읽는다는 것이다.

# fread

fwrite와 마찬가지로 읽기크기와 읽기횟수를 지정할 수 있다. 성공하면 읽기 횟수를, 실패하면 지정된 읽기 횟수보다 작은 값을 리턴한다.

```c
// freadd(버퍼, 읽기크기, 읽기횟수, 파일포인터)
char buffer[21] = { NULL };
fread(buffer, 10, 2, stdin);
```

fread에서 주의할 점은 반드시 처음 버퍼로 사용될 배열을 만들 때 `NULL`로 초기화 해주어야 한다는 것이다. 초기화해주지 않으면 이전에 메모리에 있던 값, 즉 쓰레기값이 같이 출력되게 된다. 코딩도장의 그림을 보면 이해하기 쉬울 것이다.

<img src="https://dojang.io/pluginfile.php/674/mod_page/content/31/unit70-6.png">