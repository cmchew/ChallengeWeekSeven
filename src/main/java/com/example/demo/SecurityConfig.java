package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/js/**", "/styles/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/admin", "/register").hasAnyRole("ADMIN")
//                .antMatchers("/register").hasRole("ADMIN")
                .antMatchers("/").permitAll()//design new home page welcome to courses
                .antMatchers("/addCategory").hasRole("ADMIN")
                .antMatchers("/processCategory").hasRole("ADMIN")
                .antMatchers("/updateCategory/**").hasRole("ADMIN")//adds any character after the slash such as the id number of empl
                .antMatchers("/addLibro").hasRole("ADMIN")
                .antMatchers("/processLibro").hasRole("ADMIN")
                .antMatchers("/updateLibro/**").hasRole("ADMIN")
                .antMatchers("/listCategory", "/detailCategory").hasAnyRole("USER","ADMIN")
                .antMatchers("/listLibro").permitAll()
//                .antMatchers("/detailUser").has("USER")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .permitAll();
        //for accessing H2 for debugging purpose
        httpSecurity.csrf().ignoringAntMatchers("/h2-console/**");
        httpSecurity.headers().frameOptions().sameOrigin();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user_table where username=?")
                .authoritiesByUsernameQuery("select username, role from role_table where username=?");
    }
}


