package chapter3;


public class Heart implements TrumphHuman {

    private String pattern;
    private String job;

    public Heart(String pattern, String job) {
        this.pattern = pattern;
        this.job = job;
    }

    @Override
    public void turnOver() {
        System.out.println(" 트럼프 카드 : 뒤집어지다.");
    }

    @Override
    public void kneelDown() {
        System.out.println(" 트럼프 카드 : 납작 엎드리기");
    }

    @Override
    public void flapWalk() {
        System.out.println(" 트럼프 인간 : 펄럭거리면서 걷기");
    }

    public void love() {
        System.out.println(" 하트 : 사랑 ");
    }
}
