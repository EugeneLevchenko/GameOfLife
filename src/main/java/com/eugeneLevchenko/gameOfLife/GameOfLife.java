package com.eugeneLevchenko.gameOfLife;

import java.io.*;

public class GameOfLife {

    private String pathInputFile ="src\\main\\input\\input.txt";
    private String pathOutputFile ="src\\main\\output\\output.txt";

    private int numberOfIterations;
    private int cols;
    private int rows;
    private String[][] inputArr;

    private String[][] copyArrFromTo(String[][] fromArr, String[][] toArr)
    {
        for (int i = 0; i < cols; i++) {
            if (rows >= 0)
            {
                System.arraycopy(fromArr[i], 0, toArr[i], 0, rows);
            }
        }
        return toArr;
    }

    private boolean isCellAlive(String[][] inputArr, int i, int j)
    {
        return inputArr[i][j].equals("X");
    }

    private void changeCellStateAccordingToRules(String[][] inputArr,String[][] outputArr,int i,int j)
    {
        int numOfNeighbors= getNumOfNeighbors(inputArr,i,j);
        boolean isCellAlive= isCellAlive(inputArr,i,j);
        if (isCellAlive)
        {
            if (numOfNeighbors<2 || numOfNeighbors>3)
            {
                outputArr[i][j]="O";
            }
        }
        else {
            if (numOfNeighbors==3)
            {
                outputArr[i][j]="X";
            }
        }
    }

    private int correctIndexWhichOutOfBounds(int index)
    {
        if (index == -1) {
            index = cols - 1;
        }
        return index;
    }

    private int getNumOfNeighbors(String[][] inputArr, int i, int j) {

        int numOfNeighbors = 0;

        int up    = (i - 1) % cols;
        int down  = (i + 1) % cols;
        int left  = (j - 1) % cols;
        int right = (j + 1) % cols;

        up=correctIndexWhichOutOfBounds(up);
        down=correctIndexWhichOutOfBounds(down);
        left=correctIndexWhichOutOfBounds(left);
        right=correctIndexWhichOutOfBounds(right);

        int[][] directions = new int[][]{
                {up, left},   {up, j},    {up, right},
                {i,left},   /*{curCell}*/ {i,right},
                {down, left}, {down, j},  {down, right},
        };
        int x,y;
        for (int c = 0; c < directions.length; c++)
        {
            x=directions[c][0];
            y=directions[c][1];
            if (inputArr[x][y].equals("X"))
            {
                numOfNeighbors++;
            }
        }
        return numOfNeighbors;
    }

    public void startSimulate(boolean enablePrintToConsole)
    {
        initInputData();

        String[][] outputArr=new String[cols][rows];
        outputArr=copyArrFromTo(inputArr,outputArr);

        for (int z = 0; z< numberOfIterations; z++)
        {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    changeCellStateAccordingToRules(inputArr,outputArr,i,j);
                }
            }
            inputArr = copyArrFromTo(outputArr, inputArr);
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
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void writeResultToOutputFile(String[][] arr)
    {
        StringBuilder builder = new StringBuilder();
        int arrLength=arr.length-1;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                createBuilder(builder,arrLength,i,j);
            }
            if (i!=arrLength)
                builder.append("\n");
        }
        writeToFile(builder, pathOutputFile);
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

    private void initSizeAndIterations()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(pathInputFile))) {
            String line;

            for (int i=0;i<2;i++)
            {
                line = br.readLine();
                line=line.trim();
                String[] arrFromLine = line.split(" ");
                if (i==0) //if we parse 1 line
                {
                    processSizes(arrFromLine);
                }
                else { //if we parse 2 line
                    processNumOfIterations(arrFromLine);
                }
            }
        }
        catch (IOException e) {
            System.out.println("You entered wrong path of file,please check your input file's path");
            e.printStackTrace();
        }
    }

    private void processNumOfIterations(String[] arrIterations)
    {
        int  resultParseInt= Integer.parseInt(arrIterations[0]);
        if (arrIterations.length==1 && resultParseInt>0) //data is correct
        {
            numberOfIterations = resultParseInt;
        }
        else {
            System.out.println("You entered incorrect number of iterations,please,enter only 1 integer number");
            System.exit(0);
        }
    }

    private void processSizes(String[] sizesInputArr)
    {
        String firstSize=sizesInputArr[0];
        String secondSize=sizesInputArr[1];
        if (sizesInputArr.length==2 && Integer.parseInt(firstSize)>1 && Integer.parseInt(secondSize)>1) // data is correct
        {
            cols =Integer.parseInt(firstSize);
            rows =Integer.parseInt(secondSize);
            inputArr =new String[cols][rows];
        }
        else {
            System.out.println("You entered incorrect array's size,please,enter 2 positive integers");
            System.exit(0);
        }
    }

    private void initInputArr()
    {
        int countLinesInFile=0;
        try (BufferedReader br = new BufferedReader(new FileReader(pathInputFile))) {
            String line;
            br.readLine(); //ignore 1 line
            br.readLine(); //ignore 2 line
            while ((line = br.readLine()) != null)
            {
                String[] arrFromLine = line.split(" ");
                for (int i=0;i < arrFromLine.length;i++)
                {
                    if (!isSymbolOfCellValid(arrFromLine,i))
                    {
                        System.out.println("You entered wrong symbol: \""
                                +arrFromLine[i]+"\" in input Array"+
                                " at position in Array: ["+(countLinesInFile-2)+"]"
                                +"["+i+"]");
                        System.out.println("Please,fill Array only with \"X\" or \"O\"");
                    }
                    inputArr[countLinesInFile][i]=arrFromLine[i];
                }
                countLinesInFile++;
            }
        }
        catch (IOException e) {
            System.out.println("You entered wrong path of file,please check your input file's path");
            e.printStackTrace();
        }
        if (countLinesInFile==0)
        {
            generateArrRandomlyAndWriteToFile();
        }
    }

    private void initInputData()
    {
        initSizeAndIterations();
        initInputArr();
    }

    private boolean isSymbolOfCellValid(String[] arr,int i)
    {
        return arr[i].equals("X") || arr[i].equals("O");
    }

    private void generateArrRandomlyAndWriteToFile()  {

        StringBuilder builder = new StringBuilder();
        int arrLength= inputArr.length-1;

        builder.append(cols).append(" ").append(rows);
        builder.append("\n");
        builder.append(numberOfIterations);
        builder.append("\n");

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                fillArrXorO(i,j);
                createBuilder(builder,arrLength,i,j);
            }
            if (i!=arrLength) //to exclude an empty line at the end of file
            {
                builder.append("\n");
            }
        }
        writeToFile(builder, pathInputFile);
    }

    private void createBuilder(StringBuilder builder,int arrLength,int i,int j)
    {
        builder.append(inputArr[i][j]);
        if(j < arrLength)
        {
            builder.append(" ");
        }
    }

    private void fillArrXorO(int i, int j)
    {
        int resOfRandom=(int)(Math.random()*2);
        if (resOfRandom==0)
        {
            inputArr[i][j]="O";
        }
        else {
            inputArr[i][j]="X";
        }
    }
}