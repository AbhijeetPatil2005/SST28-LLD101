import java.nio.charset.StandardCharsets;

public class JsonExporter extends Exporter {
    @Override
    public boolean canExport(ExportRequest req) {
        return req != null;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!canExport(req)) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        String title = req.title == null ? "" : req.title;
        String body = req.body == null ? "" : req.body;
        String json = "{\"title\":\"" + escape(title) + "\",\"body\":\"" + escape(body) + "\"}";
        return new ExportResult("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"").replace("\n", "\\n");
    }
}
