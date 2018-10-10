---
layout: post
category: Algorithm
title: "[알고리즘]접미사 배열(Suffix Array)"
---

# 개요

접미사 배열은 어떤 문자열의 각 접미사를 원래 문자열에 어떤 인덱스에 위치하는지 보고 해당 인덱스를 사전순으로 정렬한 배열을 말한다. 예를 들어, abracadabra를 보자. 뒤에서부터 하나씩 쪼개가면서 접미사를 구하면 된다. 길이는 11, 인덱스는 0부터 10이다.

```c
a(10)
ra(9)
bra(8)
abra(7)
dabra(6)
adabra(5)
cadabra(4)
acadabra(3)
racadabra(2)
bracadabra(1)
abracadabra(0)
```

여기서 이제 사전순으로 정렬해보면 아래와 같다.

```c
a(10)
abra(7)
abracadabra(0)
acadabra(3)
adabra(5)
bra(8)
bracadabra(1)
cadabra(4)
dabra(6)
ra(9)
racadabra(2)
```

이 각각의 인덱스를 배열로 만든 것, 즉 **{10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2}가 접미사 배열이 된다.** navie한 알고리즘으로 접미사 배열을 생성할 때의 경우를 보자. 정렬할 때 각 접미사를 정렬할 때 $O(nlgn)$이 걸리지만 비교를 할 때 상수시간이 아닌 접미사 비교가 $O(n)$만큼 걸리기 때문에 결과적으로 $O(n^2lgn)$의 시간복잡도가 나온다. 이제 시간복잡도를 줄여보자.

<br>

# 접미사의 앞 2*t개 글자씩 정렬

이게 무슨소리인가 싶은데 각 접미사들이 있을 때 앞의 글자 1개, 2개, 4개..로 증가하며 2씩 곱해서 그 길이만큼 비교를 하는 것을 말한다. **증가하는 길이를 t라고 했을 때 앞 2*t개의 문자가 같을 경우 같은 그룹에 그 접미사들을 넣는 것이 핵심 개념이다.** 또한 그룹의 번호는 0번부터 오름차순으로 부여되며 그룹의 개수가 접미사의 개수가 될 때까지 반복한다.

예를 들어, 위 예제의 경우 t=1일 때 **<span style="color:red">2*t=2이므로 앞 2글자를 비교</span>**한다. 만약 비교할 글자수보다 해당 문자열의 길이가 작다면 해당 문자열이 더 앞선 것이다.

* 그룹 0 : **a**
* 그룹 1 : **ab**ra, **ab**racadabra
* 그룹 2 : **ac**adabra
* 그룹 3 : **ad**abra
* 그룹 4 : **br**a, **br**acadabra
* 그룹 5 : **ca**dabra
* 그룹 6 : **da**bra
* 그룹 7 : **ra**, **ra**cadabra

그룹 0부터 7까지 총 8개가 나왔다. 아직 그룹의 개수가 접미사의 개수만큼 늘어나지 않았기 때문에 t가 증가하고 t=2가 되어서 **<span style="color:red">2*t=4이므로 앞 4글자를 비교</span>**한다.

* 그룹 0 : **a**
* 그룹 1 : **abra**, **abra**cadabra
* 그룹 2 : **acad**abra
* 그룹 3 : **adab**ra
* 그룹 4 : **bra**
* 그룹 5 : **brac**adabra
* 그룹 6 : **cada**bra
* 그룹 7 : **dabr**a
* 그룹 8 : **ra**
* 그룹 9 : **raca**dabra

그룹 0부터 9까지 총 10개가 나왔다. 여전히 그룹의 개수가 접미사의 개수보다 적기 때문에 t가 증가하고 t=4가 되어서 **<span style="color:red">2*t=8이므로 앞 8글자를 비교</span>**한다.

- 그룹 0 : **a**
- 그룹 1 : **abra**
- 그룹 2 : **abracada**bra
- 그룹 3 : **acadabra**
- 그룹 4 : **adabra**
- 그룹 5 : **bra**
- 그룹 6 : **bracadab**ra
- 그룹 7 : **cadabra**
- 그룹 8 : **dabra**
- 그룹 9 : **ra**
- 그룹 10 : **racadabr**a

이제 총 11개이므로 접미사가 전부 정렬되었다는 것을 알 수 있다. 여기서 주목할 점은 정렬을 어떻게 효율적으로 할 것인가이다. 어떤 접미사도 어떤 그룹에 속한다는 개념을 생각해보면 다음과 같이 비교할 수 있다.

1. **처음 반복할 때는 앞 2글자를 비교한다.**
2. **그 다음 반복 때부턴 이전 반복에서 접미사가 어떤 그룹에 속했는지를 알아냈기 때문에 그걸 통해 그룹을 비교해서 먼저 걸러낸다.**
3. **이전 반복에서 속한 그룹이 같다면 이전에 비교한 접미사 다음부터 비교하면 된다!**

이렇게 되면 1도 이해 안되니 예를 들어보자. abra와 abracadabra를 보면 처음 2글자는 ab로 같기 때문에 같은 그룹에 속한다. 그 다음 4글자를 보는 부분에서의 동작을 잘 봐야 한다. 이전 반복인 2글자에서 같은 그룹에 속했기 때문에 2글자 이후부터 보면 되는데 첫 2글자를 제외하고 그 이후부터 끝까지의 문자열 또한 접미사이다. 이게 뭔소리냐면, **<span style="color:red">ra와 racadabra도 하나의 접미사라는 것이다.</span>** 이전 반복에서 ra와 racadabra는 하나의 그룹이었기 때문에 4글자를 비교할 때도 abra와 abracadabra는 한 그룹에 들어가게 된다.

이제 t=4, 8글자를 비교해야 한다. 앞선 4글자 반복일 때 abra와 abracadabra는 같은 그룹에 속했으니 그 이후부터 봐야 하는데 abra의 경우 없고 abracadabra의 경우는 cadabra이므로 abra가 abracadabra보다 앞서게 되는 것이다. 따라서 위에서 그룹을 나눈 걸 보면 abra는 그룹 1에, abracadabra는 그룹 2에 속하게 된다.

<br>

# 구현은 어떻게?

사실 개념이 너무 어려워서 구현을 할 수가 없었기 때문에 종만북에 있는 코드를 공부해서 주석을 달았다.

```c++
#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
using namespace std;

// sort 함수에 넘길 비교클래스
struct Comparator {
	// 인덱스 i부터 시작하는 접미사가 속한 그룹
	const vector<int>& group;
	// 이 시점에 앞에서부터 비교할 문자 개수
	int t;

    // 생성자
	Comparator(const vector<int>& _group, int _t) : group(_group), t(_t) {}

	bool operator() (int a, int b) {
		// 앞의 t개 글자가 다르면 이들을 이용해 비교한다.
		if (group[a] != group[b])
			return group[a] < group[b];

		// t개의 문자가 같다면, 즉 같은 그룹에 속한다면 +t한 문자부터 시작하는 접미사가
		// 속하는 그룹으로 비교한다.
		return group[a + t] < group[b + t];
	}
};

// Suffix Array를 만들어내는 함수
vector<int> getSuffixArray(const string& s) {
	int n = s.size();
	int t = 1; // 처음 비교할 개수는 1개

	vector<int> group(n + 1);
	// t=1일때는 문자열의 각 문자를 그룹 번호로 지정해줘도 상관없다.
	for (int i = 0; i < n; i++) group[i] = s[i];
	group[n] = -1;

	// 접미사 배열 생성
	vector<int> suffix(n);
	// 일단 각 문자의 해당 인덱스로 초기화한다.
	for (int i = 0; i < n; i++) suffix[i] = i;

	// 총 n개의 그룹으로 나뉠 때까지 반복한다.
	while (t < n) {

		// 주어진 그룹번호 기준으로 정렬하는 것.
		Comparator compareUsing2T(group, t);
		sort(suffix.begin(), suffix.end(), compareUsing2T);

		// 2*t글자가 n개 이상이라면 빠져나온다.
		t *= 2;
		if (t >= n) break;

		// 새롭게 정렬된 suffix array에 2*t를 이용해 그룹 번호를 부여한다.
		vector<int> newGroup(n + 1);
		newGroup[n] = -1;
		// 정렬된 상태이므로 첫번째 접미사는 그룹번호 0
		newGroup[suffix[0]] = 0;

		// 그 다음 접미사부터 확인하면서 이전 그룹번호를 비교!
		for (int i = 1; i < n; i++) {
            // i-1번째 접미사가 더 작은 그룹에 속한 경우로, i번째 접미사가 그 다음 그룹에 오게 된다.
			if (compareUsing2T(suffix[i - 1], suffix[i]))
				newGroup[suffix[i]] = newGroup[suffix[i - 1]] + 1;
            // i-1번째 접미사가 같은 그룹, 혹은 더 큰 그룹에 속한 경우로 i번째 접미사가 해당 그룹에 속하게 된다.
			else
				newGroup[suffix[i]] = newGroup[suffix[i - 1]];
		}
		group = newGroup;
	}
	return suffix;
}

int main(void)
{
	vector<int> suffix = getSuffixArray("abracadabra");
	for (int i = 0; i < suffix.size(); i++)
		cout << suffix[i] << ' ';
	cout << '\n';
	return 0;
}
```

위에서 `group[n]`이나 `newGroup[n]`을 -1로 초기화하는데 그 이유는 a+t와 b+t의 최댓값이 n일수밖에 없기 때문이다. 인덱스 a번째의 접미사와 b번째의 접미사를 비교할 때 앞에서부터 t개만큼, 즉 이미 2*t로 계산된 t개를 비교한다. 그렇다면 a와 b의 길이는 적어도 t라는 말과 동일하다. 최대 비교할 수 있는 문자열의 길이는 n이므로 위에서 만약 2\*t로 계산된 t가 10이라면 (그럴 수 없지만...) 0번째 인덱스와 1번째 인덱스를 비교할 경우 0+10=10과 1+10=11을 비교하게 된다. 그럼 최대 11인 것을 알 수 있고 Comparator가 접근할 수 있는 최대 인덱스가 n이라는 것을 알 수 있다. 그래서 `group[n]`과 `newGroup[n]`을 -1로 초기화해주는 것이다.

<br>

## 출처

* [라이님 접미사 배열](http://blog.naver.com/PostView.nhn?blogId=kks227&logNo=221028710658) , 거의 이 블로그로 정리했다고 해도 무방한 수준...
* 종만북, 뭔가 더 여렵지만 코드는 여기서 가져왔다. 