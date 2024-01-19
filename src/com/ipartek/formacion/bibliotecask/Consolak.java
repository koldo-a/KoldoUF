package com.ipartek.formacion.bibliotecask;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Consolak {
	private static final Scanner sc = new Scanner(System.in);
	
	public static void pln(String mensaje) {
		System.out.println(mensaje);
	}
	
	public static String leerString(String mensaje) {
		System.out.print(mensaje + ": ");
		return sc.nextLine();
	}
	
	public static Long leerLong(String mensaje) {
		boolean hayError = true;
		long l = 0;
		
		do {
			try {
				String dato = leerString(mensaje);

				if(dato.trim().length() == 0) {
					return null; 
				}
				
				l = Long.parseLong(dato);
				hayError = false;
			} catch (NumberFormatException e) {
				System.out.println("El número debe ser un entero entre " + Long.MIN_VALUE + " y " + Long.MAX_VALUE);
			}
		} while (hayError);

		return l;
	}
	public static Integer leerInt(String mensaje) {
		return leerInt(mensaje, false);
	}
	
	public static Integer leerInt(String mensaje, boolean opcional) {
		boolean hayError = true;
		int i = 0;
		
		do {
			try {
				String dato = leerString(mensaje);
				
				if(opcional && dato.trim().length() == 0) {
					return null; 
				}
				
				i = Integer.parseInt(dato);
				hayError = false;
			} catch (NumberFormatException e) {
				System.out.println("El número debe ser un entero entre " + Integer.MIN_VALUE + " y " + Integer.MAX_VALUE);
			}
		} while (hayError);

		return i;
	}

	public static LocalDate leerFecha(String mensaje) {
		boolean hayError = true;
		LocalDate fecha = null;
		
		do {
			try {
				String dato = leerString(mensaje + " [AAAA-MM-DD] ");

				if(dato.trim().length() == 0) {
					return null; 
				}
				
				fecha = LocalDate.parse(dato);
				hayError = false;
			} catch (DateTimeParseException e) {
				System.out.println("La fecha debe ser válida");
			}
		} while (hayError);
		
		return fecha;
	}
}
