package ija.ija2017.drag;

import javafx.scene.input.DataFormat;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidillichmann on 02.05.18.
 *
 *
 */
public class DragContainer implements Serializable {

    private final List<Pair<String, Object>> mDataPairs = new ArrayList<Pair<String, Object>>();

    public static final DataFormat AddNode =
            new DataFormat("ija.ija2017.drag.DragBlock.add");

    public static final DataFormat DragNode =
            new DataFormat("ija.ija2017.drag.DraggableNode.drag");

    public static final DataFormat AddLink =
            new DataFormat("ija.ija2017.drag.NodeLink.add");

    public void addData(String key, Object value) {
        mDataPairs.add(new Pair<String, Object>(key, value));
    }

    public <T> T getValue(String key) {

        for (Pair<String, Object> data : mDataPairs) {

            if (data.getKey().equals(key))
                return (T) data.getValue();

        }

        return null;
    }

    public List<Pair<String, Object>> getData() {
        return mDataPairs;
    }
}
