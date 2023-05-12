package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarcas (db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_DESCRICAO TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Marcas"
        const val CAMPO_DESCRICAO = "Descricao"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_DESCRICAO)

    }
}