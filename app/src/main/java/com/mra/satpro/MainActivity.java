package com.mra.satpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dao.BaseDaoPassword;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ModulosDTO;
import com.mra.satpro.utilerias.Constantes;
import com.mra.satpro.utilerias.JSONParser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayImagenClasificacionAreas;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    int LidFinal = 0;
    Button guardaescuela, estudiamateria, estudiaClase, eamenSimu, ahorcado, ganaDinero;
    Spinner opcionuno, opciondos, opciontres;
    String opcionEscUno, opcionEscDos, opcionEscTres;

    Context ctx = this;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    JSONArray products = null;

    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID_CATEGORIA = "idPreguntasModulo";
    private static final String TAG_PID_MODULO = "IdMateria";
    private static final String TAG_PREGUNTA = "pregunta";
    private static final String TAG_RESPUESTA_UNO = "respuestaUno";
    private static final String TAG_RESPUESTA_DOS = "respuestaDos";
    private static final String TAG_RESPUESTA_TRES = "respuestaTres";
    private static final String TAG_RESPUESTA_CUATRO = "respuestaCuatro";
    private static final String TAG_IMAGEN_PREGUNTA = "imagenPregunta";
    private static final String TAG_RESPUESTA_CORRECTA = "correcta";
    private static final String TAG_TOOLTIP = "tooltip";
    private static final String TAG_IMAGEN_TOOLTIP = "tooltipImagen";
    private static final String TAG_AUDIO_PREGUNTA = "audioPregunta";
    private static final String TAG_AUDIO_TOOLTIP = "audioTooltip";

    String preguntasBloque[];
    String respuestaUnoBloque[];
    String respuestaDosBloque[];
    String respuestaTresBloque[];
    String respuestaCuatroBloque[];
    String imagenPreguntaBloque[];
    String correctaBloque[];
    String tooltipBloque[];
    String imagenTooltipBloque[];

    int idCate [];
    private boolean escuelaElegida = false;
    int examenReactivosResuelto =0;

    String opcionesActualiza [];

    /*private RewardedVideoAd mRewardedVideoAd;
    //videos pruebas
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544~3347511713";
    private static final String AD_VIDEO = "ca-app-pub-3940256099942544/5224354917";

    //videos produccion
    private static final String AD_UNIT_ID = "ca-app-pub-5240485303222073~6269745980";
    private static final String AD_VIDEO = "ca-app-pub-5240485303222073/9004913471";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*MobileAds.initialize(this, AD_UNIT_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        loadRewardedVideoAd();
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);*/

        guardaescuela = (Button) findViewById(R.id.guardaescuela);
        estudiamateria = (Button) findViewById(R.id.estudiamateria);
        estudiaClase = (Button) findViewById(R.id.estudiaClase);
        eamenSimu = (Button) findViewById(R.id.eamenSimu);
        ahorcado = (Button) findViewById(R.id.ahorcado);
        //ganaDinero = (Button) findViewById(R.id.ganaDinero);

        guardaescuela.setOnClickListener(this);
        estudiamateria.setOnClickListener(this);
        estudiaClase.setOnClickListener(this);
        eamenSimu.setOnClickListener(this);
        ahorcado.setOnClickListener(this);
        //ganaDinero.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.F
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

       /* navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);*/

        BaseDaoExamen conexionPreguntas=new BaseDaoExamen(MainActivity.this);
        List lista = conexionPreguntas.ConsultarModulos();

        Log.e("totalPreguntas", lista.size()+"");

        if(lista.size() == 0){
            new ImportarCategoriasCSVTask().execute("");
        }


        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listaopciones = conexion.ConsultarEscuelaSeleccionada();

        if(listaopciones.size()>1){
            conexion.eliminaCarreras();
        }

        /*Log.e("arrayEscuelasCarreras", arrayEscuelasCarreras.length+"");
        Log.e("arrayEscuelasPuntaje", arrayEscuelasPuntaje.length+"");
        Log.e("arrayImagenEscuela", arrayImagenEscuela.length+"");
        Log.e("arrayEscuelasArea", arrayEscuelasArea.length+"");*/

        opcionesActualiza = new String[1];
        int contadorOpciones =0;


        for(Object datos: listaopciones){
            EscuelasDTO elementos1=(EscuelasDTO) datos;

            //opcionesActualiza[contadorOpciones] = elementos1.getEscuelaLicenciatura();

            for(int i=0; i<arrayAreas.length; i++){
                if(arrayAreas[i].trim().equalsIgnoreCase(elementos1.getEscuelaLicenciatura().toString().trim())){
                    opcionesActualiza[contadorOpciones] = arrayAreas[i];

                }
            }

            contadorOpciones = contadorOpciones +1;
        }


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayAreas);

        if(listaopciones.size()>0){
            guardaescuela.setText("1.- Actualiza tu área de estudio");
        }


        opcionuno = (Spinner) findViewById(R.id.opcionuno);
        opcionuno.setAdapter(new MyAdapter(this, R.layout.rowescuelas, arrayAreas));

        opcionuno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                opcionEscUno = "";
                Object item = parent.getItemAtPosition(pos);
                opcionEscUno = String.valueOf(item);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        opcionuno.setSelection(adapter3.getPosition(opcionesActualiza[0]));
        buscaOpciones();

    }

    /*private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(AD_VIDEO,
                new AdRequest.Builder().build());
    }

    private void miraUnVideo() {
        Log.e("E_", "pada por aca");
        Toast.makeText(this, "Mira este video y luego busca un oponente", Toast.LENGTH_LONG);
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

        @Override
        public void onRewardedVideoAdLoaded() {
            //Toast.makeText(cnt, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            //Toast.makeText(cnt, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            //Toast.makeText(cnt, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            //Toast.makeText(cnt, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
            //menuPrincipal();
            //loadRewardedVideoAd();
            //regresa();
            loadRewardedVideoAd();
            //juegoAhorcado();
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {
            loadRewardedVideoAd();
            juegoAhorcado();
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            //Toast.makeText(cnt, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            //Toast.makeText(cnt, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
            loadRewardedVideoAd();
            juegoAhorcado();
        }

        @Override
        public void onRewardedVideoCompleted() {
            //Toast.makeText(cnt, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
            //mRewardedVideoAd.destroy(cnt);
            loadRewardedVideoAd();
            juegoAhorcado();
        }
    };*/



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    class ImportarCategoriasCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        // can use UI thread here

        protected void onPreExecute()

        {
            this.dialog.setMessage("Loading...");
            this.dialog.show();
            this.dialog.setCancelable(false);
            this.dialog.setCanceledOnTouchOutside(false);
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {

            BaseDaoExamen conexion=new BaseDaoExamen(MainActivity.this);

            conexion.resetAutoincrement("tblpreguntasmodulo");

            if(conexion.eliminaPreguntas()){
                Log.e("Se han eliminado", "1");
            }

            List lista = conexion.ConsultarModulos();
            ModulosDTO materia=new ModulosDTO();
            int idModMateria =0;

            //CAMBIA LA LECTURA A ASSESTS

            // Reading text file from assets folder
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getAssets().open(
                        "tblpreguntasmodulo.json")));
                String temp;
                while ((temp = br.readLine()) != null)
                    sb.append(temp);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close(); // stop reading
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String myjsonstring = sb.toString();

            //FIN CAMBIA LA LECTURA A ASSETS

            //JSONObject json = null;
            JSONObject json = null;
            try {
                json = new JSONObject(myjsonstring);
            } catch (JSONException e) {
                e.printStackTrace();
            }




            // check log cat fro response
            Log.e("Create Response", json.toString());

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);


                    idCate = new int[products.length()];

                    // looping through All Products

                    Log.e("products", products.toString());

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID_CATEGORIA);
                        int idModulo = c.getInt(TAG_PID_MODULO);
                        String pregunta = c.getString(TAG_PREGUNTA);
                        String respuestaUno = c.getString(TAG_RESPUESTA_UNO);
                        String respuestaDos = c.getString(TAG_RESPUESTA_DOS);
                        String respuestaTres = c.getString(TAG_RESPUESTA_TRES);
                        String respuestaCuatro = c.getString(TAG_RESPUESTA_CUATRO);
                        String imagenPregunta = c.getString(TAG_IMAGEN_PREGUNTA);
                        String correcta = c.getString(TAG_RESPUESTA_CORRECTA);
                        String tooltip = c.getString(TAG_TOOLTIP);
                        String imagenTooltip = c.getString(TAG_IMAGEN_TOOLTIP);
                        String audioPregunta = c.getString(TAG_AUDIO_PREGUNTA);
                        String audioTooltip = c.getString(TAG_AUDIO_TOOLTIP);

                        materia.setIdmodulo(idModulo);
                        materia.setPregunta(pregunta);
                        materia.setRespuestauno(respuestaUno);
                        materia.setRespuestados(respuestaDos);
                        materia.setRespuestatres(respuestaTres);
                        materia.setRespuestacuatro(respuestaCuatro);
                        materia.setImagenpregunta(imagenPregunta);
                        materia.setCorrecta(correcta);
                        materia.setTooltip(tooltip);
                        materia.setImagentooltip(imagenTooltip);
                        materia.setAudiopregunta(audioPregunta);
                        materia.setAudiotooltip(audioTooltip);

                        if(conexion.insertaModulosExamen(materia)){
                            //Toast.makeText(this, "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        }else{
                            //Toast.makeText(this, "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }




                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;

        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success)
        {

            if(MainActivity.this.isFinishing()){
                return;
            }

            if(success==true){
                if (this.dialog.isShowing() && this.dialog != null)
                {
                    this.dialog.dismiss();
                }
            }else{
                new ImportarCategoriasCSVTask().execute("");
            }

            BaseDaoExamen conexionPreguntas=new BaseDaoExamen(MainActivity.this);
            List lista = conexionPreguntas.ConsultarModulos();

            if(lista.size()<=0){
                new ImportarCategoriasCSVTask().execute("");
            }

        }
    }


    public void buscaOpciones() {
        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listaopciones = conexion.ConsultarEscuelaSeleccionada();

        if(listaopciones.size()>0){
            escuelaElegida = true;
        }
    }

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.rowescuelas, parent, false);
            TextView label=(TextView)row.findViewById(R.id.company);
            label.setText(arrayAreas[position]);

//            TextView sub=(TextView)row.findViewById(R.id.sub);
//            sub.setText(opciones3[position]);

            ImageView icon=(ImageView)row.findViewById(R.id.imageEscuelasMain);

            icon.setImageResource(arrayImagenClasificacionAreas[position]);

            return row;
        }
    }





    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if(v.getId()==findViewById(R.id.guardaescuela).getId()){
            BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
            List listaopciones = conexion.ConsultarEscuelaSeleccionada();

            //opcionEscUno = listaAutoCompleteUno.getText().toString().trim();
            //opcionEscDos = listaAutoCompleteDos.getText().toString().trim();
            //opcionEscTres = listaAutoCompleteTres.getText().toString().trim();

            boolean ecnontreUno = false;
            boolean ecnontreDos = false;
            boolean ecnontreTres = false;

            /*if(!opcionEscUno.trim().equalsIgnoreCase("")){
                for(int i=0; i<arrayEscuelasCarreras.length; i++){
                    if(opcionEscUno.trim().equalsIgnoreCase(arrayEscuelasCarreras[i].trim())){
                        opcionEscUno = arrayEscuelasCarreras[i];
                        ecnontreUno = true;
                    }
                }

                if(!ecnontreUno){
                    opcionEscUno = "";
                }

                if(opcionEscUno == ""){
                    Toast toast = Toast.makeText(this, "¡En la opción Uno debes seleccionar una carrera que viene en el listado de carreras!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            if(!opcionEscDos.trim().equalsIgnoreCase("")){
                for(int i=0; i<arrayEscuelasCarreras.length; i++){
                    if(opcionEscDos.trim().equalsIgnoreCase(arrayEscuelasCarreras[i].trim())){
                        opcionEscDos = arrayEscuelasCarreras[i].trim();
                        ecnontreDos = true;
                    }
                }

                if(!ecnontreDos){
                    opcionEscDos = "";
                }


                if(opcionEscDos == ""){
                    Toast toast = Toast.makeText(this, "¡En la opción Dos debes seleccionar una carrera que viene en el listado de carreras!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            if(!opcionEscTres.trim().equalsIgnoreCase("")){
                for(int i=0; i<arrayEscuelasCarreras.length; i++){
                    if(opcionEscTres.trim().equalsIgnoreCase(arrayEscuelasCarreras[i].trim())){
                        opcionEscTres = arrayEscuelasCarreras[i].trim();
                        ecnontreTres = true;
                    }
                }

                if(!ecnontreTres){
                    opcionEscTres = "";
                }


                if(opcionEscTres == ""){
                    Toast toast = Toast.makeText(this, "¡En la opción Tres debes seleccionar una carrera que viene en el listado de carreras!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }*/

            if(!opcionEscUno.equalsIgnoreCase("Selecciona un área de estudio")){

                EscuelasDTO datos = new EscuelasDTO();
                int aciertosRequeridos = 0;
                int contadorActualiza = 1;
                int idActualiza = 0;


                for(Object datosArea: listaopciones){
                    EscuelasDTO elementos1=(EscuelasDTO) datosArea;

                    idActualiza = elementos1.getId();
                }

                if(listaopciones.size()>0){

                    datos.setId(idActualiza);
                    datos.setEscuelaLicenciatura(opcionEscUno);
                    datos.setAciertos(0);
                    conexion.ActualizaEscuelaSeleccionada(datos);


                    Toast toast = Toast.makeText(this, "¡Se actualizó tu área, continua estudiando por materia!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    estudaClases();
                }else{

                    datos.setEscuelaLicenciatura(opcionEscUno);
                    datos.setAciertos(0);
                    conexion.insertaEscuelas(datos);

                    Toast toast = Toast.makeText(this, "¡Se guardó tu área, comienza estudiando por materia!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    estudaClases();
                }

            }else{
                Toast toast = Toast.makeText(this, "¡Debes seleccionar un área de estudio!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


            /*opciones = new String[3];
            opciones[0] = opcionEscUno;
            opciones[1] = opcionEscDos;
            opciones[2] = opcionEscTres;


            if(opcionEscUno.equalsIgnoreCase(opcionEscDos) || opcionEscUno.equalsIgnoreCase(opcionEscTres)
                    || opcionEscDos.equalsIgnoreCase(opcionEscTres)
                    || opcionEscUno.equalsIgnoreCase("") ||
                        opcionEscDos.equalsIgnoreCase("") ||
                        opcionEscTres.equalsIgnoreCase("")
                    ){
                Toast toast = Toast.makeText(this, "¡Debes seleccionar 3 opciones diferentes!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{

                EscuelasDTO datos = new EscuelasDTO();
                int aciertosRequeridos = 0;
                int contadorActualiza = 1;

                if(listaopciones.size()>0){
                    for(int i=0; i < opciones.length; i++){
                        for(int k=0; k < arrayEscuelasCarreras.length; k++){
                            if(opciones[i].equalsIgnoreCase(arrayEscuelasCarreras[k])){
                                aciertosRequeridos = Integer.parseInt(arrayEscuelasPuntaje[k]);
                            }
                        }
                        datos.setId(contadorActualiza);
                        datos.setEscuelaLicenciatura(opciones[i]);
                        datos.setAciertos(aciertosRequeridos);
                        conexion.ActualizaEscuelaSeleccionada(datos);

                        contadorActualiza = contadorActualiza +1;
                    }

                    Toast toast = Toast.makeText(this, "¡Se actualizaron tus opciones, continua estudiando por materia!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    estudaPorMateria();
                }else{
                    for(int i=0; i < opciones.length; i++){
                        for(int k=0; k < arrayEscuelasCarreras.length; k++){
                            if(opciones[i].equalsIgnoreCase(arrayEscuelasCarreras[k])){
                                aciertosRequeridos = Integer.parseInt(arrayEscuelasPuntaje[k]);
                            }
                        }
                        datos.setEscuelaLicenciatura(opciones[i]);
                        datos.setAciertos(aciertosRequeridos);
                        conexion.insertaEscuelas(datos);
                    }

                    Toast toast = Toast.makeText(this, "¡Se guardaron tus opciones, comienza estudiando por materia!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    estudaPorMateria();
                }



            }*/

        }

        if(v.getId()==findViewById(R.id.estudiamateria).getId()){
            /*buscaOpciones();
            if(escuelaElegida){
                estudaPorMateria();
            }else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/
            estudaPorMateria();
        }


        if(v.getId()==findViewById(R.id.estudiaClase).getId()){
            estudaClases();
        }

        if(v.getId()==findViewById(R.id.eamenSimu).getId()){
            examenSimulacro();
        }

        if(v.getId()==findViewById(R.id.ahorcado).getId()){
            juegoAhorcado();
            //miraUnVideo();
        }

        /*if(v.getId()==findViewById(R.id.ganaDinero).getId()){
            ganaRDinero();
        }*/

    }

    /*private void ganaRDinero() {
        finish();
        Intent linsertar=new Intent(MainActivity.this, InstrucionesGanaDinero.class);
        linsertar.putExtra(Constantes.DINERO_TRABAJADO, 3);
        startActivity(linsertar);
    }*/


    private void juegoAhorcado() {
        /*finish();
        Intent linsertar=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(linsertar);*/
    }


    public void estudaClases(){
        finish();
        Intent linsertar=new Intent(MainActivity.this, ListaClasesActivity.class);
        startActivity(linsertar);
    }

    public void estudaPorMateria(){
        finish();
        Intent linsertar=new Intent(MainActivity.this, SeleccionaEscuela.class);
        startActivity(linsertar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void mostrarInfoPago() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("version_pro.json");

        builder.setMessage("Solo disponible para la versiòn PRO")
                .setTitle("Versiòn PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("Versiòn Pro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                final String appPackageName ="com.mra.guaunampremium";
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


    private void consultaVoto(){

        /*BaseDaoPassword conexion = new BaseDaoPassword(this);

        List listaConsulta = conexion.ConsultarPttoDetalle(1);

        if(listaConsulta.size()>0){
            examenDetalle();
        }else{
            mostrarInfoPago();
        }*/

        examenDetalle();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        buscaOpciones();
        if (id == R.id.ptto) {
            // Handle the camera action

            //if(escuelaElegida){
                examenSimulacro();
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/

        } else if (id == R.id.grafica) {
            //if(escuelaElegida){
                resultadosExamen();
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/
        } else if (id == R.id.materia) {
            //if(escuelaElegida){
                califMaterias();
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/



        } else if (id == R.id.diagnostico) {
            //if(escuelaElegida){

                cuentaPreguntas();
                if(examenReactivosResuelto==80){
                    diagnosticoExamen();
                    //mostrarInfoPago();
                }else{
                    Toast toast = Toast.makeText(this, "Complete the test!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/

      /*  }else if (id == R.id.movimientosfrec) {
            if(escuelaElegida){
                resultadosModulos();
            }else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tus escuelas!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/

       /* }else if (id == R.id.escuelas) {
            if(escuelaElegida){
                cuentaPreguntas();
                if(examenReactivosResuelto==120){
                    califEscuelas();
                }else{
                    Toast toast = Toast.makeText(this, "¡Para ver esta opciòn debes completar el examen simulacro!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tus licenciaturas!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/

        }else if (id == R.id.examendetalle) {
            //if(escuelaElegida){
                cuentaPreguntas();
                if(examenReactivosResuelto==80){
                    consultaVoto();
                }else{
                    Toast toast = Toast.makeText(this, " Complete the test!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/

        }else if (id == R.id.repcate) {
            //   if(escuelaElegida){
            reportarProblemas();
            //   }else{
            //       Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tus escuelas!", Toast.LENGTH_LONG);
            //       toast.setGravity(Gravity.CENTER, 0, 0);
            //       toast.show();
            //   }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void examenSimulacro(){

        //buscaOpciones();

            //if(escuelaElegida){
                finish();
                Intent linsertar=new Intent(this, SeleccionaTestActivity.class);;
                startActivity(linsertar);
            /*}else{
                Toast toast = Toast.makeText(this, "¡Antes de comenzar debes seleccionar tu área de estudios!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }*/
    }

    public void resultadosExamen(){
        finish();
        String historico = "historia";
        Intent linsertar=new Intent(this, ResultadoExamen.class);
        linsertar.putExtra("aciertos", historico);
        startActivity(linsertar);
    }

    public void resultadosModulos(){
        finish();
        Intent linsertar=new Intent(this, ResultadoModulo.class);
        linsertar.putExtra("modulo", LidFinal);
        startActivity(linsertar);
    }

    public void diagnosticoExamen(){
        finish();
        Intent linsertar=new Intent(this, DiagnosticoExamen.class);
        startActivity(linsertar);
    }

    public void examenDetalle(){
        finish();
        Intent linsertar=new Intent(this, ResultadoExamenDetalle.class);
        startActivity(linsertar);
    }

    public void cuentaPreguntas(){
        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List liestaExamen = conexion.ConsultarExamenResultados();
        examenReactivosResuelto = liestaExamen.size();

    }

    public void califEscuelas(){
        /*finish();
        Intent linsertar=new Intent(this, CalificacionEscuelas.class);
        startActivity(linsertar);*/
    }

    public void califMaterias(){
        finish();
        Intent linsertar=new Intent(this, ResultadoModulo.class);
        startActivity(linsertar);
    }

    public void reportarProblemas(){
        finish();
        Intent linsertar=new Intent(this, Reportar.class);
        startActivity(linsertar);
    }
}
