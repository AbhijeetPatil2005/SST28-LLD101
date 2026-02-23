public interface EligibilityRule {
    RuleCheckResult check(StudentProfile student);
}

class RuleCheckResult {
    public final boolean passed;
    public final String reason;

    public RuleCheckResult(boolean passed, String reason) {
        this.passed = passed;
        this.reason = reason;
    }
}
