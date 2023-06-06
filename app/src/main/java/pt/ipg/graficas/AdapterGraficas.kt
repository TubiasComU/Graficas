package pt.ipg.graficas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterGraficas(val fragment: ListaGraficasFragment): RecyclerView.Adapter<AdapterGraficas.ViewHolderGrafica>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ViewHolderGrafica(contentor: View) : ViewHolder(contentor){
        private val textViewTitulo = contentor.findViewById<TextView>(R.id.textViewTitulo)
        private val textViewMarca = contentor.findViewById<TextView>(R.id.textViewMarca)

        init {
            contentor.setOnClickListener {
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        internal var grafica: Grafica? = null
            set(value) {
                field = value
                textViewTitulo.text = grafica?.titulo ?: ""
                textViewMarca.text = grafica?.marca?.descricao ?: ""
            }

        fun seleciona() {
            viewHolderSeleccionado = this
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white)
        }

        private var viewHolderSeleccionado : ViewHolderGrafica? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGrafica {
        return ViewHolderGrafica(
            fragment.layoutInflater.inflate(R.layout.item_grafica, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderGrafica, position: Int) {
        cursor!!.moveToPosition(position)
        holder.grafica = Grafica.fromCursor(cursor!!)
    }
}