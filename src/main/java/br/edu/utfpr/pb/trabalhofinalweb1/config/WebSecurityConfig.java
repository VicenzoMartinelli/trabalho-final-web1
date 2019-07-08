package br.edu.utfpr.pb.trabalhofinalweb1.config;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.Base64Converter;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ServiceUser _serviceUser;
    @Autowired
    private Base64Converter _cv64;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String admin = "ADMIN";
        http.csrf().disable()

                .exceptionHandling()
                .accessDeniedPage("/home?n=1&m=" + _cv64.encode("Você precisa estar autenticado!"))
                .and()
                .formLogin()
                .loginPage("/home?n=1&m=" + _cv64.encode("Realize o login!"))
                .loginProcessingUrl("/auth")
                .defaultSuccessUrl("/home?n=0&m=" + _cv64.encode("LogIn realizado com sucesso!"))
                .failureUrl("/home?n=1&m=" + _cv64.encode("Dados inválidos, verifique suas credenciais!"))
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/home?n=0&m=" + _cv64.encode("LogOut realizado com sucesso!"))
                .logoutUrl("/auth/logout")
                .and().authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole(admin)
                .antMatchers("/brand/**").hasAnyRole(admin)
                .antMatchers("/category/**").hasAnyRole(admin)
                .antMatchers("/provider/**").hasAnyRole(admin)
                .antMatchers("/product/page", "/product/list",
                        "/product/new", "/product/addproduct", "/product/add").hasAnyRole(admin)
                .antMatchers(HttpMethod.DELETE, "/product/**").hasAnyRole(admin)
                .antMatchers("/providerorder/**").hasAnyRole(admin)
                .antMatchers("/profile/editprofile/**").authenticated()
                .antMatchers("/order/**").authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/imgs/**")
                .antMatchers("/assets/**")
                .antMatchers("/webjars/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return _serviceUser;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
}









