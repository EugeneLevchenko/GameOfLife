package com.eugeneLevchenko.gameOfLife;

import java.io.*;

public class SimulateLife {

     static int NUMBER_OF_ITERATIONS;
     static int COLS;
     static int ROWS;
     static String[][] INPUT_ARR;




    public void initInputData()
    {
        int count=0;
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\property\\propertyInputFile.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line=line.trim();
                if (count==0)
                {
                    NUMBER_OF_ITERATIONS= Integer.parseInt(line);
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
            try {
                generateArrRandomlyAndWriteToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateArrRandomlyAndWriteToFile() throws IOException {

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

        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\property\\propertyInputFile.txt"));
        writer.write(builder.toString());
        writer.close();
    }
}
