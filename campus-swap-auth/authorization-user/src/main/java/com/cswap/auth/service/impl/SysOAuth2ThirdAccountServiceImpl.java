package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.domain.enums.UserCodeEnum;
import com.cswap.auth.exception.UserException;
import com.cswap.auth.service.ISysBaseUserService;
import com.cswap.auth.service.ISysOAuth2ThirdAccountService;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import com.cswap.auth.mapper.SysOauth2ThirdAccountMapper;
import com.cswap.auth.utils.ThirdAccountsPasswordGenerator;
import com.cswap.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方账号表 服务实现类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Service
@RequiredArgsConstructor
public class SysOAuth2ThirdAccountServiceImpl extends ServiceImpl<SysOauth2ThirdAccountMapper, SysOAuth2ThirdAccount> implements ISysOAuth2ThirdAccountService {
    private final ISysBaseUserService baseUserService;

    @Override
    public String createThirdUser(SysOAuth2ThirdAccount account) {
        // 查询存在的第三方账号
        SysOAuth2ThirdAccount existingAccount = this.lambdaQuery()
                .eq(SysOAuth2ThirdAccount::getPlatform, account.getPlatform())
                .eq(SysOAuth2ThirdAccount::getUniqueId, account.getUniqueId())
                .one();
        String generatedPassword = ThirdAccountsPasswordGenerator.generatePassword(account.getUniqueId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(generatedPassword);
        if(existingAccount == null){
            // 创建系统用户 关联 id
            Long userId = baseUserService.createUserByThirdAccount(account, encodedPassword);
            account.setUserId(userId);
            boolean saved = save(account);
            if (!saved) {
                throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "三方用户");
            }
            return generatedPassword;
        }

        // 已经存在三方用户 但没有关联系统用户
        if(BeanUtils.isEmpty(existingAccount.getUserId())){
            Long userid = baseUserService.createUserByThirdAccount(account, encodedPassword);
            existingAccount.setUserId(userid);
        }
        // 更新凭据信息
        existingAccount.setCredential(account.getCredential());
        existingAccount.setCredentialExpireTime(account.getCredentialExpireTime());
        boolean updated = updateById(existingAccount);
        if (!updated) {
            throw new UserException(UserCodeEnum.USER_UPDATE_ERROR_DB, "三方用户");
        }
        return generatedPassword;
    }


}
