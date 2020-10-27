import com.google.gson.Gson;
import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import hkube.api.INode;
import org.json.JSONObject;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class createBigArray implements IAlgorithm {

    @Override
    public void Init(Map args) {

    }

    private int addArray(int[] array){
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private int[] randArray(int arraySize){
        Random r = new Random();
        int[] results = r.ints(arraySize, 0, 1000).toArray();
        return  results;

    }

    private Object createRand(Object input){
        Object inp =  input;

        if(inp instanceof  Integer){
            randArray((Integer) inp);
        }
        if(inp instanceof ArrayList){


            Random r = new Random();
            int rand =  r.nextInt(10 + 1);
            int s;
            if(rand>5){
                if(((ArrayList) inp).get(0) instanceof ArrayList){
                    s = ((ArrayList) ((ArrayList) inp).get(0)).stream().mapToInt(n -> (int) n).sum();
                }
                else {
                    s = ((ArrayList) inp).stream().mapToInt(n -> (int) n).sum();
                }

                return s;
            }
            if(((ArrayList) inp).get(0) instanceof ArrayList ){
                return createRand(((ArrayList) inp).get(0));
            }
            return randArray((int) ((ArrayList) inp).get(0));


        }
        if(inp instanceof  Integer[]){
            int[] array = (int[]) inp;


            Random r = new Random();
            int rand =  r.nextInt(10 + 1);
            if(rand>5){
                return addArray(array);
            }
            return randArray(array[0]);
        }

        return 42;
    }
    @Override
    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {
        
        String nodeName = (String) args.get("nodeName");


        Collection<Integer> input = (Collection<Integer>)args.get("input");
        // Integer[] Foo = input.toArray(new Integer[input.size()]);

        // byte[] imageByte = new byte[buffer.remaining()];
        if(nodeName.equals("one")){
            List<Integer> newList = input.stream().collect(toList());

            //int argOne =(int)7;// input.get(0);
            int argOne =(int)newList.get(0);
            Random rd = new Random();
            byte[] arr = new byte[argOne];
            rd.nextBytes(arr);
            return arr;
        }
        if(nodeName.equals("onearray")){
            List<Integer> newList = input.stream().collect(toList());
            int argOne =(int)newList.get(0);// input.get(0);
            int argTwo =(int) newList.get(1);
            Random rd = new Random();
            ArrayList<byte[]> list = new ArrayList<>(argOne);
            byte[] arr = new byte[argTwo];
            rd.nextBytes(arr);

            for (int i = 0; i < argOne; i++)
            {
                list.add(arr);
            }
            return list;
        }

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
