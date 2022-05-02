package chapter4;

public class Hatter implements Witness{

    private Testimony testimony;

    @Override
    public Testimony testify() {
        return testimony;
    }
}
