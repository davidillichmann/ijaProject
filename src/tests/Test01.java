/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import java.lang.reflect.Modifier;

import ija.ija2017.items.connection.PortItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


/**
 *
 * @author koci
 */
public class Test01 {
//
    private PortItem port01;

    @Before
    public void setUp() {
        port01 = new PortItem(0);
    }

    /**
     * Zakladni test implementace.
     */
    @Test
    public void test01() {

        Assert.assertEquals("Test typu.", 0, port01.getType());
        Assert.assertNotEquals("Test typu.", 3, port01.getType());
    }

}
