package com.highscorec.gametest.mapas.facil

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.highscorec.gametest.*
import com.highscorec.gametest.Bonnuslive
import com.highscorec.gametest.mapas.normal.MainActivity2_Nivel19
import com.highscorec.gametest.mapas.normal.MainActivity2_Nivel20
import com.highscorec.gametest.mapas.normal.MainActivity2_Nivel21
import java.util.*

class MainActivity2_Nivel8 : AppCompatActivity() {
    //de la guia de Ernesto
    private var tv_nombre: TextView? = null
    private var tv_score: TextView? = null
    private var iv_vidas: ImageView? = null
    private val et_respuesta: EditText? = null
    private var mp: MediaPlayer? = null
    private var mp_great: MediaPlayer? = null
    private var mp_bad: MediaPlayer? = null
    var score = 0
    var vidas = 3
    var nombre_jugador: String? = null
    var string_score: String? = null
    var string_vidas: String? = null
    var imageView_1: ImageView? = null

    //Variables realizadas con mi logica y datos de la pregunta
    var texto1: TextView? = null
    private var op1: RadioButton? = null
    private var op2: RadioButton? = null
    private var op3: RadioButton? = null
    private var op4: RadioButton? = null
    private val respuesta = "acción-aventura de mundo abierto"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2_nivel1)

        //declara variables que vamos a usar de Ernesto



        tv_nombre = findViewById<View>(R.id.textView_nombre) as TextView
        tv_score = findViewById<View>(R.id.textView_score) as TextView
        iv_vidas = findViewById<View>(R.id.imageView_vidas) as ImageView

        nombre_jugador = intent.getStringExtra("jugador")
        tv_nombre!!.text = "Jugador: $nombre_jugador"

        string_score = intent.getStringExtra("score")
        score = string_score!!.toInt()
        tv_score!!.text = "Score: $score"
        string_vidas = intent.getStringExtra("vidas")
        vidas = string_vidas!!.toInt()
        if (vidas == 3) {
            iv_vidas!!.setImageResource(R.drawable.photoroom__1___1_)
        }
        if (vidas == 2) {
            iv_vidas!!.setImageResource(R.drawable._vidas)
        }
        if (vidas == 1) {
            iv_vidas!!.setImageResource(R.drawable._vida)
        }


        mp_great = MediaPlayer.create(this, R.raw.correcto)
        mp_bad = MediaPlayer.create(this, R.raw.bad)


        //////////////////////////////////////////////////////////////////////////////////////////////////
        //Sustituir pegruntas//
        texto1 = findViewById<View>(R.id.textViewPregunta) as TextView
        texto1!!.text = "¿Quién es el creador de Minecraft?"

        imageView_1 = findViewById<View>(R.id.imageView1) as ImageView
        imageView_1!!.setImageResource(R.drawable.minecraft)

        op1 = findViewById(R.id.checkBox)
        op1!!.setText("Microsoft")
        op2 = findViewById(R.id.checkBox2)
        op2!!.setText("Playstation")
        op3 = findViewById(R.id.checkBox3)
        op3!!.setText("Markus Persson/Jens Bergensten")
        op4 = findViewById(R.id.checkBox4)
        op4!!.setText("Nintendo")
    }

    //Este es el metodo del bottom
    fun Comprobar(View: View?) {

        //lista de actividades aleatorias a la que se podra dirijir//
        // We are creating a list, which will store the activities that haven't been opened yet
        var activityList = ArrayList<Class<*>>()
        activityList.add(MainActivity2_Nivel19::
        class.java)
        activityList.add(MainActivity2_Nivel20::
        class.java)
        activityList.add(MainActivity2_Nivel21::
        class.java)


        var generator = Random()

        var number = generator.nextInt(3) + 1

        var activity: Class<*>? = null

        // Here, we are checking to see what the output of the random was
        when(number)
        {
            1 -> {
                activity = MainActivity2_Nivel19::class.java
                // We are adding the number of the activity to the list
                activityList.remove(MainActivity2_Nivel19::class.java)
            }
            2 -> {
                activity = MainActivity2_Nivel20::class.java
                activityList.remove(MainActivity2_Nivel20::class.java)
            }
            else -> {
                activity = MainActivity2_Nivel21::class.java
                activityList.remove(MainActivity2_Nivel21::class.java)
            }
        }


        //////////
        // We use intents to start activities //








        if (op3!!.isChecked) {

            // We use intents to start activities
            var intent = Intent(baseContext, activity)
            // `intent.putExtra(...)` is used to pass on extra information to the next activity
            // `intent.putExtra(...)` is used to pass on extra information to the next activity
            intent.putExtra("ACTIVITY_LIST", activityList)



            mp_great!!.start()
            score++
            tv_score!!.text = "Score: $score"
            string_score = score.toString()
            string_vidas = vidas.toString()
            intent.putExtra("jugador", nombre_jugador)
            intent.putExtra("score", string_score)
            intent.putExtra("vidas", string_vidas)
            BaseDeDatos()
            startActivity(intent)
            finish()

        } else {
            mp_bad!!.start()
            vidas--
            BaseDeDatos()
            when (vidas) {
                3 -> iv_vidas!!.setImageResource(R.drawable.photoroom__1___1_)
                2 -> iv_vidas!!.setImageResource(R.drawable._vidas)
                1 -> iv_vidas!!.setImageResource(R.drawable._vida)
                0 -> {
                    val intent = Intent(this@MainActivity2_Nivel8, Bonnuslive::class.java)
                    startActivity(intent)
                    mp!!.stop()
                    mp!!.release()
                }
            }
        }
    }

    fun BaseDeDatos() {
        val admin = AdminSQLiteOpenHelper(this, "BD", null, 1)
        val BD = admin.readableDatabase
        val consulta = BD.rawQuery(
            "select * from puntaje where score = (select max(score) from puntaje)",
            null
        )
        if (consulta.moveToFirst()) {
            val temp_nombre = consulta.getString(0)
            val temp_score = consulta.getString(1)
            val bestScore = temp_score.toInt()
            if (score > bestScore) {
                val modificacion = ContentValues()
                modificacion.put("nombre", nombre_jugador)
                modificacion.put("score", score)
                BD.update("puntaje", modificacion, "score=$bestScore", null)
            }
            BD.close()
        } else {
            val insertar = ContentValues()
            insertar.put("nombre", nombre_jugador)
            insertar.put("score", score)
            BD.insert("puntaje", null, insertar)
            BD.close()
        }
    }

    override fun onBackPressed() {}

    override fun onStop() {
        super.onStop()
        FinishAffinity()

    }

    private fun FinishAffinity() {

    }
}