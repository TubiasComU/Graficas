package pt.ipg.graficas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        private val textViewCategoria = contentor.findViewById<TextView>(R.id.textViewMarca)

        internal var grafica: Grafica? = null
            set(value) {
                field = value
                textViewTitulo.text = grafica?.titulo ?: ""
                textViewCategoria.text = grafica?.idMarca.toString() ?: ""
            }

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