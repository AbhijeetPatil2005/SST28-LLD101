/*
 * ─── ExportRequest (Value Object / DTO) ───
 *
 * WHY THIS CLASS EXISTS:
 *   Carries the data to be exported (title + body). Immutable public final
 *   fields. All exporters receive the same request type — uniform input.
 */
public class ExportRequest {
    public final String title;
    public final String body;

    public ExportRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}