package com.hieu.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hieu.blog.security.CustomUserDetailService;
import com.hieu.blog.security.JwtAuthenticationEntryPoint;
import com.hieu.blog.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity

@EnableWebMvc
@EnableMethodSecurity

public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] PUBLIC_URLS = {

         //   "/api/v1/auth/**",
            "/auth/**",

            "/v3/api-docs",
            "/v3/api-docs/**",


            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",

            "/swagger-ui/**",

            "/webjars/**",
            "/configuration/security",
             "/configuration/ui"
    };

    // configuration filter chain

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())// turn off

                .cors(cors -> cors.disable())

			.authorizeHttpRequests(auth -> auth.requestMatchers("/api/**")
				//.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS)
						
						//.requestMatchers(PUBLIC_URLS)

					.permitAll()
						//.requestMatchers("/v3/api-docs/**")
						//.permitAll()

						.requestMatchers(HttpMethod.GET)

						.permitAll()

						.anyRequest()

						.authenticated()

				)

				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))

				.sessionManagement(session -> session.sessionCreationPolicy(

						SessionCreationPolicy.STATELESS))

		;
		// add to filter
		// http.addFilter(jwtAuthenticationFilter);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}*/

//test
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
               .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(req -> req.requestMatchers(PUBLIC_URLS)
                       .permitAll()
                        .requestMatchers(HttpMethod.GET)

                        .permitAll()

                        .anyRequest()
                        .authenticated()


                )


                .sessionManagement(session -> session.sessionCreationPolicy(

                        SessionCreationPolicy.STATELESS))


                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))





      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
    //    return http.build();
        return defaultSecurityFilterChain;

    }

    //test




    /*
     * @Override protected void configure(HttpSecurity http) throws Exception {
     *
     * http. csrf() .disable() .authorizeHttpRequests() .antMatchers(PUBLIC_URLS)
     * .permitAll() .antMatchers(HttpMethod.GET) .permitAll() .anyRequest()
     * .authenticated() .and().exceptionHandling()
     * .authenticationEntryPoint(this.jwtAuthenticationEntryPoint) .and()
     * .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
     *
     * http.addFilterBefore(this.jwtAuthenticationFilter,
     * UsernamePasswordAuthenticationFilter.class);
     *
     * }
     *
     */

    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception {
     *
     * auth.userDetailsService(this.customUserDetailService).passwordEncoder(
     * passwordEncoder());
     *
     * }
     *
     */

    // encode password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * @Bean
     *
     * @Override public AuthenticationManager authenticationManagerBean() throws
     * Exception { return super.authenticationManagerBean(); }
     *
     */

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration)

            throws Exception {
        return configuration.getAuthenticationManager();
    }
    //



}