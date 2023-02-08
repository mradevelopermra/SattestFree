package com.mra.satpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dao.BaseDaoPassword;
import com.mra.satpro.dto.PasswordDTo;
import com.mra.satpro.models.Vidas;
import com.mra.satpro.utilerias.Global;
import com.mra.satpro.utilerias.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.mra.satpro.utilerias.Helpers.isOnlineNet;

public class ConfigurarModulo extends AppCompatActivity implements View.OnClickListener{

    Spinner cantidadPreguntas;
    Button btnAceptarCon;
    private ImageView img_flecha;
    private RadioButton si, no, activar, descativar;
    String tipoTest;
    String ayuda = "";
    String activarJuego = "";
    String preguntasTotal = "";

    String [] periodos;

    final Context context = this;
    AlertDialog alertaGastos;
    AlertDialog comprado;
    int totalPreg = 0;

    //valida compra existosa
    private ProgressDialog pDialog;
    private static String url_valida_user = "http://www.pypsolucionesintegrales.com/ventas/valida_user_escuelas.php";
    //varibles del servidor
    String idServidor = "";
    String nombreServidor = "";
    String passwordServidor = "";
    String mailServidor = "";
    String estatusServidor = "";

    //variabnles de la base local
    String id;
    String nombre;
    String password;
    String mail;
    String estatus;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ESTATUS = "estatus";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PRODUCTS = "products";
    JSONArray products = null;
    int exitoso;

    String correo = "";
    String passwordConsulta= "";
    EditText editMail;
    EditText editPassword;

    String random = "Si";

    private String currentDate = "";
    public final BaseDaoPassword conVidas = new BaseDaoPassword(this);
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_modulo);

        tipoTest=getIntent().getExtras().getString("tipoTest");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        agregaVidas(3, "inicial");
        mContext = this;
        //infoTipoPago();
        //preguntasCortesia();

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        btnAceptarCon = (Button) findViewById(R.id.btnAceptarCon);
        btnAceptarCon.setOnClickListener(this);

        cantidadPreguntas = (Spinner) findViewById(R.id.cantidadPreguntas);

        periodos = new String[5];

        periodos[0] = "10";
        periodos[1] = "20";
        periodos[2] = "30";
        periodos[3] = "40";

        BaseDaoExamen conexion = new BaseDaoExamen(this);

        if(tipoTest.equalsIgnoreCase("Reading")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 14";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());
        }
        if(tipoTest.equalsIgnoreCase("Writing and Language")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 15";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);


            periodos[4] = String.valueOf(listaPreguntas.size());
        }
        if(tipoTest.equalsIgnoreCase("Math")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 16";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
        if(tipoTest.equalsIgnoreCase("Biology")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 17";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
  /*      if(tipoTest.equalsIgnoreCase("geomex")){
            periodos[4] = String.valueOf(preguntasBloqueGeoMex.length);
        }*/
        if(tipoTest.equalsIgnoreCase("Chemistry")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 18";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
      /*  if(tipoTest.equalsIgnoreCase("razmate")){

            periodos[4] = String.valueOf(preguntasBloqueRazMate.length);

        }*/
        if(tipoTest.equalsIgnoreCase("Physics")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 19";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
        if(tipoTest.equalsIgnoreCase("Spanish")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 20";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
        if(tipoTest.equalsIgnoreCase("quimi")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 11";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
        if(tipoTest.equalsIgnoreCase("biolo")){

            String query = "select * from tblpreguntasmodulo where idmodulo = 4";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());

        }
        if(tipoTest.equalsIgnoreCase("geouniv")){
            String query = "select * from tblpreguntasmodulo where idmodulo = 5";
            List listaPreguntas = conexion.ConsultarExamenesRandomTodas(query);

            periodos[4] = String.valueOf(listaPreguntas.size());
        }


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,  periodos);

        //cantidadPreguntas.setAdapter(adapter3);

        cantidadPreguntas.setAdapter(new MyAdapter5(this, R.layout.row, periodos));

        cantidadPreguntas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item3 = parent.getItemAtPosition(pos);

//                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colormonto));
//                ((TextView) parent.getChildAt(0)).setTextSize(20);
                preguntasTotal = String.valueOf(item3);
                totalPreg = Integer.parseInt(preguntasTotal);
               /* if(totalPreg>20){
                    infoTipoPago();
                    //consultar();
                }*/

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Log.e("tipoTest: ", tipoTest);

        si=(RadioButton)findViewById(R.id.si);
        no=(RadioButton)findViewById(R.id.no);

        activar=(RadioButton)findViewById(R.id.activar);
        descativar=(RadioButton)findViewById(R.id.descativar);

        si.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    ayuda = "Si";
                    no.setChecked(false);
                }
            }
        });

        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    ayuda = "No";
                    si.setChecked(false);
                }
            }
        });


        activar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    activarJuego = "Si";
                    descativar.setChecked(false);
                }
            }
        });

        descativar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    activarJuego = "No";
                    activar.setChecked(false);
                }
            }
        });


    }

    int vidasActuales = 0;
    private void consultaAgregaVidas(){
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ArrayList<Vidas> listaVidas = (ArrayList<Vidas>) conVidas.consultaVidas(currentDate);
        Log.e("listaVidasAntes", listaVidas.size()+"");
        if(listaVidas.size()>0){
            vidasActuales = listaVidas.get(0).getNumeroVidas();
        }
    }

    public void agregaVidas(int numVidasAgrega, String escenario){

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ArrayList<Vidas> listaVidas = (ArrayList<Vidas>) conVidas.consultaVidas(currentDate);
        Log.e("listaVidasAntes", listaVidas.size()+"");
        Vidas modeloVidas = new Vidas();
        int vidasActuales = 0;
        if(listaVidas.size()>0){
            vidasActuales = listaVidas.get(0).getNumeroVidas();
        }

        switch (escenario){
            case "inicial":

                if(listaVidas.size() <= 0){
                    modeloVidas.setNumeroVidas(numVidasAgrega);
                    modeloVidas.setFecha(currentDate);
                    modeloVidas.setEstatus("1");

                    conVidas.insertaVida(modeloVidas);
                }
                break;
            case "vidaVideo":
                if(vidasActuales <= 1){
                    vidasActuales++;
                }
                modeloVidas.setNumeroVidas(vidasActuales);
                modeloVidas.setFecha(currentDate);
                modeloVidas.setEstatus("1");
                conVidas.updateVidas(modeloVidas);
                break;

            case "restaVidas":

                if(vidasActuales > 0){
                    vidasActuales--;
                }else if(vidasActuales<=0){
                    vidasActuales = 0;
                }
                modeloVidas.setNumeroVidas(vidasActuales);
                modeloVidas.setFecha(currentDate);
                modeloVidas.setEstatus("1");

                conVidas.updateVidas(modeloVidas);
                break;


        }

        listaVidas = (ArrayList<Vidas>)conVidas.consultaVidas(currentDate);
        Log.e("listaVidasDespues", listaVidas.size()+"");
    }


    public class MyAdapter5 extends ArrayAdapter<String>{

        public MyAdapter5(Context context, int textViewResourceId, String[] objects) {
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
            View row=inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.company);
            label.setText(periodos[position]);

//            TextView sub=(TextView)row.findViewById(R.id.sub);
//            sub.setText(opciones3[position]);


            return row;
        }
    }


    public void tipoJuego(){

        Global.preguntaSigPlayGlobal = 0;

        random = "Si";

        if(tipoTest.equalsIgnoreCase("Math")){

            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("Biology")){

            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("Reading")){

            finish();
            Intent linsertar=new Intent(this, Crucigrama.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);

        }
        if(tipoTest.equalsIgnoreCase("Writing and Language")){

            finish();
            Intent linsertar=new Intent(this, Crucigrama.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);

        }
  /*      if(tipoTest.equalsIgnoreCase("geomex")){
            periodos[4] = String.valueOf(preguntasBloqueGeoMex.length);
        }*/
        if(tipoTest.equalsIgnoreCase("Spanish")){
            finish();
            Intent linsertar=new Intent(this, Crucigrama.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);

        }
      /*  if(tipoTest.equalsIgnoreCase("razmate")){

            periodos[4] = String.valueOf(preguntasBloqueRazMate.length);

        }*/
        if(tipoTest.equalsIgnoreCase("Chemistry")){
            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("Physics")){
            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("quimi")){
            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("biolo")){
            finish();
            Intent linsertar=new Intent(this, SkyInvaderPlay.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }
        if(tipoTest.equalsIgnoreCase("geouniv")){
            finish();
            Intent linsertar=new Intent(this, Crucigrama.class);
            linsertar.putExtra("tipoTest", tipoTest);
            linsertar.putExtra("autoAyuda", ayuda);
            linsertar.putExtra("preguntasTotal", preguntasTotal);
            linsertar.putExtra("random", random);
            startActivity(linsertar);
        }


    }


    private void mostrarInfoPago() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConfigurarModulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("version_pro.json");

        builder.setMessage(getResources().getString(R.string.masPreguntas))
                .setTitle(" PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("  Pro", new DialogInterface.OnClickListener()
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

    private void preguntasCortesia() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConfigurarModulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("donativo.json");

        builder.setMessage(getResources().getString(R.string.masPreguntasVideo))
                .setTitle("Free quiz");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
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


    private void infoTipoPago(){


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.version_pago, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);


        TextView textView30 = (TextView) promptView.findViewById(R.id.textView30);
        textView30.setText(getResources().getString(R.string.masPreguntas));

        Button buttonCerrar = (Button) promptView.findViewById(R.id.closepago);
        buttonCerrar.setOnClickListener(cerrarVentanaAyuda);

        Button comprafull = (Button) promptView.findViewById(R.id.comprafull);
        comprafull.setOnClickListener(compras);

        Button yalocompre = (Button) promptView.findViewById(R.id.yalocompre);
        yalocompre.setVisibility(View.GONE);
        yalocompre.setOnClickListener(yaLocompre);

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

            final String appPackageName ="com.mra.guaunampro";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            // Compras();
        }
    };

    private View.OnClickListener yaLocompre = new View.OnClickListener() {
        public void onClick(View v) {

        /*    final String appPackageName ="com.mra.examenexaniipro";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }

            ConfigurarModulo.this.finish();
            finish();
            Intent linsertar=new Intent(ConfigurarModulo.this, Pagos.class);
            startActivity(linsertar);*/

            consultaUsuarioEnLanube();
        }
    };

    private void Compras(){
        String mensa = "1";
       /* finish();
        Intent linsertar=new Intent(ConfigurarModulo.this, Pagos.class);
        linsertar.putExtra("mensa", mensa);
        startActivity(linsertar);*/
    }

    private void ComprasNoExisteUsario(){
        String mensa = "2";
        /*finish();
        Intent linsertar=new Intent(ConfigurarModulo.this, Pagos.class);
        linsertar.putExtra("mensa", mensa);
        startActivity(linsertar);*/
    }


    private void consultaUsuarioEnLanube(){


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.formulario_pagado, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        editMail = (EditText) promptView.findViewById(R.id.editMail);
        editPassword = (EditText) promptView.findViewById(R.id.editPassword);

        Button login = (Button) promptView.findViewById(R.id.login);
        login.setOnClickListener(entrar);

        comprado = alertDialogBuilder.show();
        comprado.setCanceledOnTouchOutside(true);

    }

    private View.OnClickListener entrar = new View.OnClickListener() {
        public void onClick(View v) {

            correo = editMail.getText().toString();
            passwordConsulta = editPassword.getText().toString();

            if(haveNetworkConnection()==true){
                new ConsultaUsuarios().execute();
            }else{
                mensajeNohayRed();
            }

            if(exitoso==1){
                ComprasNoExisteUsario();
            }
            if(exitoso==0){
                alertaGastos.dismiss();
                Compras();
            }
            comprado.dismiss();
        }
    };

    private void mensajeNohayRed(){
        Toast.makeText(this, getResources().getString(R.string.nohayred), Toast.LENGTH_LONG).show();
    }

    /**
     * Background Async Task to Create new product
     * */
    int success = 0;

    class ConsultaUsuarios extends AsyncTask<String, String, String> {

        /**
         * Before starting backgrConsultaUsuariosound thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ConfigurarModulo.this);
            pDialog.setMessage(getResources().getString(R.string.verificandoUsuario));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            JSONObject json = null;

        /*    for(Object datos: listasIngresos){
                IngresosDTO elementosIng=(IngresosDTO) datos;

                String id = String.valueOf(elementosIng.getId());
                String conIngreso = elementosIng.getNorecibo();
                String tipoIngreso = elementosIng.getRfc();
                String datefeinicial = elementosIng.getFecha();
                String Importe = String.valueOf(elementosIng.getImporte());
                String Imagen = String.valueOf(elementosIng.getImage());


                String arrStrIngresos[] ={id, conIngreso,
                        tipoIngreso, datefeinicial, Importe, Imagen};*/

            // Building Parameters
            // Building Parameters
            Log.e("mail", correo);
            Log.e("password", passwordConsulta);

            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", mail));
            params.add(new BasicNameValuePair("password", password));*/

            HashMap<String, String> params = new HashMap<>();
            params.put("mail", correo);
            params.put("password", passwordConsulta);

            // getting JSON Object
            // Note that create product url accepts POST method
            try {
                json = jsonParser.makeHttpRequest(url_valida_user,
                        "POST", params);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // check log cat fro response
            Log.e("Create Response", json.toString());

            //   }


            try {
                success = json.getInt(TAG_SUCCESS);
                Log.e("success", success+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(success==1){
                ComprasNoExisteUsario();
            }


            //Fin de la insersecion de los datos locales a la nube
            // check for success tag
            try {

                products = json.getJSONArray(TAG_PRODUCTS);

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                    // Storing each json item in variable
                    estatusServidor = c.getString(TAG_ESTATUS);
                    nombreServidor = c.getString(TAG_NOMBRE);
                    passwordServidor = c.getString(TAG_PASSWORD);
                    mailServidor = c.getString(TAG_MAIL);

                }


                exitoso = json.getInt(TAG_SUCCESS);

//                userFinal = json.getInt(TAG_SUCCESS);
//
                Log.e("estatusServidor", estatusServidor+"");

                if (exitoso == 0) {
                    // successfully created product

                    //guardar();
                    // Toast.makeText(ImportaExportaNube.this, "Se cargo usuario",Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(getApplicationContext(), ExportarNube.class);
                    //startActivity(i);

                    // closing this screen
                    //finish();
                    BaseDaoPassword conexiones=new BaseDaoPassword(ConfigurarModulo.this);

                    PasswordDTo datos=new PasswordDTo();

                    if(estatusServidor.equalsIgnoreCase("1")){

                        datos.setNombre(nombreServidor);
                        datos.setPassword(passwordServidor);
                        Log.e("Password", passwordServidor);
                        datos.setMail(mailServidor);
                        datos.setEstatus(estatusServidor);


                        if(conexiones.insertaPassword(datos)){

                            //Toast.makeText(this, getResources().getString(R.string.ventaOk), Toast.LENGTH_SHORT).show();
                        }else{
                            //Toast.makeText(this, getResources().getString(R.string.ventaNo), Toast.LENGTH_SHORT).show();
                        }
                      /*  if(alertaGastos.isShowing()){
                            alertaGastos.dismiss();
                        }*/

                        //new ImportIngresosCSVTask().execute("");
                    }else if(!estatusServidor.equalsIgnoreCase("1")){

                        datos.setNombre(nombreServidor);
                        datos.setPassword(passwordServidor);
                        Log.e("Password", passwordServidor);
                        datos.setMail(mail);
                        datos.setEstatus(estatusServidor);

                        if(conexiones.ActualizarPassword(datos)){
                            Log.e("UsuarioOk", "Actualizado");
                            //Toast.makeText(this, getResources().getString(R.string.usuarioActualizar), Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("UsuarioNo", "No se Actualizó");
                            //Toast.makeText(this, getResources().getString(R.string.usuarioActualizarNo), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(ImportarDesdeNube.this, getResources().getString(R.string.AccesoIncorrecto),Toast.LENGTH_SHORT).show();
                    }
                } else {


                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            Log.e("estatusServidor...",estatusServidor+"");
            pDialog.dismiss();

            if(estatusServidor.equalsIgnoreCase("0")){
                ComprasNoExisteUsario();
            }

        }

    }




    public void menuPrincipal(){

        //rewardedVideoAdListener.onRewardedVideoAdClosed();
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public void verMateria(){
        //mRewardedVideoAd.destroy();
        //rewardedVideoAdListener.onRewardedVideoAdClosed();
        finish();
        Intent linsertar=new Intent(this, Modulo.class);
        linsertar.putExtra("tipoTest", tipoTest);
        linsertar.putExtra("autoAyuda", ayuda);
        linsertar.putExtra("preguntasTotal", preguntasTotal);
        linsertar.putExtra("random", random);
        startActivity(linsertar);
    }

    String tipoDeMateria = "";


    public void consultar(){

        BaseDaoPassword conexiones=new BaseDaoPassword(this);

        List listas = conexiones.ConsultarPttoDetalle(1);
        Log.e("listas", listas.size()+"");

        for(Object datos: listas){
            PasswordDTo elementosIng=(PasswordDTo) datos;

            id = String.valueOf(elementosIng.getId());
            nombre = elementosIng.getNombre();
            password = elementosIng.getPassword();
            mail = elementosIng.getMail();
            estatus = String.valueOf(elementosIng.getEstatus());

            correo = mail;
            passwordConsulta = password;

        }


        if(listas.size()>0){
            if(haveNetworkConnection()==true){
                new ConsultaUsuarios().execute();
            }
        }


        if(listas.size()<=0){
            infoTipoPago();
        }else{
            if(estatus.equalsIgnoreCase("1")){
                String random = "Si";
                if(!activarJuego.equalsIgnoreCase("")){
                    if(activarJuego.equalsIgnoreCase("No")){
                        if(!ayuda.equalsIgnoreCase("")){
                            finish();
                            Intent linsertar=new Intent(this, Modulo.class);
                            linsertar.putExtra("tipoTest", tipoTest);
                            linsertar.putExtra("autoAyuda", ayuda);
                            linsertar.putExtra("preguntasTotal", preguntasTotal);
                            linsertar.putExtra("random", random);
                            startActivity(linsertar);
                        }else{
                            Toast toast = Toast.makeText(this,"Select a help option", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    if(activarJuego.equalsIgnoreCase("Si")){
                        Log.e("tipoTest", tipoTest);
                        if(!ayuda.equalsIgnoreCase("")){
                            tipoJuego();
                     /*   finish();
                        Intent linsertar=new Intent(this, Pagos.class);
                        linsertar.putExtra("tipoTest", tipoTest);
                        linsertar.putExtra("autoAyuda", ayuda);
                        linsertar.putExtra("preguntasTotal", preguntasTotal);
                        startActivity(linsertar);*/
                        }else{
                            Toast toast = Toast.makeText(this,"Select a help option", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }else{
                    Toast toast = Toast.makeText(this,"Select a game option", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

        }




      /*  for(Object datos: listas){
            PasswordDTo elementosIng=(PasswordDTo) datos;

            id = String.valueOf(elementosIng.getId());
            nombre = elementosIng.getNombre();
            password = elementosIng.getPassword();
            mail = elementosIng.getMail();
            estatus = String.valueOf(elementosIng.getEstatus());

            editNombre.setText(nombre);
            editPassword.setText(password);
            editPassword2.setText(password);
            editMail.setText(mail);

            if(estatus.equals("1")){
                Log.e("estatus 1", estatus);
                altapassword.setChecked(!altapassword.isChecked());
            }
            if(estatus.equals("2")){
                Log.e("estatus 2", estatus);
                bajapwd.setChecked(!bajapwd.isChecked());
            }
        }*/

    }

   /* public void showRewardedAd() {
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }*/

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
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

         /*   img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(ConfigurarModulo.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
            finish();
            Intent linsertar=new Intent(ConfigurarModulo.this, SeleccionaEscuela.class);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.btnAceptarCon).getId()){
            //   if(totalPreg<=10){
            activarJuego = "No";

            consultaAgregaVidas();
            // }else {
            if(totalPreg>40){
                //preguntasCortesia();
                //infoTipoPago();
                mostrarInfoPago();
                //consultar();
            }else{

                if(vidasActuales<=0){
                    mostrarInfoVidas();
                }else{
                    Log.e("clic", "materia");
                    random = "No";
                    if(!activarJuego.equalsIgnoreCase("")){
                        if(activarJuego.equalsIgnoreCase("No")){
                            if(!ayuda.equalsIgnoreCase("")){

                                tipoDeMateria = "materia";
                                verMateria();
                            /*if(haveNetworkConnection()==true){
                                new conectividadCSVTask().execute();
                                if(conectividad){
                                    verMateria();
                                }

                            }else{
                                showAlertDialogEliminarDatosConductores();
                            }*/

                            }else{
                                Toast toast = Toast.makeText(this,"Selecciona una opción de ayuda", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                        if(activarJuego.equalsIgnoreCase("Si")){
                            Log.e("tipoTest", tipoTest);
                            if(!ayuda.equalsIgnoreCase("")){

                                tipoDeMateria = "materiaJuego";
                                tipoJuego();

                            /*if(haveNetworkConnection()==true){
                                new conectividadCSVTask().execute();
                                if(conectividad){
                                    tipoJuegoGratis();
                                }
                            }else{
                                showAlertDialogEliminarDatosConductores();
                            }*/

                            }else{
                                Toast toast = Toast.makeText(this,"Selecciona una opción de ayuda", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }else{
                        Toast toast = Toast.makeText(this,"Selecciona una opción de juego", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }


            }
        }


    }

    private void mostrarInfoVidas() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConfigurarModulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("juega_gana.json");

        builder.setMessage(getResources().getString(R.string.masPreguntasIlimitadas))
                .setTitle("  PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("  Pro", new DialogInterface.OnClickListener()
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

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                regresaMenu();
            }
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void regresaMenu(){
        finish();
        Intent linsertar=new Intent(ConfigurarModulo.this, SeleccionaEscuela.class);
        startActivity(linsertar);
    }

    @Override
    public void onBackPressed() { }
}
