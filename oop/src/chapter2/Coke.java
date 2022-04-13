package chapter2;

public class Coke implements Beverage{

    private Integer amount;

    public Coke(Integer amount) {
        this.amount = amount;
    }

    @Override
    public void drinked() {
        if (this.amount > 10) {
            this.amount -= 10;
        } else {
            System.out.println(" 다 마신 음료입니다.");
        }
    }

    @Override
    public void showAmount() {
        if (this.amount > 10) {
            System.out.println("현재 음료의 양은 " + this.amount + "ml 입니다.");
        } else {
            System.out.println(" 다 마신 음료입니다.");
        }
    }
}
