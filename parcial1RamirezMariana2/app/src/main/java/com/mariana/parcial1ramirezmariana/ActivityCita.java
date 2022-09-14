package com.mariana.parcial1ramirezmariana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.mariana.parcial1ramirezmariana.MainActivity.NOTIFICACION_ID;

public class ActivityCita extends AppCompatActivity {

    TextView curp, nombre, apellidos, domicilio, ingresos, tipo, horario;
    Solicitud solicitud;

    private int anioAlarma = 2022;
    private int mesAlarma = 8;
    private int diaAlarma = 28;
    private int horaAlarma = 7;
    private int minutoAlarma =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        //habilitar la flecha hacia atras en la barra de estado
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICACION_ID);

        nombre = findViewById(R.id.txtNomResultado);
        curp = findViewById(R.id.txtCurpResultado);
        apellidos = findViewById(R.id.txtApeResultado);
        domicilio=findViewById(R.id.txtDomResultado);
        ingresos=findViewById(R.id.txtIngResultado);
        tipo=findViewById(R.id.txtTipoResultado);
        horario=findViewById(R.id.txtFechaHora);
        solicitud = (Solicitud) getIntent().getSerializableExtra("objeto");

        nombre.setText(solicitud.getNombre());
        curp.setText(solicitud.getCurp());
        apellidos.setText(solicitud.getApellidos());
        domicilio.setText(solicitud.getDomicilio());
        ingresos.setText(String.valueOf(solicitud.getIngresos()));
        tipo.setText(solicitud.getTipoTarjeta());
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                //Instancia de la Activity a donde se regresarà la Activity Actual
                Intent intent = new Intent(ActivityCita.this, MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected




    public void regresarMain(View view){
        Toast.makeText(this, "Registrado", Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Fecha registrada" , Toast.LENGTH_SHORT).show();

        //Establecer la alarma
        programarAlarmaEnSistema("CITA", horaAlarma, minutoAlarma);
    }




    public void setFechaAlarma(View view){
        //Instancia para calendario
        Calendar horarioHoy = Calendar.getInstance();
        //horarioHoy.setTimeInMillis(System.currentTimeMillis());
        //obtener los valores actuales del sistema
        int anioActual = horarioHoy.get(Calendar.YEAR);
        int mesActual = horarioHoy.get(Calendar.MONTH);
        int diaActual = horarioHoy.get(Calendar.DAY_OF_MONTH);
        //Fecha para elegir la cita
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityCita.this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        diaAlarma = i2;
                        mesAlarma = i1+1;
                        anioAlarma = i;
                        horario.setText(diaAlarma+"/"+mesAlarma+"/"+anioAlarma+" "+horaAlarma+":"+minutoAlarma);
                    }
                },anioActual,mesActual,diaActual);
        datePickerDialog.setTitle("Fecha de cita");
        datePickerDialog.show();
    }//setFechaAlarma



    public void setHorarioAlarma(View view){
        //Instancia para calendario
        Calendar horarioHoy = Calendar.getInstance();
        horarioHoy.setTimeInMillis(System.currentTimeMillis());
        //obtener los valores actuales del sistema
        int horaActual = horarioHoy.get(Calendar.HOUR_OF_DAY);
        int minutoActual = horarioHoy.get(Calendar.MINUTE);
        //Horario para elegir la cita
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityCita.this, new
                TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        horaAlarma = i;
                        minutoAlarma = i1;
                        horario.setText(diaAlarma+"/"+mesAlarma+"/"+anioAlarma+" "+horaAlarma+":"+minutoAlarma);
                    }
                },horaActual,minutoActual,true);

        //Definir titulo
        timePickerDialog.setTitle("Horario de cita");
        timePickerDialog.show();
    }



    public void programarAlarmaEnSistema(String mensaje, int hora, int minuto){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, mensaje)
                .putExtra(AlarmClock.EXTRA_HOUR, hora)
                .putExtra(AlarmClock.EXTRA_MINUTES, minuto);
        startActivity(intent);

    }
}