import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class A2 implements IAlgorithm {

    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {



        String nodeName = (String) args.get("nodeName");
        Collection<Object> input = (Collection<Object>)args.get("input");
        String action = (String) input.iterator().next();

       /// String act = (String)action.get("input");
        String result = "I am "+ nodeName +" I recived the following input-" + action;
        Map<String, String> articleMapOne = new HashMap<>();
        articleMapOne.put("inp", result);
        Object jnk =   hkubeAPI.startStoredPipeLine("amit",articleMapOne);
        String out =  jnk.toString(); //get("response").get(0).get("result").
        return result + " the result I got from amit pipeline was: " + out;
       // return nodeName;

    }

    @Override
    public void Init(Map map) {

    }


    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }
}
