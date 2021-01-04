import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import javassist.bytecode.ByteArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.List;
public class javaApiForImages implements IAlgorithm {
    @Override
    public void Init(Map args) {

    }


    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {

        String nodeName = (String) args.get("nodeName");

        Collection<Object> input = (Collection<Object>)args.get("input");

        Object aaaaa =   hkubeAPI.startStoredPipeLine("test-size",new HashMap());
       // Object bbbbb =  hkubeAPI.startStoredPipeLine("simple",new HashMap());
        Object aaa = ((Map)((List)((LinkedHashMap)aaaaa).get("response")).get(0)).get("result");


        byte[] bytes = (byte[]) ((Map)((List)aaa).get(0)).get("image");
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");

        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding.

        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();

        Image image = reader.read(0, param);
        //got an image file
        return aaaaa;


}
    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }



}
