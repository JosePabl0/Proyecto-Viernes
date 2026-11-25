package com.inksticker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "configuracion_estudio")
public class ConfiguracionEstudio {

    @Id
    private Long id = 1L;

    @NotBlank(message = "El nombre del estudio es obligatorio.")
    @Size(max = 120)
    @Column(name = "nombre_estudio", nullable = false, length = 120)
    private String nombreEstudio;

    @NotBlank(message = "El número de WhatsApp es obligatorio.")
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String whatsapp;

    @Size(max = 300)
    @Column(name = "mensaje_whatsapp", length = 300)
    private String mensajeWhatsapp;

    @Size(max = 150)
    @Column(name = "correo_contacto", length = 150)
    private String correoContacto;

    @Size(max = 300)
    @Column(name = "direccion_fisica", length = 300)
    private String direccionFisica;

    @Column(name = "mapa_embed_url", columnDefinition = "TEXT")
    private String mapaEmbedUrl;

    @Size(max = 255)
    @Column(name = "horario_atencion", length = 255)
    private String horarioAtencion;

    @Size(max = 255)
    @Column(name = "instagram_url", length = 255)
    private String instagramUrl;

    @Size(max = 255)
    @Column(name = "facebook_url", length = 255)
    private String facebookUrl;

    @Size(max = 255)
    @Column(name = "tiktok_url", length = 255)
    private String tiktokUrl;

    public ConfiguracionEstudio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEstudio() {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio) {
        this.nombreEstudio = nombreEstudio;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getMensajeWhatsapp() {
        return mensajeWhatsapp;
    }

    public void setMensajeWhatsapp(String mensajeWhatsapp) {
        this.mensajeWhatsapp = mensajeWhatsapp;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getDireccionFisica() {
        return direccionFisica;
    }

    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    public String getMapaEmbedUrl() {
        return mapaEmbedUrl;
    }

    public void setMapaEmbedUrl(String mapaEmbedUrl) {
        this.mapaEmbedUrl = mapaEmbedUrl;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTiktokUrl() {
        return tiktokUrl;
    }

    public void setTiktokUrl(String tiktokUrl) {
        this.tiktokUrl = tiktokUrl;
    }
}