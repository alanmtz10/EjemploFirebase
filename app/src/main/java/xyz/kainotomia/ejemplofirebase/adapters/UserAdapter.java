package xyz.kainotomia.ejemplofirebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import xyz.kainotomia.ejemplofirebase.MainActivity;
import xyz.kainotomia.ejemplofirebase.R;
import xyz.kainotomia.ejemplofirebase.models.User;

public class UserAdapter extends ArrayAdapter<User> {

    MainActivity activity;


    public UserAdapter(@NonNull Context context, ArrayList<User> users, MainActivity activity) {
        super(context, 0, users);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.nombre)).setText(user.getNombre());
        ((TextView) convertView.findViewById(R.id.email)).setText(user.getEmail());
        ((TextView) convertView.findViewById(R.id.uuid)).setText(user.getUuid());

        LinearLayout container = (LinearLayout) convertView.findViewById(R.id.item_container);

        ((Button) convertView.findViewById(R.id.btn_eliminar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = UserAdapter.this.getItem(position);
                u.remove();
                UserAdapter.this.remove(u);
            }
        });

        ((Button) convertView.findViewById(R.id.btn_editar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setCurrentEditUser(UserAdapter.this.getItem(position));
            }
        });

        return convertView;

    }
}
