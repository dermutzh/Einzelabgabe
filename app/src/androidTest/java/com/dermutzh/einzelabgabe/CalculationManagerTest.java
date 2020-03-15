package com.dermutzh.einzelabgabe;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculationManagerTest {

    char [] input;
    CalculationManager calcManager;

    @Before
    public void setUp() throws Exception {
        this.input = "9876543210".toCharArray();
        this.calcManager = new CalculationManager();
    }

    @After
    public void tearDown() throws Exception {
        this.calcManager = null;
        this.input = null;
    }

    @Test
    public void performCalculation() {
        String expected = "[0, 4, 6, 8, 9]";
        String calculated = calcManager.performCalculation(input);

        Assert.assertEquals(expected, calculated);
    }
}