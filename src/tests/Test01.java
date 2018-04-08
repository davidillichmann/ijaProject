/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import java.lang.reflect.Modifier;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.block.SubBlockItem;
import ija.ija2017.items.block.SumBlockItem;
import ija.ija2017.items.connection.PortItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.core.IsEqual.equalTo;


/**
 *
 * @author xfryct00
 */
public class Test01 {

    private PortItem port01;
    private PortItem port02;
    private PortItem port03;
    private SumBlockItem sumBlock1;
    private SubBlockItem subBlock1;

    @Before
    public void setUp() {
        port01 = new PortItem(PortItemInterface.type.input);
        port02 = new PortItem(PortItemInterface.type.output);
        port03 = new PortItem(PortItemInterface.type.input);

        sumBlock1 = new SumBlockItem();
        subBlock1 = new SubBlockItem();

    }

    /**
     * PORT: Zakladni test typu portu
     */
    @Test
    public void test01() {

        Assert.assertEquals("Test typu.", PortItemInterface.type.input, port01.getType());
        Assert.assertNotEquals("Test typu.", PortItemInterface.type.output, port01.getType());
    }

    /**
     * PORT: Zakladni test autoinkrementace ID
     */
    @Test
    public void test02() {
        Assert.assertNotEquals("Test ID.", port01.getPortItemId(), port02.getPortItemId());
    }

    /**
     * SumBlockItem: Zakladni test typu
     */
    @Test
    public void test03() {
        Assert.assertNotEquals("Test typu.", BlockItemInterface.type.DIV, sumBlock1.getType());
    }

    /**
     * SumBlockItem: Test execute()
     */
    @Test
    public void test04() {
        port01.setValue(100);
        port03.setValue(200);
        sumBlock1.addInputPort(port01);
        sumBlock1.addInputPort(port03);
        sumBlock1.execute();

//        Assert.assertEquals("Test execute().", 300, sumBlock1.getOutputPort().getValue());
        Assert.assertThat(300.0, equalTo(sumBlock1.getOutputPort().getValue()));
    }

    /**
     * SumBlockItem: test execute()
     */
    @Test
    public void test05() {
        port01.setValue(50);
        port03.setValue(100);
        subBlock1.addInputPort(port01);
        subBlock1.addInputPort(port03);
        subBlock1.execute();

        Assert.assertThat(-150.0, equalTo(subBlock1.getOutputPort().getValue()));
    }







}
