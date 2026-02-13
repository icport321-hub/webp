package ic.webp.demo


import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping



@Controller
class HomeController{

    @GetMapping("/")
    fun home(model: Model):String{
        // model.addAttribute("message", "타임리프에서 온 메시지")
        return "index"
    }
}