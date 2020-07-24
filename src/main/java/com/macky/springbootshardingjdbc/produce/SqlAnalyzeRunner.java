package com.macky.springbootshardingjdbc.produce;

import com.alibaba.fastjson.JSON;
import com.macky.springbootshardingjdbc.concurrent.VoucherExecutor;
import com.macky.springbootshardingjdbc.entity.SqlDto;
import com.macky.springbootshardingjdbc.entity.Voucher;
import com.macky.springbootshardingjdbc.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/22
 * Time: 11:16 上午
 */

@Component
public class SqlAnalyzeRunner {


    // kafka话题
    @Value("${kafka.topic}")
    private String topic;

    // kafka分区
    @Value("${kafka.partNum}")
    private int partNum;

    // Kafka备份数
    @Value("${kafka.repeatNum}")
    private short repeatNum;

    // kafka地址
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaHost;

    @Autowired
    KafkaSender kafkaSender;

    @Value("${sql.filePath}")
    private String filePath;

    public void analyze(){
        // 创建topic
        kafkaSender.createTopic(kafkaHost, topic, partNum, repeatNum);

        //获取当前文档下面的所有文件
        List<File> files = getAllFileName(filePath);
        for(File file : files) {
            sendMesToKafka(topic, file);
        }
    }

    /**
     * 解析文件，发送消息
     * @param topic
     * @param file
     */
    private void sendMesToKafka(String topic, File file) {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                String finalS = s;
                VoucherExecutor.getThreadPool().submit(()->{
                    String kmyeStr = finalS.substring(finalS.indexOf("VALUES (") + 8, finalS.indexOf(");"));
                    String[] kmyes = kmyeStr.split(",");
                    //此处截取根据sql导出格式'
                    String msg = JSON.toJSONString(new SqlDto("kmye", kmyes));
                    kafkaSender.send(topic,msg);
                });
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private List<File> getAllFileName(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        List<File> result = fileList.stream().filter(s -> s.getName().startsWith("file")).collect(Collectors.toList());
        return result;
    }
}
