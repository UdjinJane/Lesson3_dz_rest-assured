package lesson3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class PrintProperties{
    public static void main(String[] args) throws IOException {

        Properties prop = new Properties();
        InputStream configFile;

        configFile = new FileInputStream("src/main/resources/my.properties");

        int i;

        while((i=configFile.read())!= -1){

            System.out.print((char)i);
        }
    }

}