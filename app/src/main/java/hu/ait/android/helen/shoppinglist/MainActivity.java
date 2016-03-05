package hu.ait.android.helen.shoppinglist;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import hu.ait.android.helen.shoppinglist.adapter.ShoppingListAdapter;
import hu.ait.android.helen.shoppinglist.data.Item;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_NEW_ITEM_CODE = 100;
    public static final int REQUEST_EDIT_ITEM_CODE = 101;
    public static final int CONTEXT_ACTION_DELETE = 10;
    public static final int CONTEXT_ACTION_EDIT = 11;


    private ListView listView;
    private ShoppingListAdapter adapter;
    private List<Item> shoppingList;

    //private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView title = (ImageView) findViewById(R.id.titleText);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.save_anim);
        title.startAnimation(anim);

        shoppingList = Item.listAll(Item.class);

        listView = (ListView) findViewById(android.R.id.list);
        adapter = new ShoppingListAdapter(this, shoppingList);
        listView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateItemActivity();
            }//onClick
        });

        registerForContextMenu(listView);
    }//onCreate

    private void showCreateItemActivity() {
        Intent i = new Intent();
        i.setClass(this, CreateItemActivity.class);
        startActivityForResult(i, REQUEST_NEW_ITEM_CODE);
    }//showCreateItemActivity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode) {
            case RESULT_OK:
                if(requestCode == REQUEST_NEW_ITEM_CODE) {
                    Item item = (Item) data.getSerializableExtra(CreateItemActivity.KEY_ITEM);

                    item.save();

                    adapter.addItem(item);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Item added to the list!", Toast.LENGTH_LONG).show();
                }//if
                else if(requestCode == REQUEST_EDIT_ITEM_CODE) {
                    int index = data.getIntExtra(CreateItemActivity.KEY_EDIT_ID, -1);
                    if(index != -1){
                        Item item = (Item) data.getSerializableExtra(CreateItemActivity.KEY_ITEM);
                        item.setId(adapter.getItem(index).getId());
                        item.save();

                        adapter.updateItem(index,
                                (Item) data.getSerializableExtra(CreateItemActivity.KEY_ITEM));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Item updated!", Toast.LENGTH_LONG).show();
                    }//if
                }//else if
                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }//switch
    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopping_list, menu);
        return true;
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_delete_all){

            for(int i = adapter.getCount(); i > 0; i--){
                adapter.removeAll();
                adapter.notifyDataSetChanged();
            }
        }//else if

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Menu");
        menu.add(0, CONTEXT_ACTION_DELETE, 0, "Delete");
        menu.add(0, CONTEXT_ACTION_EDIT, 0, "Edit");
    }//onCreateContextMenu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == CONTEXT_ACTION_DELETE) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();
            adapter.removeItem(info.position);

            Item listItem = adapter.getItem(info.position);
            listItem.delete();
            adapter.notifyDataSetChanged();
        }//if
        if(item.getItemId() == CONTEXT_ACTION_EDIT) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();

            Item selectedItem = adapter.getItem(info.position);

            Intent i = new Intent();
            i.setClass(this, CreateItemActivity.class);
            i.putExtra(CreateItemActivity.KEY_EDIT_ITEM, selectedItem);
            i.putExtra(CreateItemActivity.KEY_EDIT_ID, info.position);
            startActivityForResult(i, REQUEST_EDIT_ITEM_CODE);
            adapter.notifyDataSetChanged();
        }
        else {
            return false;
        }//else


        return true;
    }//onContextItemSelected

}//MainActivity


