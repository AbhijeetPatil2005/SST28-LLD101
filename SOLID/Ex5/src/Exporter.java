/**
 * Base contract for all exporters.
 * Preconditions: ExportRequest must not be null.
 * Postconditions: Returns ExportResult with bytes representing the exported data.
 * 
 * Subclasses must honor this contract consistently.
 * If an exporter has limitations, it must declare them via canExport() method.
 */
public abstract class Exporter {
    public abstract ExportResult export(ExportRequest req);
    
    /**
     * Returns true if this exporter can handle the given request,
     * false if it would fail or produce invalid output.
     */
    public abstract boolean canExport(ExportRequest req);
}
