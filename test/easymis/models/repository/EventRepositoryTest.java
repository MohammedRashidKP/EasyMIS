package easymis.models.repository;

import easymis.models.entity.EventDetails;
import easymis.models.entity.enumeration.BookingStatus;
import easymis.utils.DateHelper;
import easymis.utils.IDateUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RashidKP
 */
public class EventRepositoryTest {
    public EventRepositoryTest() {
        try {
            if(properties == null){
            InputStream propertiesFileStream = new FileInputStream("C:\\Users\\RashidKP\\Documents\\NetBeansProjects\\EasyMIS\\test\\easymis\\models\\repository\\EventRepositoryTest.properties");
            properties = new Properties();
            properties.load(propertiesFileStream);
            }
        }catch (FileNotFoundException ex) {
            Logger.getLogger(EventRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
                Logger.getLogger(EventRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Properties properties;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        IDateUtils mockDate = new MockDateUtils();
        DateHelper.load(mockDate);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUniqueInstance method, of class EventRepository.
     */
    @Test
    public void testGetUniqueInstance() {
        System.out.println("getUniqueInstance");
        EventRepository expResult = null;
        EventRepository result = EventRepository.getUniqueInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class EventRepository.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        LocalDate date = LocalDate.now();
        for(int i=0; i< 15000; i++){
            EventDetails eventDetails = getEventDetails();
            date.plusDays(1);
            eventDetails.setBookingStatus(BookingStatus.BOOKED);
            eventDetails.setEventDate(java.sql.Date.valueOf(date));
            eventDetails.setCreatedDate(java.sql.Date.valueOf(date));
            EventRepository instance = EventRepository.getUniqueInstance();
            instance.create(eventDetails);
        
    }
    }

    /**
     * Test of fetchAllEvents method, of class EventRepository.
     */
    @Test
    public void testFetchAllEvents() {
        System.out.println("fetchAllEvents");
        EventRepository instance = null;
        List<EventDetails> expResult = null;
        List<EventDetails> result = instance.fetchAllEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fetchByEventDate method, of class EventRepository.
     */
    @Test
    public void testFetchEventsOnDate() {
        System.out.println("fetchEventsOnDate");
        Date eventDate = null;
        EventRepository instance = null;
        List<EventDetails> expResult = null;
        List<EventDetails> result = instance.fetchByEventDate(eventDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fetchExistingMehandiEventOnDate method, of class EventRepository.
     */
    @Test
    public void testFetchExistingMehandiEventOnDate() {
        System.out.println("fetchExistingMehandiEventOnDate");
        Date eventDateDate = null;
        EventRepository instance = null;
        EventDetails expResult = null;
        EventDetails result = instance.fetchExistingMehandiEventOnDate(eventDateDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private EventDetails getEventDetails() {
        EventDetails eventDetails = new EventDetails();
        eventDetails.setEventDate(getEventDate());
        eventDetails.setFirstName(properties.getProperty("firstName"));
        eventDetails.setAddressLine1(properties.getProperty("addressLine1"));
        eventDetails.setPrimaryMobile(properties.getProperty("mobileNumber1"));
        eventDetails.setWeddingSelected(Boolean.valueOf(properties.getProperty("weddingSelected")));
        eventDetails.setMehandiSelected(Boolean.valueOf(properties.getProperty("mehandiSelected")));
        eventDetails.setReceptionSelected(Boolean.valueOf(properties.getProperty("receptionSelected")));
        eventDetails.setIshaSelected(Boolean.valueOf(properties.getProperty("ishaSelected")));
        eventDetails.setNicaSelected(Boolean.valueOf(properties.getProperty("nicaSelected")));
        eventDetails.setAcSelected(Boolean.valueOf(properties.getProperty("acSelected")));
        eventDetails.setAdditionalACSelected(Boolean.valueOf(properties.getProperty("additionalACSelected")));
        return eventDetails;
    }

    private Date getEventDate() {
         try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            java.util.Date date = sdf.parse(properties.getProperty("eventDate"));
            
            java.sql.Date sqlDate = new Date(date.getTime());
            return sqlDate;
        } catch (ParseException ex) {
            Logger.getLogger(MockDateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
