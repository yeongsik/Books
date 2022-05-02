package chapter4;

// 재판장 역할
public interface Judge {

    void startTrial(String request);

    String judgement(Testimony testimony);
}
