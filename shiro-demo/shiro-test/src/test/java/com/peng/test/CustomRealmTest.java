package com.peng.test;

import com.peng.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {
    @Test
    public void testAuthentication() {
        //使用自定义的realm
        CustomRealm customRealm = new CustomRealm();

        //1.构建securityManager环境-安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //添加realm
        defaultSecurityManager.setRealm(customRealm);

        //密码加密
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //采用md5的加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密一次
        hashedCredentialsMatcher.setHashIterations(1);
        //自定义realm设置凭证匹配器
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //2/.主体提交认证请求-authenticator认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("cwp", "123");
        //进行登录校验
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //进行角色校验
        subject.checkRoles("admin");
        subject.checkRoles("admin","user");
        //进行权限校验
        subject.checkPermission("user:add");
        subject.checkPermissions("user:add","user:update");
        //登出
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
