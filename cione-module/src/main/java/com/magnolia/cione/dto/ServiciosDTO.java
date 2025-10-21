package com.magnolia.cione.dto;

public class ServiciosDTO {
    private boolean success;
    private DataDTO data;

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiciosDTO{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
