package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dventas_venta", referencedColumnName = "vent_id")
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
