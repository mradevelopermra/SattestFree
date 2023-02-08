package com.mra.satpro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mra.satpro.dto.AyudaDTO;
import com.mra.satpro.dto.BilleteDTO;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ExamenDTO;
import com.mra.satpro.dto.ExamenResultadosDTO;
import com.mra.satpro.dto.PlanEstudiosDTO;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoImagenBillete extends SQLiteOpenHelper {

	public BaseDaoImagenBillete(Context context) {
		super(context, "Examen5.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String creaTablaPttoDetalle="CREATE TABLE tableimage(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"Nombre VARCHAR(300) NOT NULL, " +
				"Image blob NOT NULL)";
		db.execSQL(creaTablaPttoDetalle);

		String creaTablaAyuda="CREATE TABLE ayuda(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"nombreModulo VARCHAR(50) NOT NULL, " +
				"totalPreguntas DOUBLE NOT NULL, " +
				"fecha date NOT NULL default '0000-00-00', " +
				"aciertos DOUBLE NOT NULL)";
		db.execSQL(creaTablaAyuda);

		String creaTablaExamen="CREATE TABLE examen(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"totalPreguntas DOUBLE NOT NULL, " +
				"fecha date NOT NULL default '0000-00-00', " +
				"tipoTest TEXT NOT NULL, " +
				"aciertos DOUBLE NOT NULL)";
		db.execSQL(creaTablaExamen);

		String creaTablaExamenResultados="CREATE TABLE examenresultados(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"pregunta TEXT NOT NULL, " +
				"preguntaImagen VARCHAR(100) NOT NULL, " +
				"respuesta TEXT NOT NULL, " +
				"correcta TEXT NOT NULL, " +
				"tooltip TEXT NOT NULL, " +
				"tooltipImagen VARCHAR(100) NOT NULL, " +
				"materia VARCHAR(50) NOT NULL, " +
				"fecha date NOT NULL default '0000-00-00', " +
				"aciertos DOUBLE NOT NULL) ";
		db.execSQL(creaTablaExamenResultados);

		String creaTablaEscuelas="CREATE TABLE opcionesescuelas(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"escuelalicenciatura VARCHAR(800) NOT NULL, " +
				"aciertos INTEGER NOT NULL)";
		db.execSQL(creaTablaEscuelas);

		String creaTablaPlanEstudio="CREATE TABLE planestudio(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"materia VARCHAR(50) NOT NULL, " +
				"nopreguntas INTEGER NOT NULL, " +
				"aciertosobtenidos INTEGER NOT NULL)";
		db.execSQL(creaTablaPlanEstudio);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("drop table if exists tableimage");
		onCreate(db);

		db.execSQL("drop table if exists ayuda");
		onCreate(db);

		db.execSQL("drop table if exists examen");
		onCreate(db);

		db.execSQL("drop table if exists examenresultados");
		onCreate(db);

		db.execSQL("drop table if exists opcionesescuelas");
		onCreate(db);

		db.execSQL("drop table if exists planestudio");
		onCreate(db);
		
	}
	
	
	public boolean insertaImagenBillete(BilleteDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datosptto.put("Nombre", dto.getNombre());
			datosptto.put("Image", dto.getImage());

			resultado1=(int) db.insert("tableimage", "Nombre, Image", datosptto);
			
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


	public List ConsultarTodoBilletes(){


		String query = "SELECT * FROM tableimage";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		Log.e("query", query);

		List regresamos1= new ArrayList();

		int Iid1,
				IFechaAsignacion1,
				IEstatus1,
				IConcepto,
				IMonto;

		Iid1=c.getColumnIndex("id");
		IFechaAsignacion1=c.getColumnIndex("Nombre");
		IEstatus1=c.getColumnIndex("Image");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			BilleteDTO dto=new BilleteDTO();
			dto.setId(c.getInt(Iid1));
			dto.setNombre(c.getString(IFechaAsignacion1));
			dto.setImage(c.getBlob(IEstatus1));

			regresamos1.add(dto);
		}


		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarImagenBillete(int vid){


		String argumento = String.valueOf(vid);

		String query = "SELECT * FROM tableimage WHERE id ="+ argumento;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);



		List regresamos1= new ArrayList();

		int Iid1,
				IFechaAsignacion1,
				IEstatus1,
				IConcepto,
				IMonto;

		Iid1=c.getColumnIndex("id");
		IFechaAsignacion1=c.getColumnIndex("Nombre");
		IEstatus1=c.getColumnIndex("Image");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			BilleteDTO dto=new BilleteDTO();
			dto.setId(c.getInt(Iid1));
			dto.setNombre(c.getString(IFechaAsignacion1));
			dto.setImage(c.getBlob(IEstatus1));


			regresamos1.add(dto);
		}


		c.close();
		db.close();

		return regresamos1;


	}
	
	
	public boolean ActualizarImagenBillete(BilleteDTO datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		datos.put("Nombre", datos2.getNombre());
		datos.put("Image", datos2.getImage());


		resultado=this.getWritableDatabase().update("tableimage", datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();


		if (resultado>0)return true;
		else return false;
		
	}

	//Mensajes de ayuda

	public boolean insertaAyuda(AyudaDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		try{
			datosptto.put("nombreModulo", dto.getNombreModulo());
			datosptto.put("totalPreguntas", dto.getTotalPreguntas());
			datosptto.put("fecha", dto.getFecha());
			datosptto.put("aciertos", dto.getAciertos());

			resultado1=(int)this.getWritableDatabase().insert("ayuda", "nombreModulo, totalPreguntas, fecha, aciertos", datosptto);

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


	public List ConsultarAyuda(String vid){

		String argumento = vid;

		String query = "SELECT * FROM ayuda WHERE nombreModulo ="+ "'" + argumento + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		Log.e("query", query);

		List regresamos1= new ArrayList();

		int Iid1,
				Imodulo,
				ItotalPreguntas,
				Ifecha,
				Iaciertos;

		Iid1=c.getColumnIndex("id");
		Imodulo=c.getColumnIndex("nombreModulo");
		ItotalPreguntas=c.getColumnIndex("totalPreguntas");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			AyudaDTO dto=new AyudaDTO();
			dto.setId(c.getInt(Iid1));
			dto.setNombreModulo(c.getString(Imodulo));
			dto.setTotalPreguntas(c.getDouble(ItotalPreguntas));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarTodoAyuda(){

		String query = "SELECT * FROM ayuda";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1,
				Imodulo,
				ItotalPreguntas,
				Ifecha,
				Iaciertos;

		Iid1=c.getColumnIndex("id");
		Imodulo=c.getColumnIndex("nombreModulo");
		ItotalPreguntas=c.getColumnIndex("totalPreguntas");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			AyudaDTO dto=new AyudaDTO();
			dto.setId(c.getInt(Iid1));
			dto.setNombreModulo(c.getString(Imodulo));
			dto.setTotalPreguntas(c.getDouble(ItotalPreguntas));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public boolean ActualizarModulo(AyudaDTO datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("nombreModulo", datos2.getNombreModulo());
		datos.put("totalPreguntas", datos2.getTotalPreguntas());
		datos.put("fecha", datos2.getFecha());
		datos.put("aciertos", datos2.getAciertos());

		resultado=this.getWritableDatabase().update("ayuda", datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	//Examen

	public boolean insertaExamen(ExamenDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datosptto.put("totalPreguntas", dto.getTotalPreguntas());
			datosptto.put("fecha", dto.getFecha());
			datosptto.put("aciertos", dto.getAciertos());
			datosptto.put("tipoTest", dto.getTipoTest());
			resultado1=(int)this.getWritableDatabase().insert("examen", "totalPreguntas, fecha, aciertos, tipoTest", datosptto);

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

	public List ConsultarExamenes(){

		String query = "select * from examen";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid1,
				Imodulo,
				ItotalPreguntas,
				Ifecha,
				Iaciertos, ItipoTest;

		Iid1=c.getColumnIndex("id");
		ItotalPreguntas=c.getColumnIndex("totalPreguntas");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");
		ItipoTest=c.getColumnIndex("tipoTest");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ExamenDTO dto=new ExamenDTO();
			dto.setId(c.getInt(Iid1));
			dto.setTotalPreguntas(c.getDouble(ItotalPreguntas));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));
			dto.setTipoTest(c.getString(ItipoTest));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	//Examen resultados

	public boolean insertaExamenResultados(ExamenResultadosDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		try{
			datosptto.put("pregunta", dto.getPregunta());
			datosptto.put("preguntaImagen", dto.getPreguntaImagen());
			datosptto.put("respuesta", dto.getRespuesta());
			datosptto.put("correcta", dto.getCorrecta());
			datosptto.put("tooltip", dto.getTooltip());
			datosptto.put("tooltipImagen", dto.getTooltipImagen());
			datosptto.put("materia", dto.getMateria());
			datosptto.put("fecha", dto.getFecha());
			datosptto.put("aciertos", dto.getAciertos());

			//Log.e("datosptto", datosptto+"");

			resultado1=(int)this.getWritableDatabase().insert("examenresultados", "pregunta, " +
					"preguntaImagen, respuesta, correcta, tooltip, tooltipImagen, materia, fecha, aciertos", datosptto);

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

	public List ConsultarExamenResultados(){

		String query = "select * from examenresultados";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
				Ipregunta,
				IpreguntaImagen,
				Irespuesta,
				Icorrecta,
				Itooltip,
				ItooltipImagen,
				Imateria,
				Ifecha,
				Iaciertos;

		Iid=c.getColumnIndex("id");
		Ipregunta=c.getColumnIndex("pregunta");
		IpreguntaImagen=c.getColumnIndex("preguntaImagen");
		Irespuesta=c.getColumnIndex("respuesta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		ItooltipImagen=c.getColumnIndex("tooltipImagen");
		Imateria=c.getColumnIndex("materia");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ExamenResultadosDTO dto=new ExamenResultadosDTO();
			dto.setId(c.getInt(Iid));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setPreguntaImagen(c.getString(IpreguntaImagen));
			dto.setRespuesta(c.getString(Irespuesta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setTooltipImagen(c.getString(ItooltipImagen));
			dto.setMateria(c.getString(Imateria));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenResultadosPorId(int id){

		String query = "select * from examenresultados where id=" + id;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
				Ipregunta,
				IpreguntaImagen,
				Irespuesta,
				Icorrecta,
				Itooltip,
				ItooltipImagen,
				Imateria,
				Ifecha,
				Iaciertos;

		Iid=c.getColumnIndex("id");
		Ipregunta=c.getColumnIndex("pregunta");
		IpreguntaImagen=c.getColumnIndex("preguntaImagen");
		Irespuesta=c.getColumnIndex("respuesta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		ItooltipImagen=c.getColumnIndex("tooltipImagen");
		Imateria=c.getColumnIndex("materia");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ExamenResultadosDTO dto=new ExamenResultadosDTO();
			dto.setId(c.getInt(Iid));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setPreguntaImagen(c.getString(IpreguntaImagen));
			dto.setRespuesta(c.getString(Irespuesta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setTooltipImagen(c.getString(ItooltipImagen));
			dto.setMateria(c.getString(Imateria));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarExamenResultadosPorMateria(String materia){

		String query = "select * from examenresultados where materia=" + materia;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
				Ipregunta,
				IpreguntaImagen,
				Irespuesta,
				Icorrecta,
				Itooltip,
				ItooltipImagen,
				Imateria,
				Ifecha,
				Iaciertos;

		Iid=c.getColumnIndex("id");
		Ipregunta=c.getColumnIndex("pregunta");
		IpreguntaImagen=c.getColumnIndex("preguntaImagen");
		Irespuesta=c.getColumnIndex("respuesta");
		Icorrecta=c.getColumnIndex("correcta");
		Itooltip=c.getColumnIndex("tooltip");
		ItooltipImagen=c.getColumnIndex("tooltipImagen");
		Imateria=c.getColumnIndex("materia");
		Ifecha=c.getColumnIndex("fecha");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			ExamenResultadosDTO dto=new ExamenResultadosDTO();
			dto.setId(c.getInt(Iid));
			dto.setPregunta(c.getString(Ipregunta));
			dto.setPreguntaImagen(c.getString(IpreguntaImagen));
			dto.setRespuesta(c.getString(Irespuesta));
			dto.setCorrecta(c.getString(Icorrecta));
			dto.setTooltip(c.getString(Itooltip));
			dto.setTooltipImagen(c.getString(ItooltipImagen));
			dto.setMateria(c.getString(Imateria));
			dto.setFecha(c.getString(Ifecha));
			dto.setAciertos(c.getDouble(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public boolean ExamenResultadosActualiza(ExamenResultadosDTO datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("pregunta", datos2.getPregunta());
		datos.put("preguntaImagen", datos2.getPreguntaImagen());
		datos.put("respuesta", datos2.getRespuesta());
		datos.put("correcta", datos2.getCorrecta());
		datos.put("tooltip", datos2.getTooltip());
		datos.put("tooltipImagen", datos2.getTooltipImagen());
		datos.put("materia", datos2.getMateria());
		datos.put("fecha", datos2.getFecha());
		datos.put("aciertos", datos2.getAciertos());

		resultado=this.getWritableDatabase().update("examenresultados", datos, "id=?", argumento);

		//Log.e("resultado", datos+"");

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	//diagnostico escuelas

	public boolean insertaEscuelas(EscuelasDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();


		try{
			datosptto.put("escuelalicenciatura", dto.getEscuelaLicenciatura());
			datosptto.put("aciertos", dto.getAciertos());

			resultado1=(int)this.getWritableDatabase().insert("opcionesescuelas", "escuelalicenciatura, " +
					" aciertos", datosptto);

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

	public List ConsultarEscuelaSeleccionada(){

		String query = "select * from opcionesescuelas";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
		Iescuela,
		Iaciertos;

		Iid=c.getColumnIndex("id");
		Iescuela=c.getColumnIndex("escuelalicenciatura");
		Iaciertos=c.getColumnIndex("aciertos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			EscuelasDTO dto=new EscuelasDTO();
			dto.setId(c.getInt(Iid));
			dto.setEscuelaLicenciatura(c.getString(Iescuela));
			dto.setAciertos(c.getInt(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public boolean ActualizaEscuelaSeleccionada(EscuelasDTO datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("escuelalicenciatura", datos2.getEscuelaLicenciatura());
		datos.put("aciertos", datos2.getAciertos());

		resultado=this.getWritableDatabase().update("opcionesescuelas", datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	//plan de estudios

	public boolean insertaPlanEstudios(PlanEstudiosDTO dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosptto= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datosptto.put("materia", dto.getMateria());
			datosptto.put("nopreguntas", dto.getNopreguntas());
			datosptto.put("aciertosobtenidos", dto.getAciertosobtenidos());

			resultado1=(int)this.getWritableDatabase().insert("planestudio", "materia, nopreguntas," +
					" aciertos", datosptto);

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

	public List ConsultarPlanEstudios(){

		String query = "select * from planestudio";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
				Imateria,
				InoPreguntas,
				Iaciertos;

		Iid=c.getColumnIndex("id");
		Imateria=c.getColumnIndex("materia");
		InoPreguntas=c.getColumnIndex("nopreguntas");
		Iaciertos=c.getColumnIndex("aciertosobtenidos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			PlanEstudiosDTO dto=new PlanEstudiosDTO();
			dto.setId(c.getInt(Iid));
			dto.setMateria(c.getString(Imateria));
			dto.setNopreguntas(c.getInt(InoPreguntas));
			dto.setAciertosobtenidos(c.getInt(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List ConsultarPlanEstudiosPorId(int contador){

		String query = "select * from planestudio where nopreguntas =" + contador;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);


		List regresamos1= new ArrayList();

		int Iid,
				Imateria,
				InoPreguntas,
				Iaciertos;

		Iid=c.getColumnIndex("id");
		Imateria=c.getColumnIndex("materia");
		InoPreguntas=c.getColumnIndex("nopreguntas");
		Iaciertos=c.getColumnIndex("aciertosobtenidos");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			PlanEstudiosDTO dto=new PlanEstudiosDTO();
			dto.setId(c.getInt(Iid));
			dto.setMateria(c.getString(Imateria));
			dto.setNopreguntas(c.getInt(InoPreguntas));
			dto.setAciertosobtenidos(c.getInt(Iaciertos));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public boolean ActualizaPlanEstudios(PlanEstudiosDTO datos2){
		String[] argumento ={String.valueOf(datos2.getId())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("materia", datos2.getMateria());
		datos.put("nopreguntas", datos2.getNopreguntas());
		datos.put("aciertosobtenidos", datos2.getAciertosobtenidos());

		resultado=this.getWritableDatabase().update("planestudio", datos, "id=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	public boolean eliminaCarreras(){
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		resultado=this.getWritableDatabase().delete("opcionesescuelas", null, null);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;


	}

}
