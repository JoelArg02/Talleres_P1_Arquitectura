
package ec.edu.arguello.pruebas;

import ec.edu.arguello.servicios.ConUniServicio;

public class Prueba01 {
    
    public static void main(String[] args){

        double n = 10; 
        
        // 2. Usar String (mayúscula), comillas dobles y punto y coma (;)
        String inU = "meters";
        String outU = "kilometers";

        ConUniServicio service = new ConUniServicio();

        // 3. Usar double para el resultado y el operador de asignación (=)
        double conversion = service.conversion(n, inU, outU);

        // --- FIN DE CORRECCIONES ---
        
        // Reporte
        System.out.println("n: " + n);
        System.out.println("inU: " + inU);
        System.out.println("outU: " + outU);
        System.out.println("Resultado de la conversión: " + conversion);

    }
}
