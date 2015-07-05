package ARITH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static class BigNumber{
		int[] bigNumber = null;
		public BigNumber(String numStr){
			char[] dst = new char[numStr.length()];
			bigNumber = new int[dst.length];
			numStr.getChars(0, numStr.length(), dst, 0);
			for(int i=bigNumber.length-1; i >= 0; i--){
				bigNumber[i] = Integer.parseInt("" + dst[bigNumber.length-1-i]); 
			}
		}
		public BigNumber(int[]num){
			bigNumber = num;
		}
		public BigNumber(Integer[]num){
			bigNumber = new int[num.length];
			for(int i = 0; i < bigNumber.length; i++)bigNumber[i] = num[i];
		}
		public BigNumber add(BigNumber bn){
			ArrayList<Integer> result = new ArrayList<Integer>();
			add(this.bigNumber, bn.bigNumber, 0, 0, result);
			return new BigNumber(result.toArray(new Integer[result.size()]));
		}
		public BigNumber subtract(BigNumber bn){
			ArrayList<Integer> result = new ArrayList<Integer>();
			subtract(this.bigNumber, bn.bigNumber, 0, 0, result);
			cleanZeroes(result);
			return new BigNumber(result.toArray(new Integer[result.size()]));
		}
		public BigNumber[] multiply(BigNumber bn){
			ArrayList<BigNumber> result = new ArrayList<BigNumber>();
			int maxLength = 0;
			for(int i = 0; i < bn.bigNumber.length; i++){
				ArrayList<Integer> multResult = new ArrayList<Integer>();
				multiply(bigNumber,bn.bigNumber[i],multResult);
				cleanZeroes(multResult);
				result.add(new BigNumber(multResult.toArray(new Integer[multResult.size()])));
				if(i+multResult.size()>maxLength)maxLength = i+multResult.size();
			}			
			int overflow = 0;
			ArrayList<Integer> finalResult = new ArrayList<Integer>(); 
			for(int i = 0; i < maxLength; i++){
				int currRes = 0;
				for(int j = 0; j <= i&&j<result.size(); j++){
					if(result.get(j).bigNumber.length>(i-j)){
						currRes += result.get(j).bigNumber[i-j];
					}
				}
				if((currRes+overflow)>=10){
					finalResult.add((currRes+overflow)%10);
					overflow = (currRes+overflow)/10;
				}
				else{
					finalResult.add(currRes+overflow);
					overflow = 0;
				}
			}
			if(overflow>0)finalResult.add(overflow);
			result.add(new BigNumber(finalResult.toArray(new Integer[finalResult.size()])));			
			return result.toArray(new BigNumber[result.size()]);
		}
		public void add(int[]first, int[]second, int index, int isOverflow, ArrayList<Integer>result){
			if(index >= first.length&&index>=second.length){
				if(isOverflow==1)result.add(isOverflow);
				return;				
			}
			if(index >= first.length){
				if(isOverflow+second[index]>=10){
					result.add((isOverflow+second[index])%10);
					add(first, second, index+1, 1, result);
				}
				else{
					result.add(isOverflow+second[index]);
					add(first, second, index+1, 0, result);
				}
				return;
			}
			if(index >= second.length){
				if(isOverflow+first[index]>=10){
					result.add((isOverflow+first[index])%10);
					add(first, second, index+1, 1, result);
				}
				else{
					result.add(isOverflow+first[index]);
					add(first, second, index+1, 0, result);
				}
				return;
			}
			if(isOverflow+first[index]+second[index]>=10){
				result.add((isOverflow+first[index]+second[index])%10);
				add(first,second,index+1,1,result);
			}
			else{
				result.add(isOverflow+first[index]+second[index]);
				add(first,second,index+1,0,result);
			}
		}
		public void subtract(int[]first, int[]second, int index, int isUnderflow, ArrayList<Integer>result){
			if(index>=first.length&&index>=second.length){
				return;
			}
			if(index>=second.length){
				if(first[index]-isUnderflow<0){
					result.add((10+first[index]-isUnderflow)%10);
					subtract(first, second, index+1, 1, result);
				}
				else{
					result.add(first[index]-isUnderflow);
					subtract(first, second, index+1, 0, result);
				}
				return;
			}
			if(first[index]-isUnderflow-second[index]<0){
				result.add((10+first[index]-isUnderflow-second[index])%10);
				subtract(first, second, index+1, 1, result);
			}
			else{
				result.add(first[index]-isUnderflow-second[index]);
				subtract(first, second, index+1, 0, result);
			}
		}
		public void multiply(int[]first, int secondNum, ArrayList<Integer>result){
			int overflow = 0;
			for(int i = 0; i < first.length; i++){
				int currRes = overflow+first[i]*secondNum;
				overflow = currRes/10;
				result.add(currRes%10);
			}
			if(overflow>0)result.add(overflow);
			cleanZeroes(result);
		}
		public String toString(){
			StringBuffer result = new StringBuffer();
			for(int i = bigNumber.length-1; i >= 0; i--){
				result.append(bigNumber[i]);
			}
			return result.toString();
		}
		public static void formatOp(BigNumber b1, BigNumber b2, BigNumber res, String op){
			int maxLength = Math.max(b1.bigNumber.length, Math.max(b2.bigNumber.length+1,res.bigNumber.length));
			StringBuffer toFill = new StringBuffer();
			for(int i = 0; i<maxLength-b1.bigNumber.length; i++){
				toFill.append(" ");
			}
			System.out.println(toFill+b1.toString());
			toFill = new StringBuffer();
			for(int i = 0; i<maxLength-(b2.bigNumber.length+1); i++){
				toFill.append(" ");
			}
			System.out.println(toFill+op+b2.toString());			
			toFill = new StringBuffer();
			for(int i = 0; i<maxLength; i++){
				toFill.append("-");
			}
			System.out.println(toFill);
			toFill = new StringBuffer();
			for(int i = 0; i<maxLength-res.bigNumber.length; i++){
				toFill.append(" ");
			}
			System.out.println(toFill+res.toString());
		}
		public static void formatMult(BigNumber b1, BigNumber b2, BigNumber[]res){
			int maxInput = Math.max(b1.bigNumber.length, b2.bigNumber.length+1);
			int maxLength = Math.max(maxInput, res[res.length-1].bigNumber.length);
			StringBuffer toFill = new StringBuffer();
			for(int i = 0; i<maxLength-b1.bigNumber.length; i++){
				toFill.append(" ");
			}
			System.out.println(toFill+b1.toString());
			toFill = new StringBuffer();
			for(int i = 0; i<maxLength-(b2.bigNumber.length+1); i++)toFill.append(" ");
			System.out.println(toFill+"*"+b2.toString());			
			toFill = new StringBuffer();
			if(b2.bigNumber.length!=1){
				for(int i = 0; i<maxLength-maxInput; i++)toFill.append(" ");
				for(int i = 0; i < maxInput; i++)toFill.append("-");
			}
			else{
				for(int i = 0; i < maxLength; i++)toFill.append("-");
			}
			System.out.println(toFill);
			for(int i = 0; i < res.length-1; i++){
				toFill = new StringBuffer();
				for(int j = 0; j < maxLength-(res[i].bigNumber.length+i); j++)toFill.append(" ");
				System.out.println(toFill+res[i].toString());				
			}
			if(b2.bigNumber.length==1)return;
			toFill = new StringBuffer();
			for(int i = 0; i < maxLength; i++)toFill.append("-");
			System.out.println(toFill);
			toFill = new StringBuffer();
			for(int i = 0; i < maxLength-res[res.length-1].bigNumber.length; i++)toFill.append(" ");
			System.out.println(toFill+res[res.length-1].toString());
			//lastMult.size()+Math.max(b1.bigNumber.length-1, b2.bigNumber.length-1);
		}
		private void cleanZeroes(ArrayList<Integer> result) {
			for(int i = result.size()-1; i >= 0; i--){
				if(result.get(i)==0&&i!=0){
					result.remove(i);
					continue;
				}
				if(result.get(i)!=0)break;
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		handleInput();
//		testOperations();
	}

	private static void handleInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		int testCases = -1;
		while(null != (line = br.readLine())){
			if(testCases == -1){
				testCases = Integer.parseInt(line);
				continue;
			}
			BigNumber b1 = null;
			BigNumber b2 = null;
			BigNumber res = null;
			BigNumber[] resArr = null;
			if(line.split("-").length>1){
				String[] substr = line.split("-");
				b1 = new BigNumber(substr[0]);
				b2 = new BigNumber(substr[1]);
				res = b1.subtract(b2);
				BigNumber.formatOp(b1, b2, res, "-");
			}
			else if(line.split("\\*").length>1){
				String[] mult = line.split("\\*");
				b1 = new BigNumber(mult[0]);
				b2 = new BigNumber(mult[1]);
				resArr = b1.multiply(b2);
				BigNumber.formatMult(b1, b2, resArr);
			}
			else if(line.split("\\+").length>1){
				String[] add = line.split("\\+");
				b1 = new BigNumber(add[0]);
				b2 = new BigNumber(add[1]);
				res = b1.add(b2);
				BigNumber.formatOp(b1, b2, res, "+");
			}

			System.out.println();
			if(--testCases<=0)break;
		}
	}

	private static void testOperations() {
		BigNumber bn1 = null;
		BigNumber bn2 = null;
		BigNumber res = null;

		bn1 = new BigNumber("1");
		bn2 = new BigNumber("10");
		res = bn1.add(bn2);
		BigNumber.formatOp(bn1,bn2,res,"+");

		System.out.println();

		bn1 = new BigNumber("1");
		bn2 = new BigNumber("1");
		res = bn1.subtract(bn2);
		BigNumber.formatOp(bn1,bn2,res,"-");

		System.out.println();

		bn1 = new BigNumber("35");
		bn2 = new BigNumber("111111111");
		BigNumber[] resArr = bn1.multiply(bn2);
		BigNumber.formatMult(bn1, bn2, resArr);
	}

}
