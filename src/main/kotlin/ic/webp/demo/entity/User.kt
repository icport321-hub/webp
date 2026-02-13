package ic.webp.demo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


@Entity
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    
    @Column(nullable = false, unique = true)
    open var email: String,

    @Column(nullable = false)
    open var password: String,

    @Column(nullable = false)
    open var name: String,

    @CreationTimestamp
    @Column(updatable = false)
    open var createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var role: Role = Role.USER
)

enum class Role {
    ADMIN, USER
}