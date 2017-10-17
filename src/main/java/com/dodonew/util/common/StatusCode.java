package com.dodonew.util.common;

/**
 * Created by Bruce on 2017/10/17.
 */
public class StatusCode {
    public static final Integer SUCCESS = 0; // 成功
    public static final Integer ERROR_UNKNOW = -1; // 系统错误

    // app端使用100开头的
    public static final Integer ERROR_TIMEOUT = 100; // 请求时间不合法
    public static final Integer ERROR_SIGN_INVALIDATE = 101; // 签名校验失败
    public static final Integer ERROR_REQUIREDPARAMS_LOST = 102; // 必传参数未传
    public static final Integer ERROR_DATA_EMPTY = 103; // 数据为空

    // 后台网站使用200开头的 以后其他类型以此类推

}
