package com.highscorec.gametest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.highscorec.gametest.databinding.ActivityBonnusliveBinding;


import java.util.Locale;


public class Bonnuslive extends AppCompatActivity {


    //No se si esto me ayuda aqui pero bueno
    private TextView tv_nombre, tv_score;
    private ImageView iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;


    int score,  vidas =3;

    String nombre_jugador, string_score, string_vidas;



    private static final String TAG = "Bonnuslive";

    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button mNextLevelButton, mUnaVidamas, mGamerOver;
    private InterstitialAd mInterstitialAd;
    private TextView mLevelTextView;
    private ActivityBonnusliveBinding binding;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        mp = MediaPlayer.create(this, R.raw.gameover);
        mp.start();






        binding = ActivityBonnusliveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Load the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        loadInterstitialAd();

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = binding.nextLevelButton;
        mNextLevelButton.setEnabled(false);
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        // Create the text view to show the level number.
        mLevelTextView = binding.level;
        mLevel = START_LEVEL;






        //Codigo del boton regreso artificial
        mUnaVidamas = (Button) findViewById(R.id.buttonVida);
        mUnaVidamas.setEnabled(false);



        mUnaVidamas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();


            }
        });



    }





    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.

                        mInterstitialAd = interstitialAd;
                        mNextLevelButton.setEnabled(true);

                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                        mNextLevelButton.setEnabled(true);

                        String error = String.format(
                                Locale.ENGLISH,
                                "domain: %s, code: %d, message: %s",
                                loadAdError.getDomain(),
                                loadAdError.getCode(),
                                loadAdError.getMessage());
                    }
                });
    }

    private void showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            goToNextLevel();
        }

        if (mLevel < 3){
            Button boton = (Button) findViewById(R.id.nextLevelButton);
            boton.setEnabled(true);
        } else {

            Button boton = (Button) findViewById(R.id.nextLevelButton);
            boton.setEnabled(false);


        }



    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView.setText("Vidas " + (++mLevel));
        if (mInterstitialAd == null) {
            loadInterstitialAd();
        }

        if (mLevel == 2){

            Intent btmgameover = new Intent(this, MainAcitivity.class);
            startActivity(btmgameover);
            mp.stop();
            mp.release();

        }




    }




    public void onBackPressed() {

        if (mLevel >= 2){

            super.onBackPressed();


        } else {

            Toast.makeText(this, "Primero Suma +1 Vida", Toast.LENGTH_SHORT).show();

        }

    }


    public void BTMGAMEOVER(View view) {

        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView.setText("Vidas " + (++mLevel));
        if (mInterstitialAd == null) {
            loadInterstitialAd();
        }

        if (mLevel >= 2){

            Intent btmgameover = new Intent(this, MainAcitivity.class);
            startActivity(btmgameover);
            mp.stop();
            mp.release();



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


}