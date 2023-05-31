package pt.ipg.graficas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterGraficas: RecyclerView.Adapter<AdapterGraficas.ViewHolderGrafica>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ViewHolderGrafica(itemView: View) : ViewHolder(itemView){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGrafica {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderGrafica, position: Int) {
        TODO("Not yet implemented")
    }
}