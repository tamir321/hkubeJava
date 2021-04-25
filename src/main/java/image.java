import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class image implements IAlgorithm {
    boolean active = false;

    @Override
    public void Init(Map args) {

    }

    @Override
    public Object Start(Map input, IHKubeAPI hkubeAPI) throws Exception {
        String fileName = "/hkube/algorithm-runner/algorithm_unique_folder/Chameleon.jpg";

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("Chameleon.jpg");
        BufferedImage bImage = ImageIO.read(stream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] data = bos.toByteArray();


        int rng = (int) ((Map) ((List) input.get("input")).get(0)).get("rng");
        int burst = (int) ((Map) ((List) input.get("input")).get(0)).get("burst");
        int ping = (int) ((Map) ((List) input.get("input")).get(0)).get("ping");
        List sleepTime = (List) ((Map) ((List) input.get("input")).get(0)).get("sleepTime");
        int totalMsg = (int) ((Map) ((List) input.get("input")).get(0)).get("totalMsg");
        String nodeName = (String) input.get("nodeName");

        int r = rng;
        int sent = 0;
        int i = 0;
        int z = 0;
        active = true;
        Map msg = new HashMap();
        List arrList = new ArrayList();
        arrList.add(nodeName);
        msg.put("trace", arrList);
        msg.put("image", data);
        msg.put("ping", 0);
        msg.put("last", false);
        while (active) {
            for (int c = 0; c < r; c++) {
                msg.put("id", ++sent);
                msg.put("time", new Date().getTime());
                hkubeAPI.sendMessage(msg, "analyze");
                hkubeAPI.sendMessage(msg);
                Thread.sleep(Math.round(1000 / r));
            }
            i++;
            if (i % 60 == 0) {
                z += 1;
                System.out.println("z=" + z);
            }
            if (i % ping == 0) {
                msg.put("ping", new Date().getTime() / 1000.00);
                hkubeAPI.sendMessage(msg, "analyze");
                hkubeAPI.sendMessage(msg);
                msg.put("ping", 0);
            }
            if (i % 600 == 0) {
                System.out.print("******** start burst ********");
                r = rng * burst;
            }
            if (i % 900 == 0) {
                r = rng;
                i = 0;
                System.out.print("========== end burst ==========");
            }
            if (z > (int) sleepTime.get(0)) {
                z = 0;
                System.out.print("======= start sleep ========");
                Thread.sleep((Integer) sleepTime.get(1) * 1000);
                System.out.print("======= end sleep ========");
            }
            if (sent > totalMsg) {
                Thread.sleep((Integer) sleepTime.get(1) * 1000);
                active = false;
            }

        }
        Thread.sleep(300000);
        return null;
    }


    @Override
    public void Stop() {
        active = false;
    }

    @Override
    public void Cleanup() {

    }
}
