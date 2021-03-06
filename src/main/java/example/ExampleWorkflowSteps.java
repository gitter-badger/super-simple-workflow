package example;

import com.bazaarvoice.sswf.ConstantInProgressTimerFunction;
import com.bazaarvoice.sswf.InProgressTimerFunction;
import com.bazaarvoice.sswf.WorkflowStep;

enum ExampleWorkflowSteps implements WorkflowStep {
    EXTRACT_STEP(120, 120) {
        @Override public InProgressTimerFunction inProgressTimerSecondsFn() {
            return (invocationNum, cumulativeStepDurationSeconds) -> Math.min(10 * invocationNum, 100);
        }
    },
    TRANSFORM_STEP(120, 120),
    LOAD_STEP(120, 120),
    TIMEOUT_ONCE_STEP(120, 10)
    ;

    private int startToFinishTimeout;
    private int startToHeartbeatTimeoutSeconds;

    ExampleWorkflowSteps(final int startToFinishTimeout, final int startToHeartbeatTimeoutSeconds) {
        this.startToFinishTimeout = startToFinishTimeout;
        this.startToHeartbeatTimeoutSeconds = startToHeartbeatTimeoutSeconds;
    }

    @Override public int startToFinishTimeoutSeconds() {
        return startToFinishTimeout;
    }

    @Override public int startToHeartbeatTimeoutSeconds() { return startToHeartbeatTimeoutSeconds; }

    @Override public InProgressTimerFunction inProgressTimerSecondsFn() {
        return new ConstantInProgressTimerFunction(10);
    }

}
