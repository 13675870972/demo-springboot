package com.example.demospringboot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: fangxp
 * @Date: 2018/12/28 16:29
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateWayReqStatementReqVO {
    private static final long serialVersionUID = -8375825085213123876L;

    /**
     * 主商户id
     */
    private String merchantId;

    /**
     * 帐务日期，格式：2018-12-20
     */
    private Date accountDate;

    /**
     * 交易类型：借款:3，还款:1
     */
    private String tradeType;

}
