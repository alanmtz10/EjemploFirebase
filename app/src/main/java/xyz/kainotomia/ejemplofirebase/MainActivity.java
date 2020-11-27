package xyz.kainotomia.ejemplofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import xyz.kainotomia.ejemplofirebase.adapters.UserAdapter;
import xyz.kainotomia.ejemplofirebase.models.User;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private Button agregar;

    private User currentEditUser;

    /**
     *
     */
    private ListView users;
    private ArrayList<User> users_array;
    private UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.name = findViewById(R.id.et_nombre);
        this.email = findViewById(R.id.et_email);
        this.agregar = findViewById(R.id.btn_add);
        this.users = findViewById(R.id.list_users);


        /**
         * List
         */
        users_array = new ArrayList<>();
        adapter = new UserAdapter(this, users_array, this);
        this.users.setAdapter(adapter);

        User.getAllUsers(adapter);

    }


    public void agregarUsuario(View view) {
        if (currentEditUser == null) {
            User user = new User(name.getText().toString(), email.getText().toString());
            adapter.add(user);
            user.save();

            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();

            name.setText("");
            email.setText("");
        } else {

            currentEditUser.setNombre(name.getText().toString());
            currentEditUser.setEmail(email.getText().toString());
            currentEditUser.update();

            Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();

            name.setText("");
            email.setText("");
            currentEditUser = null;

        }
    }

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public Button getAgregar() {
        return agregar;
    }

    public void setAgregar(Button agregar) {
        this.agregar = agregar;
    }

    public User getCurrentEditUser() {
        return currentEditUser;
    }

    public void setCurrentEditUser(User currentEditUser) {
        this.currentEditUser = currentEditUser;
        this.name.setText(currentEditUser.getNombre());
        this.email.setText(currentEditUser.getEmail());
    }
}