## git commit 취소하기

[여기](https://gmlwjd9405.github.io/2018/05/25/git-add-cancle.html) 잘 나와있음

```bash
git reset --soft HEAD^
git reset --mixed HEAD^
git reset HEAD^
git reset --hard HEAD^
```

* `—soft` : index 보존, 현재 브랜치가 가리키는 위치 옮김
* `—mixed` : 기본옵션, index도 리셋, 따라서 `add` 까지 리셋됨
* `—hard` : 주의해야 함. 워킹 디렉토리까지 리셋해서 수정한 데이터 덮어씌울 수 있음!!

git의 원리에 대해 다시 공부해야 확실히 정리될 것 같다.