package chapter4;

public class HeartKing implements Judge{


    @Override
    public void startTrial(String request) {
        System.out.println(request + " 의 요청으로 재판을 시작한다.");

    }

    @Override
    public String judgement(Testimony testimony) {
        String testimonyText = testimony.getTestimony();
        String judgement = testimonyText + "내용에 입각해서 판결은 내린다.";
        return judgement;
    }

    public void govern(){
        System.out.println("통치하다");
    }
}
