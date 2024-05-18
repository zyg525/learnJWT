package com.zyg.config;

import com.zyg.security.MyAccessDeniedHandler;
import com.zyg.security.MyJwtFilter;
import com.zyg.security.MyJwtProvider;
import com.zyg.security.MyUnauthorizedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtConfig {

    /**
     * 负责调用AuthenticationProvider
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public MyJwtFilter myJwtFilter() {
        return new MyJwtFilter();
    }

    /**
     * 加密算法
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyJwtProvider myJwtProvider() {
        return new MyJwtProvider();
    }

    @Bean
    public MyUnauthorizedHandler myUnauthorizedHandler() {
        return new MyUnauthorizedHandler();
    }

    @Bean
    public MyAccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //基于token，所以不需要csrf防护
        httpSecurity.csrf().disable()
                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myUnauthorizedHandler())
                .accessDeniedHandler(myAccessDeniedHandler())
                .and()
                .authorizeRequests()
                //登录不需要认证
                .antMatchers("/login").permitAll()
                //除上面的所有请求全部需要鉴权认证
                .anyRequest()
                .authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity.authenticationProvider(myJwtProvider());
        //将我们的JWT filter添加到UsernamePasswordAuthenticationFilter前面，因为这个Filter是authentication开始的filter，我们要早于它
        httpSecurity.addFilterBefore(myJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
