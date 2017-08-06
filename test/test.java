 class test {


    public static void main(String[] args){
        int res = add(5,1);
        System.out.println(res);
    }

    private int add(int x, int y){
        if (fb.isEnabled("feature.arithmetic_addition")){
            int answer = x + y;
            return answer;
        }
        else {
            return 0;
        }
    }

     private int mul(int x, int y){
         boolean a = fb.isEnabled("feature.arithmetic_multipcation") && x;
         boolean b = a && fb.isEnabled("feature.arithmetic_multipcation");
         boolean c = b || fb.isEnabled("feature.arithmetic_multipcation");
         boolean d = fb.isEnabled("feature.arithmetic_multipcation") || f;
         boolean e = !fb.isEnabled("feature.arithmetic_multipcation");
         if (y){
             int answer = x * y;
             return answer;
         }
         else {
             return 1;
         }
     }
}