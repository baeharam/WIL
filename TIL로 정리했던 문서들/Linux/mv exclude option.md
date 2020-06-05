# mv에서 특정 파일 제외시키기

mv로 특정 파일을 제외하고 옮기려는데 뭔지 몰라서 찾아봤다. 먼저 `.zshrc` 파일에 다음 옵션을 추가해주어야 한다.

```shell
setopt extendedglob
```

내가 하려던 것은 basic 폴더와 chapter5 폴더를 제외하고 현재 디렉토리의 모든 파일을 chapter5로 옮기는 것이었다. 제외옵션을 적용하려면 bash에선 !를 zsh에선 ^를 사용하면 된다.

```shell
mv ^(basic|chapter5) chapter5
```



