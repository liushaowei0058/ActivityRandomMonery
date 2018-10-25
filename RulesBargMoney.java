package com.etong.ctrl.marketing.config;

import java.util.Arrays;

import com.google.common.primitives.Ints;


/**
 * 砍价规则
 * 
 * @author Administrator
 *
 */
public class RulesBargMoney {

	// 人数
	private static final int[] number = { 5, 6, 7, 8, 9, 10 };

	/**
	 * 砍价规则 方式一
	 * @param num 人数
	 * @param countBargMoney 优惠砍价总金额
	 * @return
	 */
	public static String sprin(int numr, int countBargMoney) {
		System.out.println("======砍价一：======");
		if (!Ints.asList(number).contains(numr)) {
			throw new RuntimeException("规则人数参数异常,暂时只支持人数随机砍价：" + Arrays.toString(number));
		}
		// 人数越多调整越大 xx/xx
		System.out.println("=========砍价规则随机生成======");
		int cel = countBargMoney / 4; // 上线
		int dow = countBargMoney / 15; // 下线
		int[] moneys = divide(numr, countBargMoney, cel, dow);
		double sum = 0;
		for (int i = 0; i < moneys.length; i++) {
			moneys[i] = moneys[i] / 1;
			sum += moneys[i];
		}
		String arrStr = Arrays.toString(moneys);
		System.out.println("砍价规则总额：" + (sum) + "==" + arrStr);
		return arrStr.substring(1, arrStr.length() - 1);
	}

	public static int[] divide(int numr, int bargMoney, int cel, int dow) {
		int[] moneys = new int[numr];
		// *10 精确后几位
		int lastMoney =bargMoney * 1;
		int max = cel*1;
		int min = dow*1;
		int sum = 0;
		for (int i = 0; i < numr - 1; i++) {
			// 生成随机金额
			moneys[i] = (int) Math.ceil(min + Math.random() * (max - min));
			lastMoney -= moneys[i];// 总金额递减
			sum += moneys[i] / 1;
		}
		moneys[numr - 1] = lastMoney;
		// 最后一个人如超过上限低于下限，重新分
		if (moneys[numr - 1] > max || moneys[numr - 1] < min) {
			return divide(numr, bargMoney, cel, dow);
		}
		// 不等价，重新分
		if (Math.ceil(sum + (moneys[numr - 1] / 1)) != bargMoney) {
			return divide(numr, bargMoney, cel, dow);
		}
		return moneys;
	}

	
	
	/**
	 * 砍价规则 方式二
	 * @param total_people  抢红包总人数
	 * @param total_money 红包总金额
	 * @return
	 */
	public static String sprinTwo(int total_people, double total_money) {
		// System.out.println("======砍价二：======");
		final int totalPeople = total_people;
		final double totalMoney = total_money;
		double min_money = (total_money / total_people) / 2; // 下线
		String[] moneys = new String[totalPeople];
		double sum = 0;
		for (int i = 0; i < total_people - 1; i++) {
			int j = i + 1;
			double safe_money = (total_money - (total_people - j) * min_money)/(total_people - j);
			double tmp_money = (Math.random() * (safe_money * 100 - min_money * 100) + min_money * 100) / 100;
			if (tmp_money >= 100) {
				// 单笔不能超过100
				return sprinTwo(totalPeople, totalMoney);
			}
			total_money = total_money - tmp_money;
			sum += tmp_money;
			//保留小数点0位
			moneys[i] = String.format("%.0f", tmp_money);
			// System.out.format("第 %d 个红包： %.1f 元，剩下： %.1f 元\n",j,tmp_money,total_money);
		}
		moneys[total_people - 1] = String.format("%.0f", total_money);
		if (total_money > (totalMoney / totalPeople) * 1.5) {
			// 最后一单不能大于 (totalMoney/totalPeople)*1.5
			return sprinTwo(totalPeople, totalMoney);
		}
		String arrStr = Arrays.toString(moneys);
		System.out.println("sum:" + String.format("%.0f", (sum + total_money)) + ":" + arrStr);
		return arrStr.substring(1, arrStr.length() - 1);
	}

	public static void main(String[] args) {
		sprinTwo(12,155.0);
		//sprin(10,60);
	}

}
