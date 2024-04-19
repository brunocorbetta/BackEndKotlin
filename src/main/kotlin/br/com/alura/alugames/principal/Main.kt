package org.example.br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Gamer
import br.com.alura.alugames.servicos.ConsumoApi
import org.example.br.com.alura.alugames.modelo.Jogo
import transformarEmIdade
import java.util.*

fun main() {
    val leitura = Scanner(System.`in`)
    val gamer = Gamer.criarGamer(leitura)
    println("cadastro comcluido, Seus dados:")
    println(gamer)
    println("idade do Gamer: " + gamer.dataNascimento?.transformarEmIdade())

    do {
        println("digite o numero do jogo aqui: ")
        val busca = leitura.nextLine()

        val buscaApi = ConsumoApi()

        val informacaoJogo = buscaApi.buscaJogo(busca)

        var meuJogo: Jogo? = null

        val resultado = runCatching {
            meuJogo = Jogo(
                informacaoJogo.info.title,
                informacaoJogo.info.thumb
            )
        }

        resultado.onFailure {
            println("Jogo não existente, tente outro jogo")
        }

        resultado.onSuccess {
            println("Deseja inserir uma descricao personalizada? digite s para sim e n para não")
            val opcao = leitura.nextLine()
            if (opcao.equals("s", ignoreCase = true)) {
                println("insira a descrição:")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            } else {
                meuJogo?.descricao = meuJogo?.titulo
            }
            gamer.jogosBuscados.add(meuJogo)
        }

        println("Deseja buscar outro jogo? S/N")
        val resposta = leitura.nextLine()
    } while (resposta.equals("s", ignoreCase = true))

    println("Jogos Buscados")
    println(gamer.jogosBuscados)

    println("Jogos Buscados:")

    gamer.jogosBuscados.sortBy {
        it?.titulo
    }

    gamer.jogosBuscados.forEach {
        println("Titulo:" + it?.titulo )
    }

    val jogosFiltrados = gamer.jogosBuscados.filter {
        it?.titulo?.contains("batman", ignoreCase = true) ?: false
    }

    println("\n Jogos Filtrados!")
    println(jogosFiltrados)

    println("Deseja excluir algum item da lista original? s/n")
    val opcao = leitura.nextLine()

    if (opcao.equals("s", ignoreCase = true)) {
        println("Qual numero da posição")
        val posicao = leitura.nextInt()
        gamer.jogosBuscados.removeAt(posicao)
    }

    println("Busca finalizada!")
}