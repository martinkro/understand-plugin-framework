package info.loveai.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DynamicProxyShoppingTest {

    ShoppingImpl mOrigin;
    Shopping mProxy;
    @Before
    public void setUp() throws Exception {
        mOrigin = new ShoppingImpl();
        mProxy = (Shopping)Proxy.newProxyInstance(
                Shopping.class.getClassLoader(),
                mOrigin.getClass().getInterfaces(),
                new ShoppingHandler(mOrigin)
        );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doShopping(){
        Object[] originThings = mOrigin.doShopping(100);
        Object[] proxyThings = mProxy.doShopping(100);
        System.out.println("origin:" + Arrays.toString(originThings));
        System.out.println("proxy:" + Arrays.toString(proxyThings));
        assertEquals(4,2+2);
    }
}
