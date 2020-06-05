## grep

* `-` **가 포함된 문자열 찾기**

예를 들어, ettercap의 매뉴얼에서 특정 `-T` 옵션에 관한 설명을 찾는다고 하면 유용하다.

```bash
man ettercap | grep -- -T
```

* **문자열 찾고 난 이전/이후 몇줄까지 포함하기**

이전/이후 5줄을 포함하고 싶다고 하자.

```bash
man ettercap | grep -B5 -A5 -- -T
```

