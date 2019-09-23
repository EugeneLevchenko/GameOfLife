package com.eugeneLevchenko.gameOfLife;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class App
{
    public static void main( String[] args ) throws IOException {
        GameOfLife game = new GameOfLife();
        game.startSimulate(false);
    }
}