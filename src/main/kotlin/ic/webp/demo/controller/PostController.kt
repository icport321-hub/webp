package ic.webp.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import ic.webp.demo.service.LoginException
import ic.webp.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/posts")
public class PostController (
    private val userService: UserService
) {
    
    @GetMapping("/new")
    fun newPostForm(model: Model): String {
        return "posts/new";
    }

    @PostMapping("/new")
    fun createPost(request: HttpServletRequest): String {
        
        return "redirect:/posts"
    }
}