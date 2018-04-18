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
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //2/.主体提交认证请求-authenticator认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("cwp", "123");
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRoles("admin");
        subject.checkRoles("admin","user");

        subject.checkPermission("user:add");
        subject.checkPermissions("user:add","user:update");

        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
