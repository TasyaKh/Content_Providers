package com.example.content_providers;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class MainActivity extends Activity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        showContacts();
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            Cursor crr  = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},null,null);
            crr.moveToFirst();

            TextView textView = findViewById(R.id.textView);
            TextView textView2 = findViewById(R.id.textView2);

            RecyclerView contacts = findViewById(R.id.recycler_view);
            StateAdapter adapter = new StateAdapter(this, crr);
            // устанавливаем для списка адаптер
            contacts.setAdapter(adapter);

//            textView.setText(crr.getString( 15));
//            String[] cll = crr.getColumnNames();
//            StringBuilder columns = new StringBuilder();
//
//            for(int i =0 ;i< cll.length;i++){
//                columns.append(" ").append(i).append(" ").append(cll[i]);
//            }
//
//            textView2.setText(columns.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


//            ContentResolver cr = this.getContentResolver();
//            cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
//
//            //Cursor crr = (Cursor) c;
//            TextView textView = findViewById(R.id.textView);
//            textView.setText( ContactsContract.CommonDataKinds.Phone.NUMBER);

}
