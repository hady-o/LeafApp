package com.example.leafapp.ui.ResultFragment;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.ops.CastOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Objects;


public class ImageClassifier {
    private int INPUT_SIZE = 256;
    private final int PIXEL_SIZE = 3;
    private final int CAPACTY = 4;
    private String modelPath = "";
    private Interpreter interpreter;

    ImageClassifier(AssetManager assetManager, String modelPath,int inputSize) {
        this.modelPath = modelPath;
        this.interpreter = createInterpreter(assetManager, modelPath);
        this.INPUT_SIZE = inputSize;
    }

    public int predictIsPlant(Bitmap image) {
        ByteBuffer inpBuffer = processes(image);
        Tensor outTensor = interpreter.getOutputTensor(0);
        int[] outShape = outTensor.shape();
        DataType outType = outTensor.dataType();
        Log.d("datatype is", "predict: " + outType);
        float[][] out = new float[outShape[0]][outShape[1]];

        TensorBuffer probabilityBuffer =
                TensorBuffer.createFixedSize(new int[] {1,2102},DataType.UINT8);
        interpreter.run(inpBuffer, probabilityBuffer.getBuffer());
        Log.d("output is ", "predict: " + Arrays.toString(out[0]));
        float[] outputList = probabilityBuffer.getFloatArray();
        float max = 0f;
        int idx = 0;
        for (int i = 0; i < outputList.length; i++) {
            if (i == 0) {
                max = outputList[i];
            } else {
                if (max <= outputList[i]) {
                    max = outputList[i];
                    idx = i;
                }
            }
        }
        Log.d("The Max index is ","Her is "+ idx);
        return idx;
    }


    public float[] predict(Bitmap image) {
        ByteBuffer inpBuffer = preProcessImage(image);
        Tensor outTensor = interpreter.getOutputTensor(0);
        int[] outShape = outTensor.shape();
        DataType outType = outTensor.dataType();
        Log.d("datatype is", "predict: " + outType);
        float[][] out = new float[outShape[0]][outShape[1]];
        interpreter.run(inpBuffer,out);
        Log.d("output is ", "predict: " + Arrays.toString(out[0]));
        float[] outputList = out[0];
        /*float max = 0f;
        int idx = 0;
        for (int i = 0; i < 60; i++) {
            if (i == 0) {
                max = outputList[i];
            } else {
                if (max <= outputList[i]) {
                    max = outputList[i];
                    idx = i;
                }
            }
        }*/
        return outputList;
    }


    private ByteBuffer preProcessImage(Bitmap bitmap) {
        Bitmap bitmapp = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        ByteBuffer input = ByteBuffer.allocateDirect(INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE * CAPACTY).order(ByteOrder.nativeOrder());

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


    Interpreter createInterpreter(AssetManager assetManager, String model_path) {
        Interpreter.Options options = new Interpreter.Options();
        options.setNumThreads(5);
        options.setUseNNAPI(true);
        return new Interpreter(Objects.requireNonNull(loadModelFile(assetManager, model_path)), options);
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) {
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

    ByteBuffer processes(Bitmap bitmap) {
        // Initialization code
        // Create an ImageProcessor with all ops required. For more ops, please
        // refer to the ImageProcessor Architecture section in this README.
        ImageProcessor imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeOp(INPUT_SIZE,INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
//                .add(new NormalizeOp(0, 255))
                .add(new CastOp(DataType.UINT8))
                .build();

        // Create a TensorImage object. This creates the tensor of the corresponding
        // tensor type (uint8 in this case) that the TensorFlow Lite interpreter needs.
        TensorImage tensorImage = new TensorImage(DataType.UINT8);

        // Analysis code for every frame
        // Preprocess the image
        Bitmap bitmapp = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        tensorImage.load(bitmapp);
        tensorImage = imageProcessor.process(tensorImage);

        return tensorImage.getBuffer();
    }

}
