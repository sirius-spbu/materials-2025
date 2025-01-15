package com.explyt

import org.kosat.Kosat

fun main() {
    val n = 6
    val edges = listOf(
        1 to 5,
        1 to 4,
        5 to 4,
        5 to 6,
        4 to 6,
        6 to 2,
        6 to 3,
        2 to 4,
        2 to 3,
        1 to 6
    )
    println(edges.size)


    val kosat = Kosat(mutableListOf(), 3 * n)
    // a, b, c
    // 1, 2, 3
    // (a \/ b) /\ (b \/ c) /\ (!a \/ !c)
    with(kosat) {
        // 1 one color
        // 2 only one color
        for (i in 1..n) {
            val c0 = varNumber(i, 0)
            val c1 = varNumber(i, 1)
            val c2 = varNumber(i, 2)
            addClause(c0, c1, c2)
            addClause(-c0, -c1)
            addClause(-c0, -c2)
            addClause(-c1, -c2)
        }


        // 3 edges
        for ((u, v) in edges) {
            addClause(-varNumber(u, 0), -varNumber(v, 0))
            addClause(-varNumber(u, 1), -varNumber(v, 1))
            addClause(-varNumber(u, 2), -varNumber(v, 2))
        }

        val status = solve()
        if (status) {
            println("YES\n")
            val model = getModel()
            val coloring = (0 until n).toMutableList()
            for (i in model) {
                if (i > 0) {
                    val u = (i - 1) / 3 + 1
                    val c = (i - 1) % 3
                    coloring[u - 1] = c
                }
            }
            println(coloring.joinToString())
        } else {
            println("NO\n")
        }
    }
}

fun varNumber(vertex: Int, color: Int) = (vertex - 1) * 3 + color + 1