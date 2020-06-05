# path.resolve() vs path.join()

## path.resolve()

**오른쪽부터 왼쪽으로 경로를 합치는 함수, 단 절대경로가 포함될 경우 거기에서 멈춘다. 또한 "경로"가 문자열로 전달되지 않는 경우라면 현재 경로기준으로 해당 문자열을 붙인다.**

[공식문서](https://nodejs.org/docs/latest/api/path.html#path_path_resolve_paths) 의 코드

```javascript
path.resolve('/foo/bar', './baz');
// Returns: '/foo/bar/baz'

path.resolve('/foo/bar', '/tmp/file/');
// Returns: '/tmp/file'

path.resolve('wwwroot', 'static_files/png/', '../gif/image.gif');
// If the current working directory is /home/myself/node,
// this returns '/home/myself/node/wwwroot/static_files/gif/image.gif'
```

마지막 예제에서 가장 오른쪽 문자열인 `../gif/image.gif` 는 `..` 를 포함하고 있기 때문에 바로 왼쪽의 문자열에서 한 경로 올라간다.

## path.join()

**`path.resolve()` 와 다르게 단순한 병합을 하며 "문자열"이 아닌 다른 타입의 값이 온다면 `TypeError` 가 발생한다.**

[공식문서](https://nodejs.org/docs/latest/api/path.html#path_path_join_paths) 의 코드

```javascript
path.join('/foo', 'bar', 'baz/asdf', 'quux', '..');
// Returns: '/foo/bar/baz/asdf'

path.join('foo', {}, 'bar');
// Throws 'TypeError: Path must be a string. Received {}'
```

