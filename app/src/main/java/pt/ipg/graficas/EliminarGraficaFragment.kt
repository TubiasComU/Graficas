package pt.ipg.graficas

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.graficas.databinding.FragmentEliminarGraficaBinding

class EliminarGraficaFragment : Fragment() {
    private lateinit var grafica: Grafica
    private var _binding: FragmentEliminarGraficaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarGraficaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        grafica = EliminarGraficaFragmentArgs.fromBundle(requireArguments()).grafica

        binding.textViewTitulo.text = grafica.titulo
        binding.textViewRAM.text = grafica.ram
        binding.textViewMarca.text = grafica.marca.descricao
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaGraficas()
                true
            }
            else -> false
        }
    }

    private fun voltaListaGraficas() {
        findNavController().navigate(R.id.action_eliminarGraficaFragment_to_ListaGraficasFragment)
    }

    private fun eliminar() {
    }
}