package pt.ipg.graficas

data class Grafica(
    var titulo:String,
    var idMarca: Int,
    var isbn: String? = null,
    var id: Long = -1
) {
}