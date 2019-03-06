package com.wxxy;

/**
 * @author LiuK
 * @date 2019/1/6 16:33
 * @description:
 */
public class sort {
    public static void main(String[] args) {
        int [] arr={2,5,1,9,3,8,4,6};
        sort sort=new sort();
        sort.simpleSelectSort(arr);
    }

    public void Sort(int[] arr){

        int flag=0;
        int temp=0;

        for(int i=0;i<arr.length-1;i++){

            flag=0;
            for(int j=0;j<arr.length-1-i;j++){

                if(arr[j]>arr[j+1]){
                    flag=1;
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;

                }

            }
            if(flag==0){
                break;
            }

        }
        for (int i : arr) {
            System.out.println(i+"  ");
        }

    }

    public void simpleSelectSort(int[] arr){
        int temp;
        for(int i=0;i<arr.length;i++){
            int index=i;
            for(int j=i;j<arr.length;j++){
                if(arr[j]<arr[index]){
                    index=j;
                }
            }
            temp=arr[i];
            arr[i]=arr[index];
            arr[index]=temp;

        }
        for (int i : arr) {
            System.out.print(i+" ");
        }

    }
}
