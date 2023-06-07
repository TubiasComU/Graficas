package pt.ipg.graficas

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.graficas.databinding.FragmentListaGraficasBinding

private const val ID_LOADER_GRAFICAS = 0

/**
 * A simple [Fragment] subclass.
 * Use the [ListaGraficasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaGraficasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListaGraficasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var graficaSelecionada : Grafica? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaGraficasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterGraficas = AdapterGraficas(this)
        binding.recyclerViewGraficas.adapter = adapterGraficas
        binding.recyclerViewGraficas.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_GRAFICAS, null, this)

        val activity = activity as MainActivity
        activity.idMenuAtual = R.menu.menu_lista_graficas
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaGraficasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaGraficasFragment().apply {

            }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            GraficasContentProvider.ENDERECO_GRAFICAS,
            TabelaGraficas.CAMPOS,
            null, null,
            TabelaGraficas.CAMPO_TITULO
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterGraficas!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterGraficas!!.cursor = data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private var adapterGraficas: AdapterGraficas? = null
}