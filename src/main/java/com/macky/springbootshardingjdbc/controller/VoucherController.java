package com.macky.springbootshardingjdbc.controller;

import com.macky.springbootshardingjdbc.entity.Book;
import com.macky.springbootshardingjdbc.entity.Voucher;
import com.macky.springbootshardingjdbc.produce.SqlAnalyzeRunner;
import com.macky.springbootshardingjdbc.service.VoucherService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/22
 * Time: 11:52 上午
 */
@RestController
public class VoucherController {

    @Resource
    private SqlAnalyzeRunner sqlAnalyzeRunner;

    @Resource
    private VoucherService voucherService;

    @RequestMapping(value = "/voucher", method = RequestMethod.GET)
    public void voucher(){
        System.out.println("-------startTime-----" + new Date().getTime());
         sqlAnalyzeRunner.analyze();
    }

    @RequestMapping(value = "/voucher",method = RequestMethod.POST)
    public Boolean saveItem(@RequestBody Voucher voucher){
        return voucherService.save(voucher);
    }
}
