# 쉘(shell)

<img src="https://lh3.googleusercontent.com/nB5xJAIqgfxJmhcv869U4iEw1Xtw78hQwXP7QNMAwTLz1zyg5G4sBrMN0zEjwz9xoXNSUMRv8hjzZzxEqDHHyv69Cxxwozm4P7MewkpejaLO86rR1XMc0PB-nD-5nGytNMPQqvQ">

하드웨어를 제어하는 중심적인 역할이 커널이며 쉘은 사용자로부터 명령을 받아 커널에게 전달하는 역할을 한다. 쉘은 사용자의 명령을 해석하는 프로그램이다. UNIX를 만든 사람들은 커널과 쉘을 분리해놓았는데 이렇게 되면 사용자 입장에서 선호하는 쉘을 선택해서 사용할 수 있다. 참고로 `echo $0` 명령어를 통해서 현재 사용중인 쉘이 무엇인지 알 수 있다.



# bash vs zsh

```shell
cd 탭
# bash는 숨김파일까지 표시
# zsh는 일반파일만 표시

cd /h/h 탭
# /home/haram으로 가고 싶을 때 위처럼 입력하면 zsh는 자동완성 시켜줌
# bash는 안됨

cd 현재디렉토리 가고싶은디렉토리
# cd /home/haram/Music으로 들어왔는데 /home/haram/Documents로 가고 싶은 경우
# zsh에선 cd Music Documents를 해주면 가고싶은 디렉토리로 바꿔지지만 bash는 안됨

ls -l *.(txt|log)
# a.txt와 a.log 파일이 있다고 하면 위와 같이 한번에 목록을 출력할 수 있지만 bash 불가능
```

계속 추가~





## 출처

* http://studymake.blogspot.com/2015/05/kernel-shell.html
* https://opentutorials.org/course/2598/14203