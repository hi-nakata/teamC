package teamC;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TimerSystem implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		//Tomcat起動時処理

		Timer timer = new Timer(); // 今回追加する処理
        TimerTask task = new TimerTask() {

            public void run() {
                // 定期的に実行したい処理
            	
            	

            }
        };
        timer.scheduleAtFixedRate(task,1000,3000); // 今回追加する処理



	}

	public void contextDestroyed(ServletContextEvent event) {
		//Tomcatシャットダウン時処理
	}




}
