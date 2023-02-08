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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.utilerias.JSONParser;

import org.json.JSONArray;

import java.util.List;

import static com.mra.satpro.utilerias.Helpers.isOnlineNet;

public class InfoExamen extends AppCompatActivity implements View.OnClickListener {

    Button comenzar, btnversionpro;
    private ImageView img_flecha;

    GridView estructura;

    String [] periodos = {
            "Español",
            "Razonamiento verbal",
            "Historia universal",
            "Historia de Mexico",
            "Geografía universal",
            "Geografía de Mexico",
            "Civismo",
            "Razonamiento matemático",
            "Matemáticas",
            "Física",
            "Química",
            "Biología"
    };

    String [] reactivos = {
            "12",
            "16",
            "6",
            "6",
            "6",
            "6",
            "12",
            "16",
            "12",
            "12",
            "12",
            "12"
    };

    String [] practica = {
            "70",
            "50",
            "50",
            "50",
            "50",
            "50",
            "50",
            "70",
            "70",
            "70",
            "70",
            "50"
    };


    String[] datosgrid;


    int success = 0;
    private ProgressDialog pDialog;

    String correo = "";
    String passwordConsulta= "";
    EditText editMail;
    EditText editPassword;

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

    private static String url_valida_user = "http://www.pypsolucionesintegrales.com/ventas/valida_user_escuelas.php";

    String examenAleatorio = "";


    Context context;
    AlertDialog alertaGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_examen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;

        //    estructura = (GridView) findViewById(R.id.estructura);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        comenzar = (Button) findViewById(R.id.comenzar);
        comenzar.setOnClickListener(this);

        btnversionpro = (Button) findViewById(R.id.btnversionpro);
        btnversionpro.setOnClickListener(this);

     /*   try {
            llenarGastos();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        // cuentaPreguntas();

    }

    List liestaExamen = null;
    public void cuentaPreguntas(){
        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        liestaExamen = conexion.ConsultarExamenResultados();
        if(liestaExamen.size()>0){
            infoTipoPago();
        }

    }

    private void infoTipoPago(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.version_promo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        TextView txtMensajedeCompra = (TextView) promptView.findViewById(R.id.txtMensajedeCompra);
        txtMensajedeCompra.setText(getResources().getString(R.string.unExamen));

        Button btnCerrar = (Button) promptView.findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(cerrarVentanaAyuda);

        Button btnComprar = (Button) promptView.findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(compras);

        alertaGastos = alertDialogBuilder.show();
        alertaGastos.setCanceledOnTouchOutside(false);

    }

    private View.OnClickListener cerrarVentanaAyuda = new View.OnClickListener() {
        public void onClick(View v) {

            alertaGastos.dismiss();
            menuPrincipal();
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

        }
    };



    @Override
    public void onResume() {
        super.onResume();
    }

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



    public void examenSimulacro(){
        //mRewardedVideoAd.destroy();
        examenAleatorio = "Si";
        finish();
        Intent linsertar=new Intent(this, Examen.class);
        linsertar.putExtra("examenAleatorio", examenAleatorio);
        startActivity(linsertar);
    }

    public void menuPrincipal(){
        //mRewardedVideoAd.destroy();
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);

    }

    private void showAlertDialogEliminarDatosConductores()
    {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(InfoExamen.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage(getString(R.string.conexion));
        alertDialog.setCancelable(false);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.si),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.noGracias),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final String appPackageName ="com.mra.guaunampro";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        /*finish();
                        Intent linsertar=new Intent(Crucigrama.this, MainActivity.class);
                        startActivity(linsertar);*/

                    }
                });
        alertDialog.show();
    }

    Context ctx = this;
    boolean conectividad;

    class conectividadCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        // can use UI thread here

        protected void onPreExecute()

        {
            this.dialog.setMessage("Verificando conectividad...");
            this.dialog.show();
            this.dialog.setCancelable(false);
            this.dialog.setCanceledOnTouchOutside(false);
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {

            conectividad = isOnlineNet();
            isOnline();

            return true;

        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (this.dialog.isShowing() && this.dialog != null)
            {
                this.dialog.dismiss();
            }

            if(!conectividad){
                showAlertDialogConexion();
            }


            if(conectividad){
                examenSimulacro();
            }


        }

    }

    String nombreConexion = "";

    public void isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        nombreConexion = netInfo.getExtraInfo();

    }

    private void showAlertDialogConexion()
    {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(InfoExamen.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage("Con la siguiente red: " + nombreConexion + ", no cuentas con acceso a " +
                "internet y la aplicación no funcionará adecuadamente, por favor cambia de red " +
                "o descarga la versión premium que funciona sin acceso a internet.");
        alertDialog.setCancelable(false);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.noGracias),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final String appPackageName ="com.mra.guaunampro";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        /*finish();
                        Intent linsertar=new Intent(Crucigrama.this, MainActivity.class);
                        startActivity(linsertar);*/

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        //menuPrincipal();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

        /*    img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(InfoExamen.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
            finish();
            Intent linsertar=new Intent(InfoExamen.this, MainActivity.class);
            startActivity(linsertar);
        }

        if(v.getId()==findViewById(R.id.comenzar).getId()){

            examenSimulacro();

            /*if(haveNetworkConnection()==true){
                new conectividadCSVTask().execute();
                if(conectividad){
                    examenSimulacro();
                }
            }else{
                showAlertDialogEliminarDatosConductores();
            }*/

        }

        if(v.getId()==findViewById(R.id.btnversionpro).getId()){
            //Compras();
            final String appPackageName ="com.mra.examenunampremiumpremium";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }


    }
}
