package com.vedruna.alamofernandez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Adaptador personalizado para mostrar una lista de elementos en la pantalla de inicio.
 *
 * Este adaptador toma cuatro ArrayLists de cadenas: uno para los elementos principales, uno para los elementos secundarios,
 * uno para los elementos adicionales y otro para las URLs de las imágenes a mostrar junto a los elementos.
 * Cada elemento principal se muestra como texto grande, cada elemento secundario se muestra como texto pequeño debajo,
 * cada elemento adicional se muestra como texto aún más pequeño y la imagen asociada se carga utilizando la biblioteca Glide.
 *
 * @author Ricardo Alamo
 */
public class CustomAdapterHome extends ArrayAdapter<String> {

    private Context mContext; // Contexto de la aplicación
    private ArrayList<String> mMainItems; // Lista de elementos principales
    private ArrayList<String> mSubItems; // Lista de elementos secundarios
    private ArrayList<String> mAdditionalItems; // Lista de elementos adicionales
    private ArrayList<String> mDrawables; // Lista de URLs de las imágenes asociadas a los elementos

    /**
     * Constructor para crear un nuevo adaptador personalizado.
     *
     * @param context          El contexto de la aplicación.
     * @param mainItems        Lista de elementos principales.
     * @param subItems         Lista de elementos secundarios.
     * @param additionalItems  Lista de elementos adicionales.
     * @param drawables        Lista de URLs de las imágenes asociadas a los elementos.
     */
    public CustomAdapterHome(Context context, ArrayList<String> mainItems, ArrayList<String> subItems, ArrayList<String> additionalItems, ArrayList<String> drawables) {
        super(context, 0, mainItems);
        mContext = context;
        mMainItems = mainItems;
        mSubItems = subItems;
        mAdditionalItems = additionalItems;
        mDrawables = drawables;
    }

    /**
     * Método llamado para obtener la vista de un elemento en la posición especificada en la lista.
     *
     * @param position    La posición del elemento en la lista.
     * @param convertView La vista que se está reciclando (puede ser nula).
     * @param parent      El ViewGroup al que pertenece la vista.
     * @return La vista del elemento en la posición dada.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_home, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = convertView.findViewById(R.id.main_text);
            viewHolder.subText = convertView.findViewById(R.id.sub_text);
            viewHolder.additionalText = convertView.findViewById(R.id.additional_text);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mainText.setText(mMainItems.get(position));
        viewHolder.subText.setText(mSubItems.get(position));
        viewHolder.additionalText.setText(mAdditionalItems.get(position));
        loadImage(this.getContext(), mDrawables.get(position), viewHolder.imageView);

        return convertView;
    }

    /**
     * Método estático para cargar una imagen desde una URL utilizando la biblioteca Glide.
     *
     * @param context   El contexto de la aplicación.
     * @param imageUrl  La URL de la imagen a cargar.
     * @param imageView El ImageView donde se mostrará la imagen cargada.
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Cache la imagen para su uso futuro

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * Clase interna para almacenar vistas de elementos y evitar llamadas innecesarias a findViewById().
     */
    private static class ViewHolder {
        TextView mainText; // Vista de texto para el elemento principal
        TextView subText; // Vista de texto para el elemento secundario
        TextView additionalText; // Vista de texto para el elemento adicional
        ImageView imageView; // Vista de imagen para la imagen asociada al elemento
    }
}
