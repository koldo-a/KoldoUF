package com.ipartek.formacion.uf2213;

import static com.ipartek.formacion.bibliotecask.Consolak.*;

public class ValidacionDNI {
	
	private static final String LETRAS_NIE = "XYZ";
	private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		do {
			mostrarMenu();
			String dni = leerString("DNI");
			dni = dni.toUpperCase();
			if(!dni.matches("^[" + LETRAS_NIE + "\\d]\\d{7}[" + LETRAS_DNI + "]$")) {
				plnRojoNegr("No es un DNI o NIE válido.");
			}
			
			longitud(dni);
			
			char primeraLetra = dni.charAt(0);
			int indiceLetraNIE = LETRAS_NIE.indexOf(primeraLetra);

			String num;

			if (indiceLetraNIE != -1) {
				num = indiceLetraNIE + dni.substring(1, 8);
			} else {
				num = dni.substring(0, 8);
			}

			char letra = dni.charAt(8);

			System.out.println("No está bien. La letra debe ser la: " + LETRAS_DNI.charAt(Integer.parseInt(num) % 23));
						
		} while (true);
		
	}

	private static void longitud(String dni) {
		dni = dni.toUpperCase();
		if (dni.length() == 9) {
			plnVerde("Longitud correcta.");
			
		char lastChar = dni.charAt(dni.length() - 1);
        if (Character.isLetter(lastChar)) {
            plnVerde("El DNI: "+ dni +" es válido.");
            // Puedes continuar con el procesamiento de la cadena aquí
        } else {
            plnRojo("La cadena debe tener 8 caracteres y una letra al final.");
            // Puedes manejar el caso en el que el último carácter no sea una letra
        }
		
		} else {plnRojoNegr("Longitud incorrecta. Repite por favor");
		};
	}
	
	private static void mostrarMenu() {

			plnAzulNegr(""" 
					-----------------------------
					Introduce DNI:
					-----------------------------    
							"""
			);
		}
	}
