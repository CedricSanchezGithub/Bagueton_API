package org.bagueton_api.usermanagement

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Arrays

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { } // Permet d'afficher des données sur bagueton.cedricsanchez.fr
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/bagueton/readrecipes").permitAll()
                    .requestMatchers("/bagueton/createrecipe").permitAll()
                    .requestMatchers("/bagueton/deleterecipe/**").permitAll()
                    .requestMatchers("/bagueton/updaterecipe/**").permitAll()
                    .requestMatchers("/bagueton/readform").permitAll()
                    .requestMatchers("/bagueton/form").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { formLogin -> formLogin.permitAll() }
            .logout { logout -> logout.permitAll() }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("http://localhost:3000")
        configuration.allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = Arrays.asList("Authorization", "Cache-Control", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return UserDetailsServiceImpl(userRepository)
    }
}
