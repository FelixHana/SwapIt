package com.cswap.utils.shiro;

import com.cswap.domain.po.User;
import com.cswap.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权方法
     * @param principalCollection
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @throws AuthenticationException
     *
     * 客户端传来的 username 和 password 会自动封装到 token，先根据 username 进行查询，
     *      如果返回 null，则表示用户名错误，直接 return null 即可，Shiro 会自动抛出 UnknownAccountException 异常。
     *      如果返回不为 null，则表示用户名正确，再验证密码，直接返回  SimpleAuthenticationInfo 对象即可，
     *          如果密码验证成功，Shiro 认证通过，否则返回 IncorrectCredentialsException 异常。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // System.out.println("启动认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取存放到数据库中的实体类
        User user = userService.lambdaQuery().eq(User::getUsername, token.getUsername()).one();
        // System.out.println(user);
        if(user != null){
            // 参数列表（实体信息，密码，盐值，realm名称）
            return new SimpleAuthenticationInfo(user,user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()),getName());
        }
        return null;
    }
}
