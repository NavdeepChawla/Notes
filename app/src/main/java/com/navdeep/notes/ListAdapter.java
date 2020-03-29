package com.navdeep.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ListObject>
{
    private List<ListObject> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private int layoutResource;

    public static class ViewHolder
    {
        TextView listTextView;
        CardView listCardView;
        ViewHolder (View view)
        {
            this.listCardView=view.findViewById(R.id.listCardView);
            this.listTextView=view.findViewById(R.id.listTextView);
        }
    }

    // data is passed into the constructor
    ListAdapter(Context context, int resource, List<ListObject> tempData)
    {
        super(context, R.layout.row_list);
        this.data = tempData;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.layoutResource=resource;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,ViewGroup parent)
    {
        View view;
        view = layoutInflater.inflate(layoutResource,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        ListObject tempData=data.get(position);
        viewHolder.listTextView.setText(tempData.Title);
        viewHolder.listCardView.setCardBackgroundColor(tempData.CardColor);
        view.setTag(viewHolder);
        return view;
    }
}
