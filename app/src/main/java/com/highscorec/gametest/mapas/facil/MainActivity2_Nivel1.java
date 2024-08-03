package com.highscorec.gametest.mapas.facil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.highscorec.gametest.AdminSQLiteOpenHelper;
import com.highscorec.gametest.Bonnuslive;
import com.highscorec.gametest.MainActivity;
import com.highscorec.gametest.MainBonnusktKt;
import com.highscorec.gametest.R;

public class MainActivity2_Nivel1 extends AppCompatActivity {

    //de la guia de Ernesto
    private TextView tv_nombre, tv_score;
    private ImageView iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;







    int score,  vidas =3;




    String nombre_jugador, string_score, string_vidas;


    TextView editTextNumber;







    //Variables realizadas con mi logica y datos de la pregunta
    TextView texto1;
    private RadioButton op1, op2, op3, op4;
    ImageView imageView_1;

   Button btn;

    private String respuesta="En el 2007";
    private MainBonnusktKt MainBonnusktKt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel1);

        //declara variables que vamos a usar de Ernesto

        Toast.makeText(this, "Modo Easy", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);


        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: " + nombre_jugador);




        mp = MediaPlayer.create(this, R.raw.startmusic);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this,R.raw.correcto);

        mp_bad = MediaPlayer.create(this,R.raw.bad);

        btn = (Button) findViewById(R.id.button);


        //////////////////////////////////////////////////////////////////////////////////////////////////

        texto1 = (TextView) findViewById(R.id.textViewPregunta);
        texto1.setText("¿Cual es la Compañia dueña de esta consola?");

        imageView_1 = (ImageView) findViewById(R.id.imageView1);
        imageView_1.setImageResource(R.drawable.nintendo_wii);





        op1=findViewById(R.id.checkBox);
        op1.setText("Nintendo");

        op2=findViewById(R.id.checkBox2);
        op2.setText("Microsoft");


        op3=findViewById(R.id.checkBox3);
        op3.setText("Sony");


        op4=findViewById(R.id.checkBox4);
        op4.setText("Panasonic");





    }


    //cuenta Regresiva

      public void iniciarCuenta(){
        int min = 1000;
        int seg = 1000;
        long valor = min + seg;
        CountDownTimer cuenta = new CountDownTimer(valor, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l / 1000;
                int minutos = (int) (tiempo / 60);
                long segundos = tiempo % 60;
                String minutos_mostrar = String.format("%02d",minutos);
                String segundos_mostrar = String.format("%02d",segundos);
                editTextNumber.setText(""+minutos_mostrar+": " +segundos_mostrar);

            }

            @Override
            public void onFinish() {


            }
        }.start();


    }





    //Este es el metodo del bottom

    public void Comprobar(View View){





                     if(op1.isChecked()){



                    Intent   intent = new Intent( MainActivity2_Nivel1.this, MainActivity2_Nivel2.class);


                         finish();
                         mp_great.start();
                         score++;
                         tv_score.setText("Score: "+ score);
                         BaseDeDatos();

                         string_score = String.valueOf(score);

                         string_vidas = String.valueOf(vidas);

                         intent.putExtra("jugador", nombre_jugador);
                         intent.putExtra("score", string_score);
                         intent.putExtra("vidas", string_vidas);

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
                             iv_vidas.setImageResource(R.drawable._vidas);
                             break;
                         case 1:
                             iv_vidas.setImageResource(R.drawable._vida);
                             break;
                         case 0:
                             Intent   intent = new Intent( MainActivity2_Nivel1.this, Bonnuslive.class);


                             startActivity(intent);
                             mp.stop();
                             mp.release();


                             break;


                     }

                  }






         }

         public void BaseDeDatos(){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
             SQLiteDatabase BD = admin.getWritableDatabase();

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


             } else {

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



    protected void onStop()
    {
        super.onStop();
        FinishAffinity ();



    }

        private void FinishAffinity() {

    }


}


