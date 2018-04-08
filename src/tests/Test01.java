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
 * @author koci
 */
public class Test01 {

    private PortItem port01;

    @Before
    public void setUp() {
        port01 = new PortItem(PortItemInterface.type.input);
    }

    /**
     * Zakladni test implementace.
     */
    @Test
    public void test01() {

        Assert.assertEquals("Test typu.", PortItemInterface.type.input, port01.getType());
        Assert.assertNotEquals("Test typu.", PortItemInterface.type.output, port01.getType());
    }

}
