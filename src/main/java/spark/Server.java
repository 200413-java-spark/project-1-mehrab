package spark;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class Server {
    public static void main(String[] args) throws LifecycleException {
        List<String> names = new ArrayList<>(Arrays.asList("Mehrab", "Anthony", "Sutter", "Brandon", "Pejal", "Phuc",
                "Cynthia", "John", "Jeff", "Jeremy", "Christian", "Daniel", "Garrison", "Jacob", "Jay", "Mason"));
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Project 1 Mehrab");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        long count = sparkContext.parallelize(names).count();

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(new File("target/tomcat/").getAbsolutePath());
        tomcat.setPort(8080);
        tomcat.getConnector();
        tomcat.addWebapp("/spark", new File("src/main/resources/").getAbsolutePath());
        tomcat.addServlet("/spark", "HelloServlet", new HelloServlet(names, count)).addMapping("/hello");        
        tomcat.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    sparkContext.close();
                    tomcat.stop();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}