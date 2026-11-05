package com.inksticker.controller;

import com.inksticker.entity.Usuario;
import com.inksticker.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PerfilController {

    private final UsuarioRepository usuarioRepository;

    public PerfilController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = buscarUsuario(principal);

        if (usuario == null) {
            return "redirect:/login?error";
        }

        model.addAttribute("usuario", usuario);

        return "account/perfil";
    }

    @GetMapping("/perfil/editar")
    public String mostrarFormularioEdicion(
            Principal principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = buscarUsuario(principal);

        if (usuario == null) {
            return "redirect:/login?error";
        }

        PerfilForm formulario = new PerfilForm();
        formulario.setNombre(usuario.getNombre());
        formulario.setTelefono(usuario.getTelefono());
        formulario.setProvincia(usuario.getProvincia());
        formulario.setCanton(usuario.getCanton());
        formulario.setDistrito(usuario.getDistrito());
        formulario.setDireccion(usuario.getDireccion());

        model.addAttribute("usuario", usuario);
        model.addAttribute("perfilForm", formulario);

        return "account/editar-perfil";
    }

    @PostMapping("/perfil/editar")
    public String guardarCambios(
            Principal principal,
            @Valid @ModelAttribute("perfilForm") PerfilForm formulario,
            BindingResult resultado,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = buscarUsuario(principal);

        if (usuario == null) {
            return "redirect:/login?error";
        }

        model.addAttribute("usuario", usuario);

        if (resultado.hasErrors()) {
            return "account/editar-perfil";
        }

        usuario.setNombre(formulario.getNombre().trim());
        usuario.setTelefono(limpiar(formulario.getTelefono()));
        usuario.setProvincia(limpiar(formulario.getProvincia()));
        usuario.setCanton(limpiar(formulario.getCanton()));
        usuario.setDistrito(limpiar(formulario.getDistrito()));
        usuario.setDireccion(limpiar(formulario.getDireccion()));

        usuarioRepository.save(usuario);

        return "redirect:/perfil?actualizado";
    }

    private Usuario buscarUsuario(Principal principal) {
        return usuarioRepository
                .findByCorreoIgnoreCase(principal.getName())
                .orElse(null);
    }

    private String limpiar(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }

    public static class PerfilForm {

        @NotBlank(message = "El nombre completo es obligatorio.")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
        private String nombre;

        @NotBlank(message = "El teléfono o WhatsApp es obligatorio.")
        @Size(max = 30, message = "El teléfono no puede superar los 30 caracteres.")
        private String telefono;

        @Size(max = 60, message = "La provincia no puede superar los 60 caracteres.")
        private String provincia;

        @Size(max = 80, message = "El cantón no puede superar los 80 caracteres.")
        private String canton;

        @Size(max = 80, message = "El distrito no puede superar los 80 caracteres.")
        private String distrito;

        @Size(max = 500, message = "La dirección no puede superar los 500 caracteres.")
        private String direccion;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getCanton() {
            return canton;
        }

        public void setCanton(String canton) {
            this.canton = canton;
        }

        public String getDistrito() {
            return distrito;
        }

        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
    }
}
