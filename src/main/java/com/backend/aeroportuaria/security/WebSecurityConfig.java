package com.backend.aeroportuaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.aeroportuaria.security.jwt.AuthEntryPointJwt;
import com.backend.aeroportuaria.security.jwt.JwtFilter;
import com.backend.aeroportuaria.security.serviceimpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Se utiliza para especificar a qué métodos del controller puede acceder sólo el administrador. Los métodos que no lleven una anotación puede acceder tanto el administrador como los demás usuarios. A los que lleven anotación preautorice sólo va a poder acceder los usuarios especificados. Las anotaciones se especifican en el controller
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthEntryPointJwt authEntryPointJwt; //Es el que devuelve el mensaje de no autorizado

    @Bean
    public JwtFilter jwtTokenFilter(){
        return new JwtFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //Cifra la contraseña
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //STATELESS indica que será una política de inicio de sesión sin estados porque no hay estados, no se utiliza cookies, por cada petición se envía un token
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() //Aquí va a estar login como nuevo usuario entonces puede acceder todo mundo
                .anyRequest().authenticated(); //Para los demás debe estar autenticado
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); //Por cada petición comprueba el token y le pasa el usuario al contexto de autenticación. .addFilterBefore indica que se realizará antes de cada petición

    }

}
