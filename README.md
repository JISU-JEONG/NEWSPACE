# DEMO

[DEMOSITE](http://merong.merong)


## 개발환경

> OS
>> Windows

> Language
>> JAVA, Vue, CSS, JS

> IDE
>> VS CODE, Eclipse, WorkBench

## 기술역할 분담

김태우 | 정지수 | 정영길 | 정영훈
------- | ------- | ------- | -------
 Front | Front | Back | Back

# front-end
## Login

```java
// 일반 로그인 로직
// email과 password를 서버에 보낸 후 올바른 정보가 왔을경우
// headers로 온 토큰정보를 localstorege에 저장한다. 
http
.post("/member/signin", {
	email: this.email,
	password: this.password,
	type : "nomal"
})
.then(res => {
	if (res.data.status) {
		storage.setItem("login-token", res.headers["login-token"]);
		this.setInfo("로그인성공", "", "");
		this.getInfo();
	} else {
		this.setInfo("", "", "");
		this.username = "";
		alert("입력 정보를 확인하세요.");
	}
})
.catch(e => {
	this.setInfo("실패", "", JSON.stringify(e.response || e.message));
});
```

## SocialLogin
```java
// 구글로그인 로직
// firebase API를 이용하여 토큰을 발급받고 정보를 획득한다.
const provider = new firebase.auth.GoogleAuthProvider();
const parentFunc = this;
var _promise = function() {
return new Promise(function(resolve, reject) {
	firebase
	.auth()
	.signInWithPopup(provider)
	.then(res => {
		console.log(res.user);
		parentFunc.username = res.user.displayName;
		parentFunc.email = res.user.uid;
		parentFunc.type = "google";
		resolve("ㄲ");
	})
	.catch(error => {
		console.log(error);
		reject(error);
	});
});
};
_promise().then(() => {
	// 구글로그인을 완료한 후에 아래의 함수를 실행합니다.
	this.duplicationCheck();
});
```

## duplicationCheck
```java
const parentFunc = this;
storage.setItem("login-token", "");

// 리턴받은 소셜로그인 정보가 DB에 있는지 확인합니다.
var _promise = function() {
return new Promise(function(resolve) {
	http
	.post("/member/signupcheck", {
		email: parentFunc.email
	})
	.then(res => {
		console.log(res.data);
		if (res.data == "Notexist") {
		//아이디 중복없다.
		parentFunc.duplicationflag = 1;
		} else {
		//아이디 중복있다.
		parentFunc.duplicationflag = 0;
		}
		resolve("ㄲ");
	});
});
};

_promise().then(() => {
var _promise2 = function() {
	// 소셜로그인 정보를 JWT를 이용하여 암호화시킵니다.
	return new Promise(function(resolve) {
		http
		.post("/member/socialtoken", {
			email: parentFunc.email,
			name: parentFunc.username,
			type: parentFunc.type
		})
		.then(res => {
			storage.setItem("login-token", res.headers["login-token"]);
			resolve("ㄲ");
		});
	});
};
_promise2().then(() => {
	if (parentFunc.duplicationflag == 1) {
		//처음 로그인시 키워드 선택하는 페이지로 라우팅
		this.$router.push("/SocialSignup");
	} else {
		//이미 로그인한적이 있을시 홈으루
		this.getInfo();
	}
});
```

## getInfo
```java
// header로 현재 토큰을 서버로 전송하고 해당 토큰의 유효기간을
// 확인한 뒤 폐기되지 않은 토큰일 경우 해당하는 정보를 불러옵니다.
http
.post(
	"/info",
	{},
	{
		headers: {
			"login-token": storage.getItem("login-token")
		}
	}
)
.then(res => {
	this.username = res.data.data.name;
	this.$router.push("/");
})
.catch(e => {
	this.setInfo("정보 조회 실패", "", e.response.data.msg);
});
```
# back-end

## function

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
findNews/?		:	전체, SAMSUNG, LG, SK 각각 따로따로 뉴스 데이터를 최신순으로 검색한다.
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


<details><summary>Comment Rest Function</summary>

```
getComment/?		:	해당 번호의 뉴스의 Comment들을 가져온다.
addComment		:	POST 형태로 Comment를 등록한다.(입력값 : news_id, comment_text, comment_date)
deleteComment		:	DELETE로 comment_id를 넘겨주면 삭제된다. (세션 유저의 member_id 값과 comment에 저장된 member_id 값이 같아야 지워짐)
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


## Login
#### Application.java
```java
// Spring의 Application에 인터셉터를 설정한다.
// /member/이후의 모든경로는 토큰유뮤를 검사하지 않고 그외 모든 경로는 토큰유무를 검사한다.
@Override
public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(jwtInterceptor).addPathPatterns("/**") // 기본 적용 경로
			.excludePathPatterns(Arrays.asList("/member/**"));// 적용 제외 경로
}

// Interceptor를 이용해서 처리하므로 전역의 Cross Origin 처리를 해준다.
@Override
public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.exposedHeaders("login-token");
}
```

#### JwtInterceptor.java
```java
// JWT토큰은 header를 통하여 통신을 진행하며,
// 경로를 이동할때마다 인증토큰을 검사한다.
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler)
		throws Exception {
	log.info(request.getMethod() + " :" + request.getServletPath());
	
	if (request.getMethod().equals("OPTIONS")) {
		return true;
	} else {
		String logintoken = request.getHeader("login-token");

		if(logintoken != null && logintoken.length() > 0) {            	
			jwtService.checkValid(logintoken);
			log.trace("토큰 사용 가능: {}", logintoken);
			return true;
		}
		else {
			throw new RuntimeException("인증 토큰이 없습니다.");            	
		}
	}
}
```

#### JwtService.java
```java
//토큰 생성
public String create(final Member member) {
	log.trace("time: {}", expireMin);
	final JwtBuilder builder = Jwts.builder();
	// JWT Token = Header + Payload + Signagure

	builder.setHeaderParam("typ", "JWT");// 토큰의 타입으로 고정 값

	// Payload 설정 - claim 정보 포함
	builder.setSubject(member.getTokenname())// 토큰 제목 설정
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))// 유효기간
			.claim("data", member);

	// signature - secret key를 이용한 암호화
	builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

	// 마지막 직렬화 처리
	final String token = builder.compact();
	log.info(member.getTokenname() + " 발행: {}", token);
	return token;
}

//토큰 체크
public void checkValid(final String token) {
	log.info("토큰 점검: {}", token);
	Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
}

//토큰 분석
public Map<String, Object> get(final String token) {
	Jws<Claims> claims = null;
	try {
		claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
	} catch (final Exception e) {
		throw new RuntimeException();
	}

	log.info("claims: {}", claims);

	// Claims는 Map의 구현체이다.
	return claims.getBody();
}
```

#### MemberRestController.java
```java
// 로그인
try {
	// Salt를 추가하여 SHA-256 암호화한 값으로 비밀번호를 찾는다.
	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	digest.reset();
	String password = member.getPassword() + salt;
	digest.update(password.getBytes("utf8"));
	String sha256password = String.format("%064x", new BigInteger(1, digest.digest()));
	member.setPassword(sha256password);
	
	Member loginUser = memberservice.signin(member);
	String token = jwtService.create(loginUser);
	
	// Hearder에 토큰정보를 전달한다.
	res.setHeader("login-token", token);
	status = HttpStatus.ACCEPTED;
} catch (RuntimeException e) {
	log.error("로그인 실패", e);
	resultMap.put("message", e.getMessage());
	status = HttpStatus.INTERNAL_SERVER_ERROR;
}

//토큰 정보획득
try {
	//Header에서 토큰을 받아온 정보를 리턴해준다.
	resultMap.putAll(jwtService.get(req.getHeader("login-token")));
	status = HttpStatus.ACCEPTED;
} catch (RuntimeException e) {
	log.error("정보조회 실패", e);
	resultMap.put("message", e.getMessage());
	status = HttpStatus.INTERNAL_SERVER_ERROR;
}

//회원가입
// Salt를 추가하여 SHA-256 암호화한다.
MessageDigest digest = MessageDigest.getInstance("SHA-256");
digest.reset();
String password = member.getPassword() + salt;
digest.update(password.getBytes("utf8"));
String sha256password = String.format("%064x", new BigInteger(1, digest.digest()));
member.setPassword(sha256password);
memberservice.insertMember(member);
```