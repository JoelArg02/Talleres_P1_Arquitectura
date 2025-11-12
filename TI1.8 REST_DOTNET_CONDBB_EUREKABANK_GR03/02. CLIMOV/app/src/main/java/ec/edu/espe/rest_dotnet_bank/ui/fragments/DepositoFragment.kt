package ec.edu.espe.rest_dotnet_bank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ec.edu.espe.rest_dotnet_bank.R
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.ui.viewmodel.DepositoViewModel

class DepositoFragment : Fragment() {

    private lateinit var viewModel: DepositoViewModel
    private lateinit var etCuenta: EditText
    private lateinit var etImporte: EditText
    private lateinit var btnDepositar: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deposito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DepositoViewModel::class.java]

        etCuenta = view.findViewById(R.id.etCuenta)
        etImporte = view.findViewById(R.id.etImporte)
        btnDepositar = view.findViewById(R.id.btnDepositar)
        progressBar = view.findViewById(R.id.progressBar)

        btnDepositar.setOnClickListener {
            val cuenta = etCuenta.text.toString().trim()
            val importeStr = etImporte.text.toString().trim()
            val importe = importeStr.toDoubleOrNull() ?: 0.0
            viewModel.realizarDeposito(cuenta, importe)
        }

        viewModel.depositoResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is OperacionResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnDepositar.isEnabled = false
                }
                is OperacionResult.Success -> {
                    progressBar.visibility = View.GONE
                    btnDepositar.isEnabled = true
                    Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
                    etCuenta.text.clear()
                    etImporte.text.clear()
                }
                is OperacionResult.Error -> {
                    progressBar.visibility = View.GONE
                    btnDepositar.isEnabled = true
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
