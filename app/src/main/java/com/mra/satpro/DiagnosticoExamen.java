package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ExamenResultadosDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;

public class DiagnosticoExamen extends AppCompatActivity implements View.OnClickListener {

    ListView listamateriasplan, listaopciones;
    ArrayList<HashMap<String, Object>> productsList;

    ArrayList<HashMap<String, Object>> productsListescuelas;

    private static final String TAG_PID = "||pid";
    private static final String TAG_MATERIA = "||materia";
    private static final String TAG_ACIERTOS = "||aciertos";
    private static final String TAG_NO_ACIERTOS = "||nopreguntas";
    private static final String TAG_PREGUNTAS = "||preguntas";

    private static final String TAG_ESCUELA_IMAGEN = "||escuelaimagen";
    private static final String TAG_ESCUELA = "||escuela";
    private static final String TAG_ACIERTOS_MINIMO = "||aciertosminimos";

    Button btnestudiar;

    private ImageView img_flecha;

    String areaLicenciatura = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_examen);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        btnestudiar = (Button) findViewById(R.id.btnestudiar);
        btnestudiar.setOnClickListener(this);

        listamateriasplan = (ListView) findViewById(R.id.listamateriasplan);
        //listaopciones = (ListView) findViewById(R.id.listaopcionescal);

        //consultarEscuelasSeleccionadas();
        buscaOpciones();
        consultar();

    }


    public void buscaOpciones() {
        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listaopciones = conexion.ConsultarEscuelaSeleccionada();


        for(Object datos: listaopciones) {

            EscuelasDTO elementosIng = (EscuelasDTO) datos;

            int imagenEscuela = 0;
            String escuela = elementosIng.getEscuelaLicenciatura();



            for (int i = 0; i < arrayAreas.length; i++) {
                if (escuela.trim().equalsIgnoreCase(arrayAreas[i].trim())) {
                    areaLicenciatura = arrayClasificacionAreas[i];
                }
            }
        }


    }

    public void consultarEscuelasSeleccionadas(){

        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listas = conexion.ConsultarEscuelaSeleccionada();

        String[] conceptoIngreso= new String[listas.size()];

        productsListescuelas = new ArrayList<HashMap<String, Object>>();

        List<String> tipo = new ArrayList<String>();

        int contenedor=0;
        List listas2 = null;

        String materiaTmp[]= new String[listas.size()];
        String materiaNombreTmp[]= new String[listas.size()];
        String conI[]= new String[listas.size()];
        int imaI[]= new int[listas.size()];
        int contador =0;



        int numeroPregunta = 1;
        int contadorMateria = 0;

        for(Object datos: listas){

            EscuelasDTO elementosIng=(EscuelasDTO) datos;

            String escuela = elementosIng.getEscuelaLicenciatura();
            String aciertosEscuela = "Para esta escuela requieres: " + elementosIng.getAciertos() + " aciertos";

            HashMap<String, Object> map = new HashMap<String, Object>();



            // adding each child node to HashMap key => value
            map.put(TAG_ESCUELA_IMAGEN, R.drawable.grafica);
            map.put(TAG_ESCUELA, escuela);
            map.put(TAG_ACIERTOS_MINIMO, aciertosEscuela);



            // adding HashList to ArrayList
            productsListescuelas.add(map);


        }




        /**
         * Updating parsed JSON data into ListView
         * */




        ListAdapter adapter3 = new SimpleAdapter(DiagnosticoExamen.this,
                productsListescuelas, R.layout.listalicenciaturas, new String[] { TAG_ESCUELA_IMAGEN,
                TAG_ESCUELA, TAG_ACIERTOS_MINIMO},
                new int[] { R.id.imagenMateria, R.id.txtlicencaituras, R.id.txtresultados});
        listaopciones.setAdapter(adapter3);
        //Listadetalle1.setAdapter(new dataListAdapter(tipoI,
        //		conI, imaI));

    }

    public void consultar(){

        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listas = conexion.ConsultarExamenResultados();

        String[] conceptoIngreso= new String[listas.size()];

        productsList = new ArrayList<HashMap<String, Object>>();

        List<String> tipo = new ArrayList<String>();

        int contenedor=0;
        List listas2 = null;

        String materiaTmp[]= new String[listas.size()];
        String materiaNombreTmp[]= new String[listas.size()];
        String conI[]= new String[listas.size()];
        int imaI[]= new int[listas.size()];
        int contador =0;



        int numeroPregunta = 1;
        int contadorMateria = 0;

        for(Object datos: listas){

            ExamenResultadosDTO elementosIng=(ExamenResultadosDTO) datos;
            materiaTmp[contadorMateria] = elementosIng.getMateria();
            Log.e("materiasExamen", elementosIng.getMateria()+"");

            if(elementosIng.getMateria().equalsIgnoreCase("1")){
                materiaNombreTmp[contadorMateria] = "Espa??ol";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("7")){
                materiaNombreTmp[contadorMateria] = "Literatura";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("3")){
                materiaNombreTmp[contadorMateria] = "Historia Universal";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("8")){
                materiaNombreTmp[contadorMateria] = "Historia de M??xico";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("2")){
                materiaNombreTmp[contadorMateria] = "Filosof??a";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("6")){
                materiaNombreTmp[contadorMateria] = "Matem??ticas";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("12")){
                materiaNombreTmp[contadorMateria] = "F??sica";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("11")){
                materiaNombreTmp[contadorMateria] = "Qu??mica";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("4")){
                materiaNombreTmp[contadorMateria] = "Biolog??a";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("5")){
                materiaNombreTmp[contadorMateria] = "Geograf??a";
            }


            contadorMateria = contadorMateria + 1;
        }

        String[] materiasFinal = new HashSet<String>(Arrays.asList(materiaTmp)).toArray(new String[0]);
        String[] materiasNombreFinal = new HashSet<String>(Arrays.asList(materiaNombreTmp)).toArray(new String[0]);


        List listaMaterias;

        for(int i=0; i<materiasFinal.length; i++){

            Log.e("materiasFinal", materiasFinal[i]+"");

            listaMaterias = conexion.ConsultarExamenResultadosPorMateria(materiasFinal[i]);



            String materiaLista = materiasFinal[i];
            int numAciertos = 0;
            String resultadoObtenido = "";
            int numPregRecomendacion = 0;
            String nombreMateria = "";
            String recomendacion = "";

            //aciertos por materia
            int numAciertosEspanol = 0;
            int numAciertosLiteratura = 0;
            int numAciertosHisUniv = 0;
            int numAciertosHisMex = 0;
            int numAciertosFilos = 0;
            int numAciertosMate = 0;
            int numAciertosFisica = 0;
            int numAciertosQuimica = 0;
            int numAciertosBiolo = 0;
            int numAciertosGeografia = 0;


            for(Object datos: listaMaterias){

                ExamenResultadosDTO elementosIng=(ExamenResultadosDTO) datos;

                double aciertos = elementosIng.getAciertos();
                numAciertos = numAciertos + (int)aciertos;

            }

            BaseDaoExamen con = new BaseDaoExamen(this);


            Log.e("areaLicenciatura", areaLicenciatura+"");

            if(areaLicenciatura.equalsIgnoreCase("??rea 1: Ciencias F??sico Matem??ticas y de las Ingenierias")){
                numAciertosEspanol = 18;
                numAciertosLiteratura = 10;
                numAciertosHisUniv = 10;
                numAciertosHisMex = 10;
                numAciertosFilos = 0;
                numAciertosMate = 26;
                numAciertosFisica = 16;
                numAciertosQuimica = 10;
                numAciertosBiolo = 10;
                numAciertosGeografia = 10;

            }
            if(areaLicenciatura.equalsIgnoreCase("??rea 2: Ciencias Biol??gicas, Quimicas y de la Salud")){
                numAciertosEspanol = 18;
                numAciertosLiteratura = 10;
                numAciertosHisUniv = 10;
                numAciertosHisMex = 10;
                numAciertosFilos = 0;
                numAciertosMate = 24;
                numAciertosFisica = 12;
                numAciertosQuimica = 13;
                numAciertosBiolo = 13;
                numAciertosGeografia = 10;

            }
            if(areaLicenciatura.equalsIgnoreCase("??rea 3: Ciencias Sociales")){
                numAciertosEspanol = 18;
                numAciertosLiteratura = 10;
                numAciertosHisUniv = 14;
                numAciertosHisMex = 14;
                numAciertosFilos = 0;
                numAciertosMate = 24;
                numAciertosFisica = 10;
                numAciertosQuimica = 10;
                numAciertosBiolo = 10;
                numAciertosGeografia = 10;

            }
            if(areaLicenciatura.equalsIgnoreCase("??rea 4: Humanidades y Artes")){
                numAciertosEspanol = 18;
                numAciertosLiteratura = 10;
                numAciertosHisUniv = 10;
                numAciertosHisMex = 10;
                numAciertosFilos = 10;
                numAciertosMate = 22;
                numAciertosFisica = 10;
                numAciertosQuimica = 10;
                numAciertosBiolo = 10;
                numAciertosGeografia = 10;
            }

            if(materiasFinal[i].equalsIgnoreCase("1")){
                nombreMateria = "Espa??ol";

                String query = "select * from tblpreguntasmodulo where idmodulo = 1";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosEspanol){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosEspanol/2) && numAciertos<numAciertosEspanol){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosEspanol/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("7")){
                nombreMateria = "Literatura";

                String query = "select * from tblpreguntasmodulo where idmodulo = 7";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosLiteratura){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosLiteratura/2) && numAciertos<numAciertosLiteratura){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosLiteratura/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("3")){
                nombreMateria = "Historia universal";

                String query = "select * from tblpreguntasmodulo where idmodulo = 3";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosHisUniv){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosHisUniv/2) && numAciertos<numAciertosHisUniv){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosHisUniv/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("8")){
                nombreMateria = "Historia m??xico";

                String query = "select * from tblpreguntasmodulo where idmodulo = 8";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosHisMex){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosHisMex/2) && numAciertos<numAciertosHisMex){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosHisMex/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }

            }

            if(materiasFinal[i].equalsIgnoreCase("5")){
                nombreMateria = "Geograf??a";

                String query = "select * from tblpreguntasmodulo where idmodulo = 5";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosGeografia){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosGeografia/2) && numAciertos<numAciertosGeografia){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosGeografia/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }


            if(materiasFinal[i].equalsIgnoreCase("2")){
                nombreMateria = "Filosof??a";

                String query = "select * from tblpreguntasmodulo where idmodulo = 2";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosFilos){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosFilos/2) && numAciertos<numAciertosFilos){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosFilos/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertosFilos==0){
                    numPregRecomendacion = 0;
                    recomendacion = "No aplica";
                }
            }


            if(materiasFinal[i].equalsIgnoreCase("6")){
                nombreMateria = "Matem??ticas";

                String query = "select * from tblpreguntasmodulo where idmodulo = 6";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosMate){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosMate/2) && numAciertos<numAciertosMate){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosMate/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("12")){
                nombreMateria = "F??sica";

                String query = "select * from tblpreguntasmodulo where idmodulo = 12";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosFisica){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosFisica/2) && numAciertos<numAciertosFisica){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosFisica/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("11")){
                nombreMateria = "Qu??mica";

                String query = "select * from tblpreguntasmodulo where idmodulo = 11";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosQuimica){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosQuimica/2) && numAciertos<numAciertosQuimica){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosQuimica/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("4")){
                nombreMateria = "Biolog??a";

                String query = "select * from tblpreguntasmodulo where idmodulo = 4";
                List listaPreguntas = con.ConsultarExamenesRandomTodas(query);

                if(numAciertos==numAciertosBiolo){
                    numPregRecomendacion = (int)(0);
                    recomendacion = "??Excelente trabajo, concentrate en otras materias!";
                }
                if(numAciertos>(numAciertosBiolo/2) && numAciertos<numAciertosBiolo){
                    numPregRecomendacion = (int)(listaPreguntas.size()/2);
                    recomendacion = "??De Panzazo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
                if(numAciertos<=(numAciertosBiolo/2)){
                    numPregRecomendacion = (int)(listaPreguntas.size());
                    recomendacion = "??Muy Bajo!, Estudia al menos " + numPregRecomendacion + " preguntas";
                }
            }

            resultadoObtenido = "Obtuviste " + numAciertos + " aciertos";

            HashMap<String, Object> map = new HashMap<String, Object>();

            // adding each child node to HashMap key => value

            map.put(TAG_MATERIA, nombreMateria);
            map.put(TAG_ACIERTOS, resultadoObtenido);
            //map.put(TAG_ACIERTOS, "");
            map.put(TAG_NO_ACIERTOS, recomendacion);
            map.put(TAG_PREGUNTAS, numPregRecomendacion);


            // adding HashList to ArrayList
            productsList.add(map);


        }


        /**
         * Updating parsed JSON data into ListView
         * */




        ListAdapter adapter3 = new SimpleAdapter(DiagnosticoExamen.this,
                productsList, R.layout.listamaterias, new String[] { TAG_MATERIA,
                TAG_NO_ACIERTOS, TAG_ACIERTOS, TAG_PREGUNTAS},
                new int[] { R.id.txtMaterias, R.id.txtNoPreguntas, R.id.txtrecomendacion, R.id.txtNoPrePlan});
        listamateriasplan.setAdapter(adapter3);
        //Listadetalle1.setAdapter(new dataListAdapter(tipoI,
        //		conI, imaI));

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

            finish();
            Intent linsertar=new Intent(DiagnosticoExamen.this, MainActivity.class);
            startActivity(linsertar);

           /* img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(ResultadoExamen.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
        }

        if(v.getId()==findViewById(R.id.btnestudiar).getId()){

            finish();
            Intent linsertar=new Intent(DiagnosticoExamen.this, Modulo.class);
            linsertar.putExtra("tipoTest", "all");
            linsertar.putExtra("autoAyuda", "Si");
            linsertar.putExtra("preguntasTotal", "1");
            startActivity(linsertar);


        }


    }
}
