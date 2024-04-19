import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun String.transformarEmIdade(): Int {
    val formater = DateTimeFormatter.ofPattern("dd/mm/yyyy")
    var idade = 0
    val dataNascimento = LocalDate.parse(this, formater)
    val hoje = LocalDate.now()
    idade = Period.between(dataNascimento, hoje).years

    return idade
}