package  com.example.mylib.interfaces.dataprovider;

import  com.example.mylib.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
