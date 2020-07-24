package com.macky.springbootshardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macky.springbootshardingjdbc.entity.Voucher;
import com.macky.springbootshardingjdbc.entity.ZtkmKmye;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/23
 * Time: 4:17 下午
 */
public interface KmyeService extends IService<ZtkmKmye> {

    boolean save(ZtkmKmye ztkmKmye);
}
