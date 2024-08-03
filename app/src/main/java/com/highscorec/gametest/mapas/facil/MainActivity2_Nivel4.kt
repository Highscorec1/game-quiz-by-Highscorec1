package com.highscorec.gametest.mapas.facil

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.highscorec.gametest.AdminSQLiteOpenHelper
import com.highscorec.gametest.Bonnuslive
import com.highscorec.gametest.R
import java.util.Random

class MainActivity2_Nivel4 : AppCompatActivity() {

    private var tv_nombre: TextView? = null
    private var tv_score: TextView? = null
    private var iv_vidas: ImageView? = null
    private var mp: MediaPlayer? = null
    private var mp_great: MediaPlayer? = null
    private var mp_bad: MediaPlayer? = null
    private var score = 0
    private var vidas = 3
    private var nombre_jugador: String? = null
    private var string_score: String? = null
    private var string_vidas: String? = null
    private var imageView_1: ImageView? = null
    private var texto1: TextView? = null
    private var op1: RadioButton? = null
    private var op2: RadioButton? = null
    private var op3: RadioButton? = null
    private var op4: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2_nivel1)

        tv_nombre = findViewById(R.id.textView_nombre)
        tv_score = findViewById(R.id.textView_score)
        iv_vidas = findViewById(R.id.imageView_vidas)

        nombre_jugador = intent.getStringExtra("jugador") ?: "Desconocido"
        tv_nombre?.text = "Jugador: $nombre_jugador"

        string_score = intent.getStringExtra("score") ?: "0"
        score = string_score?.toIntOrNull() ?: 0
        tv_score?.text = "Score: $score"

        string_vidas = intent.getStringExtra("vidas") ?: "3"
        vidas = string_vidas?.toIntOrNull() ?: 3
        actualizarVidas()

        mp = MediaPlayer.create(this, R.raw.goats).apply {
            isLooping = true
        }

        mp_great = MediaPlayer.create(this, R.raw.correcto)
        mp_bad = MediaPlayer.create(this, R.raw.bad)

        texto1 = findViewById(R.id.textViewPregunta)
        texto1?.text = "¿ Cuál es el Nombre del Juego ?"

        imageView_1 = findViewById(R.id.imageView1)
        imageView_1?.setImageResource(R.drawable.bomberman)

        op1 = findViewById(R.id.checkBox)
        op1?.text = "Super Mario Bros"

        op2 = findViewById(R.id.checkBox2)
        op2?.text = "Bomberman"

        op3 = findViewById(R.id.checkBox3)
        op3?.text = "Tetris"

        op4 = findViewById(R.id.checkBox4)
        op4?.text = "Among Us"
    }

    fun Comprobar(view: View?) {
        val selectedOption = when {
            op1?.isChecked == true -> op1?.text.toString()
            op2?.isChecked == true -> op2?.text.toString()
            op3?.isChecked == true -> op3?.text.toString()
            op4?.isChecked == true -> op4?.text.toString()
            else -> ""
        }

        if (selectedOption == "Bomberman") {
            mp_great?.start()
            score++
            tv_score?.text = "Score: $score"
            string_score = score.toString()
            string_vidas = vidas.toString()

            val activityList = listOf(
                MainActivity2_Nivel5::class.java,
                MainActivity2_Nivel6::class.java,
                MainActivity2_Nivel7::class.java,
                MainActivity2_Nivel8::class.java,
                MainActivity2_Nivel9::class.java
            )

            val randomIndex = Random().nextInt(activityList.size)
            val nextActivity = activityList[randomIndex]

            val intent = Intent(this, nextActivity)
            intent.putExtra("jugador", nombre_jugador)
            intent.putExtra("score", string_score)
            intent.putExtra("vidas", string_vidas)
            BaseDeDatos()
            startActivity(intent)
        } else {
            mp_bad?.start()
            vidas--
            BaseDeDatos()
            actualizarVidas()
            if (vidas == 0) {
                val intent = Intent(this, Bonnuslive::class.java)
                intent.putExtra("jugador", nombre_jugador)
                intent.putExtra("score", string_score)
                intent.putExtra("vidas", string_vidas)
                startActivity(intent)
                mp?.stop()
                mp?.release()
            }
        }
    }

    private fun actualizarVidas() {
        iv_vidas?.setImageResource(
            when (vidas) {
                3 -> R.drawable.photoroom__1___1_
                2 -> R.drawable._vidas
                1 -> R.drawable._vida
                else -> R.drawable._vida // Asegúrate de tener un recurso para "no vida" .. cambiar recurso a no vida imagen
            }
        )
    }

    private fun BaseDeDatos() {
        val admin = AdminSQLiteOpenHelper(this, "BD", null, 1)
        val BD = admin.readableDatabase
        val consulta = BD.rawQuery(
            "select * from puntaje where score = (select max(score) from puntaje)",
            null
        )
        if (consulta.moveToFirst()) {
            val temp_score = consulta.getString(1)
            val bestScore = temp_score.toIntOrNull() ?: 0
            if (score > bestScore) {
                val modificacion = ContentValues()
                modificacion.put("nombre", nombre_jugador)
                modificacion.put("score", score)
                BD.update("puntaje", modificacion, "score=$bestScore", null)
            }
        } else {
            val insertar = ContentValues()
            insertar.put("nombre", nombre_jugador)
            insertar.put("score", score)
            BD.insert("puntaje", null, insertar)
        }
        consulta.close()
        BD.close()
    }

    override fun onBackPressed() {
        // Deshabilitar el botón de retroceso
    }

    override fun onStop() {
        super.onStop()
        finishAffinity()
        System.exit(0)
    }

    override fun finishAffinity() {
        System.exit(0)
    }
}
