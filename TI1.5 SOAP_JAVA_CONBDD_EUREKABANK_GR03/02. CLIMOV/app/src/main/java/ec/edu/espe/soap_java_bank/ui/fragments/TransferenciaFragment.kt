package ec.edu.espe.soap_java_bank.ui.fragments

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
import ec.edu.espe.soap_java_bank.R
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.ui.viewmodel.TransferenciaViewModel

class TransferenciaFragment : Fragment() {

    private lateinit var viewModel: TransferenciaViewModel
    private lateinit var etCuentaOrigen: EditText
    private lateinit var etCuentaDestino: EditText
    private lateinit var etImporte: EditText
    private lateinit var btnTransferir: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transferencia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TransferenciaViewModel::class.java]

        etCuentaOrigen = view.findViewById(R.id.etCuentaOrigen)
        etCuentaDestino = view.findViewById(R.id.etCuentaDestino)
        etImporte = view.findViewById(R.id.etImporte)
        btnTransferir = view.findViewById(R.id.btnTransferir)
        progressBar = view.findViewById(R.id.progressBar)

        btnTransferir.setOnClickListener {
            val cuentaOrigen = etCuentaOrigen.text.toString().trim()
            val cuentaDestino = etCuentaDestino.text.toString().trim()
            val importeStr = etImporte.text.toString().trim()
            val importe = importeStr.toDoubleOrNull() ?: 0.0
            viewModel.realizarTransferencia(cuentaOrigen, cuentaDestino, importe)
        }

        viewModel.transferenciaResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is OperacionResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnTransferir.isEnabled = false
                }
                is OperacionResult.Success -> {
                    progressBar.visibility = View.GONE
                    btnTransferir.isEnabled = true
                    Toast.makeText(requireContext(), "Transferencia realizada con éxito", Toast.LENGTH_SHORT).show()
                    etCuentaOrigen.text.clear()
                    etCuentaDestino.text.clear()
                    etImporte.text.clear()
                }
                is OperacionResult.Error -> {
                    progressBar.visibility = View.GONE
                    btnTransferir.isEnabled = true
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
