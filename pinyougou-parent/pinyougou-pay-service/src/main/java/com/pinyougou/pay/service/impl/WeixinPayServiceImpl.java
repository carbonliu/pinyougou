package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Value;
import utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/28 14:59
 * @description:
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    
    @Value("${partnerkey}")
    private String partnerkey;

    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        //1、创建参数
        Map<String,String> param = new HashMap();//创建参数
        param.put("appid",appid);//公众账号ID
        param.put("mch_id",partner);//商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body","品优购");//商品描述
        param.put("out_trade_no",out_trade_no);//商户订单号
        param.put("total_fee",total_fee);//金额（分）
        param.put("spbill_create_ip","127.0.0.1");//终端IP
        param.put("notify_url","www.pinyoougou.com");//通知地址
        param.put("trade_type","NATIVE");//交易类型

       
        try {
            //2、生成要发送的xml
            String xmlParam = WXPayUtil.generateSignature(param, partnerkey);
            System.out.println("生成的xml:"+xmlParam);
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3、获得结果
            String xmlResult = httpClient.getContent();
            System.out.println("获得的结果："+xmlResult);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);

            Map<String,String> map=new HashMap();

           //map.put("code_url",resultMap.get("code_url"));//支付地址
            map.put("code_url","https://www.baidu.com");
            map.put("total_fee",total_fee);//总金额
            map.put("out_trade_no",out_trade_no);//订单号
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }

    }

    @Override
    public Map queryPayStatus(String out_trade_no)  {
        Map map=new HashMap();
        map.put("appid",appid);
        map.put("mch_id",partner);
        map.put("out_trade_no",out_trade_no);
        map.put("nonce_str",WXPayUtil.generateNonceStr());
        String url="https://api.mch.weixin.qq.com/pay/orderquery";

        try {
            String xmlParam=WXPayUtil.generateSignedXml(map,partnerkey);
            HttpClient httpClient=new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            String result=httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            System.out.println(map);
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
