package com.runke.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 决策器
 */
@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 通过传递的参数来确定用户是否有权访问对应保护的对应受保护对象的权限
     * @param authentication
     * @param o
     * @param collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == collection || 0>= collection.size()){
            return;
        }else {
            String needRole;
            for (Iterator<ConfigAttribute> ites = collection.iterator();ites.hasNext();){
                needRole = ites.next().getAttribute();
                for (GrantedAuthority ga:authentication.getAuthorities()) {
                    if (needRole.trim().equals(ga.getAuthority().trim())){
                        return;
                    }
                }
            }
            throw new AccessDeniedException("当前访问没有权限");
        }
    }

    /**
     * 表示此 AccessDecisionManager 是否能够处理传递的ConfigAttribute 呈现的授权请求
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }
    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
