글로벌로 패키지를 설치했는데 커맨드가 안 먹힐 경우 npm path가 잘못되있을 수 있다.

```bash
npm ls -g --depth=0
```

으로 글로벌 패키지 목록을 확인한 뒤 없으면, npm 설정이 어떻게 되어있는지 확인하자.

```bash
npm config list
```

여기서 사용자가 정의한 path가 있는데 이걸 명시적으로 고칠 수 있다. Windows 10의 경우 다음과 같다.

```bash
npm config set prefix c:/Users/<username>/AppData/Roaming/npm
```

자세한 사항은 [Stackoverflow](https://stackoverflow.com/questions/29955217/global-npm-package-installed-but-command-not-found) 를 참고하자.