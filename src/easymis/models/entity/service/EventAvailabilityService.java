package easymis.models.entity.service;

import easymis.models.entity.Event;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.EventRepository;
import easymis.utils.DateHelper;
import easymis.views.dto.EventAvailabilityDTO;
import easymis.views.viewobjects.EventAvailability;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author RashidKP
 */
public class EventAvailabilityService {

    public static Set<EventAvailabilityDTO> checkEventAvailability(Date date) {

        Set<EventAvailabilityDTO> availabilityDTOs = new HashSet<>();
        checkSameDayAvailability(date, availabilityDTOs);
        checkWeddingAndMehandiAvailability(DateHelper.getPreviousDay(date), availabilityDTOs);
        //(availabilityDTOs);
        return availabilityDTOs;
    }

    private static void checkSameDayAvailability(Date date, Set<EventAvailabilityDTO> availabilityDTOs) {
        List<Event> allEventsOnDate = EventRepository.getUniqueInstance().fetchEventByEventDate(date);
        if (allEventsOnDate != null && !allEventsOnDate.isEmpty()) {
            for (Event event : allEventsOnDate) {
                checkAvailability(event, availabilityDTOs);
            }
        }
    }

    private static void includeAvailability(Set<EventAvailabilityDTO> events) {
        List<EventType> availableEvents = new ArrayList<>();
        for (EventType eventType : EventType.values()) {
            for (EventAvailabilityDTO event : events) {
                if (eventType != event.getEventType()) {
                    availableEvents.add(eventType);
                }
            }
        }
        if (!availableEvents.isEmpty()) {
            for (EventType eventType : availableEvents) {
                events.add(new EventAvailabilityDTO(eventType,
                        EventAvailability.AVAILABLE,
                        null));
            }
        }
    }

    private static void checkAvailability(Event event, Set<EventAvailabilityDTO> availabilityDTOs) {

        EventAvailabilityDTO eventAvailabilityDTO = new EventAvailabilityDTO(event.getEventType(),
                EventAvailability.NOT_AVAILABLE,
                event.getEventType().toString() + " already exists on the day");
        availabilityDTOs.add(eventAvailabilityDTO);

        if (null != event.getEventType()) {
            switch (event.getEventType()) {
                case WEDDING: {
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.WEDDING.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaDay = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_DAY,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.WEDDING.toString() + " already exists on the day");
                    availabilityDTOs.add(reception3Pm);
                    availabilityDTOs.add(ishaDay);
                    break;
                }
                case RECEPTION_5_PM: {
                    EventAvailabilityDTO mehandi = new EventAvailabilityDTO(EventType.MEHANDI,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_5_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_5_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaEve = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_EVE,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_5_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaDay = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_DAY,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_5_PM.toString() + " already exists on the day");
                    availabilityDTOs.add(mehandi);
                    availabilityDTOs.add(reception3Pm);
                    //            availabilityDTOs.add(ishaEve);
                    availabilityDTOs.add(ishaDay);
                    break;
                }
                case RECEPTION_3_PM: {
                    EventAvailabilityDTO mehandi = new EventAvailabilityDTO(EventType.MEHANDI,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.MEHANDI.toString() + " already exists on the day");
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_3_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaEve = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_EVE,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_3_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaDay = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_DAY,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_3_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO wedding = new EventAvailabilityDTO(EventType.WEDDING,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_3_PM.toString() + " already exists on the day");
                    availabilityDTOs.add(mehandi);
                    availabilityDTOs.add(reception3Pm);
                    availabilityDTOs.add(ishaEve);
                    availabilityDTOs.add(ishaDay);
                    availabilityDTOs.add(wedding);
                    break;
                }
                case ISHA_HALL_AC_DAY: {
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.RECEPTION_3_PM.toString() + " already exists on the day");
                    EventAvailabilityDTO wedding = new EventAvailabilityDTO(EventType.WEDDING,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.WEDDING.toString() + " already exists on the day");
                    availabilityDTOs.add(wedding);
                    availabilityDTOs.add(reception3Pm);
                    break;
                }
                case ISHA_HALL_AC_EVE:{
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.ISHA_HALL_AC_EVE.toString() + " already exists on the day");
                    EventAvailabilityDTO reception5Pm = new EventAvailabilityDTO(EventType.RECEPTION_5_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.ISHA_HALL_AC_EVE.toString() + " already exists on the day");
                    EventAvailabilityDTO mehandi = new EventAvailabilityDTO(EventType.MEHANDI,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.ISHA_HALL_AC_EVE.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaEve = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_EVE,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.ISHA_HALL_AC_EVE.toString() + " already exists on the day");
                    availabilityDTOs.add(reception3Pm);
                    availabilityDTOs.add(reception5Pm);
                    availabilityDTOs.add(mehandi);
                    availabilityDTOs.add(ishaEve);
                    break;
                }
                case MEHANDI:
                    EventAvailabilityDTO reception3Pm = new EventAvailabilityDTO(EventType.RECEPTION_3_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.MEHANDI.toString() + " already exists on the day");
                    EventAvailabilityDTO reception5Pm = new EventAvailabilityDTO(EventType.RECEPTION_5_PM,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.MEHANDI.toString() + " already exists on the day");
                    EventAvailabilityDTO ishaEve = new EventAvailabilityDTO(EventType.ISHA_HALL_AC_EVE,
                            EventAvailability.NOT_AVAILABLE,
                            EventType.MEHANDI.toString() + " already exists on the day");
                    availabilityDTOs.add(reception3Pm);
                    availabilityDTOs.add(reception5Pm);
                    availabilityDTOs.add(ishaEve);
                default:
                    break;
            }
        }
    }

    private static void checkWeddingAndMehandiAvailability(Date date, Set<EventAvailabilityDTO> availabilityDTOs) {
        List<Event> allEventsOnDate = EventRepository.getUniqueInstance().fetchEventByEventDate(date);
        if (allEventsOnDate != null && !allEventsOnDate.isEmpty()) {
            for (Event event : allEventsOnDate) {
                switch (event.getEventType()) {
                    case RECEPTION_3_PM: 
                    case RECEPTION_5_PM:
                    case ISHA_HALL_AC_EVE:
                        EventAvailabilityDTO mehandi = new EventAvailabilityDTO(EventType.MEHANDI,
                                EventAvailability.NOT_AVAILABLE,
                                EventType.MEHANDI.toString() + " already exists on the day");
                        availabilityDTOs.add(mehandi);
                         EventAvailabilityDTO mehandiAndWedding = new EventAvailabilityDTO(EventType.WEDDING_AND_MEHANDI,
                                EventAvailability.NOT_AVAILABLE,
                                EventType.WEDDING_AND_MEHANDI.toString() + " already exists on the day");
                        availabilityDTOs.add(mehandi);
                        availabilityDTOs.add(mehandiAndWedding);
                        break;
                }
            }
        }
    }
}
