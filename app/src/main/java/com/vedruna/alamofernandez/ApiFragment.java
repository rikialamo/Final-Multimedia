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
 * A simple {@link Fragment} subclass.
 * Use the {@link ApiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApiFragment extends Fragment {

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

    public ApiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApiFragment newInstance(String param1, String param2) {
        ApiFragment fragment = new ApiFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_api, container, false);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextFoto = view.findViewById(R.id.editTextFoto);
        editTextId = view.findViewById(R.id.editTextId);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextName.getText().toString().isEmpty()
                        && !editTextPrice.getText().toString().isEmpty()
                        && !editTextId.getText().toString().isEmpty()){
                    ProductoDTO dto = new ProductoDTO(editTextName.getText().toString()
                            , Float.parseFloat(editTextPrice.getText().toString()),
                            editTextDescripcion.getText().toString(),
                            editTextFoto.getText().toString());


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.105:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    crudInterface = retrofit.create(CRUDInterface.class);
                    Call<Producto> call = crudInterface.edit(Integer.parseInt(editTextId.getText().toString()), dto);
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
                                    producto.getName()+" updated!!",
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