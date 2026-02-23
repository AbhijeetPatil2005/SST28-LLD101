import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final RoomPriceRegistry roomPrices;
    private final AddOnPriceRegistry addOnPrices;

    public HostelFeeCalculator(FakeBookingRepo repo) {
        this.repo = repo;
        this.roomPrices = new RoomPriceRegistry();
        this.addOnPrices = new AddOnPriceRegistry();
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        double base = roomPrices.getMonthlyRate(req.roomType);
        double add = 0.0;
        for (AddOn a : req.addOns) {
            add += addOnPrices.getPriceFor(a);
        }
        return new Money(base + add);
    }
}
