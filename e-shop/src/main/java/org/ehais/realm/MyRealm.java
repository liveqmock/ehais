package org.ehais.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 * .shiro.subject.PrincipalCollection) 为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
	 * 
	 * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		logger.info("======用户授权认证======");
		// 获取当前登录的用户名
		String username = (String) super.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		List<String> permList = new ArrayList<String>();
		System.out.println("对当前用户：[" + username + "]进行授权！");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roleList);
		info.addStringPermissions(permList);
		return info;
		
		
//		String userName = principals.getPrimaryPrincipal().toString();
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.setRoles(userService.queryRolesByName(userName));
//        return simpleAuthorizationInfo;
        
        
		
		//获取登录时输入的用户名  
//        String loginName=(String) principalCollection.fromRealm(getName()).iterator().next();  
//        //到数据库查是否有此对象  
//        User user=userService.findByName(loginName);  
//        if(user!=null){  
//            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）  
//            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();  
//            //用户的角色集合  
//            info.setRoles(user.getRolesName());  
//            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要  
//            List<Role> roleList=user.getRoleList();  
//            for (Role role : roleList) {  
//                info.addStringPermissions(role.getPermissionsName());  
//            }  
//            return info;  
//        }  
//        return null;  
        
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.info("======用户登陆认证======");
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		System.out.println(
				"验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

		System.out.println("getName():"+getName()+";token:"+token.getUsername());
		
		AuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
		return info;
		// 将当前用户设置到Session中去以便获取当前用户信息
//		this.setSession("currentUser", user);

		
//		String userName = authenticationToken.getPrincipal().toString();
//        User user = userService.queryUserByName(userName);
//        if (user!=null) {
//            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "peng");
//            return authenticationInfo;
//        }
//        return null;
        
//		return null;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currSubject = SecurityUtils.getSubject();
		if (currSubject != null) {
			Session session = currSubject.getSession();
			System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");

			if (session != null) {
				session.setAttribute(key, value);
			}
		}

	}

}
