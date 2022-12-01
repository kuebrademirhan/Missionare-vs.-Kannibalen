import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class JungleTours {
    //b-min 1 person,max 2 person
    //If at any time on a bank side more cannibals than missionaries, the missionaries will be eaten
    //with backtracking
    static int[][] result;
    public static LinkedList<int[]>  temp=new LinkedList<>();
    static int[] currentstate;
    static boolean isFirst= true;


    public static int[][] solve(int c, int m){
        // only executing this on the first iteration

        if(isFirst && c==0 && m==0){
            return new int[][]{};
        }
        if(isFirst&& c>m){
            return null;
        }

        if(isFirst){
            System.out.println("c = " + c + " m = " + m);
            currentstate= new int[]{c,m,0,0,0};
            temp.add(currentstate);
            isFirst=false;
            return solve(c,m);
        }

        if(currentstate[0]==0 && currentstate[1] == 0 && currentstate[2] == 1 && currentstate[3]==m && currentstate[4] == c ){
            result= new int[temp.size()][5];
            for(int i=0;i<temp.size();i++){
                result[i]=temp.get(i);
            }
            temp=new LinkedList<>();
            isFirst=true;
            return result;
        }else{

        //deadends
        if((currentstate[0]>currentstate[1] && currentstate[1] != 0) || (currentstate[4]>currentstate[3] && currentstate[3] != 0)){

            return null;
        }

        if(currentstate[2]==0){// boat needs to go right
            //ship one c one m
            if(temp.getLast()[0]>0 && temp.getLast()[1]>0 ) {
                currentstate = new int[]{temp.getLast()[0] - 1, temp.getLast()[1] -1, 1, temp.getLast()[3] +1, temp.getLast()[4] + 1};
                if(!contains(temp,currentstate)) {
                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
               }
            }

            //ship two C
            if(temp.getLast()[0]>1) {
                currentstate = new int[]{temp.getLast()[0] - 2, temp.getLast()[1], 1, temp.getLast()[3], temp.getLast()[4] + 2};
                if(!contains(temp,currentstate)) {

                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
               }


            }
            //ship two m
            if(temp.getLast()[1]>1) {
                currentstate = new int[]{temp.getLast()[0], temp.getLast()[1]-2, 1, temp.getLast()[3]+2, temp.getLast()[4]};
                if(!contains(temp,currentstate)) {

                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
               }
            }

            // ship one c
            if(temp.getLast()[0]>0) {
                currentstate = new int[]{temp.getLast()[0]-1, temp.getLast()[1], 1, temp.getLast()[3], temp.getLast()[4]+1};
                if(!contains(temp,currentstate)) {
                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
               }
            }
            // ship one m
            if(temp.getLast()[1]>0) {
                currentstate = new int[]{temp.getLast()[0], temp.getLast()[1]-1, 1, temp.getLast()[3]+1, temp.getLast()[4]};
               if(!contains(temp,currentstate)) {

                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
                }
            }
        }

        if(currentstate[2]==1) { //boat needs to go back
            //ship one m
            if (temp.getLast()[3] > 0) {
                currentstate = new int[]{temp.getLast()[0], temp.getLast()[1] + 1, 0, temp.getLast()[3] - 1, temp.getLast()[4]};
                if (!contains(temp,currentstate)) {

                    temp.add(currentstate);
                    int[][] arr = solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
               }
            }

            //ship one c
            if (temp.getLast()[4] > 0) {
                currentstate = new int[]{temp.getLast()[0] + 1, temp.getLast()[1], 0, temp.getLast()[3], temp.getLast()[4] - 1};
               if (!contains(temp,currentstate)) {

                    temp.add(currentstate);
                    int[][] arr = solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
                }
            }

            //1 c and 1m
            if(temp.getLast()[4]>0 && temp.getLast()[3]>0 ) {
                currentstate = new int[]{temp.getLast()[0] +1 ,0, temp.getLast()[1] +1, 1, temp.getLast()[3] -1, temp.getLast()[4] - 1};
                if(!contains(temp,currentstate)) {
                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
                }
            }
            //ship two C
            if(temp.getLast()[4]>1) {
                currentstate = new int[]{temp.getLast()[0] +2, temp.getLast()[1], 0, temp.getLast()[3], temp.getLast()[4] - 2};
                if(!contains(temp,currentstate)) {
                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
                }


            }
            //ship two m
            if(temp.getLast()[3]>1) {
                currentstate = new int[]{temp.getLast()[0], temp.getLast()[1]+2, 1, temp.getLast()[3]-2, temp.getLast()[4]};
               if(!contains(temp,currentstate)) {
                    temp.add(currentstate);
                    int[][] arr=solve(c, m);
                    if (arr != null) {
                        return arr;
                    }
                    temp.removeLast();
                    currentstate = temp.getLast();
                }
            }

        }







        return null;
    }}

   private static boolean contains(LinkedList<int[]> s, int[] t){
        for (int[] test:s) {
            if(Arrays.equals(test, t)){return true;};
        }
        return false;
    }



    /*public static int[][] solve(int c,int m){

        int[] startState=new int[]{c,m,0,0,0};
        int[] finalState=new int[]{0,0,1,m,c};
        temp.add(startState);
        currentstate=temp.getLast();

        if(currentstate==finalState){
            for(int i=0;i<temp.size();i++){
                result[i]=temp.get(i);
            }
            return result;
        }

        if(currentstate[2]==0 && temp.getLast()[0]<=temp.getLast()[1] && temp.getLast()[4]<=temp.getLast()[3]){
            temp.add(new int[]{c-1,m-1,1,currentstate[3]+1,currentstate[4]+1});
            if(solve(c-1,m-1)!=null){
                currentstate= temp.getLast();
            }else{
                temp.removeLast();
            }

        }
        if(currentstate[2]==0 && temp.getLast()[0]<=temp.getLast()[1] && temp.getLast()[4]<=temp.getLast()[3]){
            temp.add(new int[]{c-2,m,1,currentstate[3],currentstate[4]+2});
            if(solve(c-2,m)!=null){
                currentstate= temp.getLast();
            }else{
                temp.removeLast();
            }

        }
        if(currentstate[2]==0 && temp.getLast()[0]<=temp.getLast()[1] && temp.getLast()[4]<=temp.getLast()[3]){
            temp.add(new int[]{c,m-2,1,currentstate[3]+2,currentstate[4]});
            if(solve(c-2,m)!=null){
                currentstate= temp.getLast();
            }else{
                temp.removeLast();
            }

        }

        if(currentstate[2]==1 && temp.getLast()[0]<=temp.getLast()[1] && temp.getLast()[4]<=temp.getLast()[3]){
            temp.add(new int[]{c+1,m,0,currentstate[3],currentstate[4]-1});
            if(solve(c+1,m)!=null){
                currentstate= temp.getLast();
            }else{
                temp.removeLast();
            }

        }

        if(currentstate[2]==1 && temp.getLast()[0]<=temp.getLast()[1] && temp.getLast()[4]<=temp.getLast()[3]){
            temp.add(new int[]{c,m+1,0,currentstate[3]-1,currentstate[4]});
            if(solve(c,m+1)!=null){
                currentstate= temp.getLast();
            }else{
                temp.removeLast();
            }

        }






        return null;

    }*/
}
