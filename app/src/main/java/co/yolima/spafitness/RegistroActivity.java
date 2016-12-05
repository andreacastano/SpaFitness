package co.yolima.spafitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {


    EditText eUser2, ePassword2, eRPassword2, eMail;
    Button bSend, bCancel;

    private String name, pass, rpass,  mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eUser2 = (EditText) findViewById(R.id.eUser);
        ePassword2 = (EditText) findViewById(R.id.ePassword);
        eRPassword2 = (EditText) findViewById(R.id.eRPassword);
        eMail = (EditText) findViewById(R.id.eCorreo);
        bSend = (Button) findViewById(R.id.bSend);
        bCancel = (Button) findViewById(R.id.bCancel);


        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentR = new Intent();
                setResult(RESULT_CANCELED, myIntentR);
                finish();
            }
        });

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = eUser2.getText().toString();
                pass = ePassword2.getText().toString();
                rpass = eRPassword2.getText().toString();
                mail = eMail.getText().toString();

                if (name.equals("") || pass.equals("") || rpass.equals("") || mail.equals("")){
                    Toast.makeText(getApplicationContext(), "Debe digitar todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent2 = new Intent();
                    intent2.putExtra("name",name);
                    intent2.putExtra("password",pass);
                    intent2.putExtra("mail",mail);
                    setResult(RESULT_OK,intent2);
                    finish();
                }
            }
        });
    }
}