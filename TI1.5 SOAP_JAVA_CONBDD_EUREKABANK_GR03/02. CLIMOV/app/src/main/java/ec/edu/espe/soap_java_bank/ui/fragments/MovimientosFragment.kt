package ec.edu.espe.soap_java_bank.ui.fragments

import android.content.Intent
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
import ec.edu.espe.soap_java_bank.ui.activity.MovimientosTablaActivity
import ec.edu.espe.soap_java_bank.ui.viewmodel.MovimientosViewModel

class MovimientosFragment : Fragment() {

    private lateinit var viewModel: MovimientosViewModel
    private lateinit var etCuenta: EditText
    private lateinit var btnVerMovimientos: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movimientos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovimientosViewModel::class.java]

        etCuenta = view.findViewById(R.id.etCuenta)
        btnVerMovimientos = view.findViewById(R.id.btnVerMovimientos)
        progressBar = view.findViewById(R.id.progressBar)

        btnVerMovimientos.setOnClickListener {
            val cuenta = etCuenta.text.toString().trim()
            if (cuenta.isBlank()) {
                Toast.makeText(requireContext(), "Debe ingresar un número de cuenta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(requireContext(), MovimientosTablaActivity::class.java)
            intent.putExtra("CUENTA", cuenta)
            startActivity(intent)
        }
    }
}
