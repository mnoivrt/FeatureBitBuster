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
         boolean z = !fb.isEnabled("feature.arithmetic_multipcation") && x && y;
         boolean a = fb.isEnabled("feature.arithmetic_multipcation") && x && y;
         boolean a1 = x && !fb.isEnabled("feature.arithmetic_multipcation") && y;
         boolean b = a && fb.isEnabled("feature.arithmetic_multipcation");
         boolean c = b || fb.isEnabled("feature.arithmetic_multipcation"); //true
         boolean c = b || fb.isEnabled("feature.arithmetic_multipcation") || d; //true
         boolean d = fb.isEnabled("feature.arithmetic_multipcation") || f; //true
         boolean e = !fb.isEnabled("feature.arithmetic_multipcation"); //false
         boolean f = fb.isEnabled("feature.arithmetic_multipcation"); //true
         boolean g = !!!fb.isEnabled("feature.arithmetic_multipcation"); //false
         boolean g = !x; // !x
         if (y){
             int answer = x * y;
             return answer;
         }
         else {
             return 1;
         }
     }
}