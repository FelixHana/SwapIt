package com.cswap.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.auth.domain.dto.PermissionRoleDto;
import com.cswap.auth.domain.po.SysNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysNodeMapper extends BaseMapper<SysNode> {

    List<SysNode> listRolePerms();
}
