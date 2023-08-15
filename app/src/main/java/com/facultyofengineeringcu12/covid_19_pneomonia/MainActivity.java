package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facultyofengineeringcu12.covid_19_pneomonia.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView result, confidence;
    ImageView imageView;
    Button picture ,select;
    int imageSize = 224;

    private Button shareButton ,saveImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
        select = findViewById(R.id.buttonSelect);

        getSupportActionBar().setTitle("Detection");



        shareButton = findViewById(R.id.Share);

        saveImageButton = findViewById(R.id.Savebtn);

        select.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                }
            }

        });

        picture.setOnClickListener(view -> {
            // Launch camera if we have permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });



        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Capture the screenshot of the current view
                Bitmap screenshot = takeScreenshot();

                // Save the screenshot to the device's external storage
                String imagePath = saveScreenshotToGallery(screenshot);
                if (imagePath != null) {
                    Toast.makeText(MainActivity.this, "Screenshot saved to gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
                }

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture the screenshot of the current view
                Bitmap screenshot = takeScreenshot();

                // Save the screenshot to the device's external storage
                String imagePath = saveScreenshot(screenshot);
                if (imagePath != null) {
                    // Share the image
                    shareImage(imagePath);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void classifyImage(Bitmap image){
        try {
            // Create a new instance of the Model.
            Model model = Model.newInstance(getApplicationContext());

            // Create a TensorBuffer for input.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

            // Allocate a ByteBuffer for the image data.
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // Get pixel values from the image.
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // Iterate over pixels and add RGB values to the ByteBuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            // Load the ByteBuffer data into the input TensorBuffer.
            inputFeature0.loadBuffer(byteBuffer);

            // Run model inference and get outputs.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Get class confidences from the output tensor.
            float[] confidences = outputFeature0.getFloatArray();

            // Find the index of the class with the highest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            // Define class labels.
            String[] classes = {"Normal", "Covid'19", "Pneomonia"};

            // Set the predicted class label.
            result.setText(classes[maxPos]);

            // Generate a string displaying class confidences.
            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            // Set the confidence text.
            confidence.setText(s);

            // Release model resources.
            model.close();
        } catch (IOException e) {
            // Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the captured image as a Bitmap.
            Bitmap image = (Bitmap) data.getExtras().get("data");

            // Resize the image to a square.
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            // Resize the image to the required input size and classify.
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        else{
            // Get the selected image's URI.
            Uri dat = data.getData();
            Bitmap image = null;
            try {
                // Retrieve the image from the URI.
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(image);

            // Resize the image to the required input size and classify.
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }

        // Call the parent method for handling the result.
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Bitmap takeScreenshot() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        return screenshot;
    }

    private String saveScreenshot(Bitmap screenshot) {
        try {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File screenshotFile = new File(directory, "screenshot.png");

            FileOutputStream outputStream = new FileOutputStream(screenshotFile);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return screenshotFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void shareImage(String imagePath) {
        File imageFile = new File(imagePath);
        Uri imageUri = FileProvider.getUriForFile(this, "com.facultyofengineeringcu12.covid_19_pneomonia.fileprovider", imageFile);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Add this line to grant read permission
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    private String saveScreenshotToGallery(Bitmap screenshot) {
        try {
            // Get the directory for storing images in the gallery
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            // Create a unique filename using timestamp
            String timestamp = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            }
            String filename = "detection_screenshot_" + timestamp + ".png";

            File screenshotFile = new File(directory, filename);

            // Save the screenshot to the file
            FileOutputStream outputStream = new FileOutputStream(screenshotFile);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Update the gallery so that the screenshot appears in the device's gallery app
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(screenshotFile);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);

            return screenshotFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}