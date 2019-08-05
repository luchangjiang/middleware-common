package com.lotstock.eddid.common.core.util;

/**
 * Redis Key 生成工具类
 *
 * @author Flynn
 * @date 2018/7/17
 */
public enum RedisKeyUtils {

    /**
     * 用户服务
     */
    Customer("customer"),

    /**
     * F10个股资讯
     */
    FH("fh");

    /**
     * 服务名，Redis Key 使用服务名作为Key前缀
     */
    private String serviceName;

    RedisKeyUtils(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 生成Redis Key
     *
     * @param module 模块名
     * @param func   方法或表命
     * @param args   自定义字段
     * @return 格式举例： customer:account:getUserInfo:{userId}
     */
    public String getKey(String module, String func, Object... args) {
        StringBuilder sb = new StringBuilder(serviceName);
        sb.append(":").append(module);
        sb.append(":").append(func);
        for (Object arg : args) {
            sb.append(":").append(arg);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        //测试使用
        System.out.println(RedisKeyUtils.Customer.getKey("Account", "userInfo", "userId的值"));
        System.out.println(RedisKeyUtils.FH.getKey("News", "getLatestNews", "股票Id的值"));
    }


}
