package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;
    private ListView petListView;

    private ImageView petImageView;
    private Uri imageUri;

    EditText mPetNameEditText;
    EditText mPetDetailsEditText;
    EditText mPetPhoneNumberEditText;

    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        db = new DBHelper(this);
        petList = db.getAllPets();
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);
        petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(petListAdapter);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        imageUri = getUriFromResource(this, R.drawable.none);
        petImageView.setImageURI(imageUri);

        mPetNameEditText = (EditText) findViewById(R.id.petNameEditText);
        mPetDetailsEditText = (EditText) findViewById(R.id.petDetailsEditText);
        mPetPhoneNumberEditText = (EditText) findViewById(R.id.petPhoneNumberEditText);
    }

    public void selectPetImage(View v)
    {
        List<String> permsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int hasReadStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePerm == DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int hasWriteStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some permissions have not been granted
        if (permsList.size() > 0)
        {
            // Convert the permsList into an array:
            String[] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);

            // Ask user for them:
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Let's make sure we have all the permissions, then start up the Image Gallery:
        if (hasCameraPerm == GRANTED && hasReadStoragePerm == GRANTED && hasWriteStoragePerm == GRANTED)
        {
            // Let's open up the image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start activity for a result (picture)
            startActivityForResult(galleryIntent, 2325);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2325 && resultCode == RESULT_OK && data != null)
        {
            // data = data from GalleryIntent (the URI of some image)
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }

    public static Uri getUriFromResource(Context context, int resId)
    {
        Resources res = context.getResources();
        // Build a String in the form;
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resId) + "/"
                + res.getResourceTypeName(resId) + "/"
                + res.getResourceEntryName(resId);
        // Parse the String in order to construct an URI
        return Uri.parse(uri);
    }

    public void addPet(View view) {

        if (TextUtils.isEmpty(mPetNameEditText.getText().toString())
                || TextUtils.isEmpty(mPetDetailsEditText.getText().toString())
                || TextUtils.isEmpty(mPetPhoneNumberEditText.getText().toString()))
            Toast.makeText(this, "All information about the pet must be provided", Toast.LENGTH_LONG).show();
        else
        {
            String name = mPetNameEditText.getText().toString();
            String details = mPetDetailsEditText.getText().toString();
            String phoneNumber = mPetPhoneNumberEditText.getText().toString();
            String imageUriString = imageUri.toString();

            Pet newPet = new Pet(name, details, phoneNumber, imageUriString);
            db.addPet(newPet);
            petList.add(newPet);
            petListAdapter.notifyDataSetChanged();
        }

    }

    public void viewPetDetails(View view) {

        Intent detailsIntent = new Intent(this, PetDetailsActivity.class);

        Pet selectedPet = (Pet) view.getTag();

        detailsIntent.putExtra("Name", selectedPet.getName());
        detailsIntent.putExtra("Details", selectedPet.getDetails());
        detailsIntent.putExtra("PhoneNumber", selectedPet.getPhoneNumber());
        detailsIntent.putExtra("ImageUri", selectedPet.getImageUri());

        startActivity(detailsIntent);
    }
}
