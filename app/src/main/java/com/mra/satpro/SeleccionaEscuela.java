package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;


public class SeleccionaEscuela extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    ListView Listadetalle1;
    int LidFinal = 0;
    ArrayList<HashMap<String, Object>> productsList;


    private static final String TAG_CONCEPTO = "||conceptoIngreso";
    private static final String TAG_IMAGEN = "||strImagen";

    String materias []= {
            "Español",
            "Filosofía",
            "Historia universal",
            "Biología",
            "Geografía",
            "Matemáticas",
            "Literatura",
            "Historia de México",
            "Química",
            "Física"
    };

    int imagenesMaterias [] ={
            R.mipmap.espanol,
            R.mipmap.civismodos,
            R.mipmap.historiauniversal,
            R.mipmap.biologia,
            R.mipmap.geografiauniversal,
            R.mipmap.matematicasdos,
            R.mipmap.razonamientoverbal,
            R.mipmap.historiademexico,
            R.mipmap.quimica,
            R.mipmap.fisica
    };

    Button espanol, razverbal, histouniversal, historiamexico, geouniversal, geomexico;
    Button civismo, razmate, matema, fisica, quimica, biologia;

    String test = "";

    private ImageView img_flecha;
    ListView listaMaterias;
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
        setContentView(R.layout.activity_selecciona_escuela);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listaMaterias = (ListView) findViewById(R.id.listaMaterias);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        espanol=(Button)findViewById(R.id.espanol);
        espanol.setOnClickListener(this);

        razverbal=(Button)findViewById(R.id.razverbal);
        razverbal.setOnClickListener(this);

        histouniversal=(Button)findViewById(R.id.histouniversal);
        histouniversal.setOnClickListener(this);

        historiamexico=(Button)findViewById(R.id.historiamexico);
        historiamexico.setOnClickListener(this);

        geouniversal=(Button)findViewById(R.id.geouniversal);
        geouniversal.setOnClickListener(this);

        geomexico=(Button)findViewById(R.id.geomexico);
        geomexico.setOnClickListener(this);

        civismo=(Button)findViewById(R.id.civismo);
        civismo.setOnClickListener(this);

        razmate=(Button)findViewById(R.id.razmate);
        razmate.setOnClickListener(this);

        matema=(Button)findViewById(R.id.matema);
        matema.setOnClickListener(this);

        fisica=(Button)findViewById(R.id.fisica);
        fisica.setOnClickListener(this);

        quimica=(Button)findViewById(R.id.quimica);
        quimica.setOnClickListener(this);

        biologia=(Button)findViewById(R.id.biologia);
        biologia.setOnClickListener(this);

        buscaOpciones();

        //if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){
            lostaMaterias = new String[7];
            lostaMateriasImagen = new int[7];

            lostaMaterias[0] = "Reading";
            lostaMaterias[1] = "Writing and Language";
            lostaMaterias[2] = "Math";
            lostaMaterias[3] = "Biology";
            lostaMaterias[4] = "Chemistry";
            lostaMaterias[5] = "Physics";
            lostaMaterias[6] = "Spanish";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[2] = R.mipmap.matematicas_ios;
            lostaMateriasImagen[3] = R.mipmap.biologia_ios;
            lostaMateriasImagen[4] = R.mipmap.quimica_ios;
            lostaMateriasImagen[5] = R.mipmap.fisica_ios;
            lostaMateriasImagen[6] = R.mipmap.razon_verbal_ios;


        //}
        /*if(areaLicenciatura.equalsIgnoreCase("Área 2: Ciencias Biológicas, Quimicas y de la Salud")){

            lostaMaterias = new String[9];
            lostaMateriasImagen = new int[9];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Física";
            lostaMaterias[2] = "Matemáticas";
            lostaMaterias[3] = "Literatura";
            lostaMaterias[4] = "Geografía";
            lostaMaterias[5] = "Biología";
            lostaMaterias[6] = "Química";
            lostaMaterias[7] = "Historia Universal";
            lostaMaterias[8] = "Historia de México";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.fisica_ios;
            lostaMateriasImagen[2] = R.mipmap.matematicas_ios;
            lostaMateriasImagen[3] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[4] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[5] = R.mipmap.biologia_ios;
            lostaMateriasImagen[6] = R.mipmap.quimica_ios;
            lostaMateriasImagen[7] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[8] = R.mipmap.historia_mex_ios;

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 3: Ciencias Sociales")){
            lostaMaterias = new String[9];
            lostaMateriasImagen = new int[9];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Física";
            lostaMaterias[2] = "Matemáticas";
            lostaMaterias[3] = "Literatura";
            lostaMaterias[4] = "Geografía";
            lostaMaterias[5] = "Biología";
            lostaMaterias[6] = "Química";
            lostaMaterias[7] = "Historia Universal";
            lostaMaterias[8] = "Historia de México";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.fisica_ios;
            lostaMateriasImagen[2] = R.mipmap.matematicas_ios;
            lostaMateriasImagen[3] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[4] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[5] = R.mipmap.biologia_ios;
            lostaMateriasImagen[6] = R.mipmap.quimica_ios;
            lostaMateriasImagen[7] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[8] = R.mipmap.historia_mex_ios;

        }
        if(areaLicenciatura.equalsIgnoreCase("Área 4: Humanidades y Artes")){

            lostaMaterias = new String[10];
            lostaMateriasImagen = new int[10];

            lostaMaterias[0] = "Español";
            lostaMaterias[1] = "Física";
            lostaMaterias[2] = "Matemáticas";
            lostaMaterias[3] = "Literatura";
            lostaMaterias[4] = "Geografía";
            lostaMaterias[5] = "Biología";
            lostaMaterias[6] = "Química";
            lostaMaterias[7] = "Historia Universal";
            lostaMaterias[8] = "Historia de México";
            lostaMaterias[9] = "Filosofía";

            lostaMateriasImagen[0] = R.mipmap.espanol_ios;
            lostaMateriasImagen[1] = R.mipmap.fisica_ios;
            lostaMateriasImagen[2] = R.mipmap.matematicas_ios;
            lostaMateriasImagen[3] = R.mipmap.razon_verbal_ios;
            lostaMateriasImagen[4] = R.mipmap.geografia_universal_ios;
            lostaMateriasImagen[5] = R.mipmap.biologia_ios;
            lostaMateriasImagen[6] = R.mipmap.quimica_ios;
            lostaMateriasImagen[7] = R.mipmap.historia_universal_ios;
            lostaMateriasImagen[8] = R.mipmap.historia_mex_ios;
            lostaMateriasImagen[9] = R.mipmap.civismo_ios;
        }*/


        consultarEscuelasSeleccionadas();

        listaMaterias.setOnItemClickListener(this);
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


    private void mostrarInfoPago() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SeleccionaEscuela.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("version_pro.json");

        builder.setMessage("Only avaible for PRO version")
                .setTitle("PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("PRO", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                final String appPackageName ="com.mra.satpro";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });



        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    @Override
    public void onItemClick(AdapterView parent, View vista,
                            int posicion, long id){

        //if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){
            if(id==0){
                test = "Reading";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==1){
                test = "Writing and Language";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==2){
                test = "Math";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);
            }
            if(id==3){
                mostrarInfoPago();
                /*test = "Biology";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);*/
            }
            if(id==4){
                mostrarInfoPago();
                /*test = "Chemistry";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);*/
            }
            if(id==5){
                mostrarInfoPago();
                /*test = "Physics";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);*/
            }
            if(id==6){
                mostrarInfoPago();
                /*test = "Spanish";
                finish();
                Intent linsertar=new Intent(this, ConfigurarModulo.class);
                linsertar.putExtra("tipoTest", test);
                startActivity(linsertar);*/
            }


        //}



      /*  Intent i = new Intent(this, SeleccionaEscuela.class);
        i.putExtra("id", id);
        startActivity(i);*/
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

         /*   for(int i =0; i<arrayEscuelasCarreras.length; i++){
                if(escuela.trim().equalsIgnoreCase(arrayEscuelasCarreras[i].trim())){
                    imagenEscuela = arrayImagenEscuela[i];
                }
            }*/

            HashMap<String, Object> map = new HashMap<String, Object>();


            // adding each child node to HashMap key => value
            map.put(TAG_ESCUELA_IMAGEN, lostaMateriasImagen[i]);
            map.put(TAG_ESCUELA, lostaMaterias[i]);
            //    map.put(TAG_ACIERTOS_MINIMO, aciertosEscuela);
            //    map.put(TAG_ACIERTOS_OBTENIDOS, aciertosObtenidos);


            // adding HashList to ArrayList
            productsListescuelas.add(map);


        }




        /**
         * Updating parsed JSON data into ListView
         * */




        ListAdapter adapter3 = new SimpleAdapter(SeleccionaEscuela.this,
                productsListescuelas, R.layout.listaaestudiarmaterias, new String[] { TAG_ESCUELA_IMAGEN,
                TAG_ESCUELA, TAG_ACIERTOS_MINIMO, TAG_ACIERTOS_OBTENIDOS},
                new int[] { R.id.imagenMateria, R.id.txtlicencaituras, R.id.txtresultados, R.id.txtresultadoobtenido});
        listaMaterias.setAdapter(adapter3);
        //Listadetalle1.setAdapter(new dataListAdapter(tipoI,
        //		conI, imaI));

    }

    @Override
    public void onBackPressed() { }


    @Override
    public void onClick(View v) {


        if(v.getId()==findViewById(R.id.espanol).getId()){
            test = "espan";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.razverbal).getId()){
            test = "razverbal";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.histouniversal).getId()){
            test = "hisuniv";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.historiamexico).getId()){
            test = "hismex";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.geouniversal).getId()){
            test = "geouniv";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

    /*    if(v.getId()==findViewById(R.id.geomexico).getId()){
            test = "geomex";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }*/

        if(v.getId()==findViewById(R.id.civismo).getId()){
            test = "civis";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

    /*    if(v.getId()==findViewById(R.id.razmate).getId()){
            test = "razmate";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }*/

        if(v.getId()==findViewById(R.id.matema).getId()){
            test = "mate";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.fisica).getId()){
            test = "fisic";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.quimica).getId()){
            test = "quimi";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.biologia).getId()){
            test = "biolo";
            finish();
            Intent linsertar=new Intent(this, ConfigurarModulo.class);
            linsertar.putExtra("tipoTest", test);
            startActivity(linsertar);
        }


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

         /*   img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(Modulo.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
            Log.e("Regreso", "Menu");
            finish();
            Intent linsertar=new Intent(SeleccionaEscuela.this, MainActivity.class);
            startActivity(linsertar);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
