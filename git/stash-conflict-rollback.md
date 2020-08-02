## git stash apply가 커밋과 충돌할 때 되돌리는 법

`git stash` 는 작업하고 있는 것들을 커밋하기 싫을 때 넣어두는(stash) 명령어이다. 브랜치를 바꿔가면서 작업할 때 상당히 유용해서 다음과 같이 별칭(alias)을 걸어두었었다.

```bash
alias gsta=git stash apply
alias gstu=git stash -u
```

여기서 `gstu` 를 쓰려고 하던 와중에 헷갈려서 `gsta` 를 쓰게 되었고 이미 커밋된 작업들과 충돌이 나는 상황이었다. 이 상태에서 원래대로 되돌리고 싶었고 찾아보았으나 `stash` 관련해서는 딱히 롤백하는 옵션이 없었다. 그래서 다음 방법을 사용했다.

```bash
$ git checkout -f
```

브랜치를 명시하지 않고 `git checkout` 을 하면 현재 브랜치로 이동하며, `-f` 옵션은 "force"의 약자로 병합충돌이 발생했다 해도 무시하는 것을 말한다. 설명은 아래와 같이 써있다.

> *When switching branches, proceed even if the index or the working tree differs from HEAD. This is used to throw away local changes.*
>
> *브랜치를 바꿀때, 인덱스나 작업트리에 HEAD와 다른것들이 있다해도 그대로 진행한다. 로컬 변경사항을 버릴 때 사용된다.*

그렇게 많이 쓸일은 없을 것 같지만, 그래도 알아두는 것이 좋아 정리한다.

<br>

## 참고

* https://stackoverflow.com/a/40446387/11789111



