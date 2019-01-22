package info.loveai.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StaticProxyShoppingTest {

    ShoppingImpl mOrigin;
    StaticProxyShopping mProxy;
    @Before
    public void setUp() throws Exception {
        mOrigin = new ShoppingImpl();
        mProxy = new StaticProxyShopping(mOrigin);
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
