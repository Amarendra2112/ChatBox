package com.example.chatbox.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.chatbox.R;
import com.example.chatbox.model.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class SettingProfile extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    TextView userName,phone,statusHeading,status;
    EditText newUserName;
    ImageView camera,profile,editProfileName,editStatus;
    private BottomSheetDialog bottomSheetDialog;
    private int IMAGE_GALLERY_REQUEST = 111;
    private Uri imageUri;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        userName = findViewById(R.id.ProfileAboutStatus);
        phone = findViewById(R.id.ProfilePhoneDetail);
        status = findViewById(R.id.ProfileStatusDetails);
        camera = findViewById(R.id.ProfilePicEdit);
        profile = findViewById(R.id.UserProfilePic);
        editProfileName= findViewById(R.id.EditUserNameIcon);
        editStatus = findViewById(R.id.StatusEditIcon);
        progressDialog = new ProgressDialog(this);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(firebaseUser != null)
        {
            populate();
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetLayout();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Users").document(firebaseUser.getUid().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                     if(documentSnapshot.getString("imageProfile") != "")
                     {
                         profile.invalidate();
                         Drawable dr = profile.getDrawable();
                         Common.IMAGE_BITMAP =  ((GlideBitmapDrawable)dr.getCurrent()).getBitmap();
                         ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SettingProfile.this,profile,"image");
                         Intent intent = new Intent(SettingProfile.this,ProfilePicView.class);
                         startActivity(intent);
                     }

                     else
                     {
                         Toast.makeText(getApplicationContext(),"No profile pic available",Toast.LENGTH_SHORT).show();
                     }

                    }
                });

            }
        });

        editProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLayoutOfDesire();
            }
        });

        editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatusUpdate();
            }
        });
    }

    private void getStatusUpdate() {
        final View view = getLayoutInflater().inflate(R.layout.username_edit,null);
        newUserName = view.findViewById(R.id.NewUserName);
        statusHeading = view.findViewById(R.id.NewUserNameHeader);
        statusHeading.setText("Update your status");
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();

        ((View)view.findViewById(R.id.UpdateUserName)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = String.valueOf(newUserName.getText());
                firebaseFirestore.collection("Users").document(firebaseUser.getUid().toString()).update("bio",user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"User name successfully updated",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });
    }

    private void getLayoutOfDesire() {
        final View view = getLayoutInflater().inflate(R.layout.username_edit,null);
        newUserName = view.findViewById(R.id.NewUserName);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();

        ((View)view.findViewById(R.id.UpdateUserName)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = String.valueOf(newUserName.getText());
                firebaseFirestore.collection("Users").document(firebaseUser.getUid().toString()).update("userName",user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"User name successfully updated",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });
    }

    private void showBottomSheetLayout() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_box,null);
        ((View) view.findViewById(R.id.ChooseFolder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image"),IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            uploadToFirebase();
            progressDialog.setMessage("Uploading Profile picture");
            progressDialog.show();
        }
    }

    private void uploadToFirebase() {
        if(imageUri != null)
        {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("ImageProfile/" + System.currentTimeMillis()+"."+getFileExtension(imageUri));
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    Uri downloadUri = uriTask.getResult();

                    final String download_uri = String.valueOf(downloadUri);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imageProfile",download_uri);
                    firebaseFirestore.collection("Users").document(firebaseUser.getUid()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                            populate();
                            progressDialog.dismiss();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Upload failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void populate() {
        firebaseFirestore.collection("Users").document(firebaseUser.getUid().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String username = Objects.requireNonNull(documentSnapshot.get("userName")).toString();
                userName.setText(username);
                String userPhone = Objects.requireNonNull(documentSnapshot.get("userPhone")).toString();
                phone.setText(userPhone);
                String userStatus = Objects.requireNonNull(documentSnapshot.get("bio").toString());
                status.setText(userStatus);
                String imageProfile = documentSnapshot.getString("imageProfile");
                if(imageProfile != "")
                {
                    Glide.with(SettingProfile.this).load(imageProfile).into(profile);
                }

            }
        });
    }
}