# 리눅스의 디렉토리 구조

* / : 최상위 디렉토리(=루트)
* /bin : User Binaries, 사용자들이 사용하는 명령들
* /sbin : System Binaries, 시스템 관리자가 사용하는 명령들
* /etc : Configuration Files, 프로그램 설정 파일들
* /dev : Device Files, 디바이스 관련 파일들
* /proc : Process Information, 시스템 프로세스 관련 파일들, 준 파일 시스템
* /var : Variable Files, 바뀔 수 있는 파일들
* /tmp : Temporary Files, 임시 저장소
* /usr : User Programs, 사용자 프로그램들이 위치하는 곳인데 /usr/bin, /usr/sbin, /usr/lib이 여기도 존재한다. /bin, /sbin에서 못찾을 경우 여기서 찾아보자.
* /home : Home Directories, 사용자들이 있는 디렉토리
* /boot : Boot Loader Files, 부트 관련 파일
* /lib : System Libraries, /bin과 /sbin에서 사용하는 라이브러리 파일
* /opt : Optional add-on Applications, 프로그램의 옵션파일들을 저장하고 싶을 때 저장
* /mnt : Mount Directory, 관리자가 파일시스템을 마운트 할 수 있는 디렉토리
* /media : Removable Media Devices, 제거 가능한 디바이스들의 임시 마운트 디렉토리
* /srv : Service Data, 서버 관련 데이터

<img src="https://static.thegeekstuff.com/wp-content/uploads/2010/11/filesystem-structure.png">

## 출처

* https://opentutorials.org/course/2598/14205
* https://www.thegeekstuff.com/2010/09/linux-file-system-structure