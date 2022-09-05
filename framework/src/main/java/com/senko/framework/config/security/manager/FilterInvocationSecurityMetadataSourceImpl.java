package com.senko.framework.config.security.manager;

import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.framework.web.core.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 访问资源 所需角色
 *
 * @author senko
 * @date 2022/9/5 13:01
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ISysResourceService resourceService;

    /**
     * 临时 [请求资源 : 所需角色] 缓存
     * 省去频繁读写IO
     * <p>
     * 需要在 Resource、Role等发生更改时手动清空
     */
    private Set<ResourceRoleDTO> resourceRoles;

    /**
     * 加载资源
     */
    @PostConstruct
    public void loadResourceRolesCache() {
        resourceRoles = resourceService.listNonAnonymousResourceRoles();
    }

    /**
     * 清空缓存
     */
    public void clearResourceRolesCache() {
        resourceRoles = null;
    }


    /**
     * 给出访问资源所需要的角色
     *
     * @param object FilterInvocation 也就是要访问的URL资源
     * @return 访问该资源所需要的角色
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 空指针判断
        if (Objects.isNull(resourceRoles)) {
            loadResourceRolesCache();
        }

        if (object instanceof FilterInvocation) {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            HttpServletRequest request = filterInvocation.getRequest();

            // 提取请求信息
            String method = request.getMethod();
            String url = request.getRequestURI();

            // 匹配器
            AntPathMatcher pathMatcher = new AntPathMatcher();
            for (ResourceRoleDTO resourceRole : resourceRoles) {

                // 如果路径匹配 并且 请求方法匹配
                if (pathMatcher.match(resourceRole.getUrl(), url) &&
                        method.equals(resourceRole.getMethod())) {

                    // 则返回该资源所需角色
                    String[] roles = resourceRole.getRoles()
                            .toArray(new String[0]);
                    return SecurityConfig.createList(roles);

                }

            }

            return SecurityConfig.createList("unknown");
        }

        // 运行到这一步，说明这个资源是可以匿名访问的，于是返回null
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // ignored
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // support all
        return true;
    }
}
