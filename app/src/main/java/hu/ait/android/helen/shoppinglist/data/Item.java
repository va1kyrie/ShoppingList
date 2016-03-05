package hu.ait.android.helen.shoppinglist.data;

import com.orm.SugarRecord;

import java.io.Serializable;

import hu.ait.android.helen.shoppinglist.R;

/**
 * Created by Helen on 3/18/2015.
 */
public class Item extends SugarRecord<Item> implements Serializable {

    private ItemType itemType;
    private String itemName;
    private String description;
    private String price;
    private boolean purchased;
    private String storeName;

    public Item() {

    }//Item

    public enum ItemType{
        FOOD(0, R.drawable.food),
        CLOTHES(2, R.drawable.clothes2),
        MUSIC(3, R.drawable.music),
        BOOKS(1, R.drawable.books);

        private int value;
        private int iconId;

        private ItemType(int value, int iconId){
            this.value = value;
            this.iconId = iconId;
        }//IconType

        public static ItemType fromtInt(int value){
            for (ItemType i : ItemType.values()) {
                if(i.value == value){
                    return i;
                }//if
            }//for

            return BOOKS;
        }//fromInt

        public int getIconId() { return iconId; }//getIconId

        public int getValue() { return value; }//getValue

    }//ItemType


    public Item(ItemType itemType, String itemName,
                String description, String price, boolean purchased, String storeName) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.purchased = purchased;
        this.storeName = storeName;
    }//Item

    public String getItemName() { return itemName; }//getItemName

    public String getDescription() { return description; }//getDescription

    public String getPrice() { return price; }//getPrice

    public boolean isPurchased() { return purchased; }//isPurchased

    public String getStoreName() { return storeName; }//getStoreName

    public ItemType getItemType() { return itemType; }//getItemType

    public void setItemType(ItemType itemType) { this.itemType = itemType; }//setItemType

    public void setItemName(String itemName) { this.itemName = itemName; }//setItemName

    public void setDescription(String description) { this.description = description; }//setDescription

    public void setPrice(String  price) { this.price = price; }//setPrice

    public void setPurchased(boolean purchased) { this.purchased = purchased; }//setPurchased

    public void setStoreName(String storeName) { this.storeName = storeName; }//setStoreName

}//Item
