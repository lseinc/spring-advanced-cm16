package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoUI {
	
	@Value("${spring.application.name}:${spring.application.instance_id:${random.value}}")
	private String instanceId;

    public static void main(String[] args) {
        SpringApplication.run(DemoUI.class, args);
    }
    
    @RequestMapping(value="/hello")
    public String hello() {
    	System.out.println("+++ ["+instanceId+"] received /hello request!");
    	return "Hello World from Java !";
    }
}
