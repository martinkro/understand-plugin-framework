package info.loveai.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ShoppingHandler implements InvocationHandler {

    Shopping mBase;

    public ShoppingHandler(Shopping base){
        mBase = base;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        if (methodName.equals("doShopping")){
            Long money = (Long)args[0];
            long realCost = money * 3 / 4;

            System.out.println("[ShoppingHandler]money: " + money);
            Object[] things = (Object[])method.invoke(mBase,realCost);

            if (things != null && things.length > 1){
                things[0] = "dynamic proxy";
            }

            return things;
        }
        return null;
    }
}
