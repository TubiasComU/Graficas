package pt.ipg.graficas

import android.database.sqlite.SQLiteDatabase
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
        val marca.id = TabelaMarcas(bd).insere(marca.toContentValues())
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

        val marca = Marca("MSI")
        insereMarca(bd, marca)

        val grafica1 = Grafica("RTX 3080",marca.id)
        insereGrafica(bd, grafica1)

        val grafica2 = Grafica("RTX 3090",marca.id)
        insereGrafica(bd, grafica2)
    }



}