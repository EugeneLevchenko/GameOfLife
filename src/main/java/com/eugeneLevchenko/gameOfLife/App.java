package com.eugeneLevchenko.gameOfLife;

import java.io.IOException;


public class App
{
    public static void main( String[] args ) throws IOException {
        GameOfLife game = new GameOfLife();
        game.startSimulate(true);
    }
}