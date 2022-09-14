package com.mariana.parcial1ramirezmariana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import static com.mariana.parcial1ramirezmariana.MainActivity.NOTIFICACION_ID;

public class ActivityBeneficios extends AppCompatActivity {

    protected VideoView videoView;
    protected MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICACION_ID);

        videoView = findViewById(R.id.video_beneficios);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        //Reproducir un video en especifico
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setMediaController(mediaController);
    }
}