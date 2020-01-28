
# function

### REST 명령어 뒤에 ?는 검색하고자 하는 검색명

<details><summary>News Rest Function</summary>

```
getNews/?		:	해당 번호의 뉴스를 가져온다.
getAllNews		:	모든 뉴스를 가져온다.(DataBase에 저장된 순서대로)
getSamsungNews		:	날짜순으로 삼성의 모든 뉴스를 가져온다.
getLgNews		:	날짜순으로 LG의 모든 뉴스를 가져온다.
getSkNews		:	날짜순으로 SK의 모든 뉴스를 가져온다.
getAllNewsRecent	:	날짜순으로 최근 20개의 모든 브랜드의 뉴스를 가져온다.
getSamsungRecent	:	날짜순으로 최근 20개의 삼성 뉴스를 가져온다.
getLgRecent		:	날짜순으로 최근 20개의 LG 뉴스를 가져온다.
getSkRecent		:	날짜순으로 최근 20개의 SK 뉴스를 가져온다.
findNews/?		:	SAMSUNG, LG, SK 각각 따로따로 뉴스 데이터를 검색한다.
```
</details>

<details><summary>News Inner Function</summary>

```
getNewsOne			:	같은 URL을 가진 뉴스를 검색해온다.
addNews				:	뉴스를 DataBase에 추가한다.
getNewsCountSamsung		:	삼성 뉴스의 개수를 가져온다.
getNewsKeywordSamsung		:	삼성의 키워드 들을 가져온다.
addNewsKeyword			:	해당 뉴스의 키워드들을 DataBase에 저장한다.
newsKeywordValid		:	해당 뉴스 번호의 키워드들을 가져온다.
updateNewsKeyword		:	해당 번호의 키워드들을 수정한다.
```
</details>


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
