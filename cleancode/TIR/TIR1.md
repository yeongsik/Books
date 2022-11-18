# TIR 1

생성일: 2022년 10월 7일 오전 9:30

## **TIR (Today I Read)**

### 📖 **오늘 읽은 내용**

### 1장 깨끗한 코드

- 좋은 코드 나쁜 코드 구분 하기 → 좋은 코드 작성 → 나쁜 코드를 좋은 코드로 바꾸기

- 나쁜 코드는 회사를 망하게 하는 원인
    - 시간에 쫓겨 마구 짰을 때
    - 나쁜 코드는 엉킨 덩굴 , 함정이 가득한 늪지대 → 힘겹게 헤쳐나가야한다
    - 나중에 코드를 고쳐야지 했을 때의 나중은 오지 않는다.
- 프로그래머는 바쁜 일정 와중에도 좋은 코드를 사수해야한다. 그게 프로그래머의 책임

- 가장 빠른 길은 좋은 코드를 유지하는 것
    - 깨끗한 코드를 어떻게 작성할까?
        - 코드 감각이 필요하다. → 나쁜 모듈을 보고 좋은 모듈로 수정할 개선 방안이 떠올라야 한다.


### 깨끗한 코드란

1. 논리가 간단 → 버그가 숨어들지 않는다.
2. 의존성을 최대한 줄여야 한다 → 유지보수가 쉬워진다.
3. 오류는 명백한 전략을 통해서 처리

- 세세한 사항까지 꼼꼼하게 처리하는 코드가 깨끗한 코드
- 테스트 케이스가 없는 코드는 깨끗한 코드가 아니다.

- 모든 테스트를 통과
- 중복이 없어야 한다.
- 시스템 내 모든 설계 아이디어를 표현
- 클래스 , 메서드 , 함수 등을 최대한 줄인다.

효율과 가독성

### 2장 의미 있는 이름

- 의도를 분명하게 밝혀라
- 같은 코드인데 변수명를 명확하게 하니 이해가 쉬워졌다.

```java

/* before */
public List<int[]> getThem() {
	List<int[]> list1 = new ArrayList<int[]>();
	for ( int[] x : theList ) {
		if (x[0] == 4) {
			list1.add(x);
		}
	}
	return list1;
}

/* after 1 */
public List<int[]> getFlaggedCells() {
	List<int[]> flaggedCells = new ArrayList<int[]>();
	for (int[] cell : gameBoard) {
		if(cell[STATUS_VALUE] == FLAGGED) {
			flaggedCells.add(cell);
		}
	}
	return flaggedCells;
}

/* after 2 */
public List<int[]> getFlaggedCells() {
	List<int[]> flaggedCells = new ArrayList<int[]>();
	for (int[] cell : gameBoard) {
		if(cell.isFlagged()) {
			flaggedCells.add(cell);
		}
	}
	return flaggedCells;
}
```

- 그릇된 정보 피하기

  나름 널리 쓰는 의미의 단어를 다른 의미로 사용하면 안된다.

  ex ) hp , aix , sco 등

  실제 List 가 아니면 accountList라 명명하지 않는다.

  ⇒ accountGroup , bunchOfAccounts , accounts 라 명명하자


- 의미있게 구분하기
    - 흡사한 이름 사용 X
    - 불용어 추가 의미 없다.
        - 불용어 리스트 : Data , Info , a, an , the , variable , Object , String 등
        - ex ) Product라는 클래스가 있는데 ProductData 혹은 ProductInfo 클래스를 만들 경우 → 두 가지 클래스를 구분하기 쉽지 않아짐
        - moneyAccount와 money , customerInfo 와 customer , accountData 와 account , theMessage와 message

    ```java
    /* bad */
    public static void copyChars(char a1[] , char a2[]) {
    	for ( int i = 0; i < a1.length; i++) {
    		a2[i] = a1[i];
    	}
    }
    
    /* good */
    public static void copyChars(char source[] , char destinaion[]) {
    	for (int i = 0; i < source.length; i++) {
    		destination[i] = source[i];
    	}
    }
    ```


- 발음하기 쉬운 이름

    ```java
    /* bad */
    class DtaRcrd102 {
    	private Date genymdhms;
    	private Date medymdhms;
    	private final String pszqint = "102";
    	/* ..... */
    }
    
    /* good */
    class Customer {
    	private Date generationTimestamp;
    	private Date modificationTimestamp;
    	private final String recordId = "102";
    	/* ..... */
    }
    ```


- 검색하기 쉬운 이름 사용

  MAX_CLASSES_PER_STUDENT 는 grep으로 찾기 쉽지만 숫자 7은 어렵다.

  7이 들어가는 파일과 수식이 많기 때문

    ```java
    
    /* before */
    for (int j=0; j<34; j++) {
     s+= (t[j]*4)/5;
    }
    
    /* after */
    int realDaysPerIdealDay = 4;
    const int WORK_DAYS_PER_WEEK = 5;
    int sum = 0;
    for (int j=0; j< NUMBER_OF_TASKS; j++) {
    	int realTaskDays = taskEstimate[j] * realDaysPerIdealDay;
    	int realTaskWeeks = (realTaskDays / WORK_DAYS_PER_WEEK);
    	sum += realTaskWeeks;
    }
    ```

- 인터페이스 클래스와 구현 클래스

  옛날 코드 : 인터페이스 클래스 앞에 접두어 I 를 붙였다.

  남에게 인터페이스라는 사실을 알리고 싶지 않다. → 과도한 정보를 제공하므로

  인터페이스 클래스를 ShapeFactory , 구현 클래스를 ShapeFactoryImp

  인코딩을 구현클래스에 하는 것이 낫다 → 여기서 인코딩은 아마 구분하는 것으로 이해


- 문자 하나만 사용하는 변수 이름 X ( for 문 안에 i , j , k 는 괜찮다 ⚠️ l 은 절대 안된다 )

  ( 단 루프 범위가 아주 작고 다른 이름과 충돌하지 않을 때 괜찮다. )


- 클래스 이름

  객체나 클래스는 명사 , 명사구가 적합

  ex ) Customer , WikiPage , Account , AddressParser → 좋은 예

  Manager , Processor , Data , Info 등과 같은 단어는 피하고 , 동사 사용 X

- 메서드 이름

  동사나 동사구가 적합

  ex ) postPayment , deletePage , save 등

  접근자(Accessor) , 변경자(Mutator), 조건자(Predicate)는 자바빈 표준에 따라 값 앞에 get,set,is를 붙임

- 생성자를 오버로드 할 땐 정적 팩토리 메서드를 사용

  메서드는 인수를 설명하는 이름 사용

  위 코드가 아래 코드보다 좋다.

  해당 생성자는 private 선언

    ```java
    /* good */
    Complex fulcrumPoint = Complex.FromRealNumber(23.0);
    
    /* bad */
    Complex fulcrumPoint = new Complex(23.0); 
    ```

- 기발한 이름 피하기
    - 기발함 대신 명료함을 선택

- 한 개념에 한 단어를 사용

  추상적인 개념 하나에는 단어 하나

  똑같은 메서드를 클래스 마다 다르게 부르면 혼란

  ex ) 동일 코드 기반에서 controller , manager , driver 를 섞어 쓸 경우 혼란 발생


- 프로그래머들이 이해하기 쉬운 용어를 사용해도 좋다.
    - 전산 용어 , 알고리즘 , 패턴 이름 , 수학 용어 등
    - 모든 이름을 domain 영역에서 가져오는 것은 현명하지 못하다.
- domain에서 가져온 이름을 사용
    - 적당한 프로그래머 용어가 없다면 도메인 영역의 이름을 사용
    - 해법 영역과 문제(domain)영역을 구분할 줄 알아야 한다.

- 의미 있는 맥락 추가

  number , verb , pluralModifier 세 변수의 맥락을 추측해야한다. → 하나의 클래스에 넣어서 맥락을 확실하게 해주었다.

    ```java
    /* before */
    public void printGuessStatistics(char candidate, int count) {
    	String number;
    	String verb;
    	String pluraModifier;
    	if(count==0) {
    		number = "no";
    		verb = "are";
    		pluraModifier = "s";
    	} else if (count==1) {
    		number = "1";
    		verb = "is";
    		pluralModifier = "";
    	} else {
    		number = Integer.toString(count);
    		verb = "are";
    		pluralModifier = "s";
    	}
    	String guessMessage = String.format( 
    		"There %s %s %s%s", verb, number, candidate, pluralModifier
    	);
    	print(guessMessage);
    }
    
    /* after */
    public class GuessStatisticsMessage {
    	private String number;
    	private String verb;
    	private String pluralModifier;
    	
    	public String make(char candidate, int count) {
    		createPluralDependMessageParts(count);
    		return String.format(
    			"There %s %s %s%s",
    			verb, number , candidate, pluralModifier);
    	}
    
    	private void createPluralDependMessageParts(int count){
    		if (count==0) {
    			thereAreNoLetters();
    		} else if (count==1) {
    			thereIsOneLetter();
    		} else {
    			thereAreManyLetters(count);
    		}
    	}
    	
    	private void thereAreManyLetters(int count){
    		number = Integer.toString(count);
    		verb = "are";
    		pluralModifier = "s";
    	}
    
    	private void thereIsOneLetter() {
    		number = "1";
    		verb = "is";
    		pluralModifier ="";
    	}
    
    	private void thereAreNoLetters() {
    		number = "no";
    		verb = "are";
    		pluralModifier = "s";
    	}
    }
    ```


- 불필요한 맥락 제거

  Gas Station Deluxe 라는 어플리케이션을 만들 때 모든 클래스 앞에 GSD로 시작 X

  IDE 에서 G입력 후 자동완성 키를 누르면 모든 클래스를 열거

  ⇒ GSD라는 불필요한 맥락 제거


### ❓ **궁금한 내용이 있거나, 잘 이해되지 않는 내용**

- 의존성을 줄여야한다는 뜻에서 의존성을 다시 공부

  [객체지향의 올바른 이해 : 7. 의존(Dependency)과 책임(Responsibility)](https://effectiveprogramming.tistory.com/entry/%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5%EC%9D%98-%EC%98%AC%EB%B0%94%EB%A5%B8-%EC%9D%B4%ED%95%B4-%EC%B1%85%EC%9E%84Responsibility)

  [의존성이란](https://velog.io/@huttels/%EC%9D%98%EC%A1%B4%EC%84%B1%EC%9D%B4%EB%9E%80)

  [의존성 주입(DI, Dependency Injection)](https://jaeseongdev.github.io/development/2021/04/10/%EC%9D%98%EC%A1%B4%EC%84%B1_%EC%A3%BC%EC%9E%85_DI/)

- 작게 추상화란

  [추상화 - 용어 사전 | MDN](https://developer.mozilla.org/ko/docs/Glossary/Abstraction)

  [추상화](https://velog.io/@ha0kim/2020-12-21)

- 자바빈 표준