package com.navdeep.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    ArrayList<String> titleList;
    ArrayList<Integer> colorList=new ArrayList<>();
    List<ListObject> listObjects=new ArrayList<>();
    int position=0;
    int colorPos=0;
    FloatingActionButton addButton;
    LinearLayout list;
    TextView hintTextView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindViewById();
        OnClickListener();
        ColorList();
        TitleList();


        if(titleList.size()==0)
        {
            //Show hint Text View
            hintTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            //Hide hint Text View
            hintTextView.setVisibility(View.GONE);

            //Make ArrayList For List
            for(int i=0;i<titleList.size();i++)
            {
                ListObject tempListObject =new ListObject(titleList.get(i),colorList.get(colorPos));
                listObjects.add(tempListObject);
                colorPos=(colorPos+1)%6;
            }

            //Initiate Adapter for List
            listAdapter=new ListAdapter(getApplicationContext(),R.layout.row_list,listObjects);

            //Empty list
            if(list.getChildCount()!=0)
            {
                list.removeAllViews();
            }

            //Inflate the List
            for(int i=0;i<listObjects.size();i++)
            {
                final int itemPosition=position;
                final View listItem=listAdapter.getView(position++,null,null);
                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,NoteActivity.class);
                        intent.putExtra("ItemPosition",itemPosition);
                        startActivity(intent);
                    }
                });
                list.addView(listItem);
            }
        }
    }

    public void FindViewById()
    {
        addButton=findViewById(R.id.addButton);
        list=findViewById(R.id.list);
        hintTextView=findViewById(R.id.homeTextView);
    }

    public void OnClickListener()
    {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NoteActivity.class);
                intent.putExtra("ItemPosition",titleList.size());
                startActivity(intent);
            }
        });
    }

    public void ColorList()
    {
        colorList.add(Color.parseColor("#FFDF00"));
        colorList.add(Color.parseColor("#FF00C4"));
        colorList.add(Color.parseColor("#037752"));
        colorList.add(Color.parseColor("#FD1F1F"));
        colorList.add(Color.parseColor("#6E6EF6"));
        colorList.add(Color.parseColor("#2B8279"));
    }

    public void TitleList()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(getPackageName(),MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("TitleList", null);
        if(json==null)
        {
            titleList=new ArrayList<>();
        }
        else
        {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            titleList=gson.fromJson(json, type);
        }
    }
}
