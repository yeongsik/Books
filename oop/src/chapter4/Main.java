package chapter4;

public class Main {

    public static void main(String[] args) {

        Judge judge = new HeartKing();
        WhiteRabbit whiteRabbit = new Rabbit1();

        judge.startTrial("재판요청합니다.");
        Witness hatter = whiteRabbit.callWitness("증인을 데려와라");
        hatter.testify();

    }
}
