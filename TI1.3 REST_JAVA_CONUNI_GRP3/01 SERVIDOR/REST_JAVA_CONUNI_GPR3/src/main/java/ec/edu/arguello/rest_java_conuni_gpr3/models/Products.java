
package ec.edu.arguello.rest_java_conuni_gpr3.models;

public class Products {

    public Products(int IdProduct, String descripcion, double precio) {
        this.IdProduct = IdProduct;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    private int IdProduct;
    private String descripcion;
    private double precio;
  
    public void setIdProduct(int IdProduct) {
        this.IdProduct = IdProduct;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
  

    public int getIdProduct() {
        return IdProduct;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }
}
