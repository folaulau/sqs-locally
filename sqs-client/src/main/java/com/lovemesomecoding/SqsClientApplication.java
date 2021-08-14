package com.lovemesomecoding;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SqsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            // Display Environmental Useful Variables
            try {
                System.out.println("\n");
                Runtime runtime = Runtime.getRuntime();
                double mb = 1048576;// megabtye to byte
                double gb = 1073741824;// gigabyte to byte
                Environment env = ctx.getEnvironment();
                TimeZone timeZone = TimeZone.getDefault();

                System.out.println("************************ API ***************************");
                System.out.println("** Active Profile: " + Arrays.toString(env.getActiveProfiles()));
                System.out.println("** Port: " + env.getProperty("server.port"));
                System.out.println("** Timezone: " + timeZone.getID());
                System.out.println("** TimeStamp: " + new Date().toInstant().toString());

                System.out.println("** Internal Url: http://localhost:" + env.getProperty("server.port") + env.getProperty("server.servlet.context-path"));
                System.out.println("** External Url: http://" + InetAddress.getLocalHost().getHostAddress() + ":" + env.getProperty("server.port") + env.getProperty("server.servlet.context-path"));

                System.out.println("** Internal Swagger: http://localhost:" + env.getProperty("server.port") + "/swagger-ui.html");
                System.out.println("** External Swagger: http://" + InetAddress.getLocalHost().getHostAddress() + ":" + env.getProperty("server.port") + "/swagger-ui.html");

                System.out.println("************************* Java - JVM *********************************");
                System.out.println("** Number of processors: " + runtime.availableProcessors());
                String processName = ManagementFactory.getRuntimeMXBean().getName();
                System.out.println("** Process ID: " + processName.split("@")[0]);
                System.out.println("** Total memory: " + (runtime.totalMemory() / mb) + " MB = " + (runtime.totalMemory() / gb) + " GB");
                System.out.println("** Max memory: " + (runtime.maxMemory() / mb) + " MB = " + (runtime.maxMemory() / gb) + " GB");
                System.out.println("** Free memory: " + (runtime.freeMemory() / mb) + " MB = " + (runtime.freeMemory() / gb) + " GB");

                System.out.println("*******************************************************************************");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Exception, commandlineRunner -> " + e.getMessage());
            }
            System.out.println("\n");
        };
    }
}
