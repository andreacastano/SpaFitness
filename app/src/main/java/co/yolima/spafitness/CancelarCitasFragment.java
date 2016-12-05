package co.yolima.spafitness;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CancelarCitasFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userId;
    TextView citaPendiente, nombreCita;
    Button btnCargarCita, btnCancelarCita;


    public CancelarCitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cancelar_citas, container, false);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("citas");

        citaPendiente = (TextView) rootView.findViewById(R.id.tvCitaPendiente);
        nombreCita = (TextView) rootView.findViewById(R.id.tvNombreCita);
        btnCancelarCita = (Button) rootView.findViewById(R.id.btnCancelarCita);
        btnCargarCita = (Button) rootView.findViewById(R.id.btnCargarCitas);

        // btn Cancelar cita
        btnCancelarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                databaseReference.child(userId).child("activa").setValue(i);
                Toast.makeText(getContext(),"Cita cancelada",Toast.LENGTH_SHORT).show();
                nombreCita.setVisibility(View.INVISIBLE);
            }
        });

        // btn Cargar cita
        btnCargarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = mAuth.getCurrentUser().getUid();
                databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Citas citas = dataSnapshot.getValue(Citas.class);
                        if (citas.getActiva() == 1){
                            citaPendiente.setVisibility(View.VISIBLE);
                            String text = citas.getNombre()+" "+citas.getApellido()+","+Integer.toString(citas.getEdad());
                            nombreCita.setText(text);
                        } else {
                            Toast.makeText(getContext(),"No hay citas creadas...",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return rootView;
    }

}
