package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbPayLog;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.IdWorker;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/28 15:20
 * @description:
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;

    @Reference
    private OrderService orderService;

    @RequestMapping("/createNative")
    public Map createNative(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
        IdWorker idWorker=new IdWorker();
        if(payLog!=null){
            return weixinPayService.createNative(idWorker.nextId()+"","1");
        }else {
            return new HashMap();
        }




    }

    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) throws Exception {
        Result result = null;
        int x=0;
        while(true){
            //调用查询接口
            Map<String,String> map = weixinPayService.queryPayStatus(out_trade_no);
            if(map==null){
                result=new Result(false,"支付出错");
                break;
            }
            if(map.get("trade_state").equals("SUCCESS")){
                result =new Result(true,"支付成功");

                //修改订单状态
                orderService.updateOrderStatus(out_trade_no,map.get("transaction_id"));
                break;
            }

            Thread.sleep(3000);

            x++;
            if(x>100){
                result =new Result(false,"二维码超时");
                break;
            }
        }
        return result;
    }
}
