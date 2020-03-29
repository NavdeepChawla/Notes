package com.navdeep.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    int itemPostion;
    ArrayList<String> titleList;
    ArrayList<String> descList;
    EditText titleEditText;
    EditText descEditText;
    ImageButton backButton;
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Get ItemPosition From the List
        Intent intent=getIntent();
        itemPostion=intent.getIntExtra("ItemPosition",0);

        TitleList();
        DescList();
        FindViewByID();
        OnclickListener();

        //Check if its a new Note
        if(itemPostion!=titleList.size())
        {
            titleEditText.setText(titleList.get(itemPostion));
            descEditText.setText(descList.get(itemPostion));
        }
        else
        {
            titleEditText.requestFocus();
        }
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

    public void DescList()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(getPackageName(),MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("DescList", null);
        if(json==null)
        {
            descList=new ArrayList<>();
        }
        else
        {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            descList=gson.fromJson(json, type);
        }
    }

    public void FindViewByID()
    {
        titleEditText=findViewById(R.id.titleEditText);
        descEditText=findViewById(R.id.descEditText);
        backButton=findViewById(R.id.backButton);
        deleteButton=findViewById(R.id.deleteButton);
    }

    public void OnclickListener()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemPostion==titleList.size())
                {
                    saveTitleList();
                    saveDescList();
                }
                titleList.remove(itemPostion);
                descList.remove(itemPostion);
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String titleJson = gson.toJson(titleList);
                String descJson = gson.toJson(titleList);
                editor.putString("TitleList", titleJson);
                editor.putString("DescList", descJson);
                editor.apply();
                Intent intent=new Intent(NoteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        saveDescList();
        saveTitleList();
        Intent intent=new Intent(NoteActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void saveTitleList(){
        if(itemPostion==titleList.size())
        {
            String Title=titleEditText.getText().toString();
            titleList.add(Title);
        }
        else
        {
            String Title=titleEditText.getText().toString();
            titleList.set(itemPostion,Title);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(titleList);
        editor.putString("TitleList", json);
        editor.apply();
    }

    public void saveDescList(){
        if(itemPostion==descList.size())
        {
            String Note=descEditText.getText().toString();
            descList.add(Note);
        }
        else
        {
            String Note=descEditText.getText().toString();
            descList.set(itemPostion,Note);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(descList);
        editor.putString("DescList", json);
        editor.apply();
    }
}
