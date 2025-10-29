package ec.edu.carrera.controller;

import ec.edu.carrera.model.Conversor;

/**
 *
 * @author nahir
 */
public class ConversorController {
    private Conversor calculator;
    
    public ConversorController() {
        this.calculator = new Conversor();
    }
    
    public double performConversion(int number, String inUnit, String outUnit) {
        try {
            return calculator.convertUnit(number, inUnit, outUnit );
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con el servicio web: " + e.getMessage());
        }
    }
}
