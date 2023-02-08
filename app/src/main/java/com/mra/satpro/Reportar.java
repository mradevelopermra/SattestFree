package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mra.satpro.utilerias.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reportar extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_flecha;
    Button btnReportar;
    EditText email, problematica;
    Context ctx = this;

    private static String url_valida_user = "http://www.pypsolucionesintegrales.com/ventas/reporta_problema_comipems.php";
    private ProgressDialog pDialog;
    String mail, reporte, mailMensaje;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    int exitoso;
    private ProgressDialog pDialogCate;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        btnReportar = (Button) findViewById(R.id.btnReportar);
        btnReportar.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email);
        problematica = (EditText) findViewById(R.id.problematica);

    }


    class CreateNewProduct extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogCate = new ProgressDialog(Reportar.this);
            pDialogCate.setMessage("Reportando...");
            pDialogCate.setIndeterminate(false);
            pDialogCate.setCancelable(false);
            pDialogCate.show();
        }



        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            //Inicio Consultar datos de la base local para insertar los datos en la nuebe

        /*    File dbFile=getDatabasePath("Gastos7.db");
            BaseDaoGastos conexionGastos = new BaseDaoGastos(ImportaExportaNube.this);
            List listaGasto = conexionGastos.llenarConsultaGastos();

            File dbFileIngresos=getDatabasePath("IngresosControl7.db");
            BaseDaoIngresos conexionIngresos = new BaseDaoIngresos(ImportaExportaNube.this);
            List listasIngresos = conexionIngresos.llenarConsultaIngresos();

            File dbFileDeudas=getDatabasePath("Deudas7.db");
            BaseDaoDeudas conexionDeudas = new BaseDaoDeudas(ImportaExportaNube.this);
            List listasDeudas = conexionDeudas.llenarConsultaDeudas();

            List listasPagos = conexionDeudas.llenarConsultaPagos();
            List listasAhorros = conexionDeudas.llenarAhorros();

            File dbFileCateGastos=getDatabasePath("CateGastos7.db");
            BaseDaoCateGastos conexionCategorias = new BaseDaoCateGastos(ImportaExportaNube.this);
            List listasCategorias = conexionCategorias.llenarCategoriaGastos();*/

            //Fin Consultar datos de la base local para insertar los datos en la nuebe

            //Inicio de la insersecion de los datos locales a la nube

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
            Log.e("mail", mail);
            Log.e("reporte", reporte);

               /* List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("mail", mail));
                params.add(new BasicNameValuePair("password", password));*/

            HashMap<String, String> params = new HashMap<>();
            params.put("mail", mailMensaje);
            params.put("reporte", reporte);

            // getting JSON Object
            // Note that create product url accepts POST method
            try {
                json = jsonParser.makeHttpRequest(url_valida_user,
                        "POST", params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            // check log cat fro response
            Log.e("Create Response", json.toString());

            //   }

            //Fin de la insersecion de los datos locales a la nube



            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                exitoso = json.getInt(TAG_SUCCESS);
                Log.e("exitoso", exitoso+"");
//                userFinal = json.getInt(TAG_SUCCESS);
//
//                Log.e("userFinal", userFinal+"");

                if (success != 0) {
                    // successfully created product

                    //guardar();
                    // Toast.makeText(ImportaExportaNube.this, "Se cargo usuario",Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(getApplicationContext(), ExportarNube.class);
                    //startActivity(i);

                    // closing this screen
                    //finish();
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

            if(exitoso==1){
                Toast.makeText(Reportar.this, "El reporte se envió correctamente",Toast.LENGTH_SHORT).show();
            }

            try
            {
                if (pDialogCate != null && pDialogCate.isShowing()) {
                    pDialogCate.dismiss();
                }
            }
            catch(Exception ex)
            {
                Log.e("Error in MainActivity", ex.toString());
            }


        }



    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if ( pDialogCate!=null && pDialogCate.isShowing() ){
            pDialogCate.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

            finish();
            Intent linsertar=new Intent(Reportar.this, MainActivity.class);
            startActivity(linsertar);

        /*    img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(Reportar.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
        }

        if(v.getId()==findViewById(R.id.btnReportar).getId()){

            mail = email.getText().toString();
            mailMensaje = mail + " UNAM-Android-premium";
            reporte = problematica.getText().toString();

            if(!mail.equalsIgnoreCase("") && isEmailValid(mail)){
                if(!reporte.equalsIgnoreCase("") && reporte.length()>25){
                    try
                    {
                        new CreateNewProduct().execute("");
                        email.setText("");
                        problematica.setText("");
                    }
                    catch(Exception ex)
                    {
                        Log.e("Error in MainActivity", ex.toString());
                    }
                }else{
                    Toast.makeText(this, getResources().getString(R.string.problemadescriptivo), Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, getResources().getString(R.string.mailvalida), Toast.LENGTH_SHORT).show();
            }

        }



    }
}
