package ic.webp.demo.service

import ic.webp.demo.entity.Post
import ic.webp.demo.repository.PostRepository
import ic.webp.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
@Transactional
class PostService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun create(authorId: Long, title: String, content: String): Post {
        val author = userRepository.findById(authorId).orElseThrow(
            { IllegalArgumentException("User with id $authorId not found") }
        )
        val post = Post(title = title, content = content, author = author)
        return postRepository.save(post)
    }

    fun get(id: Long): Post? {
        return postRepository.findById(id).orElseThrow(
            { IllegalArgumentException("Post with id $id not found") }
        )
    }

    fun getAll(): List<Post> =
        postRepository.findAll()
    

    fun getPage(pageable: Pageable): Page<Post> =
        postRepository.findAllByOrderByCreatedAtDesc(pageable)
        .orElseThrow { IllegalArgumentException("No posts found") }
    

    fun delete(id: Long) =
        postRepository.deleteById(id)
    
}