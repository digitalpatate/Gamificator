package ch.heigvd.amt.gamificator.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class AppController {
 /*   @RequestMapping(value = "/")
    public String index() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui/";
    }*/

    @RequestMapping("/healthcheck")
    public ResponseEntity healthcheck() {
        return  new ResponseEntity(HttpStatus.OK);
    }
}
