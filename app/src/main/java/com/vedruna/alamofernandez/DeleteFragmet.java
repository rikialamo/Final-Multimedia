package com.vedruna.alamofernandez;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vedruna.alamofernandez.interfaces.CRUDInterface;
import com.vedruna.alamofernandez.model.Producto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteFragmet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragmet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listHome;
    CRUDInterface crudInterface;
    List<Producto> productos;

    public DeleteFragmet() {
        // Required empty public constructor
    }

    public static DeleteFragmet newInstance(String param1, String param2) {
        DeleteFragmet fragment = new DeleteFragmet();
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
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        listHome = view.findViewById(R.id.listHome);
        getAll();
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position+1);
            }
        });
        return view;
    }


    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.105:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Producto>> call = crudInterface.getAll();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                System.out.println("hola");
                if (response.isSuccessful()) {
                    System.out.println("adios");

                    productos = response.body();
                    ArrayList<String> mainItems = new ArrayList<>();
                    ArrayList<String> subItems = new ArrayList<>();

                    for(Producto prod : productos){
                        mainItems.add(prod.getName());
                        subItems.add("Precio: "+prod.getPrice() + "€");
                    }

                    CustomAdapter adapter = new CustomAdapter(getContext(), mainItems, subItems);
                    listHome.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

    public void delete(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.105:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        System.out.println(id);
        Call <Producto> call = crudInterface.delete(id-1);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                Producto producto = response.body();
                System.out.println(response.isSuccessful());
                System.out.println(producto);
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            response.message(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                producto = response.body();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        producto.getName()+" deleted!!",
                        Toast.LENGTH_LONG);
                toast.show();
                getAll();
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

            }
        });
    }
}