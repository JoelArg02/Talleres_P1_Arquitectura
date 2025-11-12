package ec.edu.espe.rest_dotnet_bank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.rest_dotnet_bank.R
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.ui.adapter.BalancesAdapter
import ec.edu.espe.rest_dotnet_bank.ui.viewmodel.BalancesViewModel

class BalancesFragment : Fragment() {

    private lateinit var viewModel: BalancesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BalancesAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balances, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BalancesViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerBalances)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BalancesAdapter()
        recyclerView.adapter = adapter

        viewModel.balances.observe(viewLifecycleOwner) { result ->
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
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Obtener todas las cuentas del sistema
        viewModel.obtenerBalances()
    }
}
