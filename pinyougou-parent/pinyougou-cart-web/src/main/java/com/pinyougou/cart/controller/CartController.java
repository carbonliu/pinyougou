package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author LiuK
 * @date 2018/12/23 19:00
 * @description:
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 6000)
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/findCartList")
    public List<Cart> findCartList(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        //从cookie中查询
        String cartListString = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if("null".equals(cartListString) || cartListString ==""||cartListString==null){
            cartListString="[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);

        if(username.equals("anonymousUser")){//未登录，从cookie中取


            return cartList_cookie;
        }else{//已登录
            List<Cart> cartList_redis=cartService.findCartListFromRedis(username);
            if (cartList_cookie.size()>0){//如果cookie中存在购物车
                //合并购物车
                cartList_redis=cartService.mergeCartList(cartList_redis,cartList_cookie);
                //清除本地cookie数据
                CookieUtil.deleteCookie(request,response,"cartList");
                //将合并后的数据存入redis
                cartService.saveCartListToRedis(username,cartList_redis);
            }

            return cartList_redis;
        }

    }

    /**
     * 添加商品到购物车
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    //@CrossOrigin(origins = "http://localhost:9105",allowCredentials ="true")替代下面的两句，allowCredentials ="true"可以不写 缺省值默认为true
    public Result addGoodsToCartList(Long itemId,Integer num){
        //获取登录名
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        response.setHeader("Access-Control-Allow-Origin","http://localhost:9105");//可以访问的域（此方法不需要操作cookie）
        response.setHeader("Access-Control-Allow-Credentials","true");//如果需要操作cookie,必须加上这句话

        System.out.println("当前登录用户："+username);
        try {
            //获取购物车列表
            List<Cart> cartList = findCartList();
            //调用服务层添加商品到购物车拿到新的购物车列表
            cartList=cartService.addGoodsToCartList(cartList,itemId,num);

            //判断用户是否登录，根据结果进行不同操作
            if(username.equals("anonymousUser")){//未登录
                //将新的购物车列表存如cookie
                CookieUtil.setCookie(request,response,"cartList",JSON.toJSONString(cartList),3600*24,"UTF-8");

                System.out.println("未登录：存入cookie");
            }else {//已登录,存入redis
                cartService.saveCartListToRedis(username,cartList);
            }

            return new Result(true,"success");
        } catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"failure");
        }


    }
}
