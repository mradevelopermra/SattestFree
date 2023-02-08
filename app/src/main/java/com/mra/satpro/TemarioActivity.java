package com.mra.satpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mra.satpro.utilerias.TemarioGlobal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemarioActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    TextView txtNombreMateria;
    ListView listaTemario;
    private ImageView img_flecha;
    private static final String TAG_ESCUELA_IMAGEN = "||escuelaimagen";
    private static final String TAG_ESCUELA = "||escuela";
    private static final String TAG_ACIERTOS_MINIMO = "||aciertosminimos";
    private static final String TAG_ACIERTOS_OBTENIDOS = "||aciertosobtenidos";
    private static final String TAG_NOM_VIDE = "||idvideo";

    ArrayList<HashMap<String, Object>> productsListescuelas;
    String lostaMaterias[] = new String[3];
    String lostaVideos[] = new String[3];
    int lostaMateriasImagen[] = new int[3];
    String test = "";
    String tipoTest = "";
    final Context context = this;
    AlertDialog alertaGastos;
    AlertDialog comprado;

    int LidFinalPersona = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temario);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        tipoTest=getIntent().getExtras().getString("tipoTest");
        intiControls();

        Log.e("tipoTest", tipoTest);

        if(tipoTest.equalsIgnoreCase("espan")){
            txtNombreMateria.setText("Clases de Español");
            lostaMaterias = new String[TemarioGlobal.temasEspanol.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenEspanol.length];
            lostaVideos = new String[TemarioGlobal.temasEspanol.length];


            for(int i=0; i<TemarioGlobal.temasEspanol.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasEspanol[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenEspanol[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoEspanol[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("hismex")){
            txtNombreMateria.setText("Clases de Historia de México");
            lostaMaterias = new String[TemarioGlobal.temasHisMex.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenHisMex.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoHisMex.length];


            for(int i=0; i<TemarioGlobal.temasHisMex.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasHisMex[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenHisMex[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoHisMex[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("hisuniv")){
            txtNombreMateria.setText("Clases de Historia Universal");
            lostaMaterias = new String[TemarioGlobal.temasHisUniv.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenHisUniv.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoHisUniv.length];


            for(int i=0; i<TemarioGlobal.temasHisUniv.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasHisUniv[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenHisUniv[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoHisUniv[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("geouniv")){
            txtNombreMateria.setText("Clases de Geografía Universal");
            lostaMaterias = new String[TemarioGlobal.temasGeoUniv.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenGeo.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoGeoUniv.length];


            for(int i=0; i<TemarioGlobal.temasGeoUniv.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasGeoUniv[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenGeo[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoGeoUniv[i];
            }

        }



        if(tipoTest.equalsIgnoreCase("razmate")){
            txtNombreMateria.setText("Clases de Razonamiento Matemático");
            lostaMaterias = new String[TemarioGlobal.temasRazMate.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenRazMate.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoRazMate.length];


            for(int i=0; i<TemarioGlobal.temasRazMate.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasRazMate[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenRazMate[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoRazMate[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("geomex")){
            txtNombreMateria.setText("Clases de Geografía");
            lostaMaterias = new String[TemarioGlobal.temasGeoMex.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenGeoMex.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoGeoMex.length];


            for(int i=0; i<TemarioGlobal.temasGeoMex.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasGeoMex[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenGeoMex[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoGeoMex[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("razmate")){
            txtNombreMateria.setText("Clases de Razonamiento Matemático");
            lostaMaterias = new String[TemarioGlobal.temasRazMate.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenRazMate.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoRazMate.length];


            for(int i=0; i<TemarioGlobal.temasRazMate.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasRazMate[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenRazMate[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoRazMate[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("fisic")){
            txtNombreMateria.setText("Clases de Física");
            lostaMaterias = new String[TemarioGlobal.temasFisica.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenFisica.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoFisica.length];


            for(int i=0; i<TemarioGlobal.temasFisica.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasFisica[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenFisica[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoFisica[i];
            }

        }


        if(tipoTest.equalsIgnoreCase("razverbal")){
            txtNombreMateria.setText("Clases de Literatura");
            lostaMaterias = new String[TemarioGlobal.temasRazVerbal.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenRazVerbal.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoRazVerbal.length];


            for(int i=0; i<TemarioGlobal.temasRazVerbal.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasRazVerbal[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenRazVerbal[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoRazVerbal[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("quimi")){
            txtNombreMateria.setText("Clases de Química");
            lostaMaterias = new String[TemarioGlobal.temasQuimica.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenQuimica.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoQuimica.length];


            for(int i=0; i<TemarioGlobal.temasQuimica.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasQuimica[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenQuimica[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoQuimica[i];
            }

        }


        if(tipoTest.equalsIgnoreCase("mate")){
            txtNombreMateria.setText("Clases de Matemáticas");
            lostaMaterias = new String[TemarioGlobal.temasMatematicas.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenMatematicas.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoMatematicas.length];


            for(int i=0; i<TemarioGlobal.temasMatematicas.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasMatematicas[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenMatematicas[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoMatematicas[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("biolo")){
            txtNombreMateria.setText("Clases de Biología");
            lostaMaterias = new String[TemarioGlobal.temasBiologia.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenBiologia.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoBiologia.length];


            for(int i=0; i<TemarioGlobal.temasBiologia.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasBiologia[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenBiologia[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoBiologia[i];
            }

        }

        if(tipoTest.equalsIgnoreCase("filos")){
            txtNombreMateria.setText("Clases de Filosofía");

            lostaMaterias = new String[TemarioGlobal.temasFilosofia.length];
            lostaMateriasImagen = new int[TemarioGlobal.nombreImagenFilosofia.length];
            lostaVideos = new String[TemarioGlobal.nombreVideoFilosofia.length];


            for(int i=0; i<TemarioGlobal.temasFilosofia.length; i++){
                lostaMaterias[i] = TemarioGlobal.temasFilosofia[i];
                lostaMateriasImagen[i] = TemarioGlobal.nombreImagenFilosofia[i];
                lostaVideos[i] = TemarioGlobal.nombreVideoFilosofia[i];
            }


        }


        consultarEscuelasSeleccionadas();


    }

    private void intiControls(){
        txtNombreMateria = (TextView) findViewById(R.id.txtNombreMateria);
        listaTemario = (ListView) findViewById(R.id.listaTemario);
        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);
        listaTemario.setOnItemClickListener(this);
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
            map.put(TAG_NOM_VIDE, lostaVideos[i]);

            productsListescuelas.add(map);

        }

        ListAdapter adapter3 = new SimpleAdapter(TemarioActivity.this,
                productsListescuelas, R.layout.listaaestudiarmaterias, new String[] { TAG_ESCUELA_IMAGEN,
                TAG_ESCUELA, TAG_NOM_VIDE, TAG_ACIERTOS_OBTENIDOS},
                new int[] { R.id.imagenMateria, R.id.txtlicencaituras, R.id.txtresultados, R.id.txtresultadoobtenido});
        listaTemario.setAdapter(adapter3);

        //your grid view as child
        listaTemario.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listaTemario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>parent, View v, final int LIid, long id) {
                LidFinalPersona = LIid;

                String test[]={};
                test = listaTemario.getItemAtPosition(LidFinalPersona).toString().replace("{", "").replace("}", "").split(",");

                String numRep[] = {};
                String cadenaValida = "";
                numRep = test[0].split("=");

                for(int i=0; i<test.length; i++){
                    if(test[i].contains("idvideo")){
                        numRep = test[i].split("=");
                    }
                }

                cadenaValida = numRep[1].toString();

                verVideo(cadenaValida);

            }
        });


    }

    public void verVideo(String idVideo){
        Log.e("idVideo", idVideo);
        String nombreVideo = idVideo;

        if(nombreVideo.equalsIgnoreCase("copreterito_espanol_comipems.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/dlLgenvlb1Y")));
        }else if(nombreVideo.equalsIgnoreCase("nexo_espanol_comipems.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/QoAXlsWOHPg")));
        } else if(nombreVideo.equalsIgnoreCase("orden_de_ideas_espanol_comipems.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/ErxpLOOhMGA")));
        }else if(nombreVideo.equalsIgnoreCase("asentamiento_tres_zapotes.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/DpSqwLvHFUk")));
        }else if(nombreVideo.equalsIgnoreCase("las_audiencias.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/z2boEsIo4vE")));
        }else if(nombreVideo.equalsIgnoreCase("la_civilizacion_maya.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/bJreW7C0lXc")));
        }else if(nombreVideo.equalsIgnoreCase("edad_moderna.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/9Bg3LlRiR1k")));
        }else if(nombreVideo.equalsIgnoreCase("renacimiento_y_humanismo.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/pDRLg_j79eY")));
        }else if(nombreVideo.equalsIgnoreCase("tratado_de_tordesilla.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/j0eGbdNNL8w")));
        }else if(nombreVideo.equalsIgnoreCase("migrante.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/smdv8PmDHxU")));
        }else if(nombreVideo.equalsIgnoreCase("globo_terraqueo.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/yjWkKyZFl2Y")));
        }else if(nombreVideo.equalsIgnoreCase("paralelos_y_meridianos.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/qYJVTGuqGWU")));
        }else if(nombreVideo.equalsIgnoreCase("puerto_de_veracruz.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/AzEeXtGBtDE")));
        }else if(nombreVideo.equalsIgnoreCase("usumacinta_suchiate.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/C0frs7LdKt4")));
        }else if(nombreVideo.equalsIgnoreCase("sonda_de_campeche.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/rtB5OH-Z3Rw")));
        }else if(nombreVideo.equalsIgnoreCase("imaginacion_espacial.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/8QtV6PuQvg8")));
        }else if(nombreVideo.equalsIgnoreCase("relacion_de_magnitudes.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/uFTIYUEKwmQ")));
        }else if(nombreVideo.equalsIgnoreCase("tiro_parabolico.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/b5iTcq0ZWkY")));
        }else if(nombreVideo.equalsIgnoreCase("movimiento_rectilineo_uniforme.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/wK8gAuL-4IA")));
        }else if(nombreVideo.equalsIgnoreCase("analogIa.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/Bh-5V4t7OUU")));
        }else if(nombreVideo.equalsIgnoreCase("sinonimo.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/lPy84WlQEmA")));
        }else if(nombreVideo.equalsIgnoreCase("antonimos.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/g1krgY55lC8")));
        }else if(nombreVideo.equalsIgnoreCase("raiz_cuadrada.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/1sXFdmnd2vI")));
        }else if(nombreVideo.equalsIgnoreCase("numeros_primos.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/whr1Q4TPWtU")));
        }else if(nombreVideo.equalsIgnoreCase("quImica.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/OGL1DBp9Dps")));
        }else if(nombreVideo.equalsIgnoreCase("propiedades_cualitativas.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/7_SKtsF5vYc")));
        }else if(nombreVideo.equalsIgnoreCase("metodo_cientifico.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/K2Qn8t6-3HI")));
        }else if(nombreVideo.equalsIgnoreCase("ecuaciones_algebraicas.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/vqcgVwuGYkg")));
        }else if(nombreVideo.equalsIgnoreCase("citologia.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/Xtc8P3AELxY")));
        }else if(nombreVideo.equalsIgnoreCase("carbono.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/92hhxS2Vce4")));
        }else if(nombreVideo.equalsIgnoreCase("evolucion.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/7HKH58nnj1o")));
        }else if(nombreVideo.equalsIgnoreCase("enciclopedismo.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/F6651L_Yqk8")));
        }else if(nombreVideo.equalsIgnoreCase("newton_descartes_smith_lavoisier.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/cM93bk4Lqgk")));
        }else if(nombreVideo.equalsIgnoreCase("el_liberalismo_economico.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/Qp5PvrLTpQw")));
        }else if(nombreVideo.equalsIgnoreCase("funcion_apelativa.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/OGL1DBp9Dps")));
        }else if(nombreVideo.equalsIgnoreCase("funcion_referencial.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/K1vmNXwsU_c")));
        }else if(nombreVideo.equalsIgnoreCase("funcion_Poetica.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/0oqwMP4jVeQ")));
        }else if(nombreVideo.equalsIgnoreCase("robert_kooke.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/OGL1DBp9Dps")));
        }else if(nombreVideo.equalsIgnoreCase("robert_brown.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/7vYMjVjB2_0")));
        }else if(nombreVideo.equalsIgnoreCase("carbohidratos.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/HNGyUhAPf0I")));
        }else if(nombreVideo.equalsIgnoreCase("el_meridiano_cero.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/5fuPz6E0q0s")));
        }else if(nombreVideo.equalsIgnoreCase("Tectonica_de_placas.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/PJ7vGb4Bjbw")));
        }else if(nombreVideo.equalsIgnoreCase("plegamiento.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/0rDxyT9RtK4")));
        }else if(nombreVideo.equalsIgnoreCase("enlace_ionico_y_covalente.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/3LSUjNBCLKU")));
        }else if(nombreVideo.equalsIgnoreCase("la_polaridad.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/y2VFwOfSeJo")));
        }else if(nombreVideo.equalsIgnoreCase("cation.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/3p_qSdq5tWo")));
        }else if(nombreVideo.equalsIgnoreCase("platon.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/zE6KNnsWEMQ")));
        }else if(nombreVideo.equalsIgnoreCase("aristoteles.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/wZnBA1nVtAs")));
        }else if(nombreVideo.equalsIgnoreCase("descartes.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/r7Sc7lXGSSM")));
        }else if(nombreVideo.equalsIgnoreCase("polyforum.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/ds9ABrdFlSg")));
        }else if(nombreVideo.equalsIgnoreCase("guernica.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/UYIZyAl4UDk")));
        }else if(nombreVideo.equalsIgnoreCase("partenon.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/ldl-Vearma4")));
        }else if(nombreVideo.equalsIgnoreCase("Simplificando.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/27kGMnpMcnw")));
        }else if(nombreVideo.equalsIgnoreCase("sustituyendo_valores.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/hVKQXw17uQ4")));
        }else if(nombreVideo.equalsIgnoreCase("funciones_uno.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/FB-dfsjbxTM")));
        }else if(nombreVideo.equalsIgnoreCase("problema_magnitud_de_un_vector.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/-AFz7t8NWbo")));
        }else if(nombreVideo.equalsIgnoreCase("sistema_internacional.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/wAaczfF5yb8")));
        }else if(nombreVideo.equalsIgnoreCase("km_h_a_m_s.mp4")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/e23uxsN3bZk")));
        }else {
            infoTipoPago();
        }



        /*if(nombreVideo.equalsIgnoreCase("copreterito_espanol_comipems.mp4")
                || nombreVideo.equalsIgnoreCase("nexo_espanol_comipems.mp4")
                || nombreVideo.equalsIgnoreCase("orden_de_ideas_espanol_comipems.mp4")
                || nombreVideo.equalsIgnoreCase("asentamiento_tres_zapotes.mp4")
                || nombreVideo.equalsIgnoreCase("las_audiencias.mp4")
                || nombreVideo.equalsIgnoreCase("la_civilizacion_maya.mp4")
                || nombreVideo.equalsIgnoreCase("edad_moderna.mp4")
                || nombreVideo.equalsIgnoreCase("renacimiento_y_humanismo.mp4")
                || nombreVideo.equalsIgnoreCase("tratado_de_tordesilla.mp4")
                || nombreVideo.equalsIgnoreCase("migrante.mp4")
                || nombreVideo.equalsIgnoreCase("globo_terraqueo.mp4")
                || nombreVideo.equalsIgnoreCase("paralelos_y_meridianos.mp4")
                || nombreVideo.equalsIgnoreCase("puerto_de_veracruz.mp4")
                || nombreVideo.equalsIgnoreCase("usumacinta_suchiate.mp4")
                || nombreVideo.equalsIgnoreCase("sonda_de_campeche.mp4")
                || nombreVideo.equalsIgnoreCase("imaginacion_espacial.mp4")
                || nombreVideo.equalsIgnoreCase("relacion_de_magnitudes.mp4")
                || nombreVideo.equalsIgnoreCase("tiro_parabolico.mp4")
                || nombreVideo.equalsIgnoreCase("movimiento_rectilineo_uniforme.mp4")
                || nombreVideo.equalsIgnoreCase("analogIa.mp4")
                || nombreVideo.equalsIgnoreCase("sinonimo.mp4")
                || nombreVideo.equalsIgnoreCase("antonimos.mp4")
                || nombreVideo.equalsIgnoreCase("raiz_cuadrada.mp4")
                || nombreVideo.equalsIgnoreCase("numeros_primos.mp4")
                || nombreVideo.equalsIgnoreCase("quImica.mp4")
                || nombreVideo.equalsIgnoreCase("propiedades_cualitativas.mp4")
                || nombreVideo.equalsIgnoreCase("metodo_cientifico.mp4")
                || nombreVideo.equalsIgnoreCase("ecuaciones_algebraicas.mp4")
                || nombreVideo.equalsIgnoreCase("citologia.mp4")
                || nombreVideo.equalsIgnoreCase("carbono.mp4")
                || nombreVideo.equalsIgnoreCase("evolucion.mp4")
                || nombreVideo.equalsIgnoreCase("enciclopedismo.mp4")
                || nombreVideo.equalsIgnoreCase("newton_descartes_smith_lavoisier.mp4")
                || nombreVideo.equalsIgnoreCase("el_liberalismo_economico.mp4")
                || nombreVideo.equalsIgnoreCase("funcion_apelativa.mp4")
                || nombreVideo.equalsIgnoreCase("funcion_referencial.mp4")
                || nombreVideo.equalsIgnoreCase("funcion_Poetica.mp4")
                || nombreVideo.equalsIgnoreCase("robert_kooke.mp4")
                || nombreVideo.equalsIgnoreCase("robert_brown.mp4")
                || nombreVideo.equalsIgnoreCase("carbohidratos.mp4")
                || nombreVideo.equalsIgnoreCase("el_meridiano_cero.mp4")
                || nombreVideo.equalsIgnoreCase("Tectonica_de_placas.mp4")
                || nombreVideo.equalsIgnoreCase("plegamiento.mp4")
                || nombreVideo.equalsIgnoreCase("enlace_ionico_y_covalente.mp4")
                || nombreVideo.equalsIgnoreCase("la_polaridad.mp4")
                || nombreVideo.equalsIgnoreCase("cation.mp4")
                || nombreVideo.equalsIgnoreCase("platon.mp4")
                || nombreVideo.equalsIgnoreCase("aristoteles.mp4")
                || nombreVideo.equalsIgnoreCase("descartes.mp4")
                || nombreVideo.equalsIgnoreCase("Simplificando.mp4")
                || nombreVideo.equalsIgnoreCase("sustituyendo_valores.mp4")
                || nombreVideo.equalsIgnoreCase("funciones_uno.mp4")
                || nombreVideo.equalsIgnoreCase("problema_magnitud_de_un_vector.mp4")
                || nombreVideo.equalsIgnoreCase("sistema_internacional.mp4")
                || nombreVideo.equalsIgnoreCase("km_h_a_m_s.mp4")
        ){

            finish();
            Intent linsertar=new Intent(this, VisualizarVideoActivity.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("ideVideo", nombreVideo);
            startActivity(linsertar);

        }else {
            infoTipoPago();
        }*/


    }

    /*private void mostrarVideoComprarSaldo() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TemarioActivity.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_game_over, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("$0 Pesos Ganados");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("comprar_saldo.json");

        builder.setMessage("No cuentas con mas saldo, puedes mirar un video para darte saldo de regalo.")
                .setTitle("Saldo agotado");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("Mirar video", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                gameOver = false;
                miraUnVideo();


            }
        });

        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                salirPatos();
                //compraMasSaldo();
            }
        });



        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }*/

    private void infoTipoPago(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.version_pago, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        TextView textView30 = (TextView) promptView.findViewById(R.id.textView30);
        textView30.setText(getResources().getString(R.string.masVideos));

        Button buttonCerrar = (Button) promptView.findViewById(R.id.closepago);
        buttonCerrar.setOnClickListener(cerrarVentanaAyuda);

        Button comprafull = (Button) promptView.findViewById(R.id.comprafull);
        comprafull.setOnClickListener(compras);


        alertaGastos = alertDialogBuilder.show();
        alertaGastos.setCanceledOnTouchOutside(true);

    }

    private View.OnClickListener cerrarVentanaAyuda = new View.OnClickListener() {
        public void onClick(View v) {

            alertaGastos.dismiss();
        }
    };

    private View.OnClickListener compras = new View.OnClickListener() {
        public void onClick(View v) {

            //Compras();
            final String appPackageName ="com.mra.examenunampremiumpremium";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }

        }
    };


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onClick(View v) {



        if(v.getId()==findViewById(R.id.img_flecha).getId()){


            Log.e("Regreso", "Menu");
            finish();
            Intent linsertar=new Intent(TemarioActivity.this, ListaClasesActivity.class);
            startActivity(linsertar);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    @Override
    public void onItemClick(AdapterView parent, View vista,
                            int posicion, long id){


        /*if(id==0){
            test = "espan";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==1){
            test = "razverbal";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==2){
            test = "hisuniv";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==3){
            test = "hismex";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==4){
            test = "geouniv";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==5){
            test = "geomex";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==6){
            test = "civis";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==7){
            test = "razmate";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==8){
            test = "mate";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==9){
            test = "fisic";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==10){
            test = "quimi";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }
        if(id==11){
            test = "biolo";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }*/


    }

    @Override
    public void onBackPressed() {

    }

}
