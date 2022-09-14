package com.mariana.parcial1ramirezmariana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText curp, nombre, apellidos, domicilio, ingresos;
    private Spinner tipo;
    ScrollView layout;
    private PendingIntent pendingIntentSi;
    private PendingIntent pendingIntentNo;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.scroll);

        curp = findViewById(R.id.edtCurp);
        nombre = findViewById(R.id.edtNombre);
        apellidos = findViewById(R.id.edtApellidos);
        domicilio = findViewById(R.id.edtDomicilio);
        ingresos = findViewById(R.id.edtIngresos);
        tipo = findViewById(R.id.spnTipoTarjeta);

        String[] listaTipos = {"Tradicional", "Oro", "Platino"};
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaTipos);
        tipo.setAdapter(adapterTipos);
    }




    public void verificarDatos(View view){

        boolean verificar = true;
        if(curp.getText().toString().isEmpty() || curp.getText().toString().length() < 18 || curp.getText().toString().length() > 18){
            curp.setError("Campo requerido. Deben ser 18 espacios");
            verificar = false;
        }
        if(nombre.getText().toString().isEmpty()){
            nombre.setError("Campo requerido");
            verificar = false;
        }
        if(apellidos.getText().toString().isEmpty()){
            apellidos.setError("Campo requerido");
            verificar = false;
        }
        if(domicilio.getText().toString().isEmpty()){
            domicilio.setError("Campo requerido");
            verificar = false;
        }
        if(ingresos.getText().toString().isEmpty()) {
            ingresos.setError("Campo requerido");
            verificar = false;
        }

        if(!verificar){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Faltan datos");
            builder.setMessage("No se puede registrar hasta que se llenen todos los datos de este formulario ");
            builder.setPositiveButton("Aceptar", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            Solicitud objeto = new Solicitud(curp.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), domicilio.getText().toString(), Double.parseDouble(ingresos.getText().toString()), tipo.getSelectedItem().toString());

            if(objeto.validar(Double.parseDouble(ingresos.getText().toString()), tipo.getSelectedItem().toString())){

                Snackbar snackbar = Snackbar.make(layout, "Datos Registrados", Snackbar.LENGTH_LONG);
                snackbar.show();
                limpiar();

                setPendingIntentSi(objeto);
                setPendingIntentNo();
                crearCanalNotificacion();
                crearNotificacion();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Tarjeta seleccionada");
                builder.setMessage("Usted no cumple con las características para obtener esta tarjeta");
                builder.setPositiveButton("Aceptar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }


    public void limpiarRegistro(View view){
        limpiar();
    }


    public void limpiar(){
        curp.setText("");
        nombre.setText("");
        apellidos.setText("");
        domicilio.setText("");
        ingresos.setText("");
        tipo.setSelected(false);

        curp.requestFocus();
    }



    private void setPendingIntentSi(Solicitud solicitud) {
        Intent intent = new Intent(MainActivity.this, ActivityCita.class);
        intent.putExtra("objeto", solicitud);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ActivityCita.class);
        stackBuilder.addNextIntent(intent);
        pendingIntentSi = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    private void setPendingIntentNo() {
        Intent intent = new Intent(MainActivity.this, ActivityBeneficios.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ActivityBeneficios.class);
        stackBuilder.addNextIntent(intent);
        pendingIntentNo = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }




    private void crearCanalNotificacion() {
        //Validar si es versiòn Android superior a O (API >=26 )
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Nombre del canal
            CharSequence name = "Notificación";
            //Instancia para gestionar el canal y el servicio de la notificación
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }



    private void crearNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("Registro completado");
        builder.setContentText("¿Qué opción desea conocer?");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //Especifica la Activity que aparece al momento de elegir la notificación
        builder.setContentIntent(pendingIntentSi);

        //Se agregan las opciones que aparecen en la notificación
        builder.addAction(R.drawable.ic_thumb_up_black_24dp,"Cita",pendingIntentSi);
        builder.addAction(R.drawable.ic_thumb_down_black_24dp,"Beneficios",pendingIntentNo);

        //Instancia que gestiona la notificación con el dispositivo
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}