# DEMO

[DEMOSITE](https://52.79.249.4:8080)


## 개발환경

> OS
- Windows
- Ubuntu (배포환경 AWS)

> Language
- JAVA
- Vue
- CSS
- JS

> IDE
- VS CODE
- Eclipse

> Library
- Vuetify
- Jsoup
- Selenium
- Komoran

## 기술역할 분담

김태우 | 정지수 | 정영길 | 정영훈
------- | ------- | ------- | -------
 Front | Front | Web Master | Back

# front-end


# back-end

## function

---

리소스 | GET | POST | PUT | DELETE
------- | ------- | ------- | ------- | -------
`news/1` | 해당 번호의 뉴스 조회 | 좋아요 추가/삭제 | Error | Error



<details>
<summary>NEWS REST FUNCTION DESCRIPTION</summary>
<div markdown="1">

|Function|Description|
|--|--|
| news/? | 해당 번호의 뉴스를 가져온다. |
| news | POST 형태로 해당 번호의 뉴스를 좋아요 표시한다. |
| getAllNews | 모든 뉴스를 가져온다.(DataBase에 저장된 순서대로) |
| getSamsungNews | 날짜순으로 삼성의 모든 뉴스를 가져온다. |
| getLgNews | 날짜순으로 LG의 모든 뉴스를 가져온다. |
| getSkNews | 날짜순으로 SK의 모든 뉴스를 가져온다. |
| getAllNewsRecent | 날짜순으로 최근 20개의 모든 브랜드의 뉴스를 가져온다. |
| getSamsungRecent | 날짜순으로 최근 20개의 삼성 뉴스를 가져온다. |
| getLgRecent | 날짜순으로 최근 20개의 LG 뉴스를 가져온다. |
| getSkRecent | 날짜순으로 최근 20개의 SK 뉴스를 가져온다. |
| getUserKeyword | TOP 20 키워드들을 보여준다. |
| getUserKeywordNews/? | 토큰에 있는 Keyword를 전달 시 자신의 키워드에 따른 뉴스를 검색한다. |
| findNews/? | 전체, SAMSUNG, LG, SK 각각 따로따로 뉴스 데이터를 최신순으로 검색한다. |

</div>
</details>



---

| 리소스 | GET | POST | PUT | DELETE |
| ------- | ------- | ------- | ------- | ------- |
| `/comment` | Error | comment 등록 | Error | comment 삭제 |
| `/comment/1` | news_id 값의 comment 조회 | Error | Error | Error |

<details>
<summary>COMMENT REST FUNCTION DESCRIPTION</summary>
<div markdown="1">

|Function|Description|
|--|--|
|comment/?|해당 번호의 뉴스의 Comment들을 가져온다.|
| comment | POST 형태로 Comment를 등록한다.(입력값 : news_id, comment_text, comment_date) |
| comment | DELETE로 comment_id를 넘겨주면 삭제된다. (세션 유저의 member_id 값과 comment에 저장된 member_id 값이 같아야 지워짐) |

</div>
</details>




---

## Crawling

```java

doc = Jsoup.connect("https://news.samsung.com/kr/category/%ea%b8%b0%ec%97%85/" + category[i] + "/page/"
						+ pageIndex++).get();
//index 증가를 통해 페이지 이동

long diffSec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
long diffDay = diffSec / (24 * 60 * 60);

//해당 기사의 날짜를 기반으로 최근 7일간의 데이터를 크롤링

//BODYTEXT 내용을 토대로

Komoran komoran = new Komoran("C:\\KOMORAN\\models");
List<List<Pair<String, String>>> result = komoran.analyze(bodytext);
for (List<Pair<String, String>> eojeolResult : result) {
	for (Pair<String, String> wordMorph : eojeolResult) {
		if (wordMorph.getSecond().equals("SN") || wordMorph.getSecond().equals("SW") || wordMorph.getSecond().equals("SL")) {
			txt += wordMorph.getFirst();
		}
		if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP")) {
			keyword += wordMorph.getFirst() + " ";
		}
	}
}

//KOMORAN에 대입하여, KEYWORD들을 추출

//KOMORAN을 통해 나타나지 않는 영단어 키워드들은 따로 지정하여 추가한다.

String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV" };
int[] count = new int[countString.length];

for (int c = 0; c < count.length; c++) {
	count[c] = StringUtils.countMatches(txt, countString[c]);
}
for (int c = 0; c < count.length; c++) {
	for (int index = 0; index < count[c]; index++) {
		keyword += countString[c] + " ";
	}
}

```


## Selenium
### 무한 스크롤링 페이지를 크롤링 하기 위해 사용

```java

System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

// Driver SetUp
driver = new ChromeDriver();
driver.get(url);

//URL은 크롤링 하고자 하는 URL

webElement = driver.findElement(By.className("basic_list_morebtn"));
//basic_list_morebtn의 위치를 찾아 클릭을 한다.
//단, 이 부분에서 페이지 내의 basic_list_morebtn의 y축 위치가 변경되지 않았을 경우에는 클릭하지 않는다.

if (tempy != locationy) {
	webElement.click();
	webElement = null;
	tempy = locationy;
}

//이와 동시에 계속해서 date 클래스의 elements 들을 계속 검색해와 자신이 찾고자 하는 날짜까지 무한 스크롤링 한다.

for (int i = 0; i < webElements.size(); i++) {
	webElement = webElements.get(i);
	String title = webElement.findElement(By.className("title")).getText();
	String date = webElement.findElement(By.className("date")).getText();
	date = date.substring(0, 4) + "-" + date.substring(6, 8) + "-" + date.substring(10, 12);
	String brand = "LG";
	String cate = "ALL";
	String newsurl = webElement.findElement(By.className("thum_itembx")).getAttribute("href");
	NewsDTO news = new NewsDTO(title, date, brand, cate, newsurl);
	list.add(news);
}

//원하는 날짜까지 크롤링이 된 후 NewsDTO에 담아, 위 Crawling에서 기술한 KOMORAN을 통해
//키워드를 분류하여 DataBase에 추가한다.

```

## Keyword Sort

```java

//DataBase에 저장되어있는 News 데이터들을 브랜드 별로 불러와 해당 뉴스의 키워드들을 압축한다.
//압축된 키워드들은 NewsKeyword 라는 Table에 저장된다.

for (NewsDTO n : list) {
	int news_id = n.getNews_id();
	String word = n.getKeyword();

	HashMap<String, Integer> map = new HashMap<String, Integer>();
	//각 키워드별 인기 순위를 찾기 위해 String Integer 타입의 HashMap을 사용

	String[] keys = word.split(" ");
	//각 뉴스별 Keyword 들은 띄워쓰기를 통한 한 문자열로 되어있어, 공백을 기준으로 Split 한다.

	for (String s : keys) {
		if (s.length() <= 1 || s.equals("삼성전자") || s.equals("삼성") || s.equals("전자") || s.equals("분야")
			|| s.equals("사람") || s.equals("임직원") || s.equals("월드") || s.equals("최대") || s.equals("선정")
			|| s.equals("코리아") || s.equals("최적") || s.equals("진행") || s.equals("아래") || s.equals("소개")
			|| s.equals("부문") || s.equals("적용") || s.equals("기간") || s.equals("이상") || s.equals("상판")
			|| s.equals("규모") || s.equals("북미") || s.equals("지급") || s.equals("사원") || s.equals("규모")
			|| s.equals("지원") || s.equals("대상") || s.equals("영민") || s.equals("주옥") || s.equals("구성")
			|| s.equals("사용") || s.equals("자녀") || s.equals("사용자") || s.equals("시간") || s.equals("경험")
			|| s.equals("신창") || s.equals("제공") || s.equals("대표") || s.equals("레이") || s.equals("학교")
			|| s.equals("아우") || s.equals("아이") || s.equals("이두") || s.equals("사이") || s.equals("기준")
			|| s.equals("리뷰") || s.equals("으뜸") || s.equals("구매") || s.equals("관련") || s.equals("건조")
			|| s.equals("마음") || s.equals("시장") || s.equals("지역") || s.equals("상무") || s.equals("모습")
			|| s.equals("그니") || s.equals("그랑")) {
			continue;
			//위 문자들을 키워드 리스트에서 제외한다.
		}
		if (map.containsKey(s)) {
			int value = map.get(s);
			value++;
			map.put(s, value);
		} else {
			map.put(s, 1);
		}
	}
	ArrayList<NewsKeywordCounter> counterList = new ArrayList<NewsKeywordCounter>();

	for (String key : map.keySet()) {
		counterList.add(new NewsKeywordCounter(key, map.get(key)));
	}

	counterList.sort(new Comparator<NewsKeywordCounter>() {

		@Override
		public int compare(NewsKeywordCounter o1, NewsKeywordCounter o2) {
			// TODO Auto-generated method stub
			return o2.getCount() - o1.getCount();
		}
	});
	//키워드별 Count를 기준으로, 내림차순 정렬을 한다.

	String keyword = "";

	for (int i = 0; i < counterList.size(); i++) {
		if (i >= 5) {
			break;
		}
		keyword += counterList.get(i).getKeyword() + " ";
	}
	if(keyword.length() >= 1) {
		keyword = keyword.substring(0, keyword.length()-1);
	}
	addNewsKeyword(news_id, keyword);
}

```


## 검색 동적쿼리

```xml
<select id="검색을 하자!"
	parameterType="서치타입"
	resultType="뉴스타입">
	SELECT n.NEWS_ID, n.TITLE, n.DATE, n.BODY, n.BRAND, n.CATEGORY,
	k.KEYWORD, n.URL, n.BODYTEXT
	FROM NEWS n JOIN NEWSKEYWORD k
	WHERE
	n.NEWS_ID = k.NEWS_ID
	<!-- 기사 별로 정리된 키워드 테이블과, 뉴스 테이블을 조인한다. -->
	AND BRAND LIKE #{brand}
	<!-- 서치타입에 들어있는 검색하고자 하는 브랜드로 미리 분류한다. -->
	AND (
	<choose>
		<when test="search.length != 0">
			<!-- 서치타입에 들어있는 search(배열형태)의 길이 만큼 반복한다. -->
			(
			<foreach collection="search" item="word" index="index"
				separator="AND ">
				n.TITLE LIKE CONCAT('%',#{word},'%')
				<!-- 타이틀에 해당 워드가 존재하는지 확인한다. -->
			</foreach>
			<!-- 타이틀 같은경우 검색하고자 하는 워드들이 모두 포함되어 있어야만 한다. separator = AND
			ex) "삼성 전기"로 검색 시 타이틀에 삼성과 전기가 모두 포함되어 있어야 한다.-->
			)
		</when>
	</choose>
	OR
	<!-- 위 타이틀 검색결과와 OR를 이용하여 검색을 위해 입력한 키워드가 포함된 결과도 보여준다. -->
	<choose>
		<when test="search.length != 0">
			(
			<foreach collection="search" item="word" index="index"
				separator="OR ">
				k.KEYWORD LIKE CONCAT('%',#{word},'%')
				<!-- 키워드에 검색어에 입력한 워드가 포함되는지 확인한다. -->
			</foreach>
			<!-- 키워드의 경우 separator를 OR로 놓고, 입력한 워드중 하나라도 포함되면 검색되도록 한다. -->
			)
		</when>
	</choose>
	) ORDER BY DATE DESC
</select>	

-------------------------------------------------------------------------------

<select id="유저별 추천 뉴스"
	parameterType="서치타입">
	resultType="뉴스타입"
	SELECT n.NEWS_ID, n.TITLE, n.DATE, n.BODY, n.BRAND, n.CATEGORY,
	k.KEYWORD, n.URL, n.BODYTEXT
	FROM NEWS n JOIN NEWSKEYWORD k
	WHERE n.NEWS_ID = k.NEWS_ID
	AND (
	<choose>
		<when test="search.length != 0">
			(
			<foreach collection="search" item="word" index="index"
				separator="OR ">
				k.KEYWORD LIKE CONCAT('%',#{word},'%')
				<!-- 유저별 추천뉴스의 경우 선택해놓은 키워드만 통해 검색한다. -->
			</foreach>
			)
		</when>
	</choose>
	) AND n.DATE >= DATE_SUB(NOW(), INTERVAL 30 DAY) ORDER BY n.DATE DESC
	<!-- 검색한 결과들의 날짜가 현재 날짜에서 30일 이상 벗어난 자료는 제외한다. -->
</select>
```


## JWT
```java



```
