package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.joyh.arduinoAssistant.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

/**
 * Created by joyn on 2018/9/16 0016.
 */

public class ResourceWindow extends AppCompatActivity {
    private PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_window);
        Intent intent=getIntent();
        String path=intent.getStringExtra("pdf");
        pdfView = findViewById(R.id.pdfView);
        File pdf = new File(path);
        pdfView.fromFile(pdf)

                .enableAnnotationRendering(true)

                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .load()
        ;

    }
}
