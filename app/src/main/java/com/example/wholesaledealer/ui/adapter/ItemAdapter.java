package com.example.wholesaledealer.ui.adapter;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wholesaledealer.R;
import com.example.wholesaledealer.data.WholesaleItem;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<WholesaleItem> list;
    public ItemAdapter(List<WholesaleItem> list) { this.list = list; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        WholesaleItem it = list.get(pos);
        h.name.setText(it.name);
        h.model.setText(it.model);
        h.profit.setText("Profit: Rs. " + it.profit);
        h.customer.setText("Cust: " + it.customer);
        h.phone.setText(it.phone);
        h.image.setImageBitmap(it.image);
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, model, profit, customer, phone;
        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.itemImage);
            name = v.findViewById(R.id.itemName);
            model = v.findViewById(R.id.itemModel);
            profit = v.findViewById(R.id.itemProfit);
            customer = v.findViewById(R.id.itemCustomer);
            phone = v.findViewById(R.id.itemPhone);
        }
    }
}