package com.mra.satpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.clans.fab.FloatingActionButton;

import com.mra.satpro.dao.BaseDaoExamen;
import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.EscuelasDTO;
import com.mra.satpro.dto.ModulosDTO;
import com.mra.satpro.utilerias.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;
import static com.mra.satpro.utilerias.Global.arrayimagenes;
import static com.mra.satpro.utilerias.Global.nombreImagenPrimerCarga;

public class Crucigrama extends AppCompatActivity implements View.OnClickListener{

    String tipoTest;
    public static String autoAyuda;
    String numPreguntas;

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

    public static TableLayout tableLayout;
    public static TextView textView10;

    public static TableRow tableRow;
    private static int correctaIncorrecta = 0;

    //Konfetii
    public static KonfettiView viewKonfetti;
    StringBuilder stringBuilder;
    StringBuilder stringBuilderRespuesta;
    public static LinearLayout spacecrucigrama, formulario;

    private ImageView img_flecha;
    public static Button btnSiguientecrucigrama, ayuda;
    private static EditText preguntaImagencrucigrama, preguntacrucigrama;

    //Configurar preguntas
    public int nPreguntas =0;
    public String nombreMatTit ="";
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

    public  String preguntasBloque[];
    public  String respuestaUnoBloque[];
    public  String respuestaDosBloque[];
    public  String respuestaTresBloque[];
    public  String respuestaCuatroBloque[];
    public  String imagenPreguntaBloque[];
    public  String correctaBloque[];
    public  String tooltipBloque[];
    public  String imagenTooltipBloque[];

    public int preguntaNumero = 0;

    public static int respCorrecta = 9;
    private Context context;

    public static AlertDialog alertaAyuda;
    public static EditText txtMensajeAyuda;
    public static TextView respCorrectaToolTip;

    public static int conCorrectaTool = 0;
    public static Activity actividad;

    public static int preguntaNext = 0;

    public static String materiaEstudio = "";
    public static int preguntasTotales = 0;
    private String deDondeProviene;
    String random = "";

    TextView tituloCrucigrama;

    int tooltipImagen=0;

    //variables keyboard
    private EditText mEt, mEt1; // Edit Text boxes
    private Button mBSpace, mBdone, mBack, mNum, mBChange;
    private RelativeLayout mLayout, mKLayout;
    private boolean isEdit = false, isEdit1 = false;
    private String mUpper = "upper", mLower = "lower";
    private int w, mWindowWidth;
    private String sL[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "ñ", "à", "é", "è", "û", "î" };
    private String nS[] = { "!", ")", "'", "#", "3", "$", "%", "&", "8", "*",
            "?", "/", "+", "-", "9", "0", "1", "4", "@", "5", "7", "(", "2",
            "\"", "6", "_", "=", "]", "[", "<", ">", "|" };
    private Button mB[] = new Button[42];

    private FloatingActionButton btnAyuda, btnNotas, btnContinuar;
    int anchoPantalla, altoPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crucigrama);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAyuda    = findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(this);

        btnNotas = findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(this);

        btnContinuar= findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(this);

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
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaRespuesta();
            }
        });

        //metodos para mostrar keyboard
        mLayout = (RelativeLayout) findViewById(R.id.xK1);
        mKLayout = (RelativeLayout) findViewById(R.id.xKeyBoard);
        hideDefaultKeyboard();
        //enableKeyboard();
        setKeys();
        setFrow();
        setSrow();
        setTrow();
        setNumbersRow();
        setForow();
        //fin metodos muestra keyboard

        Global.preguntaSigPlayGlobal = 0;
        Global.aciertos = 0;

        context = this;
        actividad = this;

        //consultarAdview();
        vistas();
        updateSizeInfo();

        tipoTest=getIntent().getExtras().getString("tipoTest");
        autoAyuda=getIntent().getExtras().getString("autoAyuda");
        numPreguntas=getIntent().getExtras().getString("preguntasTotal");
        random=getIntent().getExtras().getString("random");

        buscaOpciones();
        //infoTipoPago();
        mostrarInstrucciones();


       //cargaPreguntas(tipoTest, pregSolicitadas);

    //    if(deDondeProviene.equalsIgnoreCase("0")){
    //        cargaPreguntas(tipoTest, pregSolicitadas);
    //    }


        //verImagen();

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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Crucigrama.this);
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

    Context ctx = this;

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

                int pregSolicitadas = Integer.parseInt(numPreguntas);
                tituloCrucigrama = (TextView) findViewById(R.id.tituloCrucigrama);

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


                BaseDaoExamen conexion=new BaseDaoExamen(Crucigrama.this);

                String query = "";

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


                //evaluaRespuesta(preguntaNumero);




            } catch (Exception e) {
                e.printStackTrace();
                //mesnaje("Oops algo va mal, intentalo nuevamente");
                finish();
                Intent linsertar=new Intent(Crucigrama.this, SeleccionaEscuela.class);
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
                tituloCrucigrama.setText(nombreMatTit);
                verImagen();
            }else{
                mesnaje("Oops, algo va mal, por favor, intentalo de nuevo.");
                finish();
                Intent linsertar=new Intent(Crucigrama.this, SeleccionaEscuela.class);
                startActivity(linsertar);
            }






        }




    }

    private void mesnaje(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    private void mostrarInstrucciones() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Crucigrama.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_instrucciones, null);
        // Obtenemos las referencias a los View components de nuestro layout
        TextView tvPuntos = v.findViewById(R.id.textViewPuntos);
        tvPuntos.setText("");
        TextView tvInformacion = v.findViewById(R.id.textViewInformacion);
        tvInformacion.setText("");
        LottieAnimationView gameOverAnimation = v.findViewById(R.id.animation_view);
        gameOverAnimation.setAnimation("tooltip.json");

        builder.setMessage(getResources().getString(R.string.instruccionesCrucigrama))
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
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setView(promptView);

        TextView textView30 = (TextView) promptView.findViewById(R.id.textView30);
        textView30.setText(getResources().getString(R.string.instruccionesCrucigrama));

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

    private void addText(View v) {
    //    if (isEdit == true) {
        int contadorEditext = 1;
        Log.e("editTextList", editTextList.size()+"");

        for (EditText editText : editTextList) {
            Log.e("contadorEditext", contadorEditext+"");
            if(editText.isFocused()){
                // your view is in focus
                String b = "";
                b = (String) v.getTag();
                if (b != null) {
                    // adding text in Edittext
                    editText.append(b);

                    //if(editText != null && editText.isEnabled() && editText.isFocusable()){

                    if(contadorEditext<editTextList.size()){
                        if(editText.focusSearch(editText.FOCUS_RIGHT) != null){
                            if(editText.focusSearch(v.FOCUS_RIGHT).isFocusableInTouchMode()
                                    && editText.focusSearch(v.FOCUS_RIGHT).isFocusable()
                                    && editText.focusSearch(v.FOCUS_RIGHT).isEnabled()){

                                editText.focusSearch(editText.FOCUS_RIGHT).requestFocus();
                                break;

                            }
                        }
                    }


                }
            }
            contadorEditext = contadorEditext + 1;
        }


        //    }


    }

    private void hideDefaultKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void setFrow() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        altoPantalla = metrics.heightPixels;
        anchoPantalla = metrics.widthPixels;

        w = (anchoPantalla / 10) - 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) w,
                w);

        mB[16].setLayoutParams(params);
        mB[22].setLayoutParams(params);
        mB[4].setLayoutParams(params);
        mB[17].setLayoutParams(params);
        mB[19].setLayoutParams(params);
        mB[24].setLayoutParams(params);
        mB[20].setLayoutParams(params);
        mB[8].setLayoutParams(params);
        mB[14].setLayoutParams(params);
        mB[15].setLayoutParams(params);

        /*mB[16].setWidth(w);
        mB[22].setWidth(w);
        mB[4].setWidth(w);
        mB[17].setWidth(w);
        mB[19].setWidth(w);
        mB[24].setWidth(w);
        mB[20].setWidth(w);
        mB[8].setWidth(w);
        mB[14].setWidth(w);
        mB[15].setWidth(w);
        mB[16].setHeight(50);
        mB[22].setHeight(50);
        mB[4].setHeight(50);
        mB[17].setHeight(50);
        mB[19].setHeight(50);
        mB[24].setHeight(50);
        mB[20].setHeight(50);
        mB[8].setHeight(50);
        mB[14].setHeight(50);
        mB[15].setHeight(50);*/

    }

    private void setSrow() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        altoPantalla = metrics.heightPixels;
        anchoPantalla = metrics.widthPixels;

        w = (anchoPantalla / 10) - 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) w,
                w);

        mB[0].setLayoutParams(params);
        mB[18].setLayoutParams(params);
        mB[3].setLayoutParams(params);
        mB[5].setLayoutParams(params);
        mB[6].setLayoutParams(params);
        mB[7].setLayoutParams(params);
        mB[26].setLayoutParams(params);
        mB[9].setLayoutParams(params);
        mB[10].setLayoutParams(params);
        mB[11].setLayoutParams(params);
        mB[26].setLayoutParams(params);

        /*w = (anchoPantalla / 10);
        mB[0].setWidth(w);
        mB[18].setWidth(w);
        mB[3].setWidth(w);
        mB[5].setWidth(w);
        mB[6].setWidth(w);
        mB[7].setWidth(w);
        mB[26].setWidth(w);
        mB[9].setWidth(w);
        mB[10].setWidth(w);
        mB[11].setWidth(w);
        mB[26].setWidth(w);

        mB[0].setHeight(50);
        mB[18].setHeight(50);
        mB[3].setHeight(50);
        mB[5].setHeight(50);
        mB[6].setHeight(50);
        mB[7].setHeight(50);
        mB[9].setHeight(50);
        mB[10].setHeight(50);
        mB[11].setHeight(50);
        mB[26].setHeight(50);*/
    }

    private void setTrow() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        altoPantalla = metrics.heightPixels;
        anchoPantalla = metrics.widthPixels;

        w = (anchoPantalla / 10) - 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) w,
                w);

        mB[25].setLayoutParams(params);
        mB[23].setLayoutParams(params);
        mB[2].setLayoutParams(params);
        mB[21].setLayoutParams(params);
        mB[1].setLayoutParams(params);
        mB[13].setLayoutParams(params);
        mB[12].setLayoutParams(params);
        mB[27].setLayoutParams(params);
        mB[28].setLayoutParams(params);


        /*mB[25].setWidth(w);
        mB[23].setWidth(w);
        mB[2].setWidth(w);
        mB[21].setWidth(w);
        mB[1].setWidth(w);
        mB[13].setWidth(w);
        mB[12].setWidth(w);
        mB[27].setWidth(w);
        mB[28].setWidth(w);
        mBack.setWidth(w);

        mB[25].setHeight(50);
        mB[23].setHeight(50);
        mB[2].setHeight(50);
        mB[21].setHeight(50);
        mB[1].setHeight(50);
        mB[13].setHeight(50);
        mB[12].setHeight(50);
        mB[27].setHeight(50);
        mB[28].setHeight(50);
        mBack.setHeight(50);*/

    }

    private void setForow() {
        w = (anchoPantalla / 10);
        mBSpace.setWidth(w * 4);
        mBSpace.setHeight(50);
        mB[29].setWidth(w);
        mB[29].setHeight(50);

        mB[30].setWidth(w);
        mB[30].setHeight(50);

        mB[31].setHeight(50);
        mB[31].setWidth(w);
        mBdone.setWidth(w + (w / 1));
        mBdone.setHeight(50);

    }

    private void setKeys() {
        mWindowWidth = getWindowManager().getDefaultDisplay().getWidth(); // getting
        Log.e("mWindowWidthInitial", mWindowWidth+"");
        // window
        // height
        // getting ids from xml files
        mB[0] = (Button) findViewById(R.id.xA);
        mB[1] = (Button) findViewById(R.id.xB);
        mB[2] = (Button) findViewById(R.id.xC);
        mB[3] = (Button) findViewById(R.id.xD);
        mB[4] = (Button) findViewById(R.id.xE);
        mB[5] = (Button) findViewById(R.id.xF);
        mB[6] = (Button) findViewById(R.id.xG);
        mB[7] = (Button) findViewById(R.id.xH);
        mB[8] = (Button) findViewById(R.id.xI);
        mB[9] = (Button) findViewById(R.id.xJ);
        mB[10] = (Button) findViewById(R.id.xK);
        mB[11] = (Button) findViewById(R.id.xL);
        mB[12] = (Button) findViewById(R.id.xM);
        mB[13] = (Button) findViewById(R.id.xN);
        mB[14] = (Button) findViewById(R.id.xO);
        mB[15] = (Button) findViewById(R.id.xP);
        mB[16] = (Button) findViewById(R.id.xQ);
        mB[17] = (Button) findViewById(R.id.xR);
        mB[18] = (Button) findViewById(R.id.xS);
        mB[19] = (Button) findViewById(R.id.xT);
        mB[20] = (Button) findViewById(R.id.xU);
        mB[21] = (Button) findViewById(R.id.xV);
        mB[22] = (Button) findViewById(R.id.xW);
        mB[23] = (Button) findViewById(R.id.xX);
        mB[24] = (Button) findViewById(R.id.xY);
        mB[25] = (Button) findViewById(R.id.xZ);
        mB[26] = (Button) findViewById(R.id.xS1);
        mB[27] = (Button) findViewById(R.id.xS2);
        mB[28] = (Button) findViewById(R.id.xS3);
        mB[29] = (Button) findViewById(R.id.xS4);
        mB[30] = (Button) findViewById(R.id.xS5);
        mB[31] = (Button) findViewById(R.id.xS6);

        mB[32] = (Button) findViewById(R.id.xUno);
        mB[33] = (Button) findViewById(R.id.xDos);
        mB[34] = (Button) findViewById(R.id.xTres);
        mB[35] = (Button) findViewById(R.id.xCuatro);
        mB[36] = (Button) findViewById(R.id.xCinco);
        mB[37] = (Button) findViewById(R.id.xSeis);
        mB[38] = (Button) findViewById(R.id.xSiete);
        mB[39] = (Button) findViewById(R.id.xOcho);
        mB[40] = (Button) findViewById(R.id.xNueve);
        mB[41] = (Button) findViewById(R.id.xCero);

        mBSpace = (Button) findViewById(R.id.xSpace);
        mBdone = (Button) findViewById(R.id.xDone);
        mBChange = (Button) findViewById(R.id.xChange);
        mBack = (Button) findViewById(R.id.xBack);
        mNum = (Button) findViewById(R.id.xNum);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(Color.rgb(47,25,85)); // Changes this drawbale to use a single color instead of a gradient
        gd1.setCornerRadius(10);
        gd1.setStroke(1, Color.parseColor("#53933f"));

        for (int i = 0; i < mB.length; i++){
            mB[i].setOnClickListener(this);
            mB[i].setBackgroundDrawable(gd1);
            //mB[i].setBackgroundColor(Color.BLUE);
            mB[i].setTypeface(null, Typeface.BOLD);
            mB[i].setTextColor(Color.WHITE);
        }

        mBSpace.setOnClickListener(this);
        mBdone.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBChange.setOnClickListener(this);
        mNum.setOnClickListener(this);

    }

    private void moverEditext (View v){
        Log.e("Obkey", "aqui entra");
        if(!((EditText) v).toString().isEmpty())
            Log.e("Obkey", "mueve al siguiente");
        v.focusSearch(View.FOCUS_RIGHT).requestFocus();
    }

    /*public void consultarAdview(){

        BaseDaoPassword conexiones=new BaseDaoPassword(this);

        List listas = conexiones.ConsultarPttoDetalle(1);

        String estatus = "";


        for(Object datos: listas){
            PasswordDTo elementosIng=(PasswordDTo) datos;

            estatus = String.valueOf(elementosIng.getEstatus());


        }


        if(listas.size()<=0){
            adViewCruci = (AdView) findViewById(R.id.adViewCruci);

            AdRequest adRequest = new AdRequest.Builder().build();
            adViewCruci.loadAd(adRequest);
        }else{
            adViewCruci.destroy();
            adViewCruci.setVisibility(View.GONE);

        }

    }*/

    public void vistas(){

        tableRow = new TableRow(this);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        btnSiguientecrucigrama = (Button) findViewById(R.id.btnSiguientecrucigrama);
        btnSiguientecrucigrama.setOnClickListener(this);

        ayuda = (Button) findViewById(R.id.ayuda);
        ayuda.setOnClickListener(this);

        preguntaImagencrucigrama = (EditText) findViewById(R.id.preguntaImagencrucigrama);
        preguntaImagencrucigrama.setFocusable(false);

        preguntacrucigrama = (EditText) findViewById(R.id.preguntacrucigrama);
        preguntacrucigrama.setFocusable(false);

        viewKonfetti = (KonfettiView) findViewById(R.id.viewKonfetti);

        tituloCrucigrama = (TextView) findViewById(R.id.tituloCrucigrama);

    }

    public void updateSizeInfo() {

        spacecrucigrama = (LinearLayout) findViewById(R.id.spacecrucigrama);
        DisplayMetrics displayMetrics = spacecrucigrama.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        tamanopantalla = (int) (dpWidth);

    }

    public void crossWord(String respuestaCorrecta){

        if((spacecrucigrama).getChildCount() > 0)
            (spacecrucigrama).removeAllViews();

        palabra = respuestaCorrecta.trim();
        Log.e("palabra", palabra.length()+"");
        cuentaLetras = palabra.length();

        DisplayMetrics metrics = spacecrucigrama.getResources().getDisplayMetrics();
        float dp = 10f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        //int celdasPorRenglon = Math.round(metrics.widthPixels / pixels);

        int celdasPorRenglon = 10;

        int renglon = (int) Math.ceil(cuentaLetras / celdasPorRenglon);
        int remanente = 0;
        int totalRenglones = 0;

        if(renglon<1){
            totalRenglones = 1;
        }else{
            remanente = ((renglon + (cuentaLetras / celdasPorRenglon)) - 1) / (cuentaLetras / celdasPorRenglon);
            totalRenglones = (int) renglon + (int) remanente;
        }

        Log.e("totalRenglones", totalRenglones+"");

        spacecrucigrama.addView(tableLayout(totalRenglones));

        spacecrucigrama.setBackgroundColor(Color.rgb(43,44,85));

        spacecrucigrama.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 30, 0, 30);

        spacecrucigrama.setLayoutParams(layoutParams);

        preguntacrucigrama.setBackgroundDrawable(getResources().getDrawable(R.mipmap.pizzarra_ahorcado));

        btnSiguientecrucigrama.setLayoutParams(layoutParams);

    }

    public void validaRespuesta(){

        stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);
        stringBuilder.delete(0, stringBuilder.length());

        for (EditText editText : editTextList) {
            String textoUsuario = "";
            textoUsuario = editText.getText().toString();

            if(textoUsuario.equalsIgnoreCase("á") || textoUsuario.equalsIgnoreCase("à")){
                textoUsuario = "a";
            }
            if(textoUsuario.equalsIgnoreCase("é") || textoUsuario.equalsIgnoreCase("è")){
                textoUsuario = "e";
            }
            if(textoUsuario.equalsIgnoreCase("í") || textoUsuario.equalsIgnoreCase("ì")){
                textoUsuario = "i";
            }
            if(textoUsuario.equalsIgnoreCase("ó") || textoUsuario.equalsIgnoreCase("ò")){
                textoUsuario = "o";
            }
            if(textoUsuario.equalsIgnoreCase("ú") || textoUsuario.equalsIgnoreCase("ù")){
                textoUsuario = "u";
            }
            if(textoUsuario.equalsIgnoreCase(" ")){
                textoUsuario = " ";
            }

            stringBuilder.append(editText.getHint().toString().trim());
            stringBuilder.append(textoUsuario).toString().trim();
        }

        int preguntaSiguiente = Global.preguntaSigPlayGlobal;

        String[] arr = correctaBloque[preguntaSiguiente].trim().split("");

        stringBuilderRespuesta = new StringBuilder();
        stringBuilderRespuesta.setLength(0);
        stringBuilderRespuesta.delete(0, stringBuilderRespuesta.length());

        for(String letra: arr){
            Log.e("letra", letra);
            String textoRespuesta = "";

            textoRespuesta = letra;

            if(letra.equalsIgnoreCase("á") || letra.equalsIgnoreCase("à")){
                textoRespuesta = "a";
            }
            if(letra.equalsIgnoreCase("é") || letra.equalsIgnoreCase("è")){
                textoRespuesta = "e";
            }
            if(letra.equalsIgnoreCase("í") || letra.equalsIgnoreCase("ì")){
                textoRespuesta = "i";
            }
            if(letra.equalsIgnoreCase("ó") || letra.equalsIgnoreCase("ò")){
                textoRespuesta = "o";
            }
            if(letra.equalsIgnoreCase("ú") || letra.equalsIgnoreCase("ù")){
                textoRespuesta = "u";
            }
            if(letra.equalsIgnoreCase(" ") || letra.equalsIgnoreCase("")){
                textoRespuesta = "";
            }

            stringBuilderRespuesta.append(textoRespuesta).toString().trim();
        }

        Log.e("stringBuilder", String.valueOf(stringBuilder));
        Log.e("stringBuilderRespuesta", String.valueOf(stringBuilderRespuesta));

        updateSizeInfo();
        //   if(deDondeProviene.equalsIgnoreCase("0")){

        double queTanIgual = similarity(String.valueOf(stringBuilderRespuesta), String.valueOf(stringBuilder));

        Log.e("queTanIgual", queTanIgual+"");

        if(queTanIgual>0.85){
            Global.aciertos = Global.aciertos + 1;
            correctaIncorrecta = 1;
            //initiatePopupAyuda(this);
            mostrarVideoComprarSaldo(this);
        }else{
            correctaIncorrecta = 0;
            if(autoAyuda.equalsIgnoreCase("Si") ) {
                //initiatePopupAyuda(this);
                mostrarVideoComprarSaldo(this);
            }
        }


            /*if(String.valueOf(stringBuilderRespuesta).equalsIgnoreCase(String.valueOf(stringBuilder)) ||
                    (String.valueOf(stringBuilder).compareToIgnoreCase(String.valueOf(stringBuilderRespuesta))==0)){
                Global.aciertos = Global.aciertos + 1;
                correctaIncorrecta = 1;
                initiatePopupAyuda(this, preguntaSiguiente);
                //siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);

            }else{
                correctaIncorrecta = 0;
                if(autoAyuda.equalsIgnoreCase("Si") ) {
                    initiatePopupAyuda(this, preguntaSiguiente);
                }
            }*/



      /*  }else{

            if(SkyInvaderPlay.correctaBloque[preguntaSiguiente].equalsIgnoreCase(String.valueOf(stringBuilder))){
                Global.aciertos = Global.aciertos + 1;
                SkyInvaderPlay.correctaIncorrecta = 1;
                initiatePopupAyuda(this, preguntaSiguiente);
                //siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);

            }else{
                SkyInvaderPlay.correctaIncorrecta = 0;
                if(autoAyuda.equalsIgnoreCase("Si") ) {
                    initiatePopupAyuda(this, preguntaSiguiente);
                }
            }

            int nextPregunta = preguntaNumero;
            Log.e("nextPregunta", nextPregunta+"");
            crossWord(correctaBloque[nextPregunta]);
        }*/


    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    int pixelsWithEditext = 0;

    private TableLayout tableLayout(int count) {

        tableLayout = new TableLayout(this);
        editTextList = new ArrayList<EditText>();
        tableLayout.setStretchAllColumns(true);
        int noOfRows = count; /// 5;

        char tmpLetra;

        DisplayMetrics metrics = spacecrucigrama.getResources().getDisplayMetrics();
        float dp = 10f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        pixelsWithEditext = pixels;
        //int celdasPorRenglon = Math.round(metrics.widthPixels / pixels);
        int celdasPorRenglon = 10;
        int finFocus = 0;

        Random rand = new Random();
        int numFoco = 0;

        letraCuenta = 0;

        for (int j = 0; j < noOfRows; j++) {

            tableRow = new TableRow(this);

            tableRow.setPadding(10, 10, 10, 10);

            for(int i=letraCuenta; i<cuentaLetras; i++)
            {
                finFocus = finFocus + i;
                tmpLetra = palabra.charAt(i);
                String letraPorLetra =  new StringBuilder().append(tmpLetra).toString();

                String soloConsonante = "";
                int cuentaVocales = 0;
                soloConsonante = removeVowels(letraPorLetra);

                if(soloConsonante.equalsIgnoreCase("")){
                    numFoco = numFoco + 0;
                }

                tableRow.addView(editTextFuncion(letraPorLetra, numFoco));
                numFoco = numFoco + 1;
                if((i+1)%celdasPorRenglon==0 && i>0){
                    letraCuenta = i + 1;
                    break;
                }
            }
            tableLayout.addView(tableRow);

        }

        return tableLayout;
    }

    int cuentaVol = 0;

    private EditText editTextFuncion(String hint, int numFocu) {

        EditText editText = new EditText(Crucigrama.this);
        // editText.setId(Integer.valueOf(hint));

        String soloConsonante = "";
        int cuentaVocales = 0;
        int rand = new Random().nextInt(5);

        //if(rand==0){
        soloConsonante = removeVowels(hint);
        String palabraVocal = removeVowels(palabra);
        cuentaVocales = palabraVocal.length() + 1;
        //}else{
        //    soloConsonante = removeConsonantes(hint);
        //    String palabraVocal = removeConsonantes(palabra);
        //    cuentaVocales = palabraVocal.length() + 1;
        //}

        //String soloConsonante = removeVowels(hint);
        //String palabraVocal = removeVowels(palabra);
        //int cuentaVocales = palabraVocal.length() + 1;

        GradientDrawable gd = new GradientDrawable();
        //gd.setColor(Color.rgb(255, 215, 115));
        gd.setColor(getResources().getColor(R.color.white));
        gd.setStroke(2, Color.rgb(127, 162, 8));

        if(soloConsonante.equalsIgnoreCase(" ")){
            //editText.setBackgroundColor(Color.rgb(95, 0, 10));
            editText.setBackgroundColor(getResources().getColor(R.color.gray_cuentas));
            editText.setHint(soloConsonante);
            //editText.setTextSize(25);
            editText.setFocusable(false);
            editText.setEnabled(false);
            editText.setWidth(pixelsWithEditext);
            editText.setGravity(Gravity.CENTER);
            editText.setTypeface(null, Typeface.BOLD);
        }else{

            if(!soloConsonante.equalsIgnoreCase("")){
                editText.setHint(soloConsonante);
            }else {
                editText.setHint(" ");
                if(numFocu==0){
                    editText.requestFocus();
                }
            }
            //editText.setBackgroundColor(Color.rgb(225, 195, 199));
            editText.setBackgroundColor(getResources().getColor(R.color.white));
            editText.setHintTextColor(Color.rgb(133, 8, 13));
            //editText.setHintTextColor(Color.rgb(43,44,85));
            editText.setTextColor(Color.rgb(133, 8, 13));
            //editText.setTextColor(Color.rgb(43,44,85));
            //editText.setTextSize(25);
            editText.setWidth(pixelsWithEditext);
            editText.setGravity(Gravity.CENTER);
            editText.setTypeface(null, Typeface.BOLD);
            editText.setBackground(gd);
            //editText.setInputType(InputType.TYPE_NULL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText.setShowSoftInputOnFocus(false);
            }

            if(soloConsonante.equalsIgnoreCase("")){

                cuentaVol = cuentaVol + 1;

                editText.setFocusable(true);
                editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                View.OnKeyListener key=new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(((EditText) v).toString().length()==1)
                            v.focusSearch(View.FOCUS_RIGHT).requestFocus();
                        return false;
                    }
                };

                if(cuentaVol<cuentaVocales){
                    editText.setOnKeyListener(key);
                }

            }else{
                editText.setFocusable(false);
                editText.setEnabled(false);
            }
        }

        //editText.setLayoutParams(new RelativeLayout.LayoutParams(25, 25));
        editTextList.add(editText);
        return editText;
    }


    public static String removeVowels(final String string){
        final String vowels = "aAáÁeEéÉiIíÍoOóÓuUúÚ0123456789";
        final StringBuilder builder = new StringBuilder();
        for(final char c : string.toCharArray())
            if(vowels.indexOf(c) < 0)
                builder.append(c);
        return builder.toString();
    }

    public static String removeConsonantes(final String string){
        final String vowels = "BCDFGHJKLMNPQRSTVXZbcdfghjklmnpqrstvxz";
        final StringBuilder builder = new StringBuilder();
        for(final char c : string.toCharArray())
            if(vowels.indexOf(c) < 0)
                builder.append(c);
        return builder.toString();
    }

    public void cargaPreguntas(String materia, int pregSolcitadas){


    }



    public void verImagen() {

        context = this;
        boolean siEsImagen = false;

        if(imagenPreguntaBloque[0] != null){
            if (!imagenPreguntaBloque[0].equalsIgnoreCase("")){
                siEsImagen = true;
            }
        }


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

            preguntacrucigrama.setText("");
            preguntacrucigrama.setLayoutParams(textParam);

            preguntacrucigrama.setBackgroundDrawable(getResources().getDrawable(resID));

            preguntaImagencrucigrama.setText(preguntasBloque[0]);
            preguntaImagencrucigrama.setVisibility(View.VISIBLE);


        }else{

            preguntacrucigrama.setText("\n" + preguntasBloque[0] + "\n ");


            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            preguntacrucigrama.setLayoutParams(textParam);

        }

        //   if(respuestaUnoBloque[0].contains(".png") || respuestaUnoBloque[0].contains(".PNG")){

        //       invasoresEspacio();

        //   }else{

            crossWord(correctaBloque[0]);

        //   }

    }


    public void invasoresEspacio(){
        String dedondeproviene = "1";
        finish();
        Intent linsertar=new Intent(this, SkyInvaderPlay.class);
        linsertar.putExtra("tipoTest", tipoTest);
        linsertar.putExtra("autoAyuda", autoAyuda);
        linsertar.putExtra("preguntasTotal", numPreguntas);
        linsertar.putExtra("dedondeproviene", dedondeproviene);
        startActivity(linsertar);
    }

    public static void confeti(){


        viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(50L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                //  .addSizes(new Size(12, 5))
                .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);
    }



    android.app.AlertDialog dialogConRespuesta;
    private void mostrarVideoComprarSaldo(Context cont) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Crucigrama.this);

        View v = getLayoutInflater().inflate(R.layout.dialogo_game_over, null);
        // Obtenemos las referencias a los View components de nuestro layout
        txtMensajeAyuda = (EditText) v.findViewById(R.id.ayudatooltip);
        ayudaImagentooltip = (EditText) v.findViewById(R.id.ayudaImagentooltip);
        respCorrectaToolTip = (TextView) v.findViewById(R.id.respCorrecta);
        TextView textView14 =  (TextView) v.findViewById(R.id.textView14);
        txtMensajeAyuda.setFocusable(false);
        ayudaImagentooltip.setFocusable(false);
        Button btnaceptarAyuda = (Button) v.findViewById(R.id.btnCerrarTooltip);
        btnaceptarAyuda.setOnClickListener(cerrar_ayuda_con_r);

        boolean respuestaCorrecta = false;

        if(correctaIncorrecta==0){
            textView14.setText("Failed!");
            respCorrectaToolTip.setText("");
            txtMensajeAyuda.setText("Review your knowledge! \n\n\n");
            respuestaCorrecta= false;
        } else if(correctaIncorrecta==1){
            textView14.setText("Good!");
            respCorrectaToolTip.setText("");
            txtMensajeAyuda.setText("\n\n" +
                    "Good job! " +
                    "\n\n");
            respuestaCorrecta = true;
            confeti();
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

            dialogConRespuesta.dismiss();

            String materiaEstudio = SkyInvaderPlay.materiaEstudio;
            int preguntaSiguiente = Global.preguntaSigPlayGlobal;
            int totalPreguntas = SkyInvaderPlay.preguntasTotales;

            siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);
            //siguientePreguntaButton();
            //vistas();
            //preguntaNext = preguntaNext + 1;

            if(preguntaNext == nPreguntas) {
                resultados();
            }
        }
    };


    EditText ayudaImagentooltip;

    public void initiatePopupAyuda(Context cont) {
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

            txtMensajeAyuda = (EditText) layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = (EditText) layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = (TextView) layout.findViewById(R.id.respCorrecta);
            TextView textView14 =  (TextView) layout.findViewById(R.id.textView14);
            txtMensajeAyuda.setFocusable(false);

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
                confeti();
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

                    txtMensajeAyuda.setText("\n \n\n" + tooltipBloque[preguntaNumero]);
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



    public View.OnClickListener cerrar_ayuda = new View.OnClickListener() {
        public void onClick(View v) {

            alertaAyuda.dismiss();
            alertaAyuda.dismiss();

            String materiaEstudio = SkyInvaderPlay.materiaEstudio;
            int preguntaSiguiente = Global.preguntaSigPlayGlobal;
            int totalPreguntas = SkyInvaderPlay.preguntasTotales;

            siguientePreguntaButton(preguntaSiguiente, materiaEstudio, totalPreguntas);
            //siguientePreguntaButton();
            //vistas();
            //preguntaNext = preguntaNext + 1;

            if(preguntaNext == nPreguntas) {
                resultados();
            }
        }
    };


    //Siguiente pregunta button next



    public void resultados(){

        String palomitas = Global.aciertos+"";
        String totalPreg = preguntasBloque.length+"";

        Crucigrama.actividad.finish();
        Intent linsertar=new Intent(actividad, Resultados.class);
        linsertar.putExtra("aciertos", palomitas);
        linsertar.putExtra("preguntas", totalPreg);
        linsertar.putExtra("modulo", nombreMatTit);
        actividad.startActivity(linsertar);

    }

    public void siguientePreguntaButton(int siguiente, String tipoMateria, int totPreg){

        preguntacrucigrama.setText("");
        preguntacrucigrama.setCompoundDrawables(null, null, null, null);
        preguntaImagencrucigrama.setText("");
        preguntaImagencrucigrama.setCompoundDrawables(null, null, null, null);
        preguntaImagencrucigrama.setVisibility(View.INVISIBLE);

        Global.preguntaSigPlayGlobal = siguiente + 1;
        preguntaNext = Global.preguntaSigPlayGlobal;

        if(preguntaNext == totPreg) {
            resultados();
        }

        if(preguntaNumero < (correctaBloque.length - 1)) {
            preguntaNumero = preguntaNumero + 1;

            boolean siEsImagen = false;

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
                        resID = actividad.getResources().getIdentifier(mDrawableName , "drawable", actividad.getPackageName());
                    }
                }

                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                preguntacrucigrama.setLayoutParams(textParam);

                preguntacrucigrama.setBackgroundDrawable(actividad.getResources().getDrawable(resID));

                preguntaImagencrucigrama.setText(preguntasBloque[preguntaNumero]);
                preguntaImagencrucigrama.setVisibility(View.VISIBLE);
                preguntacrucigrama.setText("");
                preguntacrucigrama.setEnabled(false);

            }else{


                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                preguntacrucigrama.setLayoutParams(textParam);

                preguntacrucigrama.setText("\n " + preguntasBloque[preguntaNumero] + "\n ");
                preguntacrucigrama.setCompoundDrawables(null, null, null, null);
                preguntacrucigrama.setBackgroundDrawable(actividad.getResources().getDrawable(R.mipmap.pizzarra_ahorcado));
                //preguntacrucigrama.setBackgroundColor(actividad.getResources().getColor(R.color.blackboard));
                preguntaImagencrucigrama.setText("");
                preguntacrucigrama.setEnabled(false);
            }


        }


        crossWord(correctaBloque[preguntaNumero]);

        materiaEstudio = tipoMateria;
        // preguntaSigPlay = siguiente;
        preguntasTotales = totPreg;



    }



    android.app.AlertDialog dialogSinResp;
    private void ayudaSinRespuesta() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Crucigrama.this);

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


    private void initiatePopupAyudaSinResp() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Crucigrama.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.tooltip,
                    (ViewGroup) findViewById(R.id.help_popup));


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setView(layout);

            Button btnaceptarAyuda = (Button) layout.findViewById(R.id.btnCerrarTooltip);
            btnaceptarAyuda.setOnClickListener(cerrar_ayuda_sin_resp);

            txtMensajeAyuda = layout.findViewById(R.id.ayudatooltip);
            ayudaImagentooltip = layout.findViewById(R.id.ayudaImagentooltip);
            respCorrectaToolTip = layout.findViewById(R.id.respCorrecta);
            txtMensajeAyuda.setFocusable(false);

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

                //if(correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("png") || correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("PNG")) {

              /*  if(correctaBloque[preguntaNumero].contains(".png") || correctaBloque[preguntaNumero].contains(".PNG")){

                    // Image url
                    String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenTooltipBloque[preguntaNumero];
                    String image_url2 = "http://www.pypsolucionesintegrales.com/Imagenes/" + correctaBloque[preguntaNumero];

                    Log.e("image_url", image_url);
                    conPagTool = 1;
                    conCorrectaTool = 1;

                    // Create an object for subclass of AsyncTask
                    ImagenTooltip task = new ImagenTooltip();
                    // Execute the task
                    task.execute(new String[] { image_url, image_url2 });

                }else */
                if(imagenTooltipBloque[preguntaNumero].contains(".png") || imagenTooltipBloque[preguntaNumero].contains(".PNG")){
                    // Image url
            /*       String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + imagenTooltipBloque[preguntaNumero];


                    conPagTool = 1;
                    conCorrectaTool = 2;

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

                txtMensajeAyuda.setText("\n" + tooltipBloque[preguntaNumero]);
            }

       /*     if(correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("png") || correctaBloque[preguntaNumero].substring(correctaBloque[preguntaNumero].length() - 3).contains("PNG")) {
                Log.e("respuestaUnoBloque[0]", correctaBloque[preguntaNumero]);

                // Image url
                String image_url = "http://www.pypsolucionesintegrales.com/Imagenes/" + correctaBloque[preguntaNumero];

                Log.e("image_url", image_url);
                conCorrectaTool = 1;

                // Create an object for subclass of AsyncTask
                ImagenTooltip task = new ImagenTooltip();
                // Execute the task
                task.execute(new String[] { image_url });

            }*/

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

    private void isBack(View v) {
        for (EditText editText : editTextList) {

            if(editText.isFocused()){
                // your view is in focus
                CharSequence cc = editText.getText();
                if (cc != null && cc.length() > 0) {
                    {
                        editText.setText("");
                        editText.append(cc.subSequence(0, cc.length() - 1));
                    }

                }
            }
        }
    }




    public void siguientePreguntaExamen(){
        validaRespuesta();
    }

    /*public  void mensajeGPS(){
        if(ContextCompat.checkSelfPermission(Crucigrama.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            mensaje();
            return;
        }
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isEnabled){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
        }else{
            mensaje();
        }
    }
    private void mensaje()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Crucigrama.this).create();
        alertDialog.setTitle("Internet");
        alertDialog.setMessage("Necesitas habilitar el GPS antes de continuar.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }*/

    private void showAlertDialogEliminarDatosConductores()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Crucigrama.this).create();
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


    @Override
    public void onClick(View v) {

        if (v != mBdone && v != mBack && v != mNum && v.getId()!=findViewById(R.id.btnSiguientecrucigrama).getId()
                && v.getId()!=findViewById(R.id.ayuda).getId() && v.getId()!=findViewById(R.id.img_flecha).getId()) {
            addText(v);
            //moverEditext(v);
        }

        if (v == mBack) {
            isBack(v);
        }

        if(v.getId()==findViewById(R.id.btnSiguientecrucigrama).getId()){
            validaRespuesta();
            //siguientePreguntaButton();
        }


        if(v.getId()==findViewById(R.id.ayuda).getId()){
            //initiatePopupAyudaSinResp();
            ayudaSinRespuesta();
            //siguientePreguntaButton();
        }



        if(v.getId()==findViewById(R.id.img_flecha).getId()){

            finish();
            Intent linsertar=new Intent(Crucigrama.this, SeleccionaEscuela.class);
            startActivity(linsertar);

        }

    }

    @Override
    public void onBackPressed() { }


    @Override
    protected void onResume() {
        super.onResume();
        setKeys();
        setFrow();
        setSrow();
        setTrow();
        setNumbersRow();
        setForow();
    }

    private void setNumbersRow() {


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        altoPantalla = metrics.heightPixels;
        anchoPantalla = metrics.widthPixels;

        w = (anchoPantalla / 10) - 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) w,
                w);

        mB[32].setLayoutParams(params);
        mB[33].setLayoutParams(params);
        mB[34].setLayoutParams(params);
        mB[35].setLayoutParams(params);
        mB[36].setLayoutParams(params);
        mB[37].setLayoutParams(params);
        mB[38].setLayoutParams(params);
        mB[39].setLayoutParams(params);
        mB[40].setLayoutParams(params);
        mB[41].setLayoutParams(params);

    }

}
