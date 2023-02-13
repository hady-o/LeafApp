package com.example.leafapp.ui.ResultFragment;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Objects;

public class ImageClassifier {
    static int INPUT_SIZE = 256;
    static  int PIXEL_SIZE = 3;

    static AssetManager assetManager;
    static String modelPath = "";
    static Interpreter interpreter;
    public static String[] labes = {
            "Alstonia Scholaris___diseased",
            "Alstonia Scholaris___healthy",
            "Apple___Apple_scab",
            "Apple___Black_rot",
            "Apple___Cedar_apple_rust",
            "Apple___healthy",
            "Arjun___diseased",
            "Arjun___healthy",
            "Bael___diseased",
            "Basil___healthy",
            "Blueberry___healthy",
            "Cherry_(including_sour)___healthy",
            "Cherry_(including_sour)___Powdery_mildew",
            "Chinar ___diseased",
            "Chinar ___healthy",
            "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot",
            "Corn_(maize)___Common_rust_",
            "Corn_(maize)___healthy",
            "Corn_(maize)___Northern_Leaf_Blight",
            "Gauva___diseased",
            "Gauva___healthy",
            "Grape___Black_rot",
            "Grape___Esca_(Black_Measles)",
            "Grape___healthy",
            "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)",
            "Jamun___diseased",
            "Jamun___healthy",
            "Jatropha___diseased",
            "Jatropha___healthy",
            "Lemon___diseased",
            "Lemon___healthy",
            "Mango ___healthy",
            "Mango___diseased",
            "Orange___Haunglongbing_(Citrus_greening)",
            "Peach___Bacterial_spot",
            "Peach___healthy",
            "Pepper,_bell___Bacterial_spot",
            "Pepper,_bell___healthy",
            "Pomegranate___diseased",
            "Pomegranate___healthy",
            "PongamiaPinnata___diseased",
            "PongamiaPinnata___healthy",
            "Potato___Early_blight",
            "Potato___healthy",
            "Potato___Late_blight",
            "Raspberry___healthy",
            "Soybean___healthy",
            "Squash___Powdery_mildew",
            "Strawberry___healthy",
            "Strawberry___Leaf_scorch",
            "Tomato___Bacterial_spot",
            "Tomato___Early_blight",
            "Tomato___healthy",
            "Tomato___Late_blight",
            "Tomato___Leaf_Mold",
            "Tomato___Septoria_leaf_spot",
            "Tomato___Spider_mites Two-spotted_spider_mite",
            "Tomato___Target_Spot",
            "Tomato___Tomato_mosaic_virus",
            "Tomato___Tomato_Yellow_Leaf_Curl_Virus"
    };
    public static float[] predict(Bitmap image) {
        ByteBuffer inpBuffer = preProcessImage(image);
        Tensor outTensor = interpreter.getOutputTensor(0);
        int[] outShape = outTensor.shape();
        DataType outType = outTensor.dataType();
        Log.d("datatype is", "predict: "+ outType);
        float[][] out = new float[1][60];
        interpreter.run(inpBuffer, out);
        Log.d("output is ", "predict: " + Arrays.toString(out[0]));
        float[] outputList = out[0];
        float max =0f;
        int idx = 0;
        for (int i = 0 ;i<60;i++){
            if(i ==0 )
            {
                max = outputList[i];
            }else{
                if(max <= outputList[i]){
                    max = outputList[i];
                    idx = i;
                }
            }
        }
        return outputList;
//        if(out[0][0]>= out[0][1]){
//            return "Cat";
//        }
//        return "Dog";
//        return Arrays.toString(out[0]);
//        return "Not found";
    }

    private static ByteBuffer preProcessImage(Bitmap bitmap) {
        Bitmap bitmapp = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        ByteBuffer input = ByteBuffer.allocateDirect(INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE * 4).order(ByteOrder.nativeOrder());
        for (int y = 0; y < INPUT_SIZE; y++) {
            for (int x = 0; x < INPUT_SIZE; x++) {
                int px = bitmapp.getPixel(x, y);

                // Get channel values from the pixel value.
                int r = Color.red(px);
                int g = Color.green(px);
                int b = Color.blue(px);

                input.putFloat(r);
                input.putFloat(g);
                input.putFloat(b);
            }
        }
        return input;
    }

    public static void init(AssetManager assetManager, String model_path){
        ImageClassifier.assetManager = assetManager;
        ImageClassifier.modelPath = model_path;
        interpreter = createInterpreter(assetManager, model_path);
    }

    static Interpreter createInterpreter(AssetManager assetManager, String model_path){
        Interpreter.Options options= new Interpreter.Options();
        options.setNumThreads(5);
        options.setUseNNAPI(true);
        return new Interpreter(Objects.requireNonNull(loadModelFile(assetManager, model_path)), options);
    }

    private static ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) {
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = assetManager.openFd(modelPath);
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
