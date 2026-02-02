package orgsarava338.sadhanakaala.domain.session;

public enum SessionOutcome {

    COMPLETED, // ≥ completion threshold
    PARTIAL, // showed up but didn’t finish
    INTERRUPTED, // explicitly stopped
    INVALID // too short to count
}
