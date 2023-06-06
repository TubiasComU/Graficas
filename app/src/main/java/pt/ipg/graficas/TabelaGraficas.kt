package pt.ipg.graficas

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaGraficas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA){

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_RAM INTEGER NOT NULL, $CAMPO_FK_MARCA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_MARCA) REFERENCES ${TabelaMarcas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaMarcas.NOME_TABELA} ON ${TabelaMarcas.CAMPO_ID}=$CAMPO_FK_MARCA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }



    companion object{
        const val NOME_TABELA = "Graficas"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_RAM = "ram"
        const val CAMPO_FK_MARCA = "id_marca"
        const val CAMPO_DESC_MARCA = TabelaMarcas.CAMPO_DESCRICAO

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_TITULO,  CAMPO_RAM, CAMPO_FK_MARCA)
    }
}

