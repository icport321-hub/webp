package ic.webp.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.security.core.Authentication
import ic.webp.demo.service.LoginException
import ic.webp.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import ic.webp.demo.service.PostService
import ic.webp.demo.entity.User

@Controller
@RequestMapping("/posts")
public class PostController (
    private val userService: UserService,
    private val postService: PostService
) {
    @GetMapping("/list")
    fun list(model: Model): String {
        val posts = postService.getAll()
        model.addAttribute("posts", posts)
        return "post/list";
    }
    
    @GetMapping("/new")
    fun newPostForm(model: Model): String {
        return "post/new";
    }

    @PostMapping("/new")
    fun createPost(request: HttpServletRequest, authentication: Authentication): String {
        // val userId = (authentication.principal as User).id
        val userId = userService.getByEmail(authentication.name)?.id

        postService.create(
            authorId = userId!!,
            title = request.getParameter("title"),
            content = request.getParameter("content")
        )
        return "redirect:/posts/list"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        val post = postService.get(id)
        model.addAttribute("post", post)
        return "post/detail"
    }
}