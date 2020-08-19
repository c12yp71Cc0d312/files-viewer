package com.example.onsitetask1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int STORAGE_PERMISSION_CODE = 1;

    private ArrayList<DirectoryCard> filesList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterClass adapter;
    private File root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            root = new File((Environment.getExternalStorageDirectory()).getAbsolutePath());

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            root = new File((getExternalFilesDir(null).getAbsolutePath()));
        }
         */

            Log.d(TAG, "onCreate: " + root.getAbsolutePath());

            ListDirectories(root);
            buildRecyclerView();
        }

    }

    private void ListDirectories(File rootFile) {
        File[] files = rootFile.listFiles();
        filesList = new ArrayList<>();
        Log.d(TAG, "ListDirectories: files size: " + files.length);
        if(files != null) {
            for (File f : files) {
                //Log.d(TAG, "ListDirectories: "+f.getAbsolutePath());
                if(f.isDirectory())
                    filesList.add(new DirectoryCard(f.getName(), f.getAbsolutePath(), "false", R.drawable.rightarrow));
                else
                    filesList.add(new DirectoryCard(f.getName(), f.getAbsolutePath(), "na"));
            }
        }
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AdapterClass(filesList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                DirectoryCard clickedCard = filesList.get(pos);
                if(!clickedCard.getOpenStatus().equals("na")) {
                    clickedCard.InvertArrow();

                    if(clickedCard.getOpenStatus().equals("false")) {
                        addItemsTofileList(pos);
                    }
                    else {
                        removeItemsFromfileList(pos);
                    }

                    clickedCard.InvertStatus();
                    adapter.notifyItemChanged(pos);
                }
                //Log.d(TAG, "onItemClick: "  + filesList.get(pos).getPath());
            }
        });

    }

    public void addItemsTofileList(int pos) {
        DirectoryCard clickedCard = filesList.get(pos);
        ArrayList<DirectoryCard> cardsToBeAdded = new ArrayList<>();

        File clickedDirectory = new File(clickedCard.getPath());
        File[] filesUnderDirectory = clickedDirectory.listFiles();

        if(filesUnderDirectory != null) {
            for (File f : filesUnderDirectory) {
                if(f.isDirectory())
                    cardsToBeAdded.add(new DirectoryCard(f.getName(), f.getAbsolutePath(), "false", R.drawable.rightarrow));
                else
                    cardsToBeAdded.add(new DirectoryCard(f.getName(), f.getAbsolutePath(), "na"));
            }
        }

        else {
            Toast.makeText(this, "Storage permission required", Toast.LENGTH_LONG).show();
        }

        filesList.addAll(pos+1, cardsToBeAdded);
        adapter.notifyItemRangeInserted(pos + 1, filesUnderDirectory.length);
    }

    public void removeItemsFromfileList(int pos) {
        DirectoryCard clickedCard = filesList.get(pos);
        ArrayList<DirectoryCard> cardsToBeRemoved = new ArrayList<>();
        int noOfItemsRemoved = 0;

        for(int i = pos + 1; i < filesList.size(); i++) {
            if(filesList.get(i).getPath().startsWith(clickedCard.getPath())) {
                cardsToBeRemoved.add(filesList.get(i));
                noOfItemsRemoved++;
            }
            else
                break;
        }

        filesList.removeAll(cardsToBeRemoved);
        adapter.notifyItemRangeRemoved(pos + 1, noOfItemsRemoved);

    }

}
