package ic.webp.demo.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ic.webp.demo.entity.Post

interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Post>

    fun findAllByAuthorId(authorId: Long): List<Post>

    fun findByTitleContaining(keyword: String): List<Post>
}
