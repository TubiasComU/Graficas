package pt.ipg.graficas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Grafica(
    var titulo:String,
    var idMarca: Long,
    var ram: Long,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaGraficas.CAMPO_TITULO, titulo)
        valores.put(TabelaGraficas.CAMPO_RAM, ram)
        valores.put(TabelaGraficas.CAMPO_FK_MARCA, idMarca)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Grafica{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaGraficas.CAMPO_TITULO)
            val posRAM = cursor.getColumnIndex(TabelaGraficas.CAMPO_RAM)
            val posMarcaFK = cursor.getColumnIndex(TabelaGraficas.CAMPO_FK_MARCA)

            val id = cursor.getLong(posId)
            val titulo = cursor.getString(posTitulo)
            val ram = cursor.getLong(posRAM)
            val marcaID = cursor.getLong(posMarcaFK)

            return Grafica(titulo, marcaID, ram, id)
        }
    }
}