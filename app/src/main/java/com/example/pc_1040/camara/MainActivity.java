package com.example.pc_1040.camara;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
RelativeLayout l;
ImageButton b;
Snackbar s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = findViewById(R.id.milayout);
        b = findViewById(R.id.imageButton);
        b.setEnabled(false);

       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(camara,200);
           }
       });



        s1 = Snackbar.make(l,"La camara esta lista para usar.", Snackbar.LENGTH_LONG);
        s2 = Snackbar.make(l, "No hay autorizacion para la camara", Snackbar.LENGTH_LONG)
        .setAction("solicitar", new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               hacerPeticion();
            }

        });

        if (verificarPermisos()) {
            iniciarCamara();
        }else {
            justificarSolicitud();
        }

    }

    private void justificarSolicitud() {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,CAMERA)) {
          s2.show();

      }else {
          hacerPeticion();
      }
    }

    private void iniciarCamara() {
     s1.show();
     b.setEnabled(true);
    }

    private boolean verificarPermisos() {

       if (Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
           return true;
       }
       if(ActivityCompat.checkSelfPermission(this,CAMERA) == PackageManager.PERMISSION_GRANTED){
           return true;
       }
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    //RESPUESTA SOLICITUD DE PERMISOS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //aqui recibimos rpta de muestra de peticion de permisos

        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                s1.show();
                b.setEnabled(true);
            }else{
                s2.show();
                b.setEnabled(false);
            }
        }

    }

    private void hacerPeticion() {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},100);



    }
}
