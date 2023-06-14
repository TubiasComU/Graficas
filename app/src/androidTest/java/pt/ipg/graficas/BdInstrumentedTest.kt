package pt.ipg.graficas

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

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
        grafica.id = TabelaGraficas(bd).insere(grafica.toContentValues())
        assertNotEquals(-1, grafica.id)
    }

    @Before
    fun apagaBaseDados(){
        //getAppContext().deleteDatabase(BdGraficasOpenHelper.NOME_BASE_DADOS)
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

        val grafica1 = Grafica("RTX 3080",marca, "16GB")
        insereGrafica(bd, grafica1)

        val grafica2 = Grafica("RTX 3090",marca,"24GB")
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

    @Test
    fun consegueAlterarMarcas(){
        val bd = getWritableDatabase()

        val marca = Marca("...")
        insereMarca(bd,marca)

        marca.descricao = "MSI"

        val registosAlterados = TabelaMarcas(bd).altera(
            marca.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marca.id.toString()),
        )
        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarMarcas(){
        val bd = getWritableDatabase()

        val marca = Marca("MSI")
        insereMarca(bd,marca)

        val registosEliminados = TabelaMarcas(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(marca.id.toString()),
        )
        assertEquals(1, registosEliminados)
    }

    @Test
    fun  consegueLerGrafica(){
        val bd= getWritableDatabase()

        val marcaMSI = Marca("MSI")
        insereMarca(bd,marcaMSI)

        val grafica1=Grafica("RTX 3090", marcaMSI,"16GB")
        insereGrafica(bd,grafica1)

        val marcaTUF = Marca("TUF")
        insereMarca(bd,marcaTUF)

        val grafica2=Grafica("RTX 4080", marcaTUF, "24GB")
        insereGrafica(bd,grafica2)

        val tabelaGrafica = TabelaGraficas(bd)
        val cursor: Cursor= tabelaGrafica.consulta(
            TabelaGraficas.CAMPOS,
            "${TabelaGraficas.CAMPO_ID}=?",
            arrayOf(grafica1.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val graficaBD=Grafica.fromCursor(cursor)

        assertEquals(grafica1,graficaBD)

        val cursorTodasGraficas=tabelaGrafica.consulta(
            TabelaGraficas.CAMPOS,
            null,
            null,
            null,
            null,
            TabelaGraficas.CAMPO_TITULO
        )

        assert(cursorTodasGraficas.count>1)
    }

    @Test
    fun consegueAlterarGrafica(){
        val bd = getWritableDatabase()

        val marcaMSI = Marca("MSI")
        insereMarca(bd,marcaMSI)

        val marcaTUF = Marca("MSI")
        insereMarca(bd,marcaTUF)

        val grafica=Grafica("...", marcaMSI,"16GB")
        insereGrafica(bd,grafica)

        grafica.marca = marcaMSI
        grafica.titulo = "RTX 3090"
        grafica.ram = "16GB"

        val registosAlterados = TabelaGraficas(bd).altera(
            grafica.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(grafica.id.toString()),
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarGraficas(){
        val bd = getWritableDatabase()

        val marca = Marca("MSI")
        insereMarca(bd,marca)

        val grafica=Grafica("...", marca,"16GB")
        insereGrafica(bd,grafica)

        val registosEliminados = TabelaGraficas(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(grafica.id.toString()),
        )

        assertEquals(1, registosEliminados)
    }

}