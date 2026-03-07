package ic.webp.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ic.webp.demo.service.UserService

import org.springframework.stereotype.Service


@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/", "/about", "/projects", "/posts/list", "/css/**", "/js/**")
                    .access { authentication, context ->
                        val request = context.request
                        val remoteAddr = request.remoteAddr
                        val isLocal = remoteAddr == "127.0.0.1" || remoteAddr == "::1"

                        org.springframework.security.authorization.AuthorizationDecision(true)
                    }
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/")
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getByEmail(username)
            ?: throw UsernameNotFoundException("User not found")

        return User.builder()
            .username(user.email)
            .password(user.password) // DB에 암호화된 비밀번호 저장되어 있어야 함
            .roles("USER")
            .build()
    }
}