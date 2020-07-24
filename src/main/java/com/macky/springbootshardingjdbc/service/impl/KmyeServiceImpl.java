package com.macky.springbootshardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macky.springbootshardingjdbc.entity.Voucher;
import com.macky.springbootshardingjdbc.entity.ZtkmKmye;
import com.macky.springbootshardingjdbc.mapper.KmyeMapper;
import com.macky.springbootshardingjdbc.mapper.VoucherMapper;
import com.macky.springbootshardingjdbc.service.KmyeService;
import com.macky.springbootshardingjdbc.service.VoucherService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/07/22
 * Time: 10:55 上午
 */
@Service
public class KmyeServiceImpl extends ServiceImpl<KmyeMapper, ZtkmKmye> implements KmyeService {



    @Override
    public boolean save(ZtkmKmye ztkmKmye) {
        return super.save(ztkmKmye);
    }
}
