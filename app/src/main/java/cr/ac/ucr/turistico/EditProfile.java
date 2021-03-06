package cr.ac.ucr.turistico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import cr.ac.ucr.turistico.fragments.ProfileFragment;
import cr.ac.ucr.turistico.utils.AppPreferences;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profileImageView;
    private Button cancelButton;
    private Button saveButton;
    private TextView changeProfile;

    private EditText editName;
    private EditText editLastName;
    String newName = "";
    String newLastName = "";

    private DatabaseReference databaseReference;
    private FirebaseAuth aAuth;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        aAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference();

        editName = findViewById(R.id.ed_edit_name);
        editLastName = findViewById(R.id.ed_edit_last);

        profileImageView = findViewById(R.id.profile_image);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById(R.id.btn_save);
        changeProfile = findViewById(R.id.change_profile_btn);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        changeProfile.setOnClickListener(this);

        getUserInfo();
    }

    private void getUserInfo() {
        databaseReference.child(aAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("imgPerfil")) {
                        String image = snapshot.child("imgPerfil").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        newName = editName.getText().toString().trim();
        newLastName = editLastName.getText().toString().trim();
        //progressDialog.setTitle("Set your profile");
        //progressDialog.setMessage("Please wait while your data is being setting");
        //progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePicsRef.child("fotosPerfil")
                    .child(aAuth.getCurrentUser().getUid() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("imgPerfil", myUri);
                        userMap.put("nombre", newName);
                        userMap.put("apellido", newLastName);
                        databaseReference.child(aAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        //progressDialog.cancel();
                    }
                }
            });
        } else {
            //progressDialog.dismiss();
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                Intent cancel = new Intent(EditProfile.this, MainActivity.class);
                startActivity(cancel);
                break;
            case R.id.btn_save:
                uploadProfileInfo();
                Intent save = new Intent(EditProfile.this, MainActivity.class);
                startActivity(save);
                finish();
                break;
            case R.id.change_profile_btn:
                CropImage.activity().setAspectRatio(1, 1).start(EditProfile.this);
                break;
        }
    }
}