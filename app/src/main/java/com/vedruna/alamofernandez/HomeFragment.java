package com.vedruna.alamofernandez;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vedruna.alamofernandez.interfaces.CRUDInterface;
import com.vedruna.alamofernandez.model.Producto;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento utilizado para mostrar una lista de productos en la pantalla de inicio.
 *
 * Este fragmento muestra una lista de productos obtenidos del servidor y los muestra en un ListView.
 * Utiliza Retrofit para realizar solicitudes al servidor y obtener la lista de productos.
 *
 * @author Ricardo Alamo
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listHome; // ListView para mostrar la lista de productos
    private CRUDInterface crudInterface; // Interfaz para realizar operaciones CRUD con el servidor
    private List<Producto> productos; // Lista de productos obtenidos del servidor

    /**
     * Constructor público por defecto requerido por Fragment.
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Método de fábrica estático para crear una nueva instancia de este fragmento.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return Una nueva instancia de HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Obtener la referencia del ListView desde el diseño
        listHome = view.findViewById(R.id.listHome);
        // Obtener la lista de productos del servidor
        getAll();
        return view;
    }

    /**
     * Método privado para obtener la lista de productos del servidor.
     */
    private void getAll() {
        // Configurar Retrofit para realizar solicitudes al servidor
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.105:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Producto>> call = crudInterface.getAll(); // Crear la llamada para obtener todos los productos
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) { // Si la solicitud fue exitosa
                    productos = response.body(); // Obtener la lista de productos del cuerpo de la respuesta
                    ArrayList<String> mainItems = new ArrayList<>();
                    ArrayList<String> subItems = new ArrayList<>();
                    ArrayList<String> descripcion = new ArrayList<>();
                    ArrayList<String> foto = new ArrayList<>();
                    for (Producto prod : productos) { // Iterar sobre la lista de productos
                        mainItems.add(prod.getName()); // Agregar el nombre del producto a la lista de elementos principales
                        subItems.add("Precio: " + prod.getPrice() + "€"); // Agregar el precio del producto a la lista de elementos secundarios
                        descripcion.add(prod.getDescripción()); // Agregar la descripción del producto a la lista de descripciones
                        foto.add(prod.getFoto()); // Agregar la URL de la foto del producto a la lista de URLs de fotos
                    }
                    CustomAdapterHome adapter = new CustomAdapterHome(getContext(), mainItems, subItems, descripcion, foto); // Crear un adaptador personalizado para la lista de productos
                    listHome.setAdapter(adapter); // Establecer el adaptador en el ListView
                } else {
                    // Manejar la respuesta no exitosa si es necesario
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage()); // Manejar los errores de la solicitud
            }
        });
    }
}
