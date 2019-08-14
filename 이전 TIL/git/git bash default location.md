# git bash 기본 경로 설정

계속 미루다 정리한다. git bash를 사용할 때 기본 경로로 되어있지 않으면 `cd`를 이용해서 계속 이동해야 하는 귀찮음이 있었기 때문에 계속 경로 설정을 찾아봤었다. 그런데 정리하지 않으니 계속 찾아서 오버헤드가 너무 발생하니 정리하자.

먼저 bash를 열고 `vi .bashrc` 명령어를 입력하면 .bashrc 파일이 열리는데 bash를 부팅할 때 쓰이는 파일이라고 한다. 여기다 `cd 경로`를 입력하고 `:wq`를 치고 끄면 설정된다.

http://www.sysnet.pe.kr/Default.aspx?mode=2&sub=0&detail=1&pageno=0&wid=11046&rssMode=1&wtype=0 



# 더해서 cmd 경로까지

mysql이나 jekyll 로컬 테스트 때 기본 경로를 설정한 적이 있어서 덤으로 cmd 기본 경로까지 설정하도록 하자. cmd의 바로가기를 만든 뒤에 속성 창을 연다. 거기서 경로되어 있는 부분에 한칸 띄고 `/S /K "cd 경로"` 추가하면 해당 경로로 시작한다.

http://aw2s0m2.blogspot.com/2011/02/cmd.html 