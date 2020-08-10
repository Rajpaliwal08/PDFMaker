package com.rajpaliwal.createpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    EditText editText, filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        filename = findViewById(R.id.filename);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    public void click(View view) {

        String myString = editText.getText().toString();
        String saveName = filename.getText().toString();

        if (TextUtils.isEmpty(saveName)) {
            filename.setError("Empty user Input");
        } else {


            PdfDocument myPdfDocument = new PdfDocument();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

            Paint myPaint = new Paint();


            int x = 10, y = 25;

            for (String line : myString.split("\n")) {
                myPage.getCanvas().drawText(line, x, y, myPaint);
                y += myPaint.descent() - myPaint.ascent();
            }


            myPdfDocument.finishPage(myPage);

            String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + saveName + ".pdf";
            File myFile = new File(myFilePath);

            try {
                myPdfDocument.writeTo(new FileOutputStream(myFile));
                Toast.makeText(this, "Successfully Downloaded", Toast.LENGTH_SHORT).show();
                editText.setText("");
                filename.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

            myPdfDocument.close();


        }

    }

}
