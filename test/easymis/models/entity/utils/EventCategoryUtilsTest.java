package easymis.models.entity.utils;

import easymis.models.utils.EventCategoryUtils;
import easymis.models.entity.EventTypeDetail;
import easymis.models.entity.enumeration.EventCategory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RashidKP
 */
public class EventCategoryUtilsTest {
    
  
    public EventCategoryUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test_wedding_mehandi_ac_isha_nica_should_return_Diamond(){
        System.out.println("test_wedding_mehandi_ac_isha_nica_should_return_Diamond");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setMehandiSelected(true);
        ec.setAcSelected(true);
        ec.setIshaSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.DIAMOND;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_mehandi_ac_isha_should_return_Diamond(){
         System.out.println("test_wedding_mehandi_ac_isha_should_return_Diamond");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setMehandiSelected(true);
        ec.setAcSelected(true);
        ec.setIshaSelected(true);
        EventCategory expResult = EventCategory.DIAMOND;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_ac_isha_nica_should_return_Diamond(){
         System.out.println("test_wedding_ac_isha_nica_should_return_Diamond");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setAcSelected(true);
        ec.setIshaSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.DIAMOND;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_ac_isha_should_return_Diamond(){
         System.out.println("test_wedding_ac_isha_should_return_Diamond");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setAcSelected(true);
        ec.setIshaSelected(true);
        EventCategory expResult = EventCategory.DIAMOND;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_isha_nica_should_return_Gold(){
         System.out.println("test_isha_nica_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setIshaSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_reception_nica_should_return_Gold(){
         System.out.println("test_reception_nica_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setReceptionSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_ac_should_return_Gold(){
         System.out.println("test_wedding_ac_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setAcSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_mehandi_nica_should_return_Gold(){
         System.out.println("test_wedding_mehandi_nica_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setMehandiSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_nica_should_return_Gold(){
         System.out.println("test_wedding_nica_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_mehandi_should_return_Gold(){
         System.out.println("test_wedding_mehandi_should_return_Gold");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        ec.setMehandiSelected(true);
        EventCategory expResult = EventCategory.GOLD;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_wedding_should_return_Silver(){
        System.out.println("test_wedding_should_return_Silver");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setWeddingSelected(true);
        EventCategory expResult = EventCategory.SILVER;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_mehandi_should_return_Silver(){
        System.out.println("test_mehandi_should_return_Silver");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setMehandiSelected(true);
        EventCategory expResult = EventCategory.SILVER;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_isha_should_return_Silver(){
        System.out.println("test_isha_should_return_Silver");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setIshaSelected(true);
        EventCategory expResult = EventCategory.SILVER;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_nica_should_return_Silver(){
        System.out.println("test_nica_should_return_Silver");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setNicaSelected(true);
        EventCategory expResult = EventCategory.SILVER;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_reception_should_return_Silver(){
        System.out.println("test_reception_should_return_Silver");
        EventTypeDetail ec = new EventTypeDetail();
        ec.setReceptionSelected(true);
        EventCategory expResult = EventCategory.SILVER;
        EventCategory result = EventCategoryUtils.getEventCategory(ec);
        assertEquals(expResult, result);
    }
}
