package com.example.backendAppMovil.util;

public class RutUtils {
    public static boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) return false;

        rut = rut.replace(".", "").replace("-", "");
        if (rut.length() < 2) return false;

        try {
            String dvIngresado = rut.substring(rut.length() -1).toUpperCase();
            String numeroStr = rut.substring(0, rut.length() -1);
            int numero = Integer.parseInt(numeroStr);

            int m = 0, s = 1;
            for (; numero !=0; numero /=10) {
                s = (s + numero %10 * (9 - m++ %6)) %11;
            }
            String dvCalculado = (char) (s != 0 ? s +47 : 75) + "";
            return dvCalculado.equals(dvIngresado);

        }catch (NumberFormatException e) {
            return false;
        }
    }
}
