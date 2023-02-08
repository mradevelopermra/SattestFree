package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dao.BaseDaoImagenBillete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;

public class ListaClasesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView Listadetalle1;
    int LidFinal = 0;
    ArrayList<HashMap<String, Object>> productsList;
    TextView txtNombreMateria;
    ListView listaClases;


    private static final String TAG_CONCEPTO = "||conceptoIngreso";
    private static final String TAG_IMAGEN = "||strImagen";

    String materias [];

    int imagenesMaterias [];

    Button espanol, razverbal, histouniversal, historiamexico, geouniversal, geomexico;
    Button civismo, razmate, matema, fisica, quimica, biologia;

    String test = "";

    private ImageView img_flecha;
    //ListView listaMaterias;
    ArrayList<HashMap<String, Object>> productsListescuelas;

    String lostaMaterias[] = new String[3];

    int lostaMateriasImagen[] = new int[3];

    private static final String TAG_ESCUELA_IMAGEN = "||escuelaimagen";
    private static final String TAG_ESCUELA = "||escuela";
    private static final String TAG_ACIERTOS_MINIMO = "||aciertosminimos";
    private static final String TAG_ACIERTOS_OBTENIDOS = "||aciertosobtenidos";

    String division = "";

    String areaLicenciatura = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clases);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        intiControls();

        buscaOpciones();

        //if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){

            lostaMaterias = new String[8];
            lostaMateriasImagen = new int[8];

            lostaMaterias[0] = "Reading";
            lostaMaterias[1] = "Writing & Language";
            lostaMaterias[2] = "Math";
            lostaMaterias[3] = "Biology";
            lostaMaterias[4] = "Chemistry";
            lostaMaterias[5] = "World History";
            //lostaMaterias[6] = "Historia de México";
            lostaMaterias[6] = "Physics";
            lostaMaterias[7] = "Spanish";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[2] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[3] = R.mipmap.biologia_ios;
            lostaMateriasImagen[4] = R.mipmap.quimica_ios;
            lostaMateriasImagen[5] = R.mipmap.historia_universal_ios;
            //lostaMateriasImagen[6] = R.mipmap.historia_mex_ios;
            lostaMateriasImagen[6] = R.mipmap.fisica_ios;
            lostaMateriasImagen[7] = R.mipmap.matematicas_ios;

        //}
        /*if(areaLicenciatura.equalsIgnoreCase("Área 2: Ciencias Biológicas, Quimicas y de la Salud")){

            lostaMaterias = new String[9];
            lostaMateriasImagen = new int[9];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Literatura";
            lostaMaterias[2] = "Geografía";
            lostaMaterias[3] = "Biología";
            lostaMaterias[4] = "Química";
            lostaMaterias[5] = "Historia Universal";
            lostaMaterias[6] = "Historia de México";
            lostaMaterias[7] = "Física";
            lostaMaterias[8] = "Matemáticas";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[2] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[3] = R.mipmap.biologia_ios;
            lostaMateriasImagen[4] = R.mipmap.quimica_ios;
            lostaMateriasImagen[5] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[6] = R.mipmap.historia_mex_ios;
            lostaMateriasImagen[7] = R.mipmap.fisica_ios;
            lostaMateriasImagen[8] = R.mipmap.matematicas_ios;

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 3: Ciencias Sociales")){
            lostaMaterias = new String[9];
            lostaMateriasImagen = new int[9];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Literatura";
            lostaMaterias[2] = "Geografía";
            lostaMaterias[3] = "Biología";
            lostaMaterias[4] = "Química";
            lostaMaterias[5] = "Historia Universal";
            lostaMaterias[6] = "Historia de México";
            lostaMaterias[7] = "Física";
            lostaMaterias[8] = "Matemáticas";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[2] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[3] = R.mipmap.biologia_ios;
            lostaMateriasImagen[4] = R.mipmap.quimica_ios;
            lostaMateriasImagen[5] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[6] = R.mipmap.historia_mex_ios;
            lostaMateriasImagen[7] = R.mipmap.fisica_ios;
            lostaMateriasImagen[8] = R.mipmap.matematicas_ios;

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 4: Humanidades y Artes")){

            lostaMaterias = new String[10];
            lostaMateriasImagen = new int[10];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Literatura";
            lostaMaterias[2] = "Geografía";
            lostaMaterias[3] = "Biología";
            lostaMaterias[4] = "Química";
            lostaMaterias[5] = "Historia Universal";
            lostaMaterias[6] = "Historia de México";
            lostaMaterias[7] = "Filosofía";
            lostaMaterias[8] = "Física";
            lostaMaterias[9] = "Matemáticas";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[2] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[3] = R.mipmap.biologia_ios;
            lostaMateriasImagen[4] = R.mipmap.quimica_ios;
            lostaMateriasImagen[5] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[6] = R.mipmap.historia_mex_ios;
            lostaMateriasImagen[7] = R.mipmap.civismo_ios;
            lostaMateriasImagen[8] = R.mipmap.fisica_ios;
            lostaMateriasImagen[9] = R.mipmap.matematicas_ios;

        }*/


        consultarEscuelasSeleccionadas();

        //listaMaterias.setOnItemClickListener(this);


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

    private void intiControls(){
        txtNombreMateria = (TextView) findViewById(R.id.txtNombreMateria);
        listaClases = (ListView) findViewById(R.id.listaClases);
        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);
        listaClases.setOnItemClickListener(this);
    }

    public void consultarEscuelasSeleccionadas(){


        productsListescuelas = new ArrayList<HashMap<String, Object>>();

        List<String> tipo = new ArrayList<String>();

        int contenedor=0;
        List listas2 = null;

        String materiaTmp[]= new String[3];
        String materiaNombreTmp[]= new String[3];
        String conI[]= new String[3];
        int imaI[]= new int[3];
        int contador =0;



        int numeroPregunta = 1;
        int contadorMateria = 0;

        for(int i=0; i<lostaMaterias.length; i++){

            HashMap<String, Object> map = new HashMap<String, Object>();


            // adding each child node to HashMap key => value
            map.put(TAG_ESCUELA_IMAGEN, lostaMateriasImagen[i]);
            map.put(TAG_ESCUELA, lostaMaterias[i]);

            productsListescuelas.add(map);


        }



        ListAdapter adapter3 = new SimpleAdapter(ListaClasesActivity.this,
                productsListescuelas, R.layout.listaaestudiarmaterias, new String[] { TAG_ESCUELA_IMAGEN,
                TAG_ESCUELA, TAG_ACIERTOS_MINIMO, TAG_ACIERTOS_OBTENIDOS},
                new int[] { R.id.imagenMateria, R.id.txtlicencaituras, R.id.txtresultados, R.id.txtresultadoobtenido});
        listaClases.setAdapter(adapter3);


    }

    @Override
    public void onClick(View v) {



        if(v.getId()==findViewById(R.id.img_flecha).getId()){


            Log.e("Regreso", "Menu");
            finish();
            Intent linsertar=new Intent(ListaClasesActivity.this, MainActivity.class);
            startActivity(linsertar);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    @Override
    public void onItemClick(AdapterView parent, View vista,
                            int posicion, long id){


        Log.e("id__", id+"");

        if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){

            if(id==0){
                test = "espan";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==1){
                test = "razverbal";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==2){
                test = "geomex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==3){
                test = "biolo";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==4){
                test = "quimi";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==5){
                test = "hisuniv";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==6){
                test = "hismex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==7){
                test = "fisic";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==8){
                test = "mate";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 2: Ciencias Biológicas, Quimicas y de la Salud")){


            if(id==0){
                test = "espan";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==1){
                test = "razverbal";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==2){
                test = "geomex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==3){
                test = "biolo";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==4){
                test = "quimi";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==5){
                test = "hisuniv";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==6){
                test = "hismex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==7){
                test = "fisic";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==8){
                test = "mate";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 3: Ciencias Sociales")){


            if(id==0){
                test = "espan";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==1){
                test = "razverbal";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==2){
                test = "geomex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==3){
                test = "biolo";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==4){
                test = "quimi";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==5){
                test = "hisuniv";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==6){
                test = "hismex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==7){
                test = "fisic";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==8){
                test = "mate";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 4: Humanidades y Artes")){

            if(id==0){
                test = "espan";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==1){
                test = "razverbal";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==2){
                test = "geomex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==3){
                test = "biolo";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

            if(id==4){
                test = "quimi";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==5){
                test = "hisuniv";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==6){
                test = "hismex";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==7){
                test = "filos";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==8){
                test = "fisic";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==9){
                test = "mate";
                finish();
                Intent linsertar=new Intent(this, TemarioActivity.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }

        }




    }

    @Override
    public void onBackPressed() {

    }

}
