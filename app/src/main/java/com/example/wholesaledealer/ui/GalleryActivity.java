package com.example.wholesaledealer.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.wholesaledealer.R;
import com.example.wholesaledealer.data.DbHelper;
import com.example.wholesaledealer.data.WholesaleItem;
import com.example.wholesaledealer.ui.adapter.ItemAdapter;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DbHelper db = new DbHelper(this);
        List<WholesaleItem> items = db.getAll();

        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}