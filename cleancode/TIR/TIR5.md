# TIR 5 - 6장 객체와 자료구조 , 7장 오류 처리

생성일: 2022년 10월 14일 오후 1:18

## **TIR (Today I Read)**

## 객체와 자료구조

### 자료 추상화

```java
// 6-1
public class Point{
	public double x;
	public double y;
}

// 6-2
public interface Point{
	double getX();
	double getY();
	void setCartesian(double x, double y);
	double getR();
	double getTheta();
	void setPolar(double r, double theta);
}
```

6-2 직교좌표계를 사용하는지, 극좌표계를 사용하는지 알 수 없다. 그래도 인터페이스는 자료구조 명백하게 표현 혹은 자료 구조 이상을 표현한다.

- 클래스 메서드가 접근 정책을 강제
- 좌표를 읽을 때는 각 값을 개별적으로 읽어야 한다
- 좌표 설정 할 때는 두 값을 같이 설정

6-1 직교좌표계를 사용, 구현을 노출한다. 만약 변수가 private으로 되어있더라도 get,set이 있으면 구현을 노출하는 것 ( 변수 사이에 함수라는 계층을 넣더라도 구현이 저절로 감춰지지 않는다. )

- 구현을 감추려면 추상화가 필요 → 추상클래스를 제공해 사용자가 구현을 모른 채 자료의 핵심을 조작할 수 있어야 진정한 의미의 클래스

```java
// 구체적인 Vehicle 클래스
public interface Vehicle {
	double getFuelTankCapacityInGallons();
	double getGallonsOfGsoline();
}
자동차 연료 상태를 구체적인 숫자로 알려준다.

// 추상적인 Vehicle 클래스
public interface Vehicle {
	double getPercentFuelRemaining();
}
연료 상태를 백분율이라는 추상적인 개념으로 전달 
```

- 자료를 구체적으로 공개하기보다 추상적인 개념으로 표현하는 것이 좋다.
- 인터페이스 , get,set 함수만으로 추상화가 되지 않는다. ⇒ 객체가 포함하는 자료를 표현할 가장 좋은 방법을 고민이 필요하다.

  **⇒ 아무 생각 없는 get , set 함수 추가 방법은 bad**


### 자료/객체 비대칭

- 객체는 추상화 뒤로 자료를 숨긴채 자료를 다루는 함수만 공개
- 자료 구조 : 자료를 그대로 공개, 별다른 함수 제공 X

두 가지의 차이를 알아야 한다. ⇒ 본질적으로 상반된 개념

```java

// 절차적인 도형 클래스 
public class Square {
	public Point topLeft;
	public double side;
}

public class Rectangle {
	public Point topLeft;
	public double height;
	public double width;
}

public class Circle {
	public Point center;
	public double radius;
}

public class Geometry {
	public final double PI = 3.141592653589793;

	public double area(Object shape) throws NoSuchShapeException {
		if(shape instanceof Square) {
			Square s = (Square)shape;
			return s.side * s.side;
		} else if (shape instanceof Rectangle) {
			Rectangle r = (Rectangle)shape;
			return r.height * r.width;
		} else if (shape instanceof Circle) {
			Circle c = (Circle)shape;
			return PI * c.radius * c.radius;
		}
		throw new NoSuchShapeException();
	}
}

// 다형적인 도형 클래스 
public class Square implements Shape {
	private Point topLeft;
	private double side;

	public double area() {
		return side*side;
	}
}

public class Rectangle implements Shape {
	private Point topLeft;
	private double height;
	private double width;
	
	public double area() {
		return height * width;
	}
}

public class Circle implements Shape {
	private Point center;
	private double radius;
	public final double PI = 3.141592653589793;

	public double area(){
		return PI * radius * radius;
	}
}
```

- 절차적인 방법과 객체지향 방법의 차이점
    1. 절차적인 코드
        - 절차적인 코드는 기존 자료구조를 변경하지 않으면서 새 함수를 추가하기 쉽다

          ⇒ Geometry 클래스에 새 함수를 추가해도 기존 자료구조는 변경이 필요하지 않는다.

        - 새로운 자료구조 추가하기 어렵다
            - 자료구조를 추가했을 때 Geometry 코드를 변경한다.
    2. 객체지향 코드
        - 객체지향 코드는 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.
            - Shape 인터페이스에 함수를 변경하지 않고 새로운 도형 클래스를 추가해서 사용할 수 있다.
        - 새로운 함수 추가가 어렵다.
            - 인터페이스에 새로운 함수를 추가하려고 하면 모든 클래스를 고쳐야 한다.

⇒ 때에 따라 절차적인 코드가 적합할 수 있고, 객체지향이 적합할 수 있다. 모든 것이 객체라는 생각인 미신

**⇒ 객체 , 자료구조 차이를 이해 필요**

### 디미터 법칙

모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙

- 클래스 C의 메서드 f는 다음과 같은 객체의 메서드만 호출
    - 클래스 C
    - f가 생성한 객체
    - f인수로 넘어온 객체
    - C 인스턴스 변수에 저장된 객체

- 디미터 법칙을 어기는 듯 보이는 코드

  final String outputDir = ctxt.getOptions().getScratchDIr().getAbsolutePath();

  이러한 코드를 기차충돌이라 부른다.


- 기차 충돌 (train wreck)

  여러 객체가 한 줄로 이어진 기차처럼 보인다. 일반적으로 조잡하게 여겨지기 때문에 피해야한다.

  Options opts = ctxt.getOptions();

  File scratchDir = opts.getScratchDir();

  final String outputDir = scratchDir.getAbsolutePath();


- 잡종 구조

  절반은 객체, 절반은 자료구조인 형태

  양쪽의 단점만 모아놓은 구조 ⇒ 피하자


### 자료 전달 객체

자료 구조체 : 공개변수만 있고 함수는 없는 클래스

이런 자료 구조체를 자료 전달 객체라 한다 (Data Transfer Object ,DTO)

DTO는 데이터베이스와 통신하거나 소켓에서 받은 메세지의 구문을 분석할 때 유용

- 데이터베이스에 저장된 가공되지 않은 정보를 애플리케이션 코드에서 사용할 객체로 변환하는 일련의 단계에서 가장 처음으로 사용하는 구조체

⇒ 좀 더 일반적인 형태는 bean 구조

- 빈은 비공개 변수를 조회/설정 함수로 조작
- 활성 레코드
    - DTO의 특수한 형태
    - save나 find 같은 탐색 함수도 제공
    - 데이터베이스 테이블이나 다른 소스에서 자료를 직접 변환한 결과
    - 비즈니스 규칙을 추가해서 객체로 취급하는 개발자들도 흔하다 → 이는 바람직하지 않다. 이런 경우 잡종구조가 되어버린다.
    - 활성 레코드는 자료구조로 취급하는 것이 맞다.

### 결론

객체는 동작하고 자료를 숨긴다.

⇒ 기존 동작 변경하지 않고 새 객체 타입 추가하기 쉬우나 새동작 추가하기는 어렵다.

자료구조는 자료를 노출시킨다.

⇒ 새 동작을 추가하기는 쉬우나, 새 자료 구조를 추가하기는 어렵다.

시스템에 상황에 맞게 새로운 자료타입을 추가하는 유연성이 필요한 경우엔 객체, 새로운 동작을 추가하는 유연성이 필요한 경우엔 자료 구조가 더 적합하다.

---

## 오류 처리

### 오류 코드보다 예외처리를 사용하라

- 예외를 지원하지 않는 프로그래밍 언어가 많았다.
    - 오류 코드를 반환하는 방법이 전부였다.

```java
public class DeviceController {
	...
	public void sendShutDown() {
		DeviceHandle handle = getHandle(DEV1);
		// 디바이스 상태를 점검한다.
		if (handle != DeviceHandle.INVALID) {
			// 레코드 필드에 디바이스 상태를 저장한다. 
			retrieveDeviceRecord(handle);
			// 디바이스가 일시정지 상태가 아니라면 종료한다.
			if (record.getStatus() != DEVICE_SUSPENDED) {
				pauseDevice(handle);
				clearDeviceWorkQueue(handle);
				closeDevice(handle);
			} else {
				logger.log("Device suspended. Unable to shut down");
			}
		} else {
			logger.log("Invalid handle for: " + DEV1.toString());	
		}
	}
	...
}

public class DeviceController {
	...
	public void sendShutDown(){
		try {
			tryToShutDown();
		} catch (DeviceShutDownError e) {
			logger.log(e);
		}
	}

	private void tryToShutDown() throws DeviceShutDownError {
		DeviceHandle handle = getHandle(DEV1);
		DeviceRecord record = retrieveDeviceRecord(handle);

		pauseDevice(handle);
		clearDeviceWorkQueue(handle);
		closeDevice(handle);
	}

	private DeviceHandle getHandle(DeviceId id) {
		...
		throw new DeviceShutDownError("Invalid handle for: " + id.toString());
		...
	}
	...
}
```

### Try-Catch-Finally 문부터 작성하라

try 블록은 트랜잭션과 비슷함

- 예외를 발생할 코드를 짤 때는 try-catch-finally 문으로 시작하는 편이 낫다.
- try 블록에 무슨일이 생기든지 호출자가 기대하는 상태를 정의하기 쉬워지기 때문에 사용해야 한다.

```java
@Test(expected = StorageException.class)
public void retrieveSectionShouldThrowOnInvalidFileName() {
	sectionStore.retrieveSection("invalid - file");
}

public List<RecordedGrip> retrieveSection(String sectionName) {
	// 실제로 구현할 때까지 비어 있는 더미를 반환한다.
	return new ArrayList<RecordedGrip>();
}
// 위에 코드가 예외를 던지지 않으므로 단위 테스트 실패 

public List<RecordedGrip> retrieveSection(String sectionName) {
	try {
		FileInputStream stream = new FileInputStream(sectionName);
	} catch (Exception e) {
		throw new StorageException("retrieval error", e);
	}
	return new ArrayList<RecordedGrip>();
}

// 코드가 예외를 던지기 변경했으므로 테스트는 성공 catch 블록에서 예외유형을 좁혀서
// FileInputStream 생성자에서 던지는지 리팩토링 

public List<RecordedGrip> retrieveSection(String sectionName) {
	try {
		FileInputStream stream = new FileInputStream(sectionName);
		stream.close();
	} catch (FileNotFoundException e) {
		throw new StorageException("retrieval error", e);
	}
	return new ArrayList<RecordedGrip>();
}

```

- 먼저 강제로 예외를 일으키는 테이트 케이스를 작성한 후 테스트를 통과하는 코드 작성을 권장

  ⇒ try 블록의 트랜잭션 범위부터 구현하게 되므로 범위 내 트랜잭션 본질을 유지하기 쉬워진다.


### 미확인(unchecked) 예외를 사용하라

### 예외에 의미를 제공

- 예외를 던질 때 전후 상황 제공 ⇒ 오류가 발생한 원인과 위치를 찾기 쉬워진다.
- 실패한 코드의 의도를 파악하려면 호출 스택만으로는 부족하다 .
    - 오류 메세지에 정보를 담아 예외와 함께 던진다. 실패한 연산 이름 , 실패 유형도 언급
    - logging 기능을 이용

### 호출자를 고려해 예외 클래스를 정의

오류를 분류할 때 오류를 잡아내는 방법이 프로그래머에게 가장 중요하다

```java
// 형편없이 오류를 분류한 사례 - 외부 라이브러리가 던질 예외를 모두 잡아낸다.
// 예외 대응 방식이 예외 유형과 무관하게 동일하다. 그렇기에 코드를 간결하게 고치기 쉽다.
ACMEPort port = new ACMEport(12);

try {
	port.open();
} catch (DeviceResponseException e) {
	reportPortError(e);
	logger.log("Device response exception",e);
} catch (ATM1212UnlockedException e) {
	reportPortError(e);
	logger.log("Unlock exception", e);
} catch (GMXError e) {
	reportPortError(e);
	logger.log("Device response exception", e);
} finally {
...
}

/** Good */

LocalPort port = new LocalPort(12);
try {
	port.open();
} catch (PortDeviceFailure e) {
	reportError(e);
	logger.log(e.getMessage(), e);
} finally {
	...
}

// LocalPort 클래스는 단순히 ACMEPort 클래스가 던지는 예외를 잡아 변환하는 wrapper 클래스
public class LocalPort {
	private ACMEPort innerport;
	
	public LocalPort(int portNumber){
		innerPort = new ACMEPort(portNumber);
	}
	public void open(){
		try {
			innerPort.open();
		} catch (DeviceReponseException e) {
			throw new PortDeviceFailure(e);
		} catch (ATM1212UnlockedException e) {
			throw new PortDeviceFailure(e);
		} catch (GMXError e) {
			throw new PortDeviceFailure(e);
		}
	}
}
```

- 외부 API를 사용할 때는 감싸기 기법이 최선
    - 감싸면 외부라이브러리와 프로그램 사이에서 의존성이 크게 줄어든다.
    - 다른 라이브러리로 갈아타도 비용이 적다.
    - 외부 API를 호출하는 대신 테스트 코드를 넣어주는 방법으로 프로그램을 테스트하기도 쉬워진다.
    - 특정 업체가 API를 설계한 방식에 발목이 잡히지 않는다.

### 정상 흐름을 정의하라

```java
try {
	MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
	m_total += expenses.getTotal();
} catch(MealExpensesNotFound e) {
	m_total += getMealPerDiem();
}
// 식비를 비용을 청구하지 않을 경우 기본 식비를 총계에 더한다. 
// 특수한 상황을 처리할 필요가 없으면 더 좋지 않을까?? 

MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
m_total += expenses.getTotal();

// 위처럼 간결한 코드를 가능하게 하기 위해 ExpensesReportDAO를 고쳐 언제나 MealExpense 객체를 반환 
// 청구한 식비가 없다면 일일 기본 식비를 반환하는 MealExpense 객체를 반환한다. 
public class PerDiemMealExpenses implements MealExpenses {
	public int getTotal() {
		// 기본값으로 일일 기본 식비를 반환한다.
	}
}

```

⇒ 이러한 사례를 특수 사례 패턴이라고 한다. 클래스를 만들거나 객체를 조작해서 특수 사례 처리하는 방식

### null을 반환하지 마라

- 오류를 유발하는 첫번째 행위는 null 반환하는 행위

```java
public void registerItem(Item item){
	if (item != null) {
		ItemRegistry registry = peristentStore.getItemRegistry();
		if(registry != null) {
			Item existing = registry.getItem(item.getID());
			if (existing.getBillingPeriod().hasRetailOwner()) {
				existing.register(item);
			}
		}
	}
}

/*
	위 코드는 나쁜 코드 
	null 반환을 대비해서 여러 분기처리를 하기 때문 
	만약 peristentStore가 null이라면 NullPointerException 발생 
	null 확인 코드가 너무 많아서 문제 
	
	메서드에서 null을 반환하는 대신 예외를 던지거나 특수 사례 객체를 반환하자
	사용하려는 외부 API가 null을 반환할 경우 감싸기 메서드를 구현해 예외 던지기 or 특수 사례 객체 반환 방식 고려 
*/ 
```

```java
List<Employee> employees = getEmployees();
if (employees != null) {
	for(Employee e : employees) {
		totalPay += e.getPay();
	}
}

List<Employee> employees = getEmployees();
for(Employee e : employees) {
	totalPay += e.getPay();
}

public List<Employee> getEmployees() {
	if(...직원이 없다면...) {
		return Collections.emptyList();
	}
}

```

### Null을 전달하지 마라

- null을 반환하는 방식도 나쁘지만 전달하는 것이 더 나쁘다. 정상적인 인수로 null을 기대하는 api가 아니라면 피하자

```java
public class MeticsCalculator {
	public double xProjection(Point p1, Point p2) {
		return (p2.x - p1.x) * 1.5;
	}
	...
}

// NPE 보다는 낫지만 InvalidArgumentException 을 처리할 부분을 만들어야 한다.
public class MeticsCalculator {
	public double xProjection(Point p1, Point p2) {
		if (p1 == null || p2 == null) {
			throw InvalidArgumentException("Invalid argument for MetricsCalculator.xProjection");
		}
		return (p2.x - p1.x) * 1.5;
	}
}

// 또다른 대안 assert문 사용 
public class MeticsCalculator {
	public double xProjection(Point p1, Point p2) {
		assert p1 != null : "p1 should not be null";
		assert p2 != null : "p2 should not be null";
		return (p2.x - p1.x) * 1.5;
	}
}
```

- 백프로 문제를 해결하진 못한다. 대다수 프로그래밍 언어는 호출자가 실수로 넘긴 null을 적절히 처리하는 방법이 없다.
  그렇기에 null을 넘기지 못하도록 금지하는 정책이 합리적이다.

### 결론

클린코드는 가독성도 중요하지만 그만큼 안정성도 높아야 한다. 두 가지는 상충하는 목표가 아니기 때문에 깔끔한 오류처리 코드를 작성하면서 가독성과 안정성 두마리 토끼를 잡을 수 있다.

- 프로그램 논리와 오류 처리를 분리시켜서 코드 작성하자

---

### ❓ **궁금한 부분 && 공부 필요한 부분**

- 디미터 법칙

[[OOP] 디미터의 법칙(Law of Demeter)](https://mangkyu.tistory.com/147)

- 자바의 정석 예외처리 부분 읽기

- 트랜잭션의 개념 다시 공부