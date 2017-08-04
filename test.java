public class test {

    public int add(int x, int y){
        if (fb.isEnabled("feature.arithmetic_addition")){
            return x + y;
        }
        else {
            return 0;
        }
    }
}