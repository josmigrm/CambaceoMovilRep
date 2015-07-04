package com.cablevision.cambaceomovil.dto;

import java.io.Serializable;

/**
 * Created by Mike on 7/3/15.
 */
public class Usuario implements Serializable{

    String nombre;
    String username;
    String password;
    String sessionToken;

    public Usuario() {
    }

    public Usuario(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Usuario(String nombre, String username, String password, String sessionToken) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.sessionToken = sessionToken;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

}
