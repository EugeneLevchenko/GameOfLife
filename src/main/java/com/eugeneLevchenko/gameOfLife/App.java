package com.eugeneLevchenko.gameOfLife;

public class App
{
    public static void main( String[] args ){
        GameOfLife game = new GameOfLife();
        game.startSimulate(true);
    }
}