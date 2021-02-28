import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class javaReturenInput implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }


    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {

        String nodeName = (String) args.get("nodeName");

        Collection<Object> input = (Collection<Object>)args.get("input");





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

        return input;


    }






    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }


    private INode createNode(String nodeName,String algName,String nodeInput) {
        JSONObject obj=new JSONObject();
        obj.put("input",nodeInput);
        JSONObject[] obj1 =new JSONObject[1];
        obj1[0]=obj;

        INode node = new INode() {
            @Override
            public String getName() {
                return nodeName;
            }

            @Override
            public JSONObject[] getInput() {
                return obj1;
            }

            @Override
            public void setInput(JSONObject[] input) {

            }

            @Override
            public String getAlgorithmName() {
                return algName;
            }

            @Override
            public void setAlgorithmName(String algName) {

            }

        };
        return node;
    }
}
