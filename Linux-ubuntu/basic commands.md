# ls

```bash
ls // 일반 파일들 보여줌
ls -a // 숨김파일들까지 보여줌
ls -l // 긴 이름 포맷으로 보여준다.
ls -al // 숨김파일들을 긴 이름 포맷으로 보여줌
```

# 시스템 종료

```bash
poweroff
shutdown -P now
halt -p
init 0
```

# 가상 콘솔

GUI 환경의 화면을 X 윈도라고 하며 1~7번의 가상 콘솔중 7번째 콘솔이다 `Ctrl`+`Alt`+`F1`~`F7`으로 전환할 수 있다.

# history

```bash
history // 지금까지 사용한 명령어 리스트 보여줌
history -c // 삭제
```

# vim 

vi 에디터를 향상시켜서 vi Improved, 즉 vim이라고 불리는 에디터로 가장 많이 사용된다. i나 a로 입력모드로 갈 수 있으며 esc누르면 다시 명령모드로 전환된다.

* i : 현재커서부터 입력, a : 다음칸부터 입력, o : 다음 줄 부터 입력
* I : 현재 커서 줄 맨 앞부터 입력, A : 현재 커서 줄 맨 뒤부터 입력, O : 현재 커서 이전 줄에 입력
* j : 아래로 이동, k : 위로 이동, l: 오른쪽으로 이동, h : 왼쪽으로 이동
* gg : 첫 행으로 이동, G : 끝 행으로 이동, x : 현재 커서 클자 삭제, dd : 현재 커서 행 삭제
* yy : 현재 커서 행 복사, p : 불여넣기 

# rm

```bash
rm a.txt // 파일 삭제 (내부적으로 rm -f)
rm -i a.txt // 확인하고 삭제
rm -f a.txt // 바로 삭제
rm -r a // 디렉터리와 하위 디렉터리까지 전부 삭제
```

# cp

```bash
cp a.txt b.tx // 파일복사
cp a -r b // a 디렉터리를 b 디렉터리로 복사
```

# touch

빈 파일 생성, 있으면 최종 수정시간 반영

# mv

```bash
mv a b c d // a,b,c를 b로 이동
mv a.txt b.txt // a.txt이름을 b.txt로 변경
```

# mkdir

```bash
mkdir -p a/b // a디렉터리가 없으면 자동 생성
```

# head, tail

파일의 앞 10행, 뒤 10행 출력, -숫자하면 그 숫자만큼 출력

```bash
head -3 a.txt // 앞 3행
tail -4 a.txt // 뒤 4행
```

# more, less

페이지 단위로 화면에 출력, 스페이스는 다음페이지로 이동, B누르면 앞 페이지로 이동, less는 기능을 확장시킨 것으로 page up/down 사용 가능. +숫자하면 그 숫자 행부터 출력한다.

# group

```bash
adduser user1 // 사용자 생성
adduser --uid 1 user1 // 사용자 아이디를 1로 지정
adduser --gid 1 user1 // 사용자를 그룹 아이디가 1인 그룹에 포함시킴
adduser --home /home1 user1 // 사용자의 홈 디렉터리를 /home1으로 지정
adduser --shell /bin/csh user1 // 사용자의 기본 쉘을 /bin/csh로 지정
```

# 파일과 디렉터리의 소유/허가권

```
-|rw-r--r--|1|root|root|0|7월 15일 16:11|sample.txt
```

|를 기준으로 각각 속성이 정해져 있다.

* 파일 유형 : 디렉터리(d), 파일(-), 블록 디바이스(b), 문자 디바이스(c), 링크(l)
* 파일 허가권 : 첫번째 rw-는 소유자의 파일 접근 권한으로 read/write 모두 가능, 2번째 r--은 그룹의 파일 접근 권한으로 read만 가능, 3번째 r--은 그 외 사용자의 파일 접근권한으로 read만 가능하다. (x는 실행 가능하다는 의미, 해당 디렉터리로 이동하려면 x권한이 반드시 필요)
* 링크 수 : 1
* 파일 소유자 이름 : root
* 파일 소유그룹 이름 : root
* 파일 크기 : 0 byte
* 마지막 변경 날짜시간
* 파일 이름

r,w,x가 있으므로 3개의 비트로 표시할 수 있다. 허가권이 숫자로 6이라면 110이므로 rw-로 표시할 수 있고 허가권이 7이라면 111이므로 rwx로 표시할 수 있다.

# chmod

비트 단위로 파일의 허가권을 변경하는 명령어, root 사용자나 소유자만 실행 가능하다.

```bash
chmod 777 a.txt // 모든 사용자에게 rwx 허용 (777은 rwx rwx rwx를 뜻함)
chmod u+x a.txt // 소유자에게 실행권한 허용
chmod u-wx a.txt // 소유자에게서 쓰고 실행하는 권한 제거
chmod g+rx a.txt // 그룹에게 읽고 실행하는 권한 허용
chmod o+rwx a.txt // 그 외 사용자에게 모든 권한 허용
```

# chown, chgrp

파일의 소유권 변경, 소유자나 그룹을 변경하는 명령어이다.

```bash
chown haram a.txt // haram으로 소유자 변경
chgrp haram a.txt // haram으로 그룹 변경
chown haram.haram a.txt // haram으로 소유자/그룹 변경
```

# ln

타겟 파일에 링크를 만드는 명령어 하드링크/소프트링크가 있음. inode라는 개념에 대해 알아야 하는데 리눅스/유닉스에서 사용하는 자료구조로 모든 파일이나 디렉터리는 각자 1개씩의 inode가 존재한다. 각 inode에는 소유권, 허가권, 파일 종류, 실제 위치 등이 있으며 이런 inode들이 모여있는 것이 inode 블록. 

```bash
ln test hardlink // test파일의 하드링크 생성
ln -s test softlink // test파일의 소프트링크 생성
ls -il // inode 번호를 제일 앞에 출력
```

실제 실행해보면 하드링크와 원본파일은 inode가 같지만 소프트링크는 inode가 다름. 소프트링크는 원본파일에 대한 포인터를 갖기 때문에 크기도 다름. 이런 점에서 원본파일이 다른 디렉터리로 이동할 경우 연결이 끊어진다.