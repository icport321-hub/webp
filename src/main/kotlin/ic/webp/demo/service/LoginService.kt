package ic.webp.demo.service

import ic.webp.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ic.webp.demo.entity.User
import ic.webp.demo.config.SecurityConfig

@Service
@Transactional
class LoginService (
    private val userRepository: UserRepository,
    private val passwordEncoder: SecurityConfig
) {
    fun login(email: String, password: String): User {
        val user = userRepository.findByEmail(email) ?: throw LoginException.UserNotFound
        if (!passwordEncoder.passwordEncoder().matches(password, user.password)) {
            throw LoginException.PasswordMismatch
        }
        return user
    }
}

sealed class LoginException : RuntimeException() {
    object UserNotFound : LoginException()
    object PasswordMismatch : LoginException()
}
