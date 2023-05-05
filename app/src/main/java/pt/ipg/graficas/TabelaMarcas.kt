package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase

class TabelaMarcas (db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, decricao TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Marcas"
    }
}