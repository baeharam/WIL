# Dijkstra algorithm (다익스트라 알고리즘)

다익스트라 알고리즘은 벨만-포드 알고리즘, 플로이드-와샬 알고리즘과 더불어 directed weighted graph에서 특정 source에 대해 shortest path를 찾는 알고리즘 중에 하나이다. 벨만-포드와 마찬가지로 single-source shortest path를 해결하는 알고리즘이지만 **negative edge 가 포함되어 있을 경우 적용될 수 없다는 점이 취약하다.** 하지만 알고리즘 자체는 벨만-포드 알고리즘보다 빠르기 때문에 negative edge가 없는 그래프에서 shortest path를 찾아야 한다면 다익스트라를 사용하는 것이 더 좋다고 할 수 있다.

## 아이디어

다익스트라 알고리즘은 minimum spanning tree를 찾는 프림 알고리즘과 아주 비슷하기 때문에 greedy 알고리즘이다. 그래프의 각 vertex는 자신까지 오는 path의 minium weight을 나타내는 distance 변수와 직전 vertex를 표시하는 predecessor 변수를 갖는다. 

**vertex를 u라고 하고 그 distance를 $d[u]$, predecessor를 $\pi[u]$ 라고 하면, 그래프에서 임의의 vertex u에 대해 $d[u]$가 가장 작은 것부터 선택해서 인접한 vertex들에 대해 relaxation을 해주는 것이 핵심 방법이다.**

<u>특히 distance와 predecessor는 shortest path를 구하는 알고리즘에 모두 적용되기 때문에 반드시 알아두어야 한다.</u> 그림으로 이해하는 설명은 오버헤드라고 여겨지기 때문에 [나무위키](https://namu.wiki/w/%EB%8B%A4%EC%9D%B5%EC%8A%A4%ED%8A%B8%EB%9D%BC%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)를 참고하도록 하자.

## 의사코드

![default](https://user-images.githubusercontent.com/35518072/40431880-4d0f2dc2-5ee3-11e8-8f5a-22e8659648ac.JPG)

$INITIALIZE-SINGLE-SOURCE$는 distance와 predecessor를 초기화하는 함수로 모든 shortest path 알고리즘에 등장하며 $S$는 minimum distance의 계산이 끝난 vertex가 담긴 배열로 이 vertex들에 대해선 relaxation을 하지 않아도 되기 때문에 사용된다.

다익스트라에선 프림과 마찬가지로 우선순위 큐를 사용하는데 여기선 기준이 minium distance이다. 따라서 일단 3번째 줄에서 모든 vertex를 큐에 넣고 비워질 때까집 반복한다.

5번째 줄에서 제일 distance가 작은 vertex를 꺼내서 $S$에 집어넣고 그 vertex의 인접한 vertex들에 대해서 만약, $S$에 없다면, 즉 큐에 있다면 relaxation을 해준다. 이 부분이 다익스트라 알고리즘에 negative edge가 제대로 작용하지 않는 부분인데, 이미 shortest path의 계산이 끝났을 경우 다른 곳에 있을 negative edge를 생각하지 않기 때문이다.

## 시간복잡도

시간복잡도는 총 $|V|$개의 원소를 가졌던 우선순위 큐에서 최소 힙을 재구성하게 되면 $O(lg|V|)$의 시간이 걸리므로 $O(|V|lg|V|)$가 나오고, 7번째 줄에서 인접한 각 vertex들에 대해 relaxation을 하면 $O(|E|lg|V|)$의 시간이 걸려서  총 $O(|V|lg|V|+|E|lg|V|)=O(|E|lg|V|)$의 시간이 걸린다는 것을 알 수 있다. 여기서 4번째 줄의 while문 때문에 헷갈릴 수 있는데, [비슷한 프림알고리즘에 대해 질문했던 것](https://cs.stackexchange.com/questions/92193/why-the-time-complexity-of-prims-algorithm-is-not-velgv-but-elgv)을 참고하자.

