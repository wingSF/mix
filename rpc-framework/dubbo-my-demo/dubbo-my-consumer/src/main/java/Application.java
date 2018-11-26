import com.wing.lynne.inter.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");

        context.start();

        DemoService demoService = context.getBean(DemoService.class);

        String result = demoService.sayHello("wing");

        System.out.println(result);


    }
}
