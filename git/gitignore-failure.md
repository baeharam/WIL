## .gitignore 인식 실패할 때

협업을 하던 도중 누군가 `.gitignore` 파일에 새로운 파일을 추가한 뒤 푸시했고 그걸 당겨와서 사용하고 있었는데, 내 로컬에서는 업데이트가 되지 않는 상태였다. 무시되어야 하는 파일을 A라고 하면 A가 계속해서 git에 의해 트래킹되고 있던 것이다. 정확한 원인은 잘 모르겠지만 `.gitignore` 파일에 추가를 해도 이미 인덱스에 올라간 파일이면 계속 트래킹이 되는 것 같다. 해결방법은 인덱스에서 해당 파일을 지운 뒤 다시 커밋하는 방법이다.

```bash
$ git rm -r --cached A
$ git add A
$ git commit -sm "A를 .gitignore에 추가"
```

이렇게 하면 다시 `.gitignore` 가 인식을 하게 되고 git이 더 이상 트래킹하지 않게 된다.

<br>

## 참고

* [.gitignore is ignored by Git](https://stackoverflow.com/questions/11451535/gitignore-is-ignored-by-git)

