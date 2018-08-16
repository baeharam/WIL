---
layout: post
category: Algorithm
title: "[알고리즘]라인 스위핑"
---

[백준님 블로그 글](https://www.acmicpc.net/blog/view/25)을 공부하며 정리한 내용이며 모든 설명과 사진은 여기서 가져왔습니다.

<br>

# 개요

[가장 가까운 두 점](https://www.acmicpc.net/problem/2261) 문제를 공부하면서 알게 된 알고리즘으로 이해하는데만 상당 시간이 소요되었기 때문에 정리된 글이 많음에도, 다시 정리하려고 한다. 해당 문제는 분할정복으로도 해결할 수 있다고 하나 라인 스위핑을 처음 들어봤기 때문에 이걸 통해서 해결하는 과정이 어떻게 되는지 보려고 한다.

문제는 좌표평면에 주어진 많은 점들 중에서 가장 거리가 가까운 한 쌍의 점을 구하는 것이다. 먼저 navie한 알고리즘을 적용하면 $N$개의 점이 있다고 했을 때 자신을 제외한 모든 점들과의 거리를 구해야 하므로 $O(N^2)$의 시간이 걸린다. 상당히 긴 시간이라 시간초과가 발생하기 때문에 줄여야 하는데 이제부터 어떻게 줄여지는지를 보는것이 핵심이다.

코드는 아래와 같은데 실제 채점해보면 7% 이후에서 시간초과가 발생한다.

```c++
#include <cstdio>

int x[100000], y[100000];
int n;

int dist(int x1, int x2, int y1, int y2)
{
	return (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
}

int main(void)
{
	scanf("%d", &n);
	for (int i = 0; i < n; i++) {
		scanf("%d%d", &x[i], &y[i]);
	}

	int ans = -1;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (i == j) continue;
			int d = dist(x[i], x[j], y[i], y[j]);
			if (ans == -1 || ans > d)
				ans = d;
		}
	}

	printf("%d\n", ans);
	return 0;
}
```

<br>

#1. X좌표 기준으로 정렬하자.

시간복잡도를 줄이는 첫번째 아이디어는 x좌표 기준으로 정렬하는 것이다. 왜일까? 그 이유는 굳이 고려할 필요가 없는 점들은 제외하자는 것인데, 아래 그림을 생각해보자.

<img src="https://onlinejudgeimages.s3-ap-northeast-1.amazonaws.com/blog/closest1.png" width="300px">

초록색 점을 M번째 점이라고 하고 **1~M-1번째 점까지 해서 가장 가까운 거리를 구해놓았다고 하자.** 그 거리를 d라고 하면 M번째 점과 x좌표 차이가 d보다 더 나는 것은 당연하게도 굳이 고려할 필요가 없다. **이렇게 고려할 필요가 없는 것들을 가지치기 하기 위해서 x좌표 기준으로 정렬한 것이다.** 최대 d까지만 허용하는데 그 이유는 같은 y좌표 상에 있을 때, 즉 y좌표의 차이가 가장 작을 때 x좌표 차이가 d여야 이미 계산된 d를 충족시킬 수 있기 때문이다.

* x좌표 기준으로 정렬하려면 x와 y가 한쌍이 되어야 하므로 `Point`라는 구조체를 만든다.
* 정렬을 위해 `Point`를 인자로 받는 비교함수를 만들고 dist 함수도 `Point`를 인자로 받도록 한다.
* 유연함을 위해 배열이 아닌 vector를 사용한다.

```c++
#include <cstdio>
#include <vector>
#include <algorithm>
using namespace std;

struct Point {
	int x;
	int y;
};
int dist(const Point &p1, const Point &p2)
{
	return (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y);
}
// 정렬을 위한 비교함수
bool cmp(const Point &p1, const Point &p2) {
	return p1.x < p2.x;
}

int main(void)
{
	int n;
	scanf("%d", &n);
	vector<Point> coor(n);
	for (int i = 0; i < n; i++) {
		scanf("%d%d", &coor[i].x, &coor[i].y);
	}

	// x좌표 기준으로 정렬
	sort(coor.begin(), coor.end(), cmp);

	// 처음 두 쌍을 후보로 정하고 d를 갱신해나간다.
	vector<Point> candidate = { coor[0], coor[1] };
    // 처음에 가장 가까운 거리는 두 쌍이다.
	int ans = dist(coor[0], coor[1]);

	for (int i = 2; i < n; i++) {
		Point now = coor[i];
		for (auto it = candidate.begin(); it != candidate.end();) {
			Point temp = *it;
			int dx = now.x - temp.x;
			
			// 제일 가까운 거리보다 x좌표 차이가 클 경우 후보에서 제외
			if (dx*dx > ans)
				it = candidate.erase(it);
			// 위 조건을 통과한다면 거리를 계산해서 업데이트
			else {
				int d = dist(now, temp);
				if (ans > d)
					ans = d;
				it++;
			}
		}
        // 현재 좌표가 다시 후보로 들어간다.
		candidate.push_back(now);
	}

	printf("%d\n", ans);
	return 0;
}
```

이렇게 개선을 했는데 시간복잡도가 줄어들었을까? $N$개의 점을 검사하는데 `candidate`에 들어갈 수 있는 최대 후보의 개수, 즉 점이 전부 들어가서 지워지지 않을 수 있기 때문에 이 경우에 $N$번 반복하므로 여전히 $O(N^2)$의 시간복잡도를 가진다. 그러면 어떻게 해야 할까? x좌표 기준으로 정렬했으니 이제는 y좌표 기준으로 정렬해보면 되지 않을까?

<br>

# 2. Y좌표 기준으로 정렬하자.

<img src="https://onlinejudgeimages.s3-ap-northeast-1.amazonaws.com/blog/closest2.png" width="300px">

y좌표도 x좌표와 아이디어가 똑같이 거리가 d초과인 점을 제외하는 방식이다. `candidate` 벡터에서 각 후보들을 아래와 같은 기준으로 가지치기 할 수 있다.

* **후보 중에서 M번째 점의 y좌표 + d보다 작은 점들 중에서 인덱스가 가장 큰 점**
* **후보 중에서 M번째 점의 y좌표 - d보다 큰 점들 중에서 인덱스가 가장 작은 점**

위 2개의 점을 구간으로 놓고 이분탐색(binary search)을 활용하여 찾으면 된다. 그런데 왜 저렇게 기준을 잡는지를 알아야 한다. +d보다 작아야 하고 -d보다 커야 하는 이유는 아까처럼 이미 가장 가까운 거리가 d인데 굳이 고려할 필요가 없는 것은 제거해주는 것이다. +d에서 인덱스가 가장 큰 점, -d에서 인덱스가 가장 작은 점을 찾는 것은 y좌표 기준으로 정렬했기 때문이다.

* y좌표 기준으로 정렬하기 위한 비교함수 한개 더 필요.
* 구간을 가지치기하기 위해 lower_bound와 upper_bound 함수 필요.

여기서 [lower_bound](https://en.cppreference.com/w/cpp/algorithm/lower_bound)와 [upper_bound](https://en.cppreference.com/w/cpp/algorithm/upper_bound)에 대해 알아야 하는데 나중에 다시 따로 정리할 거지만 간단하게 설명하면, 어떤 값을 주고 그 값이 정렬상태를 깨지 않고 위치할 수 있는 `ForwardIt` 타입의 iterator를 리턴해주는 함수라고 생각하면 된다. lower는 그 중 가장 작은 위치를, upper는 가장 큰 위치를 참조한다. 따라서 이걸 활용하면 y좌표로 정렬했을 때 d를 기준으로 가장 작은 것부터 가장 큰 것 까지의 구간을 구해낼 수 있다.

```c++
#include <cstdio>
#include <cmath>
#include <vector>
#include <algorithm>
using namespace std;

struct Point {
	int x;
	int y;

	Point() {}
	Point(int x, int y) : x(x), y(y) {}
};

int dist(const Point &p1, const Point &p2)
{
	return (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y);
}

bool cmp(const Point &p1, const Point &p2) {
	return p1.x < p2.x;
}

bool cmp2(const Point &p1, const Point &p2) {
	return p1.y < p2.y;
}


int main(void)
{
	int n;
	scanf("%d", &n);
	vector<Point> coor(n);
	for (int i = 0; i < n; i++) {
		scanf("%d%d", &coor[i].x, &coor[i].y);
	}

	// x좌표 기준으로 정렬
	sort(coor.begin(), coor.end(), cmp);

	// 처음 두 쌍을 후보로 정하고 d를 갱신해나간다.
	vector<Point> candidate = { coor[0], coor[1] };
	int ans = dist(coor[0], coor[1]);

	for (int i = 2; i < n; i++) {
		Point now = coor[i];
		for (auto it = candidate.begin(); it != candidate.end();) {
			Point temp = *it;
			int dx = now.x - temp.x;
			
			// 제일 가까운 거리보다 x좌표 차이가 클 경우 후보에서 제외
			if (dx*dx > ans)
				it = candidate.erase(it);
			else
				it++;
		}

		// y좌표 기준 정렬
		sort(candidate.begin(), candidate.end(), cmp2);
		int d = (int)sqrt((double)ans) + 1;;

		// 구간안에 속한 가장 작은값, 큰값
		Point lower_point = Point(-10001, now.y - d);
		Point upper_point = Point(10001, now.y + d);

		// iterator 리턴 값 받기
		auto lower = lower_bound(candidate.begin(), candidate.end(), lower_point, cmp2);
		auto upper = upper_bound(candidate.begin(), candidate.end(), upper_point, cmp2);

		for (auto it = lower; it != upper; it++) {
			int d = dist(now, *it);
			if (d < ans)
				ans = d;
		}
		candidate.push_back(now);
	}

	printf("%d\n", ans);
	return 0;
}
```

위에서 `lower_point`와 `upper_point`를 만들 때, x값을 각각 -10001과 10001로 했는데 그 이유는 x값의 최대 범위가 -10000~10000이므로 y좌표가 같을 때를 대비해서 각각 가장 작은 값과 큰 값으로 설정하기 위해서 1씩 빼고 더해준 것이다.

시간복잡도는 어떨까? 개선을 한 것 같지만 y좌표 정렬을 할 때 $O(NlgN)$이 걸리기 때문에 총 $O(N^2lgN)$의 시간복잡도로 더 안좋아졌다는 것을 알 수 있다. 여기서 개선하기 위해선 내부에서 정렬하는 것이 아닌 다른 자료구조를 활용하면 되는데 바로 set이다. set은 삽입/삭제/탐색이 모두 $O(lgN)$이 걸리며 기본적으로 오름차순 정렬되므로 아주 적합하다고 할 수 있다.

<br>

# 3. set을 이용하자.

* `candidate`를 vector에서 set으로 바꾼다.
* `lower_bound`와 `upper_bound`를 set의 함수로 바꾼다.
* `Point` 구조체에 <를 오버라이딩 하는데 y좌표 정렬을 위해서 y좌표가 같을 경우 x좌표 기준으로 정렬한다.

```c++
#include <cstdio>
#include <cmath>
#include <vector>
#include <set>
#include <algorithm>
using namespace std;

struct Point {
	int x;
	int y;

	Point() {}
	Point(int x, int y) : x(x), y(y) {}

	bool operator<(const Point &p) const {
		if (y == p.y)
			return x < p.x;
		else
			return y < p.y;
	}
};

int dist(const Point &p1, const Point &p2) {
	return (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y);
}
bool cmp(const Point &p1, const Point &p2) {
	return p1.x < p2.x;
}

int main(void)
{
	int n;
	scanf("%d", &n);
	vector<Point> coor(n);
	for (int i = 0; i < n; i++) {
		scanf("%d%d", &coor[i].x, &coor[i].y);
	}

	// x좌표 기준으로 정렬
	sort(coor.begin(), coor.end(), cmp);

	// 처음 두 쌍을 후보로 정하고 d를 갱신해나간다.
	set<Point> candidate = { coor[0], coor[1] };
	int ans = dist(coor[0], coor[1]);

	for (int i = 2; i < n; i++) {
		Point now = coor[i];
		for (auto it = candidate.begin(); it != candidate.end();) {
			Point temp = *it;
			int dx = now.x - temp.x;
			
			// 제일 가까운 거리보다 x좌표 차이가 클 경우 후보에서 제외
			if (dx*dx > ans)
				it = candidate.erase(it);
			else
				it++;
		}

		int d = (int)sqrt((double)ans) + 1;;

		// 구간안에 속한 가장 작은값, 큰값
		Point lower_point = Point(-10001, now.y - d);
		Point upper_point = Point(10001, now.y + d);

		// set의 함수로서 lower_bound, upper_bound 사용
		auto lower = candidate.lower_bound(lower_point);
		auto upper = candidate.upper_bound(upper_point);

		for (auto it = lower; it != upper; it++) {
			int d = dist(now, *it);
			if (d < ans)
				ans = d;
		}
		candidate.insert(now);
	}

	printf("%d\n", ans);
	return 0;
}
```

그러나 이렇게 개선을 해도 `candidate`를 순회하면서 후보들을 제거해나가는 방식 때문에 여전히 시간복잡도는 $O(N^2lgN)$이다. 이미 x좌표 기준으로 정렬이 되어있기 때문에 반복할 때마다 전부 순회하지 말고 이미 순회한 것 이후부터 검사하게 되면 시간복잡도를 더 줄일 수 있다.

<br>

# 4.이미 검사한 후보들은 검사하지 말자. 

* `start` 변수를 선언해서 `i-1`번째까지 검사하는 방식으로 진행한다.
* `i-1`번째 의미하는 것은 현재 좌표 바로 직전값.

```c++
#include <cstdio>
#include <cmath>
#include <vector>
#include <set>
#include <algorithm>
using namespace std;

struct Point {
	int x;
	int y;

	Point() {}
	Point(int x, int y) : x(x), y(y) {}

	bool operator<(const Point &p) const {
		if (y == p.y)
			return x < p.x;
		else
			return y < p.y;
	}
};

int dist(const Point &p1, const Point &p2)
{
	return (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y);
}

bool cmp(const Point &p1, const Point &p2) {
	return p1.x < p2.x;
}


int main(void)
{
	int n;
	scanf("%d", &n);
	vector<Point> coor(n);
	for (int i = 0; i < n; i++) {
		scanf("%d%d", &coor[i].x, &coor[i].y);
	}

	// x좌표 기준으로 정렬
	sort(coor.begin(), coor.end(), cmp);

	// 처음 두 쌍을 후보로 정하고 d를 갱신해나간다.
	set<Point> candidate = { coor[0], coor[1] };
	int ans = dist(coor[0], coor[1]);

	int start = 0;
	for (int i = 2; i < n; i++) {
		Point now = coor[i];
		while (start < i) {
			Point temp = coor[start];
			int dx = now.x - temp.x;

			// 제일 가까운 거리보다 x좌표 차이가 클 경우 후보에서 제외
			if (dx*dx > ans) {
				candidate.erase(temp);
				start++;
			}
			else
				break;
		}

		int d = (int)sqrt((double)ans) + 1;;

		// 구간안에 속한 가장 작은값, 큰값
		Point lower_point = Point(-10001, now.y - d);
		Point upper_point = Point(10001, now.y + d);

		// set의 함수로서 lower_bound, upper_bound 사용
		auto lower = candidate.lower_bound(lower_point);
		auto upper = candidate.upper_bound(upper_point);

		for (auto it = lower; it != upper; it++) {
			int d = dist(now, *it);
			if (d < ans)
				ans = d;
		}
		candidate.insert(now);
	}

	printf("%d\n", ans);
	return 0;
}
```

이렇게 하면 `candidate`에 대해 가지치기를 하는 횟수가 $N$번 반복할 때 $O(lgN)$만큼 걸리기 때문에 총 시간 복잡도는 $O(NlgN)$이라고 할 수 있다.