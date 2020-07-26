## git status --porcelain

보통, 추적하고 있지 않은 파일들 및 커밋되지 않은 파일들이 어떤 것인치 체크하기 위해서 `git status` 명령어를 사용하는데 굉장히 상세하게 나온다. 하지만 현재 저장소가 더럽혀져있는지 여부, 즉 `git status` 를 입력했을 때 어떤 대상들이 나오는지의 여부만 판단하고 싶을 땐 적합하지 않다. 보통은 사용하지 않겠지만, 쉘 스크립트를 작성해서 해당 여부를 알고 싶은 경우에 필요할 수 있다.

이럴 때 사용하는 옵션이 1.7.0에서 소개된 `--porcelain` 이다. "porcelain"의 뜻은 "자기", 즉 도자기를 의미하는데 `git` 에서는 사용자가 사용하기 쉽게 구성한 고수준 명령어 인터페이스를 가리킨다. 이것과 반대되는 것이 저수준 명령어 인터페이스인 "plumbing" 명령어이다. 더 자세한 내용은 "참고" 부분을 확인하기로 하고, 어쨌든 이런 컨텍스트에서 볼 때 사용자가 보기 쉽게 포맷팅된 버전의 `git status` 라고 할 수 있다. 실제로 `git status --help` 로 `--porcelain` 옵션을 찾아보면 아래와 같이 나온다.

```
--porcelain[=<version>]
  Give the output in an easy-to-parse format for scripts. This is similar to the short output, but
  will remain stable across Git versions and regardless of user configuration. See below for
  details.

  The version parameter is used to specify the format version. This is optional and defaults to the
  original version v1 format.
```

`git` 의 버전과 사용자 설정에 관계없이 동일하다고 나와있는 걸로 봐서 "porcelain"의 의미를 확실히 알 수 있다. 또한 포맷팅되는 것도 버전1/2가 나뉘어있다는 것도 확인할 수 있다. 마지막으로, 일반적인 `git status` 와 어떤 것이 다른지를 확인해보자.

`b.txt` 가 추적되지 않고 `c.txt` 가 추적되고 있을 때,

* `git status`

```
커밋할 변경 사항:
  (use "git restore --staged <file>..." to unstage)
	새 파일:       c.txt

추적하지 않는 파일:
  (커밋할 사항에 포함하려면 "git add <파일>..."을 사용하십시오)
	b.txt
```

* `git status --porcelain`

```
A  c.txt
?? b.txt
```

`--porcelain` 이 훨씬 깔끔하게 포맷팅된다는 것을 확인할 수 있다. 앞으로 스크립트를 작성할 때나 빠르게 상태를 확인해야할 때 애용할 것 같다.

## 참고

* [Checking for a dirty index or untracked files with Git](https://stackoverflow.com/a/5737794/11789111)
* [10.1 Git의 내부 - Plumbing 명령과 Porcelain 명령](https://git-scm.com/book/ko/v2/Git의-내부-Plumbing-명령과-Porcelain-명령)