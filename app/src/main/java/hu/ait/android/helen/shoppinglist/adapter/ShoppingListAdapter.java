package hu.ait.android.helen.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.ait.android.helen.shoppinglist.R;
import hu.ait.android.helen.shoppinglist.data.Item;

/**
 * Created by Helen on 3/19/2015.
 */
public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> shoppingList;

    public ShoppingListAdapter(Context context, List<Item> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }//ShoppingListAdapter

    public void addItem(Item item) { shoppingList.add(item); }//addItem

    public void updateItem(int index, Item item) { shoppingList.set(index, item);}//updateItem

    public void removeItem(int index) { shoppingList.remove(index); }//removeItem

    public void removeAll() { shoppingList.clear(); }//removeAll

    @Override
    public int getCount() {
        return shoppingList.size();
    }//getCount

    @Override
    public Item getItem(int position) {
        return shoppingList.get(position);
    }//getItem

    @Override
    public long getItemId(int position) {
        return position;
    }//getItemId

    static class ViewHolder {
        ImageView ivIcon;
        TextView tvItem;
        TextView tvDescription;
        TextView tvPrice;
        CheckBox cbPurchased;
        TextView storeName;
    }//ViewHolder

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_item, null);
            ViewHolder holder = new ViewHolder();
            holder.ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            holder.tvItem = (TextView) v.findViewById(R.id.tvItem);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            holder.tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            holder.cbPurchased = (CheckBox) v.findViewById(R.id.cbPurchased);
            holder.storeName = (TextView) v.findViewById(R.id.storeName);
            v.setTag(holder);
        }//if

        Item item = shoppingList.get(position);
        if(item != null){
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.tvItem.setText(item.getItemName());
            holder.tvDescription.setText(item.getDescription());
            holder.tvPrice.setText(item.getPrice());
            holder.ivIcon.setImageResource(item.getItemType().getIconId());
            holder.storeName.setText(item.getStoreName());

            holder.cbPurchased.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        holder.cbPurchased.setChecked(true);
                    }//if
                    else {
                        holder.cbPurchased.setChecked(false);
                    }//else
                }
            });
        }//if

        return v;
    }//getView



}//ShoppingListAdapter
