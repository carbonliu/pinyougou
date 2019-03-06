<html>
    <head>
        <meta charset="utf-8">
        <title>Freemarker入门小demo</title>
    </head>
    <body>
        <#include "head.ftl">
        <#-- 这里是一个注释，不会有任何输出 -->
        ${name},你好。${message}!
        <br>
        <#assign linkman="张先生">
        ${linkman}

    <br>
    <#assign info={"mobile":"1381767165","address":"北京市东城"}>
    ${info.mobile}<br>
    ${info.address}<br>

    <#if success=true>
        你以通过认证
        <#else >
        你未通过认证
    </#if>
    <br>
    <#--商品列表--><br>
    <#list goodsList as goods>
    ${goods_index+1}商品名称：${goods.name}商品价格：${goods.price}<br>
    </#list><br>
    共${goodsList?size}条记录<br>

    <#--将json字符串转化为对象-->
    <#assign text="{'brank':'工商银行','account':'6435454534235433243'}"/>
    <#assign data=text?eval/>
    开户行：${data.account}<br>
    账户:${data.account}<br>

    <#-- 日期格式化 -->
    当前日期：${today?date}<br>
    当前时间：${today?time}<br>
    当前日期加时间：${today?datetime}<br>
    日期格式化：${today?string("yyyy年MM月dd日")}<br>

    <#--数字转变为字符串-->
    当前会员积分：${point?c}<br>

    <#--空值处理运算符-->
    <#--判断莫字符串是否存在："??"-->
    <#if aaa??>
        aaa存在
    <#else>
        aaa不存在
    </#if>
    <br>
    <#--缺失变量默认值:"!"  在代码中不对aaa赋值，也不会报错了，当aaa为null则返回！后边的内容-->
    ${aaa!'-'}<br>

    </body>
</html>