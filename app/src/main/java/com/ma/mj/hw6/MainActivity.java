package com.ma.mj.hw6;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<Info>store_info;
    CustomAdapter adapter;
    Button choose;
    Button delete;
    CheckBox checkBox;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose = (Button)findViewById(R.id.bChoose);
        delete = (Button)findViewById(R.id.bDelete);
        EditText et = (EditText)findViewById(R.id.editText);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString().toLowerCase(Locale.getDefault());
                //adapter.setArrayList(store_info);
                adapter.filter(search);
//                else {
//                    adapter.setArrayList(store_info);
//                    adapter.notifyDataSetChanged();
//                }
            }
        });

        store_info = new ArrayList<>();
        setListView();
    }

    public void setListView(){
        listview = (ListView)findViewById(R.id.listview);

        adapter = new CustomAdapter(this, store_info);
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newIntent = new Intent(MainActivity.this, Main3Activity.class);
                newIntent.putExtra("data", store_info.get(position));
                startActivityForResult(newIntent,20);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                return false;
            }
        });
    }
    public void onClick(View v){
        if(v.getId() == R.id.bAdd) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent,10);
        }
        else if(v.getId() == R.id.bSortN){
            adapter.setAscSort(true);
        }
        else if(v.getId() == R.id.bStortK){
            adapter.setAscSort(false);
        }
        else if(v.getId() == R.id.bChoose){
            adapter.showCheckBox(true);
            choose.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.VISIBLE);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                        adapter.checking.set(position,"false");
                    }
                    else{
                        checkBox.setChecked(true);
                        adapter.checking.set(position,"true");
                    }
                }
            });
        }
        else if(v.getId() == R.id.bDelete){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            int i = 0, check = 0;
            do{
                check++;
            }while(adapter.checking.get(i++) == "true");
            if(i != adapter.checking.size()-1) {

                dialog.setTitle("삭제여부 최종 확인")
                        .setMessage("정말로 삭제 하시겠습니까?")
                        .setNegativeButton("닫기", null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.showList.clear();
                                for (int i = 0; i < adapter.checking.size(); i++) {
                                    if (adapter.checking.get(i).equals("true")) {
                                        store_info.remove(i);
                                        adapter.checking.remove(i);
                                        i--;
                                        count--;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                adapter.showList.addAll(store_info);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
            adapter.showCheckBox(false);
            choose.setVisibility(View.VISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 2) {
            Info info = data.getParcelableExtra("store_info");
            store_info.add(info);
            Log.i("storeinfo",store_info.toString());
            count++;
            adapter.notifyDataSetChanged();
            adapter.showList.clear();
            adapter.showList.addAll(store_info);

        }
    }
}
