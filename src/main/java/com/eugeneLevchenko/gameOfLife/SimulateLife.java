package com.eugeneLevchenko.gameOfLife;

import java.io.*;

public class SimulateLife {

    private static final String PATH_INPUT_FILE="src\\main\\input\\input.txt";
    private static final String PATH_OUTPUT_FILE="src\\main\\output\\output.txt";

    private static int NUMBER_OF_ITERATIONS;
    private static int COLS;
    private static int ROWS;
    private static String[][] INPUT_ARR;
    private static String[][] OUTPUT_ARR;

    public void startSimulate()
    {
        initInputData();
        OUTPUT_ARR=new String[COLS][ROWS];
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++)
            {
                OUTPUT_ARR[i][j]=INPUT_ARR[i][j];
            }
        }
        for (int z=0;z<NUMBER_OF_ITERATIONS;z++)
        {
            for (int i = 0; i < COLS; i++) {
                for (int j = 0; j < ROWS; j++) {

                    int numOfNeighbors=getNumOfNeighbors(INPUT_ARR,i,j);

                    if (INPUT_ARR[i][j].equals("X"))
                    {
                        if (numOfNeighbors<2 || numOfNeighbors>3)
                        {
                            OUTPUT_ARR[i][j]="O";
                        }
                    }

                    if (INPUT_ARR[i][j].equals("O"))
                    {
                        if (numOfNeighbors==3)
                        {
                            OUTPUT_ARR[i][j]="X";
                        }
                    }
                }
            }
//here
            for (int i = 0; i < COLS; i++) {
                for (int j = 0; j < ROWS; j++)
                {
                    INPUT_ARR[i][j]=OUTPUT_ARR[i][j];
                }
            }
printEachNewGenerationToConsole(OUTPUT_ARR,z);
        }
        writeResultToOutputFile(OUTPUT_ARR);
    }

    public void printEachNewGenerationToConsole(String[][] arr,int z)
    {
        z+=1;
        System.out.println("After "+z+" iteration:");
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++)
            {
                System.out.print(arr[i][j]);
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


    public void writeResultToOutputFile(String[][] arr)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                builder.append(arr[i][j]+"");
                if(j < arr.length - 1)
                    builder.append(" ");
            }
            if (i!=INPUT_ARR.length-1)
                builder.append("\n");
        }
        writeToFile(builder,PATH_OUTPUT_FILE);
    }

    public void writeToFile(StringBuilder builder,String filePath)
    {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumOfNeighbors(String[][] tempArr,int i,int j)
    {
        int countOfNeighbors=0;
        //calculate number of neighbors

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

    public void initInputData()
    {
        int count=0;
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_INPUT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                line=line.trim();
                if (count==0)
                {
                    NUMBER_OF_ITERATIONS = Integer.parseInt(line);
                }
                if (count==1)
                {
                    String[] sizesInputArr = line.split(" ");
                    COLS =Integer.parseInt(sizesInputArr[0]);
                    ROWS =Integer.parseInt(sizesInputArr[1]);
                    INPUT_ARR =new String[COLS][ROWS];
                }
                if (count>1)
                {
                    String[] oneLineOfInputArr = line.split(" ");
                    for (int i=0;i < oneLineOfInputArr.length;i++)
                    {
                        INPUT_ARR[count-2][i]=oneLineOfInputArr[i];
                    }
                }
                count++;
            }
            //System.out.println(Arrays.deepToString(INPUT_ARR));
        } catch (IOException e) {
            System.out.println("You entered wrong path of file,please check your input file's path");
            e.printStackTrace();
        }
        if (count<3)
        {
            generateArrRandomlyAndWriteToFile();
        }
    }

    public void generateArrRandomlyAndWriteToFile()  {

        StringBuilder builder = new StringBuilder();
        builder.append(NUMBER_OF_ITERATIONS+"");
        builder.append("\n");
        builder.append(COLS+" "+ROWS);
        builder.append("\n");

        for (int i = 0; i < INPUT_ARR.length; i++) {
            for (int j = 0; j < INPUT_ARR[i].length; j++) {

                int resOfRandom=(int)(Math.random()*2);
                if (resOfRandom==0)
                {
                    INPUT_ARR[i][j]="O";
                }
                else {
                    INPUT_ARR[i][j]="X";
                }
                builder.append(INPUT_ARR[i][j]+"");
                if(j < INPUT_ARR.length - 1)
                    builder.append(" ");
            }
            if (i!=INPUT_ARR.length-1)
                builder.append("\n");
        }
        writeToFile(builder,PATH_INPUT_FILE);
    }
}