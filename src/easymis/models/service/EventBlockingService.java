package easymis.models.service;

import easymis.models.entity.Event;
import easymis.models.entity.enumeration.BookingStatus;
import easymis.models.repository.EventRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author RashidKP
 */
public class EventBlockingService {

    public String getEventBlockedBy(List<Event> events) {
        StringBuilder blockedBy = new StringBuilder();
        Set<String> receiptNumberSet = getBlockedBy(events);
        if (receiptNumberSet != null && !receiptNumberSet.isEmpty()) {
            receiptNumberSet.remove(events.get(0).getBookingDetails().getReceiptNumber());
            receiptNumberSet.stream().forEach(e -> blockedBy.append(e).append(", "));
        }
        return blockedBy.toString();
    }

    private Set<String> getBlockedBy(List<Event> events) {

        Set<String> blockedBy = new HashSet<>();
        if (events != null && !events.isEmpty()) {
            for (Event bookedEvent : events) {
                if (BookingStatus.BLOCKED == bookedEvent.getBookingStatus()) {
                    List<Event> existing = EventRepository.getUniqueInstance().fetchBlockedEventsBytDate(bookedEvent.getEventDate());
                    if (existing != null && !existing.isEmpty()) {
                        for (Event event : existing) {
                                if (event.getEventType() == bookedEvent.getEventType()) {
                                    blockedBy.add(event.getBookingDetails().getReceiptNumber());
                                }
                            

                        }
                    }
                }

            }
        }
        return blockedBy;
    }

}
