/*
 * ArrayList,List: List is a class(interface)
 * Map,HashMap: Stores Data key Value Pair
 * Optionals: a container that handle null pointer exception
 * Streams
 * Interfaces
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class AdvanceJava{
    static public void list(){
        List<Object>arr=new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        for(int i=0;i<arr.size();i++){
            System.out.println(arr.get(i));
        }
    }
    static public void map(){
        Map<String,Integer> marks= new HashMap<>();

        marks.put("Tahshin",100);
        marks.put("Sharon",90);

        for(Map.Entry<String,Integer>entry: marks.entrySet()){
            System.out.print(entry.getKey()+"---");
            System.out.println(entry.getValue());
        }
        //System.out.println(marks.get("tah")>20); gives null pointer exception
        Optional<Integer> MarksOfTah = Optional.ofNullable(marks.get("tah"));
        if(MarksOfTah.isPresent()){
            ///
        }else{
            System.out.println("Not present");
        }
    }
    static public void main(String[]args){
        list();
        map();
    }
}
