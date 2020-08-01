package net.rodrigoamaral.algorithms.smpso;

import net.rodrigoamaral.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.archive.BoundedArchive;

import java.util.List;

@SuppressWarnings("serial")
public class SMPSO_MA extends SMPSO {
    private static BoundedArchive<DoubleSolution> prevIterationLeaders;

    public SMPSO_MA(DoubleProblem problem, int swarmSize, BoundedArchive<DoubleSolution> leaders,
                    MutationOperator<DoubleSolution> mutationOperator, int maxIterations, double r1Min, double r1Max,
                    double r2Min, double r2Max, double c1Min, double c1Max, double c2Min, double c2Max,
                    double weightMin, double weightMax, double changeVelocity1, double changeVelocity2,
                    SolutionListEvaluator<DoubleSolution> evaluator) {
        super(problem, swarmSize, leaders, mutationOperator, maxIterations, r1Min, r1Max, r2Min, r2Max,
                c1Min, c1Max, c2Min, c2Max, weightMin, weightMax, changeVelocity1, changeVelocity2,
                evaluator);
    }

    @Override
    protected void initializeLeader(List<DoubleSolution> swarm) {
        for (DoubleSolution particle : swarm) {
            getLeaders().add(particle);
        }

        if (mustIncludePrevSolutions()) {
            for (DoubleSolution solution : prevIterationLeaders.getSolutionList()) {
                getLeaders().add(solution);
            }
        }
    }

    @Override
    public void run() {
        List<DoubleSolution> swarm = createInitialSwarm();
        swarm = evaluateSwarm(swarm);
        initializeVelocity(swarm);
        initializeParticlesMemory(swarm);
        initializeLeader(swarm);
        initProgress();

        while (!isStoppingConditionReached()) {
            updateVelocity(swarm);
            updatePosition(swarm);
            perturbation(swarm);
            swarm = evaluateSwarm(swarm);
            updateLeaders(swarm);
            updateParticlesMemory(swarm);
            updateProgress();
        }

        prevIterationLeaders = (BoundedArchive<DoubleSolution>) getLeaders();
    }

    private boolean mustIncludePrevSolutions() {
        return (getProblem().getNumberOfObjectives() > 3)
                && (prevIterationLeaders.get(0).getNumberOfObjectives() > 3);
    }

    @Override
    public String getName() {
        return "SMPSO-MA";
    }

    @Override
    public String getDescription() {
        return "Speed contrained Multiobjective PSO - Memory Approach";
    }
}
