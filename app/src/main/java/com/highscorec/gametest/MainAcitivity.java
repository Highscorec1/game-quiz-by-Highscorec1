package com.highscorec.gametest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.highscorec.gametest.mapas.facil.MainActivity2_Nivel1;

public class MainAcitivity extends AppCompatActivity {

    private TextView  tv_score;
    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_bestScore;
    private MediaPlayer mp;



    int num_aleatorio = (int) (Math.random() * 9);


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        iv_personaje = (ImageView) findViewById(R.id.imageView_Personaje);
        tv_bestScore = (TextView) findViewById(R.id.bestScore);






        int id;

        if (num_aleatorio == 0 ) {

            id = getResources().getIdentifier("high1", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 1 ) {

            id = getResources().getIdentifier("high2", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 10 ) {

            id = getResources().getIdentifier("high3", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if ( num_aleatorio == 9) {

            id = getResources().getIdentifier("high4", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 2 ) {

            id = getResources().getIdentifier("high5", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 8) {

            id = getResources().getIdentifier("high6", "drawable", getPackageName());
            iv_personaje.setImageResource(id);


        } else if (num_aleatorio == 3 ) {

            id = getResources().getIdentifier("high7", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 7 ) {

            id = getResources().getIdentifier("high8", "drawable", getPackageName());
            iv_personaje.setImageResource(id);


        } else if (num_aleatorio == 4 ) {

            id = getResources().getIdentifier("high9", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        } else if (num_aleatorio == 5 ) {

            id = getResources().getIdentifier("high10", "drawable", getPackageName());
            iv_personaje.setImageResource(id);

        }


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max(score) from puntaje)", null  );

        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String tempo_score = consulta.getString(1);
            tv_bestScore.setText("Record: " + tempo_score + " de " + temp_nombre);
            BD.close();


        } else {
            BD.close();

        }

        mp = MediaPlayer.create(this, R.raw.startmusic);
        mp.start();
        mp.setLooping(true);


    }


    public void pulsado(View view) {

            mp.stop();

        }



    public void Jugar(View View){
        String nombre = et_nombre.getText().toString();

        if(!nombre.equals("")){
            mp.stop();
            mp.release();

                Intent   intent = new Intent( MainAcitivity.this, MainActivity2_Nivel1.class);

                intent.putExtra("jugador", nombre);
                startActivity(intent);
                finish();




        } else {
            Toast.makeText(this, "Primero Ingresa tu Nombre", Toast.LENGTH_SHORT).show();

            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);


        }




    }


    @Override
    public void onBackPressed(){
    }


}
