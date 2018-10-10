# Strongly Connected Component(SCC)

## 정의

A strongly connected component of a directed graph G=(V,E) is a maximal set of vertices C $\subset$ V such that that for every pair of vertices u and v in C, u → v and v → u.

즉, Directed graph에서 적용되는 것인데, 임의의 vertex에서 다른 vertex로 갈 수 있는 path가 존재하는 최대 크기의 subgraph를 말하는 것이다.

![scc](https://user-images.githubusercontent.com/35518072/39109533-4ba42624-4708-11e8-90e4-7a708ae6a99f.png)

> 출처 : 위키백과

다음 그림에서 a,b,e는 모든 vertex에서 다른 vertex로의 path가 존재하지만, f를 포함하는 순간 f에선 a,b,e의 어떤 vertex에도 갈 수 없기 때문에 SCC가 아니다. 따라서, 이런 방식으로 위 그래프의 SCC를 모두 구하면 연보라색으로 표시된 영역 3개의 Subgraph가 SCC가 된다. 



## 어떻게 구할 것인가? (Kosaraju's algorithm)

그렇다면 하나의 Graph로부터, 어떻게 SCC를 구해낼 수 있을까? 먼저 그 점을 알기 위해 Transpose를 알아야 한다. Transpose란 "순서를 뒤바꾸다"라는 의미로 directed graph에선 각 vertex 사이의 방향을 바꾸면 transpose graph가 만들어진다.  이제 아래의 순서를 따르면 아주 간단하게 해결된다.

1. **기존 그래프 G에 대해서 DFS를 사용하고 finish time을 계산한다. DFS를 사용하므로 O(V+E)만큼 걸린다.**
2. **G에 대해 transpose 된 graph $G^T$를 계산한다. 인접 리스트일 경우 연결만 바꿔주면 되기 때문에 O(V+E)만큼 걸린다.**
3. **$G^T$ 에 대해 finish time이 큰 것 기준으로 DFS를 다시 진행한다. 여기도 DFS이므로 O(V+E)이다.**
