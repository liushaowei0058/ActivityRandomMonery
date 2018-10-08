package com.etong.ctrl.marketing.config;

import java.util.Arrays;

import com.google.common.primitives.Ints;

/**
 * 砍价规则
 * @author Administrator
 *
 */
public class RulesBargMoney {
	
	
	//人数
	private static final int[] number={5,6,7,8,9,10};
	
	/**
	 * 砍价规则金额生成
	 * @param num 人数
	 * @param countBargMoney 优惠砍价总金额
	 * @return
	 */
	public static String sprin(int numr,int countBargMoney){
		
		if(!Ints.asList(number).contains(numr)){
			throw new RuntimeException("规则人数参数异常,暂时只支持人数随机砍价："+Arrays.toString(number));
		}
		//人数越多调整越大 xx/xx
		System.out.println("=========砍价规则随机生成======");
    	double cel=countBargMoney/4;  //上线
    	double dow=countBargMoney/15; //下线
    	double[] moneys=divide(numr,countBargMoney,cel,dow);
    	double sum=0;
    	for(int i=0; i<moneys.length;i++){
    		moneys[i]=moneys[i]/10;
    		sum+=moneys[i];
    	}
    	String arrStr=Arrays.toString(moneys);
    	System.out.println("砍价规则总额："+(sum)+"=="+arrStr);
		return arrStr.substring(1,arrStr.length()-1);
	}


	public static double[] divide(int numr,double bargMoney,double cel,double dow){
		double[] moneys=new double[numr];
		// *10 精确后几位
		double lastMoney=bargMoney*10;
		double max=cel*10;
		double min=dow*10;
		double sum=0;
 	    for(int i=0;i<numr-1;i++){
              moneys[i]=Math.ceil(min+Math.random()*(max-min));
              lastMoney-=moneys[i];//总金额递减
              sum+=moneys[i]/10;
         }
 	     moneys[numr-1]=lastMoney;
 	     //最后一个人如超过上限低于下限，重新分
         if(moneys[numr-1]>max || moneys[numr-1]<min){
             return divide(numr,bargMoney,cel,dow);
         }
         //不等价，重新分
         if(Math.ceil(sum+(moneys[numr-1]/10))!=bargMoney){
        	 return divide(numr,bargMoney,cel,dow);
         }
		 return moneys;
    }
	
	
	public static void main(String[] args) {
		for(int i=0; i<number.length;i++){
			sprin(number[i],85);
    	}
	}
	
}
