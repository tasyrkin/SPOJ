package PRIME1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		int testCases = -1;
		while(null != (line = br.readLine())){
			if(testCases == -1){
				testCases = Integer.parseInt(line);
				continue;
			}
			String[] parts = line.split("\\s+");
			int N1 = Integer.parseInt(parts[0]);
			int N2 = Integer.parseInt(parts[1]);
			for(int N = N1; N <= N2; N++){
				if(isPrime2(N,3)){
					System.out.println(N);
				}
			}
			if(--testCases<=0)break;
			System.out.println();
		}
	}
	public static boolean isPrime2(int n, int t){
		if(n==1)return false;
		if(n==2)return true;
		if(n==3)return true;
		if(n%2==0)return false;
		int r = n-1;
		int s = 0;
		while(r%2==0){
			r=r/2;
			s++;
		}
		for(int i = 1; i <= t; i++){
			long a = (long)((n-4)*Math.random()+2);
			long rest = 1;
			int pow = r;
			long a1 = a;
			while(pow>0){
				if(pow%2==1)rest=(rest*a1)%n;
				pow/=2;
				a1 = (a1*a1)%n;
			}
			long y = rest;
			if(y!=1&&y!=n-1){
				int j = 1;
				while(j<=s-1&&y!=n-1){
					y = (y*y)%n;
					if(y==1)return false;
					j++;
				}
				if(y!=n-1)return false;
			}
		}
		return true;		
	}
}
