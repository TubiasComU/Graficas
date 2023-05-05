package pt.ipg.graficas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val NOME_BASE_DADOS = "Graficas.db"

private const val VERSAO_BASE_DADOS = 1

class BdGraficasOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 == VERSAO_BASE_DADOS){

        }

        if (p2 < 3){

        }

    }

}