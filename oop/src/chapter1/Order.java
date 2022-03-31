package chapter1;

public class Order {
    private Menu menu;
    private Bean bean;

    public Order(Menu menu, Bean bean) {
        this.menu = menu;
        this.bean = bean;
    }

    public Menu getMenu() {
        return menu;
    }

    public Bean getBean() {
        return bean;
    }
}
