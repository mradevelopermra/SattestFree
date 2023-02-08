package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.ExamenDTO;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultadoExamen extends AppCompatActivity implements View.OnClickListener{

    String resultadoFinal;
    String totalPreguntas;
    private ImageView img_flecha;
    TextView resultadoExamen;
    GridView resultadoExamenTabla;
    String[] datosgrid;
    String tipoTest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_examen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

        resultadoExamen = (TextView) findViewById(R.id.resultadoExamen);
        resultadoExamenTabla = (GridView) findViewById(R.id.resultadoExamenTabla);

        resultadoFinal = getIntent().getExtras().getString("aciertos")+"";
        tipoTest = getIntent().getExtras().getString("areaLicenciatura");

        if(resultadoFinal.equalsIgnoreCase("historia")){
            resultadoExamen.setText("Scores of test");
        }else{
            totalPreguntas = "80";

            BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
            ExamenDTO datos=new ExamenDTO();

            Date currentTime = Calendar.getInstance().getTime();

            datos.setFecha(currentTime+"");
            datos.setAciertos(Double.parseDouble(resultadoFinal));
            datos.setTotalPreguntas(Double.parseDouble(totalPreguntas));
            datos.setTipoTest(tipoTest);

            if(conexion.insertaExamen(datos)){
                // Toast.makeText(this, getResources().getString(R.string.IngresoGaurdo), Toast.LENGTH_SHORT).show();
            }else{
                //  Toast.makeText(this, getResources().getString(R.string.IngresoGaurdoNo), Toast.LENGTH_SHORT).show();
            }

            resultadoExamen.setText("" + resultadoFinal + " corrects of " + totalPreguntas + " ");


        }



        try {
            llenarGastos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void llenarGastos() throws ParseException {

        BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
        List lista = conexion.ConsultarExamenes();

        Log.e("lista", lista.size()+"");

        if(lista.size()<=0){
            Toast toast = Toast.makeText(this,getResources().getString(R.string.noresultadoshistoricos), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            regresar();
        }

        datosgrid = new String[(lista.size()*3) + 3];
        //  datosgrid[0]="Intento";
        datosgrid[0]="Date";
        datosgrid[1]="Correct answers";
        datosgrid[2]="Test";

        int contador=3;

        for(Object datos: lista){
            ExamenDTO elementos=(ExamenDTO) datos;
            //    datosgrid[contador]=String.valueOf(elementos.getId());

            String mes = elementos.getFecha().substring(4,7);
            String dia = elementos.getFecha().substring(8,10);
            String year = elementos.getFecha().substring(26,28);

            //Mon Apr 23 14:44:52 CDT 2018

            datosgrid[contador]=dia + "/" + mes + "/" + year;
            datosgrid[contador+1] = (int)elementos.getAciertos()+"";
            datosgrid[contador+2] = elementos.getTipoTest()+"";

            contador+=3;
        }

        for(int a=0; a<datosgrid.length;a++){
            if(datosgrid[a]==null){
                datosgrid[a]="-";
            }

        }

        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datosgrid);
        resultadoExamenTabla.setAdapter(adaptador);
        resultadoExamenTabla.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datosgrid) {
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

    private void regresar(){
        finish();
        Intent linsertar=new Intent(ResultadoExamen.this, MainActivity.class);
        startActivity(linsertar);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v.getId()==findViewById(R.id.img_flecha).getId()){

            regresar();
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



    }
}
