package com.mra.satpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.clans.fab.FloatingActionButton;
import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ModulosDTO;
import com.mra.satpro.skyinvaders.Bitmaps;
import com.mra.satpro.skyinvaders.Game;
import com.mra.satpro.skyinvaders.GameLooper;
import com.mra.satpro.skyinvaders.GameView;
import com.mra.satpro.skyinvaders.SpaceInvadersGame;
import com.mra.satpro.utilerias.Calculator;
import com.mra.satpro.utilerias.Global;
import com.mra.satpro.utilerias.Helpers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mra.satpro.utilerias.Global.arrayimagenes;
import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;
import static com.mra.satpro.utilerias.Global.nombreImagenPrimerCarga;


public class SkyInvaderPlay extends AppCompatActivity implements View.OnClickListener {

    public static String tipoTest;
    public static String autoAyuda;
    public static String numPreguntas;

    private static Game mGame;
    private static GameView myView;
    private static GameLooper looper;

    public static LinearLayout espacio, formulario;
    private static ScrollView scrollView4, scrollView2;
    private Context context;

    //Configurar preguntas
    public static int nPreguntas =0;
    public static String nombreMatTit ="";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID_CATEGORIA = "idpreguntasmodulo";
    private static final String TAG_PREGUNTA = "pregunta";
    private static final String TAG_RESPUESTA_UNO = "respuestauno";
    private static final String TAG_RESPUESTA_DOS = "respuestados";
    private static final String TAG_RESPUESTA_TRES = "respuestatres";
    private static final String TAG_RESPUESTA_CUATRO = "respuestacuatro";
    private static final String TAG_IMAGEN_PREGUNTA = "imagenpregunta";
    private static final String TAG_RESPUESTA_CORRECTA = "correcta";
    private static final String TAG_TOOLTIP = "tooltip";
    private static final String TAG_IMAGEN_TOOLTIP = "imagentooltip";

    public static String preguntasBloque[];
    public static String respuestaUnoBloque[];
    public static String respuestaDosBloque[];
    public static String respuestaTresBloque[];
    public static String respuestaCuatroBloque[];
    public static String imagenPreguntaBloque[];
    public static String correctaBloque[];
    public static String tooltipBloque[];
    public static String imagenTooltipBloque[];

    public static EditText preguntaImagen, pregunta;

    public static Bitmap runo;
    public static Bitmap rdos;
    public static Bitmap rtres;
    public static Bitmap rcuatro;

    private boolean yaTermino = false;
    private ImageView img_flecha;

    public static int respCorrecta = 9;

    public static TextView respCollapsable;
    public static RadioGroup radioRespuestas;
    public static RadioButton r1, r2, r3, r4;

    public static String respuestaRadio = "";
    public static AlertDialog alertaAyuda;
    public static EditText txtMensajeAyuda, ayudaImagentooltip;
    public static ImageView imagenTooltip;
    public static TextView respCorrectaToolTip;
    public static int preguntaNumero = 0;

    int conPagTool = 0;
    public static int conCorrectaTool = 0;
    int tooltipImagen=0;
    public static int preguntaNext = 0;

    public static int siguientePregunt = 0;

    public static int correctoIncorrecto = 0;

    public static String materiaEstudio = "";
    public static int preguntasTotales = 0;

    public static Button btnSiguiente;
    public static Activity actividad;

    public static int preguntaSigPlay = 0;
    public SkyInvaderPlay activity;

    //Variables de crossWord

    EditText editText;
    private static List<EditText> editTextList;
    public String palabra = "";
    public static int cuentaLetras = 0;
    public static int tamanopantalla = 0;
    private int fieldHeight, fieldWidth;
    Context contexty= this;
    private static int letraCuenta = 0;

    public static int tipoJuego = 0;
    private static final String accents 	= "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ";
    private static final String expected	= "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,C,c,A,a,O,o";

    String random = "";
    TextView tituloSpace;
    static TextView txtDuraion;
    public static int cool = R.drawable.blackboard;


    private FloatingActionButton btnAyuda, btnNotas, btnCalculadora, btnContinuar;
    public static TextView respUno, respDos, respTres, respCuatro;

    public static int correctaIncorrecta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky_invader_play);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Global.preguntaSigPlayGlobal = 0;
        preguntaNumero = 0;
        Global.aciertos = 0;

        context = this;
        actividad = this;
        vistas();

        tipoTest=getIntent().getExtras().getString("tipoTest");
        autoAyuda=getIntent().getExtras().getString("autoAyuda");
        numPreguntas=getIntent().getExtras().getString("preguntasTotal");
        random=getIntent().getExtras().getString("random");

        materiaEstudio = tipoTest;
        preguntasTotales = Integer.parseInt(numPreguntas);

        nPreguntas = Integer.parseInt(numPreguntas);

        buscaOpciones();
        updateSizeInfo();
        //new ImportarCategoriasCSVTask().execute("");

        //infoTipoPago();
        mostrarInstrucciones();
        temporizadorFromSpace();

        respUno    = (TextView) findViewById(R.id.respUno);
        respDos    = (TextView) findViewById(R.id.respDos);
        respTres    = (TextView) findViewById(R.id.respTres);
        respCuatro    = (TextView) findViewById(R.id.respCuatro);

        btnAyuda    = findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(this);

        btnNotas = findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(this);

        btnCalculadora= findViewById(R.id.btnCalculadora);
        btnCalculadora.setOnClickListener(this);

        btnContinuar= findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(this);

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiatePopupAyudaSinResp();
            }
        });

        btnNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogFirma();
            }
        });

        btnCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculadoraSi = true;
                initiatePopupWindowCalculadoraBotonesCientifica();
            }
        });

        respCollapsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioRespuestas.isShown()){
                    radioRespuestas.setVisibility(View.GONE);
                    respUno.setVisibility(View.GONE);
                    respDos.setVisibility(View.GONE);
                    respTres.setVisibility(View.GONE);
                    respCuatro.setVisibility(View.GONE);
                    respCollapsable.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pluss, 0);
                } else if(!radioRespuestas.isShown()){
                    radioRespuestas.setVisibility(View.VISIBLE);
                    respUno.setVisibility(View.VISIBLE);
                    respDos.setVisibility(View.VISIBLE);
                    respTres.setVisibility(View.VISIBLE);
                    respCuatro.setVisibility(View.VISIBLE);
                    respCollapsable.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.minus, 0);
                }
            }
        });


        img_flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                stop();
                finish();
                Intent linsertar=new Intent(SkyInvaderPlay.this, SeleccionaEscuela.class);
                startActivity(linsertar);
            }
        });

    }

    String areaLicenciatura = "";
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

    private GestureOverlayView gestureFirma;
    private AlertDialog dialog;
    Button btnGestureClean, btnGestureCerrar;
    FloatingActionButton btnClearNotas, btnCloseNotas;

    private void showAlertDialogFirma()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SkyInvaderPlay.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_firma,null);
        gestureFirma = mView.findViewById(R.id.gesturesFirma);
        //btnGestureClean = mView.findViewById(R.id.btnGestureClean);
        //btnGestureCerrar = mView.findViewById(R.id.btnGestureCerrar);
        btnClearNotas = mView.findViewById(R.id.btnClearNotas);
        btnCloseNotas = mView.findViewById(R.id.btnCloseNotas);

        btnClearNotas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                gestureFirma.cancelClearAnimation();
                gestureFirma.clear(true);
            }
        });

        btnCloseNotas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void mostrarInstrucciones() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SkyInvaderPlay.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("tooltip.json");

        builder.setMessage(getResources().getString(R.string.instruccionesInvader))
                .setTitle("Directions");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                new ImportarCategoriasCSVTask().execute("");

            }
        });

        /*builder.setNegativeButton("Salir", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                finish();
            }
        });*/



        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    AlertDialog alertaGastos;

    private void infoTipoPago(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.version_pago, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        TextView textView30 = (TextView) promptView.findViewById(R.id.textView30);
        textView30.setText(getResources().getString(R.string.instruccionesInvader));

        Button buttonCerrar = (Button) promptView.findViewById(R.id.closepago);
        buttonCerrar.setOnClickListener(cerrarVentanaInstrucciones);

        Button comprafull = (Button) promptView.findViewById(R.id.comprafull);
        comprafull.setVisibility(View.GONE);

        Button yalocompre = (Button) promptView.findViewById(R.id.yalocompre);
        yalocompre.setVisibility(View.GONE);

        alertaGastos = alertDialogBuilder.show();
        alertaGastos.setCanceledOnTouchOutside(false);
        alertaGastos.setCancelable(false);

    }

    private View.OnClickListener cerrarVentanaInstrucciones = new View.OnClickListener() {
        public void onClick(View v) {

            alertaGastos.dismiss();
            new ImportarCategoriasCSVTask().execute("");
        }
    };

    private static CountDownTimer mCountDownTimer;

    public static void temporizadorFromSpace(){

        // stop();
        txtDuraion.setText("");
        mCountDownTimer = null;

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                String timerValue =   String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtDuraion.setText(timerValue);
            }

            public void onFinish() {
                txtDuraion.setText("¡Start game!");
                mCountDownTimer.cancel();
                armaJuegoSiguientePregunta();
                oResumeNext();
                //SpaceInvadersGame game = new SpaceInvadersGame(SkyInvaderPlay.this);
                //game.reset();
            }
        }.start();
    }




    /*public void consultarAdview(){

        BaseDaoPassword conexiones=new BaseDaoPassword(this);

        List listas = conexiones.ConsultarPttoDetalle(1);

        if(listas.size()<=0){
            adViewSky = (AdView) findViewById(R.id.adViewSky);

            AdRequest adRequest = new AdRequest.Builder().build();
            adViewSky.loadAd(adRequest);
        }else{
            adViewSky.destroy();
            adViewSky.setVisibility(View.GONE);

        }


    }*/



    public void updateSizeInfo() {

        espacio = (LinearLayout) findViewById(R.id.space);
        DisplayMetrics displayMetrics = espacio.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        tamanopantalla = (int) (dpWidth);
    }

    int cuentaVol = 0;


    public void vistas(){

        tituloSpace = (TextView) findViewById(R.id.tituloSpace);

        espacio = (LinearLayout) findViewById(R.id.space);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 50, 0, 50);

        //  espacio.setLayoutParams(layoutParams);

        //textView10 = (TextView) findViewById(R.id.textView10);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(this);

        preguntaImagen = (EditText) findViewById(R.id.preguntaImagen);
        preguntaImagen.setFocusable(false);

        pregunta = (EditText) findViewById(R.id.pregunta);
        pregunta.setFocusable(false);

        respCollapsable = (TextView) findViewById(R.id.respCollapsable);
        respCollapsable.setOnClickListener(this);

        radioRespuestas = (RadioGroup) findViewById(R.id.radioRespuestas);
        radioRespuestas.setVisibility(View.VISIBLE);

        radioRespuestas = (RadioGroup) findViewById(R.id.radioRespuestas);
        radioRespuestas.clearCheck();

        txtDuraion = (TextView) findViewById(R.id.txtDuraion);

        r1=(RadioButton)findViewById(R.id.r1);
        r2=(RadioButton)findViewById(R.id.r2);
        r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);
    }

    @Override
    public void onClick(View v) {

      /*  if(v.getId()==findViewById(R.id.btnSiguiente).getId()){
            validaRespuesta();
            //siguientePreguntaButton();
        }


        if(v.getId()==findViewById(R.id.img_flecha).getId()){
            stop();
            finish();
            Intent linsertar=new Intent(SkyInvaderPlay.this, SeleccionaEscuela.class);
            startActivity(linsertar);

        }*/

        if(calculadoraSi){
            Button button = (Button) v;
            String data = button.getText().toString();
            //Toast.makeText(this, "Click " + data, Toast.LENGTH_LONG).show();
            if(data.equals("AC")){
                outputResult.setText("");
                currentDisplayedInput = "";
                inputToBeParsed = "";
            }
            else if(data.equals("del")){
                String enteredInput = outputResult.getText().toString();
                if(enteredInput.length() > 0){
                    enteredInput = enteredInput.substring(0, enteredInput.length() - 1);
                    currentDisplayedInput = enteredInput;
                    inputToBeParsed = enteredInput;
                    outputResult.setText(currentDisplayedInput);
                }
            }else if(data.equals("=")){
                String enteredInput = outputResult.getText().toString();
                // call a function that will return the result of the calculate.
                String resultObject = mCalculator.getResult(currentDisplayedInput, inputToBeParsed);
                outputResult.setText(removeTrailingZero(resultObject));
                //reultadoFinal = Double.parseDouble(enteredInput);
                /*if(reultadoFinal!=0){
                    montoFact.setText(reultadoFinal + "");
                } else if(reultadoFinal==0){
                    montoFact.setText(0 + "");
                }*/
            }else if(data.equals("Ans")){
                String enteredInput = outputResult.getText().toString();
                enteredInput += lastResultObtain;
                outputResult.setText(enteredInput);
        /*}else if(data.equals("SHIFT")){
            if(!isInverse){
                isInverse = true;
            }else{
                isInverse = false;
            }
            toggleShiftButton();
        }else if(data.equals("RAD")){
            buttonRad.setText("DEG");
            degreeRad.setText("RAD");
        }
        else if(data.equals("DEG")){
            buttonRad.setText("RAD");
            degreeRad.setText("DEG");*/
            }else{
                obtainInputValues(data);
            }
        }

    }

    class ImportarCategoriasCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(context);

        // can use UI thread here

        protected void onPreExecute()

        {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
            this.dialog.setCancelable(false);
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {


         //   try{

                int pregSolicitadas = Integer.parseInt(numPreguntas);

                nPreguntas = pregSolicitadas;

                preguntasBloque = new String[nPreguntas];
                respuestaUnoBloque = new String[nPreguntas];
                respuestaDosBloque = new String[nPreguntas];
                respuestaTresBloque = new String[nPreguntas];
                respuestaCuatroBloque = new String[nPreguntas];
                imagenPreguntaBloque = new String[nPreguntas];
                correctaBloque = new String[nPreguntas];
                tooltipBloque = new String[nPreguntas];
                imagenTooltipBloque = new String[nPreguntas];


                BaseDaoExamen conexion=new BaseDaoExamen(SkyInvaderPlay.this);

                query = "";

                numPreguntas = nPreguntas+"";

            if(random.equalsIgnoreCase("Si")){
                if(tipoTest.equalsIgnoreCase("Reading")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 14 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Reading";

                }
                if(tipoTest.equalsIgnoreCase("Writing and Language")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 15 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Writing and Language";

                }
                if(tipoTest.equalsIgnoreCase("Math")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 16 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Math";

                }
                if(tipoTest.equalsIgnoreCase("Biology")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 17 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Biology";

                }

                if(tipoTest.equalsIgnoreCase("Chemistry")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 18 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Chemistry";

                }

                if(tipoTest.equalsIgnoreCase("Physics")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 19 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Physics";

                }
                if(tipoTest.equalsIgnoreCase("Spanish")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 20 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Spanish";

                }
                if(tipoTest.equalsIgnoreCase("quimi")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 11 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Química";

                }
                if(tipoTest.equalsIgnoreCase("biolo")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 4 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Biología";

                }
                if(tipoTest.equalsIgnoreCase("filosofia")){
                    query = "select * from tblpreguntasmodulo where idmodulo = 2 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Filosofía";
                }

                if(tipoTest.equalsIgnoreCase("geouniv")){
                    query = "select * from tblpreguntasmodulo where idmodulo = 5 ORDER BY RANDOM() LIMIT " + numPreguntas;
                    nombreMatTit = "Geografía";
                }
            }else{
                if(tipoTest.equalsIgnoreCase("Reading")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 14 LIMIT " + numPreguntas;
                    nombreMatTit = "Reading";

                }
                if(tipoTest.equalsIgnoreCase("Writing and Language")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 15 LIMIT " + numPreguntas;
                    nombreMatTit = "Literatura";

                }
                if(tipoTest.equalsIgnoreCase("Math")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 16 LIMIT " + numPreguntas;
                    nombreMatTit = "Historia Universal";

                }
                if(tipoTest.equalsIgnoreCase("Biology")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 17 LIMIT " + numPreguntas;
                    nombreMatTit = "Historia de México";

                }
  /*      if(tipoTest.equalsIgnoreCase("geomex")){
            periodos[4] = String.valueOf(preguntasBloqueGeoMex.length);
        }*/
                if(tipoTest.equalsIgnoreCase("Chemistry")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 18 LIMIT " + numPreguntas;
                    nombreMatTit = "Filosofía";

                }

                if(tipoTest.equalsIgnoreCase("Physics")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 19 LIMIT " + numPreguntas;
                    nombreMatTit = "Matemáticas";

                }
                if(tipoTest.equalsIgnoreCase("Spanish")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 20 LIMIT " + numPreguntas;
                    nombreMatTit = "Física";

                }
                if(tipoTest.equalsIgnoreCase("quimi")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 11 LIMIT " + numPreguntas;
                    nombreMatTit = "Química";

                }
                if(tipoTest.equalsIgnoreCase("biolo")){

                    query = "select * from tblpreguntasmodulo where idmodulo = 4 LIMIT " + numPreguntas;
                    nombreMatTit = "Biología";

                }
                if(tipoTest.equalsIgnoreCase("filosofia")){
                    query = "select * from tblpreguntasmodulo where idmodulo = 2 LIMIT " + numPreguntas;
                    nombreMatTit = "Filosofía";
                }

                if(tipoTest.equalsIgnoreCase("geouniv")){
                    query = "select * from tblpreguntasmodulo where idmodulo = 5 LIMIT " + numPreguntas;
                    nombreMatTit = "Geografía";
                }
            }



                List lista = conexion.ConsultarExamenesRandomTodas(query);
                int contador = 0;
                Log.e("listaExamen", lista.size()+"");


                for(Object datos: lista) {
                    ModulosDTO elementos=(ModulosDTO) datos;

                    // Storing each json item in variable
                    String idModulo = String.valueOf(elementos.getIdmodulo());
                    String pregunta = elementos.getPregunta();
                    String respuestaUno = elementos.getRespuestauno();
                    String respuestaDos = elementos.getRespuestados();
                    String respuestaTres = elementos.getRespuestatres();
                    String respuestaCuatro = elementos.getRespuestacuatro();
                    String imagenPregunta = elementos.getImagenpregunta();
                    String correcta = elementos.getCorrecta();
                    String tooltip = elementos.getTooltip();
                    String imagenTooltip = elementos.getImagentooltip();

                    Log.e("imagenPregunta", imagenPregunta);

                    preguntasBloque[contador] = pregunta.trim();
                    respuestaUnoBloque[contador] = respuestaUno;
                    respuestaDosBloque[contador] = respuestaDos;
                    respuestaTresBloque[contador] = respuestaTres;
                    respuestaCuatroBloque[contador] = respuestaCuatro;
                    imagenPreguntaBloque[contador] = imagenPregunta;
                    correctaBloque[contador] = correcta;
                    tooltipBloque[contador] = tooltip;
                    imagenTooltipBloque[contador] = imagenTooltip;

                    contador = contador +1;
                }



            return true;

        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if(success==true){
                evaluaRespuesta(preguntaNumero);
                verImagen();
                tituloSpace.setText(nombreMatTit);
            }else{
                mesnaje("Oops, algo va mal, por favor, intentalo de nuevo.");
                finish();
                Intent linsertar=new Intent(SkyInvaderPlay.this, SeleccionaEscuela.class);
                startActivity(linsertar);
            }



        }
    }

    private void mesnaje(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }





    public void armaJuego(){
        espacio = (LinearLayout) findViewById(R.id.space);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 50, 0, 50);

        //  espacio.setLayoutParams(layoutParams);

        //Create a helper static class responsible for mapping of bitmap resources to IDs
        Bitmaps.createInstance(this);

        // Make an object of current game.
        // If you make your own game, just make sure it is all encapsulated inside a separate class
        // that inherits from abstract Game class
        mGame = new SpaceInvadersGame(this);

        //reset all states
        mGame.reset();

        // Making a looper. The thread that is going to fire update(deltaTime) events to the game
        // Actually, for a simple 2D game it is not really necessary
        // as it can be combined with the renderer and the world can be recalculated on every frame
        looper = new GameLooper(mGame);

        //Creating a custom view: this is basically a canvas + thread-renderer
        myView = GameView.createView(mGame, getApplicationContext());

        espacio.addView(myView);
        //setContentView(myView);

        //Make it touchable
        myView.setEnabled(true);
        //Redirect touches to the game object, let the game to take care of it
        myView.setOnTouchListener(mGame);
    }

    public static Game mGameNext;
    public static GameView myViewNext;
    public static GameLooper looperNext;

    public static void armaJuegoSiguientePregunta(){

        espacio = (LinearLayout) actividad.findViewById(R.id.space);
        // espacio.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 50, 0, 50);

        //  espacio.setLayoutParams(layoutParams);

        //Create a helper static class responsible for mapping of bitmap resources to IDs
        Bitmaps.createInstance(actividad);

        // Make an object of current game.
        // If you make your own game, just make sure it is all encapsulated inside a separate class
        // that inherits from abstract Game class
        mGameNext = new SpaceInvadersGame(actividad);

        //reset all states
        mGameNext.reset();

        // Making a looper. The thread that is going to fire update(deltaTime) events to the game
        // Actually, for a simple 2D game it is not really necessary
        // as it can be combined with the renderer and the world can be recalculated on every frame
        looperNext = new GameLooper(mGameNext);

        //Creating a custom view: this is basically a canvas + thread-renderer
        myViewNext = GameView.createView(mGameNext, actividad.getApplicationContext());

        espacio.addView(myViewNext);
        //setContentView(myView);

        //Make it touchable
        myViewNext.setEnabled(true);
        //Redirect touches to the game object, let the game to take care of it
        myViewNext.setOnTouchListener(mGameNext);
    }


    String query = "";

    public static void evaluaRespuesta(int contador){

        if(correctaBloque[contador] != null){
            if(correctaBloque[contador].trim().equalsIgnoreCase(respuestaUnoBloque[contador].trim())){
                respCorrecta = 0;
            } else if(correctaBloque[contador].trim().equalsIgnoreCase(respuestaDosBloque[contador].trim())){
                respCorrecta = 1;
            } else if(correctaBloque[contador].trim().equalsIgnoreCase(respuestaTresBloque[contador].trim())){
                respCorrecta = 2;
            } else if(correctaBloque[contador].trim().equalsIgnoreCase(respuestaCuatroBloque[contador].trim())){
                respCorrecta = 3;
            }
        }

    }


    public void verImagen() {

        context = this;
        boolean siEsImagen = false;

        if(imagenPreguntaBloque[0] != null){
            if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                siEsImagen = true;
            }
        }

        respUno.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respDos.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respTres.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);


        if(siEsImagen==true){

            String mDrawableName = "";
            int resID = 0;

            for(int i=0; i<arrayimagenes.length; i ++){
                if(imagenPreguntaBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());


                }
            }

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

            pregunta.setBackgroundDrawable(getResources().getDrawable(resID));

            preguntaImagen.setText(preguntasBloque[0]);
            preguntaImagen.setVisibility(View.VISIBLE);
            pregunta.setText("");



        }else{

            pregunta.setText("\n" + preguntasBloque[0] + "\n ");


            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

        }

        if(correctaBloque[0].contains(".png") || correctaBloque[0].contains(".PNG")){

            r1.setText("1) ");
            r2.setText("2) ");
            r3.setText("3) ");
            r4.setText("4) ");

            respUno.setText("1) ");
            respDos.setText("2) ");
            respTres.setText("3) ");
            respCuatro.setText("4) ");


            String mDrawableName = "";
            int resID = 0;

            for(int i=0; i<arrayimagenes.length; i ++){
                if(respuestaUnoBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());

                }
            }

            Matrix matrix = new Matrix();
            matrix.postRotate(90);



            runo = BitmapFactory.decodeResource(context.getResources(),
                    resID);

            //   Bitmap rotatedBitmap = Bitmap.createBitmap(runo, 0, 0, runo.getWidth(), runo.getHeight(), matrix, true);
            //   runo = rotatedBitmap;



            r1.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);
            respUno.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);


            for(int i=0; i<arrayimagenes.length; i ++){
                if(respuestaDosBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }


            rdos = BitmapFactory.decodeResource(context.getResources(),
                    resID);
            r2.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);
            respDos.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);



            for(int i=0; i<arrayimagenes.length; i ++){
                if(respuestaTresBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }

            rtres = BitmapFactory.decodeResource(context.getResources(),
                    resID);
            r3.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);
            respTres.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);


            for(int i=0; i<arrayimagenes.length; i ++){
                if(respuestaCuatroBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }

            rcuatro = BitmapFactory.decodeResource(context.getResources(),
                    resID);
            r4.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);
            respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(resID),null);



        }else{

            r1.setText("1) " + (respuestaUnoBloque[0]));
            r2.setText("2) " + (respuestaDosBloque[0]));
            r3.setText("3) " + (respuestaTresBloque[0]));
            r4.setText("4) " + (respuestaCuatroBloque[0]));


            respUno.setText("1) " + (respuestaUnoBloque[0]));
            respDos.setText("2) " + (respuestaDosBloque[0]));
            respTres.setText("3) " + (respuestaTresBloque[0]));
            respCuatro.setText("4) " + (respuestaCuatroBloque[0]));

        }

        /*    armaJuego();
            oResume();*/

    }




    public static void initiatePopupAyuda(Context cont, int siguiente) {
        try {

            //cargaPreguntas();

            //     ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) cont
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //  LayoutInflater inflater = this.getLayoutInflater();

            View layout = inflater.inflate(R.layout.tooltip,
                    null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cont);

            alertDialogBuilder.setView(layout);

            Button btnaceptarAyuda = (Button) layout.findViewById(R.id.btnCerrarTooltip);
            btnaceptarAyuda.setOnClickListener(cerrar_ayuda);

            LottieAnimationView gameOverAnimation = layout.findViewById(R.id.animation_view);
            gameOverAnimation.setAnimation("resp_incorrecta_dos.json");

            txtMensajeAyuda = (EditText) layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = (EditText) layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = (TextView) layout.findViewById(R.id.respCorrecta);
            TextView textView14 =  (TextView) layout.findViewById(R.id.textView14);
            txtMensajeAyuda.setFocusable(false);
            ayudaImagentooltip.setFocusable(false);

      /*      if(correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio)){
                textView14.setText("¡Respuesta correcta!");
                respCorrectaToolTip.setText("");
                respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.an_ic_check_on, 0, 0, 0);
                String varLabel = "aciertos";
                if(aciertos==1){
                    varLabel = "acierto";
                }
                txtMensajeAyuda.setText("¡Continua así! \n Has logrado: \n\n" + aciertos + " " + varLabel + " \n");

            }*/

            if(correctaIncorrecta==0){
                textView14.setText("Failed!");
                respCorrectaToolTip.setText("");
                txtMensajeAyuda.setText("Review your knowledge! \n\n\n");
            } else if(correctaIncorrecta==1){
                textView14.setText("Good!");
                respCorrectaToolTip.setText("");
                txtMensajeAyuda.setText("\n\n" +
                        "Good job! " +
                        "\n\n");
            }

            autoAyuda= "Si";

            Log.e("autoAyuda_____", autoAyuda);


            //   }
            //if(!correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio) && autoAyuda.equalsIgnoreCase("Si")){
            if(autoAyuda.equalsIgnoreCase("Si") && correctaIncorrecta==0){
                boolean siEsImagen = false;
                conCorrectaTool =0;
                // conPagTool = 0;

                if(imagenTooltipBloque[preguntaNumero] != null){
                    if (!imagenTooltipBloque[preguntaNumero].equalsIgnoreCase("")){
                        siEsImagen = true;
                        //    tooltipImagen = 1;
                    }
                }


                if(siEsImagen==true){
                    // Loader image - will be shown before loading image
                    //txtMensajeAyuda.setText("");
                    ayudaImagentooltip.setVisibility(View.VISIBLE);
                    txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);

                    //if(correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("png") || correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("PNG")) {

                    if(imagenTooltipBloque[preguntaNumero].contains(".png") || imagenTooltipBloque[preguntaNumero].contains(".PNG")){
                        // Image url
              /*          String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenTooltipBloque[preguntaNumero];

                        conPagTool = 1;


                        // Create an object for subclass of AsyncTask
                        ImagenTooltip task = new ImagenTooltip();
                        // Execute the task
                        task.execute(new String[] { image_url });*/

                        String mDrawableName = "";
                        int resID = 0;

                        for(int i=0; i<arrayimagenes.length; i ++){
                            if(imagenTooltipBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                                mDrawableName = nombreImagenPrimerCarga[i]+"";
                                resID = cont.getResources().getIdentifier(mDrawableName , "drawable", cont.getPackageName());
                            }
                        }

                        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                        ayudaImagentooltip.setLayoutParams(textParam);

                        ayudaImagentooltip.setBackgroundDrawable(cont.getResources().getDrawable(resID));

                    }


                }else{

                    LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    txtMensajeAyuda.setLayoutParams(textParam);

                    txtMensajeAyuda.setText("\nExplain \n\n" + tooltipBloque[preguntaNumero]);
                }

                Log.e("correctaBloque[0]", correctaBloque[preguntaNumero]);
                if(correctaBloque[preguntaNumero].contains(".png") || correctaBloque[preguntaNumero].contains(".PNG")){
                    // Image url
//                String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenTooltipBloque[preguntaNumero];
                    //            String image_url2 = "http://www.pypsolucionesintegrales.com/Imagenes/" + correctaBloque[preguntaNumero];

                    conCorrectaTool = 1;

                    // Create an object for subclass of AsyncTask
                    //            ImagenCorrecta task = new ImagenCorrecta();
                    // Execute the task
                    //            task.execute(new String[] { image_url2 });

                    String mDrawableName = "";
                    int resID = 0;

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(correctaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = cont.getResources().getIdentifier(mDrawableName , "drawable", cont.getPackageName());
                        }
                    }


                    respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, cont.getResources().getDrawable(resID), null);
                    respCorrectaToolTip.setText("Correct answer: ");



                }else{
                    respCorrectaToolTip.setText("Correct answer: " + correctaBloque[preguntaNumero]);
                }


            }

            alertaAyuda = alertDialogBuilder.show();
            alertaAyuda.setCanceledOnTouchOutside(false);
            alertaAyuda.setCancelable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static View.OnClickListener cerrar_ayuda = new View.OnClickListener() {
        public void onClick(View v) {

            alertaAyuda.dismiss();
            alertaAyuda.dismiss();

            String materiaEstudio = SkyInvaderPlay.materiaEstudio;
            int preguntaSiguiente = Global.preguntaSigPlayGlobal;
            int totalPreguntas = SkyInvaderPlay.preguntasTotales;

            /*if(haveNetworkConnection(actividad.getApplicationContext())==true){
                if(preguntaSiguiente==4 || preguntaSiguiente==7
                ){
                    respUno.setText("");
                    respUno.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respDos.setText("");
                    respDos.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respTres.setText("");
                    respTres.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respCuatro.setText("");
                    respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    muestraVideo();
                }else{

                    respUno.setText("");
                    respUno.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respDos.setText("");
                    respDos.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respTres.setText("");
                    respTres.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respCuatro.setText("");
                    respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);
                }
            }else{
                Message msg = new Message();
                msg.what = MSG_SHOW_TOAST;
                msg.obj = "Para continuar requieres conexión a internet, por favor activa tu conexión y continua.";
                messageHandler.sendMessage(msg);
            }*/

            siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);

            if(preguntaNext == nPreguntas) {
                //resultados();
            }
        }
    };

    /////////////////RESP CORRECTA

    public static void initiatePopupCorrecta(Context cont, int siguiente) {
        try {

            //cargaPreguntas();

            //     ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) cont
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //  LayoutInflater inflater = this.getLayoutInflater();

            View layout = inflater.inflate(R.layout.tooltip,
                    null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cont);

            alertDialogBuilder.setView(layout);

            Button btnaceptarAyuda = (Button) layout.findViewById(R.id.btnCerrarTooltip);
            btnaceptarAyuda.setOnClickListener(cerrar_correcta);

            LottieAnimationView gameOverAnimation = layout.findViewById(R.id.animation_view);
            gameOverAnimation.setAnimation("resp_correcta_dos.json");

            imagenTooltip = (ImageView) layout.findViewById(R.id.imageView6);
            txtMensajeAyuda = (EditText) layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = (EditText) layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = (TextView) layout.findViewById(R.id.respCorrecta);
            TextView textView14 =  (TextView) layout.findViewById(R.id.textView14);
            txtMensajeAyuda.setFocusable(false);
            ayudaImagentooltip.setFocusable(false);

            imagenTooltip.setImageResource(R.mipmap.resp_correcta_changuito);
            textView14.setText("Good!");
            respCorrectaToolTip.setText("");
            txtMensajeAyuda.setText("\n\n" +
                    "Good job! " +
                    "\n\n");



            alertaAyuda = alertDialogBuilder.show();
            alertaAyuda.setCanceledOnTouchOutside(false);
            alertaAyuda.setCancelable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static View.OnClickListener cerrar_correcta = new View.OnClickListener() {
        public void onClick(View v) {

            alertaAyuda.dismiss();
            alertaAyuda.dismiss();

            String materiaEstudio = SkyInvaderPlay.materiaEstudio;
            int preguntaSiguiente = Global.preguntaSigPlayGlobal;
            int totalPreguntas = SkyInvaderPlay.preguntasTotales;

            /*if(haveNetworkConnection(actividad.getApplicationContext())==true){
                if(preguntaSiguiente==4 || preguntaSiguiente==7
                ){
                    respUno.setText("");
                    respUno.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respDos.setText("");
                    respDos.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respTres.setText("");
                    respTres.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respCuatro.setText("");
                    respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    muestraVideo();
                }else{

                    respUno.setText("");
                    respUno.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respDos.setText("");
                    respDos.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respTres.setText("");
                    respTres.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    respCuatro.setText("");
                    respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

                    siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);
                }

            }else{
                Message msg = new Message();
                msg.what = MSG_SHOW_TOAST;
                msg.obj = "Para continuar requieres conexión a internet, por favor activa tu conexión y continua.";
                messageHandler.sendMessage(msg);
            }*/

            siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);

            if(preguntaNext == nPreguntas) {
                //resultados();
            }
        }
    };



    //Siguiente pregunta button next
    public static String palomitas = "";
    public static String totalPreg = "";


    public static void resultados(){

        //stop();
        palomitas = Global.aciertos+"";
        totalPreg = preguntasBloque.length+"";

        //stop();
        mCountDownTimer.cancel();
        SkyInvaderPlay.actividad.finish();
        Intent linsertar=new Intent(actividad, Resultados.class);
        linsertar.putExtra("aciertos", palomitas);
        linsertar.putExtra("preguntas", totalPreg);
        linsertar.putExtra("modulo", nombreMatTit);
        actividad.startActivity(linsertar);

    }

    // Backwards compatible recreate().
    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.onCreate(null);
            setContentView(R.layout.activity_sky_invader_play);
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public static void siguientePreguntaButton(int siguiente, String tipoMateria, int totPreg){

        pregunta.setText("");
        pregunta.setCompoundDrawables(null, null, null, null);
        preguntaImagen.setText("");
        preguntaImagen.setCompoundDrawables(null, null, null, null);
        preguntaImagen.setVisibility(View.INVISIBLE);

        respUno.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respDos.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respTres.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

        Global.preguntaSigPlayGlobal = siguiente + 1;

        preguntaNext = Global.preguntaSigPlayGlobal;

        if(preguntaNext == totPreg) {
            resultados();
        }


        if(preguntaNumero < (correctaBloque.length - 1)) {
            //preguntaNumero = preguntaNumero + 1;
            preguntaNumero = preguntaNext;

            evaluaRespuesta(preguntaNext);

            ///radios y preguntas con imagen
            boolean siEsImagen = false;


            if(imagenPreguntaBloque[preguntaNumero] != null){
                if (!imagenPreguntaBloque[preguntaNumero].equalsIgnoreCase("")){
                    siEsImagen = true;
                }
            }


            if(siEsImagen==true){

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(imagenPreguntaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i].trim())){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                pregunta.setLayoutParams(textParam);

                pregunta.setBackgroundDrawable(actividad.getResources().getDrawable(resID));

                preguntaImagen.setText(preguntasBloque[preguntaNumero]);
                preguntaImagen.setVisibility(View.VISIBLE);
                pregunta.setText("");
                pregunta.setEnabled(false);

            }else{


                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                pregunta.setLayoutParams(textParam);

                pregunta.setText("\n " + preguntasBloque[preguntaNumero] + "\n ");
                pregunta.setCompoundDrawables(null, null, null, null);
                pregunta.setBackgroundDrawable(actividad.getResources().getDrawable(R.mipmap.pizzarra_ahorcado));
                //pregunta.setBackgroundColor(actividad.getResources().getColor(R.color.blackboard));
                preguntaImagen.setText("");
                pregunta.setEnabled(false);
            }

            if(correctaBloque[preguntaNumero].contains(".png") || correctaBloque[preguntaNumero].contains(".PNG")){

                r1.setText("1) ");
                r2.setText("2) ");
                r3.setText("3) ");
                r4.setText("4) ");


                respUno.setText("1) ");
                respDos.setText("2) ");
                respTres.setText("3) ");
                respCuatro.setText("4) ");

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaUnoBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }

                r1.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);
                respUno.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaDosBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }


                r2.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);
                respDos.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);



                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaTresBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }

                r3.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);
                respTres.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);


                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaCuatroBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }

                r4.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);
                respCuatro.setCompoundDrawablesWithIntrinsicBounds(null,null, actividad.getResources().getDrawable(resID),null);


            }else{

                //  SkyInvaderPlay.crucigrama();

                r1.setText("1) " + respuestaUnoBloque[preguntaNumero]);
                r2.setText("2) " + respuestaDosBloque[preguntaNumero]);
                r3.setText("3) " + respuestaTresBloque[preguntaNumero]);
                r4.setText("4) " + respuestaCuatroBloque[preguntaNumero]);

                r1.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r2.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r3.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r4.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

                respUno.setText("1) " + respuestaUnoBloque[preguntaNumero]);
                respDos.setText("2) " + respuestaDosBloque[preguntaNumero]);
                respTres.setText("3) " + respuestaTresBloque[preguntaNumero]);
                respCuatro.setText("4) " + respuestaCuatroBloque[preguntaNumero]);
            }



            temporizadorFromSpace();

            radioRespuestas.clearCheck();

        }

        respuestaRadio = "";

        materiaEstudio = tipoMateria;

        preguntasTotales = totPreg;
    }

    public void oResume()
    {
        //super.onResume();
        myView.doStart();
        looper.startLoop();

    }

    public static void oResumeNext()
    {
        // super.onResume();
        myViewNext.doStart();
        looperNext.startLoop();

    }

    public static void stop(){

        if(myView != null){
            myView.doStop();
            looper.stopLoop();
        }

        if(myViewNext != null){
            myViewNext.doStop();
            looperNext.stopLoop();
        }
    }


    public void pause()
    {
        if(myView != null){
            myView.doPause();
            looper.pauseLoop();
        }

        if(myViewNext != null){
            myViewNext.doPause();
            looperNext.pauseLoop();
        }

    }


    public void onDestroy()
    {
        // if(tipoJuego==0) {
        super.onDestroy();
        if(myView != null){
            myView.doStop();
            looper.stopLoop();
            mGame.release();
        }

        if(myViewNext != null){
            myViewNext.doStop();
            looperNext.stopLoop();
        }
        // }
    }

    @Override
    public void onStop() {
        super.onStop();

        stop();
        finish();

        if(SkyInvaderPlay.actividad.isFinishing()){
            /*Intent lin=new Intent(actividad, Resultados.class);
            lin.putExtra("aciertos", palomitas);
            lin.putExtra("preguntas", totalPreg);
            lin.putExtra("modulo", nombreMatTit);
            actividad.startActivity(lin);*/
        }else{
            Intent linsertar=new Intent(SkyInvaderPlay.this, SeleccionaEscuela.class);
            startActivity(linsertar);
        }

        //onpause();

    }

    @Override
    public void onStart() {
        super.onStart();
        //new ImportarCategoriasCSVTask().execute("");
    }

    ///inicio calc botones
    private TextView outputResult;
    private TextView shiftDisplay;
    private TextView degreeRad;
    private boolean isDegree = false;
    private boolean isInverse = false;
    private String lastResultObtain = "";
    private String currentDisplayedInput = "";
    private String inputToBeParsed = "";
    private Calculator mCalculator;
    private static String PREFS_NAME = "memory";
    private Button button0, button1, button2,button3,button4,button5,button6,button7,
            button8,button9,buttonClear, buttonDivide,buttonMultiply,buttonSubtract,
            buttonAdd, buttonPercentage, buttonEqual, buttonDecimal, closeParenthesis, openParenthesis, buttonAnswer,
            buttonSingleDelete, buttonExp, close;
    private TextView labelFactorial, labelCombination, labelPermutation, labelPi, labelE, labelComma, labelCubeRoot, labelCube,
            labelInverseX, labelInverseSin, labelInverseCos, labelInverseTan, labelExponential, labelTenPowerX, labelRCL,
            labelSTO, labelMMinus, labelFloat, labelDeg;
    private Button buttonSin, buttonLn,buttonCos, buttonLog, buttonTan, buttonSquareRoot,  buttonXSquare, buttonYPowerX,
            buttonRnd;
    private Button buttonShift, buttonRad, buttonAbs, buttonMr, buttonMs, buttonMPlus;

    /*Segunda calculadora***********************************************************************************************/
    private double reultadoFinal = 0;
    AlertDialog alertaCalc;
    private boolean calculadoraSi = false;

    private void initiatePopupWindowCalculadoraBotonesCientifica() {
        try {

            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(R.layout.calculator_scientific, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptView);

            mCalculator = new Calculator();
            outputResult = (TextView)promptView.findViewById(R.id.display);
            outputResult.setText("0");
            //shiftDisplay = (TextView)promptView.findViewById(R.id.shift_display);
            //degreeRad = (TextView)promptView.findViewById(R.id.degree);
            button0 = (Button)promptView.findViewById(R.id.zero_button);
            button1 = (Button)promptView.findViewById(R.id.one_button);
            button2 = (Button)promptView.findViewById(R.id.two_button);
            button3 = (Button)promptView.findViewById(R.id.three_button);
            button4 = (Button)promptView.findViewById(R.id.four_button);
            button5 = (Button)promptView.findViewById(R.id.five_button);
            button6 = (Button)promptView.findViewById(R.id.six_button);
            button7 = (Button)promptView.findViewById(R.id.seven_button);
            button8 = (Button)promptView.findViewById(R.id.eight_button);
            button9 = (Button)promptView.findViewById(R.id.nine_button);
            buttonDivide = (Button)promptView.findViewById(R.id.division);
            buttonMultiply = (Button)promptView.findViewById(R.id.multiplication);
            buttonSubtract = (Button)promptView.findViewById(R.id.subtraction);
            buttonAdd = (Button)promptView.findViewById(R.id.addition);
            buttonPercentage = (Button)promptView.findViewById(R.id.percent);
            buttonDecimal = (Button)promptView.findViewById(R.id.dot);
            closeParenthesis = (Button)promptView.findViewById(R.id.close_bracket);
            openParenthesis = (Button)promptView.findViewById(R.id.open_bracket);
            buttonExp = (Button)promptView.findViewById(R.id.exp);
            buttonSquareRoot = (Button)promptView.findViewById(R.id.square_root);
            buttonXSquare = (Button)promptView.findViewById(R.id.x_square);
            buttonYPowerX = (Button)promptView.findViewById(R.id.x_power_y);
            close = (Button)promptView.findViewById(R.id.close);
            //buttonSin = (Button)promptView.findViewById(R.id.sin_sign);
            //buttonCos = (Button)promptView.findViewById(R.id.cos_sign);
            //buttonTan = (Button)promptView.findViewById(R.id.tan_sign);
            //buttonLn = (Button)promptView.findViewById(R.id.natural_log);
            //buttonLog = (Button)promptView.findViewById(R.id.log);
            //buttonRnd = (Button)promptView.findViewById(R.id.hys);
            buttonDivide.setText(Html.fromHtml(Helpers.division));
            buttonSquareRoot.setText(Html.fromHtml(Helpers.squareRoot));
            buttonXSquare.setText(Html.fromHtml(Helpers.xSquare));
            buttonYPowerX.setText(Html.fromHtml(Helpers.yPowerX));
            //buttonShift = (Button)promptView.findViewById(R.id.shift);
            //buttonRad = (Button)promptView.findViewById(R.id.rad);
            //buttonAbs = (Button)promptView.findViewById(R.id.abs);
            //buttonMr = (Button)promptView.findViewById(R.id.mr);
            //buttonMs = (Button)promptView.findViewById(R.id.ms);
            //buttonMPlus = (Button)promptView.findViewById(R.id.m_plus);
            buttonClear = (Button)promptView.findViewById(R.id.clear);
            buttonSingleDelete = (Button)promptView.findViewById(R.id.single_delete);
            buttonEqual = (Button)promptView.findViewById(R.id.equal_sign);
            buttonAnswer = (Button)promptView.findViewById(R.id.ans);
            labelFactorial = (TextView)promptView.findViewById(R.id.factorial);
            labelCombination = (TextView)promptView.findViewById(R.id.combination);
            labelPermutation = (TextView)promptView.findViewById(R.id.permutation);
            labelPi = (TextView)promptView.findViewById(R.id.pi);
            labelE = (TextView)promptView.findViewById(R.id.e);
            labelComma = (TextView)promptView.findViewById(R.id.comma);
            //labelCubeRoot = (TextView)promptView.findViewById(R.id.cube_root);
            //labelCube = (TextView)promptView.findViewById(R.id.cube);
            //labelInverseX = (TextView)promptView.findViewById(R.id.one_over_x);
            //labelInverseSin = (TextView)promptView.findViewById(R.id.inverse_sin);
            //labelInverseCos = (TextView)promptView.findViewById(R.id.inverse_cos);
            //labelInverseTan = (TextView)promptView.findViewById(R.id.inverse_tan);
            //labelExponential = (TextView)promptView.findViewById(R.id.expo);
            //labelTenPowerX = (TextView)promptView.findViewById(R.id.ten_power_x);
            //labelRCL = (TextView)promptView.findViewById(R.id.rcl);
            //labelSTO = (TextView)promptView.findViewById(R.id.sto);
            //labelMMinus = (TextView)promptView.findViewById(R.id.m_minus);
            //labelFloat = (TextView)promptView.findViewById(R.id.float_number);
            //labelDeg = (TextView)promptView.findViewById(R.id.degree);
            //labelInverseSin.setText(Html.fromHtml(Helpers.inverseSin));
            //labelInverseCos.setText(Html.fromHtml(Helpers.inverseCos));
            //labelInverseTan.setText(Html.fromHtml(Helpers.inverseTan));
            //labelExponential.setText(Html.fromHtml(Helpers.exponential));
            //labelTenPowerX.setText(Html.fromHtml(Helpers.tenPowerX));
            //labelCubeRoot.setText(Html.fromHtml(Helpers.cubeSquare));
            //labelCube.setText(Html.fromHtml(Helpers.cubeRoot));
            labelPi.setText(Html.fromHtml(Helpers.pi));
            button0.setOnClickListener(this);
            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);
            button5.setOnClickListener(this);
            button6.setOnClickListener(this);
            button7.setOnClickListener(this);
            button8.setOnClickListener(this);
            button9.setOnClickListener(this);
            //buttonClear.setOnClickListener(this);
            buttonDivide.setOnClickListener(this);
            buttonMultiply.setOnClickListener(this);
            buttonSubtract.setOnClickListener(this);
            buttonAdd.setOnClickListener(this);
            buttonPercentage.setOnClickListener(this);
            //buttonEqual.setOnClickListener(this);
            buttonDecimal.setOnClickListener(this);
            closeParenthesis.setOnClickListener(this);
            openParenthesis.setOnClickListener(this);
            //buttonSingleDelete.setOnClickListener(this);
            buttonExp.setOnClickListener(this);
            buttonSquareRoot.setOnClickListener(this);
            buttonXSquare.setOnClickListener(this);
            buttonYPowerX.setOnClickListener(this);
            //buttonSin.setOnClickListener(this);
            //buttonCos.setOnClickListener(this);
            //buttonTan.setOnClickListener(this);
            //buttonLn.setOnClickListener(this);
            //buttonLog.setOnClickListener(this);
            //buttonRnd.setOnClickListener(this);
            //buttonShift.setOnClickListener(this);
            //buttonRad.setOnClickListener(this);
            //buttonAbs.setOnClickListener(this);
            //buttonMr.setOnClickListener(this);
            //buttonMs.setOnClickListener(this);
            //buttonMPlus.setOnClickListener(this);

            buttonClear.setOnClickListener(clear);
            buttonSingleDelete.setOnClickListener(delete);
            buttonEqual.setOnClickListener(equal);
            buttonAnswer.setOnClickListener(answer);
            close.setOnClickListener(closer);
            //buttonShift.setOnClickListener(shift);
            //buttonRad.setOnClickListener(rand);


            alertaCalc = alertDialogBuilder.show();
            alertaCalc.setCanceledOnTouchOutside(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener clear = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            outputResult.setText("");
            currentDisplayedInput = "";
            inputToBeParsed = "";
        }
    };

    private View.OnClickListener delete = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            String enteredInput = outputResult.getText().toString();
            if(enteredInput.length() > 0){
                enteredInput = enteredInput.substring(0, enteredInput.length() - 1);
                currentDisplayedInput = enteredInput;
                inputToBeParsed = enteredInput;
                outputResult.setText(currentDisplayedInput);
            }
        }
    };

    private View.OnClickListener equal = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            String enteredInput = outputResult.getText().toString();
            // call a function that will return the result of the calculate.
            String resultObject = mCalculator.getResult(currentDisplayedInput, inputToBeParsed);
            outputResult.setText(removeTrailingZero(resultObject));

            //reultadoFinal = Double.parseDouble(resultObject);
            //Log.e("reultadoFinal", reultadoFinal+"");
            //montoFact.setText(reultadoFinal + "");
            //alertaCalc.dismiss();
        }
    };

    private View.OnClickListener answer = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            String enteredInput = outputResult.getText().toString();
            enteredInput += lastResultObtain;
            outputResult.setText(enteredInput);
        }
    };

    private View.OnClickListener shift = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            if(!isInverse){
                isInverse = true;
            }else{
                isInverse = false;
            }
            //toggleShiftButton();
        }
    };

    private View.OnClickListener rand = new View.OnClickListener() {
        public void onClick(View v) {
            //alertaCalc.dismiss();
            buttonRad.setText("DEG");
            degreeRad.setText("RAD");
        }
    };


    private void obtainInputValues(String input){
        switch (input){
            case "0":
                currentDisplayedInput += "0";
                inputToBeParsed += "0";
                break;
            case "1":
                if(isInverse){
                    currentDisplayedInput += "π";
                    inputToBeParsed += "pi";
                }else{
                    currentDisplayedInput += "1";
                    inputToBeParsed += "1";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "2":
                if(isInverse){
                    currentDisplayedInput += "e";
                    inputToBeParsed += "e";
                }else{
                    currentDisplayedInput += "2";
                    inputToBeParsed += "2";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "3":
                if(isInverse){
                    currentDisplayedInput += ",";
                    inputToBeParsed += ",";
                }else{
                    currentDisplayedInput += "3";
                    inputToBeParsed += "3";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "4":
                if(isInverse){
                    currentDisplayedInput += "!(";
                    inputToBeParsed += "!(";
                }else{
                    currentDisplayedInput += "4";
                    inputToBeParsed += "4";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "5":
                if(isInverse){
                    currentDisplayedInput += "comb(";
                    inputToBeParsed += "comb(";
                }else{
                    currentDisplayedInput += "5";
                    inputToBeParsed += "5";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "6":
                if(isInverse){
                    currentDisplayedInput += "permu(";
                    inputToBeParsed += "permu(";
                }else{
                    currentDisplayedInput += "6";
                    inputToBeParsed += "6";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "7":
                currentDisplayedInput += "7";
                inputToBeParsed += "7";
                break;
            case "8":
                currentDisplayedInput += "8";
                inputToBeParsed += "8";
                break;
            case "9":
                currentDisplayedInput += "9";
                inputToBeParsed += "9";
                break;
            case ".":
                currentDisplayedInput += ".";
                inputToBeParsed += ".";
                break;
            case "+":
                currentDisplayedInput += "+";
                inputToBeParsed += "+";
                break;
            case "-":
                currentDisplayedInput += "-";
                inputToBeParsed += "-";
                break;
            case "÷":
                currentDisplayedInput += "÷";
                inputToBeParsed += "/";
                break;
            case "x":
                currentDisplayedInput += "*";
                inputToBeParsed += "*";
                break;
            case "(":
                currentDisplayedInput += "(";
                inputToBeParsed += "(";
                break;
            case ")":
                currentDisplayedInput += ")";
                inputToBeParsed += ")";
                break;
            case "%":
                if(isInverse){
                    currentDisplayedInput += "1÷";
                    inputToBeParsed += "1÷";
                }else{
                    currentDisplayedInput += "%";
                    inputToBeParsed += "%";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "ln":
                if(isInverse){
                    currentDisplayedInput += "e^";
                    inputToBeParsed += "e^";
                }else{
                    currentDisplayedInput += "ln(";
                    inputToBeParsed += "ln(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "log":
                if(isInverse){
                    currentDisplayedInput += "10^";
                    inputToBeParsed += "10^";
                }else{
                    currentDisplayedInput += "log(";
                    inputToBeParsed += "log(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "√":
                if(isInverse){
                    currentDisplayedInput += "3√(";
                    inputToBeParsed += "crt(";
                }else{
                    currentDisplayedInput += "√(";
                    inputToBeParsed += "sqrt(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "Yx":
                currentDisplayedInput += "^";
                inputToBeParsed += "^";
                break;
            case "sin":
                if(isInverse){
                    currentDisplayedInput += "asin(";
                    inputToBeParsed += "asin(";
                }else{
                    currentDisplayedInput += "sin(";
                    inputToBeParsed += "sin(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "cos":
                if(isInverse){
                    currentDisplayedInput += "acos(";
                    inputToBeParsed += "acos(";
                }else{
                    currentDisplayedInput += "cos(";
                    inputToBeParsed += "cos(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "tan":
                if(isInverse){
                    currentDisplayedInput += "atan(";
                    inputToBeParsed += "atan(";
                }else{
                    currentDisplayedInput += "tan(";
                    inputToBeParsed += "tan(";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "exp":
                currentDisplayedInput += "E";
                inputToBeParsed += "E0";
                break;
            case "x2":
                if(isInverse){
                    currentDisplayedInput += "^3";
                    inputToBeParsed += "^3";
                }else{
                    currentDisplayedInput += "^2";
                    inputToBeParsed += "^2";
                }
                toggleInverse();
                //toggleShiftButton();
                break;
            case "rnd":
                double ran = Math.random();
                currentDisplayedInput += String.valueOf(ran);
                inputToBeParsed += String.valueOf(ran);
                break;
            case "ABS":
                currentDisplayedInput += "abs(";
                inputToBeParsed += "abs(";
                break;
            case "MR":
                String mValue = getStoredPreferenceValue(SkyInvaderPlay.this);
                String result = removeTrailingZero(mValue);
                if(!result.equals("0")){
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(SkyInvaderPlay.this);
                break;
            case "M+":
                if (isInverse){
                    double inputValueMinus  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValueMinus)){
                        subtractMemoryStorage(SkyInvaderPlay.this, inputValueMinus);
                    }
                }else{
                    double inputValue  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValue)){
                        addToMemoryStorage(SkyInvaderPlay.this, inputValue);
                    }
                }
                toggleInverse();
                //toggleShiftButton();
                break;
        }
        outputResult.setText(currentDisplayedInput);
    }

    private View.OnClickListener closer = new View.OnClickListener() {
        public void onClick(View v) {
            outputResult.setText("");
            currentDisplayedInput = "";
            inputToBeParsed = "";
            alertaCalc.dismiss();
        }
    };

    private String removeTrailingZero(String formattingInput){
        if(!formattingInput.contains(".")){
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if(newValue.equals(".0")){
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }
    private void toggleInverse(){
        if(isInverse){
            isInverse = false;
        }
    }


    private double isANumber(String numberInput){
        double result = Double.NaN;
        try{
            result = Double.parseDouble(numberInput);
        }catch(NumberFormatException nfe){
        }
        return result;
    }
    private void addToMemoryStorage(Context context, double inputToStore){
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue + (float)inputToStore;
        setPreference(context, newValue);
    }
    private void subtractMemoryStorage(Context context, double inputToStore){
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue - (float)inputToStore;
        setPreference(context, newValue);
    }
    private void clearMemoryStorage(Context context){
        setPreference(context, 0);
    }
    private String getStoredPreferenceValue(Context context){
        float returnedValue = getPreference(context);
        return String.valueOf(returnedValue);
    }
    static public boolean setPreference(Context c, float value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("key", value);
        return editor.commit();
    }
    static public float getPreference(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        float value = settings.getFloat("key", 0);
        return value;
    }

    @Override
    public void onBackPressed() {

    }
}
