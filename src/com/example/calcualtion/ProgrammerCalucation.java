package com.example.calcualtion;

import java.util.Stack;

public class ProgrammerCalucation {
	public static int NBaseToDecimal(String str, int N) {
		int num = 0;
		int r = 1;
		
		int t = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			num += r * CharToInt(str.charAt(i));
			r = r * N;
		}
		
		return num;
	}
	
	public static String DecimalToNBase(int num, int N) {
		Stack<Integer> stack = new Stack<Integer>();
		String nBaseNum = new String();
		
		while (num != 0) {
			stack.push(num % N);
			num /= N;
		}

		while (!stack.isEmpty()) {
			nBaseNum += stack.pop();
		}
		return nBaseNum;
	}

	private static int CharToInt(char c) {
		try {
			return Integer.parseInt(String.valueOf(c));
		} catch (Exception e) {
			switch (c) {
			case 'A':
				return 10;
			case 'B':
				return 11;
			case 'C':
				return 12;
			case 'D':
				return 13;
			case 'E':
				return 14;
			default:
				return 0;
			}
		}
	}
}
