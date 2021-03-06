package match;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wangjian on 17-10-27.
 */
@MapperScan(basePackages = "match.dao")
@SpringBootApplication
public class App {
    public static void main(String args[]){
        System.loadLibrary("CasiaCenter");
        SpringApplication.run(App.class, args);
    }
}
