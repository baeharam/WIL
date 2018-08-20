---
layout: post
category: Algorithm
title: "[자료구조]Union-Find"
---

# 개요 및 초기화

Union-Find는 [disjoint set](https://ko.wikipedia.org/wiki/%EC%84%9C%EB%A1%9C%EC%86%8C_%EC%A7%91%ED%95%A9_%EC%9E%90%EB%A3%8C_%EA%B5%AC%EC%A1%B0)이라고도 하며, union과 find라는 함수를 지원하는 자료구조이다. Union과 Find함수가 핵심이며 각 원소들이 서로 같은 집합에 속하는지를 알기 위해 사용하는 것이 보통이다.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Dsu_disjoint_sets_init.svg/540px-Dsu_disjoint_sets_init.svg.png">

처음엔 이렇게 각 원소들이 하나의 집합을 이룬다. 특정 기준에 따라 하나의 집합으로 합쳐질 수 있는데 트리를 이용해서 부모-자식 관계로 표현한다. 그걸 나타내는 것이 아래의 코드에서 parent 배열이다. 초기화에선 모든 원소들 자신이 부모이며 자식이기 때문에 `parent[i]=i`와 같이 해주는 것이다. `rank[i]=0`은 이따 살펴보자.

```c
int parent[1000], size[1000];
for(int i=0; i<1000; i++){
    parent[i]=i;
    rank[i]=0;
}
```

<br>

# find

```c
int find(int p)
{
    if(parent[p]==p) return p;
    else return find(parent[p]);
}
```

p라는 vertex가 인자로 들어오면 각 vertex의 부모 vertex가 저장되어 있는 parent 배열에서 찾아봐서 만약, 자기 자신이 부모 vertex라면 자기 자신을 리턴한다. 그게 아니라면 부모 vertex를 인자로 재귀함수를 돌리면서 마지막 root vertex를 찾아낸다. 

하지만 tree가 1-2-3-4-5로 **skewed tree** 같이 연결되어 있을 경우, find(5)를 호출하면 4번의 재귀함수 호출을 거치기 때문에 **overhead가 너무 쉽게 발생한다.** 따라서 어쩌피 find(5)=find(4)=find(3)=find(2)=1이니까, 다음과 같이 코드를 수정하면 예방할 수 있다.

```c
int find(int p)
{
    if(parent[p] == p) return p;
    else return parent[p] = find(parent[p]);
}
```

아예 부모 vertex를 root vertex로 바꿔버려서 한번에 찾게 하는 방법이다. 이 방법을 **<span color:"red">경로 압축(path compression)</span>**이라고 한다.

<br>

# union(uni)

C/C++에서 union은 예약어이므로 사용할 수 없어서 uni를 대신 사용하기로 한다.

```c
void uni(int p, int q)
{
    p = find(p);
    q = find(q);
    parent[p] = q;
}
```

p와 q의 root vertex를 찾아서 p의 부모 vertex를 q로 설정하면, q 하위에 p를 연결한 것이 되지만 이렇게 하면 아까의 문제처럼 **skewed tree가 만들어질 수 있다.** 따라서 위에서 살펴봤떤 `rank`를 이용해 다음과 같이 개선해야 한다.

```c
void uni(int p, int q)
{
    p = find(p);
    q = find(q);
    if(rank[p] < rank[q]) parent[p] = q;
    else if(rank[p] > rank[q]) parent[q] = p;
    if(rank[p] == rank[q]) rank[p]++;
}
```

rank가 의미하는 것은 tree의 높이이며 skewed tree를 방지하기 위해서 높이를 비교한 후 부모-자식 관계를 정의한다. 두 tree의 높이가 같을 경우 어떻게 합치든 높이는 반드시 1 증가한다. 하지만 높이가 다를 경우, **<span color:"red">높이가 큰 tree에 작은 높이의 tree를 합치면 높이는 증가하지 않을 것이다.</span>** 이렇게 좀 더 균형있는 tree를 만들 수 있다. 이 방법을 **<span color:"red">Union by Rank</span>**라고 부른다.

<br>

# 시간복잡도

Path compression과 Union by rank를 적용하지 않았을 경우 skewed tree가 생길 가능성이 있으므로 $O(N)$이라는 시간복잡도가 나온다. 둘 중 하나만 적용했을 경우 $O(lgN)$이 나오며 둘 다 적용하면$O(\alpha(N))$ 이라는 처음 보는 기호가 등장하는데, 아커만 함수의 역함수라고 한다. 어쨌든 이 경우 $n$이 우주 원자 개수라 하더라도 4밖에 안되므로 $O(1)$로 간주해도 상관이 없다고 한다. 

<br>

## 출처

- [koosaga님](http://koosaga.com/6)
- [모르고리즘](http://isukorea.com/group/morgorithm/board/b/115)
- [라이님](https://kks227.blog.me/220791837179)