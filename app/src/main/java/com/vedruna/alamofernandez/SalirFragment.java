package com.vedruna.alamofernandez;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Clase que representa la funcionalidad de salir de la aplicación.
 * Al hacer clic en el botón, se finaliza la actividad actual y se cierra la aplicación.
 *
 * @author Ricardo Alamo
 */
public class SalirFragment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_salir);

        // Obtener referencia al botón de salir
        Button salirButton = findViewById(R.id.salirButton);

        // Configurar el evento clic del botón
        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finalizar todas las actividades asociadas a esta aplicación
                finishAffinity();
            }
        });
    }
}
