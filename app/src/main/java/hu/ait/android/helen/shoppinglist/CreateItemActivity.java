package hu.ait.android.helen.shoppinglist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hu.ait.android.helen.shoppinglist.data.Item;


public class CreateItemActivity extends ActionBarActivity {

    public static final String KEY_EDIT_ITEM = "KEY_EDIT_ITEM";
    public static final String KEY_EDIT_ID = "KEY_EDIT_ID";
    public static final String KEY_ITEM = "KEY_ITEM";

    private boolean inEditMode = false;
    private Spinner spinnerItemType;
    private EditText etItem;
    private EditText etItemDesc;
    private EditText etPrice;
    private EditText etStoreName;
    private CheckBox checkPurchased;
    private int itemToEditId = 0;
    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItem = (EditText) findViewById(R.id.etItemName);
        etItemDesc = (EditText) findViewById(R.id.etDescription);
        etPrice = (EditText) findViewById(R.id.etPrice);
        checkPurchased = (CheckBox) findViewById(R.id.checkPurchased);
        etStoreName = (EditText) findViewById(R.id.etStoreName);

        if(getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(KEY_EDIT_ITEM)) {
            inEditMode = true;

            itemToEdit = (Item) getIntent().getSerializableExtra(KEY_EDIT_ITEM);
            itemToEditId = getIntent().getIntExtra(KEY_EDIT_ID, -1);

            spinnerItemType.setSelection(itemToEdit.getItemType().getValue());
            etItem.setText(itemToEdit.getItemName());
            etItemDesc.setText(itemToEdit.getDescription());
        }//if

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inEditMode) {
                    updateItem();
                }//if
                else {
                    saveItem();
                }//else
            }//onClick
        });

    }//onCreate

    private void saveItem() {
        Intent intentResult = new Intent();
        intentResult.putExtra(KEY_ITEM,
                new Item(Item.ItemType.fromtInt(spinnerItemType.getSelectedItemPosition()),
                        etItem.getText().toString(), etItemDesc.getText().toString(),
                        etPrice.getText().toString(),checkPurchased.isChecked(),
                        etStoreName.getText().toString()));

        setResult(RESULT_OK, intentResult);
        finish();
    }//savePlace

    private void updateItem() {
        itemToEdit.setItemName(etItem.getText().toString());
        itemToEdit.setDescription(etItemDesc.getText().toString());
        itemToEdit.setItemType(Item.ItemType.fromtInt(spinnerItemType.getSelectedItemPosition()));
        itemToEdit.setPrice(etPrice.getText().toString());
        itemToEdit.setPurchased(checkPurchased.isChecked());
        itemToEdit.setStoreName(etStoreName.getText().toString());

        Intent intentResult = new Intent();
        intentResult.putExtra(KEY_ITEM, itemToEdit);
        intentResult.putExtra(KEY_EDIT_ID, itemToEditId);
        setResult(RESULT_OK, intentResult);
        finish();
    }//updateItem

}//CreateItemActivity
