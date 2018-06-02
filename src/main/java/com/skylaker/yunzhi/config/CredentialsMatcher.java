package com.skylaker.yunzhi.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证登录用户——匹配用户登录令牌和数据库用户名、密码信息一致性
 *
 * User: zhuyong
 * Date: 2018/5/12 9:48
 */
public class CredentialsMatcher extends HashedCredentialsMatcher {
    //声明缓存接口
    private Cache<String, AtomicInteger> passwordRetryCache;

    public  CredentialsMatcher(CacheManager cacheManager){
        this.passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 凭证验证
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户名
        String username = (String) token.getPrincipal();
        //输错密码次数
        //从缓存中获取已保存的用户密码输错次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(null == retryCount){
            retryCount = new AtomicInteger(0);
            //缓存中没有则放入初始输错密码次数
            passwordRetryCache.put(username, retryCount);
        }

        //输错密码5次则锁定用户一段时间
        if(retryCount.incrementAndGet() > 5){
            throw  new ExcessiveAttemptsException();
        }

        boolean matchResult = super.doCredentialsMatch(token, info);
        //验证通过时清除输错密码次数缓存
        if(matchResult){
            passwordRetryCache.remove(username);
        }

        return matchResult;
    }
}