package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.common.core.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * Resource Mapper
 *
 * @author senko
 * @date 2022/9/5 15:08
 */
@Mapper
public interface ISysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 获取所有需要权限控制的资源
     */
    Set<ResourceRoleDTO> listNonAnonymousResourceRoles();

}
