package com.example.trainrail;


        import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    CustomAdapter(Context context,List<RowItem> rowItems){
        this.context=context;
        this.rowItems=rowItems;
    }
    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder{
        ImageView trainImage;
        TextView trainName;
        TextView trainRoute;
        TextView trainTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        LayoutInflater mInflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_item,null);
            holder=new ViewHolder();

            holder.trainName=(TextView)convertView.findViewById(R.id.trainName);
            holder.trainImage=(ImageView) convertView.findViewById(R.id.trainImage);
            holder.trainRoute=(TextView)convertView.findViewById(R.id.trainRoute);
            holder.trainTime=(TextView)convertView.findViewById(R.id.trainTime);

            RowItem row_pos=rowItems.get(position);

            holder.trainImage.setImageResource(row_pos.getPicId());
            holder.trainName.setText(row_pos.getTrainName());
            holder.trainRoute.setText(row_pos.getRoute());
            holder.trainTime.setText(row_pos.getTime());
        }
        return convertView;
    }
}

