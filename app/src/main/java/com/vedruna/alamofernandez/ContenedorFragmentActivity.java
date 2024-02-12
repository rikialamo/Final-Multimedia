package com.vedruna.alamofernandez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContenedorFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenedorfragmentactivity);

/*
        String username = getIntent().getStringExtra("usuario");

        TextView nombre = findViewById(R.id.ByUser);

        nombre.setText("Bienvenido, " + username);

 */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.homeFragment);
            } else if (item.getItemId() == R.id.navigation_contador) {
                navController.navigate(R.id.contadorFragment);
            } else if (item.getItemId() == R.id.navigation_api){
                navController.navigate(R.id.apiFragment);
            } else if (item.getItemId() == R.id.navigation_delete){
                navController.navigate(R.id.delete);
            } else if (item.getItemId() == R.id.navigation_salir){
                navController.navigate(R.id.salirFragment);
            }
            return true;
        });

        /*
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_home);
        NavController navController = navHostFragment.getNavController();


        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.homeFragment);
            } else if (item.getItemId() == R.id.navigation_contador) {
                navController.navigate(R.id.contadorFragment);
            }
            return true;
        });

         */
    }
}
