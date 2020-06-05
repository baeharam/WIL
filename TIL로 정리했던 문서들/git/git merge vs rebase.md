## git merge

브랜치를 병합하는 과정으로, 3-way merge와 fast-forward merge가 있다. 3-way merge는 특정 커밋 기준으로 2개의 브랜치에서 작업을 하게 되면 각 브랜치의 마지막 커밋 2개와 공통 조상 커밋을 사용해서 충돌이 있는지 확인하고 머지한다.

fast-forward merge는 특정 커밋 기준으로 다른 브랜치를 생성해서 더 작업을 했다면 해당 브랜치가 이미 특정 커밋까지 포함한다. 따라서 기존 브랜치의 HEAD를 새로운 브랜치의 HEAD로 옮기는 머지 방식이다.

* 3-way merge : merge made by 'recursive' strategy
* fast-forward merge : Fast-forward

위와 같이 로그가 나온다.



## git rebase

특정 브랜치의 base를 바꾸는 명령어로 master와 issue 브랜치가 있고 각 작업을 다르게 했다고 하자. 이 때 issue 브랜치의 base를 master의 HEAD로 옮길 수 있다.  보통 rebase를 한 후에 fast-forward merge를 사용하여 기존 브랜치의 HEAD를 최신으로 옮긴다.



## git merge --squash

squash and merge 기법으로 merge하고자 하는 브랜치의 모든 커밋을 하나의 커밋으로 합친 후에 merge하는 방식이다.