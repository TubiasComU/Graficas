package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaGraficas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA){

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, titulo TEXT NOT NULL, isbn TEXT, id_marcas INTEGER NOT NULL), FOREIGN KEY (id_marcas) REFERENCES ${TabelaMarcas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT")
    }

    companion object{
        private const val NOME_TABELA = "Graficas"
    }
}

