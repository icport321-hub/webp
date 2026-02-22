package ic.webp.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/**")
                    .access { authentication, context ->
                        val request = context.request
                        val remoteAddr = request.remoteAddr
                        val isLocal = remoteAddr == "127.0.0.1" || remoteAddr == "::1"

                        // if (isLocal) {
                        //     org.springframework.security.authorization.AuthorizationDecision(true)
                        // } else {
                        //     org.springframework.security.authorization.AuthorizationDecision(
                        //         authentication.get().isAuthenticated
                        //     )
                        // }
                        org.springframework.security.authorization.AuthorizationDecision(true)
                    }
                    .anyRequest().authenticated()
            }
            .formLogin { it.permitAll() }

        return http.build()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}