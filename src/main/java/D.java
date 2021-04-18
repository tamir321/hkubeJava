import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class D implements IAlgorithm {

    public Object Start(Map args, IHKubeAPI hkubeAPI) throws Exception {
        System.out.println("~~~Algorithm started ~~~");


        String nodeName = (String) args.get("nodeName");
        Collection<Object> input = (Collection<Object>)args.get("input");
        Iterator iterator = input.iterator();
        String inputAsString ="";
        while (iterator.hasNext()) {
            System.out.println("~~~iterator.hasNext()~~~");
            Object action =  iterator.next();
            if (action instanceof  String){
                System.out.println("~~~ if (action~~~");
                inputAsString = inputAsString + action;
            }
            else if(action instanceof List){
                System.out.println("~~~ else if~~~");
                inputAsString = inputAsString + "[";
                Iterator iterator1 = ((List)action).iterator();
                while (iterator1.hasNext()){
                    System.out.println("~~~iterator1.hasNext()~~~");
                    action =  iterator1.next();
                    inputAsString = inputAsString + action;
                }
                inputAsString = inputAsString + "]";
            }
        }

       /// String act = (String)action.get("input");

        String result = "I am "+ nodeName +" I recived the following input-" + inputAsString;
        return result;

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
