package ic.webp.demo.service

import ic.webp.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ic.webp.demo.entity.User
import ic.webp.demo.entity.Role
import ic.webp.demo.config.SecurityConfig

@Service
@Transactional
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: SecurityConfig
) {
    fun register(email: String, password: String, name: String, role: Role = Role.USER): User {
        if (userRepository.findByEmail(email) != null) {
            throw IllegalArgumentException("Email already registered")
        }
        val encodedPassword = passwordEncoder.passwordEncoder().encode(password)
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw IllegalArgumentException("Password cannot be blank")
        }
        val newUser = User(
            email = email, 
            password = encodedPassword, 
            name = name, 
            role = role)
        return userRepository.save(newUser)
    }

    fun getByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}