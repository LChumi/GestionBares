package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detventas")
@ToString(exclude = "venta")
@Getter
@Setter
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dventas_venta", referencedColumnName = "vent_id")
    @JsonBackReference
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "dventas_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "dventas_bodega", referencedColumnName = "bod_id")
    private Bodega bodega;

    @Column(name = "dventas_cantidad")
    private int cantidad;

    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_precio_unitario")
    private BigDecimal precioUnitario;

    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_subtotal")
    private BigDecimal subtotal;

    @Column(name = "dventas_tipo_precio")
    private int tipoPrecio; // 1, 2, 3 para indicar el tipo de precio

    @JsonBackReference
    public Venta getVenta(){
        return venta;
    }

    public BigDecimal getSubtotal() {
        return this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        setPrecioUnitario();
    }

    public void setPrecio(int tipoPrecio){
        this.tipoPrecio = tipoPrecio;
        setPrecioUnitario();
    }

    public void setPrecioUnitario() {
        if (this.producto != null){
            switch (this.tipoPrecio){
                case 1:
                    this.precioUnitario = this.producto.getPrecio1();
                    break;
                case 2:
                    this.precioUnitario = this.producto.getPrecio2();
                    break;
                case 3:
                    this.precioUnitario = this.producto.getPrecio3();
                    break;
            }
        }
    }

    public void actualizarSubtotal() {
        setPrecioUnitario();
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
}
