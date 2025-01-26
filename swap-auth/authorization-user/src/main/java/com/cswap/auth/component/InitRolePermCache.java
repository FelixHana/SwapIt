package com.cswap.auth.component;

import com.cswap.auth.service.ISysNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ZCY-
 */
@Component
@RequiredArgsConstructor
public class InitRolePermCache implements CommandLineRunner {
    private final ISysNodeService sysNodeService;

    @Override
    public void run(String... args) throws Exception {
        sysNodeService.initRolePermCache();
    }
}
