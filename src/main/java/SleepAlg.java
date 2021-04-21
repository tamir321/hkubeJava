import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;

import java.util.List;
import java.util.Map;

public class SleepAlg implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }

    @Override
    public Object Start(Map input, IHKubeAPI hkubeAPI)  {
        Map msg = (Map) ((Map) input.get("streamInput")).get("message");
        String nodeName = (String) input.get("nodeName");
        List arrList = (List) msg.get("trace");
        arrList.add(nodeName);
        msg.put("trace",arrList);
        System.out.println("handling " + msg.get("id") + " on " + msg.get("trace") + " " +System.getenv().get("POD_IP"));
        if(((List) input.get("input")).get(0) != null && ((Map) ((List) input.get("input")).get(0)).get("rate") != null) {
            int rate = (int) ((Map) ((List) input.get("input")).get(0)).get("rate");

            try {
                Thread.sleep(1000 / rate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }
}
