package com.peng.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
    //SimpleAccountRealm不支持添加授权
    SimpleAccountRealm realm = new SimpleAccountRealm();
    @Before
    public void addUser(){
        realm.addAccount("cwp","123","admin","user");
    }
    @Test
    public void testAuthentication() {
        //1.构建securityManager环境-安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //添加realm
        defaultSecurityManager.setRealm(realm);

        //2/.主体提交认证请求-authenticator认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("cwp", "123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());

        //subject.checkRoles("admin");
        subject.checkRoles("admin","user");

        subject.logout();
        System.out.println(subject.isAuthenticated());


    }
}
