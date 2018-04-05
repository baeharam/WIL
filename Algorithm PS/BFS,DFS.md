# BFS와 DFS

BFS와 DFS는 그래프를 탐색하는 기법 중 가장 대표적인 2가지 기법이다. 진작에 공부했어야 하나 시간을 핑계로 공부를 안하다가 branch and bound로 0/1 knapsack problem을 풀 때 Best First Search를 사용해야 하기 때문에 이 개념들에 대해 알아둬야 되서 지금 정리하려고 한다. 이 2개의 알고리즘은 문제 풀 때 굉장히 많이 쓰이기 때문에 빠른 구현과 확실한 개념을 이해하고 있어야 한다.

![1fe9246903b78fae07577b243a0b22791e02cb39640d5cbaae10d9849343b4ea6f162a9a677a5892fbf7819abd4ef7221ebd3608849cfb66793411fb5e6439514ed5c5e86db6d87a310ee3a249998ad2](https://user-images.githubusercontent.com/35518072/38340397-3d71fc74-38ae-11e8-9747-923286bc6e0e.gif)

> 출처  : 나무위키

다음의 움짤과 같이 DFS는 **깊이**를 중심으로 탐색하기 때문에 노드 1에서 시작해서 처음에 노드 3까지 갔다가 인접한(Adjacent) 노드가 없으므로 다시 노드 1로 돌아와야 한다. 이렇게 다시 돌아와야 하므로 구현방식이 *재귀함수*와 *스택*으로 2가지이다. 하지만 BFS는 **너비**를 중심으로 탐색하기 때문에 끝까지 각 노드를 반드시 한 번밖에 방문하지 않는다. 노드 1에서 시작해서 노드2,3,4를 방문하고 다음 위상(Level)인 5,6,7,8을 방문하고 마지막에 노드 9를 방문해서 끝난다. 따라서 BFS는 재귀함수가 아닌 *큐*를 통해 구현한다.



## BFS(Breadth First Search)

먼저 **너비** 우선 탐색인 BFS이다. BFS는 자기에게 인접한 노드들부터 우선순위로 방문하는 알고리즘이다. 알고리즘은 다음과 같다.

* 루트 노드를 방문하고 인접한 모든 노드를 큐에 넣는다. (루트 노드는 방문했다고 표시)
* 큐의 front(전단)에서 노드를 pop하여 방문하고 인접한 모든 노드를 큐에 넣는다. (방문표시)
* 위 2가지 방식을 큐가 완전히 비어질 때까지 반복한다.

정말 의외로 간단해서 놀랐지만 구현하기 쉽다. 단지 그래프를 탐색하는 거라 아직 익숙하지 않을 뿐이다. 그래프는 행렬과 리스트를 통해서 구현할 수 있는데 여기선 구현의 편의성을 위해 행렬을 사용하기로 한다. 행렬로 표시할 땐 Undirected graph(무방향 그래프)이기 때문에 만약 노드 1과 노드 2가 연결되었다면 graph\[1]\[2]=graph\[2]\[1]=1로 표시해주어야 한다.

```c++
for (int i = 1; i <= 10; i++) visited[i] = false;
std::queue<int> adj;

int v = 1;
// push first node into queue and makes it true for visiting
adj.push(v); visited[v] = true;

// If queue is not empty
while (!adj.empty()) {
	v = adj.front();
	printf("visited node %d\n", v);
	for (int i = 1; i <= 10; i++) {
		if (graph[v][i] == 1 && !visited[i]) {
			adj.push(i);
			visited[i] = true;
		}
	}
	adj.pop();
}
```

visited 배열은 각 노드에 대해서 방문을 했는지 표시해주는 배열이고 adj 큐가 인접한 노드들을 계속 push, pop하면서 BFS를 진행하게 되는 방식이다. 정말 간단하니 까먹었다면 쭉 읽고 넘어가도록 하자.



## DFS(Depth First Search)

DFS는 **깊이**를 우선으로 탐색하기 때문에 위에서 말했다시피 재귀함수와 스택을 통해 구현될 수 있으며 2가지 방법 모두 알아보도록 한다. 일단, 알고리즘이다.

* 루트 노드를 방문하고 인접한 첫번째 노드를 스택에 넣거나 재귀함수로 부른다.(방문표시)
* 그 노드의 인접한 첫번째 노드를 스택에 넣거나 재귀함수로 부른다.(방문표시)
* 인접한 노드가 없으면 재귀함수는 알아서 종료되고 스택일 경우 pop한다.
* **단, 주의할 점은 스택일 경우 pop했을 때 top에 있는 노드가 방문한 노드일 수 있기 때문에 따로 핸들링을 해주어야 한다.**
* 위 과정을 스택이 비워지거나 재귀함수가 완전히 끝날 때까지 반복한다.

### 재귀함수

```c++
void DFS(int v)
{
	printf("visited node %d\n", v);
	visited[v] = true;
	for (int i = 1; i <= 10; i++)
		if (graph[v][i] == 1 && !visited[i]) DFS(i);
}
```

재귀함수는 정말 너무 간단하다. 노드를 방문했다고 출력한뒤, 방문 표시를 해주고 인접한 노드를 발견하는 즉시 다시 DFS를 진행하면 된다.

### 스택

```c++
std::stack<int> adj;
int v = 1;
adj.push(v);


// Stack DFS
while (!adj.empty()) {
	bool adjacent = false;
	v = adj.top();
	if (!visited[v]) {
		printf("visited node %d\n", v);
		visited[v] = true;
	}
	for (int i = 1; i <= 10; i++) {
		if (graph[v][i] == 1 && !visited[i]) {
			adjacent = true;
			adj.push(i);
			break;
		}
	}
	if (!adjacent) adj.pop();
}
```

스택은 재귀함수보다는 복잡하긴 하지만 BFS를 큐로 구현할 때와 거의 비슷하다. 스택을 pop했을 때 방문한 노드일 수 있는 경우에 대한 처리가 printf를 하기 전 !visited[v]를 검사한 코드이다. 또한 for문에서 BFS와는 다르게 모든 인접 노드들을 스택에 넣지 않고 처음 인접 노드를 발견한 순간 스택에 넣고 바로 break로 빠져나온다. 마지막에 if(!adjacent)는 인접한 노드가 없을 경우 pop하는 코드이다.