package com.beitshean.mylaundry;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderList extends ArrayAdapter<Order> {

    private Activity context;
    private List<Order> order_list;

    public OrderList(Activity context, List<Order> order_list) {

        super(context, R.layout.list_layout, order_list);
        this.context = context;
        this.order_list = order_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View list_view_item = inflater.inflate(R.layout.list_layout, null, true);

        TextView weight_text_view = list_view_item.findViewById(R.id.ll_weight_text_view);
        TextView is_delivery_text_view = list_view_item.findViewById(R.id.ll_is_delivery_text_view);
        TextView is_ironing_text_view = list_view_item.findViewById(R.id.ll_is_ironing_text_view);
        TextView order_status_text_view = list_view_item.findViewById(R.id.ll_order_status_text_view);
        TextView price_text_view = list_view_item.findViewById(R.id.ll_price_text_view);

        return list_view_item;
    }
}
