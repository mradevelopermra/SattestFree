package com.mra.satpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mra.satpro.dao.BaseDaoImagenBillete;
import com.mra.satpro.dto.AyudaDTO;
import com.mra.satpro.dto.EscuelasDTO;

import java.text.ParseException;
import java.util.List;

import static com.mra.satpro.utilerias.Escuelas.arrayAreas;
import static com.mra.satpro.utilerias.Escuelas.arrayClasificacionAreas;

public class ResultadoModulo extends AppCompatActivity implements View.OnClickListener {

    Spinner modulos;

    String [] periodos;
    String [] idModulo;

    String modulo = "";
    GridView resultados_modulos;
    String[] datosgrid;

    private ImageView img_flecha;
    String division;

    String areaLicenciatura = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_modulo);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img_flecha = (ImageView) findViewById(R.id.img_flecha);
        img_flecha.setOnClickListener(this);

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

        /*buscaOpciones();
        if(areaLicenciatura.equalsIgnoreCase("Área 1: Ciencias Físico Matemáticas y de las Ingenierias")){
            periodos = new String[]{
                    "Español",
                    "Física",
                    "Matemáticas",
                    "Literatura",
                    "Geografía",
                    "Biología",
                    "Química",
                    "Historia Universal",
                    "Historia de México"
            };
            idModulo = new String[]{
                    "1",
                    "12",
                    "6",
                    "7",
                    "5",
                    "4",
                    "11",
                    "3",
                    "8"

            };
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 2: Ciencias Biológicas, Quimicas y de la Salud")){

            periodos = new String[]{
                    "Español",
                    "Física",
                    "Matemáticas",
                    "Literatura",
                    "Geografía",
                    "Biología",
                    "Química",
                    "Historia Universal",
                    "Historia de México"
            };
            idModulo = new String[]{
                    "1",
                    "12",
                    "6",
                    "7",
                    "5",
                    "4",
                    "11",
                    "3",
                    "8"
            };
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 3: Ciencias Sociales")){

            periodos = new String[]{
                    "Español",
                    "Física",
                    "Matemáticas",
                    "Literatura",
                    "Geografía",
                    "Biología",
                    "Química",
                    "Historia Universal",
                    "Historia de México"
            };

            idModulo = new String[]{
                    "1",
                    "12",
                    "6",
                    "7",
                    "5",
                    "4",
                    "11",
                    "3",
                    "8"
            };
        }
        if(areaLicenciatura.equalsIgnoreCase("Área 4: Humanidades y Artes")){
            periodos = new String[]{
                    "Español",
                    "Física",
                    "Matemáticas",
                    "Literatura",
                    "Geografía",
                    "Biología",
                    "Química",
                    "Historia Universal",
                    "Historia de México",
                    "Filosofía"
            };

            idModulo = new String[]{
                    "1",
                    "12",
                    "6",
                    "7",
                    "5",
                    "4",
                    "11",
                    "3",
                    "8",
                    "2"

            };


        }*/

        modulo = "6";


        modulos = (Spinner) findViewById(R.id.modulos);

        resultados_modulos = (GridView) findViewById(R.id.resultados_modulos);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,  periodos);

        modulos.setAdapter(new ResultadoModulo.MyAdapter5(this, R.layout.row, periodos));
        //modulos.setAdapter(adapter3);

        modulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item3 = parent.getItemAtPosition(pos);
                //    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorletra));
                //    ((TextView) parent.getChildAt(0)).setTextSize(20);

                for(int i=0; i<periodos.length; i++){
                    String moduloElegido = String.valueOf(item3);
                    if(periodos[i].equalsIgnoreCase(moduloElegido)){
                        modulo = idModulo[i];
                    }
                }

                try {
                    llenarGastos();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Log.e("modulo ", modulo);

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

    public void llenarGastos() throws ParseException {

        BaseDaoImagenBillete conexion=new BaseDaoImagenBillete(this);
        List lista = conexion.ConsultarAyuda(modulo);
        List lista2 = conexion.ConsultarTodoAyuda();

        if(lista.size()<=0){
            Toast toast = Toast.makeText(this,getResources().getString(R.string.noresultadoshistoricosmateria), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


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
        resultados_modulos.setAdapter(adaptador);
        resultados_modulos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datosgrid) {
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
            Intent linsertar=new Intent(ResultadoModulo.this, MainActivity.class);
            startActivity(linsertar);

        /*    img_flecha.setImageResource(R.drawable.flecha_back);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    Intent linsertar=new Intent(ResultadoModulo.this, MainActivity.class);
                    startActivity(linsertar);

                }
            };
            Timer t = new Timer();
            t.schedule(task, 100);*/
        }



    }

}
