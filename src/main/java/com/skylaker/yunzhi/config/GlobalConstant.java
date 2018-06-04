package com.skylaker.yunzhi.config;

import java.net.URI;

/**
 * 常量定义类
 *
 * User: zhuyong
 * Date: 2018/5/20 14:46
 */
public class GlobalConstant {
    //Redis数据结构名称定义
    //规则：REDIS_ + 结构类型_ + 业务含义
    //-------------------------------
    //已申请验证码手机号时间戳
    public static final String REDIS_HASH_PHONEVERCODE_TIME = "REDIS_HASH_PHONEVERCODE_TIME";
    //手机号与验证码匹配集合
    public static final String REDIS_HASH_PHONEVERCODES     = "REDIS_HASH_PHONEVERCODES";
    //用户头像相对路径
    public static final Object REDIS_HASH_USER_HEAD_IMG     = "REDIS_HASH_USER_HEAD_IMG";
    //已注册手机号
    public static final String REDIS_SET_HASREGISTERPHONE   = "REDIS_SET_HASREGISTERPHONE";
    //用户点赞的回答
    public static final String REDIS_SET_STAR_ANSWERS       = "REDIS_SET_STAR_ANSWERS_";
    //用户的问题
    public static final String REDIS_SET_USER_QUESTIONS     = "REDIS_SET_USER_QUESTIONS_";
    //问题时间戳信息
    public static final Object REDIS_ZSET_QUESTIONS_TIME    = "REDIS_ZSET_QUESTIONS_TIME";
    //热门问题
    public static final Object REDIS_ZSET_QUESTIONS_HOT     = "REDIS_ZSET_QUESTIONS_HOT";
    //问题对应的所有回答zset key名称后缀
    public static final String REDIS_ZSET_QUESTION_ANSWERS  = "REDIS_ZSET_QUESTION_ANSWERS_";


    //非业务型常量定义
    //-------------------------------
    //一分钟毫秒数
    public static final long    ONE_MINUTE_MICRO_SECONDS    = 60 * 1000;
    //密码加密次数
    public static final int     PASSWORD_ENCRYPT_COUNT      = 3;
    //密码加密算法
    public static final String  PWD_MD5                     = "md5";
    //用户session名称
    public static final Object  SESSION_USER_NAME           = "user";
    //每次加载最新问题数量
    public static final int     QUESTIONS_NUM               = 10;
    //每次加载回答数量
    public static final Integer ANSWERS_NUM                 = 10;
    //热门指数最小值
    public static final double  MIN_HOT_INDEX               = Double.valueOf(1);
    //文件类型:用户上传头像
    public static final String  FILE_TYPE_USER_HEAD_IMG     = "USERHEADIMG";
    //文件类型:回答中插入的图片
    public static final String  FILE_TYPE_ANSWERS_IMG       = "ANSWERSIMG";
    //合法性验证对象类型：问题
    public static final String QUESTION = "QUESTION";
    //合法性验证对象类型：回答
    public static final String ANSWER = "ANSWER";
    //添加操作
    public static final String ADD = "add";
    //删除操作
    public static final String DELETE = "delete";
    //修改操作
    public static final String UPDATE = "update";
    //Lucene索引库位置
    public static final String LUCENE_INDEX_PATH = "D:\\YunZhi\\Temp";
}