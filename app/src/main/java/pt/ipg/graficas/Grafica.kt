package pt.ipg.graficas

import android.content.ContentValues

data class Grafica(
    var titulo:String,
    var idMarca: Long,
    var isbn: String? = null,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaGraficas.CAMPO_TITULO, titulo)
        valores.put(TabelaGraficas.CAMPO_ISBN, isbn)
        valores.put(TabelaGraficas.CAMPO_FK_MARCA, idMarca)

        return valores
    }
}