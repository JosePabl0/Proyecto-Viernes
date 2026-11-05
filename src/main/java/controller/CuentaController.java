package com.inksticker.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CuentaController {

    @GetMapping("/cuenta")
    public String mostrarCuenta(Principal principal) {

        if (principal != null) {
            return "redirect:/perfil";
        }

        return "account/cuenta";
    }
}
