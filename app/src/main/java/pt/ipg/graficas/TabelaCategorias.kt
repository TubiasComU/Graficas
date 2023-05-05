package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

private const val NOME_TABELA = "categorias"

class TabelaCategorias (db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, decricao TEXT NOT NULL)")
    }
}