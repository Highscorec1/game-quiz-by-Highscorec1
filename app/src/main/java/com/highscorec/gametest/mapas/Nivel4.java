package com.highscorec.gametest.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.highscorec.gametest.AdminSQLiteOpenHelper;
import com.highscorec.gametest.MainActivity;
import com.highscorec.gametest.R;
import com.highscorec.gametest.mapas.facil.MainActivity2_Nivel1;
import com.highscorec.gametest.mapas.facil.MainActivity2_Nivel2;
import com.highscorec.gametest.mapas.facil.MainActivity2_Nivel3;
import com.highscorec.gametest.mapas.facil.MainActivity2_Nivel4;
import com.highscorec.gametest.mapas.normal.map1normal;

import java.util.ArrayList;
import java.util.Random;

public class Nivel4 extends AppCompatActivity {

    //de la guia de Ernesto
    private TextView tv_nombre, tv_score;
    private ImageView iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;


    int score,  vidas =3;

    String nombre_jugador, string_score, string_vidas;


    ImageView imageView_1;





    //Variables realizadas con mi logica y datos de la pregunta
    TextView texto1;
    private RadioButton op1, op2, op3, op4;

    private String respuesta="En el 1899";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel1);

        //declara variables que vamos a usar de Ernesto

        Toast.makeText(this, "Nivel 2", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);


        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: " + nombre_jugador);

        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: " + score);



        string_vidas = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(string_vidas);
        if(vidas == 3){
            iv_vidas.setImageResource(R.drawable.photoroom__1___1_);
        } if(vidas == 2){
            iv_vidas.setImageResource(R.drawable.photoroom__1___1___1___1_);
        } if(vidas == 1){
            iv_vidas.setImageResource(R.drawable.photoroom__1___1___1___1___1_);
        }



        mp_great = MediaPlayer.create(this,R.raw.wonderful);

        mp_bad = MediaPlayer.create(this,R.raw.bad);




        //////////////////////////////////////////////////////////////////////////////////////////////////

        texto1 = (TextView) findViewById(R.id.textViewPregunta);
        texto1.setText("¿ Nombre del Juego ?");

        imageView_1 = (ImageView) findViewById(R.id.imageView1);
        imageView_1.setImageResource(R.drawable.amongus);




        op1=findViewById(R.id.checkBox);
        op1.setText("Fall Guys");

        op2=findViewById(R.id.checkBox2);
        op2.setText("Pou");


        op3=findViewById(R.id.checkBox3);
        op3.setText("Issac");


        op4=findViewById(R.id.checkBox4);
        op4.setText("Among Us");

    }




//NIVEL DEFECTUOSO NO USAR/////NIVEL DEFECTUOSO NO USAR/////NIVEL DEFECTUOSO NO USAR/////NIVEL DEFECTUOSO NO USAR/////NIVEL DEFECTUOSO NO USAR/////NIVEL DEFECTUOSO NO USAR///
    //NIVEL DEFECTUOSO NO USAR///
//NIVEL DEFECTUOSO NO USAR///
//NIVEL DEFECTUOSO NO USAR///
//NIVEL DEFECTUOSO NO USAR///





    //Este es el metodo del bottom

    public void Comprobar(View View){




        if(op4.isChecked()){



            Intent   intent = new Intent( Nivel4.this, MainActivity2_Nivel2.class);



            mp_great.start();
            score++;
            tv_score.setText("Score: "+ score);

            string_score = String.valueOf(score);

            string_vidas = String.valueOf(vidas);

            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);
            BaseDeDatos();

            startActivity(intent);



        } else {



            mp_bad.start();
            vidas--;
            BaseDeDatos();

            switch (vidas){

                case 3:
                    iv_vidas.setImageResource(R.drawable.photoroom__1___1_);
                    break;
                case 2:
                    iv_vidas.setImageResource(R.drawable.photoroom__1___1___1___1_);
                    break;
                case 1:
                    iv_vidas.setImageResource(R.drawable.photoroom__1___1___1___1___1_);
                    break;
                case 0:
                    Intent   intent = new Intent( Nivel4.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    mp.stop();
                    mp.release();

                    break;


            }

        }





    }

    public void BaseDeDatos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getReadableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)", null);
        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_score);

            if(score > bestScore){
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("score", score);

                BD.update("puntaje", modificacion, "score=" + bestScore, null);
            }

            BD.close();


        }else {

            ContentValues insertar = new ContentValues();

            insertar.put("nombre", nombre_jugador);
            insertar.put("score", score);

            BD.insert("puntaje", null, insertar);
            BD.close();



        }


    }

    @Override

    public void onBackPressed() {


    }

}