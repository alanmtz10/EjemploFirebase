package xyz.kainotomia.ejemplofirebase.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import xyz.kainotomia.ejemplofirebase.adapters.UserAdapter;

public class User {

    private static DatabaseReference db;

    private String uuid;
    private String nombre;
    private String email;

    public User(String nombre, String email) {

        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    private static void recoverDbInstance() {
        if (db == null) {
            db = FirebaseDatabase.getInstance().getReference();
        }
    }

    public void save() {

        recoverDbInstance();

        this.uuid = UUID.randomUUID().toString();
        db.child("Users").child(this.uuid).setValue(this);
    }

    public void remove() {
        recoverDbInstance();
        db.child("Users").child(this.uuid).removeValue();
    }

    public void update() {
        recoverDbInstance();

        DatabaseReference ref = db.child("Users").child(this.uuid);
        ref.child("nombre").setValue(this.nombre);
        ref.child("email").setValue(this.email);


    }

    public static void getAllUsers(UserAdapter users) {
        recoverDbInstance();


        db.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() != 0) {
                    User aux;

                    for (DataSnapshot data : snapshot.getChildren()) {
                        aux = new User(data.child("nombre").getValue(String.class), data.child("email").getValue(String.class));
                        aux.setUuid(data.child("uuid").getValue(String.class));
                        users.add(aux);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
