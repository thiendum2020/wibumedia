package  com.example.mylib.interfaces.dataprovider;

import  com.example.mylib.components.YAxis.AxisDependency;
import  com.example.mylib.data.BarLineScatterCandleBubbleData;
import  com.example.mylib.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
