# TIR 3 - 4장 주석

생성일: 2022년 10월 12일 오전 9:33

## **TIR (Today I Read)**

## 주석

- 나쁜 코드에 주석을 달지 마라 . 새로 짜라

- 주석은 오래될수록 코드에서 멀어진다.
    - 코드는 변화하고 주석은 그 코드를 따라가지 못한다.
- 코드로 의도를 표현하자
- 부정확한 주석은 없는것보다 안좋다.

### 코드로 의도 표현

```java
/** bad */
// 직원에게 복지 혜택을 받을 자격이 있는지 검사 
if ((employee.flags & HOURLY_FLAG) && (employee.age > 65))

/** good */
if (employee.isEligibleForFullBenefits())

```

### 좋은 주석

- 법적인 주석

  저작권 정보, 소유권 정보 등

- 정보제공주석
    - 기본적인 정보 제공

    ```java
    // 테스트 중인 Responder 인스턴스를 반환한다.
    protected abstract Responder responderInstance();
    
    /** better */
    protected abstract Responder responderBeingTested();
    
    // kk:mm:ss EEE, MMM dd, yyyy 형식이다.
    Pattern timeMatcher = Pattern.compile(
    	"\\d*:\\d*:\\d* \\w*, \\w* \\d*, \\d*");
    
    -> 사용된 정규표현식이 시간과 날짜를 뜻한다고 설명 -> SimpleDateFormat.format 을 사용하여
    코드를 옮겨주면 주석이 필요 없어진다.
    ```

- 의도 설명 주석

    ```java
    public int compareTo(Object o) {
    	if(o instanceof WikiPagePath) {
    		WikiPagePath p = (WikiPagePath) o;
    		String compressedName = StringUtil.join(names, "");
    		String compressedArgumentName = StringUtil.join(p.names, "");
    		return compressedName.compareTo(compressedArgumentName);
    	}
    	return 1; // 오른쪽 유형이므로 정렬 순위가 더 높다.
    }
    
    public void testConcurrentAddWidgets() throws Exception {
    	WidgetBuilder widgetBuilder =
    		new WidgetBuilder(new Class[]{BoldWidget.class});
    	String text = "'''bold text'''";
    	ParentWidget parent =
    		new BoldWidget(new MockWidgetRoot(), "'''bold text'''");
    	AtomicBoolean failFlag = new AtomicBoolean();
    	failFlag.set(false);
    
    	// 스레드를 대량 생성하는 방법으로 어떻게든 경쟁 조건을 만들려 시도한다. <- 저자의 의도가 들어남 
    	for (int i = 0; i< 25000; i++) {
    		WidgetBuilderThread widgetBuilderThread = 
    			new WidgetBuilderThread(widgetBuilder, text, parent , failFlag);
    		Thread thread = new Thread(widgetBuilderThread);
    		thread.start();
    	}
    	assertEquals(false, failFlag.get());
    }
    ```

- 의미를 명료하게 밝히는 주석

  모호한 인수나 반환값은 의미를 읽게 좋게 표현하면 이해하기 쉬워진다.

  그러나 인수나 반환값이 표준 라이브러리 거나 변경하지 못하는 코드라면 의미를 명료하게 밝히는 주석이 필요


- 결과 경고 주석

  ex ) 특정 테스트 케이스를 꺼야하는 이유 설명

    ```java
    // 여유 시간이 충분하지 않다면 실행하지 마십시오.
    public void _testWithReallyBigFile() {
    	/*.............*/
    }
    
    => @ignore 속성을 이용해서 테스트 케이스를 꺼버릴 수 있다.
    ex) @ignore("실행이 너무 오래 걸린다.")
    
    메서드 앞에 _붙이는 방법은 JUnit4 나오기전에 일반적인 관례
    
    /* good */
    
    public static SimpleDateFormat makeStandardHttpDateFormat() {
    	// SimpleDateFormat은 스레드에 안전하지 못하다.
    	// 따라서 각 인스턴스를 독립적으로 생성해야 한다. 
    
    	SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    	df.setTimeZone(TimeZone.getTimeZone("GMT"));
    	return df;
    }
    ```


- TODO 주석

  당장 구현하기 어려운 업무를 기술

  하지만 시스템에 남겨놓으면 나쁜 코드다. ⇒ IDE 에 TODO 주석을 찾아주는 기능이 있기 때문에 잊어버릴 염려도 없으니 떡칠한 코드를 주의하자


- 중요성을 강조하는 주석

    ```java
    String listItemContent = match.group(3).trim();
    // 여기서 trim은 정말 중요하다. trim 함수는 문자열에서 시작 공백을 제거한다. 
    // 문자열에서 시작 공백이 있으면 다른 문자열로 인식되기 때문이다.
    new ListItemWidget (this, listItemContent, this.level + 1);
    return buildList(text.substring(match.end());
    ```


### 나쁜 주석

대다수의 주석이 해당 범주에 속한다. 프로그래머의 주절거리는 독백이 되지 않도록 주의

- 주절거리는 주석
    - 특별한 이유없이 작성한 주석

    ```java
    public void loadProperites() {
    	
    	try {
    		String propertiesPath = propertiesLocation + "/" + PROPERTEIS_FILE;
    		FileInputStream properitesStream = new FileInputStream(propertiesPath);
    		loadedProperties.load(propertiesStream);
    	} catch (IOException e) {
    		// 속성 파일이 없다면 기본값을 모두 메모리로 읽어 들였다는 의미다.
    		
    		=> 해당 주석은 무슨 의미일까? 다른 사람들에게는 의미가 전해지지 않는다. 
    		기본값은 언제 읽어 들이는지에 대한 시점도 불분명
    		.load 시인지, .load의 예외처리 시점인지 모른다.
    	}
    }
    ```


- 같은 이야기를 중복하는 주석

  코드 내용과 중복하는 경우 → 코드보다 주석을 읽는 시간이 더 오래 걸릴 수 있다.

    ```java
    // this.closed가 true일 때 반환되는 유틸리티 메서드다.
    // 타임아웃에 도달하면 예외를 던진다.
    public synchronized void waitForClose(final long timeoutMillis) throws Exception {
    	if(!closed) {
    		wait(timeoutMillis);
    		if(!closed)
    			throw new Exception("MockResponseSender could not be closed");
    	}
    }
    
    public abstract class ContainerBase implements Container, Lifecycle, PipeLine,
    	MBeanRegistration, Serializable {
    	
    	/**
    	 * 이 컴포넌트의 프로세서 자연값
    	 */
    	protected int backgroundProcessorDelay = -1; -> 주석이랑 코드랑 같은 내용을 말하고 있다. (중복)
    	
    	/**
    	 * 이 컴포넌트를 지원하기 위한 생명주기 이벤트 
    	 */
    	protected LifecycleSupport lifecycle = new LifecycleSupport(this);
    }
    ```


- 오해할 여지가 있는 주석

    ```java
    // this.closed가 true일 때 반환되는 유틸리티 메서드다.
    // 타임아웃에 도달하면 예외를 던진다.
    public synchronized void waitForClose(final long timeoutMillis) throws Exception {
    	if(!closed) {
    		wait(timeoutMillis);
    		if(!closed)
    			throw new Exception("MockResponseSender could not be closed");
    	}
    }
    ```

  주석에는 true일때 반환한다고 써있지만 , 막상 코드는 기다렸다가도 true가 아닐 경우 예외처리를 한다.으


- 의무적으로 다는 주석

  ex) javadocs 주석

    ```java
    /**
     *
     *
     * @param title CD 제목
     * @param author CD 저자
     * @param tracks CD 트랙 숫자
     * @param durationInMinutes CD 길이(단위:분)
     */
    public void addCD(String title, String author, int tracks, int durationInMinutes) {
    
    }
    ```


- 이력을 기록하는 주석

  git 같은 형상관리가 있기 때문에 기록 X ( 형상관리가 없었을 때 기록했던 주석 )


- 위치를 표시하는 주석

  ex ) // Actions //////////////////////////////////////////////


- 닫는 괄호에 다는 주석

  닫는 괄호에 다는 주석보다 함수를 줄이려고 하자

  작고 캡슐화된 함수엔 필요가 없다.

    ```java
    public class wc{
    	public static void main(String[] args) {
    		BufferedRead in = new BufferedRead(new InputStreamReader(System.in));
    		String line;
    		int lineCount = 0;
    		int charCount = 0;
    		int wordCount = 0;
    		try {
    			while((line = in.readLine()) != null) {
    				lineCount++;
    				charCount += line.length();
    				String words[] = line.split("\\W");
    				wordCount += words.length;
    			} //while
    			System.out.println("wordCount = " + wordCount);
    			System.out.println("lineCount = " + lineCount);
    			System.out.println("charCount = " + charCount);
    		} //try 
    	]
    }
    ```


- 주석으로 처리한 코드
    - 이유가 있어서 남겨 놓았으리라고, 중요하니까 지우면 안된다고 생각하지만 쓸모없는 코드들이 점점 쌓이게 되면 좋지 않다.
- HTML 주석

### 📖 내용 정리

- **주석을 쓰기보다 코드로 다 표현하도록 하자**
- 전회사에서 썼던 주석들을 나쁜 주석 예시에 많이 나왔다. 다음부터 주의하고 최대한 주석을 적게 사용해보자

  ⇒ 전회사에서 썼던 주석들

    - javadocs 주석
    - 코드와 중복되는 주석
    - 코드 주석

- 좋은 주석
    - 법적인 주석
    - 정보 제공 주석
    - 의도 설명
    - 의미 명료
    - 결과 경고
    - TODO 주석
- 나쁜 주석
    - 코드와 중복 주석
    - 오해할 여지있는 주석
    - 코드 주석
    - 의무적으로 다는 주석
    - 닫는 주석