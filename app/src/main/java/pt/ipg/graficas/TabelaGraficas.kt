package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaGraficas(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA){

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_ISBN TEXT, $CAMPO_FK_MARCA INTEGER NOT NULL, FOREIGN KEY (id_marcas) REFERENCES ${TabelaMarcas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME_TABELA = "Graficas"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_ISBN = "isbn"
        const val CAMPO_FK_MARCA = "id_marca"
    }
}

