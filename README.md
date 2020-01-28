
### function

<details><summary>News Rest Function</summary>

```
getNews/?	:	해당 번호의 뉴스를 가져온다.
getSamsungNews	:	날짜순으로 삼성의 모든 뉴스를 가져온다.
getLgNews	:	날짜순으로 LG의 모든 뉴스를 가져온다.
getSkNews	:	날짜순으로 SK의 모든 뉴스를 가져온다.
getSamsungRecent	:	날짜순으로 최근 20개의 삼성 뉴스를 가져온다.
getLgRecent	:	날짜순으로 최근 20개의 LG 뉴스를 가져온다.
getSkRecent	:	날짜순으로 최근 20개의 SK 뉴스를 가져온다.
findNews/?	:	SAMSUNG, LG, SK 각각 따로따로 뉴스 데이터를 검색한다.
getAllNews	:	모든 뉴스를 가져온다.(DataBase에 저장된 순서대로)
```
</details>

<details><summary>News Inner Function</summary>

```
getNewsOne		:	같은 URL을 가진 뉴스를 검색해온다.
addNews			:	뉴스를 DataBase에 추가한다.
getNewsCountSamsung	:	삼성 뉴스의 개수를 가져온다.
getNewsKeywordSamsung	:	삼성의 키워드 들을 가져온다.
addNewsKeyword		:	해당 뉴스의 키워드들을 DataBase에 저장한다.
newsKeywordValid		:	해당 뉴스 번호의 키워드들을 가져온다.
updateNewsKeyword	:	해당 번호의 키워드들을 수정한다.
```
</details>