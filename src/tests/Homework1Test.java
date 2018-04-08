/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


/**
 *
 * @author koci
 */
public class Homework1Test {
//
    private AbstractItem it10;
    private AbstractItem it20;
    private Item it11;
    private Item it21;

    @Before
    public void setUp() {
        it10 = new Item01("first", 2);
        it20 = new Item02("second", 4);
        it11 = new Item01("first", 2);
        it21 = new Item02("third", 2);
    }

    /**
     * Zakladni test implementace.
     */
    @Test
    public void test01() {

        Assert.assertEquals("Test nazvu.", "first", it10.getName());
        Assert.assertEquals("Test nazvu.", "second", it20.getName());
        Assert.assertEquals("Test poctu portu.", 2, it10.numberOfPorts());
        Assert.assertEquals("Test poctu portu.", 4, it20.numberOfPorts());

        Assert.assertEquals("Test stavu.", 0, it10.getState());
        Assert.assertEquals("Test stavu.", 0, it20.getState());
        it10.execute();
        it20.execute();
        Assert.assertEquals("Test stavu.", 1, it10.getState());
        Assert.assertEquals("Test stavu.", 2, it20.getState());
    }

    /**
     * Rozsireny test implementace.
     */
    @Test
    public void test02() {
        Repository rep01 = new Repository("Repo1");

        rep01.add(it10);
        rep01.add(it20);

        Assert.assertTrue("Vyhledání prvku.", rep01.contains(it11));
        Assert.assertFalse("Vyhledání prvku.", rep01.contains(it21));

    }

    /**
     * Test parametru trid.
     */
    @Test
    public void testClasses() {
        Assert.assertTrue("Trida AbstractItem ma byt abstraktni.",
                Modifier.isAbstract(AbstractItem.class.getModifiers()));
        Assert.assertTrue("Item01 extends AbstractItem", it10 instanceof AbstractItem);
        Assert.assertTrue("Item01 implements Item", it10 instanceof Item);
    }

}
