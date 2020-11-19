package matcha.controller;//package matcha.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Locale;
//
//@Controller
//public class RegistrationController {
//
//    @Autowired
//    private EntityActions entityActions;
//
//    @GetMapping("/regitrationConfirm.html")
//    public String confirmRegistration
//            (WebRequest request, Model model, @RequestParam("token") String token) {
//
//        Locale locale = request.getLocale();
//        boolean verificationToken = entityActions.getVerificationToken(token);
//
//        if (!verificationToken) {
//            String message = "auth.message.invalidToken"; //messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("message", message);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }
//        return "redirect:?lang=" + request.getLocale().getLanguage();
//    }
//}
