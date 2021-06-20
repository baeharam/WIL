## git switch와 git restore

기존에 브랜치를 변경하는 명령어는 `git checkout` 이었는데 사실 이 명령어는 관계가 거의 없는 두 기능의 조합이었다. 먼저로는 잘 아는 브랜치 전환이다.

```bash
$ git checkout branchA # 기존에 있는 브랜치로 전환
$ git checkout -b branchB # 새로운 브랜치 생성 후 그 브랜치로 전환
$ git checkout -- [파일명] # [파일명] 파일의 변경 취소
```

"브랜치 전환" 과 "파일 변경 취소" 는 아래 명령어들로 분리되었다.

```bash
$ git switch branchA # 기존에 있는 브랜치로 전환
$ git switch -c branchB # 새로운 브랜치 생성 후 그 브랜치로 전환
$ git restore [파일명] # [파일명] 파일의 변경 취소
$ git restore --staged [파일명] # 스테이징된 [파일명] 파일의 스테이징 취소 (git add 취소)
```

위 명령어들에서 마지막 스테이징 취소는 `git reset -- [파일명]` 이었으며 이 또한 `git restore` 로 병합되었다.



### 그 외 기능들

* 원격저장소 브랜치 추적하면서 전환하기

```bash
$ git checkout origin/branchA -t # 기존
$ git switch origin/branchA -t # 신규
```



