package com.inksticker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String tipo = "HERO";

    @NotBlank(message = "El título del banner es obligatorio.")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String titulo;

    @Size(max = 300)
    @Column(length = 300)
    private String subtitulo;

    @Size(max = 60)
    @Column(name = "texto_boton", length = 60)
    private String textoBoton;

    @Size(max = 255)
    @Column(name = "url_boton", length = 255)
    private String urlBoton;

    @NotBlank(message = "La imagen del banner es obligatoria.")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String imagen;

    @Min(1)
    @Column(nullable = false)
    private int orden = 1;

    @Column(nullable = false)
    private boolean activo = true;

    public Banner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }

    public String getUrlBoton() {
        return urlBoton;
    }

    public void setUrlBoton(String urlBoton) {
        this.urlBoton = urlBoton;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}