# 기존 알고리즘의 문제

Single-source shortest path의 경우 벨만-포드 알고리즘과 다익스트라 알고리즘으로 풀리지만, 다음 2개의 알고리즘으로 all-pairs shortest path 를 해결하기 위해선 시간복잡도가 상당히 커진다.

* 다익스트라 알고리즘의 시간 복잡도는 피보나치 힙을 이용했을 때, $O(VlgV+E)$ 이기 때문에 이걸 각 vertex만큼 해주면 $O(V^2lgV+VE)$ 가 걸리지만 dense graph에선 $O(V^3)$ 이 된다. 하지만 음의 가중치가 있으면 사용할 수 없다.
* 벨만-포드 알고리즘의 시간 복잡도는 $O(VE)$ 이고 각 vertex 만큼 해줬을 때 $O(V^2E)$ 가 된다. 또한 dense graph에선 $O(V^4)$ 으로 어마무시하다.
* Faster-All-Pairs-Shortest-Paths라고 다른 알고리즘이 있는데 이건 $O(V^3lgV)$ 이라고 한다. 

이런 2가지 알고리즘의 문제점을 통해서 플로이드 알고리즘이 얼마나 명쾌한지 보도록 하자.



# Floyd Algorithm (플로이드-와샬 알고리즘)

기본적인 개념은 다이나믹 프로그래밍을 활용해서 임의의 vertex i에서 임의의 vertex j로의 shortest path를 구하는 것인데 중간에 거치는 vertices에 대한 각각의 shortest path를 optimal substructure로 한다. 따라서 bottom-up 방식으로 임의의 vertex에서 임의의 vertex로 가는 shortest path 중에 임의의 vertex를 거치는 shortest path를 구하는 것이 핵심이다!

DP의 점화식은 다음과 같이 정의된다.

![default](https://user-images.githubusercontent.com/35518072/39817069-1b2cb57e-53d8-11e8-822c-080d2cfe2599.PNG)

d는 i에서 k를 거쳐 j로 가는 shortest path의 length를 말하고 w는 가중치를 말한다.

* k는 중간 vertex를 말하는 것으로 k=0이라는 것은 중간 vertex가 없다는 것이다. 따라서 i에서 j로 가는 path가 단번에 존재하므로 그 가중치가 바로 shortest path의 length가 된다.
* k>=1일 때는 중간 vertex가 존재할 수 있는 것이기 때문에 k를 거치는가 안 거치는가에 따른 shortest path의 length를 비교해서 결정한다.

그래프가 인접배열로 표시되기 때문에 2차원 배열에 predecessor 또한 저장해주어야 하며 이것 또한 k에 따라 달라진다.

1. **k가 0인 경우**

* i=j 이거나 i~j의 가중치가 무한대라면 : NIL

* i<>j 이거나 i~j의 가중치가 무한대보다 작다면 : i

* k가 0이라는 건 바로 갈 수 있다는 것이므로 자기 자신과의 edge이거나 다른 곳으로 가는 edge가 없으면 NIL인 것이고 다른 곳으로 가는 edge가 존재한다면 predecessor는 i가 될 수 밖에 없다.

2. **k>=1인 경우**

* k를 거치지 않는 shortest path가 결정되면 i~j의 원래 predecessor이고
* k를 거치는 shortest path가 결정되면 k~j의 원래 predecessor이다.

이제 코드를 보면서 어떻게 동작하는지 보자.



# 구현

```c
for(int k=1; k<=v; k++){
	for(int i=1; i<=v; i++){
		for(int j=1; j<=v; j++){
			if(D[i][k]+D[k][j]<D[i][j]){
				D[i][j]=D[i][k]+D[k][j];
				PI[i][j]=k;
			}
		}
	}
}
```

* 1번째 반복 : k는 중간 vertex를 의미하며 모든 vertex는 중간 vertex가 될 수 있다.
* 2번째 반복 : i는 시작 vertex를 의미하며 single source 문제로 보고 i로부터 임의의 vertex k를 거쳐 임의의 vertex j를 가는 것을 말한다.
* 3번째 반복 : j는 도착 vertex를 의미하며 2번째 반복에서 임의의 정점 vertex로 도착하는 것을 말한다.
* `PI[i][j]=k`의 의미는 predecessor가 초기화 할 때 k를 거치지 않는 경우를 결정해줌으로 이것은 k를 거치는 경우 그 k가 predecessor가 되는 것을 말한다.



# 시간복잡도

의심의 여지가 없이 $\theta(|V^3|)$ 이다. 그 이유는 for문을 무조건 vertex의 개수만큼 3번 돌려야 하기 때문에 최소이든 최대이든 반복횟수가 $|V^3|$ 이기 때문이다. 따라서 제일 위에서 봤던 문제점인, 음의 가중치가 가능한 알고리즘의 경우 $O(V^4), O(V^3lgV)$ 이므로 플로이드 알고리즘보다 느리고 비슷하다고 해도 구현이 훨씬 복잡하다. 그러니 all-pairs shortest problem에 대해선 플로이드 알고리즘을 애용하도록 하자.