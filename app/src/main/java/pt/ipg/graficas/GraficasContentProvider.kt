package pt.ipg.graficas

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class GraficasContentProvider: ContentProvider() {
    private var bdOpenHelper : BdGraficasOpenHelper? = null


    override fun onCreate(): Boolean {
        bdOpenHelper = BdGraficasOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase
        val id = uri.lastPathSegment

        val endereco = uriMatcher().match(uri)


        val tabela = when (endereco){
           URI_MARCAS -> TabelaMarcas(bd)
           URI_MARCA_ID -> TabelaMarcas(bd)
           URI_GRAFICAS -> TabelaGraficas(bd)
           URI_GRAFICA_ID-> TabelaGraficas(bd)
           else -> null
        }

        val (selecao, argsSel) = when (endereco){
            URI_MARCA_ID, URI_GRAFICA_ID-> Pair ("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection, selectionArgs)
        }


    return tabela?.consulta(
        projection as Array<String>,
        selecao,
        selectionArgs as Array<String>,
        null,
        null,
        sortOrder)
    }


    companion object{
        private const val AUTORIDADE = "pt.ipg.graficas"

        const val MARCAS = "marcas"
        const val GRAFICAS = "graficas"

        private const val URI_MARCAS = 100
        private const val URI_MARCA_ID = 101
        private const val URI_GRAFICAS = 200
        private const val URI_GRAFICA_ID = 201

        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, MARCAS, URI_MARCAS)
            addURI(AUTORIDADE, "$MARCAS/#", URI_MARCA_ID)
            addURI(AUTORIDADE, GRAFICAS, URI_GRAFICAS)
            addURI(AUTORIDADE, "$GRAFICAS/#", URI_GRAFICA_ID)
        }

        //content://pt.ipg.graficas/marcas -> 100
        //content://pt.ipg.graficas/marcas/5 -> 100
        //content://pt.ipg.graficas/marcas/243 -> 100
        //content://pt.ipg.graficas/categorias -> 100

    }


    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco){
            URI_MARCAS -> TabelaMarcas(bd)
            URI_GRAFICAS -> TabelaGraficas(bd)
            else -> return null
        }

        val id = tabela.insere(values!!)
        if (id == -1L){
            return null
        }

        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, p3: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco){
            URI_MARCA_ID -> TabelaMarcas(bd)
            URI_GRAFICA_ID -> TabelaGraficas(bd)
            else -> return 0
        }


        val id = uri.lastPathSegment!!
        return tabela.altera(values!!, "${BaseColumns._ID}=?", arrayOf(id))

    }


}