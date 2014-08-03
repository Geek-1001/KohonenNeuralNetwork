package com.hornet.kohonenneuralnetwork.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hornet.kohonenneuralnetwork.KohonenNetwork;
import com.hornet.kohonenneuralnetwork.KohonenNetworkBuilder;
import com.hornet.kohonenneuralnetwork.KohonenNetworkLearningBuilder;
import com.hornet.kohonenneuralnetwork.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ahmed on 8/2/14.
 */
public class BitmapCompareDemoActivity extends Activity {

    private KohonenNetwork network;
    private double[] currentSignal;

    @Override
    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.activity_bitmap_demo);
        currentSignal = null;
    }

    public void onImageChoose(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 0);
    }

    public void onLearn(View view) {
        Bitmap[] learningBitmapsArray = new Bitmap[32];

        // TODO: more learning vectors
        // TODO: track learning process which image to which cluster recognized.

        learningBitmapsArray[0] = BitmapFactory.decodeResource(getResources(), R.drawable.one_arial_regular);
        learningBitmapsArray[1] = BitmapFactory.decodeResource(getResources(), R.drawable.one_arial_italic);
        learningBitmapsArray[2] = BitmapFactory.decodeResource(getResources(), R.drawable.one_arial_bold);

        learningBitmapsArray[3] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_regular);
        learningBitmapsArray[4] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticaue_italic);
        learningBitmapsArray[5] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_bold);
        learningBitmapsArray[6] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_medium);
        learningBitmapsArray[7] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_thin);
        learningBitmapsArray[8] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_light);
        learningBitmapsArray[9] = BitmapFactory.decodeResource(getResources(), R.drawable.one_helveticanue_ultralight);

        learningBitmapsArray[10] = BitmapFactory.decodeResource(getResources(), R.drawable.one_ptsans_regular);
        learningBitmapsArray[11] = BitmapFactory.decodeResource(getResources(), R.drawable.one_ptsans_italic);
        learningBitmapsArray[12] = BitmapFactory.decodeResource(getResources(), R.drawable.one_ptsans_bold);

        learningBitmapsArray[13] = BitmapFactory.decodeResource(getResources(), R.drawable.one_timesnewroman_regular);
        learningBitmapsArray[14] = BitmapFactory.decodeResource(getResources(), R.drawable.one_timesnewroman_italic);
        learningBitmapsArray[15] = BitmapFactory.decodeResource(getResources(), R.drawable.one_timesnewroman_bold);

        learningBitmapsArray[16] = BitmapFactory.decodeResource(getResources(), R.drawable.two_arial_regular);
        learningBitmapsArray[17] = BitmapFactory.decodeResource(getResources(), R.drawable.two_arial_italic);
        learningBitmapsArray[18] = BitmapFactory.decodeResource(getResources(), R.drawable.two_arial_bold);

        learningBitmapsArray[19] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_regular);
        learningBitmapsArray[20] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_italic);
        learningBitmapsArray[21] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_bold);
        learningBitmapsArray[22] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_medium);
        learningBitmapsArray[23] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_thin);
        learningBitmapsArray[24] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_light);
        learningBitmapsArray[25] = BitmapFactory.decodeResource(getResources(), R.drawable.two_helveticanue_ultralight);

        learningBitmapsArray[26] = BitmapFactory.decodeResource(getResources(), R.drawable.two_ptsans_regular);
        learningBitmapsArray[27] = BitmapFactory.decodeResource(getResources(), R.drawable.two_ptsans_italic);
        learningBitmapsArray[28] = BitmapFactory.decodeResource(getResources(), R.drawable.two_ptsans_bold);

        learningBitmapsArray[29] = BitmapFactory.decodeResource(getResources(), R.drawable.two_timesnewroman_regular);
        learningBitmapsArray[30] = BitmapFactory.decodeResource(getResources(), R.drawable.two_timesnewroman_italic);
        learningBitmapsArray[31] = BitmapFactory.decodeResource(getResources(), R.drawable.two_timesnewroman_bold);

        KohonenNetworkLearningBuilder learningBuilder = new KohonenNetworkLearningBuilder();
        learningBuilder.setLearningEraCount(7);
        learningBuilder.setLearningNorm(0.8, 0.1, 0.1);
        learningBuilder.setUpdateRadius(2);

        List<double[]> learningVectors = new ArrayList<double[]>();
        for(int i = 0; i < learningBitmapsArray.length; i++) {
            double[] currentLearningVector = getBytesFromBitmap(learningBitmapsArray[i]);
            learningVectors.add(currentLearningVector);
        }
        learningBuilder.setLearningVectors(learningVectors);
        network.startLearning(learningBuilder);
    }

    public void onRecognize(View view) {
        if(currentSignal != null) {
            network.setInputSignal(currentSignal);
            double[] outputSignal = network.getOutputSignal();

            TextView log = (TextView) findViewById(R.id.output);
            log.setText("");
            for(double value : outputSignal){
                log.append(" | " + value);
            }

            int number = 1;
            if(outputSignal[0] != 1.0) {
                number = 2;
            }
            log.append("\n\n It`s number : " + number);

            log.append("\n\n" + System.currentTimeMillis());

            currentSignal = null;
        }
    }

    public double[] getBytesFromBitmap(Bitmap bitmap) {
        double[] bitmapBytes;
        int[] bitmapBytesInt = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(bitmapBytesInt, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for(int i = 0; i < bitmapBytesInt.length; ++i) {
            if(bitmapBytesInt[i] != 0) {
                bitmapBytesInt[i] = 1;
            }
        }
        bitmapBytes = copyFromIntArray(bitmapBytesInt);

        return bitmapBytes;
    }

    public double[] copyFromIntArray(int[] source) {
        double[] dest = new double[source.length];
        for(int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }

        return dest;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                ImageView imageViewBitmapFirst = (ImageView) findViewById(R.id.bitmap_1);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                imageViewBitmapFirst.setImageBitmap(bitmap);

                currentSignal = getBytesFromBitmap(bitmap);

                int[] bitmapBytesInt = new int[bitmap.getWidth() * bitmap.getHeight()];
                bitmap.getPixels(bitmapBytesInt, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                for (int i = 0; i < bitmapBytesInt.length; ++i) {
                    if (bitmapBytesInt[i] != 0) {
                        bitmapBytesInt[i] = Color.BLACK;
                    }
                }
                bitmap = Bitmap.createBitmap(bitmapBytesInt, 0, bitmap.getWidth(), bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                ImageView imageViewBitmapSecond = (ImageView) findViewById(R.id.bitmap_2);
                imageViewBitmapSecond.setImageBitmap(bitmap);

                int clusterNumber = 2;
                int inputNumber = bitmap.getWidth() * bitmap.getHeight();
                KohonenNetworkBuilder networkBuilder = new KohonenNetworkBuilder(clusterNumber, inputNumber);

                Bitmap oneBitmapStart = BitmapFactory.decodeResource(getResources(), R.drawable.one_arial_regular);
                Bitmap twoBitmapStart = BitmapFactory.decodeResource(getResources(), R.drawable.two_arial_regular);
                List<double[]> weights = new ArrayList<double[]>();
                weights.add(getBytesFromBitmap(oneBitmapStart));
                weights.add(getBytesFromBitmap(twoBitmapStart));
                networkBuilder.setEdgesWeightList(weights);

                network = new KohonenNetwork(this, networkBuilder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
