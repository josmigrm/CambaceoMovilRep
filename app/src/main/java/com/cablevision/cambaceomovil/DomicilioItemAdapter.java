package com.cablevision.cambaceomovil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cablevision.cambaceomovil.dto.Domicilio;

import java.util.ArrayList;

/**
 * Created by Mike on 6/26/15.
 */
public class DomicilioItemAdapter extends ArrayAdapter<Domicilio> implements Filterable{

    private Context context;
    private ArrayList<Domicilio> domicilioListValues;
    private ArrayList<Domicilio> origDomicilioListValues;
    private DomicilioFilter domicilioFilter;

    public DomicilioItemAdapter(Context context, int resource, ArrayList<Domicilio> objects) {
        super(context, resource, objects);
        this.context = context;
        this.domicilioListValues = new ArrayList<Domicilio>();
        this.domicilioListValues.addAll(objects);
        this.origDomicilioListValues = new ArrayList<Domicilio>();
        this.origDomicilioListValues.addAll(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.domicilio_list_item, parent, false);

        ((TextView) rowView.findViewById(R.id.textNumExt)).setText(domicilioListValues.get(position).getNum_Ext());
        ((TextView) rowView.findViewById(R.id.textNumInt)).setText(domicilioListValues.get(position).getNum_Int());
        ((TextView) rowView.findViewById(R.id.textEdificio)).setText(domicilioListValues.get(position).getEdificio());
        ((TextView) rowView.findViewById(R.id.textDepto)).setText(domicilioListValues.get(position).getDepartamento());
        ((TextView) rowView.findViewById(R.id.textOrientacion)).setText(domicilioListValues.get(position).getOrientacion());

        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (domicilioFilter == null)
            domicilioFilter = new DomicilioFilter();

        return domicilioFilter;
    }




    private class DomicilioFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                synchronized (this) {
                    results.values = origDomicilioListValues;
                    results.count = origDomicilioListValues.size();
                }
            }
            else {
                // We perform filtering operation
                ArrayList<Domicilio> domicilioFilteredList = new ArrayList<Domicilio>();

                for (Domicilio d : origDomicilioListValues) {
                    if (d.toString().toLowerCase().contains(constraint.toString().toLowerCase()))
                        domicilioFilteredList.add(d);
                }

                results.values = domicilioFilteredList;
                results.count = domicilioFilteredList.size();

            }
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            domicilioListValues = (ArrayList<Domicilio>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = domicilioListValues.size(); i < l; i++)
                add(domicilioListValues.get(i));
            notifyDataSetInvalidated();
        }
    }

}
