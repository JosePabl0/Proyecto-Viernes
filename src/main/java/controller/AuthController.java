package com.inksticker.controller;

import com.inksticker.entity.Usuario;
import com.inksticker.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioServiceImpl usuarioService;

    public AuthController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(
            @Valid @ModelAttribute("registroForm") RegistroForm formulario,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            return "auth/registro";
        }

        try {
            Usuario usuario = usuarioService.registrarUsuario(
                    formulario.getNombre(),
                    formulario.getCorreo(),
                    formulario.getContrasena()
            );

            return "redirect:/login?registrado";

        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorRegistro", ex.getMessage());
            return "auth/registro";
        }
    }

    public static class RegistroForm {

        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
        private String nombre;

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "Introduce un correo válido.")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
        private String correo;

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres.")
        private String contrasena;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }
}
