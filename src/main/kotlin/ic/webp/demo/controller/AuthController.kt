package ic.webp.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import ic.webp.demo.service.LoginService
import ic.webp.demo.service.LoginException
import ic.webp.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/auth")
public class AuthController (
    private val loginService: LoginService,
    private val userService: UserService
) {
    
    @GetMapping("/login")
    fun loginForm(model: Model): String {
        return "login";
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        request.session.invalidate()
        return "redirect:/"
    }

    @PostMapping("/register")
    fun registerSubmit(@RequestParam email: String, @RequestParam password: String, @RequestParam name: String, model: Model): String {
        val existingUser = userService.getByEmail(email)
        if (existingUser != null) {
            model.addAttribute("error", "Email already registered")
            return "auth/login"
        }
        userService.register(email, password, name)
        return "redirect:/auth/login"
    }
}