package pt.ipg.graficas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Marca(
    var descricao: String,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaMarcas.CAMPO_DESCRICAO, descricao)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Marca{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDescricao = cursor.getColumnIndex(TabelaMarcas.CAMPO_DESCRICAO)

            val id = cursor.getLong(posId)
            val descricao = cursor.getString(posDescricao)

            return Marca(descricao, id)
        }
    }

}