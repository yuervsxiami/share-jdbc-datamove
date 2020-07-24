package com.macky.springbootshardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macky.springbootshardingjdbc.entity.Book;
import com.macky.springbootshardingjdbc.entity.Voucher;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/22
 * Time: 10:53 上午
 */
public interface VoucherService extends IService<Voucher> {

    boolean save(Voucher voucher);
}
