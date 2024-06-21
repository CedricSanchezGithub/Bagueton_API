package org.bagueton_api.usermanagement

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

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
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/bagueton/readrecipes").permitAll()
                    .requestMatchers("/bagueton/createrecipe").permitAll()
                    .requestMatchers("/bagueton/deleterecipe/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { formLogin -> formLogin.permitAll() }
            .logout { logout -> logout.permitAll() }

        return http.build()
    }

}
@Bean
fun userDetailsService(userRepository: UserRepository): UserDetailsService {
    return UserDetailsServiceImpl(userRepository)
}
