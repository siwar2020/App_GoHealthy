package com.example.gohealthlyapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gohealthlyapplication.alarmreminder.Activity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class post_fragment extends Fragment implements View.OnClickListener {
    private static final int PICK_INTENT = 100;
    private static final String LOG_TAG = "add ";
    ImageView im_medc;
    Uri mImageuri;
    EditText name_medc, description_medc, price_medc;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private DatabaseReference equipements_medicaments;
    String downloadImageUrl;
    View v;
    ImageButton button6;
    Button upload;


    public post_fragment() {
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.post_fragment, container, false);
        button6 = (ImageButton) v.findViewById(R.id.button6);
        upload = (Button) v.findViewById(R.id.upload);
        name_medc = v.findViewById(R.id.name);
        description_medc = v.findViewById(R.id.description);
        price_medc = v.findViewById(R.id.price_id);
        im_medc = v.findViewById(R.id.im_medc);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        equipements_medicaments = mDatabase.child("equipements_médicaments");
        mStorageRef = FirebaseStorage.getInstance().getReference("equipements_images");
        button6.setOnClickListener(this);
        upload.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == button6) {
            openGallery();
            Toast.makeText(getActivity(), "choose a photo ", Toast.LENGTH_SHORT).show();
        }
        if (v == upload) {
            FileUpLoader();
        }


    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_INTENT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_INTENT) {
            mImageuri = data.getData();
            im_medc.setImageURI(mImageuri);


        }
    }


    private void FileUpLoader() {
        StorageReference filepath = mStorageRef.child(mImageuri.getLastPathSegment());
        final UploadTask uploadTask = filepath.putFile(mImageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(), "Error" + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(getActivity(), "image uploaded ", Toast.LENGTH_SHORT).show();
                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            SaveEquipementToDatabase();
                            Toast.makeText(getActivity(), "your post will be approved soon by admin ", Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }

        });


    }

    private void SaveEquipementToDatabase() {

        UUID id = UUID.randomUUID();
        String productRandomKey =id.toString();
        String txt_name = name_medc.getText().toString();
        String txt_desc = description_medc.getText().toString();
        String txt_price = price_medc.getText().toString();
        equipement_medicament eq = new equipement_medicament(productRandomKey, txt_name, txt_price, txt_desc, downloadImageUrl);
        equipements_medicaments.push().setValue(eq);

    }

}

