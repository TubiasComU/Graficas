package pt.ipg.graficas

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
        val openHelper = BdGraficasOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase

        val marca = Marca("ASUS")

        //TabelaMarcas(bd).insere()
    }

}