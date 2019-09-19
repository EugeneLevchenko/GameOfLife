package com.eugeneLevchenko.gameOfLife;

import java.io.IOException;
import java.util.Arrays;

import static com.eugeneLevchenko.gameOfLife.SimulateLife.COLS;
import static com.eugeneLevchenko.gameOfLife.SimulateLife.INPUT_ARR;
import static com.eugeneLevchenko.gameOfLife.SimulateLife.ROWS;

public class App
{
    public static void main( String[] args ) throws IOException {

        SimulateLife sl=new SimulateLife();
        sl.initInputData();

        // System.out.println(Arrays.deepToString(SimulateLife.INPUT_ARR));


    }
}


