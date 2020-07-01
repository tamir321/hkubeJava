import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONArray;
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

public class ImageProcessByteToImage implements IAlgorithm {

    @Override
    public void Init(JSONObject args) {

    }

    @Override
    public JSONObject Start(JSONArray input, IHKubeAPI hkubeAPI) throws Exception {
        JSONObject inputData = (JSONObject) input.get(0);//.get("data");
        JSONObject  inputDataMap = (JSONObject) inputData.get("data");
        byte [] imageByte = (byte[]) inputDataMap.get("image") ;
        byte [] cloneByte = (byte[]) inputDataMap.get("clone");

        System.out.println("imageByte.length - "+imageByte.length);
        System.out.println("cloneByte.length - "+cloneByte.length);

        ByteArrayInputStream bisImage = new ByteArrayInputStream(imageByte);
        MBFImage image12 = ImageUtilities.readMBF(bisImage);


        ByteArrayInputStream bisClone = new ByteArrayInputStream(cloneByte);
        MBFImage image11 = ImageUtilities.readMBF(bisClone);


        Map<String, Object> data = new HashMap<>();
        JSONObject results = new JSONObject();
       // Map files = new HashMap();


        String url = (String) inputData.get("url");
        data.put("imageURL", url);
        //MBFImage image = ImageUtilities.readMBF(new URL(url)); //"http://static.openimaj.org/media/tutorial/sinaface.jpg"

        System.out.println(image12.colourSpace);

        data.put("imageColourSpace", image12.colourSpace);
        data.put("image", image12.toByteImage());


        boolean retval = Arrays.equals(imageByte, image12.toByteImage());
//        MBFImage clone1 = image.clone();
//        for (int y=0; y<image.getHeight(); y++) {
//            for(int x=0; x<image.getWidth(); x++) {
//                clone1.getBand(1).pixels[y][x] = 0;
//                clone1.getBand(2).pixels[y][x] = 0;
//            }
//        }
//        data.put("clone", clone1.toByteImage());

        results.put("data",data);





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

    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }

}
