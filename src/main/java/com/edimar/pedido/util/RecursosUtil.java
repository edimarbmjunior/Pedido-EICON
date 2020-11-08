package com.edimar.pedido.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.edimar.pedido.exceptions.GenericExcpetion;

public class RecursosUtil {

	/**
	 * Metodo para transformar data em string para Date em java
	 * @param data em string(exemplo 05/10/2020 13:58)
	 * @return class Date
	 */
	public static Date converterStringToDate(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(data);
		} catch (ParseException e) {
			throw new GenericExcpetion("Error no parse de data");
		}
	}
	
	/**
	 * Metodo para transformar data em string para Date em java
	 * @param data em string(exemplo 05/10/2020 13:58)
	 * @return class Date
	 */
	public static String converterDateToString(Date data) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(data);
		
	}
	
	public static Double casasDecimais(Double numero) {
		BigDecimal bd = new BigDecimal(Double.toString(numero));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
