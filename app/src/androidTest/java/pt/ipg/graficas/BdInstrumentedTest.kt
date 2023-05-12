package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.manipulation.Ordering.Context

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)

class BdInstrumentedTest {
    private fun getAppContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase{
        val openHelper = BdGraficasOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

    private fun insereMarca(
        bd: SQLiteDatabase,
        marca: Marca
    ) {
        marca.id = TabelaMarcas(bd).insere(marca.toContentValues())
        assertNotEquals(-1, marca.id)
    }

    private fun insereGrafica(
        bd: SQLiteDatabase,
        grafica: Grafica
    ) {
        TabelaGraficas(bd).insere(grafica.toContentValues())
        assertNotEquals(-1, grafica.id)
    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdGraficasOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdGraficasOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
        // Context of the app under test.
        val appContext = getAppContext()
        assertEquals("pt.ipg.graficas", appContext.packageName)
    }

    @Test
    fun consegueInserirMarcas(){
        val bd = getWritableDatabase()

        val marca = Marca("ASUS")

        insereMarca(bd, marca)
    }

    @Test
    fun consegueInserirGraficas(){
        val bd = getWritableDatabase()

        val marca = Marca("GIGABYTE")
        insereMarca(bd, marca)

        val grafica1 = Grafica("RTX 3080",marca.id)
        insereGrafica(bd, grafica1)

        val grafica2 = Grafica("RTX 3090",marca.id)
        insereGrafica(bd, grafica2)
    }

    @Test
    fun consegueLerMarcas(){
        val bd = getWritableDatabase()

        val marcaMSI = Marca("MSI")
        insereMarca(bd,marcaMSI)

        val marcaTUF = Marca("TUF")
        insereMarca(bd,marcaTUF)

        val tabelaMarcas = TabelaMarcas(bd)

        val cursor = tabelaMarcas.consulta(
            TabelaMarcas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(marcaMSI.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val marcaBD = Marca.fromCursor(cursor)

        assertEquals(marcaMSI, marcaBD)

        val cursorTodasMarcas = tabelaMarcas.consulta(
            TabelaMarcas.CAMPOS,
            null,null,null,null,
            TabelaMarcas.CAMPO_DESCRICAO
        )

        assert(cursorTodasMarcas.count > 1)
    }

}