package com.pinyougou.pay.service;

import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/28 14:55
 * @description: 微信支付接口
 */
public interface WeixinPayService {

    /**
     *  生成支付二维码
     * @param out_trade_no 订单号
     * @param total_fee 金额（分）
     * @return
     */
    public Map createNative(String out_trade_no,String total_fee);

    public Map queryPayStatus(String out_trade_no) throws Exception;
}
