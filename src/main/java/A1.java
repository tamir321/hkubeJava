import com.google.gson.Gson;
import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import jdk.internal.jmod.JmodFile;
import org.json.JSONObject;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class A1 implements IAlgorithm {

    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {



        String nodeName = (String) args.get("nodeName");
        Collection<Object> input = (Collection<Object>)args.get("input");
        Map action = (Map) input.iterator().next();

        String act = (String)action.get("input");
        String result = "I am "+ nodeName +" I recived the following input-" + act;
        return nodeName;

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
