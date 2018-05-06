//package tests;
//
//import ija.ija2017.controllers.ExecuteController;
//import ija.ija2017.interfaces.BoardItemInterface;
//import ija.ija2017.interfaces.ExecuteControllerInterface;
//import ija.ija2017.interfaces.PipeItemInterface;
//import ija.ija2017.interfaces.PortItemInterface;
//import ija.ija2017.items.block.MulBlockItem;
//import ija.ija2017.items.block.SubBlockItem;
//import ija.ija2017.items.block.SumBlockItem;
//import ija.ija2017.items.board.BoardItem;
//import ija.ija2017.items.connection.PipeItem;
//import ija.ija2017.items.connection.PortItem;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//
///**
// * Created by xillic00 on 08.04.18.
// *
// */
//public class Test02 {
//
//    private PipeItemInterface pipe01;
//    private PipeItemInterface pipe02;
//    private BoardItemInterface board;
////    private ExecuteControllerInterface controller;
//
//    private PortItem port01;
//    private PortItem port02;
//    private PortItem port03;
//    private SumBlockItem sumBlock1;
//    private SubBlockItem subBlock1;
//    private MulBlockItem mulBlock1;
//
//    @Before
//    public void setUp() throws Exception {
//        pipe01 = new PipeItem(new PortItem(PortItemInterface.type.input).getPortItemId(), new PortItem(PortItemInterface.type.output).getPortItemId());
//        pipe02 = new PipeItem(new PortItem(PortItemInterface.type.input).getPortItemId(), new PortItem(PortItemInterface.type.output).getPortItemId());
//        board = new BoardItem();
//        board.setName("Board 1");
//        port01 = new PortItem(PortItemInterface.type.input);
//        port02 = new PortItem(PortItemInterface.type.output);
//        port03 = new PortItem(PortItemInterface.type.input);
//
//        sumBlock1 = new SumBlockItem();
//        subBlock1 = new SubBlockItem();
//        mulBlock1 = new MulBlockItem();
//    }
//
//    @Test
//    public void test01() {
//        Assert.assertNotEquals("Ocekavam nastavene pipeID",null, pipe01.getPipeItemId());
//        Assert.assertNotEquals("Ocekavam rozdilna IDs", pipe01.getPipeItemId(), pipe02.getPipeItemId());
//    }
//
//    @Test
//    public void test02() {
//        Assert.assertNotEquals("Ocekavam nastavene jmeno",null, board.getName());
//        Assert.assertEquals("Nazev boardu neni stejny", "Board 1", board.getName());
//        board.setName("New name");
//        Assert.assertEquals("Nazev boardu neni stejny", "New name", board.getName());
//        boolean aux = board.setName("");
//        Assert.assertFalse("Nazev byl zmenen na prazdny string", aux);
//        Assert.assertEquals("Nazev boardu musi zustat nezmenen", "New name", board.getName());
//
//    }
//
//    /**
//     * Test jednoducheho programu
//     */
//    @Test
//    public void test03() {
//        port01.setValue(10);
//        port02.setValue(20);
//        port03.setValue(30);
//
//        sumBlock1.addInputPort(port01);
//        sumBlock1.addInputPort(port02);
//
//        subBlock1.addInputPort(port01);
//        subBlock1.addInputPort(port02);
//        subBlock1.addInputPort(port03);
//
//        mulBlock1.addInputPort(sumBlock1.getOutputPort());
//        mulBlock1.addInputPort(subBlock1.getOutputPort());
//
//        board.addBlockItem(sumBlock1);
//        board.addBlockItem(subBlock1);
//        board.addBlockItem(mulBlock1);
////        ExecuteControllerInterface controller = new ExecuteController(board);
//
////        Assert.assertEquals("Vysledek je spatne", -1200.0, controller.solveWholeBoard(), 0);
//    }
//
//    @Test
//    public void test04() {
////        Assert.assertTrue("Board nebyl ulozen", board.saveItem());
////        Assert.assertTrue("Board nebyl nacten", board.loadItem());
//    }
//}
