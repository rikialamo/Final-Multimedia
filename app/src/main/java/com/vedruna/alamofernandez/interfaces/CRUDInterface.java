package com.vedruna.alamofernandez.interfaces;

import com.vedruna.alamofernandez.DTO.ProductoDTO;
import com.vedruna.alamofernandez.model.Producto;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Esta interfaz define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la gestión de productos.
 * Utiliza Retrofit para realizar solicitudes HTTP a un servidor que proporciona servicios RESTful.
 *
 * @author Ricardo Alamo
 */
public interface CRUDInterface {

    /**
     * Obtiene todos los productos.
     *
     * @return Una llamada que devuelve una lista de productos.
     */
    @GET("producto")
    Call<List<Producto>> getAll();

    /**
     * Crea un nuevo producto.
     *
     * @param dto El DTO (Data Transfer Object) que contiene la información del producto a crear.
     * @return Una llamada que devuelve el producto creado.
     */
    @POST("producto")
    Call<Producto> create(@Body ProductoDTO dto);

    /**
     * Edita un producto existente.
     *
     * @param id El identificador del producto a editar.
     * @param dto El DTO que contiene la información actualizada del producto.
     * @return Una llamada que devuelve el producto editado.
     */
    @PUT("producto/{id}")
    Call<Producto> edit(@Path("id") int id, @Body ProductoDTO dto);

    /**
     * Elimina un producto.
     *
     * @param id El identificador del producto a eliminar.
     * @return Una llamada que devuelve el producto eliminado.
     */
    @DELETE("producto/{id}")
    Call<Producto> delete(@Path("id") int id);
}
