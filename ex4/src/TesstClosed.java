import org.junit.*;
import static org.junit.Assert.*;

public class TesstClosed {
    private ClosedHashSet hash;
    @Before
    public void creatObject(){
        hash = new ClosedHashSet();
    }

    @Test
    public void testAdd(){
        hash.add("a");
        hash.add("b");
        hash.add("c");
        hash.add("d");
        System.out.println("--------------------ADD a b c d C:16 S:4-----------------------");
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("false: "+hash.contains("g"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("-------------------- a b c  C:8 S:3-----------------------");
        hash.delete("d");
        assertEquals(8,hash.capacity());
        assertEquals(3,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("false: "+hash.contains("g"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("--------------------  b c  C:8 S:2-----------------------");
        hash.delete("a");
        assertEquals(8,hash.capacity());
        assertEquals(2,hash.size());
        System.out.println();
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("false: "+hash.contains("a"));
        System.out.println("false: "+hash.contains("g"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("-------------------- b  C:4 S:1-----------------------");
        hash.delete("c");
        assertEquals(4,hash.capacity());
        assertEquals(1,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("false: "+hash.contains("c"));
        System.out.println("false: "+hash.contains("g"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("-------------------- empty  C:2 S:0-----------------------");
        hash.delete("b");
        assertEquals(2,hash.capacity());
        assertEquals(0,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("false: "+hash.contains("b"));
        System.out.println("false: "+hash.contains("F"));
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("--------------------ADD d C:2 S:1-----------------------");
        hash.add("d");
        assertEquals(2,hash.capacity());
        assertEquals(1,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
        System.out.println("-------------------- a d C:4 S:2-----------------------");
        hash.add("a");
        assertEquals(4,hash.capacity());
        assertEquals(2,hash.size());
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        for (int i =0; i<hash.capacity();i++)
            System.out.println(i+" "+hash.hashArray[i]);
    }
    @Test

    public void testConstructorList() {
        String[] ABC = new String[]{"a", "b", "c", "d"};
        hash = new ClosedHashSet(ABC);
        assertEquals(16, hash.capacity());
        assertEquals(4, hash.size());
        System.out.println("--------------------ADD a b c d C:16 S:4-----------------------");
        for (int i = 0; i < hash.capacity(); i++)
            System.out.println(i + " " + hash.hashArray[i]);
        hash.add("d");
        assertEquals(16, hash.capacity());
        assertEquals(4, hash.size());
        System.out.println("-------------------- a b c d C:16 S:4-----------------------");
        System.out.println("capacity: " + hash.capacity());
        System.out.println("size: " + hash.size());
        System.out.println("low factor " + hash.getLowerLoadFactor());
        System.out.println("up factor: " + hash.getUpperLoadFactor());
        System.out.println("true: " + hash.contains("a"));
        System.out.println("false: " + hash.contains(" g"));
    }
    @Test
    public void testConstructorListResize(){
        String[] ABC = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m"};
        hash = new ClosedHashSet(ABC);
        assertEquals(32 ,hash.capacity());
        assertEquals(13, hash.size());
    }


    @Test
    public void testConstactur2(){
        hash = new ClosedHashSet(0.8f,0.2f);
        hash.add("a");
        hash.add("b");
        hash.add("c");
        hash.add("d");
        hash.add("e");
        hash.add("f");
        hash.add("g");
        hash.add("h");
        hash.add("i");
        hash.add("j");
        hash.add("k");
        hash.add("l");
        assertEquals(16,hash.capacity());
        assertEquals(12,hash.size());
        System.out.println("--------------------ADD a - l C:16 S:12-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("a"));
        System.out.println("true: "+hash.contains("g"));
        //for (int i =0; i<hash.capacity();i++)
         //   System.out.println(i+" "+hash.hashArray[i]);
        hash.add("m");
        assertEquals(32,hash.capacity());
        assertEquals(13,hash.size());
        System.out.println("-------------------- a - l add m C:32 S:13-----------------------");
        System.out.println("capacity: "+hash.capacity());
        System.out.println("size: "+hash.size());
        System.out.println("low factor "+hash.getLowerLoadFactor());
        System.out.println("up factor: "+hash.getUpperLoadFactor());
        System.out.println("true: "+hash.contains("m"));
        System.out.println("false: "+hash.contains(" w"));
    }

    @Test
    public void testCapacityAndSize(){
        hash.add("a");
        hash.add("b");
        hash.add("c");
        hash.add("d");
        assertEquals(16,hash.capacity());
        assertEquals(4,hash.size());
        hash.add("p");
        hash.add("q");
        assertEquals(16,hash.capacity());
        assertEquals(6,hash.size());
        hash.add("e");
        hash.add("w");
        hash.add("z");
        hash.add("x");
        hash.add("v");
        hash.add("n");
        hash.add("m"); // 13/16 > 0.75
        assertEquals(32,hash.capacity());
        assertEquals(13,hash.size());
        hash.delete("a");
        hash.delete("b");
        hash.delete("c");
        hash.delete("d");
        hash.delete("p"); // 8/32 = 0.25
        assertEquals(32,hash.capacity());
        assertEquals(8,hash.size());
        hash.delete("q"); // 7/32 < 0.25
        assertEquals(16,hash.capacity());
        assertEquals(7,hash.size());
        hash.delete("e");
        hash.delete("w");
        hash.delete("z");
        hash.delete("x"); // 3/16 < 0.25
        assertEquals(8,hash.capacity());
        assertEquals(3,hash.size());
        hash.delete("v"); // 2/8 = 0.25
        assertEquals(8,hash.capacity());
        assertEquals(2,hash.size());
        hash.delete("n");// 1/8 < 0.25 only m left
        assertEquals(4,hash.capacity());
        assertEquals(1,hash.size());
        hash.delete("m"); // 0/8 < 0.25 empty
        assertEquals(2,hash.capacity());
        assertEquals(0,hash.size());
    }
    @Test
    public void test1LoadFactor(){
        hash = new ClosedHashSet(1.0f,0.25f);
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
        assertEquals(17,hash.size());
        assertEquals(32,hash.capacity());
        System.out.println("-------------------------add w ------------------");
        for (int i =0; i<hash.capacity();i++){
            System.out.println("index "+i+hash.hashArray[i]);
        }

    }

}
