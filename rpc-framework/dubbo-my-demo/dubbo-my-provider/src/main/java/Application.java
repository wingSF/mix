import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.InetAddress;

public class Application {

    public static void main(String[] args) throws IOException {

        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");

        context.start();

        System.in.read();


    }

}
