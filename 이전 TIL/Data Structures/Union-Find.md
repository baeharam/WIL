# Union-Find

크루스칼 알고리즘을 공부하다가 코드를 구현할 때 light edge를 찾는 자료구조로 이 방식을 쓴다고 하길래 처음 보는 자료구조라 정리해두고자 한다. Union-Find는 disjoint set이라고도 불리는 "서로소 집합" 자료구조이다. 단 2개의 연산인 find와 union만 존재하는데 tree 기준으로 설명하면 find는 어떤 vertex가 어떤 tree에 속했는지 root로 알려주는 함수이며 union은 두 개의 root vertices를 통해 두 개의 tree를 합치는 함수이다. 일단 초기화시키는 부분만 코드로 보자.

```c
int parent[1000], size[1000];
for(int i=0; i<1000; i++){
    parent[i]=i;
    size[i]=i;
}
```



## find 함수

```c
int find(int p)
{
    if(parent[p]==p) return p;
    else return find(parent[p]);
}
```

p라는 vertex가 인자로 들어오면 각 vertex의 부모 vertex가 저장되어 있는 parent 배열에서 찾아봐서 만약, 자기 자신이 부모 vertex라면 자기 자신을 리턴한다.

그에 아니라면 부모 vertex를 인자로 재귀함수를 돌리면서 마지막 root vertex를 찾아낸다. 이 방법의 문제점은 tree가 1-2-3-4-5로 skewed tree 같이 연결되어 있을 경우, find(5)를 호출하면 4번의 재귀함수 호출을 거치기 때문에 overhead가 너무 쉽게 발생한다. 따라서 어쩌피 find(5)=find(4)=find(3)=find(2)=1이니까, 다음과 같이 코드를 수정하면 예방할 수 있다.

```c
int find(int p)
{
    if(parent[p] == p) return p;
    else return parent[p] = find(parent[p]);
}
```

아예 부모 vertex를 root vertex로 바꿔버려서 한번에 찾게 하는 것이다.

## union 함수

C/C++에서 union은 예약어이므로 사용할 수 없어서 uni를 대신 사용하기로 한다.

```c
void uni(int p, int q)
{
    p = find(p);
    q = find(q);
    parent[p] = q;
    size[q] += size[p];
}
```

p와 q의 root vertex를 찾아서 p의 부모 vertex를 q로 설정하면, q 하위에 p를 연결한 것이 되고 그러므로 size[p]도 size[q]에 합해지게 된다. 하지만 이렇게 하면 아까의 문제처럼 skewed tree가 만들어질 수 있다. 따라서 다음과 같이 개선해야 한다.

```c
void uni(int p, int q)
{
    p = find(p);
    q = find(q);
    if(rank[p] < rank[q]) parent[p] = q, size[q] += size[p];
    else parent[q] = p; size[p] += size[q];
    if(rank[p] == rank[q]) rank[p]++;
}
```

두 tree의 높이가 같을 경우 어떻게 합치든 높이는 반드시 1 증가한다. 하지만 높이가 다를 경우, 높이가 큰 tree에 작은 높이의 tree를 합치면 높이는 증가하지 않을 것이다. 따라서 이것이 skewed tree를 방지하는 방법이 된다. (rank는 모두 0으로 초기화됨)

## 시간복잡도

시간복잡도는	 $O(\alpha(n))$ 이라는 처음 보는 기호가 등장하는데, 아커만 함수의 역함수라고 한다. 어쨌든 이 경우 $n$이 우주 원자 개수라 하더라도 4밖에 안되므로 $O(1)$로 간주해도 상관이 없다고 한다.

## 출처

* [koosaga님](http://koosaga.com/6)
* [모르고리즘](http://isukorea.com/group/morgorithm/board/b/115)
* [라이님](https://kks227.blog.me/220791837179)