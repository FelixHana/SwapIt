package com.cswap.auth.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.domain.dto.PermissionRoleDto;
import com.cswap.auth.service.ISysNodeService;
import com.cswap.auth.domain.po.SysNode;
import com.cswap.auth.mapper.SysNodeMapper;
import com.cswap.common.constant.GlobalConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ZCY-
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysNodeServiceImpl extends ServiceImpl<SysNodeMapper, SysNode> implements ISysNodeService {

    public final RedisTemplate<String, Object> redisTemplate;
    @Override
    public void initRolePermCache() {
        redisTemplate.delete(GlobalConstants.REDIS_PERM_ENDPOINT_KEY);
        List<SysNode> rolePerms = this.listRolePerms();
        if (CollUtil.isNotEmpty(rolePerms)){
            Map<String, List<String>> endpointPerms = new HashMap<>();
            rolePerms.forEach(item -> {
                String endpoint = item.getMethod().concat(":").concat(item.getUrl());
                List<String> roles = List.of(item.getRoles().split(","));
                endpointPerms.put(endpoint, roles);
            });
            redisTemplate.opsForHash().putAll(GlobalConstants.REDIS_PERM_ENDPOINT_KEY, endpointPerms);
        }

    }

    @Override
    public List<SysNode> listRolePerms() {
        return baseMapper.listRolePerms();
    }
}
