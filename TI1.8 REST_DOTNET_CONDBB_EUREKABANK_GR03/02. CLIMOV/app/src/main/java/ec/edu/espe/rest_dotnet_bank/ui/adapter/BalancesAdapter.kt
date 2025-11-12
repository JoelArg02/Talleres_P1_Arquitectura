package ec.edu.espe.rest_dotnet_bank.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.rest_dotnet_bank.R
import ec.edu.espe.rest_dotnet_bank.data.models.Cuenta
import java.text.DecimalFormat

class BalancesAdapter : RecyclerView.Adapter<BalancesAdapter.BalanceViewHolder>() {

    private val balances = mutableListOf<Cuenta>()
    private val df = DecimalFormat("#,##0.00")

    fun submitList(newBalances: List<Cuenta>) {
        balances.clear()
        balances.addAll(newBalances)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_balance, parent, false)
        return BalanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(balances[position])
    }

    override fun getItemCount(): Int = balances.size

    inner class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNumeroCuenta: TextView = itemView.findViewById(R.id.tvNumeroCuenta)
        private val tvNombreCliente: TextView = itemView.findViewById(R.id.tvNombreCliente)
        private val tvSaldo: TextView = itemView.findViewById(R.id.tvSaldo)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)

        fun bind(cuenta: Cuenta) {
            tvNumeroCuenta.text = cuenta.numeroCuenta
            tvNombreCliente.text = cuenta.nombreCliente
            tvSaldo.text = "$${df.format(cuenta.saldo)}"
            tvEstado.text = cuenta.estado
        }
    }
}
