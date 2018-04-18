package com.peng.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("3er4#ER$");
    }

    @Test
    public void testAuthentication() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        //使用自定义的sql语句进行鉴权
        String sql = "select pass_word from test_users where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role from test_user_roles where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String rolePermissionSql = "select permission from test_roles_permissions wher role = ?";
        //1.构建securityManager环境-安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //添加realm
        //defaultSecurityManager.setRealm(realm);
        defaultSecurityManager.setRealm(jdbcRealm);

        //2/.主体提交认证请求-authenticator认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("cwp", "123");
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        //subject.checkRole("admin");
        //subject.checkRoles("admin","user");
        subject.checkRoles("user");
        //鉴权之前需要设置权限开关为true
        subject.checkPermission("user:add");

        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());


    }
}
