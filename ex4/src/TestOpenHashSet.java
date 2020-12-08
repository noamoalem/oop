import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOpenHashSet {
    private OpenHashSet hash;

    @Before
    public void creatObject(){
        hash = new OpenHashSet();
    }
    @Test
    public void testConstructor(){
        hash.add("a");
        hash.add("aa");
        hash.add("b");
        hash.add("p");
        hash.add("q");
        assertEquals(16,hash.capacity());
        assertEquals(5,hash.size());
        System.out.println("--------------------ADD a aa b p q C:16 S:5-----------------------"); //0[p aa]
        System.out.println("capacity: "+hash.capacity());                                         //1[q a]
        System.out.println("size: "+hash.size());                                                 //2[b]
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("false: "+hash.contains("g"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        hash.delete("a");
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        System.out.println("-------------------del a C:16 S:4-----------------------");
        System.out.println("size: "+hash.size());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        hash.delete("q");
        assertEquals(8,hash.capacity());
        assertEquals(3,hash.size());
        System.out.println("-------------------del q C:8 S:3-----------------------");
        System.out.println("size: "+hash.size());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        hash.delete("b");
        assertEquals(8,hash.capacity());
        assertEquals(2,hash.size());
        System.out.println("-------------------del b C:8 S:2-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        hash.delete("p");
        System.out.println("-------------------del p C:4 S:1-----------------------");
        assertEquals(4,hash.capacity());
        assertEquals(1,hash.size());
        hash.delete("aa");
        System.out.println("-------------------del aa C:2 S:0-----------------------");
        assertEquals(2,hash.capacity());
        assertEquals(0,hash.size());
        System.out.println("-------------------del b C:2 S:0-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
    }
    @Test
    public void testConstracturList(){
        String[] ABC = new String[]{"a","b","c","d"};
        hash = new OpenHashSet(ABC);
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        System.out.println("--------------------ADD a b c d C:16 S:4-----------------------");
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        hash.add("d");
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        System.out.println("-------------------- a b c d C:16 S:4-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("false: "+hash.contains(" g"));

        hash.add("p");
        hash.add("q");
        assertEquals(16,hash.capacity());
        assertEquals(6,hash.size());
        System.out.println("-------------------- a b c d p q C:16 S:6-----------------------"); //0 [p]
        for (int i =0; i<hash.capacity();i++)                                                   //1 [q,a]
            System.out.println(i+" "+hash.hashArray[i]);                                        //2 [b]
        System.out.println("capacity: "+hash.capacity());                                       //3 [c]
        System.out.println("size: "+hash.size());                                               //4 [d]
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("true: "+hash.contains("p"));

        hash.add("e");
        hash.add("f");
        hash.add("g");
        hash.add("h");
        hash.add("i");
        hash.add("j");
        hash.add("k");
        hash.add("l");
        assertEquals(32,hash.capacity());
        assertEquals(14,hash.size());
        System.out.println("-------------------- a -l p q C:32 S:14-----------------------");
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("l"));
        hash.add("m");
        assertEquals(32,hash.capacity());
        assertEquals(15,hash.size());
        System.out.println("-------------------- a - l add m C:32 S:15-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);

    }

    @Test
    public void testResizeDown(){
        String[] ABC = new String[]{"a","q","b","c","d"}; //1 [a,q]
        hash = new OpenHashSet(ABC);
        assertEquals(16,hash.capacity());
        assertEquals(5,hash.size());
        hash.delete("d"); // 4/16=0.25
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        hash.delete("a"); // 3/16<0.25
        assertEquals(8,hash.capacity());
        assertEquals(3,hash.size());
        hash.delete("q");// 2/8=0.25
        assertEquals(8,hash.capacity());
        assertEquals(2,hash.size());
        hash.delete("b"); // 1/8 <0.25
        assertEquals(4,hash.capacity());
        assertEquals(1,hash.size());
    }
    @Test
    public void test1LoadFactor(){
        hash = new OpenHashSet(1.0f,0.25f);
        String[] ABC = new String[]{"a", "b", "c", "d","e","f","g","h","i","j","k","l","m","n","o","p"}; //add 16
        for (String str:ABC){
            hash.add(str);
        }
        assertEquals(16,hash.size());
        assertEquals(16,hash.capacity());
        for (int i =0; i<hash.capacity();i++){
            System.out.println("index "+i+hash.hashArray[i]);
        }
        hash.add("w");
        hash.add("w");
        assertEquals(17,hash.size());
        assertEquals(32,hash.capacity());
        System.out.println("-------------------------add w ------------------");
        for (int i =0; i<hash.capacity();i++) {
            System.out.println("index " + i + hash.hashArray[i]);
        }
        hash.add("z");
        assertEquals(18,hash.size());
        assertEquals(32,hash.capacity());
        System.out.println("-------------------------add m ------------------");
        for (int i =0; i<hash.capacity();i++) {
            System.out.println("index " + i + hash.hashArray[i]);
        }
        hash.add("noa");
        assertEquals(19,hash.size());
        assertEquals(32,hash.capacity());
        System.out.println("-------------------------add noa ------------------");
        for (int i =0; i<hash.capacity();i++) {
            System.out.println("index " + i + hash.hashArray[i]);
        }
        hash.add("moalem");
        hash.add("NOA");
        assertEquals(21,hash.size());
        assertEquals(32,hash.capacity());
        System.out.println("-------------------------add moalem ------------------");
        for (int i =0; i<hash.capacity();i++) {
            System.out.println("index " + i + hash.hashArray[i]);
        }
    }
}
