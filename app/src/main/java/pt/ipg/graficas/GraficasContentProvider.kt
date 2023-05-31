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

        private const val MARCAS = "marcas"
        private const val GRAFICAS = "graficas"

        private const val URI_MARCAS = 100
        private const val URI_MARCA_ID = 101
        private const val URI_GRAFICAS = 200
        private const val URI_GRAFICA_ID = 201

        private val ENDERECO_BASE = Uri.parse("content://$AUTORIDADE")

        val ENDERECO_MARCAS = Uri.withAppendedPath(ENDERECO_BASE, MARCAS)
        val ENDERECO_GRAFICAS = Uri.withAppendedPath(ENDERECO_BASE, GRAFICAS)

        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, MARCAS, URI_MARCAS)
            addURI(AUTORIDADE, "$MARCAS/#", URI_MARCA_ID)
            addURI(AUTORIDADE, GRAFICAS, URI_GRAFICAS)
            addURI(AUTORIDADE, "$GRAFICAS/#", URI_GRAFICA_ID)
        }

    }


    override fun getType(uri: Uri): String? {
        val endereco = uriMatcher().match(uri)

        return when(endereco){
            URI_MARCAS -> "vnd.android.cursor.dir/$MARCAS"
            URI_MARCA_ID -> "vnd.android.cursor.item/$MARCAS"
            URI_GRAFICAS -> "vnd.android.cursor.dir/$GRAFICAS"
            URI_GRAFICA_ID-> "vnd.android.cursor.item$GRAFICAS"
            else -> null
        }
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


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco){
            URI_MARCA_ID -> TabelaMarcas(bd)
            URI_GRAFICA_ID -> TabelaGraficas(bd)
            else -> return 0
        }


        val id = uri.lastPathSegment!!
        return tabela.elimina( "${BaseColumns._ID}=?", arrayOf(id))
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
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