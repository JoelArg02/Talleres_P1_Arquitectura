package ec.edu.espe.soap_java_bank.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.soap_java_bank.R
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.ui.adapter.MovimientosAdapter
import ec.edu.espe.soap_java_bank.ui.viewmodel.MovimientosViewModel

class MovimientosTablaActivity : AppCompatActivity() {

    private lateinit var viewModel: MovimientosViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovimientosAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var btnCerrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos_tabla)

        viewModel = ViewModelProvider(this)[MovimientosViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerMovimientos)
        progressBar = findViewById(R.id.progressBar)
        btnCerrar = findViewById(R.id.btnCerrar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovimientosAdapter()
        recyclerView.adapter = adapter

        btnCerrar.setOnClickListener {
            finish()
        }

        val cuenta = intent.getStringExtra("CUENTA") ?: ""

        viewModel.movimientos.observe(this) { result ->
            when (result) {
                is OperacionResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is OperacionResult.Success -> {
                    progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                }
                is OperacionResult.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }

        viewModel.obtenerMovimientos(cuenta)
    }
}
