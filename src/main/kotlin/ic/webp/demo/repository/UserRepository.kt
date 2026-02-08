package ic.webp.demo.repository

import ic.webp.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    // check for login
    fun findByEmail(email: String): User?
    //check for duplicate email
    fun existsByEmail(email: String): Boolean
}
