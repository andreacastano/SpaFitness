package co.yolima.spafitness;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedirCitasFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userId;
    private String name, apellido;
    private int edad;
    EditText nombreCita, lastNameCita, edadCita;
    Button btnEnviar;

    public PedirCitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pedir_citas, container, false);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("citas");

        // linking
        nombreCita = (EditText) rootView.findViewById(R.id.teNombre);
        lastNameCita = (EditText) rootView.findViewById(R.id.teApellido);
        edadCita = (EditText) rootView.findViewById(R.id.teEdad);
        btnEnviar = (Button) rootView.findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombreCita.getText().toString();
                apellido = lastNameCita.getText().toString();
                edad = Integer.valueOf(edadCita.getText().toString());

                if (name.equals("") || apellido.equals("") || edadCita.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Debe ingresas datos completos",Toast.LENGTH_SHORT).show();
                } else{
                    Citas citas = new Citas(name, apellido, edad, 1);
                    databaseReference.child(userId).setValue(citas);
                    Toast.makeText(getContext(),"Cita creada",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

}
