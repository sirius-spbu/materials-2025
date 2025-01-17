package com.explyt

import io.ksmt.KContext
import io.ksmt.expr.KInt32NumExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.utils.mkConst

fun main() {
    val ctx = KContext()
    with(ctx) {
        use {
            val solver = KZ3Solver(this)

            val vars = (0 until 8).map { intSort.mkConst("v$it") }

            for (v in vars) {
                val constraint = (0.expr le v) and (v lt 8.expr)
                solver.assert(constraint)
            }

            val allDistinct = mkDistinct(vars)
            solver.assert(allDistinct)

            val diags1 = vars.mapIndexed { idx, v -> idx.expr + v }
            solver.assert(mkDistinct(diags1))

            val diags2 = vars.mapIndexed { idx, v -> idx.expr - v }
            solver.assert(mkDistinct(diags2))




            var status = solver.check()
            var cnt = 0
            while (status == KSolverStatus.SAT) {
                cnt++
                val model = solver.model()

                val conjunct = vars.map { v ->
                    val y = (model.eval(v, isComplete = true) as KInt32NumExpr).value
                    mkEq(v, y.expr)
                }

                solver.assert(mkAnd(conjunct).not())
                status = solver.check()
            }

            println(cnt)
        }
    }
}