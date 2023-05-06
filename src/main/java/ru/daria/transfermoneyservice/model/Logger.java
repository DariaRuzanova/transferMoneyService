package ru.daria.transfermoneyservice.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Logger {

    private final AtomicInteger numberMsg = new AtomicInteger(0);

    public Logger() {

    }
    public void getLog(String msg){
        Date time  = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (FileOutputStream fos = new FileOutputStream("logFile.txt",true)){
            String str = numberMsg.getAndIncrement()+" "+formatter.format(time)+" "+msg+"\n";
            byte[]bytes = str.getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}