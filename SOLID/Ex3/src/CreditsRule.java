public class CreditsRule implements EligibilityRule {
    private final int minCredits;

    public CreditsRule(int minCredits) {
        this.minCredits = minCredits;
    }

    @Override
    public RuleCheckResult check(StudentProfile student) {
        if (student.earnedCredits < minCredits) {
            return new RuleCheckResult(false, "credits below " + minCredits);
        }
        return new RuleCheckResult(true, null);
    }
}
