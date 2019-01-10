package com.example.calcualtion;

import java.util.Stack;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProgrammerActivity extends Activity 
implements OnClickListener, OnCheckedChangeListener {
	
	static int ERROR = -1;
	
	int[] radix = { 2, 8, 10, 16 };
	String[] opSet = { "+", "-", "X", "/", "<<", ">>", "&", "|", "=" };
	int[] btnIdSet = { R.id.btnP0, R.id.btnP1, R.id.btnP2, R.id.btnP3, R.id.btnP4, 
			R.id.btnP5, R.id.btnP6, R.id.btnP7, R.id.btnP8, R.id.btnP9, R.id.btnPA, 
			R.id.btnPB, R.id.btnPC, R.id.btnPD, R.id.btnPE, R.id.btnPF, R.id.btnPAdd,
			R.id.btnPMinus, R.id.btnPMultiply, R.id.btnPDivide, R.id.btnPLsh, 
			R.id.btnPRsh, R.id.btnPAnd, R.id.btnPOr, R.id.btnPEqual, R.id.btnPClear,
	};
	int[] cbIdSet = { R.id.cbPBin, R.id.cbPOct, R.id.cbPDec, R.id.cbPHex };
	int[] tvIdSet = { R.id.tvPBin, R.id.tvPOct, R.id.tvPDec, R.id.tvPHex };
	
	EditText input;
	Button[] btnSet = new Button[btnIdSet.length];
	CheckBox[] cbSet = new CheckBox[cbIdSet.length];
	TextView[] tvSet = new TextView[tvIdSet.length];
	
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        for (int i = 0; i < btnIdSet.length; i++) {
        	btnSet[i] = (Button)findViewById(btnIdSet[i]);
        	btnSet[i].setOnClickListener(this);
        }
        
        for (int i = 0; i < cbIdSet.length; i++) {
        	cbSet[i] = (CheckBox)findViewById(cbIdSet[i]);
        	cbSet[i].setOnCheckedChangeListener(this);
        }
        
        for (int i = 0; i < tvIdSet.length; i++) {
        	tvSet[i] = (TextView)findViewById(tvIdSet[i]);
        }
        
        input = (EditText)findViewById(R.id.etPInput); 
        exp = input.getText().toString();
        
        setButtonEnable(N);
        cbSet[2].setChecked(true);	
    }

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			for (int i = 0; i < cbSet.length; i++) {
				if (cbIdSet[i] == button.getId()) {
					cbSet[i].setChecked(true);
					switch (i) {
					case 0:
						N = 2;
						break;
					case 1:
						N = 8;
						break;
					case 2:
						N = 10;
						break;
					case 3:
						N = 16;
						break;
					}
					setButtonEnable(N);
				} else {
					cbSet[i].setChecked(false);
				}
			}
		}
	}

	private void setButtonEnable(int n) {
		// TODO Auto-generated method stub
		
		switch (n) {
		case 2:
			for (int i = 0; i < 2; i++) {
				btnSet[i].setEnabled(true);
			}
			for (int i = 2; i < 16; i++) {
				btnSet[i].setEnabled(false);
			}
			break;
		case 8:
			for (int i = 0; i < 9; i++) {
				btnSet[i].setEnabled(true);
			}
			for (int i = 9; i < 16; i++) {
				btnSet[i].setEnabled(false);
			}
			break;
		case 10:
			for (int i = 0; i < 10; i++) {
				btnSet[i].setEnabled(true);
			}
			for (int i = 10; i <16; i++) {
				btnSet[i].setEnabled(false);
			}
			break;
		case 16:
			for (int i = 0; i < 16; i++) {
				btnSet[i].setEnabled(true);
			}
			break;
		default:
			for (int i = 0; i < 16; i++) {
				btnSet[i].setEnabled(true);
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnP0:
		case R.id.btnP1:
		case R.id.btnP2:
		case R.id.btnP3:
		case R.id.btnP4:
		case R.id.btnP5:
		case R.id.btnP6:
		case R.id.btnP7:
		case R.id.btnP8:
		case R.id.btnP9:
		case R.id.btnPA:
		case R.id.btnPB:
		case R.id.btnPC:
		case R.id.btnPD:
		case R.id.btnPE:
		case R.id.btnPF:
			tmp += ((Button)v).getText().toString();
			exp += ((Button)v).getText().toString();
			break;
		case R.id.btnPAdd:
			processOperator(0);
			break;
		case R.id.btnPMinus:
			processOperator(1);
			break;
		case R.id.btnPMultiply:
			processOperator(2);
			break;
		case R.id.btnPDivide:
			processOperator(3);
			break;
		case R.id.btnPLsh:
			processOperator(4);
			break;
		case R.id.btnPRsh:
			processOperator(5);
			break;
		case R.id.btnPAnd:
			processOperator(6);
			break;
		case R.id.btnPOr:
			processOperator(7);
			break;
		case R.id.btnPEqual:
			processOperator(8);
			break;
		case R.id.btnPClear:
			leftNum = 0;
			rightNum = 0;
			tmp = "";
			exp = "";
			op = -1;
			break;
		case R.id.btnPBack:
			if (op == 4 || op == 5) {
				tmp = tmp.substring(0, tmp.length() - 2);
			} else {
				tmp = tmp.substring(0, tmp.length() - 1);
			}
		}
		
		input.setText(exp.toString());
	}

	private void processOperator(int i) {
		if (leftNum == 0) {
			leftNum = nBaseToDecimal(tmp, N);
			setRadixOutput(leftNum);
			tmp = "";
		} else {
			rightNum = nBaseToDecimal(tmp, N);
			setRadixOutput(rightNum);
			tmp = "";
		}
	
		if (i == 8) {		
			if (leftNum != 0 && rightNum != 0) {	
				res = calculate(op);
				exp = String.valueOf(res);
				leftNum = res;
				setRadixOutput(res);
				rightNum = 0;
				tmp = "";
				op = -1;
			}
			return;
		} else if (op == -1) {				
			exp += opSet[i];
		} else {
			if (rightNum == 0) {
				exp = exp.substring(0, exp.length() - 1) + opSet[i];	
			} else {
//				System.out.println(leftNum);
//				System.out.println(rightNum);
				res = calculate(op);
				setRadixOutput(res);
				exp = String.valueOf(res);
				exp += opSet[i];
				leftNum = res;
				rightNum = 0;
				tmp = "";
			}
		}
		op = i;
	}

	private void setRadixOutput(int num) {
		for (int i = 0; i < radix.length; i++) {
			tvSet[i].setText(decimalToNBase(num, radix[i]));
		}
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
		boolean minusFlag = false;
		
		if (num < 0) {
			num = -num;
			minusFlag = true;
		}
		while (num != 0) {
			stack.push(numToChar(num % N, N));
			num /= N;
		}

		while (!stack.isEmpty()) {
			nBaseNum += stack.pop();
		}
		
		if (minusFlag) {
			nBaseNum = "-" + nBaseNum;
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
