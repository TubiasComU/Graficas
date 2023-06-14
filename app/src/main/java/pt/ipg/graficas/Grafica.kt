package pt.ipg.graficas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Grafica(
    var titulo:String,
    var marca: Marca,
    var ram: String,
    var id: Long = -1
) : Serializable {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaGraficas.CAMPO_TITULO, titulo)
        valores.put(TabelaGraficas.CAMPO_RAM, ram)
        valores.put(TabelaGraficas.CAMPO_FK_MARCA, marca.id)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Grafica{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaGraficas.CAMPO_TITULO)
            val posRAM = cursor.getColumnIndex(TabelaGraficas.CAMPO_RAM)
            val posMarcaFK = cursor.getColumnIndex(TabelaGraficas.CAMPO_FK_MARCA)
            val posDescMarca = cursor.getColumnIndex(TabelaGraficas.CAMPO_DESC_MARCA)


            val id = cursor.getLong(posId)
            val titulo = cursor.getString(posTitulo)
            val ram = cursor.getString(posRAM)


            val marcaID = cursor.getLong(posMarcaFK)
            val descricaoMarca = cursor.getString(posDescMarca)

            return Grafica(titulo, Marca(descricaoMarca, marcaID), ram, id)
        }
    }
}