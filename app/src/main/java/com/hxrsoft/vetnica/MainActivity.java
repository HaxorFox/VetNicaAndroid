package com.hxrsoft.vetnica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar=(Button)findViewById(R.id.iniciar);

        iniciar.setOnClickListener(new View.OnClickListener()
        {

        public void onClick(View arg0){
            Intent inten = new Intent(MainActivity.this,Veterinarias.class);
            startActivity(inten);

        }

        });

    }
}
