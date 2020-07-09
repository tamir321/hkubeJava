import com.google.gson.JsonObject;
import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class ImageProcessByteToImage implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }


    @Override
    public Map Start(Map args, IHKubeAPI hkubeAPI) throws Exception {
        Gson gson = new Gson();
        Map results = new HashMap();
       // System.out.println(new JSONObject(args));
       // printJsonObject(new JSONObject(args));
        Map input=(Map) ((Collection)args.get("input")).iterator().next();


        String url =(String) input.get("imageURL");
        System.out.println("url = "+url);
        byte [] imageByte =( byte [])input.get("image");//.toString().getBytes();

        ByteArrayInputStream bisImage = new ByteArrayInputStream(imageByte);
        MBFImage imageFromArray = ImageUtilities.readMBF(bisImage);

        MBFImage image = ImageUtilities.readMBF(new URL(url));
        boolean compare = Arrays.equals(imageFromArray.toByteImage(), image.toByteImage());
        System.out.println("compare = "+ compare);
        results.put("compare",compare);
        results.put("data","data");





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


        return results;
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
