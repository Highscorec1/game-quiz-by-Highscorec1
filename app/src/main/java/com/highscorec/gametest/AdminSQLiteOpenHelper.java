package com.highscorec.gametest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

                                   //podremos acceder crear y borrar de la BD
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    //Constructur de la BD
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Metodo automatico para generar la tablas
    @Override
    public void onCreate(SQLiteDatabase BD) {

        //Tabla para almacenar los datos
        BD.execSQL("create table puntaje(nombre text, score int)");

    }
    //metodo que se ejecuta cuando es necesario actualizar la estructura de la  BD o una conversion de datos
    @Override
    //Solo ocupo una tabla por lo que elimino la anterior para volverla a crear  con una nueva  version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
