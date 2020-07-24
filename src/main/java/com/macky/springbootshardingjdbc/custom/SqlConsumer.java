package com.macky.springbootshardingjdbc.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.macky.springbootshardingjdbc.concurrent.VoucherExecutor;
import com.macky.springbootshardingjdbc.entity.Voucher;
import com.macky.springbootshardingjdbc.entity.ZtkmKmye;
import com.macky.springbootshardingjdbc.service.KmyeService;
import com.macky.springbootshardingjdbc.service.VoucherService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/22
 * Time: 11:13 上午
 */

@Component
public class SqlConsumer {
    @Value("${sql.format}")
    private String voucherFormat;

    @Resource
    private VoucherService voucherService;

    @Resource
    private KmyeService kmyeService;

   private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(topics = "kmye", id = "consumer1", containerFactory = "batchFactory")
    public void listen(List<ConsumerRecord<?, ?>> list) throws Exception{
        List<String> messages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 获取消息
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));
        }
        if (messages.size() > 0) {
//            // 更新索引
            VoucherExecutor.getThreadPool().submit(()-> {
                try {
                    saveToMysql(messages);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void saveToMysql(List<String> messages) throws Exception{
        for (String message : messages) {

            JSONObject result = null;
            try {
                result = JSON.parseObject(message);
            } catch (Exception e) {
                continue;
            }
            // 获取具体数据
            JSONArray valueStr = (JSONArray) result.get("value");
            // 转化为对应格式的json字符串
            JSONObject object = getObject(valueStr);
//            Voucher voucher = transToVoucher(object.toJavaObject(Map.class));
////            Voucher voucher = object.toJavaObject(Voucher.class);
//            voucherService.save(voucher);
            ZtkmKmye ztkmKmye = transToKmye(object.toJavaObject(Map.class));
            kmyeService.save(ztkmKmye);
        }
        System.out.println("-------endTime-----" + new Date().getTime());
        //voucherService.saveBatch(vouchers);
    }

    private ZtkmKmye transToKmye(Map map) throws Exception{
        ZtkmKmye kmye = new ZtkmKmye();
        kmye.setId(Long.parseLong(map.get("id").toString().trim()));
        kmye.setZtdm(Long.parseLong(map.get("ztdm").toString().trim()));
        kmye.setKjnd(Integer.parseInt(map.get("kjnd").toString().trim()));
        kmye.setKjqj(Integer.parseInt(map.get("kjqj").toString().trim()));
        kmye.setZtkmId(Long.parseLong(map.get("ztkmId").toString().trim()));
        kmye.setJf(new BigDecimal(map.get("jf").toString().trim()));
        kmye.setDf(new BigDecimal(map.get("df").toString().trim()));
        kmye.setDelFlag(Integer.parseInt(map.get("delFlag").toString().trim()));
        if(!map.get("createUser").toString().trim().equals("NULL") ){
            kmye.setCreateUser(Long.parseLong(map.get("createUser").toString().trim()));
        }
        if(!map.get("updateUser").toString().trim().equals("NULL") ){
            kmye.setCreateUser(Long.parseLong(map.get("updateUser").toString().trim()));
        }
//        if(!map.get("createTime").toString().trim().equals("NULL") ){
//                String substring = map.get("createTime").toString().trim();
//                String substring2 = map.get("createTime").toString().trim().substring(1, substring.length() - 1);
//                Date parse = dateFormat.parse(substring2);
//                kmye.setCreateTime(parse);
//        }
//        if(!map.get("updateTime").toString().trim().equals("NULL") ){
//                String substring = map.get("updateTime").toString().trim();
//                String substring2 = map.get("updateTime").toString().trim().substring(1, substring.length() - 1);
//                Date parse = dateFormat.parse(substring2);
//                kmye.setCreateTime(parse);
//        }
        return kmye;
    }

    private Voucher transToVoucher(Map map) throws Exception{
          Voucher voucher = new Voucher();
          voucher.setId(Long.parseLong(map.get("id").toString().trim()));
          voucher.setBbh( map.get("bbh").toString().equals("NULL") ? null :Long.parseLong(map.get("bbh").toString().trim()));
          voucher.setFhr( map.get("fhr").toString().equals("NULL") ? null :map.get("fhr").toString().trim());
          voucher.setZtdm(map.get("ztdm").toString().equals("NULL") ? null :Long.parseLong(map.get("ztdm").toString().trim()));
          //voucher.setZzrq(map.get("zzrq").toString().equals("NULL") ? null : dateFormat.parse(map.get("zzrq").toString()));
          voucher.setZt(map.get("zt").toString().equals("NULL") ? null :Integer.parseInt(map.get("zt").toString().trim()) );
          voucher.setKjnd(map.get("kjnd").toString().equals("NULL") ? null :Integer.parseInt(map.get("kjnd").toString().trim()));
          voucher.setKjqj(map.get("kjqj").toString().equals("NULL") ? null :Integer.parseInt(map.get("kjqj").toString().trim()));
          voucher.setLsh(map.get("lsh").toString().equals("NULL") ? null :Integer.parseInt(map.get("lsh").toString().trim()));
          voucher.setZje(map.get("zje").toString().equals("NULL") ? null : new BigDecimal(map.get("zje").toString().trim()));
          voucher.setPzz(map.get("pzz").toString().equals("NULL") ? null :Integer.parseInt(map.get("pzz").toString().trim()));
          voucher.setLy(map.get("ly").toString().equals("NULL") ? null :map.get("ly").toString().trim());
          return voucher;
    }

    private JSONObject getObject(JSONArray message) {
        JSONObject resultObject = new JSONObject();
        String format = voucherFormat;
        if (!format.isEmpty()) {
            JSONObject jsonFormatObject = JSON.parseObject(format);
            for (String key : jsonFormatObject.keySet()) {
                String[] formatValues = jsonFormatObject.getString(key).split(",");
                if (formatValues.length < 2) {
                    resultObject.put(key, message.get(jsonFormatObject.getInteger(key)));
                } else {
                    Object object = message.get(Integer.parseInt(formatValues[0]));
                    if (object == null) {
                        String[] array = {};
                        resultObject.put(key, array);
                    } else {
                        String objectStr = message.get(Integer.parseInt(formatValues[0])).toString();
                        String[] result = objectStr.split(formatValues[1]);
                        resultObject.put(key, result);
                    }
                }
            }
        }
        return resultObject;
    }
}
