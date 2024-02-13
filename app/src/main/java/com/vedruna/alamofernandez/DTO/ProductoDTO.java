package com.vedruna.alamofernandez.DTO;

/**
 * Esta clase representa un ProductoDTO (Data Transfer Object) que contiene información sobre un producto.
 *
 * @author Ricardo Alamo
 */
public class ProductoDTO {
    private String name; // Nombre del producto
    private float price; // Precio del producto
    private String descripción; // Descripción del producto
    private String foto; // URL de la foto del producto

    /**
     * Constructor para crear un nuevo ProductoDTO.
     *
     * @param name         El nombre del producto.
     * @param price        El precio del producto.
     * @param descripción  La descripción del producto.
     * @param foto         La URL de la foto del producto.
     */
    public ProductoDTO(String name, float price, String descripción, String foto) {
        this.name = name;
        this.price = price;
        this.descripción = descripción;
        this.foto = foto;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param name El nuevo nombre del producto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Establece el precio del producto.
     *
     * @param price El nuevo precio del producto.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return La descripción del producto.
     */
    public String getDescripción() {
        return descripción;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripción La nueva descripción del producto.
     */
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    /**
     * Obtiene la URL de la foto del producto.
     *
     * @return La URL de la foto del producto.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Establece la URL de la foto del producto.
     *
     * @param foto La nueva URL de la foto del producto.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Sobrescribe el método toString para proporcionar una representación de cadena del objeto ProductoDTO.
     *
     * @return Una representación de cadena del objeto ProductoDTO.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", descripción='" + descripción + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
