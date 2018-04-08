package tests;

import ija.ija2017.interfaces.PipeItemInterface;
import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.connection.PipeItem;
import ija.ija2017.items.connection.PortItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xillic00 on 08.04.18.
 */
public class Test02 {

    private PipeItemInterface pipe01;
    private PipeItemInterface pipe02;
    //promenne

    @Before
    public void setUp() throws Exception {
        pipe01 = new PipeItem(new PortItem(PortItemInterface.type.input).getPortItemId(), new PortItem(PortItemInterface.type.output).getPortItemId());
        pipe02 = new PipeItem(new PortItem(PortItemInterface.type.input).getPortItemId(), new PortItem(PortItemInterface.type.output).getPortItemId());
    }

    @Test
    public void test01() {
        Assert.assertNotEquals("Ocekavam nastavene pipeID",null, pipe01.getPipeItemId());
        Assert.assertNotEquals("Ocekavam rozdilna IDs", pipe01.getPipeItemId(), pipe02.getPipeItemId());
    }
}
