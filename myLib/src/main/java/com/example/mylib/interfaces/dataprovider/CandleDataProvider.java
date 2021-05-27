package  com.example.mylib.interfaces.dataprovider;

import  com.example.mylib.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
