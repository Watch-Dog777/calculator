package com.example.calcualtion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.Stack;

import com.example.calcualtion.R;


public class ProgrammerActivity extends Activity implements OnClickListener {	
	static int ERROR = -1;
	
	String[] opSet = { "+", "-", "X", "/", "<<", ">>", "&", "|", "=" };
	int[] btnIdSet = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, 
			R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnA, R.id.btnB, R.id.btnC, 
			R.id.btnD, R.id.btnE, R.id.btnF, R.id.btnAdd, R.id.btnMinus, R.id.btnMultiply,
			R.id.btnDivide, R.id.btnEqual, R.id.btnLsh, R.id.btnRsh, R.id.btnAnd, R.id.btnOr
	};
	
	EditText input;
	Button[] btnSet = new Button[btnIdSet.length];
	
	int op = -1;
	int N = 10;
	
	int leftNum = 0;
	int rightNum = 0;
	int res = 0;
	
	boolean clearFlag = false;
	
	String tmp = new String();
	String exp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programmer_activity);
			
        for (int i = 0; i < btnIdSet.length; i++) {
        	btnSet[i] = (Button)findViewById(btnIdSet[i]);
        	btnSet[i].setOnClickListener(this);
        }
        
        input = (EditText)findViewById(R.id.input);  
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		exp = input.getText().toString();
		
		switch (v.getId()) {
		case R.id.btn0:
		case R.id.btn1:
		case R.id.btn2:
		case R.id.btn3:
		case R.id.btn4:
		case R.id.btn5:
		case R.id.btn6:
		case R.id.btn7:
		case R.id.btn8:
		case R.id.btn9:
			tmp += ((Button)v).getText().toString();
			exp += ((Button)v).getText().toString();
			break;
		case R.id.btnAdd:
			processOperator(0);
			break;
		case R.id.btnMinus:
			processOperator(1);
			break;
		case R.id.btnMultiply:
			processOperator(2);
			break;
		case R.id.btnDivide:
			processOperator(3);
			break;
		case R.id.btnLsh:
			processOperator(4);
			break;
		case R.id.btnRsh:
			processOperator(5);
			break;
		case R.id.btnAnd:
			processOperator(6);
			break;
		case R.id.btnOr:
			processOperator(7);
			break;
		case R.id.btnEqual:
			processOperator(8);
			break;
		}
		
		input.setText(exp.toString());
	}
	
	private void processOperator(int i) {
		exp = input.getText().toString();

		if (leftNum == 0) {
			leftNum = nBaseToDecimal(tmp, N);
			tmp = "";
		} else {
			rightNum = nBaseToDecimal(tmp, N);
			tmp = "";
		}
		
		if (i == 8) {
			if (leftNum != 0 && rightNum != 0) {
				res = calculate(op);
				exp = String.valueOf(res);
				leftNum = res;
				rightNum = 0;
				tmp = "";
				op = -1;
				return;
			}
		}
		else if (op == -1) {				
			exp += opSet[i];
		} else {
			if (rightNum == 0) {
				exp = exp.substring(0, exp.length() - 1) + opSet[i];	
			} else {
				System.out.println(leftNum);
				System.out.println(rightNum);
				res = calculate(op);
				exp = String.valueOf(res);
				exp += opSet[i];
				leftNum = res;
				rightNum = 0;
				tmp = "";
			}
		}
		op = i;
	}

	private int calculate(int op) {		
		switch (op) {
		case 0:
			return leftNum + rightNum;
		case 1:
			return leftNum - rightNum;
		case 2:
			return leftNum * rightNum;
		case 3:
			return leftNum / rightNum;
		case 4:
			return leftNum << rightNum;
		case 5:
			return leftNum >> rightNum;
		case 6:
			return leftNum & rightNum;
		case 7:
			return leftNum | rightNum;
		}
		
		return 0;
	}

	public static int nBaseToDecimal(String str, int N) {
		int num = 0;
		int r = 1;
		
		int t = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			t = charToInt(str.charAt(i));
			if (t >= N) {
				return ERROR;
			}
			num += r * charToInt(str.charAt(i));
			r = r * N;
		}
		
		return num;
	}
	
	public static String decimalToNBase(int num, int N) {
		Stack<Character> stack = new Stack<Character>();
		String nBaseNum = new String();
		
		while (num != 0) {
			stack.push(numToChar(num % N, N));
			num /= N;
		}

		while (!stack.isEmpty()) {
			nBaseNum += stack.pop();
		}
		return nBaseNum;
	}

	private static Character numToChar(int num, int N) {
		char c = ' ';
		if (num < 10) {
			c = String.valueOf(num).charAt(0);
		} else {
			switch (num) {
			case 10:
				c = 'A';
				break;
			case 11:
				c = 'B';
				break;
			case 12:
				c = 'C';
				break;
			case 13:
				c = 'D';
				break;
			case 14:
				c = 'E';
				break;
			case 15:
				c = 'F';
				break;
			default:
				c = ' ';
				break;
			}
		}
		return c;
	}

	private static int charToInt(char c) {
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
