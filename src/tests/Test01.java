/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import java.lang.reflect.Modifier;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.block.DivBlockItem;
import ija.ija2017.items.block.MulBlockItem;
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
    private MulBlockItem mulBlock1;
    private DivBlockItem divBlock1;

    @Before
    public void setUp() {
        port01 = new PortItem(PortItemInterface.type.input);
        port02 = new PortItem(PortItemInterface.type.output);
        port03 = new PortItem(PortItemInterface.type.input);

        sumBlock1 = new SumBlockItem();
        subBlock1 = new SubBlockItem();
        mulBlock1 = new MulBlockItem();
        divBlock1 = new DivBlockItem();

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
        Assert.assertEquals(300.0, sumBlock1.getOutputPort().getValue(), 0);
    }

    /**
     * SubBlockItem: test execute()
     */
    @Test
    public void test05() {
        port01.setValue(50);
        port03.setValue(100);
        subBlock1.addInputPort(port01);
        subBlock1.addInputPort(port03);
        subBlock1.execute();

        Assert.assertEquals(-50.0, subBlock1.getOutputPort().getValue(), 0);
    }

    /**
     * MulBlockItem: test execute()
     */
    @Test
    public void test06() {
        port01.setValue(21);
        port03.setValue(2);
        mulBlock1.addInputPort(port01);
        mulBlock1.addInputPort(port03);
        mulBlock1.execute();

        Assert.assertEquals(42.0, mulBlock1.getOutputPort().getValue(), 0);
    }

    /**
     * DivBlockItem: test execute()
     */
    @Test
    public void test07() {
        port01.setValue(10);
        port03.setValue(5);
        divBlock1.addInputPort(port01);
        divBlock1.addInputPort(port03);
        divBlock1.execute();

        Assert.assertEquals(2.0, divBlock1.getOutputPort().getValue(), 0);
    }


    /**
     * DivBlockItem: Testuji to, ze bez execute je value 0
     */
    @Test
    public void test08() {
        Assert.assertEquals(0.0, divBlock1.getOutputPort().getValue(), 0);
    }

    /**
     * DivBlockItem: Testuji to, ze prirazeni inputPorts neexistuje
     */
    @Test
    public void test09() {
        Assert.assertEquals(divBlock1.getInputPorts(), null);
    }

    /**
     * DivBlockItem, SumBlockItem: Test rozdilnosti typu
     */
    @Test
    public void test10() {
        Assert.assertNotEquals(sumBlock1.getType(), divBlock1.getType());
    }

    /**
     * Test jednoducheho programu. Scitacka 3 vstupu - 1 zadany, 2 z dalsich bloku
     */
    @Test
    public void test11() {
        port01.setValue(10);
        port02.setValue(20);
        port03.setValue(30);

        sumBlock1.addInputPort(port01);
        sumBlock1.addInputPort(port02);
        sumBlock1.execute();
        Assert.assertEquals(30.0, sumBlock1.getOutputPort().getValue(), 0);

        subBlock1.addInputPort(port01);
        subBlock1.addInputPort(port02);
        subBlock1.addInputPort(port03);
        subBlock1.execute();
        Assert.assertEquals(-40.0, subBlock1.getOutputPort().getValue(), 0);

        mulBlock1.addInputPort(sumBlock1.getOutputPort());
        mulBlock1.addInputPort(subBlock1.getOutputPort());
        mulBlock1.execute();
        Assert.assertEquals(-1200.0, mulBlock1.getOutputPort().getValue(), 0);

    }





}
