package the305labs.inventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    // mvn spring-boot:run
    //mysql -u root -p

    @GetMapping("/")
    public String home() {
        return "prueba 123";
    }
}