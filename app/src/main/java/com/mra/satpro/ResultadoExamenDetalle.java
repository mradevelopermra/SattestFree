package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.ExamenDTO;
import com.mra.satpro.dto.ExamenResultadosDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.mra.satpro.utilerias.Global.arrayimagenes;
import static com.mra.satpro.utilerias.Global.nombreImagenPrimerCarga;


public class ResultadoExamenDetalle extends AppCompatActivity implements View.OnClickListener {

    String resultadoFinal;
    String totalPreguntas;
    private ImageView img_flecha;
    TextView resultadoExamen;
    GridView resultadoExamenTabla;
    String[] datosgrid;

    ListView listaexamendetalle;
    ArrayList<HashMap<String, Object>> productsList;

    private static final String TAG_PID = "||pid";
    private static final String TAG_PREGUNTA = "||pregunta";
    private static final String TAG_IMAGENPREGUNTA = "||preguntaImagen";
    private static final String TAG_RESPUESTA = "||respuesta";
    private static final String TAG_CORRECTA = "||correcta";
    private static final String TAG_RESPUESTA_IMAGEN = "||respuestaImagen";
    private static final String TAG_CORRECTA_IMAGEN = "||correctaImagen";
    private static final String TAG_TOOLTIP = "||tooltip";
    private static final String TAG_TOOLTIPIMAGEN = "||tooltipImagen";
    private static final String TAG_ACIERTO = "||acierto";
    private static final String TAG_IMAGEN_ACIERTO = "||imagenAcierto";

    EditText pregRest;
    EditText imaPre;
    EditText respUser;
    EditText correctPreg;
    EditText tooltipExamen;
    EditText imaTooltip;

    int buenas = 0;

    Button btndiagnostico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_examen_detalle);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listaexamendetalle=(ListView)findViewById(R.id.listaexamendetalle);

        btndiagnostico = (Button) findViewById(R.id.btndiagnostico);
        btndiagnostico.setOnClickListener(this);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);


        consultar();
        //guardaResHisExamen();

        TextView txtResultados = (TextView) findViewById(R.id.txtResultados);
        txtResultados.setText("¡Corrects: " + buenas + " of 80!");

    }

    public void guardaResHisExamen(){
        totalPreguntas = "120";
        String totalBuenas = buenas+"";

        BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
        ExamenDTO datos=new ExamenDTO();

        Date currentTime = Calendar.getInstance().getTime();

        datos.setFecha(currentTime+"");
        datos.setAciertos(Double.parseDouble(totalBuenas));
        datos.setTotalPreguntas(Double.parseDouble(totalPreguntas));

        if(conexion.insertaExamen(datos)){
            // Toast.makeText(this, getResources().getString(R.string.IngresoGaurdo), Toast.LENGTH_SHORT).show();
        }else{
            //  Toast.makeText(this, getResources().getString(R.string.IngresoGaurdoNo), Toast.LENGTH_SHORT).show();
        }
    }



    public void consultar(){

        BaseDaoImagenBillete conexion = new BaseDaoImagenBillete(this);
        List listas = conexion.ConsultarExamenResultados();

        String[] conceptoIngreso= new String[listas.size()];

        productsList = new ArrayList<HashMap<String, Object>>();

        List<String> tipo = new ArrayList<String>();

        int contenedor=0;
        List listas2 = null;

        String tipoI[]= new String[listas.size()];
        String conI[]= new String[listas.size()];
        int imaI[]= new int[listas.size()];
        int contador =0;

        Log.e("listaResultados", listas.size()+"");

        int numeroPregunta = 1;

        for(Object datos: listas){

            ExamenResultadosDTO elementosIng=(ExamenResultadosDTO) datos;

            String id = String.valueOf(elementosIng.getId());
            String pregunta = numeroPregunta+".- " + elementosIng.getPregunta();
            String imagenPregunta = elementosIng.getPreguntaImagen();
            String respuesta = "Your answer: \n\n" + elementosIng.getRespuesta();
            String correcta = "Correct answer: \n\n" + elementosIng.getCorrecta();
            String respuestaImagen = elementosIng.getRespuesta();
            String correctaImagen = elementosIng.getCorrecta();
            String tooltip = "Tooltip: \n\n" + elementosIng.getTooltip();
            String tooltipImagen = elementosIng.getTooltipImagen();
            double acierto = elementosIng.getAciertos();



            if(acierto==1 || acierto==1.00){

                buenas = buenas + 1;
            }

            String mDrawableName = "";
            int resID = 0;

            String mDrawableNameImagenTooltip = "";
            int resIDImaTooltip = 0;

            String mDrawableNameImagenRespUser = "";
            int resIDImaRespUser = 0;

            String mDrawableNameImagenRespCorrecta = "";
            int resIDImaRespCorrecta = 0;

            for(int i=0; i<arrayimagenes.length; i ++){
                if(imagenPregunta.equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableName = nombreImagenPrimerCarga[i]+"";
                    resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                }
            }


            for(int i=0; i<arrayimagenes.length; i ++){
                if(tooltipImagen.equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableNameImagenTooltip = nombreImagenPrimerCarga[i]+"";
                    resIDImaTooltip = getResources().getIdentifier(mDrawableNameImagenTooltip , "drawable", getPackageName());
                }
            }

            //if(respuesta.contains(".png") || respuesta.contains(".PNG")){
            for(int i=0; i<arrayimagenes.length; i ++){
                if(respuestaImagen.equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableNameImagenRespUser = nombreImagenPrimerCarga[i]+"";
                    resIDImaRespUser = getResources().getIdentifier(mDrawableNameImagenRespUser , "drawable", getPackageName());
                }
            }
            //    respUser.setBackgroundDrawable(getResources().getDrawable(resIDImaRespUser));
            //}else{
            //    respUser.setText(respuesta);
            //}

            //if(correcta.contains(".png") || correcta.contains(".PNG")){
            for(int i=0; i<arrayimagenes.length; i ++){
                if(correctaImagen.equalsIgnoreCase(arrayimagenes[i])){

                    mDrawableNameImagenRespCorrecta = nombreImagenPrimerCarga[i]+"";
                    resIDImaRespCorrecta = getResources().getIdentifier(mDrawableNameImagenRespCorrecta , "drawable", getPackageName());
                }
            }
            //   correctPreg.setBackgroundDrawable(getResources().getDrawable(resIDImaRespCorrecta));
            //}else{
            //    correctPreg.setText(correcta);
            //}

       /*     LayoutInflater inflater = (LayoutInflater) ResultadoExamenDetalle.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.listaexamenresultados,
                    (ViewGroup) findViewById(R.id.listadodetalle));

             pregRest = layout.findViewById(R.id.pregRest);
             imaPre = layout.findViewById(R.id.imagenPregunta);
             respUser = layout.findViewById(R.id.respUser);
             correctPreg = layout.findViewById(R.id.correctPreg);
             tooltipExamen = layout.findViewById(R.id.tooltipExamen);
            imaTooltip = layout.findViewById(R.id.imageTooltipPreg);

            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            pregRest.setLayoutParams(textParam);
            imaPre.setLayoutParams(textParam);
            respUser.setLayoutParams(textParam);
            correctPreg.setLayoutParams(textParam);
            tooltipExamen.setLayoutParams(textParam);
            imaTooltip.setLayoutParams(textParam);*/



            int buenaMala = 0;
            String leyendaCorrectaIncorrecta = "";
            int imagenBuenaMala = 0;
            if(acierto==0.00){
                buenaMala = 0;
                leyendaCorrectaIncorrecta = "wrong";
                imagenBuenaMala = android.R.drawable.ic_delete;

            }else{
                buenaMala = 1;
                tooltip = "";
                resIDImaTooltip = 0;
                correcta = "";
                resIDImaRespCorrecta = 0;
                leyendaCorrectaIncorrecta = "correct";
                imagenBuenaMala = R.drawable.an_ic_check_on;
            }

            String leyendaRespuesta = "Your answer:";
            String leyendaCorrecta = "Corect answer:";

            HashMap<String, Object> map = new HashMap<String, Object>();

            // adding each child node to HashMap key => value

            map.put(TAG_PID, id);
            map.put(TAG_PREGUNTA, pregunta);
            map.put(TAG_IMAGENPREGUNTA, resID);
            if(respuesta.contains(".png") || respuesta.contains(".PNG")) {
                map.put(TAG_RESPUESTA, leyendaRespuesta);
                map.put(TAG_RESPUESTA_IMAGEN, resIDImaRespUser);
            }else{
                map.put(TAG_RESPUESTA, respuesta);
            }
            if(correcta.contains(".png") || correcta.contains(".PNG")) {
                map.put(TAG_CORRECTA, leyendaCorrecta);
                map.put(TAG_CORRECTA_IMAGEN, resIDImaRespCorrecta);
            }else{
                map.put(TAG_CORRECTA, correcta);
            }
            map.put(TAG_TOOLTIP, tooltip);
            map.put(TAG_TOOLTIPIMAGEN, resIDImaTooltip);
            map.put(TAG_ACIERTO, leyendaCorrectaIncorrecta);
            map.put(TAG_IMAGEN_ACIERTO, imagenBuenaMala);

            // adding HashList to ArrayList
            productsList.add(map);

            numeroPregunta = numeroPregunta +1;

        }



        /**
         * Updating parsed JSON data into ListView
         * */




        ListAdapter adapter3 = new SimpleAdapter(ResultadoExamenDetalle.this,
                productsList, R.layout.listaexamenresultados, new String[] { TAG_PREGUNTA,
                TAG_IMAGENPREGUNTA, TAG_RESPUESTA, TAG_RESPUESTA_IMAGEN, TAG_CORRECTA, TAG_CORRECTA_IMAGEN, TAG_TOOLTIP,
                TAG_TOOLTIPIMAGEN, TAG_ACIERTO, TAG_IMAGEN_ACIERTO},
                new int[] { R.id.pregRest, R.id.imagenPregunta, R.id.respUser,
                        R.id.imaRespUser, R.id.correctPreg, R.id.imagRespCorrecta, R.id.tooltipExamen,
                        R.id.imageTooltipPreg, R.id.txtleyendabuenamala, R.id.imageView8 });
        listaexamendetalle.setAdapter(adapter3);
        //Listadetalle1.setAdapter(new dataListAdapter(tipoI,
        //		conI, imaI));

    }


    @Override
    public void onBackPressed() {
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

            finish();
            Intent linsertar=new Intent(ResultadoExamenDetalle.this, MainActivity.class);
            startActivity(linsertar);

           /* img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(ResultadoExamen.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
        }

        if(v.getId()==findViewById(R.id.btndiagnostico).getId()){
            finish();
            Intent linsertar=new Intent(ResultadoExamenDetalle.this, DiagnosticoExamen.class);
            startActivity(linsertar);
        }

    }
}
