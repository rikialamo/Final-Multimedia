package com.vedruna.alamofernandez;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vedruna.alamofernandez.DTO.ProductoDTO;
import com.vedruna.alamofernandez.interfaces.CRUDInterface;
import com.vedruna.alamofernandez.model.Producto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento que permite actualizar un producto mediante la API.
 * Este fragmento se utiliza para enviar una solicitud de actualización de un producto a través de la API REST.
 * Implementa una interfaz de usuario para que el usuario ingrese los datos del producto a actualizar.
 *
 * @author Ricardo Alamo
 */
public class UpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextId;
    private EditText editTextDescripcion;
    private EditText editTextFoto;
    private Button btnUpdate;
    CRUDInterface crudInterface;

    public UpdateFragment() {
        // Constructor público requerido
    }

    /**
     * Método de fábrica para crear una nueva instancia de este fragmento.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return Una nueva instancia de UpdateFragment.
     */
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_api, container, false);

        // Inicializar vistas
        btnUpdate = view.findViewById(R.id.btnUpdate);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextFoto = view.findViewById(R.id.editTextFoto);
        editTextId = view.findViewById(R.id.editTextId);

        // Configurar el evento clic del botón de actualización
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si se han ingresado todos los campos necesarios
                if (!editTextName.getText().toString().isEmpty()
                        && !editTextPrice.getText().toString().isEmpty()
                        && !editTextId.getText().toString().isEmpty()) {
                    // Crear un DTO de Producto con los datos ingresados por el usuario
                    ProductoDTO dto = new ProductoDTO(editTextName.getText().toString(),
                            Float.parseFloat(editTextPrice.getText().toString()),
                            editTextDescripcion.getText().toString(),
                            editTextFoto.getText().toString());

                    // Configurar Retrofit para realizar la solicitud HTTP
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.105:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    crudInterface = retrofit.create(CRUDInterface.class);

                    // Enviar la solicitud de actualización a la API
                    Call<Producto> call = crudInterface.edit(Integer.parseInt(editTextId.getText().toString()), dto);
                    call.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                            if (!response.isSuccessful()) {
                                // Mostrar mensaje de error si la solicitud no fue exitosa
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                        response.message(),
                                        Toast.LENGTH_LONG);
                                toast.show();
                                Log.e("Response err: ", response.message());
                                return;
                            }
                            // Mostrar mensaje de éxito si la solicitud fue exitosa
                            Producto producto = response.body();
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    producto.getName() + " updated!!",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            // Manejar el caso de falla de la solicitud
                        }
                    });

                } else {
                    // Mostrar mensaje de advertencia si no se ingresaron todos los campos
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "Rellene todos los campos",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }

}
