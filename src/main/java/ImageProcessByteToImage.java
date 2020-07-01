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
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class ImageProcessByteToImage implements IAlgorithm {

    @Override
    public void Init(JSONObject args) {

    }

    public JSONObject getJsonObjectChild(JSONObject  inputData ,String childName){
        Gson gson = new Gson();
        try{

            Object  dataObj =  inputData.get(childName);
            String objString =  gson.toJson(dataObj);
            JSONObject dataJson = new JSONObject(objString);
            return dataJson;
        }
        catch (Exception e){
            System.out.println("Fail to get " + childName);
        }
        return null;
    }

    @Override
    public JSONObject Start(JSONArray input, IHKubeAPI hkubeAPI) throws Exception {
        Gson gson = new Gson();
        JSONObject results = new JSONObject();
        JSONObject inputData = (JSONObject) input.get(0);//.get("data");
        System.out.println("++++++++++++++++++");
        System.out.println("inputData");
        printJsonObject(inputData);
        System.out.println("==================");

        JSONObject dataJson = getJsonObjectChild(inputData , "data");

       // JSONObject imageJson = getJsonObjectChild(dataJson , "image");
      //  JSONObject clonJson = getJsonObjectChild(dataJson , "clone");
      //  JSONObject urlJson = getJsonObjectChild(dataJson , "imageURL");

        String url = dataJson.getString("imageURL");
        System.out.println("url = "+url);
        byte [] imageByte = dataJson.getString("image").getBytes();

        ByteArrayInputStream bisImage = new ByteArrayInputStream(imageByte);
        MBFImage imageFromArray = ImageUtilities.readMBF(bisImage);

        MBFImage image = ImageUtilities.readMBF(new URL(url));

        boolean retval = Arrays.equals(imageFromArray.toByteImage(), image.toByteImage());
        System.out.println("retval = "+retval);

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
        INode[] nodes ={node};

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
