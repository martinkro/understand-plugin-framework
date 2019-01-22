package info.loveai.proxy;

public class StaticProxyShopping implements Shopping {

    Shopping mBase;
    StaticProxyShopping(Shopping base){
        mBase = base;
    }
    @Override
    public Object[] doShopping(long money){
        System.out.println("[StaticProxyShopping]money:" + money);

        long realCost = money * 3 / 4;
        Object[] things = mBase.doShopping(realCost);

        if (things != null && things.length > 1){
            things[0] = "hat";
        }
        return things;

    }
}
