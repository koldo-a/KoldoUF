package com.ipartek.formacion.bibliotecask;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Consolak {
	
	private static final Scanner sc = new Scanner(System.in);
	
	public static void pln(String mensaje) {
		System.out.println(mensaje);
	}
	
    public static void plnColor(String mensaje, String color) {
        String ansiInicial = color;
        String ansiFinal = Colores.normal;
        pln(ansiInicial + mensaje + ansiFinal);
    }
    public static void plnColorNegr(String mensaje, String color) {
    	String ansiInicial = color.replace("[", "[7;");
    	String ansiFinal = Colores.normal;
    	pln(ansiInicial + mensaje + ansiFinal);
    }
    public static void plnColorSub(String mensaje, String color) {
    	String ansiInicial = color.replace("[", "[4;");
    	String ansiFinal = Colores.normal;
    	pln(ansiInicial + mensaje + ansiFinal);
    }

    public static void plnRojo(String mensaje) {
        plnColor(mensaje, Colores.rojo);
    }

    public static void plnAzul(String mensaje) {
        plnColor(mensaje, Colores.azul);
    }

    public static void plnVerde(String mensaje) {
        plnColor(mensaje, Colores.verde);
    }

    public static void plnAmarillo(String mensaje) {
        plnColor(mensaje, Colores.amarillo);
    }
    public static void plnRojoNegr(String mensaje) {
    	plnColorNegr(mensaje, Colores.rojo);
    }
    
    public static void plnAzulNegr(String mensaje) {
    	plnColorNegr(mensaje, Colores.azul);
    }
    
    public static void plnVerdeNegr(String mensaje) {
    	plnColorNegr(mensaje, Colores.verde);
    }
    
    public static void plnAmarilloNegr(String mensaje) {
    	plnColorNegr(mensaje, Colores.amarillo);
    }
    
    public static void plnRojoSub(String mensaje) {
    	plnColorSub(mensaje, Colores.rojo);
    }
    
    public static void plnAzulSub(String mensaje) {
    	plnColorSub(mensaje, Colores.azul);
    }
    
    public static void plnVerdeSub(String mensaje) {
    	plnColorSub(mensaje, Colores.verde);
    }
    
    public static void plnAmarilloSub(String mensaje) {
    	plnColorSub(mensaje, Colores.amarillo);
    }

	public static String leerString(String mensaje) {
		pln(mensaje + ": ");
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
				plnRojo("El número debe ser un entero entre " + Long.MIN_VALUE + " y " + Long.MAX_VALUE);
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
				plnRojo("El número debe ser un entero entre " + Integer.MIN_VALUE + " y " + Integer.MAX_VALUE);
			}
		} while (hayError);

		return i;
	}

	public static LocalDate leerFecha(String mensaje) {
		boolean hayError = true;
		LocalDate fecha = null;
		
		do {
			try {
				String dato = leerString(mensaje + " [AAAA-MM-DD]: ");

				if(dato.trim().length() == 0) {
					return null; 
				}
				
				fecha = LocalDate.parse(dato);
				hayError = false;
			} catch (DateTimeParseException e) {
				plnRojo("La fecha debe ser válida");
			}
		} while (hayError);
		
		return fecha;
	}
}
