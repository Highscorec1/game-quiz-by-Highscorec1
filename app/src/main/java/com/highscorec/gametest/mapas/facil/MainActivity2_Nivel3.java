package com.highscorec.gametest.mapas.facil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.highscorec.gametest.AdminSQLiteOpenHelper;
import com.highscorec.gametest.Bonnuslive;
import com.highscorec.gametest.R;

public class MainActivity2_Nivel3 extends AppCompatActivity {

    private TextView tv_nombre, tv_score, texto1;
    private ImageView iv_vidas, imageView_1;
    private MediaPlayer mp_great, mp_bad;
    private int score, vidas = 3;
    private String nombre_jugador, string_score, string_vidas;
    private RadioButton op1, op2, op3, op4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel1);

        tv_nombre = findViewById(R.id.textView_nombre);
        tv_score = findViewById(R.id.textView_score);
        iv_vidas = findViewById(R.id.imageView_vidas);
        texto1 = findViewById(R.id.textViewPregunta);  // Asegúrate de que este ID esté definido en el layout
        imageView_1 = findViewById(R.id.imageView1);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: " + nombre_jugador);

        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: " + score);

        string_vidas = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(string_vidas);
        actualizarVidas();

        mp_great = MediaPlayer.create(this, R.raw.correcto);
        mp_bad = MediaPlayer.create(this, R.raw.bad);

        texto1.setText("¿ Nombre del Juego ?");
        imageView_1.setImageResource(R.drawable.amongus);

        op1 = findViewById(R.id.checkBox);
        op1.setText("Fall Guys");

        op2 = findViewById(R.id.checkBox2);
        op2.setText("Pou");

        op3 = findViewById(R.id.checkBox3);
        op3.setText("Issac");

        op4 = findViewById(R.id.checkBox4);
        op4.setText("Among Us");
    }

    public void Comprobar(View view) {
        if (op4.isChecked()) {
            mp_great.start();
            score++;
            tv_score.setText("Score: " + score);
            BaseDeDatos();

            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);

            Intent intent = new Intent(MainActivity2_Nivel3.this, MainActivity2_Nivel5.class);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);
            startActivity(intent);
        } else {
            mp_bad.start();
            vidas--;
            BaseDeDatos();
            actualizarVidas();

            if (vidas == 0) {
                Intent intent = new Intent(MainActivity2_Nivel3.this, Bonnuslive.class);
                startActivity(intent);
            }
        }
    }

    private void actualizarVidas() {
        if (vidas == 3) {
            iv_vidas.setImageResource(R.drawable.photoroom__1___1_);
        } else if (vidas == 2) {
            iv_vidas.setImageResource(R.drawable._vidas);
        } else if (vidas == 1) {
            iv_vidas.setImageResource(R.drawable._vida);
        }
    }

    public void BaseDeDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getReadableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)", null);
        if (consulta.moveToFirst()) {
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_score);

            if (score > bestScore) {
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("score", score);
                BD.update("puntaje", modificacion, "score=" + bestScore, null);
            }
        } else {
            ContentValues insertar = new ContentValues();
            insertar.put("nombre", nombre_jugador);
            insertar.put("score", score);
            BD.insert("puntaje", null, insertar);
        }

        BD.close();
    }

    @Override
    public void onBackPressed() {
        // Deshabilitar el botón de retroceso
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
        System.exit(0);
    }

    public void finishAffinity() {
        System.exit(0);
    }
}
