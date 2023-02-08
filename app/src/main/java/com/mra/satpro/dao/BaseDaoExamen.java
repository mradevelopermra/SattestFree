package com.mra.satpro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mra.satpro.dto.ModulosDTO;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoExamen extends SQLiteOpenHelper {

	public BaseDaoExamen(Context context) {
		super(context, "ExamenAleatorio4.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub



		String creaTablaExamenSimulacro="CREATE TABLE tblpreguntasmodulo(" +
				"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idmodulo INTEGER NOT NULL, " +
				"pregunta TEXT NOT NULL, " +
				"respuestauno TEXT NOT NULL, " +
				"respuestados TEXT NOT NULL, " +
				"respuestatres TEXT NOT NULL, " +
				"respuestacuatro TEXT NOT NULL, " +
				"imagenpregunta VARCHAR(100) NOT NULL, " +
				"correcta TEXT NOT NULL, " +
				"tooltip TEXT NOT NULL, " +
				"imagentooltip VARCHAR(100) NOT NULL, " +
				"audiopregunta TEXT  , " +
				"audiotooltip TEXT )";
		db.execSQL(creaTablaExamenSimulacro);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub


		db.execSQL("drop table if exists tblpreguntasmodulo");
		onCreate(db);
		
	}
	

	//Consulta inserta moduos

	public boolean insertaModulosExamen(ModulosDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		try{
			datosptto.put("idmodulo", dto.getIdmodulo());
			datosptto.put("pregunta", dto.getPregunta());
			datosptto.put("respuestauno", dto.getRespuestauno());
			datosptto.put("respuestados", dto.getRespuestados());
			datosptto.put("respuestatres", dto.getRespuestatres());
			datosptto.put("respuestacuatro", dto.getRespuestacuatro());
			datosptto.put("imagenpregunta", dto.getImagenpregunta());
			datosptto.put("correcta", dto.getCorrecta());
			datosptto.put("tooltip", dto.getTooltip());
			datosptto.put("imagentooltip", dto.getImagentooltip());
			datosptto.put("audiopregunta", dto.getAudiopregunta());
			datosptto.put("audiotooltip", dto.getAudiotooltip());

			resultado1=(int)this.getWritableDatabase().insert("tblpreguntasmodulo",
					"idmodulo, pregunta, respuestauno, respuestados, respuestatres, respuestacuatro, " +
							"imagenpregunta, correcta, tooltip, imagentooltip, audiopregunta, audiotooltip", datosptto);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();


		return estado1;

	}

	public boolean ActualizaModulosExamen(ModulosDTO dto){
		String[] argumento ={String.valueOf(dto.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("idmodulo", dto.getIdmodulo());
		datos.put("pregunta", dto.getPregunta());
		datos.put("respuestauno", dto.getRespuestauno());
		datos.put("respuestados", dto.getRespuestados());
		datos.put("respuestatres", dto.getRespuestatres());
		datos.put("respuestacuatro", dto.getRespuestacuatro());
		datos.put("imagenpregunta", dto.getImagenpregunta());
		datos.put("correcta", dto.getCorrecta());
		datos.put("tooltip", dto.getTooltip());
		datos.put("imagentooltip", dto.getImagentooltip());

		resultado=this.getWritableDatabase().update("tblpreguntasmodulo", datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	//Área 1: Ciencias Físico Matemáticas y de las Ingenierias

	public List ConsultarExamenesRandomTodas(String consulta){

		//String query = "select * from tblpreguntasmodulo where idmodulo = 1 ORDER BY RANDOM() LIMIT 18";
		String query = consulta;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip,
				IaudioPregunta,
				IaudioTolltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");
		IaudioPregunta=c.getColumnIndex("audiopregunta");
		IaudioTolltip=c.getColumnIndex("audiotooltip");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setId(c.getInt(Iid1));
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));
			dto.setAudiopregunta(c.getString(IaudioPregunta));
			dto.setAudiotooltip(c.getString(IaudioTolltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public List ConsultarExamenesRandomEspa(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 1 ORDER BY RANDOM() LIMIT 18";


		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomRazVerbal(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 12 ORDER BY RANDOM() LIMIT 16";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomHisUniv(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 6 ORDER BY RANDOM() LIMIT 26";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomHisMex(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 7 ORDER BY RANDOM() LIMIT 10";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public List ConsultarExamenesRandomGeoUniv(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 5 ORDER BY RANDOM() LIMIT 6";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomGeoMex(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 6 ORDER BY RANDOM() LIMIT 6";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomCivismo(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 7 ORDER BY RANDOM() LIMIT 12";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomRazMate(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 8 ORDER BY RANDOM() LIMIT 16";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomMatematicas(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 9 ORDER BY RANDOM() LIMIT 12";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomFisica(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 10 ORDER BY RANDOM() LIMIT 12";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomQuimica(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 11 ORDER BY RANDOM() LIMIT 12";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenesRandomBiologia(){

		String query = "select * from tblpreguntasmodulo where idmodulo = 12 ORDER BY RANDOM() LIMIT 12";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1, Iidmodulo,
				Ipregunta,
				Irespuestauno,
				Irespuestados,
				Irespuestatres,
				Irespuestacuatro,
				Iimagenpregunta,
				Icorrecta,
				Itooltip,
				Iimagentooltip;

		Iid1=c.getColumnIndex("id");
		Iidmodulo=c.getColumnIndex("idmodulo");
		Ipregunta=c.getColumnIndex("pregunta");
		Irespuestauno=c.getColumnIndex("respuestauno");
		Irespuestados=c.getColumnIndex("respuestados");
		Irespuestatres=c.getColumnIndex("respuestatres");
		Irespuestacuatro=c.getColumnIndex("respuestacuatro");
		Iimagenpregunta=c.getColumnIndex("imagenpregunta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		Iimagentooltip=c.getColumnIndex("imagentooltip");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setIdmodulo(c.getInt(Iidmodulo));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setRespuestauno(c.getString(Irespuestauno));
			dto.setRespuestados(c.getString(Irespuestados));
			dto.setRespuestatres(c.getString(Irespuestatres));
			dto.setRespuestacuatro(c.getString(Irespuestacuatro));
			dto.setImagenpregunta(c.getString(Iimagenpregunta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setImagentooltip(c.getString(Iimagentooltip));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public List ConsultarModulos(){

		String query = "select * from tblpreguntasmodulo";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int ItotalPreguntas;

		ItotalPreguntas=c.getColumnIndex("pregunta");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ModulosDTO dto=new ModulosDTO();
			dto.setPregunta(c.getString(ItotalPreguntas));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public boolean eliminaPreguntas(){
		int resultado;
		int restoreID;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		resultado=this.getWritableDatabase().delete("tblpreguntasmodulo", null, null);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;
	}

	public void resetAutoincrement(String tableName) {
		getWritableDatabase().execSQL(
				"delete from sqlite_sequence where name='" + tableName + "'"
		);
		Log.d("DATABASE", tableName + " autoincrement reset");
	}

}
