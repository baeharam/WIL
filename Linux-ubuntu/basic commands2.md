# 패키지 형식

패키지이름_버전-개정번호\_아키텍쳐.deb 로 명명되며 우분투가 데비안 리눅스에서 파생되었기 때문에 확장명은 *.deb이다. 초창기에 리눅스에 새로운 프로그램을 설치하는 것이 어려워서 설치한 후에 바로 실행할 수 있는 설치 파일을 만들게 되었는데 이게 패키지다.

# dpkg

apt-get 이전의 패키지를 설치하는 명령어인데 의존성 문제 때문에 apt-get을 쓰게 되었다.

```bash
dpkg -l 패키지 // 해당 패키지가 있는지 확인한다.
dpkg --info 패키지 // 해당 패키지의 정보를 보여준다.
dpkg -i 패키지 // 패키지를 설치한다.
dpkg -r 패키지 // 패키지를 제거한다.
```

mysql 5.7을 설치할 때 의존성 문제로 에러가 발생했다. mysql 5.7을 설치하기 위해선 다른 패키지를 설치해야 하는데 어떤 deb 파일을 설치해야 할지 알기가 어렵다. 이렇기 때문에 apt-get 명령어를 사용하는 것이다.

# apt-get

의존성이 있는 다른 패키지를 자동으로 설치해준다. /etc/apt/sources.list 파일에 저장되어 있는 우분투 패키지 저장소에 가서 해당 패키지와 의존성이 있는 패키지를 가져와 자동으로 설치해주는 아주 괜찮은 명령어이다.

```bash
apt-get -y install 패키지이름 // -y를 붙이면 물어보지 않고 바로 설치한다는 것
apt-get update // /etc/apt/sources.list 파일 내용 수정되면 다운받을 패키지 목록 업데이트
apt-get remove 패키지이름 // 제거
apt-get autoremove // 사용하지 않는 패키지 제거
apt-get clean // 내려받기한 파일 및 과거의 파일 제거
```

# apt-cache

의존성 문제를 확인하는 명령어이다.

```bash
apt-cache show 패키지이름 // 패키지 정보 출력
apt-cache depends 패키지이름 // 의존성 정보 출력
apt-cache rdepends 패키지이름 // 해당 패키지에 의존하고 있는 패키지 목록 출력
```

# 패키지의 4가지 종류

패키지를 다운받는 사이트로 4가지 종류가 있다.

* main : 공식으로 지원하는 무료 소프트웨어
* universe : 지원하지 않는 무료 소프트웨어
* restricted : 공식으로 지원하는 유료 소프트웨어
* multiverse : 지원하지 않는 유료 소프트웨어

# 파일압축

## xz

```shell
xz 파일이름 # 파일이름.xz로 압축
xz -d 파일이름.gz # 압축을 풀어줌
xz -l 파일이름 # 압축파일의 정보 출력
xz -k 파일이름 # 압축하고도 기존 파일 남김
```

## bzip2 (=bunzip2)

```shell
bzip2 파일이름 # 파일이름.bz2로 압축
bzip2 -d 파일이름.gz # 압축해제
bzip2 -k 파일이름 # 압축하고도 기존 파일 남김
```

## gzip (=gunzip)

```shell
gzip 파일이름 # 파일이름.gz로 압축
gzip2 -d 파일이름.gz # 압축해제
```

## zip/unzip

```shell
zip 파일명1.zip 파일명2 # 파일명2를 파일명1.zip으로 압축
unzip 파일명.zip # 파일명 압축해제
```

# 파일 묶기

윈도우에선 파일을 묶고 압축까지 동시에 해주지만 리눅스에선 파일묶기를 따로 해야 한다. 그걸 해주는 명령어가 tar이다.

* c : 새로운 묶음을 만든다.
* x : 묶인 파일을 푼다.
* t : 묶음을 풀기 전에 묶인 경로를 보여준다.
* C : 묶음을 풀 때 압축을 푸는 것으로 지정된 디렉터리에 풀며 지정하지 않으면 묶을 때와 동일한 디렉터리에 푼다.
* f(필수) : 묶음 파일의 이름을 지정하는 것으로 원래 tar는 테이프 장치 백업이 기본이라 생략할 경우 테이프로 보내진다.
* v : visual, 파일이 묶이거나 풀리는 과정을 보여준다.
* J : tar + xz
* z : tar + gzip
* j : tar + bzip2

```shell
tar cvf my.tar /etc/systemd #c(묶고) v(과정보고) f(이름지정)
tar tvf my.tar # t(경로보고) v(과정보고) f(이름지정)
tar Cxvf newdir my.tar # C(디렉터리 설정해서 압축해제) x(묶음풀고) v(과정보고) f(이름)
tar xvfJ my.tar.xz # x(묶음풀고) v(과정보고) f(이름지정) J(다시 묶고 압축)
```

# 파일 찾기

* 옵션 : -name(이름) -user(소유자) -perm(허가권) -size(크기)...
* action : -print(기본 값), -exec(외부 명령 실행)

```shell
find /etc -name "*.conf" # /etc 디렉터리 하위에 확장명이 conf인 파일검색
find /home -user haram # /home 디렉터리 하위에 소유자가 haram인 파일 검색
find ~ -perm 644 # 현재 사용자의 홈 디렉터리 하위에 허가권이 644인 파일 검색
find /usr/bin -size +10k -size -100k # /usr/bin 디렉터리 하위에 10KB~100KB인 파일 검색
find ~ -size 0k -exec ls -l {} \; # 홈 디렉터리에서 크기가 0KB인 파일 목록 출력
# -exec 이전 부분이 {}안에 들어가서 실행되는 것이다. -exec와 \;는 외부명령어의 시작과 끝
```

이외에도 which/whereis/locate 등이 있다.



# wget

네트워크를 통해 파일을 다운로드 받는다.

```shell
wget 다운로드 주소
wget -O a.txt 다운로드 주소 # 다운로드 주소로부터 다운받아 a.txt로 저장한다.
```



# 파이프라인과 grep

grep은 특정 문자열이 있는 행을 출력하는 명령어이며 파이프라인은 여러 명령어를 이어주는 역할로  앞에 오는 명령어의 결과가 뒤에 오는 명령어의 입력값이 되도록 한다.

```shell
grep 하람 a.txt # a.txt에서 "하람"을 포함한 행 출력
grep -n 하람 a.txt # 행 번호를 함께 출력
grep -l 하람 * # 현재 디렉토리에서 "하람"을 포함한 파일들 출력
grep -v 하람 * # 현재 디렉토리에서 "하람"을 포함하지 않은 파일들 출력
grep -r 하람 * # 현재 디렉토리 및 하위 디렉토리에서 "하람"이 들어간 문자열 출력

# 파이프라인과 같이 사용
ls --help | grep sort # ls --help에서 sort가 들어간 행 출력
```



# I/O Redirection

UNIX 계열의 운영체제에선 명령어가 실행되면 프로세스로 취급되며 화면에 출력하기 위해 standard output을 사용한다. 그러나 명령어의 결과를 파일에 저장하고 싶다면 어떻게 해야 할까? `>`를 사용하면 간단하게 할 수 있다. 원리는 모니터에 출력되는 것을 파일로 리다이렉션 시키는 것이다.

```shell
ls -l > result.txt # ls -l의 결과를 result.txt에 담겠다.
```

하지만 standard output 말고도 에러가 발생할 경우 보여주는 standard error라는 것도 있는데 이건 `2>`를 사용한다. 원래의 `>`는 사실 standard output을 의미하는 `1>`이기 때문이다.

```shell
# 없는 파일을 지운다고 해보자.
rm a.txt 2> result.log # rm a.txt의 에러결과를 result.log에 담겠다.
```

Standard ouptut 뿐만 아니라 input도 리다이렉션 할 수 있다. cat과 head를 이용해보자.

```shell
cat < a.txt # a.txt를 cat의 input으로 넣는다.
# cat a.txt와의 차이점은 이건 command argument라는 것
head < a.txt # a.txt를 head의 input으로 넣는다.
# head a.txt와의 차이점은 위와 마찬가지
```



