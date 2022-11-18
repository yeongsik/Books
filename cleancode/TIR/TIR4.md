# TIR 4 - 5장 형식 맞추기

생성일: 2022년 10월 13일 오후 1:25

## **TIR (Today I Read)**

## 5장 형식 맞추기

깔끔한 코드는 중요하다. 코드는 계속 변하기 때문에 유지보수를 위해서 형식을 맞춰야 한다.

### 적절한 행길이 유지

- 세로 길이

  대부분 200줄인 (최대 500) 파일로도 커다란 시스템 구축을 할 수 있다.

  이해하기 쉬우려면 너무 긴 파일이면 안된다.

- 신문 기사처럼 작성

  소스도 기사처럼 파일 제목으로 커다란 개념을 설명할 수 있어야한다.

  ⇒ 파일 제목 고차원 개념 , 알고리즘을 설명하자

- 개념은 빈행으로 구분

  빈행은 새로운 개념이 시작한다는 시각적 단서이므로

  소스 중간에 필요할 때 사용해야 한다.

  ex ) 패키지 선언부, import문, 각 함수 사이 등

- 세로 밀집도 && 수직 거리

  서로 밀접한 개념은 수직적으로 가까이 있어야 한다.

    - 변수 선언
        - 지역 변수 : 함수 맨 앞 혹은 루프 시작 할 때

          변수를 사용하는 위치와 최대한 가까이 있어야 한다.

    - 인스턴스 변수
        - 클래스 맨 처음에 선언
        - 메서드 중간에 인스턴스 변수들이 나타나면 당황스럽다.
    - 종속 함수
        - 호출하는 함수가 호출되는 함수보다 먼저 위치 해야한다 ( 읽기 편하게 )
    - 개념적 유사성
        - 개념적 친화도가 높은 코드끼리 가까이 배치
            - 친화도가 높은 요인
                - 한 함수가 한 함수를 호출해서 생기는 직접적인 종속성
                - 비슷한 동작을 수행하는 일군의 함수

- 세로 순서
    - 고차원에서 저 차원으로

### 가로형식 맞추기

한 행의 가로 길이 100~120자 정도로 사용하자

- 가로 공백과 밀집도

  띄어쓰기를 이용해서 밀접한 개념과 느슨한 개념을 표현

    - 할당 연산자 왼쪽 요소와 오른쪽 요소를 확실히 구분하기 위해 가로 공백 사용

      ex : int listSize = line.length();

    - 함수의 경우 괄호 안에 공백을 넣지 않는다. (함수와 인수는 밀접하기 때문에)

      ex : lineWidthHistogram.addLine(linSize, lineCount);

    - 연산자 우선순위를 위해 가로공백 사용

      ex : reutrn (-b + Math.sqrt(determinant)) / (2*a);

      곱셈이 우선순위 가장 높기 때문에 가로공백 X , 우선순위가 낮은 덧셈 , 뺄셈의 경우 띄어쓰기

      ⇒ 코드 형식을 맞춰주는 도구 들은 연산 우선순위를 고려하지 못하므로 수식에 똑같은 간격을 적용하므로

      나중에 없어지는 경우가 흔함.


- 가로정렬
    - 접근 제어자 , 자료형 , 이름을 나란히 정렬 시켜서 사용해서 사용했지만 유용하지 않아서 가로 정렬 사용 X
    - 정렬이 필요할 정도로 선언부가 길어진다면 클래스를 쪼개자

- 들여쓰기

  소스 파일 안에는 계층이 있다.

  클래스에 적용되는 정보 , 각 메서드에 적용되는 정보, 블록 내 블록에 재귀적으로 적용되는 정보가 있다.

  각 계층을 표현하기 위해 들여쓰기를 사용

    - 들여쓰기 무시하기

      간단한 if문, 짧은 while문, 짧은 함수에 들여쓰기 규칙을 무시하고 싶어도 원점으로 돌아가서 들여쓰기를 하자


### 팀규칙

팀에 속해있다면 팀 규칙을 잘 지켜서 코딩 해야한다.

좋은 시스템은 읽기 쉬운 문서로 이뤄진다.

### 밥아저씨의 형식 규칙 ( 좋은 예시 )

```java
public class CodeAnalyzer implements JavaFileAnalysis {
	private int lineCount;
	private int maxLineWidth;
	private int widestLineNumber;
	private LineWidthHistogram lineWidthHistogam;
	private int totalChars;

	public CodeAnalyzer() {
		lineWidthHistogram = new LineWidthHistogram();
	}

	public static List<File> findJavaFiles(File parentDirectory) {
		List<File> files = new ArrayList<File>();
		findJavaFiles(parentDirectory, files);
		return files;
	}

	private static void findJavaFiles(File parentDirectory, List<File> files) {
		for (File file : parentDirectory.listFiles()) {
			if (file.getName().endsWith(".java"))
				files.add(file);
			else if (file.isDirectory())
				findJavaFiles(file, files);
		}
	}

	public void analyzeFile(File javaFile) throws Exception {
		BufferedReader br = new BufferedRead(new FileReader(javaFile));
		String line;
		while((line = br.readLine()) != null)
			measureLine(line);
	}

	private void measureLine(String line) {
		lineCount++;
		int lineSize = line.length();
		totalChars += lineSize;;
		lineWidthHistogram.addLine(lineSize, lineCount);
		recordWidestLine(lineSize);
	}

	private void recordWidestLine(int lineSize) {
		if (lineSize > maxLineWidth) {
			maxLineWidth = lineSize;
			widestLineNumber = lineCount;
		}
	}

	public int getLineCount() {
		return lineCount;
	}
	
	public int getMaxLineWidth() {
		return maxLineWidth;
	}

	public int getWidestLineNumber() {
		return widestLineNumber;
	}

	public LineWidthHistogram getLineWidthHistogram() {
		return lineWidthHistogram;
	}

	public double getMeanLineWidth() {
		return (double)totalChars/lineCount;
	}

	public int getMedianLineWidth() {
		Integer[] sortedWidths = getSortedWidths();
		int cumulativeLineCount = 0;
		for (int width : sortedWidths) {
			cumulativeLineCount += lineCountForWidth(width);
			if (cumulativeLineCount > lineCount/2)
				return width;
		}
		throw new Error("Cannot get here");
	}

	private int lineCountForWidth(int width) {
		return lineWidthHistogram.getLinesforWidth(width).size();
	}

	private Integer[] getSortedWidths() {
		Set<Integer> widths = lineWidthHistogram.getWidths();
		Integer[] sortedWidths = (widths.toArray(new Integer[0]));
		Arrays.sort(sortedWidths);
		return sortdWidths;
	}
}
```

### 📖 생각 정리

### ❓ **궁금한 내용**