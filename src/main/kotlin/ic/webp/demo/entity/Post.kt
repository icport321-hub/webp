package ic.webp.demo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
open class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @Column(nullable = false)
    open var title: String,

    @Lob
    open var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    open var author: User,

    @CreationTimestamp
    @Column(updatable = false)
    open var createdAt: LocalDateTime = LocalDateTime.now()
)
