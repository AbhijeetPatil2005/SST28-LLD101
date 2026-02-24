public abstract class Exporter {
    public abstract ExportResult export(ExportRequest req);
    public abstract boolean canExport(ExportRequest req);
}
