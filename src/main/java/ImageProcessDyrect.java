import com.google.gson.Gson;
import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONObject;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ImageProcessDyrect implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }


    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {

        String fileName = "filename.jpg";
        Collection input = (Collection)args.get("input");

        ByteBuffer buffer =  (ByteBuffer) input.iterator().next();

        byte[] imageByte = new byte[buffer.remaining()];

        File file = new File(fileName);

        boolean append = false;

        FileChannel wChannel = new FileOutputStream(file, append).getChannel();

        wChannel.write(buffer);

        wChannel.close();

        MBFImage image = ImageUtilities.readMBF(file);

        DisplayUtilities.display(image);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ImageUtilities.write(image,"jpeg",os);
        byte[] outputbytes = os.toByteArray();

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


        return  Arrays.equals(outputbytes,imageByte);
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
