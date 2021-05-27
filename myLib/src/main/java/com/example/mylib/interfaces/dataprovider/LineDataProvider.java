package  com.example.mylib.interfaces.dataprovider;

import  com.example.mylib.components.YAxis;
import  com.example.mylib.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
