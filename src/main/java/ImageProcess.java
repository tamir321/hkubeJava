import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
//{"url":"https://chandra.harvard.edu/graphics/resources/desktops/2006/crab_1680.jpg","returnImge":true,"red":true}
//{"url":"https://tinyjpg.com/images/social/website.jpg","returnImge":"true"}
public class ImageProcess implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }
    //{"url": "http://static.openimaj.org/media/tutorial/sinaface.jpg",
    // "returnImge": "true",
    // "red":"false"}
    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {
      //  Map<String, Object> data = new HashMap<>();
        Map results = new HashMap();
       // Map files = new HashMap();
        Collection input = (Collection)args.get("input");
        Map inputData = (Map) input.iterator().next();
        String url = (String) inputData.get("url");
        Boolean returnImge = Boolean.parseBoolean((String) inputData.get("returnImge")) ;
        Boolean red = Boolean.parseBoolean((String) inputData.get("red")) ;

        results.put("imageURL", url);
        MBFImage image = ImageUtilities.readMBF(new URL(url)); //"http://static.openimaj.org/media/tutorial/sinaface.jpg"

        System.out.println(image.colourSpace);

        results.put("imageColourSpace", image.colourSpace);
        results.put("image", image.toByteImage());
        MBFImage clone = image.clone();
        for (int y=0; y<image.getHeight(); y++) {
            for(int x=0; x<image.getWidth(); x++) {
                clone.getBand(1).pixels[y][x] = 0;
                clone.getBand(2).pixels[y][x] = 0;
            }
        }
        results.put("clone", clone.toByteImage());

       // results.put("data",data);





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
        MBFImage outputImage = image;
        if(red){
            outputImage = clone;
        }
        if(returnImge){
          //  DisplayUtilities.display(outputImage);
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            ImageUtilities.write(outputImage,"jpeg",os);
            byte[] outputbytes = os.toByteArray();
        //    FileOutputStream fs = new FileOutputStream("blackwhite.jpg");
         //   fs.write(outputbytes);


            //byte[] dd = n clone.toByteImage();
            return outputbytes;
        }
        return results;
    }

    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }

}
