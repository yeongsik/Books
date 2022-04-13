package chapter2;

public class AliceA implements Alice{

    private Integer height;
    private Location location;

    public AliceA(Integer height, Location location) {
        this.height = height;
        this.location = location;
    }

    @Override
    public void drink(Beverage beverage) {
        beverage.drinked();
        if (this.height < 10) {
            System.out.println("현재 키가 10cm 미만입니다.");
        } else {
            this.height -= 10;
        }
    }

    @Override
    public void showLocation() {
        System.out.println("현재 위치는 " + location + " 입니다.");
    }

    @Override
    public void passDoor() {
        if (this.height < 40) {
            this.location = Location.INSIDE;
        } else {
            System.out.println("문을 통과할 수 없는 키입니다.");
        }
    }
}
