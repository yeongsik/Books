package chapter1;

import chapter1.Customer.Barista;

public class Customer {

    public static void main(String[] args) {
        Order coffeeOrder = new Order(Menu.AMERICANO, Bean.BASIC);
        Casher casher = new MrLee();
        casher.receiveOrder(coffeeOrder);
        Coffee coffee = casher.returnCoffee();

    }

    interface Barista {
         Coffee makeCoffee(Order order);

    }
    interface Casher {
        void receiveOrder(Order order);

        Coffee returnCoffee();
    }

}
class MrKim implements Barista {

    @Override
    public Coffee makeCoffee(Order order) {
        System.out.println("주문받은 " + order.getMenu() + " 만들겠습니다.");
        Coffee coffee;
        switch (order.getMenu()) {
            case AMERICANO:
                coffee = new Coffee(order.getBean(), 500, 0);
                break;
            case LATTE:
                coffee = new Coffee(order.getBean(), 300, 200);
                break;
            case ESPRESSO:
                coffee = new Coffee(order.getBean(), 0, 0);
                break;
            default:
                throw new IllegalArgumentException("저희 매장에 없는 메뉴입니다." + order.getMenu());
        }
        return coffee;
    }
}

class MrLee implements Customer.Casher {

    private Order order;
    @Override
    public void receiveOrder(Order order) {
        this.order = order;
        System.out.println("주문받았습니다.");
    }

    @Override
    public Coffee returnCoffee() {
        MrKim barista = new MrKim();
        Coffee coffee = barista.makeCoffee(this.order);
        System.out.println("주문하신 "+this.order.getMenu()+" 나왔습니다.");
        return coffee;
    }
}


