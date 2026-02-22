package ic.webp.demo


import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import jakarta.servlet.http.HttpServletRequest



@Controller
class HomeController{

    @GetMapping("/")
    fun home(model: Model, request: HttpServletRequest):String{
        // model.addAttribute("message", "타임리프에서 온 메시지")
        val userId = request.session.getAttribute("USER_ID")
        model.addAttribute("isLoggedIn", request.session.getAttribute("USER_ID") != null)
        return "index"
    }
    
    @GetMapping("/about")
    fun about(model: Model, request: HttpServletRequest):String{
        return "about"
    }
    
    @GetMapping("/projects")
    fun projects(model: Model, request: HttpServletRequest):String{
        return "projects"
    }

}