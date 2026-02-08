package ic.webp.demo.service

import ic.webp.demo.entity.Post
import ic.webp.demo.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {
    fun create(post: Post): Post {
        return postRepository.save(post)
    }

    fun get(id: Long): Post? {
        return postRepository.findById(id).orElseThrow(
            { IllegalArgumentException("Post with id $id not found") }
        )
    }

    // fun getAll(): List<Post> {
    //     return postRepository.findAll()
    // }

    fun getPage(pageable: Pageable): Page<Post> {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
    }

    fun delete(id: Long) {
        postRepository.deleteById(id)
    }
}