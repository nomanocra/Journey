package fr.m2ihm.journey.activites;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.m2ihm.journey.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class ActiveCameraActivity extends Activity implements
		SurfaceHolder.Callback {
	/** Called when the activity is first created. */
	private Camera camera;
	private SurfaceView surfaceCamera;
	private Boolean isPreview;
	private FileOutputStream stream;

	private String lieu;
	private String commentaire;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// On met l'application en plein écran et sans barre de titre
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		isPreview = false;
		
		lieu = getIntent().getExtras().getString("lieu");
		commentaire = getIntent().getExtras().getString("commentaire");

		// Si vous voulez gérer plusieurs caméra sur votre mobile / tablette
		for (int nbCamera = 0; nbCamera < Camera.getNumberOfCameras(); nbCamera++) {
			Camera.CameraInfo informationsCamera = new Camera.CameraInfo();
			Camera.getCameraInfo(nbCamera, informationsCamera);

			if (informationsCamera != null) {
				switch (informationsCamera.facing) {
				case Camera.CameraInfo.CAMERA_FACING_FRONT:
					// Caméra facial
					break;
				case Camera.CameraInfo.CAMERA_FACING_BACK:
					// Caméra arrière
					break;
				}
			}

		}

		// On applique notre layout
		setContentView(R.layout.camera);

		// On récupère notre surface pour le preview
		surfaceCamera = (SurfaceView) findViewById(R.id.surfaceViewCamera);

		// Quand on clique sur notre surface
		surfaceCamera.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// On prend une photo
				if (camera != null) {
					SavePicture();
				}

			}
		});

		// M�thode d'initialisation de la cam�ra
		InitializeCamera();
	}

	public void InitializeCamera() {
		// On attache nos retour du holder � notre activite
		surfaceCamera.getHolder().addCallback(this);

		// On sp�cifie le type du holder en mode SURFACE_TYPE_PUSH_BUFFERS
		surfaceCamera.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// Retour sur l'application
	@Override
	public void onResume() {
		super.onResume();
		camera = Camera.open();
	}

	// Mise en pause de l'application
	@Override
	public void onPause() {
		super.onPause();

		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	// Implémentation du surface holder

	// Quand la surface change
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		// Si le mode preview est lancé alors on le stop
		if (isPreview) {
			camera.stopPreview();
		}
		// On récupère les parametres de la camera
		Camera.Parameters parameters = camera.getParameters();
		List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
		// On change la taille
		Camera.Size previewSize = previewSizes.get(0);
		
		parameters.setPreviewSize(previewSize.width, previewSize.height);

		// On applique nos nouveaux parametres
		camera.setParameters(parameters);

		try {
			// On attache notre previsualisation de la camera au holder de la
			// surface
			camera.setPreviewDisplay(surfaceCamera.getHolder());
		} catch (IOException e) {
		}

		// On lance la previeuw
		camera.startPreview();

		isPreview = true;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// On prend le controle de la camera
		if (camera == null)
			camera = Camera.open();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// On arrête la camera et on rend la main
		if (camera != null) {
			camera.stopPreview();
			isPreview = false;
			camera.release();
		}
	}

	// Callback pour la prise de photo
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			if (data != null) {
				// Enregistrement de votre image
				try {
					if (stream != null) {
						stream.write(data);
						stream.flush();
						stream.close();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				// On red�marre la pr�visualisation
				camera.startPreview();
			}
		}
	};

	private void SavePicture(){
		String fileName = "";
		try {
			SimpleDateFormat timeStampFormat = new SimpleDateFormat(
					"yyyy-MM-dd-HH.mm.ss");
			
			fileName = "photo_" + timeStampFormat.format(new Date())
					+ ".jpg";
			
			
			
			
			Intent intent = new Intent(this, AjouterEvenementActivity.class);
			intent.putExtra("nomMedia", ""+ Media.EXTERNAL_CONTENT_URI + "\\" + fileName);
			intent.putExtra("typeMedia", "photo");
			intent.putExtra("lieu", lieu);
			intent.putExtra("commentaire", commentaire);
			startActivity(intent);
			finish();
			
			// Metadata pour la photo
			ContentValues values = new ContentValues();
			values.put(Media.TITLE, fileName);
			values.put(Media.DISPLAY_NAME, fileName);
			values.put(Media.DESCRIPTION, "Image prise par FormationCamera");
			values.put(Media.DATE_TAKEN, new Date().getTime());
			values.put(Media.MIME_TYPE, "image/jpeg");
			// Support de stockage
			Uri taken = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,
					values);
			
			// Ouverture du flux pour la sauvegarde
			
	
			stream = (FileOutputStream) getContentResolver().openOutputStream(
					taken);
			camera.takePicture(null, pictureCallback, pictureCallback);
			
			Toast messagePhotoTaken = Toast
					.makeText(
							ActiveCameraActivity.this,
							"! Photo prise ! \n Titre : " + fileName,
							Toast.LENGTH_LONG);
			messagePhotoTaken.show();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}