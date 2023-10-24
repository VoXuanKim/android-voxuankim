package com.example.projectappdelivery.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.projectappdelivery.R;
import com.example.projectappdelivery.models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name, email, number, address;
    Button update;

    EditText nameEditText;
    EditText emailEditText;
    EditText numberEditText;
    EditText addressEditText;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        nameEditText = root.findViewById(R.id.profile_name);
        emailEditText = root.findViewById(R.id.profile_email);
        numberEditText = root.findViewById(R.id.profile_number);
        addressEditText = root.findViewById(R.id.profile_address);

        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        number = root.findViewById(R.id.profile_number);
        address = root.findViewById(R.id.profile_address);
        update = root.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });



        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        // Điền dữ liệu vào các trường thông tin
                        if (userModel != null) {
                            nameEditText.setText(userModel.getName());
                            emailEditText.setText(userModel.getEmail());
                            numberEditText.setText(userModel.getNumber());
                            addressEditText.setText(userModel.getAddress());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý trường hợp lỗi
                    }
                });


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        return root;
    }

    private void updateUserProfile() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String number = numberEditText.getText().toString();
        String address = addressEditText.getText().toString();

        if (!TextUtils.isEmpty(name)) {
            // Lưu tên người dùng vào Firebase Realtime Database
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("name").setValue(name);
        }

        if (!TextUtils.isEmpty(email)) {
            // Lưu email người dùng vào Firebase Realtime Database
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("email").setValue(email);
        }

        if (!TextUtils.isEmpty(number)) {
            // Lưu số điện thoại người dùng vào Firebase Realtime Database
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("number").setValue(number);
        }

        if (!TextUtils.isEmpty(address)) {
            // Lưu địa chỉ người dùng vào Firebase Realtime Database
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("address").setValue(address);
        }

        Toast.makeText(getContext(), "Thông tin của bạn đã được cập nhật.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                Uri profileUri = data.getData();

                final StorageReference reference = storage.getReference().child("profile_picture")
                        .child(FirebaseAuth.getInstance().getUid());

                reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profileImg").setValue(imageUrl);
                                Glide.with(requireContext()).load(imageUrl).into(profileImg);

                                Toast.makeText(getContext(), "Hình đại diện đã được tải lên", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }


}