package com.senko.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.dto.SysUserAssignmentDTO;
import com.senko.common.core.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户Mapper
 *
 * @author senko
 * @date 2022/8/31 13:48
 */
@Mapper
public interface ISysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询授权用户 集合
     * @param userIds       用户ID集合
     */
    List<SysUserAssignmentDTO> listAssignmentUserDTOs(@Param("userIds") List<Long> userIds,
                                                      @Param("current") Long current,
                                                      @Param("size") Long size,
                                                      @Param("username") String username,
                                                      @Param("nickname") String nickname,
                                                      @Param("email") String email,
                                                      @Param("isDisabled") Integer isDisabled);
}
