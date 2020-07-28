package selfpacked;

import com.alibaba.fastjson.JSON;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTask {
    public static void main(String[] args) {
        JSON.parse("dasdasda");
        Timer timer = new Timer();
        //timer.schedule(new TimeTest(), 2000, 3000);
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                System.out.println("time");
            }
        }, 2000, 3000);
    }
}
