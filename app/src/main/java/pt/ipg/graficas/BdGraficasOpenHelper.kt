package pt.ipg.graficas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val VERSAO_BASE_DADOS = 1

class BdGraficasOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {

    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaMarcas(db).cria()
        TabelaGraficas(db).cria()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 == VERSAO_BASE_DADOS){

        }

        if (p2 < 3){

        }

    }
    companion object{
        val NOME_BASE_DADOS = "Graficas.db"
    }


}