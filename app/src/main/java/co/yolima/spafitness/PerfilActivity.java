package co.yolima.spafitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView tUsuario,tCorreo;
    String nombre,correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil );

        Bundle extras=getIntent().getExtras();
        nombre= extras.getString("nombre");
        correo= extras.getString("correo");

        tUsuario= (TextView) findViewById(R.id.tUsuario);
        tCorreo= (TextView) findViewById(R.id.tCorreo);

        tUsuario.setText(nombre);
        tCorreo.setText(correo);

    }
}