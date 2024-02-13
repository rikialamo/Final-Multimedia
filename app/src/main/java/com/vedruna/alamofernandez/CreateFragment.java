package com.vedruna.alamofernandez;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vedruna.alamofernandez.DTO.ProductoDTO;
import com.vedruna.alamofernandez.interfaces.CRUDInterface;
import com.vedruna.alamofernandez.model.Producto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento utilizado para crear un nuevo producto.
 *
 * Permite al usuario ingresar información sobre el nuevo producto, como nombre, precio, descripción y foto,
 * y enviarla al servidor utilizando Retrofit para realizar la operación de creación.
 *
 * @author Ricardo Alamo
 */
public class CreateFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextDescripcion;
    private EditText editTextFoto;

    CRUDInterface crudInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contador, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextFoto = view.findViewById(R.id.editTextFoto);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextName.getText().toString().isEmpty()
                        && !editTextPrice.getText().toString().isEmpty()){

                    ProductoDTO dto = new ProductoDTO(editTextName.getText().toString()
                            , Float.parseFloat(editTextPrice.getText().toString()),
                            editTextDescripcion.getText().toString(),
                            editTextFoto.getText().toString());


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.105:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    crudInterface = retrofit.create(CRUDInterface.class);
                    Call<Producto> call = crudInterface.create(dto);
                    call.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                            if(!response.isSuccessful()){
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                        response.message(),
                                        Toast.LENGTH_LONG);
                                toast.show();
                                Log.e("Response err: ", response.message());
                                return;
                            }
                            Producto producto = response.body();
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    producto.getName()+" create!!",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {

                        }
                    });

                }else {
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
