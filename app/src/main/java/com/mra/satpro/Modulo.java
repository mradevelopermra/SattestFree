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
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mra.satpro.dao.BaseDaoPassword;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ExamenResultadosDTO;
import com.mra.satpro.dto.ModulosDTO;
import com.mra.satpro.dto.PasswordDTo;
import com.mra.satpro.models.Vidas;
import com.mra.satpro.utilerias.Calculator;
import com.mra.satpro.utilerias.GifImageView;
import com.mra.satpro.utilerias.Helpers;
import com.mra.satpro.utilerias.JSONParser;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;
import static com.mra.satpro.utilerias.Global.*;
import static com.mra.satpro.ConfigurarModulo.mContext;


public class Modulo extends AppCompatActivity implements View.OnClickListener {

    String tipoTest;
    String autoAyuda;
    String numPreguntas;

    TextView titulomodulo;
    private ImageView img_flecha;

    private static String url_importa_categorias = "http://www.pypsolucionesintegrales.com/ventas/get_modulos.php";
    Context ctx = this;

    String idModulo ="";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    JSONArray products = null;

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

    String preguntasBloque[];
    String respuestaUnoBloque[];
    String respuestaDosBloque[];
    String respuestaTresBloque[];
    String respuestaCuatroBloque[];
    String imagenPreguntaBloque[];
    String correctaBloque[];
    String tooltipBloque[];
    String imagenTooltipBloque[];
    String audioTooltip[];
    String audioPregunta[];

    String nombreMateriaPropuesta[];

    int idCate [];
    RadioGroup radioRespuestas;

    private RadioButton r1, r2, r3, r4;
    private int preguntaNumero = 0;
    private int aciertos = 0;
    private int preguntaNext = 0;
    private int correctoIncorrecto = 0;

    final Context context = this;
    AlertDialog alertaAyuda;

    Button ToolTip, next;
    int nPreguntas =0;

    String imgUrl;
    EditText txtMensajeAyuda;
    int tooltipImagen=0;
    EditText preguntaImagen, pregunta;

    int contRadio = 0;
    TextView respCorrectaToolTip;

    int conPagTool = 0;
    int conCorrectaTool = 0;

    DisplayMetrics metrics = new DisplayMetrics();

    String nombreMatTit ="";
    String nombreAssests = "";

    String respuestaRadio = "";
    String random = "";

    private FloatingActionButton btnAyuda, btnNotas, btnCalculadora, btnContinuar;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    AdRequest adRequest;
    private final String ID_VIDEO = "ca-app-pub-5240485303222073/5047578820"; // PRODCTIvO
    //private final String ID_VIDEO = "ca-app-pub-3940256099942544/5224354917"; // PRUEBAS
    //Vidas
    private TextView textViewVidas;
    private LottieAnimationView animation_view_vidas;
    private AdView mAdView;
    //private RewardedVideoAd mRewardedVideoAd;
    //videos de pruebas
    /*private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544~3347511713";
    private static final String AD_VIDEO = "ca-app-pub-3940256099942544/5224354917";*/

    //videos productivos
    private static final String AD_UNIT_ID = "ca-app-pub-5240485303222073~6269745980";
    private static final String AD_VIDEO = "ca-app-pub-5240485303222073/1509566830";

    private Button playMp3, stopMp3;
    private pl.droidsonroids.gif.GifImageView casette;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.e("onAdClicked", "usuario_toco");
                ((ConfigurarModulo) mContext).agregaVidas(0, "vidaVideo");
                consultaAgregaVidas();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.e("onAdClosed", "usuario_toco");
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.e("onAdImpression", "usuario_toco");
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("onAdLoaded", "usuario_toco");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.e("onAdOpened", "usuario_toco");
                ((ConfigurarModulo) mContext).agregaVidas(0, "vidaVideo");
                consultaAgregaVidas();
            }
        });

        textViewVidas = (TextView) findViewById(R.id.textViewVidas);
        consultaAgregaVidas();

        playMp3 = (Button) findViewById(R.id.playMp3);
        stopMp3 = (Button) findViewById(R.id.stopMp3);
        casette = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.casette);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        ToolTip = (Button) findViewById(R.id.ToolTip);
        ToolTip.setOnClickListener(this);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        btnAyuda    = findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(this);

        btnNotas = findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(this);

        btnCalculadora= findViewById(R.id.btnCalculadora);
        btnCalculadora.setOnClickListener(this);

        btnContinuar= findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(this);

        tipoTest=getIntent().getExtras().getString("tipoTest");
        autoAyuda=getIntent().getExtras().getString("autoAyuda");
        numPreguntas=getIntent().getExtras().getString("preguntasTotal");
        random=getIntent().getExtras().getString("random");

        nPreguntas = Integer.parseInt(numPreguntas);

        titulomodulo = (TextView)findViewById(R.id.titulomodulo);
        pregunta = (EditText) findViewById(R.id.pregunta);
        pregunta.setFocusable(false);

        preguntaImagen = (EditText)findViewById(R.id.preguntaImagen);
        preguntaImagen.setFocusable(false);

        radioRespuestas = (RadioGroup) findViewById(R.id.radioRespuestas);
        radioRespuestas.clearCheck();

        r1=(RadioButton)findViewById(R.id.r1);
        r2=(RadioButton)findViewById(R.id.r2);
        r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);

        preguntasBloque = new String[nPreguntas];
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

        buscaOpciones();
        //infoTipoPago();
        mostrarInstrucciones();

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


        img_flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent linsertar=new Intent(Modulo.this, SeleccionaEscuela.class);
                startActivity(linsertar);
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiatePopupAyudaSinResp();
                ayudaSinRespuesta();
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

                if(vidasActuales<=0){

                    mostrarInfoVidas();

                }else{

                    if(respuestaRadio != null){
                        if(!respuestaRadio.equalsIgnoreCase("")){

                            consultaAgregaVidas();

                            if(preguntaNumero==11
                                    || preguntaNumero==20
                                    || preguntaNumero==30
                                    || preguntaNumero==40
                                    || preguntaNumero==50
                                    || preguntaNumero==60
                                    || preguntaNumero==70
                                    || preguntaNumero==80
                                    || preguntaNumero==90
                            ){
                                siguientePreguntaExamen();
                            }else if(preguntaNumero==5 || preguntaNumero==15
                                    || preguntaNumero==25
                                    || preguntaNumero==35
                                    || preguntaNumero==45
                                    || preguntaNumero==55
                                    || preguntaNumero==65
                                    || preguntaNumero==75
                                    || preguntaNumero==85
                                    || preguntaNumero==95

                            ){
                                siguientePreguntaExamen();
                            }else{
                                siguientePreguntaExamen();
                            }
                            /*}else{
                                showAlertDialogEliminarDatosConductores();
                            }*/

                        }else{
                            mesnaje("Choose an answer");
                        }
                    }
                }


            }
        });
    }

    /*private void miraUnVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }*/


    MediaPlayer mPlayer;
    public void reproduceMp3(String nombreAudio){
        int filePlay = getResources().getIdentifier(nombreAudio,
                "raw", getPackageName());
        mPlayer = MediaPlayer.create(Modulo.this, filePlay);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                casette.setVisibility(View.INVISIBLE);
            }
        });
        mPlayer.start();
        casette.setVisibility(View.VISIBLE);
    }

    public void reproduceMp3Tooltip(String nombreAudio){
        int filePlay = getResources().getIdentifier(nombreAudio,
                "raw", getPackageName());
        mPlayer = MediaPlayer.create(Modulo.this, filePlay);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                casetteTooltip.setVisibility(View.INVISIBLE);
            }
        });
        mPlayer.start();
        casetteTooltip.setVisibility(View.VISIBLE);
    }



    private void mostrarInfoVidas() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Modulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("juega_gana.json");

        builder.setMessage(getResources().getString(R.string.masPreguntasIlimitadas))
                .setTitle(" PRO");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("  Pro", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                regresaMenu();
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

    private void regresaMenu(){
        finish();
        Intent linsertar=new Intent(Modulo.this, SeleccionaEscuela.class);
        startActivity(linsertar);
    }

    private String currentDate = "";
    public final BaseDaoPassword conVidas = new BaseDaoPassword(this);
    int vidasActuales = 0;
    private void consultaAgregaVidas(){
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ArrayList<Vidas> listaVidas = (ArrayList<Vidas>) conVidas.consultaVidas(currentDate);
        Log.e("listaVidasAntes", listaVidas.size()+"");
        if(listaVidas.size()>0){
            vidasActuales = listaVidas.get(0).getNumeroVidas();
        }
        if(vidasActuales<=1){
            textViewVidas.setText(" X " + vidasActuales + "  ");
        }else{
            textViewVidas.setText(" X " + vidasActuales + "  ");
        }
    }

    public void validaVideoPreguntas(){

        if(preguntaNumero==8 || preguntaNumero==16
                || preguntaNumero==24 || preguntaNumero==32
                || preguntaNumero==40 || preguntaNumero==50){


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

                Activity activityContext = Modulo.this;
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

    public void siguientePreguntaExamen(){
        correctoIncorrecto = 0;
        if(correctaBloque[preguntaNumero].trim().equalsIgnoreCase(respuestaRadio.trim())){
            aciertos = aciertos + 1;
            correctoIncorrecto = 1;
            //initiatePopupAyuda();
            mostrarVideoComprarSaldo();
            //siguientePreguntaButton(respuestaRadio);
        }else{
            if(autoAyuda.equalsIgnoreCase("Si")){
                //initiatePopupAyuda();
                mostrarVideoComprarSaldo();
            }else{
                //Siguiente pregunta
                //siguientePreguntaButton(respuestaRadio);
                //initiatePopupAyuda();
                mostrarVideoComprarSaldo();
            }
        }
    }

    private void mostrarInstrucciones() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Modulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("tooltip.json");

        builder.setMessage(getResources().getString(R.string.instruccionesModulo))
                .setTitle("Diretions");
        builder.setCancelable(false);
        builder.setView(v);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Modulo.this);
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



    class ImportarCategoriasCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        // can use UI thread here

        protected void onPreExecute()

        {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
            this.dialog.setCancelable(false);
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {


            try {
                BaseDaoExamen conexion=new BaseDaoExamen(Modulo.this);

                String query = "";

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
                        nombreMatTit = "Writing and Language";

                    }
                    if(tipoTest.equalsIgnoreCase("Math")){

                        query = "select * from tblpreguntasmodulo where idmodulo = 16 LIMIT " + numPreguntas;
                        nombreMatTit = "Math";

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


                //   query = "select * from tblpreguntasmodulo";

                Log.e("query: ", query);

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
                    String audioPreg = elementos.getAudiopregunta().replace("-", "_");
                    String audioTool = elementos.getAudiotooltip().replace("-", "_");


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

                /*if(tipoTest.equalsIgnoreCase("all")){

                    consultarMateriasAestudiar();
                    titulomodulo.setText(nombreMateriaPropuesta[0]);
                    Log.e("nombreMateriaPro", nombreMateriaPropuesta[0]);
                }*/

    /*    if(!idModulo.equalsIgnoreCase("")){
            new ImportarCategoriasCSVTask().execute("");
        }*/



                r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            respuestaRadio = respuestaUnoBloque[preguntaNumero];
                        }
                    }
                });

                r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            //   Log.e("r2", r2.getText().toString());
                            //siguientePregunta(respuestaDosBloque[preguntaNumero]);
                            respuestaRadio = respuestaDosBloque[preguntaNumero];
                        }
                    }
                });

                r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            //  Log.e("r3", r3.getText().toString());
                            //siguientePregunta(respuestaTresBloque[preguntaNumero]);
                            respuestaRadio = respuestaTresBloque[preguntaNumero];
                        }
                    }
                });

                r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            // Log.e("r4", r4.getText().toString());
                            //  siguientePregunta(respuestaCuatroBloque[preguntaNumero]);
                            respuestaRadio = respuestaCuatroBloque[preguntaNumero];
                        }
                    }
                });




            } catch (Exception e) {
                e.printStackTrace();

                finish();
                Intent linsertar=new Intent(Modulo.this, SeleccionaEscuela.class);
                startActivity(linsertar);

            }



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

            if(success==true){
                titulomodulo.setText(nombreMatTit);
                verImagen();
            }else{
                mesnaje("Oops, algo salió mal, por favor, vuelve a intentarlo");
                finish();
                Intent linsertar=new Intent(Modulo.this, SeleccionaEscuela.class);
                startActivity(linsertar);
            }




        }
    }

    private void mesnaje(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    AlertDialog alertaGastos;

    private void infoTipoPago(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.version_pago, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setView(promptView);

        TextView textView30 = (TextView) promptView.findViewById(R.id.textView30);
        textView30.setText(getResources().getString(R.string.instruccionesModulo));

        Button buttonCerrar = (Button) promptView.findViewById(R.id.closepago);
        buttonCerrar.setOnClickListener(cerrarVentanaAyuda);

        Button comprafull = (Button) promptView.findViewById(R.id.comprafull);
        comprafull.setVisibility(View.GONE);

        Button yalocompre = (Button) promptView.findViewById(R.id.yalocompre);
        yalocompre.setVisibility(View.GONE);

        alertaGastos = alertDialogBuilder.show();
        alertaGastos.setCanceledOnTouchOutside(false);
        alertaGastos.setCancelable(false);

    }

    private View.OnClickListener cerrarVentanaAyuda = new View.OnClickListener() {
        public void onClick(View v) {

            alertaGastos.dismiss();
            new ImportarCategoriasCSVTask().execute("");

        }
    };

/*    public void consultarAdview(){

        BaseDaoPassword conexiones=new BaseDaoPassword(this);


        List listas = conexiones.ConsultarPttoDetalle(1);

        String estatus = "";


        for(Object datos: listas){
            PasswordDTo elementosIng=(PasswordDTo) datos;

            estatus = String.valueOf(elementosIng.getEstatus());


        }


        mAdView = (AdView) findViewById(R.id.adView3);
        if(listas.size()<=0){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else{
            mAdView.destroy();
            mAdView.setVisibility(View.GONE);

        }

    }*/

    public void vistas(){
        LinearLayout mRlayout = (LinearLayout) findViewById(R.id.preguntas);
        LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRparams.setMargins(8, 6, 8, 0);
        pregunta = new EditText(context);
        pregunta.setLayoutParams(mRparams);
        mRlayout.addView(pregunta);

        //pregunta = (EditText) findViewById(R.id.pregunta);
        pregunta.setFocusable(false);
        pregunta.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        pregunta.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);

        LinearLayout mRlayoutDos = (LinearLayout) findViewById(R.id.preguntas);
        LinearLayout.LayoutParams mRparamsDos = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRparamsDos.setMargins(8, 23, 8, 0);
        preguntaImagen = new EditText(context);
        preguntaImagen.setLayoutParams(mRparamsDos);
        mRlayoutDos.addView(preguntaImagen);

        //preguntaImagen = (EditText)findViewById(R.id.preguntaImagen);
        preguntaImagen.setFocusable(false);
        preguntaImagen.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        preguntaImagen.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
    }


    public void consultarMateriasAestudiar(){

        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listas = conexion.ConsultarExamenResultados();

        String[] conceptoIngreso= new String[listas.size()];

        List<String> tipo = new ArrayList<String>();

        int contenedor=0;
        List listas2 = null;

        String materiaTmp[]= new String[listas.size()];
        String materiaNombreTmp[]= new String[listas.size()];
        String conI[]= new String[listas.size()];
        int imaI[]= new int[listas.size()];
        int contador =0;

        nPreguntas=0;


        int numeroPregunta = 1;
        int contadorMateria = 0;

        for(Object datos: listas){

            ExamenResultadosDTO elementosIng=(ExamenResultadosDTO) datos;
            materiaTmp[contadorMateria] = elementosIng.getMateria();
            Log.e("materiasExamen", elementosIng.getMateria()+"");

            if(elementosIng.getMateria().equalsIgnoreCase("1")){
                materiaNombreTmp[contadorMateria] = "Español";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("2")){
                materiaNombreTmp[contadorMateria] = "Razonamiento verbal";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("3")){
                materiaNombreTmp[contadorMateria] = "Historia universal";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("4")){
                materiaNombreTmp[contadorMateria] = "Historia méxico";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("5")){
                materiaNombreTmp[contadorMateria] = "Geografía universal";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("6")){
                materiaNombreTmp[contadorMateria] = "Geografía de méxico";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("7")){
                materiaNombreTmp[contadorMateria] = "Civismo";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("8")){
                materiaNombreTmp[contadorMateria] = "Razonamiento matemático";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("9")){
                materiaNombreTmp[contadorMateria] = "Matemáticas";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("10")){
                materiaNombreTmp[contadorMateria] = "Física";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("11")){
                materiaNombreTmp[contadorMateria] = "Química";
            }

            if(elementosIng.getMateria().equalsIgnoreCase("12")){
                materiaNombreTmp[contadorMateria] = "Biología";
            }

            contadorMateria = contadorMateria + 1;
        }

        String[] materiasFinal = new HashSet<String>(Arrays.asList(materiaTmp)).toArray(new String[0]);
        String[] materiasNombreFinal = new HashSet<String>(Arrays.asList(materiaNombreTmp)).toArray(new String[0]);


        List listaMaterias;

        //preguntas por materias
        int numPregRecomendacionEspa = 0;
        int numPregRecomendacionRazVer = 0;
        int numPregRecomendacionHisUni = 0;
        int numPregRecomendacionHisMex = 0;
        int numPregRecomendacionGeoUniv = 0;
        int numPregRecomendacionGeoMex = 0;
        int numPregRecomendacionCivismo = 0;
        int numPregRecomendacionRazMate = 0;
        int numPregRecomendacionMate = 0;
        int numPregRecomendacionFisica = 0;
        int numPregRecomendacionQuimica = 0;
        int numPregRecomendacionBiologia = 0;

        for(int i=0; i<materiasFinal.length; i++){

            Log.e("materiasFinal", materiasFinal[i]+"");

            listaMaterias = conexion.ConsultarExamenResultadosPorMateria(materiasFinal[i]);

            String materiaLista = materiasFinal[i];
            int numAciertos = 0;
            String resultadoObtenido = "";
            int numPregRecomendacion = 0;
            String nombreMateria = "";
            String recomendacion = "";




            for(Object datos: listaMaterias){

                ExamenResultadosDTO elementosIng=(ExamenResultadosDTO) datos;

                double aciertos = elementosIng.getAciertos();
                numAciertos = numAciertos + (int)aciertos;

            }

            if(materiasFinal[i].equalsIgnoreCase("1")){
                nombreMateria = "Español";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionEspa= (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueEspa.length/2);
                    numPregRecomendacionEspa= (int)(preguntasBloqueEspa.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueEspa.length);
                    numPregRecomendacionEspa= (int)(preguntasBloqueEspa.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("2")){
                nombreMateria = "Razonamiento verbal";

                if(numAciertos==16){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionRazVer = (int)(0);
                }
                if(numAciertos>8 && numAciertos<16){
                    numPregRecomendacion = (int)(preguntasBloqueRazVerbal.length/2);
                    numPregRecomendacionRazVer = (int)(preguntasBloqueRazVerbal.length/2);
                }
                if(numAciertos<=8){
                    numPregRecomendacion = (int)(preguntasBloqueRazVerbal.length);
                    numPregRecomendacionRazVer = (int)(preguntasBloqueRazVerbal.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("3")){
                nombreMateria = "Historia universal";

                if(numAciertos==6){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionHisUni = (int)(0);
                }
                if(numAciertos>3 && numAciertos<6){
                    numPregRecomendacion = (int)(preguntasBloqueHisUniv.length/2);
                    numPregRecomendacionHisUni = (int)(preguntasBloqueHisUniv.length/2);
                }
                if(numAciertos<=3){
                    numPregRecomendacion = (int)(preguntasBloqueHisUniv.length);
                    numPregRecomendacionHisUni = (int)(preguntasBloqueHisUniv.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("4")){
                nombreMateria = "Historia méxico";

                if(numAciertos==6){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionHisMex = (int)(0);
                }
                if(numAciertos>3 && numAciertos<6){
                    numPregRecomendacion = (int)(preguntasBloqueHisMex.length/2);
                    numPregRecomendacionHisMex = (int)(preguntasBloqueHisMex.length/2);
                }
                if(numAciertos<=3){
                    numPregRecomendacion = (int)(preguntasBloqueHisMex.length);
                    numPregRecomendacionHisMex = (int)(preguntasBloqueHisMex.length);
                }

            }

            if(materiasFinal[i].equalsIgnoreCase("5")){
                nombreMateria = "Geografía universal";

                if(numAciertos==6){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionGeoUniv = (int)(0);
                }
                if(numAciertos>3 && numAciertos<6){
                    numPregRecomendacion = (int)(preguntasBloqueGeoUniv.length/2);
                    numPregRecomendacionGeoUniv = (int)(preguntasBloqueGeoUniv.length/2);
                }
                if(numAciertos<=3){
                    numPregRecomendacion = (int)(preguntasBloqueGeoUniv.length);
                    numPregRecomendacionGeoUniv = (int)(preguntasBloqueGeoUniv.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("6")){
                nombreMateria = "Geografía de méxico";

                if(numAciertos==6){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionGeoMex = (int)(0);
                }
                if(numAciertos>3 && numAciertos<6){
                    numPregRecomendacion = (int)(preguntasBloqueGeoMex.length/2);
                    numPregRecomendacionGeoMex = (int)(preguntasBloqueGeoMex.length/2);
                }
                if(numAciertos<=3){
                    numPregRecomendacion = (int)(preguntasBloqueGeoMex.length);
                    numPregRecomendacionGeoMex = (int)(preguntasBloqueGeoMex.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("7")){
                nombreMateria = "Civismo";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionCivismo = (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueCivismo.length/2);
                    numPregRecomendacionCivismo = (int)(preguntasBloqueCivismo.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueCivismo.length);
                    numPregRecomendacionCivismo = (int)(preguntasBloqueCivismo.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("8")){
                nombreMateria = "Razonamiento matemático";

                if(numAciertos==16){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionRazMate = (int)(0);
                }
                if(numAciertos>8 && numAciertos<16){
                    numPregRecomendacion = (int)(preguntasBloqueRazMate.length/2);
                    numPregRecomendacionRazMate = (int)(preguntasBloqueRazMate.length/2);
                }
                if(numAciertos<=8){
                    numPregRecomendacion = (int)(preguntasBloqueRazMate.length);
                    numPregRecomendacionRazMate = (int)(preguntasBloqueRazMate.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("9")){
                nombreMateria = "Matemáticas";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionMate = (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueMatematicas.length/2);
                    numPregRecomendacionMate = (int)(preguntasBloqueMatematicas.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueMatematicas.length);
                    numPregRecomendacionMate = (int)(preguntasBloqueMatematicas.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("10")){
                nombreMateria = "Física";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionFisica = (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueFisica.length/2);
                    numPregRecomendacionFisica = (int)(preguntasBloqueFisica.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueFisica.length);
                    numPregRecomendacionFisica = (int)(preguntasBloqueFisica.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("11")){
                nombreMateria = "Química";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionQuimica = (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueQuimica.length/2);
                    numPregRecomendacionQuimica = (int)(preguntasBloqueQuimica.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueQuimica.length);
                    numPregRecomendacionQuimica = (int)(preguntasBloqueQuimica.length);
                }
            }

            if(materiasFinal[i].equalsIgnoreCase("12")){
                nombreMateria = "Biología";

                if(numAciertos==12){
                    numPregRecomendacion = (int)(0);
                    numPregRecomendacionBiologia = (int)(0);
                }
                if(numAciertos>6 && numAciertos<12){
                    numPregRecomendacion = (int)(preguntasBloqueBiologia.length/2);
                    numPregRecomendacionBiologia = (int)(preguntasBloqueBiologia.length/2);
                }
                if(numAciertos<=6){
                    numPregRecomendacion = (int)(preguntasBloqueBiologia.length);
                    numPregRecomendacionBiologia = (int)(preguntasBloqueBiologia.length);
                }
            }

            nPreguntas = nPreguntas + numPregRecomendacion;


        }

        Log.e("nPreguntas", nPreguntas+"");

        Log.e("Espa", numPregRecomendacionEspa+"");
        Log.e("RazVer", numPregRecomendacionRazVer+"");
        Log.e("HisUni", numPregRecomendacionHisUni+"");
        Log.e("HisMex", numPregRecomendacionHisMex+"");
        Log.e("GeoUniv", numPregRecomendacionGeoUniv+"");
        Log.e("GeoMex", numPregRecomendacionGeoMex+"");
        Log.e("Civismo", numPregRecomendacionCivismo+"");
        Log.e("RazMate", numPregRecomendacionRazMate+"");
        Log.e("Mate", numPregRecomendacionMate+"");
        Log.e("Fisica", numPregRecomendacionFisica+"");
        Log.e("Quimica", numPregRecomendacionQuimica+"");
        Log.e("Biologia", numPregRecomendacionBiologia+"");

        preguntasBloque = new String[nPreguntas];
        respuestaUnoBloque = new String[nPreguntas];
        respuestaDosBloque = new String[nPreguntas];
        respuestaTresBloque = new String[nPreguntas];
        respuestaCuatroBloque = new String[nPreguntas];
        imagenPreguntaBloque = new String[nPreguntas];
        correctaBloque = new String[nPreguntas];
        tooltipBloque = new String[nPreguntas];
        imagenTooltipBloque = new String[nPreguntas];

        nombreMateriaPropuesta = new String[nPreguntas];

        int contadorMateriaPropuesta = 0;
        int contadorPreguntaBloque = 0;
        int contadorImagenPreguntaBloque = 0;
        int contadorRunoBloque = 0;
        int contadorRdosBloque = 0;
        int contadorRtresBloque = 0;
        int contadorRcuatroBloque = 0;
        int contadorCorrectaBloque = 0;
        int contadorTooltipBloque = 0;
        int contadorImagenTooltipBloque = 0;


    /*    if(tipoTest.equalsIgnoreCase("espan")){
            idModulo = "1";
            nombreMatTit = "Español";
            nombreAssests = "espanol";*/

            for (int i = 0; i < preguntasBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueEspa[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Español";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }


            for (int i = 0; i <  respuestaUnoBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueEspa[i];
                    contadorRunoBloque = contadorRunoBloque + 1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueEspa[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueEspa[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueEspa[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueEspa[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueEspa[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueEspa[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueEspa.length; i++) {
                if(i<numPregRecomendacionEspa){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueEspa[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }

     /*       verImagen();
        }*/


     /*   if(tipoTest.equalsIgnoreCase("razverbal")){
            idModulo = "2";
            nombreMatTit = "Razonamiento verbal";
            nombreAssests = "razonamientoverbal";*/

            for (int i = 0; i < preguntasBloqueRazVerbal.length; i++) {

                if(i<numPregRecomendacionRazVer){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueRazVerbal[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Razonamiento verbal";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueRazVerbal[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueRazVerbal[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueRazVerbal[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueRazVerbal[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueRazVerbal[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueRazVerbal[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueRazVerbal[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueRazVerbal.length; i++) {
                if(i<numPregRecomendacionRazVer){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueRazVerbal[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();
        }*/

     /*   if(tipoTest.equalsIgnoreCase("hisuniv")){
            idModulo = "3";
            nombreMatTit = "Historia universal";
            nombreAssests = "historiauniversal";*/

            for (int i = 0; i < preguntasBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueHisUniv[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Historia universal";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueHisUniv[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueHisUniv[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueHisUniv[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueHisUniv[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueHisUniv[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueHisUniv[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueHisUniv[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueHisUniv.length; i++) {
                if(i<numPregRecomendacionHisUni){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueHisUniv[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();

        }*/

     /*   if(tipoTest.equalsIgnoreCase("hismex")){
            idModulo = "4";
            nombreMatTit = "Historia de México";
            nombreAssests = "historiademexico";*/

            for (int i = 0; i < preguntasBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueHisMex[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Historia de México";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueHisMex[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueHisMex[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueHisMex[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueHisMex[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueHisMex[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueHisMex[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueHisMex[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueHisMex.length; i++) {
                if(i<numPregRecomendacionHisMex){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueHisMex[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;

                }
            }
       /*     verImagen();

        }*/

       /* if(tipoTest.equalsIgnoreCase("geouniv")){
            idModulo = "5";
            nombreMatTit = "Geografía universal";
            nombreAssests = "geouniversal";*/

            for (int i = 0; i < preguntasBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueGeoUniv[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Geografía universal";
                    contadorMateriaPropuesta = contadorMateriaPropuesta + 1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueGeoUniv[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueGeoUniv[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueGeoUniv[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueGeoUniv[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueGeoUniv[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueGeoUniv[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueGeoUniv[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueGeoUniv.length; i++) {
                if(i<numPregRecomendacionGeoUniv){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueGeoUniv[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();

        }*/


     /*   if(tipoTest.equalsIgnoreCase("geomex")){
            idModulo = "6";
            nombreMatTit = "Geografía de México";
            nombreAssests = "geodemexico";*/

            for (int i = 0; i < preguntasBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueGeoMex[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Geografía de México";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueGeoMex[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueGeoMex[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueGeoMex[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueGeoMex[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueGeoMex[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueGeoMex[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueGeoMex[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueGeoMex.length; i++) {
                if(i<numPregRecomendacionGeoMex){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueGeoMex[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();

        }*/

     /*   if(tipoTest.equalsIgnoreCase("civis")){
            idModulo = "7";
            nombreMatTit = "Civismo";
            nombreAssests = "civismo";*/

            for (int i = 0; i < preguntasBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueCivismo[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Civismo";
                    contadorMateriaPropuesta = contadorMateriaPropuesta + 1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueCivismo[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueCivismo[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueCivismo[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueCivismo[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueCivismo[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueCivismo[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueCivismo[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueCivismo.length; i++) {
                if(i<numPregRecomendacionCivismo){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueCivismo[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
      /*      verImagen();

        }*/

      /*  if(tipoTest.equalsIgnoreCase("razmate")){
            idModulo = "8";
            nombreMatTit = "Razonamiento matemático";
            nombreAssests = "razonamientomatematico";*/

            for (int i = 0; i < preguntasBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueRazMate[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Razonamiento matemático";
                    contadorMateriaPropuesta = contadorMateriaPropuesta + 1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueRazMate[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueRazMate[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueRazMate[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueRazMate[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueRazMate[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueRazMate[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueRazMate[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueRazMate.length; i++) {
                if(i<numPregRecomendacionRazMate){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueRazMate[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();

        }*/

      /*  if(tipoTest.equalsIgnoreCase("mate")){
            idModulo = "9";
            nombreMatTit = "Matemáticas";
            nombreAssests = "matematicas";*/

            for (int i = 0; i < preguntasBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueMatematicas[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Matemáticas";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueMatematicas[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueMatematicas[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueMatematicas[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueMatematicas[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueMatematicas[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueMatematicas[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueMatematicas[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueMatematicas.length; i++) {
                if(i<numPregRecomendacionMate){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueMatematicas[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       /*     verImagen();

        }*/
       /* if(tipoTest.equalsIgnoreCase("fisic")){
            idModulo = "10";
            nombreMatTit = "Física";
            nombreAssests = "fisica";*/

            for (int i = 0; i < preguntasBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueFisica[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Física";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueFisica[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueFisica[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueFisica[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueFisica[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueFisica[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueFisica[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueFisica[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueFisica.length; i++) {
                if(i<numPregRecomendacionFisica){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueFisica[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
      /*      verImagen();
        }*/

      /*  if(tipoTest.equalsIgnoreCase("quimi")){
            idModulo = "11";
            nombreMatTit = "Química";
            nombreAssests = "quimica";*/

            for (int i = 0; i < preguntasBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueQuimica[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Química";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueQuimica[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueQuimica[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueQuimica[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueQuimica[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueQuimica[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueQuimica[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueQuimica[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueQuimica.length; i++) {
                if(i<numPregRecomendacionQuimica){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueQuimica[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
       //     verImagen();

       // }
       // if(tipoTest.equalsIgnoreCase("biolo")){
       //     idModulo = "12";
       //     nombreMatTit = "Biología";
       //     nombreAssests = "bilogia";

            for (int i = 0; i < preguntasBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    preguntasBloque[contadorPreguntaBloque] = preguntasBloqueBiologia[i];
                    nombreMateriaPropuesta[contadorMateriaPropuesta] = "Biología";
                    contadorMateriaPropuesta = contadorMateriaPropuesta +1;
                    contadorPreguntaBloque = contadorPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < respuestaUnoBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    respuestaUnoBloque[contadorRunoBloque] = respuestaUnoBloqueBiologia[i];
                    contadorRunoBloque = contadorRunoBloque +1;
                }
            }

            for (int i = 0; i < respuestaDosBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    respuestaDosBloque[contadorRdosBloque] = respuestaDosBloqueBiologia[i];
                    contadorRdosBloque = contadorRdosBloque + 1;
                }
            }

            for (int i = 0; i < respuestaTresBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    respuestaTresBloque[contadorRtresBloque] = respuestaTresBloqueBiologia[i];
                    contadorRtresBloque = contadorRtresBloque +1 ;
                }
            }

            for (int i = 0; i < respuestaCuatroBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    respuestaCuatroBloque[contadorRcuatroBloque] = respuestaCuatroBloqueBiologia[i];
                    contadorRcuatroBloque = contadorRcuatroBloque + 1;
                }
            }

            for (int i = 0; i < imagenPreguntaBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    imagenPreguntaBloque[contadorImagenPreguntaBloque] = imagenPreguntaBloqueBiologia[i];
                    contadorImagenPreguntaBloque = contadorImagenPreguntaBloque + 1;
                }
            }

            for (int i = 0; i < correctaBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    correctaBloque[contadorCorrectaBloque] = correctaBloqueBiologia[i];
                    contadorCorrectaBloque = contadorCorrectaBloque + 1;
                }
            }

            for (int i = 0; i < tooltipBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    tooltipBloque[contadorTooltipBloque] = tooltipBloqueBiologia[i];
                    contadorTooltipBloque = contadorTooltipBloque + 1;
                }
            }

            for (int i = 0; i < imagenTooltipBloqueBiologia.length; i++) {
                if(i<numPregRecomendacionBiologia){
                    imagenTooltipBloque[contadorImagenTooltipBloque] = imagenTooltipBloqueBiologia[i];
                    contadorImagenTooltipBloque = contadorImagenTooltipBloque + 1;
                }
            }
        /*    verImagen();

        }*/

        int contadorRespuesta =1;
        int contadorCorrectas = 1;

        Log.e("Correcta Total", correctaBloque.length+"");

        for(int i=0; i<correctaBloque.length; i++){
            if(correctaBloque[i].trim().equalsIgnoreCase(respuestaUnoBloque[i].trim())){
                Log.e("Correcta ", correctaBloque[i]);
                Log.e("respuestaUnoBloque ", respuestaUnoBloque[i]);
                Log.e("contadorRespuesta", contadorRespuesta+"");
                contadorCorrectas = contadorCorrectas +1;
            }
            if(correctaBloque[i].trim().equalsIgnoreCase(respuestaDosBloque[i].trim())){
                Log.e("Correcta ", correctaBloque[i]);
                Log.e("respuestaDosBloque ", respuestaDosBloque[i]);
                Log.e("contadorRespuesta", contadorRespuesta+"");
                contadorCorrectas = contadorCorrectas +1;
            }
            if(correctaBloque[i].trim().equalsIgnoreCase(respuestaTresBloque[i].trim())){
                Log.e("Correcta ", correctaBloque[i]);
                Log.e("respuestaTresBloque ", respuestaTresBloque[i]);
                Log.e("contadorRespuesta", contadorRespuesta+"");
                contadorCorrectas = contadorCorrectas +1;
            }
            if(correctaBloque[i].trim().equalsIgnoreCase(respuestaCuatroBloque[i].trim())){
                Log.e("Correcta ", correctaBloque[i]);
                Log.e("respuestaCuatroBloque ", respuestaCuatroBloque[i]);
                Log.e("contadorRespuesta", contadorRespuesta+"");
                contadorCorrectas = contadorCorrectas +1;
            }
            contadorRespuesta = contadorRespuesta + 1;

        }

        Log.e("contadorRespuesta", contadorRespuesta+"");

        tipoTest = "Plan completo";

        /*Log.e("nombreMateriaPropuesta", nombreMateriaPropuesta.length+"");

        for(int i=0; i<nombreMateriaPropuesta.length; i++){
            Log.d("nombreMateriaPropuesta", nombreMateriaPropuesta[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<preguntasBloque.length; i++){
            Log.d("preguntasBloque", preguntasBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<respuestaUnoBloque.length; i++){
            Log.d("respuestaUnoBloque", respuestaUnoBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<respuestaDosBloque.length; i++){
            Log.d("respuestaDosBloque", respuestaDosBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<respuestaTresBloque.length; i++){
            Log.d("respuestaTresBloque", respuestaTresBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<respuestaCuatroBloque.length; i++){
            Log.d("respuestaCuatroBloque", respuestaCuatroBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<imagenPreguntaBloque.length; i++){
            Log.d("imagenPreguntaBloque", imagenPreguntaBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<correctaBloque.length; i++){
            Log.d("correctaBloque", correctaBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<tooltipBloque.length; i++){
            Log.d("tooltipBloque", tooltipBloque[i]);
            Log.d("contador", i+"");
        }

        for(int i=0; i<imagenTooltipBloque.length; i++){
            Log.d("imagenTooltipBloque", imagenTooltipBloque[i]);
            Log.d("contador", i+"");
        }*/



        verImagen();

    }

    public void resultados(){

            String palomitas = aciertos+"";
            String totalPreg = preguntasBloque.length+"";

            finish();
            Intent linsertar=new Intent(this, Resultados.class);
            linsertar.putExtra("aciertos", palomitas);
            linsertar.putExtra("preguntas", totalPreg);
            linsertar.putExtra("modulo", nombreMatTit);
            startActivity(linsertar);

    }

    public void verImagen() {

        boolean siEsImagen = false;

        if(imagenPreguntaBloque[0] != null){
            if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                siEsImagen = true;
            }
        }


        if(siEsImagen==true){

            // Image url
      //    String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenPreguntaBloque[0];

            // Create an object for subclass of AsyncTask
      //      GetXMLTask task = new GetXMLTask();
            // Execute the task
      //      task.execute(new String[] { image_url });




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
            //pregunta.setBackgroundDrawable(preguntaConImagen);


          //  pregunta.setCompoundDrawablesRelativeWithIntrinsicBounds();
            pregunta.setBackgroundDrawable(getResources().getDrawable(resID));
            //pregunta.setBackground(getResources().getDrawable(resID));

            preguntaImagen.setText(preguntasBloque[0]);
            preguntaImagen.setVisibility(View.VISIBLE);
            pregunta.setText("");



        }else{

            pregunta.setText("\n" + preguntasBloque[0] + "\n ");

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregunta.setLayoutParams(textParam);
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
                    if(respuestaTresBloque[0].equalsIgnoreCase(arrayimagenes[i])){

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
                r1.setText("a) " + (respuestaUnoBloque[0]));
                r2.setText("b) " + (respuestaDosBloque[0]));
                r3.setText("c) " + (respuestaTresBloque[0]));
                r4.setText("d) " + (respuestaCuatroBloque[0]));
            }

        }


    }


    //Siguiente pregunta button next

    public void siguientePreguntaButton(String radio){

        String respCorrecta = "";
        respCorrecta = radio;

        pregunta.setText("");
        pregunta.setCompoundDrawables(null, null, null, null);
        preguntaImagen.setText("");
        preguntaImagen.setCompoundDrawables(null, null, null, null);
        preguntaImagen.setVisibility(View.INVISIBLE);
        pregunta.setBackgroundDrawable(this.getResources().getDrawable(R.mipmap.pizzarra_ahorcado));
        //pregunta.setBackgroundColor(this.getResources().getColor(R.color.blackboard));


        preguntaNext = preguntaNext + 1;

        if(tipoTest.equalsIgnoreCase("all")){
            titulomodulo.setText(nombreMateriaPropuesta[preguntaNext]);

            /*BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);

            List lista = conexion.ConsultarPlanEstudios();
            int listaPlan = lista.size();

            if(listaPlan>0){

                List listaPlanExis = conexion.ConsultarPlanEstudiosPorId(preguntaNext);
                if(listaPlanExis.size()>0){
                    PlanEstudiosDTO datos = new PlanEstudiosDTO();
                    datos.setId(preguntaNext);
                    datos.setMateria(nombreMateriaPropuesta[preguntaNext]);
                    datos.setNopreguntas(preguntaNext);
                    datos.setAciertosobtenidos(correctoIncorrecto);

                    conexion.ActualizaPlanEstudios(datos);
                }else{
                    PlanEstudiosDTO datos = new PlanEstudiosDTO();
                    datos.setMateria(nombreMateriaPropuesta[preguntaNext]);
                    datos.setNopreguntas(preguntaNext);
                    datos.setAciertosobtenidos(correctoIncorrecto);

                    conexion.insertaPlanEstudios(datos);
                }

            }else{
                PlanEstudiosDTO datos = new PlanEstudiosDTO();
                datos.setMateria(nombreMateriaPropuesta[preguntaNext]);
                datos.setNopreguntas(preguntaNext);
                datos.setAciertosobtenidos(correctoIncorrecto);

                conexion.insertaPlanEstudios(datos);
            }*/

        }


        if(preguntaNext == nPreguntas) {
            resultados();
        }


        //    aciertos = aciertos + 1;
         //   preguntaNext = preguntaNext + 1;

            if(preguntaNumero < (correctaBloque.length - 1)) {
                preguntaNumero = preguntaNumero + 1;

                ///radios y preguntas con imagen
                boolean siEsImagen = false;

           //     Log.e("imagenPreguntaBloque", imagenPreguntaBloque[preguntaNumero]);

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
                        if(imagenPreguntaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i].trim())){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

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

                pregunta.setText("\n " + preguntasBloque[preguntaNumero] + "\n ");
                pregunta.setCompoundDrawables(null, null, null, null);
                preguntaImagen.setText("");
                pregunta.setEnabled(false);
            }

            if(respuestaUnoBloque[preguntaNumero] != null){
                if(respuestaUnoBloque[preguntaNumero].contains(".png") ||
                        respuestaUnoBloque[preguntaNumero].contains(".PNG")){

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
                    //   }
                    //   if(contRadio==3){
                    //r3.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));



                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(respuestaTresBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    r3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID),null,null,null);
                    //   }
                    //   if(contRadio==4){
                    //r4.setBackgroundDrawable(getResources().getDrawable(R.drawable.aciertos));

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

            radioRespuestas.clearCheck();

        }


        respuestaRadio = "";

    }

    android.app.AlertDialog dialogConRespuesta;
    Button playMp3Tooltip, stopMp3Tooltip;
    pl.droidsonroids.gif.GifImageView casetteTooltip;
    private void mostrarVideoComprarSaldo() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Modulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_game_over, null);
        // Obtenemos las referencias a los View components de nuestro layout
        txtMensajeAyuda = (EditText) v.findViewById(R.id.ayudatooltip);
        ayudaImagentooltip = (EditText) v.findViewById(R.id.ayudaImagentooltip);
        respCorrectaToolTip = (TextView) v.findViewById(R.id.respCorrecta);

        playMp3Tooltip = (Button) v.findViewById(R.id.playMp3Tooltip);
        stopMp3Tooltip = (Button) v.findViewById(R.id.stopMp3Tooltip);
        casetteTooltip = (pl.droidsonroids.gif.GifImageView) v.findViewById(R.id.casetteTooltip);

        stopMp3Tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                casetteTooltip.setVisibility(View.INVISIBLE);
            }
        });

        playMp3Tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproduceMp3Tooltip(audioTooltip[preguntaNumero].replace(".mp3", ""));
            }
        });

        TextView textView14 =  (TextView) v.findViewById(R.id.textView14);
        txtMensajeAyuda.setFocusable(false);
        ayudaImagentooltip.setFocusable(false);
        Button btnaceptarAyuda = (Button) v.findViewById(R.id.btnCerrarTooltip);
        btnaceptarAyuda.setOnClickListener(cerrar_ayuda_con_r);


        String mensajeCorrectaIncorrecta = "";
        boolean respuestaCorrecta = false;

        if(correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio)){
            respuestaCorrecta = true;
            textView14.setText("Good!");
            mensajeCorrectaIncorrecta = "Good!";
            respCorrectaToolTip.setText("");
          /*  respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.an_ic_check_on, 0, 0, 0);*/
            String varLabel = "";
            if(aciertos==1){
                varLabel = "";
            }
            if(aciertos==0){
                aciertos = aciertos + 1;
            }
            txtMensajeAyuda.setText(aciertos + " " + varLabel );
            txtMensajeAyuda.setVisibility(View.INVISIBLE);

        }
        if(!correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio) && autoAyuda.equalsIgnoreCase("No")){
            respuestaCorrecta = false;
            mensajeCorrectaIncorrecta = "Failed!";
            textView14.setText("Failed!");
            respCorrectaToolTip.setText("");
            ((ConfigurarModulo) mContext).agregaVidas(0, "restaVidas");
            /*respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.an_ic_check_off, 0, 0, 0);*/
            txtMensajeAyuda.setText("Review your knowledge! \n\n\n");
            txtMensajeAyuda.setVisibility(View.VISIBLE );

        }
        if(!correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio) &&
                autoAyuda.equalsIgnoreCase("Si")){

            respuestaCorrecta = false;
            mensajeCorrectaIncorrecta = "Failed!";
            textView14.setText("Failed!");
            respCorrectaToolTip.setText("");
            ((ConfigurarModulo) mContext).agregaVidas(0, "restaVidas");
            /*respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.an_ic_check_off, 0, 0, 0);*/
            txtMensajeAyuda.setText("Review your knowledge! \n\n\n");
            txtMensajeAyuda.setVisibility(View.VISIBLE );

            boolean siEsImagen = false;
            conCorrectaTool =0;
            conPagTool = 0;


            if(imagenTooltipBloque[preguntaNumero] != null){
                if (!imagenTooltipBloque[preguntaNumero].equalsIgnoreCase("")){
                    siEsImagen = true;
                    tooltipImagen = 1;
                }
            }

            if(siEsImagen==true){
                // Loader image - will be shown before loading image
                //txtMensajeAyuda.setText("");
                ayudaImagentooltip.setVisibility(View.VISIBLE);
                txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);
                txtMensajeAyuda.setVisibility(View.VISIBLE );

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
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    ayudaImagentooltip.setLayoutParams(textParam);

                    ayudaImagentooltip.setBackgroundDrawable(getResources().getDrawable(resID));

                }


            }else{

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                txtMensajeAyuda.setLayoutParams(textParam);

                txtMensajeAyuda.setText("\nExplain \n\n" + tooltipBloque[preguntaNumero]);
            }


            if(correctaBloque[preguntaNumero] != null){
                if(correctaBloque[preguntaNumero].contains(".png") || correctaBloque[preguntaNumero].contains(".PNG")){

                    conCorrectaTool = 1;

                    String mDrawableName = "";
                    int resID = 0;

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(correctaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }
                    respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(resID), null);
                    respCorrectaToolTip.setText("Correct anwer: ");


                }else{
                    respCorrectaToolTip.setText("Correct anwer: " + correctaBloque[preguntaNumero]);
                }
            }



        }

        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);

        if(respuestaCorrecta){
            gameOverAnimation.setAnimation("resp_correcta_dos.json");
        }else{
            gameOverAnimation.setAnimation("resp_incorrecta_dos.json");
        }


        builder.setMessage("")
                .setTitle("Tooltip");
        builder.setCancelable(false);
        builder.setView(v);

        /*builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                siguientePreguntaButton(respuestaRadio);

            }
        });*/

        /*builder.setNegativeButton("Salir", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                salirPatos();
                //compraMasSaldo();
            }
        });*/



        dialogConRespuesta = builder.create();
        dialogConRespuesta.setCancelable(false);
        dialogConRespuesta.setCanceledOnTouchOutside(false);
        dialogConRespuesta.show();
    }

    private View.OnClickListener cerrar_ayuda_con_r = new View.OnClickListener() {
        public void onClick(View v) {

            if (dialogConRespuesta != null && dialogConRespuesta.isShowing()) {
                dialogConRespuesta.dismiss();
                if(correctaBloque[preguntaNumero].trim().equalsIgnoreCase(respuestaRadio.trim())){
                    if(aciertos < nPreguntas ){
                        aciertos = aciertos + 1;
                        correctoIncorrecto = 1;
                    }
                }
                siguientePreguntaButton(respuestaRadio);
            }
        }
    };

    //Fin siguiente pregunta button next

    EditText ayudaImagentooltip;

    private void initiatePopupAyuda() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Modulo.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.tooltip,
                    (ViewGroup) findViewById(R.id.help_popup));


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setView(layout);

            Button btnaceptarAyuda = (Button) layout.findViewById(R.id.btnCerrarTooltip);
            btnaceptarAyuda.setOnClickListener(cerrar_ayuda);

            txtMensajeAyuda = (EditText) layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = (EditText) layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = (TextView) layout.findViewById(R.id.respCorrecta);
            TextView textView14 =  (TextView) layout.findViewById(R.id.textView14);
            txtMensajeAyuda.setFocusable(false);
            ayudaImagentooltip.setFocusable(false);

            if(correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio)){
                textView14.setText("Correct!");
                respCorrectaToolTip.setText("");
                respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.an_ic_check_on, 0, 0, 0);
                String varLabel = "";
                if(aciertos==1){
                    varLabel = "";
                }
                txtMensajeAyuda.setText(aciertos + " " + varLabel + " \n");

            }
            if(!correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio) && autoAyuda.equalsIgnoreCase("No")){
                textView14.setText("Failed!");
                respCorrectaToolTip.setText("");
                respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.an_ic_check_off, 0, 0, 0);
                txtMensajeAyuda.setText("Review your knowledge! \n\n\n");

            }
            if(!correctaBloque[preguntaNumero].equalsIgnoreCase(respuestaRadio) && autoAyuda.equalsIgnoreCase("Si")){
                boolean siEsImagen = false;
                conCorrectaTool =0;
                conPagTool = 0;


                if(imagenTooltipBloque[preguntaNumero] != null){
                    if (!imagenTooltipBloque[preguntaNumero].equalsIgnoreCase("")){
                        siEsImagen = true;
                        tooltipImagen = 1;
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
                                resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                            }
                        }

                        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                        ayudaImagentooltip.setLayoutParams(textParam);

                        ayudaImagentooltip.setBackgroundDrawable(getResources().getDrawable(resID));

                    }


                }else{

                    LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    txtMensajeAyuda.setLayoutParams(textParam);

                    txtMensajeAyuda.setText("\nExplain \n\n" + tooltipBloque[preguntaNumero]);
                }


                if(correctaBloque[preguntaNumero] != null){
                    if(correctaBloque[preguntaNumero].contains(".png") || correctaBloque[preguntaNumero].contains(".PNG")){

                        conCorrectaTool = 1;

                        String mDrawableName = "";
                        int resID = 0;

                        for(int i=0; i<arrayimagenes.length; i ++){
                            if(correctaBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                                mDrawableName = nombreImagenPrimerCarga[i]+"";
                                resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                            }
                        }
                        respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(resID), null);
                        respCorrectaToolTip.setText("Correct anwer: ");

                    }else{
                        respCorrectaToolTip.setText("Correct anwer: " + correctaBloque[preguntaNumero]);
                    }
                }



            }

            alertaAyuda = alertDialogBuilder.show();
            alertaAyuda.setCanceledOnTouchOutside(false);
            alertaAyuda.setCancelable(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cerrar_ayuda = new View.OnClickListener() {
        public void onClick(View v) {


            alertaAyuda.dismiss();
            alertaAyuda.dismiss();
            siguientePreguntaButton(respuestaRadio);
            //vistas();
            //preguntaNext = preguntaNext + 1;

     /*       if(preguntaNext == nPreguntas) {
                resultados();
            }*/
        }
    };


    android.app.AlertDialog dialogSinResp;
    private void ayudaSinRespuesta() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Modulo.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_game_over, null);
        // Obtenemos las referencias a los View components de nuestro layout
        txtMensajeAyuda = (EditText) v.findViewById(R.id.ayudatooltip);
        ayudaImagentooltip = (EditText) v.findViewById(R.id.ayudaImagentooltip);
        respCorrectaToolTip = (TextView) v.findViewById(R.id.respCorrecta);
        TextView textView14 =  (TextView) v.findViewById(R.id.textView14);
        txtMensajeAyuda.setFocusable(false);
        ayudaImagentooltip.setFocusable(false);
        Button btnaceptarAyuda = (Button) v.findViewById(R.id.btnCerrarTooltip);
        btnaceptarAyuda.setOnClickListener(cerrar_ayuda_sin_r);

        playMp3Tooltip = (Button) v.findViewById(R.id.playMp3Tooltip);
        stopMp3Tooltip = (Button) v.findViewById(R.id.stopMp3Tooltip);
        casetteTooltip = (pl.droidsonroids.gif.GifImageView) v.findViewById(R.id.casetteTooltip);

        stopMp3Tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                casetteTooltip.setVisibility(View.INVISIBLE);
            }
        });

        playMp3Tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproduceMp3Tooltip(audioTooltip[preguntaNumero].replace(".mp3", ""));
            }
        });

        respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);

        boolean siEsImagen = false;

        if(imagenTooltipBloque[preguntaNumero] != null){
            if (!imagenTooltipBloque[preguntaNumero].equalsIgnoreCase("")){
                siEsImagen = true;
                tooltipImagen = 1;
            }
        }

        if(siEsImagen==true){
            // Loader image - will be shown before loading image

            //txtMensajeAyuda.setText("");
            ayudaImagentooltip.setVisibility(View.VISIBLE);
            txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);

                /*String nombreFinalGif = "";
                String[] nombreGif = imagenTooltipBloque[preguntaNumero].split("\\.");
                nombreFinalGif = "file:///android_asset/" + nombreGif[0] + ".gif";

                InputStream stream = null;
                try {
                    stream = getAssets().open(nombreGif[0] + ".gif");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GifWebView view = null;
                view = new GifWebView(this, nombreFinalGif);

                if(stream!=null){
                    gifLinearLayout.addView(view);
                    //setContentView(view);
                    gifLinearLayout.setVisibility(View.VISIBLE);
                }else{
                    gifLinearLayout.setVisibility(View.GONE);
                }*/


            //Fin gift

            if(imagenTooltipBloque[preguntaNumero].contains(".png") || imagenTooltipBloque[preguntaNumero].contains(".PNG")){

                String mDrawableName = "";
                int resID = 0;

                for(int i=0; i<arrayimagenes.length; i ++){
                    if(imagenTooltipBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                        mDrawableName = nombreImagenPrimerCarga[i]+"";
                        resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                    }
                }

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                ayudaImagentooltip.setLayoutParams(textParam);

                ayudaImagentooltip.setBackgroundDrawable(getResources().getDrawable(resID));

            }


        }else{

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            txtMensajeAyuda.setLayoutParams(textParam);

            txtMensajeAyuda.setText("\n" + tooltipBloque[preguntaNumero]);
        }
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("tooltip_sin_respuesta.json");


        builder.setMessage("")
                .setTitle("Tooltip");
        builder.setCancelable(false);
        builder.setView(v);

        /*builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });*/

        /*builder.setNegativeButton("Salir", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                salirPatos();
                //compraMasSaldo();
            }
        });*/



        dialogSinResp = builder.create();
        dialogSinResp.setCancelable(false);
        dialogSinResp.setCanceledOnTouchOutside(false);
        dialogSinResp.show();
    }

    private View.OnClickListener cerrar_ayuda_sin_r = new View.OnClickListener() {
        public void onClick(View v) {

            dialogSinResp.dismiss();
        }
    };

    private LinearLayout gifLinearLayout = null;
    private Button showGifOneButton;
    private GifImageView gifImageView;
    private void initiatePopupAyudaSinResp() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Modulo.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.tooltip,
                    (ViewGroup) findViewById(R.id.help_popup));

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setView(layout);

            Button btnaceptarAyuda = (Button) layout.findViewById(R.id.btnCerrarTooltip);
            btnaceptarAyuda.setOnClickListener(cerrar_ayuda_sin_resp);

            txtMensajeAyuda = layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = (EditText) layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = layout.findViewById(R.id.respCorrecta);
            txtMensajeAyuda.setFocusable(false);
            ayudaImagentooltip.setFocusable(false);

            respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


            txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);

            boolean siEsImagen = false;


            if(imagenTooltipBloque[preguntaNumero] != null){
                if (!imagenTooltipBloque[preguntaNumero].equalsIgnoreCase("")){
                    siEsImagen = true;
                    tooltipImagen = 1;
                }
            }


            if(siEsImagen==true){
                // Loader image - will be shown before loading image

                //txtMensajeAyuda.setText("");
                ayudaImagentooltip.setVisibility(View.VISIBLE);
                txtMensajeAyuda.setText("\n\n" + tooltipBloque[preguntaNumero]);


                gifLinearLayout = layout.findViewById(R.id.gifLinearLayout);

                /*String nombreFinalGif = "";
                String[] nombreGif = imagenTooltipBloque[preguntaNumero].split("\\.");
                nombreFinalGif = "file:///android_asset/" + nombreGif[0] + ".gif";

                InputStream stream = null;
                try {
                    stream = getAssets().open(nombreGif[0] + ".gif");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GifWebView view = null;
                view = new GifWebView(this, nombreFinalGif);

                if(stream!=null){
                    gifLinearLayout.addView(view);
                    //setContentView(view);
                    gifLinearLayout.setVisibility(View.VISIBLE);
                }else{
                    gifLinearLayout.setVisibility(View.GONE);
                }*/


                //Fin gift

                if(imagenTooltipBloque[preguntaNumero].contains(".png") || imagenTooltipBloque[preguntaNumero].contains(".PNG")){

                    String mDrawableName = "";
                    int resID = 0;

                    for(int i=0; i<arrayimagenes.length; i ++){
                        if(imagenTooltipBloque[preguntaNumero].equalsIgnoreCase(arrayimagenes[i])){

                            mDrawableName = nombreImagenPrimerCarga[i]+"";
                            resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                        }
                    }

                    LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    ayudaImagentooltip.setLayoutParams(textParam);

                    ayudaImagentooltip.setBackgroundDrawable(getResources().getDrawable(resID));

                }


            }else{

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                txtMensajeAyuda.setLayoutParams(textParam);

                txtMensajeAyuda.setText("\n" + tooltipBloque[preguntaNumero]);
            }


            alertaAyuda = alertDialogBuilder.show();
            alertaAyuda.setCanceledOnTouchOutside(false);
            alertaAyuda.setCancelable(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cerrar_ayuda_sin_resp = new View.OnClickListener() {
        public void onClick(View v) {

            alertaAyuda.dismiss();
        }
    };


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

                map = downloadImage(url);
                contRadio = contRadio + 1;

                map = downloadImage(url);

                if(contRadio==1){
                    radioImageUno = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 200, true));
                }
                if(contRadio==2){
                    radioImageDos = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 200, true));
                }
                if(contRadio==3){
                    radioImageTres = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 200, true));
                }
                if(contRadio==4){
                    radioImageCuatro = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(map, 300, 200, true));
                }

            }
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            r1.setCompoundDrawablesWithIntrinsicBounds(radioImageUno,null,null,null);
            r2.setCompoundDrawablesWithIntrinsicBounds(radioImageDos,null,null,null);
            r3.setCompoundDrawablesWithIntrinsicBounds(radioImageTres,null,null,null);
            r4.setCompoundDrawablesWithIntrinsicBounds(radioImageCuatro,null,null,null);

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

    //FIN IMPORTAR CATEGORIAS
    BitmapDrawable tooltip;
    BitmapDrawable correctaRespTooltip;
    BitmapDrawable resizedBitmap;
    int contadorAyuda;


    private class ImagenTooltip extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmapTool = null;
            contadorAyuda =0;

            for (String url : urls) {
                bitmapTool = downloadImage(url);

                //inicia calculo de tamaño
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                Bitmap bitmapOrg = new BitmapDrawable(getResources(), bitmapTool).getBitmap();

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

                //tooltip = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapTool, 300, 650, true));
                tooltip = new BitmapDrawable(getResources(), resizedBitmap);

            }
            return bitmapTool;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            // image.setImageBitmap(result);


            txtMensajeAyuda.setBackgroundDrawable(tooltip);


        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmapTool = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmapTool = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmapTool;
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

    private class ImagenCorrecta extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmapTool = null;

            for (String url : urls) {
                bitmapTool = downloadImage(url);

                correctaRespTooltip = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapTool, 250, 150, true));

            }
            return bitmapTool;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            // image.setImageBitmap(result);


            respCorrectaToolTip.setCompoundDrawablesWithIntrinsicBounds(null, null, correctaRespTooltip, null);
            respCorrectaToolTip.setText("Correct anwer: ");



        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmapTool = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmapTool = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmapTool;
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
            // pregunta.setCompoundDrawablesWithIntrinsicBounds(null, null, preguntaConImagen, null);
            pregunta.setBackgroundDrawable(preguntaConImagen);
          /*  BitmapDrawable bkgbt = new BitmapDrawable(getResources(), result);
            pregunta.setBackgroundDrawable(bkgbt);
            if(tooltipImagen==1){
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
                String mValue = getStoredPreferenceValue(Modulo.this);
                String result = removeTrailingZero(mValue);
                if(!result.equals("0")){
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(Modulo.this);
                break;
            case "M+":
                if (isInverse){
                    double inputValueMinus  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValueMinus)){
                        subtractMemoryStorage(Modulo.this, inputValueMinus);
                    }
                }else{
                    double inputValue  = isANumber(outputResult.getText().toString());
                    if(!Double.isNaN(inputValue)){
                        addToMemoryStorage(Modulo.this, inputValue);
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

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


     /*   if(v.getId()==findViewById(R.id.img_flecha).getId()){


            finish();
            Intent linsertar=new Intent(Modulo.this, SeleccionaEscuela.class);
            startActivity(linsertar);

        }

        if(v.getId()==findViewById(R.id.ToolTip).getId()){

            initiatePopupAyudaSinResp();
        }*/

       /* if(v.getId()==findViewById(R.id.next).getId()){
            //vistas();

            if(respuestaRadio != null){
                if(!respuestaRadio.equalsIgnoreCase("")){

                    if(haveNetworkConnection()==true){
                        if(preguntaNumero==5 || preguntaNumero==10
                                || preguntaNumero==15){
                            muestraVideo();
                        }else{
                            siguientePreguntaExamen();
                        }
                    }else{
                        showAlertDialogEliminarDatosConductores();
                    }

                }else{
                    Toast toast = Toast.makeText(this,"Selecciona una respuesta", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }



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
