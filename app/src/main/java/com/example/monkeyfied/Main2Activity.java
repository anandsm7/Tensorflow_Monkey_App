package com.example.monkeyfied;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.File;
import java.io.FileOutputStream;

public class Main2Activity extends AppCompatActivity {
    static {
        System.loadLibrary("tensorflow_inference");
    }
    private static final String MODEL_NAME="file:///android_asset/Monkey10.pb";
    //private static final String INPUT_NODE="reshape_2_input";
    private static final String INPUT_NODE="input_2";
    private static final long INPUT_SHAPE=224;
    //private static final String OUTPUT_NODE="dense_6/Softmax";
    private static final String OUTPUT_NODE="dense_8/Softmax";
    TensorFlowInferenceInterface inferenceInterface;
    Camera camera;
    FrameLayout framelayout;
    ShowCamera showcamera;
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageview=(ImageView) findViewById(R.id.image_view);
        inferenceInterface=new TensorFlowInferenceInterface(getAssets(),MODEL_NAME);
        Intent cam_intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam_intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap monkey_image=(Bitmap) data.getExtras().get("data");
        imageview.setImageBitmap(monkey_image);
        float[] pixelBuffer=formatImageData(monkey_image);
        float[] results=makePrediction(pixelBuffer);
        display(results);

    }
    public void loadLibrary(View view){
        Intent myIntent4=new Intent(this,Main4Activity.class);
        startActivity(myIntent4);
    }
    public void captureImage(View view){

        }
    private void display(float[] results) {
        String[] answers = {"Mantled Howler",
                "Patas Monkey",
                "Bald Uakari",
                "Japanese Macaque",
                "Pygmy Marmoset",
                "White Capuchin",
                "Silvery Marmoset",
                "Squirrel Monkey",
                "Black Monkey",
                "Nilgiri langur"};
        int maxIndex=0;
        float max=0;
        for(int i=0;i<10;i++){
            Log.i("point",Float.toString(results[i]));
            Log.i("name",answers[i]);
            if(results[i]>max){
                max=results[i];
                Log.e("score",Float.toString(max));
                maxIndex=i;
            }
        }
        if(max<0.6f){
            Intent wrongIntent=new Intent(this,Main4Activity.class);
            startActivity(wrongIntent);
        }
        else {
            String answer = answers[maxIndex];
            Intent myIntent = new Intent(this, Main3Activity.class);
            myIntent.putExtra("type", answer);
            startActivity(myIntent);
        }
    }

    private float[] makePrediction(float[] pixelBuffer){
        inferenceInterface.feed(INPUT_NODE,pixelBuffer,1,INPUT_SHAPE,INPUT_SHAPE,3);
        inferenceInterface.run(new String[] {OUTPUT_NODE});
        float[] results=new float[10];
        inferenceInterface.fetch(OUTPUT_NODE,results);
        return results;
    }

    private float[] formatImageData(Bitmap bitmap) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        int[] intArray = new int[224*224];
        scaledBitmap.getPixels(intArray, 0, 224, 0, 0, 224, 224);
        float[] floatArray = new float[224*224*3];
        for (int i = 0; i < intArray.length; i++) {
            floatArray[i * 3 + 0] = ((intArray[i] >> 16) & 0xff) / 255.0f; //Red
            floatArray[i * 3 + 1] = ((intArray[i] >> 8) & 0xff) / 255.0f; //Green
            floatArray[i * 3 + 2] = (intArray[i] & 0xff) / 255.0f;//blue
        }
        return  floatArray;
    }
}
