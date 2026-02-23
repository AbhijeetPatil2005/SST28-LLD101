import java.util.*;

public class CafeteriaSystem implements MenuLookup{
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private int invoiceSeq = 1000;
    private final TaxPolicy taxPolicy = new DefaultTaxPolicy();
    private final DiscountPolicy discountPolicy= new DefaultDiscountPolicy();
    private final InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
    private final InvoiceStore store= new FileStore();

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // Intentionally SRP-violating: menu mgmt + tax + discount + format + persistence.
    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            subtotal += item.price * l.qty;
        }
        double taxPct = taxPolicy.taxPercent(customerType);
        double discount = discountPolicy.discountAmount(customerType, subtotal, lines.size());


        String invoiceText = invoiceBuilder.buildInvoice(invId, lines, this, taxPct, discount);
        String printable = InvoiceFormatter.identityFormat(invoiceText);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }

    @Override
    public MenuItem find(String itemId) {
        return menu.get(itemId);
    }
}
