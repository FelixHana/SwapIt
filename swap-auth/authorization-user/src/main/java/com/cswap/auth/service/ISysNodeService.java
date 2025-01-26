package com.cswap.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.auth.domain.po.SysNode;

import java.util.List;


public interface ISysNodeService extends IService<SysNode> {

    void initRolePermCache();

    List<SysNode> listRolePerms();
}
