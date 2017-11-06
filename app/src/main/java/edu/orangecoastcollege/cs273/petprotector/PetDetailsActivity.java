package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class PetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.petDetailsNameTextView);
        TextView petDetailsDetailsTextView = (TextView) findViewById(R.id.petDetailsDetailsTextView);
        TextView petDetailsPhoneNumberTextView = (TextView) findViewById(R.id.petDetailsPhoneNumberTextView);

        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String details = detailsIntent.getStringExtra("Details");
        String phoneNumber = detailsIntent.getStringExtra("PhoneNumber");
        String imageUri = detailsIntent.getStringExtra("ImageUri");

        petDetailsImageView.setImageURI(Uri.parse(imageUri));
        petDetailsNameTextView.setText(name);
        petDetailsDetailsTextView.setText(details);
        petDetailsPhoneNumberTextView.setText(phoneNumber);
    }

}
