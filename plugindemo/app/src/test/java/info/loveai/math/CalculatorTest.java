package info.loveai.math;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator mCalculator;
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sum() {
        assertEquals(6d, mCalculator.sum(1d, 5d), 0);
    }

    @Test
    public void substract() {
        assertEquals(1d, mCalculator.substract(5d, 4d), 0);
    }

    @Test
    public void divide() {
        assertEquals(4d, mCalculator.divide(20d, 5d), 0);
    }

    @Test
    public void multiply() {
        assertEquals(10d, mCalculator.multiply(2d, 5d), 0);
    }
}