package chapter3;

public class Main {

    public static void main(String[] args) {
        TrumphCard heart = new Heart("heart" , "card");
        TrumphHuman heartKing = new Heart("heart" , "king");
        Heart heartPattern = new Heart("heart" , "specialHeart");
        // 트럼프 카드 의 서브 타입으로 트럼프 인간 인터페이스를 생성
        // 행동에 따라서 다형성을 만들기 위해서 인터페이스를 토대로 클래스 생성
        // Heart 클래스 뿐만 아니라 무늬 별로 트럼프 카드 및 트럼프 인간의 책임을 갖고 있는 객체를 만들 수 있다.

        heart.kneelDown(); // 트럼프 카드에서만 있는 행동

        heartKing.flapWalk(); // 트럼프 인간에서 추가된 행동
        heartKing.kneelDown();
        heartKing.turnOver();

        heartPattern.love(); // 하트 클래스에서 추가된 행동

        // 트럼프인간은 트럼프 카드의 행동을 할 수 있지만 , 트럼프 카드는 트럼프 인간만하는 행동을 하지 못한다.
        // 일반화 ( 트럼프 카드 ) / 특수화 ( 트럼프 인간 ) -> Heart 클래스에서 기능을 추가해서 특수화를 더욱 진행할 수 있다.
    }
}
