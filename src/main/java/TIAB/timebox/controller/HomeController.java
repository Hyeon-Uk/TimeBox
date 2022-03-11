package TIAB.timebox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value="/")
    public String home(Model model){
        model.addAttribute("name","hyeonuk");
        return "home";
    }
}
