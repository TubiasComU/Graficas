package pt.ipg.graficas

import android.content.ContentValues

data class Marca(
    var id: Long = -1,
    var descricao: String
) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaMarcas.CAMPO_DESCRICAO, descricao)

        return valores
    }

}