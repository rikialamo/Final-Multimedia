package com.vedruna.alamofernandez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adaptador personalizado para mostrar una lista de elementos en un ListView.
 *
 * Este adaptador toma dos ArrayLists de cadenas: uno para los elementos principales y otro para los elementos secundarios.
 * Cada elemento principal se muestra como texto grande y cada elemento secundario se muestra como texto pequeño debajo.
 *
 * @author Ricardo Alamo
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext; // Contexto de la aplicación
    private ArrayList<String> mMainItems; // Lista de elementos principales
    private ArrayList<String> mSubItems; // Lista de elementos secundarios

    /**
     * Constructor para crear un nuevo adaptador personalizado.
     *
     * @param context   El contexto de la aplicación.
     * @param mainItems Lista de elementos principales.
     * @param subItems  Lista de elementos secundarios.
     */
    public CustomAdapter(Context context, ArrayList<String> mainItems, ArrayList<String> subItems) {
        super(context, 0, mainItems);
        mContext = context;
        mMainItems = mainItems;
        mSubItems = subItems;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = convertView.findViewById(R.id.main_text);
            viewHolder.subText = convertView.findViewById(R.id.sub_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mainText.setText(mMainItems.get(position));
        viewHolder.subText.setText(mSubItems.get(position));

        return convertView;
    }

    /**
     * Clase interna para almacenar vistas de elementos y evitar llamadas innecesarias a findViewById().
     */
    private static class ViewHolder {
        TextView mainText; // Vista de texto para el elemento principal
        TextView subText; // Vista de texto para el elemento secundario
    }
}
