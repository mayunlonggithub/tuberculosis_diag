//package com.example.tuberculosis.conf;
//
//import com.example.tuberculosis.dao.MenuDao;
//import com.example.tuberculosis.dao.MenuRoleDao;
//import com.example.tuberculosis.dao.RoleDao;
//import com.example.tuberculosis.domain.entity.Menu;
//import com.example.tuberculosis.domain.entity.MenuRole;
//import com.example.tuberculosis.domain.entity.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.core.parameters.P;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Component
//public class CustomFilterInvocationSecurityMetadataSource
//        implements FilterInvocationSecurityMetadataSource {
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//    @Autowired
//    private MenuDao menuDao;
//    @Autowired
//    private MenuRoleDao menuRoleDao;
//    @Autowired
//    private RoleDao roleDao;
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object)
//            throws IllegalArgumentException {
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        List<Menu> allMenus = menuDao.findAll();
//        for (Menu menu : allMenus) {
//            if (antPathMatcher.match(menu.getPattern(), requestUrl)) {
//                List<MenuRole> menuRoleList=menuRoleDao.findByMenuId(menu.getId());
//                List<Integer>  roleList=new ArrayList<>();
//                for(MenuRole menuRole:menuRoleList){
//                    roleList.add(menuRole.getRoleId());
//                }
//
//                List<Role> roles = roleDao.findByIdIn(roleList);
//                String[] roleArr = new String[roles.size()];
//                for (int i = 0; i < roleArr.length; i++) {
//                    roleArr[i] = roles.get(i).getName();
//                }
//                return SecurityConfig.createList(roleArr);
//            }
//        }
//        return SecurityConfig.createList("ROLE_LOGIN");
//    }
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return FilterInvocation.class.isAssignableFrom(clazz);
//    }
//}
