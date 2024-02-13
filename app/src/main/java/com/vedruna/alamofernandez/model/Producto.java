package com.vedruna.alamofernandez.model;

public class Producto {
    private int id;
    private String name;
    private float price;
    private String descripción;
    private String foto;

    public Producto(){}

    public Producto(int id, String name, float price, String descripción, String foto) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.descripción = descripción;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", descripción='" + descripción + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
