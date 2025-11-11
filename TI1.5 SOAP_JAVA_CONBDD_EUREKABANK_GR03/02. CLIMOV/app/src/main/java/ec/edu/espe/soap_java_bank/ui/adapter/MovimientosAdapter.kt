package ec.edu.espe.soap_java_bank.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.soap_java_bank.R
import ec.edu.espe.soap_java_bank.data.models.Movimiento
import java.text.DecimalFormat

class MovimientosAdapter : RecyclerView.Adapter<MovimientosAdapter.MovimientoViewHolder>() {

    private val movimientos = mutableListOf<Movimiento>()
    private val df = DecimalFormat("#,##0.00")

    fun submitList(newMovimientos: List<Movimiento>) {
        movimientos.clear()
        movimientos.addAll(newMovimientos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movimiento, parent, false)
        return MovimientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {
        holder.bind(movimientos[position])
    }

    override fun getItemCount(): Int = movimientos.size

    inner class MovimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTipoMovimiento: TextView = itemView.findViewById(R.id.tvTipoMovimiento)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvNumeroCuenta: TextView = itemView.findViewById(R.id.tvNumeroCuenta)
        private val tvImporte: TextView = itemView.findViewById(R.id.tvImporte)

        fun bind(movimiento: Movimiento) {
            tvTipoMovimiento.text = "${movimiento.tipo} (${movimiento.accion})"
            tvFecha.text = movimiento.fecha.substring(0, 10)
            tvNumeroCuenta.text = "Cuenta: ${movimiento.cuenta}"
            tvImporte.text = "$${df.format(movimiento.importe)}"
        }
    }
}
