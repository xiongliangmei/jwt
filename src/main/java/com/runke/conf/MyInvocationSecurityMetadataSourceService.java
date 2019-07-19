package com.runke.conf;

import com.runke.entity.RolePermisson;
import com.runke.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @desc 存储角色 权限
 * @author xl
 * @date 2019/7/19 11:45
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    PermissionMapper permissionMapper;

    private static HashMap<String,Collection<ConfigAttribute>> map = null;

    /**
     * 返回请求的资源需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation)o).getRequest();
        for (Iterator<String> it = map.keySet().iterator() ; it.hasNext();) {
            String url = it.next();
            if (new AntPathRequestMatcher(url).matches(request)){
                return map.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadResourceDefine();
        return null;
    }
    //指示该类是否能够为指定的方法调用或Web请求提供ConfigAttributes
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化 所有的资源 对应的角色
     */
    public void  loadResourceDefine(){
        map = new HashMap<>();
        List<RolePermisson> rolePermissons = permissionMapper.getRolePermissions();
        //某些个资源 可以被哪些角色访问
        for (RolePermisson rolePermisson:rolePermissons) {
            String url = rolePermisson.getUrl();
            String roleName  = rolePermisson.getRoleName();
            ConfigAttribute role = new SecurityConfig(roleName);

            if (map.containsKey(url)){
                map.get(url).add(role);
            }else {
                List<ConfigAttribute> list = new ArrayList<>();
                list.add(role);
                map.put(url,list);
            }
        }
    }


}
