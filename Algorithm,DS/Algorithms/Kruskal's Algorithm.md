# Kruskal's Algorithm (크루스칼 알고리즘)

크루스칼 알고리즘은 **Undirected Weighted Graph (무방향 가중치 그래프) 에서 MST(Minimum Spanning Tree)를 찾는 알고리즘** 중 하나이다. Spanning tree란 Spanning graph에서 tree의 조건을 만족하는 graph를 말한다. Spanning graph이므로 모든 정점이 connected 되어 어떤 정점에서든지 다른 정점으로 가는 path가 존재하여야 한다.

이런 조건을 만족하는 Spanning tree에서 각 간선의 총합을 최소로 하는 Spanning tree를 MST라고 한다. MST를 찾는 크루스칼 알고리즘의 과정은 다음과 같다.

1. 그래프의 각 vertex가 각각 하나의 tree가 되도록 하는 disjoint-set을 만든다.
2. 모든 edge를 원소로 갖는 집합 S를 만들어서 non-decreasing order로 정렬한다.
3. S가 비어있지 않는 동안.
   1. 가장 작은 weight의 edge를 S에서 뺀다.
   2. 그 edge를 연결하는 각 vertex가 같은 tree에 존재하지 않는지 확인한다. (cycle 체크)
   3. Cycle을 생성하지 않는다면 Find 함수를 사용하여 각 vertex가 속한 tree의 root vertex를 받아온 후 Union 함수로 연결한다.
   4. Cycle을 생성한다면 그 edge를 버린다.

여기서 나오는 새로운 자료구조인 **Union-Find (혹은 disjoint-set)** 가 어색할 수 있는데 Cycle을 검사할 때 굉장히 중요하다. 나중에 다시 한 번 TIL로 확실히 정리할테니 아직 정리되지 않았다면 [다음 설명](http://bowbowbow.tistory.com/26)을 보자!

![fig-0](https://user-images.githubusercontent.com/35518072/39518989-f63c3552-4e3f-11e8-87e2-fe24e1c12e39.jpg)

이제 예시를 통해 크루스칼 알고리즘이 어떻게 동작하는지 보자. 먼저, edge가 오름차순으로 담긴 집합을 E, MST가 저장되는 집합을 MST라고 하자.

* E : (6,7,1) | (5,6,2) | (2,8,2) | (0,1,4) | (2,5,4) | (6,8,6) | (2,3,7) | (7,8,7) | (1,2,8) | (0,7,8) | (3,4,9) | (4,5,10) | (1,7,11) | (3,5,14)
* MST ← (6,7,1), Cycle 생성 X, MST에 들어갈 수 있음
* MST ← (5,6,2), Cycle 생성 X, MST에 들어갈 수 있음
* MST ← (2,8,2), Cycle 생성 X, MST에 들어갈 수 있음
* 이런식으로 계속해서 MST에 edge를 넣었을 때 Cycle을 생성하는지 체크한다면 집합 E에 있는 모든 edge를 검사할 경우 MST 생성이 완료된다.
* **MST : (6,7,1) | (5,6,2) | (2,8,2) | (0,1,4) | (2,5,4) | (2,3,7) | (1,2,8) |  (3,4,9)**



# 시간복잡도

1. Disjoint-Set 자료구조를 이용해서 Make-Set 함수를 쓰면 각 vertex를 개별적인 tree로 취급하는 것으로 $O(|V|)$의 시간이 걸린다.
2. Edge의 weight을 기준으로 오름차순 정렬하므로 제일 빠른 알고리즘인 Merge sort, Heap sort를 쓴다고 해도 $O(|E|log|E|)$의 시간이 걸린다.
3. Find-Set 함수를 쓰는데 $O(log|V|)$의 시간이 걸리고 모든 edge를 연결하는 각각의 vertex에 대해 써야 하므로 $O(2|V|log|V|)=O(|V|log|V|)$의 시간이 걸린다.
4. Spanning tree의 최소 edge 개수가 $|V|-1$개이고, 최대 edge 개수가 거의 $|V|^2$이기 때문에 $O(|E|log|E|)=O(|E|log|V|^2)=O(2|E|log|V|)=O(|E|log|V|)$로 나타낼 수 있다. 