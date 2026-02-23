public class DisciplinaryFlagRule implements EligibilityRule {
    @Override
    public RuleCheckResult check(StudentProfile student) {
        if (student.disciplinaryFlag != LegacyFlags.NONE) {
            return new RuleCheckResult(false, "disciplinary flag present");
        }
        return new RuleCheckResult(true, null);
    }
}
