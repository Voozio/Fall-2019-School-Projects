import java.io.File;
import java.util.Scanner;

public class Subneg {
	public static void main(String[] args) {
		int programCounter = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int[] memory = readFile(args[0]); //args[0]
		
		while (programCounter >= 0) {
			a = memory[programCounter];
	        b = memory[programCounter + 1];
	        c = memory[programCounter + 2];
	        if (a < 0 || b < 0) {
	        	programCounter = -1;
	        } else {
	        	memory[b] = memory[b] - memory[a];
	        	if (memory[b] >= 0) {
	        		programCounter += 3;
	        	} else {
	        		programCounter = c;
	            }
	        }
	    }
		for (int i = 0; i < memory.length-2; i += 3) {
			System.out.println(memory[i] + " " + memory[i+1] + " " + memory[i+2]);
		}
		System.out.println();
//		System.out.println(memory[memory.length-1]); // Default print location
//		System.out.println(memory[1]); // Fibo print location
	}
	
	public static int[] readFile(String file) {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt()) {
				ctr++;
				s.nextInt();
			}
			s.close();
			int[] arr = new int[ctr];
			s = new Scanner(f);
			
			for (int i = 0; i < arr.length; i++) {
				arr[i] = s.nextInt();
			}
			s.close();
			return arr;
		}
		catch (Exception e) {
			return null;
		}
	}
}
