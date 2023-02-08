package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.AyudaDTO;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Resultados extends AppCompatActivity implements View.OnClickListener {

    TextView resultadosFinal, textView14;
    String resultadoFinal;
    String totalPreguntas;
    String nomModulo;
    String idModuloFinal;

    private ImageView img_flecha;
    GridView resultados;
    String[] datosgrid;
    String [] periodos;
    String [] idModulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        resultadosFinal = (TextView) findViewById(R.id.resultadosFinal);

        textView14 = (TextView) findViewById(R.id.textView14);

        resultadoFinal = getIntent().getExtras().getString("aciertos")+"";
        totalPreguntas = getIntent().getExtras().getString("preguntas")+"";
        nomModulo = getIntent().getExtras().getString("modulo")+"";


        periodos = new String[]{
                "Reading",
                "Writing and Language",
                "Math",
                "Biology",
                "Chemistry",
                "Physics",
                "Spanish"
        };

        idModulo = new String[]{
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20"
        };


        for(int i=0; i<periodos.length; i++){
            if(nomModulo.equalsIgnoreCase(periodos[i])){
                idModuloFinal = idModulo[i];
            }
        }

        BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
        AyudaDTO datos=new AyudaDTO();

        Date currentTime = Calendar.getInstance().getTime();

        double totalPalomitas = 0.0;
        double totalCuestions = 0.0;

        try {
            totalPalomitas = Double.parseDouble(resultadoFinal);
            totalCuestions = Double.parseDouble(totalPreguntas);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        datos.setNombreModulo(idModuloFinal);
        datos.setFecha(currentTime+"");
        datos.setAciertos(totalPalomitas);
        datos.setTotalPreguntas(totalCuestions);

        if(conexion.insertaAyuda(datos)){
            // Toast.makeText(this, getResources().getString(R.string.IngresoGaurdo), Toast.LENGTH_SHORT).show();
        }else{
            //  Toast.makeText(this, getResources().getString(R.string.IngresoGaurdoNo), Toast.LENGTH_SHORT).show();
        }

        resultadosFinal.setText(resultadoFinal);

        resultados = (GridView) findViewById(R.id.resultados);
        try {
            llenarGastos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textView14.setText(nomModulo);

    }


    public void llenarGastos() throws ParseException {

        BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
        List lista = conexion.ConsultarAyuda(idModuloFinal);

        Log.e("listaGastos", lista.size()+"");

        datosgrid = new String[(lista.size()*3) + 3];
        //  datosgrid[0]="Intento";
        datosgrid[0]="Date";
        datosgrid[1]="Hits";
        datosgrid[2]="Number of questions";

        int contador=3;

        for(Object datos: lista){
            AyudaDTO elementos=(AyudaDTO) datos;
            //    datosgrid[contador]=String.valueOf(elementos.getId());

            String mes = elementos.getFecha().substring(4,7);
            String dia = elementos.getFecha().substring(8,10);
            String year = elementos.getFecha().substring(26,28);

            //Mon Apr 23 14:44:52 CDT 2018

            datosgrid[contador]=dia + "/" + mes + "/" + year;
            datosgrid[contador+1]=elementos.getAciertos()+"";
            datosgrid[contador+2]=elementos.getTotalPreguntas()+"";

            contador+=3;
        }

        for(int a=0; a<datosgrid.length;a++){
            if(datosgrid[a]==null){
                datosgrid[a]="-";
            }

        }

        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datosgrid);
        resultados.setAdapter(adaptador);
        resultados.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datosgrid) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.white));
                return view;
            }
        });
//    	Log.e("Ingresos", datosgrid[0]);


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
            Intent linsertar=new Intent(Resultados.this, MainActivity.class);
            startActivity(linsertar);

         /*   img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(Resultados.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
        }



    }
}
