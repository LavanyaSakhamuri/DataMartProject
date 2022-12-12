package Gomo;

import utility.Custom_Functions;
import utility.Generic;

public class GetOtp {

	public static void main(String[] args) throws Exception 
	{
		String otp = Custom_Functions.getOtp(Generic.ReadFromExcel("GCashNumber","AI_TestData",1), false);
		
		System.out.println(otp);
	}
}
