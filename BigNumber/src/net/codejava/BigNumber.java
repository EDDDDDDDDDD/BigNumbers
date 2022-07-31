package net.codejava;
import java.util.*;
public class BigNumber {
	/*
	 *   Java program to find the sum(+), difference(-), 
	 *   product(*), quotient(/) of formulas.
	 */
	
	// Function to test all combinations 
	// for addition and subtraction. 
	// Results and formulas will generate themselves.
	static void testAllComofSumDiff(){
		// Please set the same length for numbers
		String big = "10";
		String small = "01";
		int numSize = big.length();
		String str = "";
		for (int i=0; i<2; i++) {
			if (i==1)
				str += "-";
			for (int j=0; j<2; j++) {
				if (j==0)
					str += small;
				else
					str += big;
				for (int k=0; k<2; k++) {
					if (k==0)
						str += " + ";
					else
						str += " - ";
					for ( int l=0; l<2; l++) {
						if (l==1)
							str += "-";
						for (int m=0; m<2; m++) {
							if (m==0)
								str += small + " =";
							else
								str += big + " =";
							System.out.println(str);
							System.out.println(calculator(str) + "\n");
							str = str.substring(0, i+numSize+3+l);
						}
						str = str.substring(0, i+numSize+3);
					}
					str = str.substring(0, i+numSize);
				}
				str = str.substring(0, i);
			}
			str = "";
		}
	}
	
	// Function to compare str1 and str2 
	// Return True if str1 < str2, False if str1 > str2 
	static boolean str1IsSmaller(String str1, String str2) 
	{ 
	    // Calculate lengths of both string 
	    int n1 = str1.length(), n2 = str2.length(); 
	    if (n1 < n2) 
	        return true; 
	    if (n1 > n2) 
	        return false; 
	    
	    // If the lengths are equal, compare each number
	    for (int i = 0; i < n1; i++) 
	    { 
	        if (str1.charAt(i) < str2.charAt(i)) 
	            return true; 
	        else if (str1.charAt(i) > str2.charAt(i)) 
	            return false; 
	    } 
	    return false;
	} 
	
	// Function to ignore the leading zeros
	static String ignoreZero(String str)
	{
		while (str.length() > 1 && str.charAt(0) == '0') {
			str = str.substring(1);
		}
		return str;
	}
	
	// Function to find the sum of two larger numbers
	static String findSum(String str1, String str2)
	{	
		
		boolean negative = false, neg1 = false, neg2 = false;
		if( str1.charAt(0) == '-' ) {
			neg1 = true;
			str1 = str1.substring(1);
		}
		if( str2.charAt(0) == '-' ) {
			neg2 = true;
			negative = neg1 && neg2;
			str2 = str2.substring(1);
		}
		
		// Before proceeding further, make sure
		// str1 is larger.
		if (str1IsSmaller(str1, str2)){
			String t = str1;
	        str1 = str2;
	        str2 = t;
	        
	        // Str1 < Str2
	        // str1 < 0 and str2 > 0,
	        // so the result must be positive
	        if ( neg1 && !neg2 )
	        	return findDiff(str1, str2);
	        
	        // str1 > 0 and str2 < 0,
	        // so the result must be negative
	        if ( !neg1 && neg2 ) {
	        	String result = findDiff(str1, str2);
	        	if ( result.matches("[0]+") )
	        		return "0";
	        	return "-" + result;
	        }
		}
		// Str1 > Str2
		// str1 < 0 and str2 > 0,
		// so the result must be negative
		if ( neg1 && !neg2 ) {
        	String result = findDiff(str1, str2);
        	
        	// return "0" if result is 0
        	if ( result.matches("[0]+") )
        		return "0";
        	
        	return "-" + result;
        }
		// str1 > 0 and str2 < 0,
		// so the result will be the difference between them
		if ( !neg1 && neg2 )
			return findDiff(str1, str2);
		
		// Take an empty String for storing result
		String str = "";
		
		
		str1 = ignoreZero(str1);
		str2 = ignoreZero(str2);
		
		// Reverse both of Strings
		str1=new StringBuilder(str1).reverse().toString();
		str2=new StringBuilder(str2).reverse().toString();

		// Calculate length of both String
		int n1 = str1.length(), n2 = str2.length();
		int carry = 0;
		for (int i = 0; i < n2; i++)
		{
			// Compute sum of current digits and carry	
			int sum = ((int)(str1.charAt(i) - '0') +
						(int)(str2.charAt(i) - '0') + carry);
			str += (char)(sum % 10 + '0');
	
				// Calculate carry for next step
			carry = sum / 10;
		}

		// Add remaining digits of larger number
		for (int i = n2; i < n1; i++)
		{
			int sum = ((int)(str1.charAt(i) - '0') + carry);
			str += (char)(sum % 10 + '0');
			carry = sum / 10;
		}

		// Add remaining carry
		if (carry > 0)
			str += (char)(carry + '0');
		
		
		// return "0" if result is 0
		if ( str.matches("[0]+") )
	    	return "0";
	    
		
		
		// return negative result, cuz str1 and str2 are all negative
		if( negative )
			return "-" + new StringBuilder(str).reverse().toString();
		// reverse resultant String
		return new StringBuilder(str).reverse().toString();
		
	}
	
	// Function to find the difference of two larger numbers 
	static String findDiff(String str1, String str2) 
	{ 
		boolean negative = false, neg1 = false, neg2 = false;
		if( str1.charAt(0) == '-' ) {
			neg1 = true;
			str1 = str1.substring(1);
		}
		if( str2.charAt(0) == '-' ) {
			neg2 = true;
			negative = neg1 && neg2;
			str2 = str2.substring(1);
		}
		
		boolean changed = false;
		// Before proceeding further, make sure
		// str1 is larger.
	    if (str1IsSmaller(str1, str2)) { 
	        String t = str1;
	        str1 = str2;
	        str2 = t;
	        changed = true;
	    }
	    
	    if ( !neg1 && neg2 )
	    	return findSum(str1, str2);
	    if( neg1 && !neg2 ) {
        	String result = findSum(str1, str2);
        	
        	// return "0" if result is 0
        	if ( result.matches("[0]+") )
        		return "0";
        	
        	return "-" + result;
        }
	    
	    // Take an empty string for storing result 
	    String str = ""; 

	    // Calculate lengths of both string 
	    int n1 = str1.length(), n2 = str2.length(); 
	    int diff = n1 - n2; 
	  
	    // Initially take carry zero 
	    int carry = 0; 

	    // Traverse from end of both strings
	    for (int i = n2 - 1; i >= 0; i--) 
	    { 
	        // Do school mathematics, compute difference of
	        // current digits and carry 
	        int sub = (((int)str1.charAt(i + diff) - (int)'0') - 
	                    ((int)str2.charAt(i) - (int)'0') - carry); 
	        if (sub < 0) { 
	            sub = sub+10; 
	            carry = 1; 
	        } 
	        else
	            carry = 0; 
	        str += String.valueOf(sub); 
	    } 

	    // subtract remaining digits of str1[] 
	    for (int i = n1 - n2 - 1; i >= 0; i--) 
	    { 
	        if (str1.charAt(i) == '0' && carry > 0) { 
	            str += "9"; 
	            continue; 
	        } 
	        int sub = (((int)str1.charAt(i) - (int)'0') - carry);
	        if (i > 0 || sub > 0) // remove preceding 0's 
	            str += String.valueOf(sub); 
	        carry = 0;
	    }
	    
	    // return "0" if result is 0
	    if ( str.matches("[0]+") )
	    	return "0";
	    
	    str = new StringBuilder(str).reverse().toString();
	    str = ignoreZero(str);
	    
	    // reverse resultant string
	    if( (!changed && negative) || (changed && !negative) )
	    	return "-" + str;
	    return str;
	}
	
	// Function to find the product of two larger numbers
	static String findProd(String str1, String str2)
	{
		boolean negative = false;
		if( str1.charAt(0) == '-' ) {
			negative = true;
			str1 = str1.substring(1);
		}
		if( str2.charAt(0) == '-' ) {
			negative = negative ^ str2.charAt(0) == '-';
			str2 = str2.substring(1);
		}
		
		String s1 = new StringBuffer(str1).reverse().toString();
		String s2 = new StringBuffer(str2).reverse().toString();

		int[] m = new int[s1.length() + s2.length()];

	    // Go from right to left in str1
	    for (int i = 0; i < s1.length(); i++) {
	        // Go from right to left in str2
	        for (int j = 0; j < s2.length(); j++) {
	            m[i + j] += (s1.charAt(i) - '0') * 
	                        (s2.charAt(j) - '0');
	        }
	    }
	
	    String product = new String();
	    // Multiply with current digit of first number
	    // and add result to previously stored product
	    // at current position.
	    for (int i = 0; i < m.length; i++) {
	        int digit = m[i] % 10;
	        int carry = m[i] / 10;
	        if (i + 1 < m.length) {
	            m[i + 1] += carry;
	        }
	        product = digit + product;
	    }
	
	    product = ignoreZero(product);
	    
	    if ( negative ) 
	    	return "-" + product;
	    return product;
	}
	
	// Function to find the quotient of two larger numbers
	static String findQuot(String str1, String str2) 
	{
		boolean negative = false;
		if( str1.charAt(0) == '-' ) {
			negative = true;
			str1 = str1.substring(1);
		}
		if( str2.charAt(0) == '-' ) {
			negative = negative ^ str2.charAt(0) == '-';
			str2 = str2.substring(1);
		}
		String result = new String();

	    // We will be iterating the dividend 
	    // so converting it to char array
	
	    // Initially the carry would be zero
		String remainder = "0";
	    String prod = new String();
	    
	    // Iterate the dividend
	    for (int i = 0; i < str1.length(); i++) {
	        // Prepare the str1 to be divided
	    	String x = findSum(findProd(remainder, "10"), ""+str1.charAt(i));
	        String multi = new String();
	        
	        for (int j=1; j<10; j++) {
	        	multi = Integer.toString(j);
	        	prod = findProd(str2, multi);
	        	
	        	// Append the result with partial quotient
	        	if( str1IsSmaller(x, prod) ) {
	        		multi = Integer.toString(j-1);
		        	prod = findProd(str2, multi);
		        	result += multi;
	        		break;
	        	}
	        }
	        
	        // Prepare the carry for the next Iteration
	        remainder = findDiff(x, prod);
	        
	    }
	    
	    if( result.matches("[0]+") )
	    	return "0";
	    
	    // Remove any leading zeros
	    for (int i = 0; i < result.length(); i++) {
	        if ( result.charAt(i) != '0' ) {
	            // Return the result
	        	if ( negative )
	        		return "-" + result.substring(i);
	            return result.substring(i);
	        }
	    }
	    // Return empty string if str1 is empty
	    return "";
	}
	
	// Function that processes formulas
	static String calculator(String formula)
	{
		// Splits formula into numbers and operations
		String[] splits = formula.split(" ");
		List<String> nums = new ArrayList<String>();
		List<String> opers = new ArrayList<String>();
		
		
		// Process the multiplication first, 
		// then put it back on the stack.
		for (int i=0; i < splits.length; i++) {
			if ( splits[i].matches("[+-]") ) {
				opers.add(splits[i]);
			}
			else if ( splits[i].matches("[-]?[0-9]+") ) {
				nums.add(splits[i]);
			}
			else if ( splits[i].equals("*") ) { 
				i++; 
				int last = nums.size();
				if( last == 0 ) return "Formula starts with multiply!";
				String lastNum = nums.remove(last - 1); 
				nums.add(findProd(lastNum, splits[i]));
			}
			else if ( splits[i].equals("/") ) { 
				i++; 
				int last = nums.size();
				if( last == 0 ) return "Formula starts with multiply!";
				String lastNum = nums.remove(last - 1);
				nums.add(findQuot(lastNum, splits[i]));
			}
		}

		// Check numbers and operations
		if ( nums.size() != opers.size() + 1 ) {
			return "Please check formula!";
		}
		
		// Process addition and subtraction to get the final result
		for (int i=0; i<opers.size(); i++) {
			String str1 = nums.remove(0);
			String str2 = nums.remove(0);
			if( opers.get(i).equals("+") ) 
				nums.add(0, findSum(str1, str2));
			else if( opers.get(i).equals("-") ) {
				nums.add(0, findDiff(str1, str2));
			}
		}
		return nums.get(0);
	}


	public static void main(String[] args)
	{
		
//		testAllComofSumDiff();
		
		// Please separate numbers and operators with spaces
		// Program can detect negative numbers
		// 1 + 2 * 3 + -4 * 2 - 5
		String formula = "1 + 2 * 3 + -4 * 2 - -15 / 5 * 2 =";
		System.out.println(formula + "\n" + calculator(formula));
	}

}
