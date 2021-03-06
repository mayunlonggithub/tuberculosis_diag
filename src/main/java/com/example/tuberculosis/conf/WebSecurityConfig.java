//package com.example.tuberculosis.conf;
//
//import com.example.tuberculosis.service.Impl.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    UserServiceImpl userService;
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                        object.setSecurityMetadataSource(cfisms());
//                        object.setAccessDecisionManager(cadm());
//                        return object;
//                    }
//                })
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login").permitAll()
//                .and()
//                .csrf().disable();
//    }
//    @Bean
//    CustomFilterInvocationSecurityMetadataSource cfisms() {
//        return new CustomFilterInvocationSecurityMetadataSource();
//    }
//    @Bean
//    CustomAccessDecisionManager cadm() {
//        return new     CustomAccessDecisionManager();
//    }
//}
