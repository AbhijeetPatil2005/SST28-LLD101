import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demo that shows immutability in action.
 *
 * - service updates return NEW instances (original unchanged)
 * - tags list is unmodifiable (external mutation throws
 * UnsupportedOperationException)
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // Each service call returns a NEW object; the old reference is untouched
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nOriginal ticket (unchanged): " + t);
        System.out.println("After assign + escalate (new instance): " + escalated);

        // Attempting to mutate the tags list from outside is blocked
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nERROR: mutation was allowed!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked — tags are immutable.");
        }

        System.out.println("\nFinal escalated ticket: " + escalated);
    }
}