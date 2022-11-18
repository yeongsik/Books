# TIR 2 - 3장 함수

생성일: 2022년 10월 11일 오전 11:50

## **TIR (Today I Read)**

### 📖 **오늘 읽은 내용**

## 3장 함수

- 어떤 프로그램이든 가장 기본적인 단위 ⇒ 함수

```java

/** Bad */
public static String testableHtml(PageData pageData,boolean includeSuiteSetUp)
        throws Exception{
        WikiPage wikiPage=pageData.getWikiPage();
        StringBuffer buffer=new StringBuffer();
        if(pageData.hasAttribute("Test")){
        if(includeSuiteSetup){
        WikiPage suiteSetup=
        PageCrawlerImpl.getInheritedPage(SuiteResponser.SUITE_SETUP_NAME,wikiPage);
        if(suiteSetup!=null){
        WikiPagePath pagePath=
        suiteSetup.getPageCrawler().getFullPath(suiteSetup);
        String pagePathName=PathParser.render(pagePath);
        buffer.append("!include -setup .")
        .append(pagePathName)
        .append("\n");
        }
        }
        WikiPage setup=
        PageCrawlerImpl.getInheritedPage("SetUp",wikiPage);
        if(setup!=null){
        WikiPagePath setupPath=
        wikiPage.getPageCrawler().getFullPath(setup);
        String setupPathName=PathParser.render(setupPath);
        buffer.append("!include -setup .")
        .append(setupPathName)
        .append("\n");
        }
        }
        buffer.append(pageData.getContent());
        if(pageData.hasAttribute("Test")){
        WikiPage teardown=
        PageCrawlerImpl.getInheritedPage("TearDown",wikiPage);
        if(teardown!=null){
        WikiPagePath tearDownPath=
        wikiPage.getPageCrawler().getFullPath(tearDown);
        String tearDownPathName=PathParser.render(tearDownPath);
        buffer.append("\n")
        .append("!include -teardown .")
        .append(tearDownPathName)
        .append("\n");
        }
        if(includeSuiteSetup){
        WikiPage suiteTearDown=
        PageCrawlerImpl.getInheritedPage(
        SuiteResponser.SUITE_TEARDOWN_NAME,
        wikiPage
        );
        if(suiteTeardown!=null){
        WikiPagePath pagePath=
        suiteTeardown.getPageCrawler().getFullPath(suiteTeardown);
        String pagePathName=PathParser.render(pagePath);
        buffer.append("!include -teardown .")
        .append(pagePathName)
        .append("\n");
        }
        }
        }

        pageData.setContent(buffer.toString());
        return pageData.getHtml();
        }

/** good 1 */
public static String renderPageWithSetupsAndTeardowns(
        PageData pageData,boolean isSuite
        )throws Exception{
        boolean isTestPage=pageData.hasAttribute("Test");
        if(isTestPage){
        WikiPage testPage=pageData.getWikiPage();
        StringBuffer newPageContent=new StringBuffer();
        includeSetupPages(testPage,newPageContent,isSuite);
        newPageContent.append(pageData.getContent());
        includeTeardownPages(testPage,newPageContent,isSuite);
        pageData.setContent(newPageContent.toString());
        }
        return pageData.getHtml();
        }

/** good 2 코드를 더 작게 만들어라 */

public static String renderPageWithSetupsAndTeardowns(
        PageData pageData,boolean isSuite)throws Exception{
        if(isTestPage(pageData))
        includeSetupAndTeardownPages(pageData,isSuite);
        return pageData.getHtml();
        }

/** 최종 Good */

public class SetupTeardownIncluder {
    private PageData pageData;
    private boolean isSuite;
    private WikiPage testPage;
    private StringBuffer newPageContent;
    private PageCrawler pageCrawler;

    public static String render(PageData pageData) throws Exception {
        return render(pageData, false);
    }

    public static String render(PageData pageData, boolean isSuite)
            throws Exception {
        return new SetupTeardownIncluder(pageData).render(isSuite);
    }

    private SetupTeardownIncluder(PageData pageData) {
        this.pageData = pageData;
        testPage = pageData.getWikiPage();
        pageCrawler = testPage.getPageCrawler();
        newPageContent = new StringBuffer();
    }

    private String render(boolean isSuite) throws Exception {
        this.isSuite = isSuite;
        if (isTestPage())
            includeSetupAndTeardownPages();
        return pageData.getHtml();
    }

    private boolean isTestPage() throws Exception {
        return pageData.hasAttribute("Test");
    }

    private void includeSetupAndTeardownPages() throws Exception {
        includeSetupPages();
        includePageContent();
        includeTeardownPages();
        updatePageContent();
    }

    private void includeSetupPages() throws Exception {
        if (isSuite)
            includeSuiteSetupPage();
        includeSetupPage();
    }

    private void includeSuiteSetupPages() throws Exception {
        include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
    }

    private void includeSetupPage() throws Exception {
        include("SetUp", "-setup");
    }

    private void includePageContent() throws Exception {
        newPageContent.append(pageData.getContent());
    }

    private void includeTeardownPages() throws Exception {
        includeTeardownPages();
        if (isSuite)
            includeSuiteTeardownPage();
    }

    private void includeTeardownPage() throws Exception {
        include("Teardown", "-teardown");
    }

    private void includeSuiteTeardownPage() throws Exception {
        include(SuiteResponder.SUITE_TEARDOWN_NAME, "-teardown");
    }

    private void updatePageContent() throws Exception {
        pageData.setContent(newPageContent.toString());
    }

    private void include(String pageName, String arg) throws Exception {
        WikiPage inheritedPage = findInheritedPage(pageName);
        if (inheritedPage != null) {
            String pagePathName = getPathNameForPage(inheritedPage);
            buildIncludeDirective(pagePathName, arg);
        }
    }

    private WikiPage findInheritedPage(String pageName) throws Exception {
        return PageCrawlerImpl.getInheritedPage(pageName, testPage);
    }

    private String getPathNameForPage(WikiPage page) throws Exception {
        WikiPagePath pagePath = pageCrawler.getFullPath(page);
        return PathParser.render(pagePath);
    }

    private void buildIncludeDirective(String pagePathName, String arg) {
        newPageContent
                .appned("\n!include ")
                .append(arg)
                .append("")
                .append(pagePathName)
                .append("\n");
    }
}
```

- 작게 만들어라
    - if/else 문 , while문 등에 들어가는 블록은 한 줄
    - 들여쓰기 수준을 1단이나 2단을 넘어가서는 안된다.

- 한가지만 해라
    - before 코드를 보면 여러 가지를 처리
        - 버퍼 생성 , 페이지를 가져오고 , 상속된 페이지 검색 , 경로 렌더링 , 문자열을 만들고 , Html를 생성한다.
    - good 2 코드는 한가지만 수행 ⇒ 설정 페이지와 해제페이지를 테스트 페이지에 넣는다.
    - 한가지가 무엇일까? → 추상화 수준이 하나인 함수

- 함수 당 추상화 수준은 하나로
    - 높은 추상화 수준 : getHtml()
    - 중간 추상화 수준 : String pagePathName = PathParser.render(pagePath);
    - 낮은 추상화 수준 : append(”\n”);

  ⇒ 한 함수에서 추상화 수준이 섞이면 읽는 사람이 헷갈린다.

    - 근본 개념과 세부사항이 섞임

- 위에서 아래로 코드 읽기 : 내려가기 규칙
    - 위에서 아래로 이야기처럼 읽혀야 한다.
    - 한 함수 다음에 추상화 수준이 한 단계 낮은 함수가 온다.
    - 위에서 아래로 읽을 수록 추상화 수준이 단계별로 내려간다. ⇒ 내려가기 규칙

⇒ 추상화 수준이 하나인 함수를 만들기가 쉽지 않다 , 핵심은 한 가지만 하는 함수

- Switch 문
    - 작게 만들기 어려운 switch 문

    ```java
    /** 
    	bad 
    	1. 함수가 길다
    	2. 한가지만 수행하지 않는다.
    	3. SRP 위반 :
    	4. OCP 위반 : 새직원 유형을 추가할 때마다 코드 변경
    */
    public Money calculatePay(Employee e) throws InvalidEmployeeType {
    	switch(e.type) {
    		case COMMISSIONED:
    			return calculateCommissionedPay(e);
    		case HOURLY;
    			return calculateHourlyPay(e);
    		case SALARIED:
    			return calculateSalariedPay(e);
    		default :
    			throw new InvalidEmployeeType(e.type);
    	}
    }
    
    /** 
    	good 추상 팩토리로 switch 문 숨기기 
    	+ 불가피한 상황도 생기기도 한다.
    */
    
    public abstract class Employee {
    	public abstract boolean isPayday();
    	public abstract Money calculatePay();
    	public abstract void deliverPay(Money pay);
    }
    -------------------------------------
    
    public interface EmployeeFactory {
    	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
    }
    
    --------------------------------------
    
    public class EmployeeFactoryImpl implements EmployeeFactory {
    	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
    		switch(r.type) {
    			case COMMISSTIONED:
    				return new CommissionedEmployee(r);
    			case HOURLY:
    				return new HourlyEmployee(r);
    			case SALARIED:
    				return new SalariedEmpolyee(r);
    			default:
    				throw new InvalidEmployeeType(r.type);
    		}
    	}
    }
    ```

- 서술적인 이름 사용

  함수가 하는 일을 좀 더 잘 표현하는 이름을 사용

  작은 함수에 좋은 이름을 붙인다면 절반은 성공

  이름을 붙일 때 일관성이 필요 → 모듈 내에서 함수 이름은 같은 문구 , 명사 , 동사를 사용

- 함수 인수
    - 이상적인 인수는 0개 (무항)
    - 1개 , 2개까지는 괜찮고 3개부터는 가능한 피하는 편이 좋다.
    - 테스트 관점에서 인수는 더 어렵다, 갖가지 인수 조합으로 테스트 케이스를 작성해야하기 때문이다.

  출력 인수는 이해하기 더욱 어렵다 ( 인수 : 입력값 , 출력 : 반환값 으로 이해하고 있기 때문에 )

    - 하나 인수를 넘기는 이유 두 가지
        1. 질문을 던지는 경우

           ex : boolean fileExists(”MyFile”)

        2. 인수를 뭔가로 변환해 결과 반환하는 경우

           ex : InputStream fileOpen(”Myfile”) → String형 파일 이름을 InputStream으로 변환


        ⇒ 함수 이름을 지을 때는 두 경우를 분명히 구분해서 작성 ( 명령과 조회를 분리 ) 
        
    - 드물게 사용하는 형식 : 이벤트
        - 입력 인수만 있고 , 출력 인수 X
        - 이벤트로 해석해 입력인수로 시스템 상태를 변경
        - 이벤트라는 사실을 명확하게 드러나는 이름을 선택해야 한다.
        
        ex : passwordAttemptFailedNtimes(int attempts) 
        
    - 플래그 인수
        - 추하다 → 여러 가지를 처리한다고 공표하는 셈 → 참과 거짓 각각 다른 기능을 한다는 의미
    
    - 이항 함수
        - 단항 함수보다 이해하는 데 시간이 걸린다.
        - 적절한 경우 → Point p = new Point (0,0)
        
    - 인수 객체
        
        ```java
        Circle makeCircle(double x, double y, double radius);
        
        Circle makeCircle(Point center, double radius);
        ```
        
        - 두 인수를 묶어서 개념을 표현했으므로 적절한 인수 객체다.
    
    - 동사와 키워드
        - 함수의 의도와 인수의 순서 의도 등을 표현하려면 좋은 이름이 필요
            1. 단항 함수 - 함수 와 인수 동사/명사 
                1. write(name) 
                2. writeField(name)
            - assertEquals 보다 assertExpectedEqualsActual(expected, actual)이 더 좋다

- 부수효과를 일으키지 말자

```java
public class UserValidator {
	private Cryptographer cryptographer;
	
	public boolean checkPassword(String userName, String password) {
		User user = UserGateway.findByName(userName);
		if (user != User.Null) {
			String codedPhrase = user.getPhraseEncodedByPassword();
			String phrase = cryptogrpaher.decrypt(codedPhrase, password);
			if("Valid Password".equals(phrase)) {
				**Session.initialize();** // 부수효과를 일으킨다.
				return true;
			}
		}
		return false;
	}
}

/*
	함수명으로는 세션 초기화를 알 수 없다. 
	기존 세션 정보를 지울 수 있는 위험이 존재 
	=> 세션 초기화해도 괜찮을 경우에만 해당 메서드를 사용할 수 있다.
	따라서 해당 메서드를 사용하려면 명시를 해줘야한다. ( 이름 변경 )
	=> 한가지만 한다는 규칙이 깨지게 된다.
*/
```

- 명령과 조회를 분리

  수행하거나 뭔가에 답하거나 둘 중 하나를 해야 한다.

  둘다 하면 안된다. → 객체 상태를 변경하거나 , 객체 정보를 반환하거나 둘 중 하나

  ex )

    ```java
    public boolean set(String attribute, String value);
    // 괴상한 코드다 
    
    if(set("username","unclebob"))// 독자 입장에서는 이해하기 쉽지 않다. 
    
    /** 명령과 조회를 분리 */
    
    if (attributeExists("username")) {
    	setAttribute("username","unclebob");
    }
    ```


- 오류 코드보다 예외를 사용
    - 명령 함수에서 오류 코드를 반환하는 방식은 명령/조회 구분 규칙을 위반

        ```java
        /** bad */
        if(deletePage(page)== E_OK) {
        	if(registry.deleteReference(page.name) ==E_OK) {
        		if(configKeys.deleteKey(page.name.makeKey()) == E_OK) {
        			logger.log("page deleted");
        		} else {
        			logger.log("configKey not deleted"):
        		}
        	} else {
        		logger.log("deleteReference from registry failed");
        	}
        } else {
        	logger.log("delete failed");
        	return E_ERROR;
        }
        
        /** 
        	good 1 
        */
        try {
        	deletePage(page);
        	registry.deleteReference(page.name);
        	configKeys.deleteKey(page.name.makeKey());
        } catch(Exception e) {
        	logger.log(e.getMessage());
        }
        
        /**
        	good 2
        	try/catch 블록은 원래 추하다.
        	코드 구조 혼란 -> 정사 동작과 오류 처리 동작을 뒤섞는다. 그러므로 try/catch 블록은 
        	별도 함수로 뽑아내자
        */
        
        public void delete(Page page) {
        	try {
        		deletePageAndAllReferences(page);
        	} catch (Exception e) {
        		logError(e);
        	}
        }
        
        private void deletePageAndAllReferences(Page page) throws Exception {
        	deletePage(page);
        	registry.deleteReference(page.name);
        	configKeys.deleteKey(page.name.makeKey());
        }
        
        private void logError(Exception e) {
        	logger.log(e.getMessage());
        }
        
        ```

        - 오류 처리도 한 가지 작업이다.

- 반복하지 마라!
    - 중복 제거

### 함수를 어떻게 짜죠?

- 글짓기와 비슷

  초안 작성 후 읽힐 때까지 고치는 것처럼

  함수 역시 처음엔 복잡하고 길다 , 들여쓰기 단계도 많고 중복된 루프도 많다

  이러한 서투른 코드도 테스트하는 단위 테스트 케이스를 만든다.

  그 후 코드를 다듬고 , 함수를 만들고 , 이름을 바꾸고 중복을 제거한다.

  메서드를 줄이고 , 순서를 바꾼다. 때로는 전체 클래스를 쪼갠다.

  이러는 중에도 단위 테스트를 통과 해야 한다.


### 🧐 생각 정리

1. 추상화 개념에 대한 이해
2. 함수는 한 기능만 , 이름의 중요성
3. 중복 X
4. 처음부터 완벽한 함수를 작성하는 개발자는 드물다. 지속적으로 다듬는 습관이 중요

### ❓ **궁금한 내용**