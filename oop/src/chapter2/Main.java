package chapter2;

public class Main {

    public static void main(String[] args) {
        Alice alice = new AliceA(170,Location.OUTSIDE);
        Beverage coke = new Coke(200);
        // Alice , Beverage 행동에 집중 , 상태와 구체적인 행동방법을 알지 못하도록 캡슐화

        for (int i = 0; i < 17; i++) {
            coke.showAmount();
            alice.drink(coke);
        }
        alice.showLocation();
        alice.passDoor();
        alice.showLocation();

    }
}
