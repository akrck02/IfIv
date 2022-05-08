package com.example.if_iv.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.if_iv.BBDD.BBDDSQLiteHelper;
import com.example.if_iv.model.Capitulo;
import com.example.if_iv.util.MegaClase;

import java.util.ArrayList;

//findAll
//find
public class CapituloDao {

    //BBDD
    private SQLiteDatabase db;

    //Constructor donde crea el acceso a la BBDD
    public CapituloDao(Context context)
    {
        db= new BBDDSQLiteHelper(context).getWritableDatabase();
    }

    //Devuelve todos los Capitulos de la BBDD
    public ArrayList<Capitulo> findAll()
    {
        ArrayList<Capitulo> capitulos = new ArrayList<Capitulo>();

        Cursor c=db.rawQuery("select nombre, rutaFic, hecho from Capitulo order by nombre", null);
        if(c.moveToFirst())
        {
            do {
                String nombre=c.getString(0);
                String rutaFic=c.getString(1);
                Integer h=c.getInt(2);
                Boolean hecho= MegaClase.gestionInt(h);

                Capitulo capitulo=new Capitulo(nombre, rutaFic, hecho);

                ArrayList<Capitulo> hijos=findHijos(capitulo);
                if(hijos!=null)
                    capitulo.setHijos(hijos);

                capitulos.add(capitulo);

            }while(c.moveToNext());
        }

        if(capitulos.size()<=0)
            capitulos=null;
        return capitulos;
    }

    //Devuelve el capitulo que tenga el mismo nombre que el Capitulo que se le pasa como parametro
    //Devuelve null si no lo encuentra
    public Capitulo find(Capitulo cap)
    {
        Capitulo capitulo=null;
        Cursor c=db.rawQuery("select nombre, rutaFic, hecho from Capitulo where nombre=?", new String[]{cap.getNombre()});
        if(c.moveToFirst())
        {
                String nombre=c.getString(0);
                String rutaFic=c.getString(1);
                Integer h=c.getInt(2);
                Boolean hecho= MegaClase.gestionInt(h);

                capitulo=new Capitulo(nombre, rutaFic, hecho);

        }

        return capitulo;
    }

    public ArrayList findHijos(Capitulo cap)
    {
        ArrayList<Capitulo> hijos = new ArrayList<Capitulo>();

        Cursor c=db.rawQuery("select nombre, rutaFic, hecho from Capitulo where nombre like '%"+cap.getNombre()+"-"+"%' order by nombre", null);
        if(c.moveToFirst())
        {
            do {
                String nombre=c.getString(0);
                String rutaFic=c.getString(1);
                Integer h=c.getInt(2);
                Boolean hecho= MegaClase.gestionInt(h);

                Capitulo capitulo=new Capitulo(nombre, rutaFic, hecho);

                hijos.add(capitulo);

            }while(c.moveToNext());
        }

        if(hijos.size()<=0)
            hijos=null;
        return hijos;
    }

}
