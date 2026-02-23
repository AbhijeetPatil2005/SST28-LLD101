public class CgrRule implements EligibilityRule {
    private final double minCgr;

    public CgrRule(double minCgr) {
        this.minCgr = minCgr;
    }

    @Override
    public RuleCheckResult check(StudentProfile student) {
        if (student.cgr < minCgr) {
            return new RuleCheckResult(false, "CGR below " + minCgr);
        }
        return new RuleCheckResult(true, null);
    }
}
