package com.inksticker.service.impl;

import com.inksticker.entity.Usuario;
import com.inksticker.repository.UsuarioRepository;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarUsuario(
            String nombre,
            String correo,
            String contrasena) {

        String nombreNormalizado = nombre == null ? "" : nombre.trim();
        String correoNormalizado = correo == null
                ? ""
                : correo.trim().toLowerCase(Locale.ROOT);

        if (nombreNormalizado.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        if (correoNormalizado.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo electrónico es obligatorio.");
        }

        if (contrasena == null || contrasena.length() < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres.");
        }

        if (usuarioRepository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe una cuenta registrada con ese correo.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombreNormalizado);
        usuario.setCorreo(correoNormalizado);
        usuario.setContrasena(passwordEncoder.encode(contrasena));

        return usuarioRepository.save(usuario);
    }
}
