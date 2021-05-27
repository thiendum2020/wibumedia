package  com.example.mylib.interfaces.dataprovider;

import  com.example.mylib.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
