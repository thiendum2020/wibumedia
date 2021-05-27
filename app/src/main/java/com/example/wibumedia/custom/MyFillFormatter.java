package com.example.wibumedia.custom;

import  com.example.mylib.formatter.IFillFormatter;
import  com.example.mylib.interfaces.dataprovider.LineDataProvider;
import  com.example.mylib.interfaces.datasets.ILineDataSet;

/**
 * Created by Philipp Jahoda on 12/09/15.
 */
@SuppressWarnings("unused")
public class MyFillFormatter implements IFillFormatter
{

    private float fillPos;

    public MyFillFormatter(float fillPos) {
        this.fillPos = fillPos;
    }

    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        // your logic could be here
        return fillPos;
    }
}
