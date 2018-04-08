/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import java.lang.reflect.Modifier;

import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.connection.PortItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


/**
 *
 * @author xfryct00
 */
public class Test01 {

    private PortItem port01;
    private PortItem port02;

    @Before
    public void setUp() {
        port01 = new PortItem(PortItemInterface.type.input);
        port02 = new PortItem(PortItemInterface.type.output);
    }

    /**
     * Zakladni test typu portu
     */
    @Test
    public void test01() {

        Assert.assertEquals("Test typu.", PortItemInterface.type.input, port01.getType());
        Assert.assertNotEquals("Test typu.", PortItemInterface.type.output, port01.getType());
    }

    /**
     * Zakladni test autoinkrementace ID
     */
    @Test
    public void test02() {
        Assert.assertNotEquals("Test ID.", port01.getPortItemId(), port02.getPortItemId());
    }

}
