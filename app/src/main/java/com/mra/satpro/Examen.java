package com.mra.satpro;

import androidx.annotation.NonNull;
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
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ExamenResultadosDTO;
import com.mra.satpro.dto.ModulosDTO;
import com.mra.satpro.utilerias.Calculator;
import com.mra.satpro.utilerias.Helpers;
import com.mra.satpro.utilerias.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;
import static com.mra.satpro.utilerias.Global.arrayimagenes;
import static com.mra.satpro.utilerias.Global.nombreImagenPrimerCarga;

public class Examen extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_flecha;
    private RadioButton r1, r2, r3, r4;
    RadioGroup radioRespuestas;

    private static String url_importa_categorias = "http://www.pypsolucionesintegrales.com/ventas/get_examen.php";
    Context ctx = this;

    String idModulo;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    JSONArray products = null;

    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID_CATEGORIA = "idpreguntasmodulo";
    private static final String TAG_MODULO = "idmodulo";
    private static final String TAG_PREGUNTA = "pregunta";
    private static final String TAG_RESPUESTA_UNO = "respuestauno";
    private static final String TAG_RESPUESTA_DOS = "respuestados";
    private static final String TAG_RESPUESTA_TRES = "respuestatres";
    private static final String TAG_RESPUESTA_CUATRO = "respuestacuatro";
    private static final String TAG_IMAGEN_PREGUNTA = "imagenpregunta";
    private static final String TAG_RESPUESTA_CORRECTA = "correcta";
    private static final String TAG_TOOLTIP = "tooltip";
    private static final String TAG_IMAGEN_TOOLTIP = "imagentooltip";

    String preguntasBloque[];
    String modulosBloque[];
    String respuestaUnoBloque[];
    String respuestaDosBloque[];
    String respuestaTresBloque[];
    String respuestaCuatroBloque[];
    String imagenPreguntaBloque[];
    String correctaBloque[];
    String tooltipBloque[];
    String imagenTooltipBloque[];
    String nombreMateria[];
    String audioTooltip[];
    String audioPregunta[];

    private int preguntaNumero = 0;
    private int aciertos = 0;
    private int preguntaNext = 0;


    EditText pregunta;
    EditText preguntaImagen;
    int tooltipImagen=0;

    TextView tiempoValor;
    private long startTime = 0L;


    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    Timer timer;
    int contRadio = 0;
    int nPreguntas =0;

    DisplayMetrics metrics = new DisplayMetrics();

    Button siguientePreg;

    String respUser = "";
    String nombreAssests = "";
    RadioGroup radioRespuestasExamen;

    int preguntaNumeroExamen = 1;
    String areaLicenciatura = "";
    String queryPorArea[];
    String aleatorio="";

    Context context;

    private FloatingActionButton btnNotas, btnCalculadora, btnContinuar;
    private CountDownTimer mCountDownTimer;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    AdRequest adRequest;
      private final String ID_VIDEO = "ca-app-pub-5240485303222073/2396530603"; // PRODCTIvO
      //private final String ID_VIDEO = "ca-app-pub-3940256099942544/5224354917"; // PRUEBAS
    private AdView mAdView;
    private boolean muestraBanner = true;
    private Button playMp3, stopMp3;
    private LinearLayout botonesMp3;
    private pl.droidsonroids.gif.GifImageView casette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen);

        context = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.e("onAdClicked", "usuario_toco");
                if(!respUser.equalsIgnoreCase("")){
                    siguientePreguntaExamen();
                }
                muestraBanner = true;
            }

            // Called when an ad failed to load.

            public void onAdFailedToLoad(int error) {
                Log.e("onAdFailedToLoad", "usuario_toco");
                muestraBanner = false;
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.e("onAdClosed", "usuario_toco");
                muestraBanner = true;
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.e("onAdImpression", "usuario_toco");
                muestraBanner = true;
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("onAdLoaded", "usuario_toco");
                muestraBanner = true;
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.e("onAdOpened", "usuario_toco");
                if(!respUser.equalsIgnoreCase("")){
                    siguientePreguntaExamen();
                }
                muestraBanner = true;
            }
        });

        adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, ID_VIDEO,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        idModulo = "1";
        nombreAssests = "examen";
        nPreguntas = 80;
        //nPreguntas = 10;

        playMp3 = (Button) findViewById(R.id.playMp3);
        stopMp3 = (Button) findViewById(R.id.stopMp3);
        casette = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.casette);
        botonesMp3 = (LinearLayout) findViewById(R.id.botonesMp3);

        stopMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                casette.setVisibility(View.INVISIBLE);
            }
        });

        playMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproduceMp3(audioPregunta[preguntaNumero].replace(".mp3", ""));
            }
        });

        radioRespuestasExamen = (RadioGroup) findViewById(R.id.radioRespuestasExamen);
        radioRespuestasExamen.clearCheck();

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);


        pregunta = (EditText) findViewById(R.id.preguntaseditText);
        pregunta.setFocusable(false);

        preguntaImagen = (EditText) findViewById(R.id.preguntaImagenExamen);

        preguntaImagen.setFocusable(false);

        tiempoValor = (TextView) findViewById(R.id.timerValue);

        aleatorio = getIntent().getExtras().getString("examenAleatorio");
        areaLicenciatura = getIntent().getExtras().getString("tipoTest");

        Log.e("aleatorio", aleatorio);

      /*  mAdView = (AdView) findViewById(R.id.adView4);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        siguientePreg = (Button) findViewById(R.id.siguientePreg);
        siguientePreg.setOnClickListener(this);

        btnNotas    = findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(this);

        btnCalculadora = findViewById(R.id.btnCalculadora);
        btnCalculadora.setOnClickListener(this);

        btnContinuar= findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(this);

        mCountDownTimer = null;

        mCountDownTimer = new CountDownTimer(10800000, 1000) {

            public void onTick(long millisUntilFinished) {


                String timerValue =   String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                tiempoValor.setText(timerValue);
            }

            public void onFinish() {
                tiempoValor.setText("done!");
            }
        }.start();


        r1=(RadioButton)findViewById(R.id.r1);
        r2=(RadioButton)findViewById(R.id.r2);
        r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);

      /*  if(!idModulo.equalsIgnoreCase("")){
            new ImportarCategoriasCSVTask().execute("");
        }*/

        buscaOpciones();

        if(aleatorio.equalsIgnoreCase("Si")){
            examenAleatorio();
        }else{
            examenGratis();
        }


        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("r1", r1.getText().toString());
                    //siguientePregunta(respuestaUnoBloque[preguntaNumero]);
                    respUser = respuestaUnoBloque[preguntaNumero];
                }
            }
        });

        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("r2", r2.getText().toString());
                    //siguientePregunta(respuestaDosBloque[preguntaNumero]);
                    respUser = respuestaDosBloque[preguntaNumero];
                }
            }
        });

        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("r3", r3.getText().toString());
                    //siguientePregunta(respuestaTresBloque[preguntaNumero]);
                    respUser = respuestaTresBloque[preguntaNumero];
                }
            }
        });

        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("r4", r4.getText().toString());
                    //siguientePregunta(respuestaCuatroBloque[preguntaNumero]);
                    respUser = respuestaCuatroBloque[preguntaNumero];
                }
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

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuaPregunta();
            }
        });

        img_flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent linsertar = new Intent(Examen.this, MainActivity.class);
                startActivity(linsertar);
            }
        });


    }

    MediaPlayer mPlayer;
    public void reproduceMp3(String nombreAudio){
        int filePlay = getResources().getIdentifier(nombreAudio,
                "raw", getPackageName());
        mPlayer = MediaPlayer.create(Examen.this, filePlay);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                casette.setVisibility(View.INVISIBLE);
            }
        });
        mPlayer.start();
        casette.setVisibility(View.VISIBLE);
    }

    private GestureOverlayView gestureFirma;
    private AlertDialog dialog;
    Button btnGestureClean, btnGestureCerrar;
    FloatingActionButton btnClearNotas, btnCloseNotas;

    private void showAlertDialogFirma()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Examen.this);
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


    public void examenAleatorio(){

        preguntasBloque = new String[nPreguntas];
        modulosBloque = new String[nPreguntas];
        respuestaUnoBloque = new String[nPreguntas];
        respuestaDosBloque = new String[nPreguntas];
        respuestaTresBloque = new String[nPreguntas];
        respuestaCuatroBloque = new String[nPreguntas];
        imagenPreguntaBloque = new String[nPreguntas];
        correctaBloque = new String[nPreguntas];
        tooltipBloque = new String[nPreguntas];
        imagenTooltipBloque = new String[nPreguntas];
        audioPregunta = new String[nPreguntas];
        audioTooltip = new String[nPreguntas];

        if(areaLicenciatura.equalsIgnoreCase("Reading")){
            queryPorArea = new String[2];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 14 ORDER BY RANDOM() LIMIT 40";
            queryPorArea[1] = "select * from tblpreguntasmodulo where idmodulo = 15 ORDER BY RANDOM() LIMIT 40";
            botonesMp3.setVisibility(View.VISIBLE);
        }
        if(areaLicenciatura.equalsIgnoreCase("Math")){
            queryPorArea = new String[1];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 16 ORDER BY RANDOM() LIMIT 80";
        }
        if(areaLicenciatura.equalsIgnoreCase("Biology")){
            queryPorArea = new String[1];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 17 ORDER BY RANDOM() LIMIT 80";
        }
        if(areaLicenciatura.equalsIgnoreCase("Chemistry")){
            queryPorArea = new String[1];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 18 ORDER BY RANDOM() LIMIT 80";
        }
        if(areaLicenciatura.equalsIgnoreCase("Physics")){
            queryPorArea = new String[1];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 19 ORDER BY RANDOM() LIMIT 80";
        }
        if(areaLicenciatura.equalsIgnoreCase("Spanish")){
            queryPorArea = new String[1];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 20 ORDER BY RANDOM() LIMIT 80";
        }

        int contador =0;

        for(int i=0; i<queryPorArea.length; i++){

            BaseDaoExamen conexion=new BaseDaoExamen(this);
            List lista = conexion.ConsultarExamenesRandomTodas(queryPorArea[i]);

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
                String audioPreg = elementos.getAudiopregunta().replace("-", "_");
                String audioTool = elementos.getAudiotooltip().replace("-", "_");

                Log.e("idModulo", idModulo);

                modulosBloque[contador] = idModulo;
                preguntasBloque[contador] = pregunta.trim();
                respuestaUnoBloque[contador] = respuestaUno;
                respuestaDosBloque[contador] = respuestaDos;
                respuestaTresBloque[contador] = respuestaTres;
                respuestaCuatroBloque[contador] = respuestaCuatro;
                imagenPreguntaBloque[contador] = imagenPregunta;
                correctaBloque[contador] = correcta;
                tooltipBloque[contador] = tooltip;
                imagenTooltipBloque[contador] = imagenTooltip;
                audioPregunta[contador] = audioPreg;
                audioTooltip[contador] = audioTool;

                contador = contador +1;
            }
        }


     /*   BaseDaoExamen conexion=new BaseDaoExamen(this);
        List lista = conexion.ConsultarExamenesRandomEspa();

        Log.e("listaExamen", lista.size()+"");

        int contador =0;

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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
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

        //raz verbal
        List listaRazVerbal = conexion.ConsultarExamenesRandomRazVerbal();

        for(Object datos: listaRazVerbal) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        //raz verbal
        List listaHistUniv = conexion.ConsultarExamenesRandomHisUniv();

        for(Object datos: listaHistUniv) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        //raz verbal
        List listaHistMEx = conexion.ConsultarExamenesRandomHisMex();

        for(Object datos: listaHistMEx) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaGeoUniv = conexion.ConsultarExamenesRandomGeoUniv();

        for(Object datos: listaGeoUniv) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaGeoMex = conexion.ConsultarExamenesRandomGeoMex();

        for(Object datos: listaGeoMex) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaCivismo = conexion.ConsultarExamenesRandomCivismo();

        for(Object datos: listaCivismo) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaRazMate = conexion.ConsultarExamenesRandomRazMate();

        for(Object datos: listaRazMate) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaMatematicas = conexion.ConsultarExamenesRandomMatematicas();

        for(Object datos: listaMatematicas) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaFisica = conexion.ConsultarExamenesRandomFisica();

        for(Object datos: listaFisica) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaQuimica = conexion.ConsultarExamenesRandomQuimica();

        for(Object datos: listaQuimica) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
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

        List listaBilogia = conexion.ConsultarExamenesRandomBiologia();

        for(Object datos: listaBilogia) {
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

            Log.e("idModulo", idModulo);

            modulosBloque[contador] = idModulo;
            preguntasBloque[contador] = pregunta;
            respuestaUnoBloque[contador] = respuestaUno;
            respuestaDosBloque[contador] = respuestaDos;
            respuestaTresBloque[contador] = respuestaTres;
            respuestaCuatroBloque[contador] = respuestaCuatro;
            imagenPreguntaBloque[contador] = imagenPregunta;
            correctaBloque[contador] = correcta;
            tooltipBloque[contador] = tooltip;
            imagenTooltipBloque[contador] = imagenTooltip;

            contador = contador +1;
        }*/

        ///radios y preguntas con imagen
        boolean siEsImagen = false;

        if(imagenPreguntaBloque[0] != null){
            if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                siEsImagen = true;
            }
        }


        if(siEsImagen==true){
            // Loader image - will be shown before loading image

            // Image url
            /*        String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenPreguntaBloque[preguntaNumero];

                    // Create an object for subclass of AsyncTask
                    GetXMLTask task = new GetXMLTask();
                    // Execute the task
                    task.execute(new String[] { image_url });*/

            String mDrawableName = "";
            int resID = 0;

            for(int i=0; i<arrayimagenes.length; i ++){
                if(imagenPreguntaBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

            pregunta.setBackgroundDrawable(getResources().getDrawable(resID));

            preguntaImagen.setText(preguntasBloque[0]);
            preguntaImagen.setVisibility(View.VISIBLE);
            pregunta.setText("");
            pregunta.setEnabled(false);

        }else{

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

            pregunta.setText(preguntasBloque[0]);
            pregunta.setCompoundDrawables(null, null, null, null);
            preguntaImagen.setText("");
            pregunta.setEnabled(false);
        }

        if(respuestaUnoBloque[0] != null){
            if(respuestaUnoBloque[0].contains(".png") || respuestaUnoBloque[0].contains(".PNG")){

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaUnoBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }


                r1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaDosBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }


                r2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaTresBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                r3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaCuatroBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                r4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                r1.setText("");
                r2.setText("");
                r3.setText("");
                r4.setText("");


            }else{
                r1.setText("a) " + respuestaUnoBloque[0]);
                r2.setText("b) " + respuestaDosBloque[0]);
                r3.setText("c) " + respuestaTresBloque[0]);
                r4.setText("d) " + respuestaCuatroBloque[0]);

                r1.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r2.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r3.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r4.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }

        }


    }

    public void examenGratis(){

        preguntasBloque = new String[nPreguntas];
        modulosBloque = new String[nPreguntas];
        respuestaUnoBloque = new String[nPreguntas];
        respuestaDosBloque = new String[nPreguntas];
        respuestaTresBloque = new String[nPreguntas];
        respuestaCuatroBloque = new String[nPreguntas];
        imagenPreguntaBloque = new String[nPreguntas];
        correctaBloque = new String[nPreguntas];
        tooltipBloque = new String[nPreguntas];
        imagenTooltipBloque = new String[nPreguntas];



        if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){
            queryPorArea = new String[9];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 1 LIMIT 18";
            queryPorArea[1] = "select * from tblpreguntasmodulo where idmodulo = 12 LIMIT 16";
            queryPorArea[2] = "select * from tblpreguntasmodulo where idmodulo = 6 LIMIT 26";
            queryPorArea[3] = "select * from tblpreguntasmodulo where idmodulo = 7 LIMIT 10";
            queryPorArea[4] = "select * from tblpreguntasmodulo where idmodulo = 5 LIMIT 10";
            queryPorArea[5] = "select * from tblpreguntasmodulo where idmodulo = 4 LIMIT 10";
            queryPorArea[6] = "select * from tblpreguntasmodulo where idmodulo = 11 LIMIT 10";
            queryPorArea[7] = "select * from tblpreguntasmodulo where idmodulo = 3 LIMIT 10";
            queryPorArea[8] = "select * from tblpreguntasmodulo where idmodulo = 8 LIMIT 10";
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 2: Ciencias Biológicas, Quimicas y de la Salud")){
            queryPorArea = new String[9];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 1 LIMIT 18";
            queryPorArea[1] = "select * from tblpreguntasmodulo where idmodulo = 12 LIMIT 12";
            queryPorArea[2] = "select * from tblpreguntasmodulo where idmodulo = 6 LIMIT 24";
            queryPorArea[3] = "select * from tblpreguntasmodulo where idmodulo = 7 LIMIT 10";
            queryPorArea[4] = "select * from tblpreguntasmodulo where idmodulo = 5 LIMIT 10";
            queryPorArea[5] = "select * from tblpreguntasmodulo where idmodulo = 4 LIMIT 13";
            queryPorArea[6] = "select * from tblpreguntasmodulo where idmodulo = 11 LIMIT 13";
            queryPorArea[7] = "select * from tblpreguntasmodulo where idmodulo = 3 LIMIT 10";
            queryPorArea[8] = "select * from tblpreguntasmodulo where idmodulo = 8 LIMIT 10";
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 3: Ciencias Sociales")){
            queryPorArea = new String[9];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 1 LIMIT 18";
            queryPorArea[1] = "select * from tblpreguntasmodulo where idmodulo = 12 LIMIT 10";
            queryPorArea[2] = "select * from tblpreguntasmodulo where idmodulo = 6 LIMIT 24";
            queryPorArea[3] = "select * from tblpreguntasmodulo where idmodulo = 7 LIMIT 10";
            queryPorArea[4] = "select * from tblpreguntasmodulo where idmodulo = 5 LIMIT 10";
            queryPorArea[5] = "select * from tblpreguntasmodulo where idmodulo = 4 LIMIT 10";
            queryPorArea[6] = "select * from tblpreguntasmodulo where idmodulo = 11 LIMIT 10";
            queryPorArea[7] = "select * from tblpreguntasmodulo where idmodulo = 3 LIMIT 14";
            queryPorArea[8] = "select * from tblpreguntasmodulo where idmodulo = 8 LIMIT 14";
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 4: Humanidades y Artes")){
            queryPorArea = new String[10];
            queryPorArea[0] = "select * from tblpreguntasmodulo where idmodulo = 1 LIMIT 18";
            queryPorArea[1] = "select * from tblpreguntasmodulo where idmodulo = 12 LIMIT 10";
            queryPorArea[2] = "select * from tblpreguntasmodulo where idmodulo = 6 LIMIT 22";
            queryPorArea[3] = "select * from tblpreguntasmodulo where idmodulo = 7 LIMIT 10";
            queryPorArea[4] = "select * from tblpreguntasmodulo where idmodulo = 5 LIMIT 10";
            queryPorArea[5] = "select * from tblpreguntasmodulo where idmodulo = 4 LIMIT 10";
            queryPorArea[6] = "select * from tblpreguntasmodulo where idmodulo = 11 LIMIT 10";
            queryPorArea[7] = "select * from tblpreguntasmodulo where idmodulo = 3 LIMIT 10";
            queryPorArea[8] = "select * from tblpreguntasmodulo where idmodulo = 8 LIMIT 10";
            queryPorArea[9] = "select * from tblpreguntasmodulo where idmodulo = 2 LIMIT 10";
        }

        int contador =0;

        for(int i=0; i<queryPorArea.length; i++){

            BaseDaoExamen conexion=new BaseDaoExamen(this);
            List lista = conexion.ConsultarExamenesRandomTodas(queryPorArea[i]);

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
                String audioPreg = elementos.getAudiopregunta().replace("-", "_");
                String audioTool = elementos.getAudiotooltip().replace("-", "_");


                modulosBloque[contador] = idModulo;
                preguntasBloque[contador] = pregunta.trim();
                respuestaUnoBloque[contador] = respuestaUno;
                respuestaDosBloque[contador] = respuestaDos;
                respuestaTresBloque[contador] = respuestaTres;
                respuestaCuatroBloque[contador] = respuestaCuatro;
                imagenPreguntaBloque[contador] = imagenPregunta;
                correctaBloque[contador] = correcta;
                tooltipBloque[contador] = tooltip;
                imagenTooltipBloque[contador] = imagenTooltip;
                audioPregunta[contador] = audioPreg;
                audioTooltip[contador] = audioTool;

                contador = contador +1;
            }
        }



        ///radios y preguntas con imagen
        boolean siEsImagen = false;

        if(imagenPreguntaBloque[0] != null){
            if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                siEsImagen = true;
            }
        }


        if(siEsImagen==true){
            // Loader image - will be shown before loading image

            // Image url
            /*        String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenPreguntaBloque[preguntaNumero];

                    // Create an object for subclass of AsyncTask
                    GetXMLTask task = new GetXMLTask();
                    // Execute the task
                    task.execute(new String[] { image_url });*/

            String mDrawableName = "";
            int resID = 0;

            for(int i=0; i<arrayimagenes.length; i ++){
                if(imagenPreguntaBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

            pregunta.setBackgroundDrawable(getResources().getDrawable(resID));

            preguntaImagen.setText(preguntasBloque[0]);
            preguntaImagen.setVisibility(View.VISIBLE);
            pregunta.setText("");
            pregunta.setEnabled(false);

        }else{

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);

            pregunta.setText(preguntasBloque[0]);
            pregunta.setCompoundDrawables(null, null, null, null);
            preguntaImagen.setText("");
            pregunta.setEnabled(false);
        }

        if(respuestaUnoBloque[0] != null){
            if(respuestaUnoBloque[0].contains(".png") || respuestaUnoBloque[0].contains(".PNG")){

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaUnoBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }


                r1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaDosBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }


                r2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaTresBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                r3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(respuestaCuatroBloque[0].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                r4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                r1.setText("");
                r2.setText("");
                r3.setText("");
                r4.setText("");


            }else{
                r1.setText("a) " + respuestaUnoBloque[0]);
                r2.setText("b) " + respuestaDosBloque[0]);
                r3.setText("c) " + respuestaTresBloque[0]);
                r4.setText("d) " + respuestaCuatroBloque[0]);

                r1.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r2.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r3.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                r4.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }

        }


    }


    public void siguientePreguntaButton(String radio){

        String respCorrecta = "";
        respCorrecta = radio;

        pregunta.setText("");
        pregunta.setCompoundDrawables(null, null, null, null);
        pregunta.setBackgroundDrawable(null);
        preguntaImagen.setText("");
        preguntaImagen.setCompoundDrawables(null, null, null, null);
        preguntaImagen.setBackgroundDrawable(null);
        preguntaImagen.setVisibility(View.INVISIBLE);


        preguntaNext = preguntaNext + 1;

        if(preguntaNext == nPreguntas) {
            resultados();
        }


        //    aciertos = aciertos + 1;
        //   preguntaNext = preguntaNext + 1;

        if(preguntaNumero < (correctaBloque.length - 1)) {
            preguntaNumero = preguntaNumero + 1;

            ///radios y preguntas con imagen
            boolean siEsImagen = false;

            //Log.e("imagenPreguntaBloque", imagenPreguntaBloque[preguntaNumero]);

            if(imagenPreguntaBloque[preguntaNumero] != null){
                if (!imagenPreguntaBloque[preguntaNumero].equalsIgnoreCase("")){
                    siEsImagen = true;
                }
            }


            if(siEsImagen==true){
                // Loader image - will be shown before loading image

                // Image url
            /*        String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenPreguntaBloque[preguntaNumero];

                    // Create an object for subclass of AsyncTask
                    GetXMLTask task = new GetXMLTask();
                    // Execute the task
                    task.execute(new String[] { image_url });*/

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(imagenPreguntaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                pregunta.setLayoutParams(textParam);

                pregunta.setBackgroundDrawable(getResources().getDrawable(resID));

                preguntaImagen.setText(preguntasBloque[preguntaNumero]);
                preguntaImagen.setVisibility(View.VISIBLE);
                pregunta.setText("");
                pregunta.setEnabled(false);

            }else{

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                pregunta.setLayoutParams(textParam);

                pregunta.setText(preguntasBloque[preguntaNumero]);
                pregunta.setCompoundDrawables(null, null, null, null);
                preguntaImagen.setText("");
                pregunta.setEnabled(false);
            }

            if(respuestaUnoBloque[preguntaNumero] != null){

                if(respuestaUnoBloque[preguntaNumero].contains(".png")
                        || respuestaUnoBloque[preguntaNumero].contains(".PNG")){

                    String mDrawableName = "";
                    int resID = 0;

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(respuestaUnoBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }


                    r1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(respuestaDosBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }


                    r2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(respuestaTresBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    r3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(respuestaCuatroBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    r4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);

                    r1.setText("");
                    r2.setText("");
                    r3.setText("");
                    r4.setText("");


                }else{
                    r1.setText("a) " + respuestaUnoBloque[preguntaNumero]);
                    r2.setText("b) " + respuestaDosBloque[preguntaNumero]);
                    r3.setText("c) " + respuestaTresBloque[preguntaNumero]);
                    r4.setText("d) " + respuestaCuatroBloque[preguntaNumero]);

                    r1.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    r2.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    r3.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    r4.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }

            }



            /*    r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);*/

            radioRespuestasExamen.clearCheck();

        }


        respUser = "";

    }


    public void resultados(){

        String palomitas = aciertos+"";
        String totalPreg = preguntasBloque.length+"";

        Log.e("aciertos", aciertos+"");
        Log.e("preguntasBloque", preguntasBloque.length+"");

        finish();
        Intent linsertar=new Intent(this, ResultadoExamen.class);
        linsertar.putExtra("aciertos", palomitas);
        linsertar.putExtra("preguntas", totalPreg);
        linsertar.putExtra("areaLicenciatura", areaLicenciatura);
        startActivity(linsertar);

    }

    //iNICIA IMPORTAR CATEGORIAS

    class ImportarCategoriasCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        // can use UI thread here

        protected void onPreExecute()

        {
            this.dialog.setMessage("Consultando exámen...");
            this.dialog.show();
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {

            JSONObject json = null;

            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", mail));
            params.add(new BasicNameValuePair("password", password));*/

            HashMap<String, String> params = new HashMap<>();
            params.put("mail", idModulo);


            // getting JSON Object
            // Note that create product url accepts POST method
            try {
                json = jsonParser.makeHttpRequest(url_importa_categorias,
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

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    preguntasBloque = new String[products.length()];
                    respuestaUnoBloque = new String[products.length()];
                    respuestaDosBloque = new String[products.length()];
                    respuestaTresBloque = new String[products.length()];
                    respuestaCuatroBloque = new String[products.length()];
                    imagenPreguntaBloque = new String[products.length()];
                    correctaBloque = new String[products.length()];
                    tooltipBloque = new String[products.length()];
                    imagenTooltipBloque = new String[products.length()];
                    modulosBloque = new String[products.length()];

                    // looping through All Products


                    Log.e("products.length()", products.length()+"");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID_CATEGORIA);
                        String pregunta = c.getString(TAG_PREGUNTA);
                        String idModulo = c.getString(TAG_MODULO);
                        String respuestaUno = c.getString(TAG_RESPUESTA_UNO);
                        String respuestaDos = c.getString(TAG_RESPUESTA_DOS);
                        String respuestaTres = c.getString(TAG_RESPUESTA_TRES);
                        String respuestaCuatro = c.getString(TAG_RESPUESTA_CUATRO);
                        String imagenPregunta = c.getString(TAG_IMAGEN_PREGUNTA);
                        String correcta = c.getString(TAG_RESPUESTA_CORRECTA);
                        String tooltip = c.getString(TAG_TOOLTIP);
                        String imagenTooltip = c.getString(TAG_IMAGEN_TOOLTIP);

                        Log.e("idModulo", idModulo);

                        modulosBloque[i] = idModulo;
                        preguntasBloque[i] = pregunta;
                        respuestaUnoBloque[i] = respuestaUno;
                        respuestaDosBloque[i] = respuestaDos;
                        respuestaTresBloque[i] = respuestaTres;
                        respuestaCuatroBloque[i] = respuestaCuatro;
                        imagenPreguntaBloque[i] = imagenPregunta;
                        correctaBloque[i] = correcta;
                        tooltipBloque[i] = tooltip;
                        imagenTooltipBloque[i] = imagenTooltip;


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


            if (dialog!=null && this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            boolean siEsImagen = false;

            if(imagenPreguntaBloque[0] != null){
                if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                    siEsImagen = true;
                }
            }


            if(siEsImagen==true){
                // Loader image - will be shown before loading image


                // Image url
                String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenPreguntaBloque[0];

                // Create an object for subclass of AsyncTask
                GetXMLTask task = new GetXMLTask();
                // Execute the task
                task.execute(new String[] { image_url });

                preguntaImagen.setText(preguntasBloque[0]);
                preguntaImagen.setVisibility(View.VISIBLE);
                pregunta.setText("");

            }else{
                pregunta.setText(Html.fromHtml(preguntasBloque[0]));
            }


            if(respuestaUnoBloque[0] != null){


                if(respuestaUnoBloque[0].contains(".png")
                        || respuestaUnoBloque[0].contains(".PNG")){


                    // Image url
                    String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + respuestaUnoBloque[0];
                    String image_url2 = "http://www.pypsolucionesintegrales.com/Imagenes/" + respuestaDosBloque[0];
                    String image_url3 = "http://www.pypsolucionesintegrales.com/Imagenes/" + respuestaTresBloque[0];
                    String image_url4 = "http://www.pypsolucionesintegrales.com/Imagenes/" + respuestaCuatroBloque[0];

                    // Create an object for subclass of AsyncTask
                    radios task = new radios();
                    // Execute the task
                    task.execute(new String[] { image_url, image_url2, image_url3, image_url4});

                    r1.setText("");
                    //r1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.aciertos, 0);
                    r2.setText("");
                    r3.setText("");
                    r4.setText("");

                }else{
                    r1.setText("a) " + respuestaUnoBloque[0]);
                    r2.setText("b) " + respuestaDosBloque[0]);
                    r3.setText("c) " + respuestaTresBloque[0]);
                    r4.setText("d) " + respuestaCuatroBloque[0]);
                }

            }


        }
    }


    BitmapDrawable radioImageUno;
    BitmapDrawable radioImageDos;
    BitmapDrawable radioImageTres;
    BitmapDrawable radioImageCuatro;

    //IMPORTAR RADIOS
    private class radios extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            contRadio = 0;
            for (String url : urls) {


                Log.e("urls", url);
                map = downloadImage(url);
                contRadio = contRadio + 1;



                if(contRadio==1){
                    radioImageUno = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 250, true));
                    Log.e("url1", url);
                }
                if(contRadio==2){
                    radioImageDos = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 250, true));
                    Log.e("url2", url);
                }
                if(contRadio==3){
                    radioImageTres = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 250, true));
                    Log.e("url3", url);
                }
                if(contRadio==4){
                    radioImageCuatro = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 250, true));
                    Log.e("url4", url);
                }

            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            // image.setImageBitmap(result);

            // BitmapDrawable bkgbt = new BitmapDrawable(getResources(),result);


            //r1.setCompoundDrawablesWithIntrinsicBounds(null,null,bkgbt,null);
            //   if(contRadio==1){
            //r1.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));
            r1.setCompoundDrawablesWithIntrinsicBounds(radioImageUno,null,null,null);
            //   }
            //   if(contRadio==2){
            //r2.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));
            r2.setCompoundDrawablesWithIntrinsicBounds(radioImageDos,null,null,null);
            //   }
            //   if(contRadio==3){
            //r3.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));
            r3.setCompoundDrawablesWithIntrinsicBounds(radioImageTres,null,null,null);
            //   }
            //   if(contRadio==4){
            //r4.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));
            r4.setCompoundDrawablesWithIntrinsicBounds(radioImageCuatro,null,null,null);
            //   }







        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

    //Descarga imagen pregunta
    BitmapDrawable preguntaConImagen;

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);

                //inicia calculo de tamaño
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                Bitmap bitmapOrg = new BitmapDrawable(getResources(), map).getBitmap();

                int width = bitmapOrg.getWidth();
                int height = bitmapOrg.getHeight();

                float scaleWidth = metrics.scaledDensity;
                float scaleHeight = metrics.scaledDensity;

                // create a matrix for the manipulation
                Matrix matrix = new Matrix();
                // resize the bit map
                matrix.postScale(scaleWidth, scaleHeight);

                // recreate the new Bitmap
                Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
                //fin calculo de tamaño

                //tooltip = new BitmapDrawable(getResources(), resizedBitmap);

                //preguntaConImagen = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 250, 350, true));
                preguntaConImagen = new BitmapDrawable(getResources(), resizedBitmap);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            // image.setImageBitmap(result);



            //BitmapDrawable bkgbt = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, 100, 250, true));
            //BitmapDrawable bkgbt = new BitmapDrawable(getResources(), result);
            pregunta.setBackgroundDrawable(preguntaConImagen);
          /*  if(tooltipImagen==1){
                txtMensajeAyuda.setBackgroundDrawable(bkgbt);
            }*/

        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

    public void siguientePreguntaExamen(){

        //mRewardedVideoAd.loadAd(AD_VIDEO, new AdRequest.Builder().build());

        int respCorrecta = 0;

        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        ExamenResultadosDTO datos = new ExamenResultadosDTO();
        Date currentTime = Calendar.getInstance().getTime();

        datos.setMateria(modulosBloque[preguntaNumero]);
        datos.setPregunta(preguntasBloque[preguntaNumero]);
        datos.setPreguntaImagen(imagenPreguntaBloque[preguntaNumero]);
        datos.setRespuesta(respUser);
        datos.setCorrecta(correctaBloque[preguntaNumero]);
        datos.setTooltip(tooltipBloque[preguntaNumero]);
        datos.setTooltipImagen(imagenTooltipBloque[preguntaNumero]);

        datos.setFecha(currentTime+"");

        Log.e("preguntaNumeroExamen", preguntaNumeroExamen+"");

        List listas = conexion.ConsultarExamenResultadosPorId(preguntaNumeroExamen);

        if(correctaBloque[preguntaNumero].trim().equalsIgnoreCase(respUser.trim())){
            aciertos = aciertos + 1;
            respCorrecta = 1;
            datos.setAciertos(respCorrecta);
            if(listas.size()>0){

                datos.setId(preguntaNumeroExamen);
                if (conexion.ExamenResultadosActualiza(datos)) {
                    //       Toast.makeText(this, "¡Se actualizò respuesta!", Toast.LENGTH_SHORT).show();
                } else {
                    //       Toast.makeText(this, "¡Error al actualizar varifique datos!", Toast.LENGTH_SHORT).show();
                }

            }else{
                if (conexion.insertaExamenResultados(datos)) {
                    //       Toast.makeText(this, "¡Se guardó respuesta!", Toast.LENGTH_SHORT).show();
                } else {
                    //       Toast.makeText(this, "¡Error al guardar varifique datos!", Toast.LENGTH_SHORT).show();
                }
            }
            preguntaNumeroExamen = preguntaNumeroExamen + 1;

            siguientePreguntaButton(respUser);


        }else{
            datos.setAciertos(respCorrecta);
            if(listas.size()>0){

                datos.setId(preguntaNumeroExamen);
                if (conexion.ExamenResultadosActualiza(datos)) {
                    //       Toast.makeText(this, "¡Se actualizò respuesta!", Toast.LENGTH_SHORT).show();
                } else {
                    //       Toast.makeText(this, "¡Error al actualizar varifique datos!", Toast.LENGTH_SHORT).show();
                }

            }else{
                if (conexion.insertaExamenResultados(datos)) {
                    //       Toast.makeText(this, "¡Se guardó respuesta!", Toast.LENGTH_SHORT).show();
                } else {
                    //       Toast.makeText(this, "¡Error al guardar varifique datos!", Toast.LENGTH_SHORT).show();
                }
            }
            preguntaNumeroExamen = preguntaNumeroExamen +1;

            siguientePreguntaButton(respUser);
        }

    }




    public void continuaPregunta(){

        if(!respUser.equalsIgnoreCase("")){

            //if(videoSi){
                //Log.e("videoSiincorrecto", preguntaNumeroExamen+"");
                //siguientePreguntaExamen();
                //validaVideoPreguntas();

            if(preguntaNumeroExamen==15  || preguntaNumeroExamen==30 || preguntaNumeroExamen==45
                    || preguntaNumeroExamen==60 || preguntaNumeroExamen==75
                    || preguntaNumeroExamen==90 || preguntaNumeroExamen==105
            ){
                validaVideoPreguntas();
            }else if(preguntaNumeroExamen==10 || preguntaNumeroExamen==30
                    || preguntaNumeroExamen==60 || preguntaNumeroExamen==90
            ){
                if(muestraBanner){
                    mostrarInfoVidas();
                }else{
                    siguientePreguntaExamen();
                }
            }else{
                siguientePreguntaExamen();
            }

                /*if(haveNetworkConnection()==true){
                    if(preguntaNumeroExamen==10  || preguntaNumeroExamen==20 || preguntaNumeroExamen==30
                            || preguntaNumeroExamen==40 || preguntaNumeroExamen==50 || preguntaNumeroExamen==60
                            || preguntaNumeroExamen==70 || preguntaNumeroExamen==80
                            || preguntaNumeroExamen==90 || preguntaNumeroExamen==100
                            || preguntaNumeroExamen==110
                    ){
                        muestraVideo();
                    }else{
                        siguientePreguntaExamen();
                    }
                }else{
                    showAlertDialogEliminarDatosConductores();
                }*/

           // }



        }else{
            Toast toast = Toast.makeText(this,"Choose an answer", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void mostrarInfoVidas() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Examen.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("donativo.json");

        builder.setMessage(getResources().getString(R.string.masPreguntasIlimitadasExamen))
                .setTitle(" PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("  Pro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                //regresaMenu();
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
                //regresaMenu();
            }
        });



        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public void validaVideoPreguntas(){


        if(preguntaNumeroExamen==15  || preguntaNumeroExamen==30 || preguntaNumeroExamen==45
                || preguntaNumeroExamen==60 || preguntaNumeroExamen==75
                || preguntaNumeroExamen==90 || preguntaNumeroExamen==105
        ){


            RewardedAd.load(this, ID_VIDEO,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.d(TAG, loadAdError.getMessage());
                            mRewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            Log.d(TAG, "Ad was loaded.");
                        }

                    });


            if (mRewardedAd != null) {

                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad was shown.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "Ad failed to show.");
                        siguientePreguntaExamen();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        //salirPatos();
                        Log.d(TAG, "Ad was dismissed.");
                        mRewardedAd = null;
                    }


                });

                Activity activityContext = Examen.this;
                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        mRewardedAd = null;
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        siguientePreguntaExamen();
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }

        }else{
            siguientePreguntaExamen();
        }



    }

    private void showAlertDialogEliminarDatosConductores()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Examen.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage(getString(R.string.conexion));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.si),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.noGracias),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final String appPackageName ="com.mra.examenunampremiumpremium";
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



    //FIN IMPORTAR CATEGORIAS

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
                String mValue = getStoredPreferenceValue(Examen.this);
                String result = removeTrailingZero(mValue);
                if(!result.equals("0")){
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(Examen.this);
                break;
            case "M+":
                if (isInverse){
                    double inputValueMinus  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValueMinus)){
                        subtractMemoryStorage(Examen.this, inputValueMinus);
                    }
                }else{
                    double inputValue  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValue)){
                        addToMemoryStorage(Examen.this, inputValue);
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
   /* private void toggleShiftButton(){
        if(isInverse){
            shiftDisplay.setText("SHIFT");
        }else{
            shiftDisplay.setText("");
        }
    }*/

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

    //fin calculadora

    @Override
    public void onBackPressed() {
        /*finish();
        Intent i=new Intent(this, InfoExamen.class);
        startActivity(i);*/
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


       /* if (v.getId() == findViewById(R.id.img_flecha).getId()) {
            finish();
            Intent linsertar = new Intent(Examen.this, InfoExamen.class);
            startActivity(linsertar);
        }*/

        /*if(v.getId() == findViewById(R.id.btnNotas).getId()){
            showAlertDialogFirma();
        }*/

        /*if(v.getId() == findViewById(R.id.btnCalculadora).getId()){
            calculadoraSi = true;
            initiatePopupWindowCalculadoraBotonesCientifica();
        }*/

        /*if (v.getId() == findViewById(R.id.siguientePreg).getId()) {
            continuaPregunta();
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
}
