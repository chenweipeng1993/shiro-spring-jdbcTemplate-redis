package com.peng.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {
    @Test
    public void testAuthentication() {
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //添加realm
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("cwp","123");
        subject.login(usernamePasswordToken);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:update");

        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
