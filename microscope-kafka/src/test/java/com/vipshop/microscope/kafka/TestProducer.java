package com.vipshop.microscope.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestProducer extends Thread {
    private Producer<Integer, String> producer = null;
    private List<String> arrayList = new ArrayList<String>(1024 * 1024);

    public TestProducer(String connectStr) {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("zk.connect", connectStr);
        props.put("zk.connectiontimeout.ms", "1000000");
        props.put("metadata.broker.list", "192.168.201.234:9092");
        producer = new Producer<Integer, String>(new ProducerConfig(props));
    }

    /**
     * @param args 0:zkStr 1:fileName 2:interval 3:topic
     * @throws java.io.IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String zkStr = "192.168.201.234:2181";
        if (args.length > 0) {
            zkStr = args[0];
        }
        TestProducer tp = new TestProducer(zkStr);


        //tp.send("mobile");


        String fileName = "C:\\test.txt";
        //String fileName = "C:\\Users\\Administrator\\Desktop\\site_speed.log";

        if (args.length > 1) {
            fileName = args[1];
        }

        int interval = 1;

        if (args.length > 2) {
            interval = Integer.valueOf(args[2]);
        }

        String topic = "mobile";
        if (args.length > 3) {
            topic = args[3];
        }

        tp.sendFromFileInfinitly(fileName, interval, topic);
    }

    public void send(String topic) {
        for (int i = 0; i < 100; i++) {
            producer.send(new KeyedMessage<Integer, String>(topic, i + ""));
        }
        producer.close();
    }

    public void sendFromFile(String file, long intervalMillis, String topic) throws IOException, InterruptedException {
        BufferedReader r = new BufferedReader(new FileReader(file));
        String line = null;
        //读文件
        while ((line = r.readLine()) != null) {
            arrayList.add(line);
        }
        r.close();

        int index = 0;
        while (true) {//
            if (intervalMillis > 0) {
                Thread.sleep(intervalMillis);
            }
            if (index >= arrayList.size()) {
                index = 0;
            }
            line = arrayList.get(index++);
            producer.send(new KeyedMessage<Integer, String>(topic, line));
        }
    }

    public void sendFromFileInfinitly(String file, long intervalMillis, String topic) throws IOException, InterruptedException {
        while (true) {
            sendFromFile(file, intervalMillis, topic);
        }
    }

}
