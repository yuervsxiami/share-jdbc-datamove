package com.macky.springbootshardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 科目余额信息
 * created by chengchao on 2019-09-23 16:21:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ztkm_kmye")
public class ZtkmKmye implements Serializable {

    private static final long serialVersionUID = 8853529220151095140L;

    /**
     * ID
     */
    private Long id;

    /**
     * 账套代码
     */
    private Long ztdm;

    /**
     * 会计年度
     */
    private Integer kjnd;

    /**
     * 会计期间
     */
    private Integer kjqj;

    /**
     * 账套科目ID
     */
    private Long ztkmId;

    /**
     * 借方
     */
    private BigDecimal jf = BigDecimal.ZERO;
    /**
     * 贷方
     */
    private BigDecimal df = BigDecimal.ZERO;

    /**
     * 删除标记: 1-删除 0-正常
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private Long createUser;

    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    private Date updateTime;
}
