package com.eugeneLevchenko.gameOfLife;

import mockit.Deencapsulation;
import mockit.Tested;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameOfLifeTest {

    @Tested
    private GameOfLife gameOfLife;

    String[][] tempArr = new String[][]{
            {"O", "O", "O", "O", "O"},
            {"O", "O", "X", "O", "O"},
            {"O", "O", "O", "X", "O"},
            {"O", "X", "X", "X", "O"},
            {"O", "O", "O", "O", "O"}
    };

    @Test
    public void isCellAlive()
    {
        boolean actual= Deencapsulation.invoke(gameOfLife,"isCellAlive",tempArr,1,2);
        boolean expected=true;
        assertEquals(expected,actual);
    }

    @Test
    public void getNumOfNeighbors() {
        Deencapsulation.setField(gameOfLife, "cols", tempArr.length);
        int actual= Deencapsulation.invoke(gameOfLife,"getNumOfNeighbors",tempArr,2,2);
        int expected=5;
        assertEquals(expected,actual);
    }
}