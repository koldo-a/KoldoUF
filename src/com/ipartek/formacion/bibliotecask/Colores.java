package com.ipartek.formacion.bibliotecask;

public class Colores {
    // Formato predeterminado
    public static String normal = "\u001B[0m";

    // Colores de texto
    public static String negro = "\u001B[30m";
    public static String rojo = "\u001B[31m";
    public static String verde = "\u001B[32m";
    public static String amarillo = "\u001B[33m";
    public static String azul = "\u001B[34m";
    public static String magenta = "\u001B[35m";
    public static String cian = "\u001B[36m";
    public static String blanco = "\u001B[37m";

    // Colores de fondo
    public static String fondoNegro = "\u001B[40m";
    public static String fondoRojo = "\u001B[41m";
    public static String fondoVerde = "\u001B[42m";
    public static String fondoAmarillo = "\u001B[43m";
    public static String fondoAzul = "\u001B[44m";
    public static String fondoMagenta = "\u001B[45m";
    public static String fondoCian = "\u001B[46m";
    public static String fondoBlanco = "\u001B[47m";

    // Combinación de estilos y colores
    public static String textoRojoNegrita = "\u001B[1;31m";
    public static String fondoVerdeSubrayado = "\u001B[4;42m";

    // Restablecer formato
    public static String reset = "\u001B[0m";

    // Estilos de texto
    public static String negrita = "\u001B[1m";
    public static String subrayado = "\u001B[4m";
    public static String parpadeante = "\u001B[5m";
    public static String inverso = "\u001B[7m";
}
