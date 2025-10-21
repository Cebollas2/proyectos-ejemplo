package com.magnolia.cione.dto;

public class DataDTO {
    private String jwt;

    // Getters y Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "Data{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}