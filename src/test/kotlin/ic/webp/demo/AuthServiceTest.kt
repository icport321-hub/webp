package ic.webp.demo

import org.springframework.boot.test.context.SpringBootTest
import ic.webp.demo.service.LoginService
import ic.webp.demo.service.LoginException
import ic.webp.demo.service.UserService
import ic.webp.demo.entity.Role
import ic.webp.demo.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AuthServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val loginService: LoginService,
    private val userService: UserService
){
    @Test
    fun testRegisterAndLogin() {
        val email = "fox-god"
        val password = "securePassword"
        val name = "Test User"
        val registeredUser = userService.register(email, password, name)
        assertNotNull(registeredUser.id)
        val loggedInUser = loginService.login(email, password)
        assertEquals(registeredUser.id, loggedInUser.id)
    }

    @Test
    fun testLoginWithWrongPassword() {
        val email = "fox-god-2"
        val password = "securePassword"
        val name = "Test User 2"
        userService.register(email, password, name)
        assertThrows<LoginException.PasswordMismatch> {
            loginService.login(email, "wrongPassword")
        }
    }

    @Test
    fun testLoginWithNonExistentEmail() {
        assertThrows<LoginException.UserNotFound> {
            loginService.login("nonexistent@example.com", "anyPassword")
        }
    }

    @Test
    fun testRegisterWithExistingEmail() {
        val email = "fox-god-3"
        val password = "securePassword"
        val name = "Test User 3"
        userService.register(email, password, name)
        assertThrows<IllegalArgumentException> {
            userService.register(email, "anotherPassword", "Another User")
        }
    }   

    @Test
    fun testPasswordEncoding() {
        val rawPassword = "mySecretPassword"
        val encodedPassword = passwordEncoder.encode(rawPassword)
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
        
    }
}