package ic.webp.demo

import ic.webp.demo.entity.Post
import ic.webp.demo.repository.PostRepository
import ic.webp.demo.repository.UserRepository
import ic.webp.demo.service.PostService
import ic.webp.demo.entity.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class PostServiceTest @Autowired constructor(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {

    @Test
    fun testCreatePost() {
        val user = userRepository.save(
            User(
                email = "test@test.com",
                password = "password",
                name = "tester"
            )
        )

        val post = Post(
            title = "Test Post",
            content = "This is a test post.",
            author = user
        )
        
        val savedPost = postService.create(post)

        assertNotNull(savedPost.id)
    }

    @Test
    fun testGetPost() {
        val user = userRepository.save(
            User(
                email = "test@test.com",
                password = "password",
                name = "tester"
            )
        )

        val post = Post(
            title = "Test Post",
            content = "This is a test post.",
            author = user
        )

        val savedPost = postService.create(post)

        val retrievedPost = postService.get(savedPost.id!!)!!

        assertEquals("Test Post", retrievedPost.title)
        assertEquals("This is a test post.", retrievedPost.content)
        assertEquals("tester", retrievedPost.author.name)
        assertEquals("test@test.com", retrievedPost.author.email)
    }

    @Test
    fun testGetPostNotFound() {
        assertThrows<IllegalArgumentException> {
            postService.get(9999L)
        }
    }

    @Test
    fun testDeletePost() {
val user = userRepository.save(
            User(
                email = "test@test.com",
                password = "password",
                name = "tester"
            )
        )

        val post = Post(
            title = "Test Post",
            content = "This is a test post.",
            author = user
        )
        
        val savedPost = postService.create(post)

        postService.delete(savedPost.id!!)
        assertThrows<IllegalArgumentException> {
            postService.get(savedPost.id!!)
        }
    }

    private fun user(): User = 
        User(name = "testuser", email = "testuser@example.com", password = "password")
}