package chapter4;

public class Rabbit1 implements WhiteRabbit{
    @Override
    public Witness callWitness(String Command) {
        return new Hatter();
    }
}

