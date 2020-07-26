## 프로세스 치환 (Process Substitution)

쉘 스크립트를 작성하는데 stderr(Standard Error)로 출력되는 에러 메시지를 파이프(`|`) 로 잡지 못하는 걸 깨닫고 찾아본 결과, 여러가지 해결방법이 있었지만 가장 간단하고 명료한 해결방법이 바로 프로세스 치환이었다. 이 방법은 stderr의 출력값을 특정 파일로 저장해서 해당 파일을 `grep` 으로 잡는 것이다.

```bash
$ [명령어] > >(grep -q [찾고자 하는 문장])
```

리서치를 해보니 stdin(Standard Input)으로 문자열을 리다이렉션 할 때 자주 사용한다고 한다. 예를 들면 이런식이다.

```bash
$ cat < <(echo "어떤 문자열");
$ 어떤 문자열
```

위와 같이 엄청 간단하게 사용할 수도 있고 **어떤 명령어의 결과값을 특정 명령어의 입력으로 줄 때** 도 유용하게 사용할 수 있다. 예를 들면 `ls` 명령어의 결과값을 `while` 의 입력으로 줄 때가 되는 경우가 그 예가 된다. "프로세스 치환" 이라는 명명이 된 이유는 특정 프로세스의 결과값을 특정 프로세스의 입력값으로 "치환"하기 때문이다. 아래 정의를 보면 확인할 수 있다.

> *Process substitution* feeds the output of a [process](https://tldp.org/LDP/abs/html/special-chars.html#PROCESSREF) (or processes) into the `stdin` of another process.

아까 특정 파일에 저장한다고 했는데 실제로 어디에 저장되는지 `echo` 로 확인할 수도 있다.

```bash
$ echo <(echo "테스트")
$ /dev/fd/14
```

내 맥에선 `/dev/fd/14` 로 저장되고 있다.

## 참고

* [Process Substitution(Subshell)](https://www.nemonein.xyz/2019/08/2400/)
* [Chapter 23. Process Substitution](https://tldp.org/LDP/abs/html/process-sub.html)