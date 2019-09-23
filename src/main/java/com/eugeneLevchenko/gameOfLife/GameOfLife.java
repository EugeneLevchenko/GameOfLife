package com.eugeneLevchenko.gameOfLife;

import java.io.*;

public class GameOfLife {

    private static final String PATH_INPUT_FILE="src\\main\\input\\input.txt";
    private static final String PATH_OUTPUT_FILE="src\\main\\output\\output.txt";

    private static int NUMBER_OF_ITERATIONS;
    private static int COLS;
    private static int ROWS;
    private static String[][] INPUT_ARR;

    private String[][] copyArrFromTo(String[][] fromArr,String[][] toArr)
    {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++)
            {
                toArr[i][j]=fromArr[i][j];
            }
        }
        return toArr;
    }

    private void changeCellStateAccordingToRules(String[][] inputArr,String[][] outputArr,int i,int j)
    {
        int numOfNeighbors=getNumOfNeighbors(inputArr,i,j);
        boolean isCellAlive=checkIsCellAlive(inputArr,i,j);
        if (isCellAlive)
        {
            if (numOfNeighbors<2 || numOfNeighbors>3) //rule №1
            {
                outputArr[i][j]="O";
            }
        }
        else {
            if (numOfNeighbors==3) //rule №2
            {
                outputArr[i][j]="X";
            }
        }
    }

    private boolean checkIsCellAlive(String[][] inputArr,int i,int j) {
        if (inputArr[i][j].equals("X")) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void startSimulate(boolean enablePrintToConsole)
    {
        initInputData();

        String[][] outputArr=new String[COLS][ROWS];
        outputArr=copyArrFromTo(INPUT_ARR,outputArr);

        for (int z=0;z<NUMBER_OF_ITERATIONS;z++)
        {
            for (int i = 0; i < COLS; i++) {
                for (int j = 0; j < ROWS; j++) {
                   changeCellStateAccordingToRules(INPUT_ARR,outputArr,i,j);
                }
            }
            INPUT_ARR=copyArrFromTo(outputArr,INPUT_ARR);
            if (enablePrintToConsole)
            {
                printEachNewGenerationToConsole(outputArr,z);
            }
        }
        writeResultToOutputFile(outputArr);
    }

    private void printEachNewGenerationToConsole(String[][] arr,int z)
    {
        z+=1;
        System.out.println("After "+z+" iteration:");
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("------------");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void writeResultToOutputFile(String[][] arr)
    {
        StringBuilder builder = new StringBuilder();
        int arrLength=arr.length-1;

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                createBuilder(builder,arrLength,i,j);
            }
            if (i!=arrLength)
                builder.append("\n");
        }
        writeToFile(builder,PATH_OUTPUT_FILE);
    }

    private void writeToFile(StringBuilder builder,String filePath)
    {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNumOfNeighbors(String[][] tempArr,int i,int j)
    {
        //calculate number of neighbors
        int countOfNeighbors=0;

        //check left neighbor
        if (j!=0 && tempArr[i][j-1].equals("X"))
        {
            countOfNeighbors++;
        }

        //check upper left neighbor
        if ( (i!=0&&j!=0) && tempArr[i-1][j-1].equals("X"))
        {
            countOfNeighbors++;
        }

        //check up neighbor
        if ( i!=0 && tempArr[i-1][j].equals("X"))
        {
            countOfNeighbors++;
        }

        //check upper right neighbor
        if ( (i != 0 && j!=ROWS-1) && tempArr[i-1][j+1].equals("X"))
        {
            countOfNeighbors++;
        }

        //check right neighbor
        if (j!=ROWS-1 && tempArr[i][j+1].equals("X"))
        {
            countOfNeighbors++;
        }

        //check low right neighbor
        if ((j!=ROWS-1&&i!=COLS-1) && tempArr[i+1][j+1].equals("X"))
        {
            countOfNeighbors++;
        }

        //check low  neighbor
        if ( i!=COLS-1 && tempArr[i+1][j].equals("X"))
        {
            countOfNeighbors++;
        }

        //check low left neighbor
        if ( (i!=COLS-1&&j!=0) && tempArr[i+1][j-1].equals("X"))
        {
            countOfNeighbors++;
        }

        return countOfNeighbors;
    }

    private void initInputData()
    {
        int countLinesInFile=0;
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_INPUT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                line=line.trim();
                String[] sizesInputArr = line.split(" ");
                int lengthSizesInputArr=sizesInputArr.length;
                switch (countLinesInFile) {
                    case 0://process array's size
                        if (lengthSizesInputArr==2) // data is correct
                        {
                            COLS =Integer.parseInt(sizesInputArr[0]);
                            ROWS =Integer.parseInt(sizesInputArr[1]);
                            INPUT_ARR =new String[COLS][ROWS];
                        }
                        else {
                            System.out.println("You entered incorrect array's size,please,enter 2 number");
                            System.exit(0);
                        }
                        break;
                    case 1://process number of iterations
                        if (lengthSizesInputArr>1) //data is incorrect
                        {
                            System.out.println("You entered incorrect number of iterations,please,enter only 1 number");
                            System.exit(0);
                        }
                        NUMBER_OF_ITERATIONS = Integer.parseInt(line);
                        break;
                    default://process input Array
                        String[] oneLineOfInputArr = line.split(" ");
                        for (int i=0;i < oneLineOfInputArr.length;i++)
                        {
                            if (!isSymbolOfCellValid(oneLineOfInputArr,i))
                            {
                                System.out.println("You entered incorrect symbol \""+oneLineOfInputArr[i]+"\" of input Array at position: "
                                        +"["+(countLinesInFile-2)+"]["+i+"]");
                                System.out.println("Please,fill Array only with \"X\" or \"O\"");
                                System.exit(0);
                            }
                            INPUT_ARR[countLinesInFile-2][i]=oneLineOfInputArr[i];
                        }
                        break;
                }
                countLinesInFile++;
            }
            if (countLinesInFile==3)//if array is 1D
            {
                System.out.println("You entered one dimension array,please make it 2D");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("You entered wrong path of file,please check your input file's path");
            e.printStackTrace();
        }
        if (countLinesInFile<3)//if there isn't arr in file -> create it
        {
            generateArrRandomlyAndWriteToFile();
        }
    }

    private boolean isSymbolOfCellValid(String[] arr,int i)
    {
        if (arr[i].equals("X")||arr[i].equals("O"))
        {
            return true;
        }
        return false;
    }

    private void generateArrRandomlyAndWriteToFile()  {

        StringBuilder builder = new StringBuilder();
        int arrLength=INPUT_ARR.length-1;

        builder.append(COLS).append(" ").append(ROWS);
        builder.append("\n");
        builder.append(NUMBER_OF_ITERATIONS);
        builder.append("\n");

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                fillArrXOrY(i,j);
                createBuilder(builder,arrLength,i,j);
            }
            if (i!=arrLength) //to exclude an empty line at the end of file
            {
                builder.append("\n");
            }
        }
        writeToFile(builder,PATH_INPUT_FILE);
    }

    private void createBuilder(StringBuilder builder,int arrLength,int i,int j)
    {
        builder.append(INPUT_ARR[i][j]);
        if(j < arrLength)
        {
            builder.append(" ");
        }
    }

    private void fillArrXOrY(int i, int j)
    {
        int resOfRandom=(int)(Math.random()*2);

        if (resOfRandom==0)
        {
            INPUT_ARR[i][j]="O";
        }
        else {
            INPUT_ARR[i][j]="X";
        }
    }
}