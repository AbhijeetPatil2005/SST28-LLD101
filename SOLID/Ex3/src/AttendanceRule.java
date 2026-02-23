public class AttendanceRule implements EligibilityRule {
    private final int minAttendance;

    public AttendanceRule(int minAttendance) {
        this.minAttendance = minAttendance;
    }

    @Override
    public RuleCheckResult check(StudentProfile student) {
        if (student.attendancePct < minAttendance) {
            return new RuleCheckResult(false, "attendance below " + minAttendance);
        }
        return new RuleCheckResult(true, null);
    }
}
