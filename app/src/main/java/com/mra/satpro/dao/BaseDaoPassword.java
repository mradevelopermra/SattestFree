package com.mra.satpro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mra.satpro.dto.PasswordDTo;
import com.mra.satpro.models.Vidas;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoPassword extends SQLiteOpenHelper {

	public BaseDaoPassword(Context context) {
		super(context, "PasswordDiesiseis1.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String creaTablaPttoDetalle="CREATE TABLE usuarioypasword(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"Nombre VARCHAR(300) NOT NULL, " +
				"Password VARCHAR(300) NOT NULL, " +
				"Mail VARCHAR(300) NOT NULL, " +
				"Estatus VARCHAR(10) NOT NULL)";
		db.execSQL(creaTablaPttoDetalle);

		String creaTablaVidas="CREATE TABLE vidas(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"numeroVidas INTEGER NOT NULL, " +
				"fecha VARCHAR(300) NOT NULL, " +
				"estatus VARCHAR(10) NOT NULL)";
		db.execSQL(creaTablaVidas);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("drop table if exists usuarioypasword");
		onCreate(db);
		
	}
	
	
	public boolean insertaPassword(PasswordDTo dto){

		boolean estado1;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datosptto.put("Nombre", dto.getNombre());
			datosptto.put("Password", dto.getPassword());
			datosptto.put("Mail", dto.getMail());
			datosptto.put("Estatus", dto.getEstatus());

			
			resultado1=(int)this.getWritableDatabase().insert("usuarioypasword", "Nombre, Password, Mail, Estatus", datosptto);
			
			if(resultado1>0)estado1=true;
			else estado1=false;

			Log.e("estado1", estado1+"");
		
		}catch(Exception e){
			estado1=false;
		}


		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		return estado1;
		
	}

	public boolean insertaVida(Vidas dto){

		boolean estado1;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datosptto.put("numeroVidas", dto.getNumeroVidas());
			datosptto.put("fecha", dto.getFecha());
			datosptto.put("estatus", dto.getEstatus());

			resultado1=(int)this.getWritableDatabase().insert("vidas", "numeroVidas, fecha, estatus", datosptto);

			if(resultado1>0)estado1=true;
			else estado1=false;

			Log.e("estado1", estado1+"");

		}catch(Exception e){
			estado1=false;
		}


		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		return estado1;

	}

	public List consultaVidas(String fechaConsulta){


		String argumento = String.valueOf(fechaConsulta);

		String query = "SELECT * FROM vidas WHERE fecha ="+ '"' + fechaConsulta + '"';
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		ArrayList<Vidas> regresamos1= new ArrayList<Vidas>();

		Log.e("regresamos1", regresamos1.size()+"");

		int Iid1,
				InumeroVidas,
				Ifecha,
				Iestatus;

		Iid1=c.getColumnIndex("id");
		InumeroVidas=c.getColumnIndex("numeroVidas");
		Ifecha=c.getColumnIndex("fecha");
		Iestatus=c.getColumnIndex("estatus");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Vidas dto=new Vidas();
			dto.setId(c.getInt(Iid1));
			dto.setNumeroVidas(c.getInt(InumeroVidas));
			dto.setFecha(c.getString(Ifecha));
			dto.setEstatus(c.getString(Iestatus));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}


	public boolean updateVidas(Vidas datos2){
		String[] argumento ={String.valueOf(datos2.getFecha())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("numeroVidas", datos2.getNumeroVidas());

		resultado=this.getWritableDatabase().update("vidas", datos, "fecha=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();


		if (resultado>0)return true;
		else return false;

	}
	
	
	public List ConsultarPttoDetalle(int vid){
		
		
		String argumento = String.valueOf(vid);
		
		String query = "SELECT * FROM usuarioypasword WHERE id ="+ argumento;
		//String query = "SELECT * FROM usuarioypasword";
		SQLiteDatabase db = this.getWritableDatabase();
	      Cursor c = null;
	         c= db.rawQuery(query, null);    

		List regresamos1= new ArrayList();

		Log.e("regresamos1", regresamos1.size()+"");

		int Iid1,
		IFechaAsignacion1, 
		IEstatus1, 
		IConcepto,
		IMonto; 
		
		Iid1=c.getColumnIndex("id");
		IFechaAsignacion1=c.getColumnIndex("Nombre");
		IEstatus1=c.getColumnIndex("Password");
		IConcepto=c.getColumnIndex("Mail");
		IMonto=c.getColumnIndex("Estatus");
	
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			
			PasswordDTo dto=new PasswordDTo();
			dto.setId(c.getInt(Iid1));
			dto.setNombre(c.getString(IFechaAsignacion1));
			dto.setPassword(c.getString(IEstatus1));
			dto.setMail(c.getString(IConcepto));
			dto.setEstatus(c.getString(IMonto));
			

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;

		
	}
	
	
	public boolean ActualizarPassword(PasswordDTo datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		//datos.put("Nombre", datos2.getNombre());
		//datos.put("Password", datos2.getPassword());
		//datos.put("Mail", datos2.getMail());
		datos.put("Estatus", datos2.getEstatus());

		resultado=this.getWritableDatabase().update("usuarioypasword",datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;
		
	}

}
