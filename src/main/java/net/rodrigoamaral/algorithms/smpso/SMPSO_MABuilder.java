package net.rodrigoamaral.algorithms.smpso;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.archive.BoundedArchive;

public class SMPSO_MABuilder extends SMPSOBuilder {
    public SMPSO_MABuilder(DoubleProblem problem, BoundedArchive<DoubleSolution> leaders) {
        super(problem, leaders);
    }

    public SMPSO build() {
        return new SMPSO_MA(getProblem(), getSwarmSize(), leaders, getMutationOperator(), getMaxIterations(), getR1Min(),
                getR1Max(), getR2Min(), getR2Max(), getC1Min(), getC1Max(), getC2Min(), getC2Max(), getWeightMin(),
                getWeightMax(), getChangeVelocity1(), getChangeVelocity2(), getEvaluator());
    }
}
