import com.google.gson.Gson;
import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONObject;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class randAlg implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }

    private int addArray(int[] array){
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private int[] randArray(int arraySize){
        Random r = new Random();
        int[] results = r.ints(arraySize, 0, 1000).toArray();
        return  results;

    }
    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {


        Collection input = (Collection)args.get("input");

        Object inp =  input.iterator().next();

        if(inp instanceof  Integer){
            randArray((Integer) inp);
        }
        if(inp instanceof ArrayList){


            Random r = new Random();
            int rand =  r.nextInt(10 + 1);
            int s;
            if(rand>5){
                s = ((ArrayList) inp).stream().mapToInt(n -> (int) n).sum();
               return s;
            }
            return randArray((int) ((ArrayList) inp).get(0));


        }
        if(inp instanceof  Integer[]){
            int[] array = (int[]) inp;


            Random r = new Random();
            int rand =  r.nextInt(10 + 1);
            if(rand>5){
                return addArray(array);
            }
            return randArray(array[0]);
        }
       // byte[] imageByte = new byte[buffer.remaining()];



        INode node = new INode() {
            @Override
            public String getName() {
                return "yellow-alg-NOde";
            }

            @Override
            public JSONObject[] getInput() {
                return new JSONObject[0];
            }

            @Override
            public void setInput(JSONObject[] input) {

            }
            @Override
            public String getAlgorithmName() {
                return "yellow-alg";
            }

            @Override
            public void setAlgorithmName(String algorithmName) {

            }
        };


        return  42;
    }


    public static void printJsonObject(JSONObject jsonObj) {
        Gson gson = new Gson();

        jsonObj.keySet().forEach(keyStr ->
        {

            Object keyValue = jsonObj.get(keyStr);
            String dataJsonString = gson.toJson(keyValue);
            System.out.println("key: "+ keyStr + " value size: " + String.valueOf(dataJsonString.length()));
            try{
                System.out.println("Try to get "+ keyStr + "child");
                printJsonObject((JSONObject) keyValue);
            }
            catch (Exception e){
                System.out.println("end of "+ keyStr );
            }

        });
    }



    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }

}
