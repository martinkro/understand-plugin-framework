package info.loveai.proxy;

public class ShoppingImpl implements Shopping {
    @Override
    public Object[] doShopping(long money){
        System.out.println("[ShoppingImpl]doShopping");
        System.out.println("[ShoppingImpl]money:" + money);
        return new Object[]{"shoes","snack","clothes"};
    }
}
