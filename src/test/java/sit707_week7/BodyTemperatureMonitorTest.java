package sit707_week7;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import sit707_week7.TemperatureReading;
import sit707_week7.BodyTemperatureMonitor;
import static org.mockito.Mockito.*;

public class BodyTemperatureMonitorTest {

	@Test
    public void testStudentIdentity() {
        String studentId = "s223417356";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Ritam Sunil Rao";
        Assert.assertNotNull("Student name is null", studentName);
    }
    
    @Test
    public void testReadTemperatureNegative() {
        // Mocking dependencies
        TemperatureSensor temperatureSensor = new TemperatureSensor() {
            @Override
            public double readTemperatureValue() {
                return -1.0; // Assuming temperature cannot be negative
            }
        };

        // Creating BodyTemperatureMonitor instance
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensor, null, null);

        // Calling method to be tested
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verifying behavior
        Assert.assertTrue("Temperature should not be negative", temperature >= 0);
    }
    
    @Test
    public void testReadTemperatureZero() {
        // Mocking dependencies
        TemperatureSensor temperatureSensor = new TemperatureSensor() {
            @Override
            public double readTemperatureValue() {
                return 0.0;
            }
        };

        // Creating BodyTemperatureMonitor instance
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensor, null, null);

        // Calling method to be tested
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verifying behavior
        Assert.assertEquals("Temperature should be zero", 0.0, temperature, 0.001);
    }
    
    @Test
    public void testReadTemperatureNormal() {
        // Mocking dependencies
        TemperatureSensor temperatureSensor = new TemperatureSensor() {
            @Override
            public double readTemperatureValue() {
                return 37.0; // Normal body temperature
            }
        };

        // Creating BodyTemperatureMonitor instance
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensor, null, null);

        // Calling method to be tested
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verifying behavior
        Assert.assertEquals("Normal body temperature is 37.0", 37.0, temperature, 0.001);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
        // Mocking dependencies
        TemperatureSensor temperatureSensor = new TemperatureSensor() {
            @Override
            public double readTemperatureValue() {
                return 40.0; // Abnormally high body temperature
            }
        };

        // Creating BodyTemperatureMonitor instance
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensor, null, null);

        // Calling method to be tested
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verifying behavior
        Assert.assertTrue("Abnormally high body temperature", temperature > 37.5); // Assuming normal body temperature is around 37.5
    }
    
    @Test
    public void testReportTemperatureReadingToCloud() {
        // Create a mock CloudService
        CloudService cloudService = mock(CloudService.class);

        // Create a mock TemperatureReading
        TemperatureReading temperatureReading = mock(TemperatureReading.class);

        // Create the BodyTemperatureMonitor instance with the mock CloudService
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(null, cloudService, null);

        // Call the method to be tested
        bodyTemperatureMonitor.reportTemperatureReadingToCloud(temperatureReading);

        // Verify that sendTemperatureToCloud() method is called with the correct TemperatureReading object
        verify(cloudService).sendTemperatureToCloud(temperatureReading);
    }
    

    @Test
    public void testInquireBodyStatus_Normal() {
        // Create mock objects for CloudService and NotificationSender
        CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        // Set up stubbing for cloudService.queryCustomerBodyStatus() to return "NORMAL"
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("NORMAL");

        // Create BodyTemperatureMonitor instance with mock dependencies
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(null, cloudService, notificationSender);

        // Call the method to be tested
        bodyTemperatureMonitor.inquireBodyStatus();

        // Verify that sendEmailNotification(Customer, String) is called with the correct arguments
        verify(notificationSender).sendEmailNotification(any(Customer.class), eq("Thumbs Up!"));
    }

    @Test
    public void testInquireBodyStatus_Abnormal() {
        // Create mock objects for CloudService and NotificationSender
        CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        // Set up stubbing for cloudService.queryCustomerBodyStatus() to return "ABNORMAL"
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("ABNORMAL");

        // Create BodyTemperatureMonitor instance with mock dependencies
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(null, cloudService, notificationSender);

        // Call the method to be tested
        bodyTemperatureMonitor.inquireBodyStatus();

        // Verify that sendEmailNotification(FamilyDoctor, String) is called with the correct arguments
        verify(notificationSender).sendEmailNotification(any(FamilyDoctor.class), eq("Emergency!"));
    }
    
    

    @Test
    public void testGetFixedCustomer() {
        // Create a Customer object
        Customer expectedCustomer = new Customer(); // You may need to initialize with required data

        // Create BodyTemperatureMonitor instance with null dependencies
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(null, null, null);

        // Call getFixedCustomer() method
        Customer actualCustomer = bodyTemperatureMonitor.getFixedCustomer();

        // Assert that the returned Customer object matches the expected one
        Assert.assertEquals("Returned Customer object does not match expected", expectedCustomer, actualCustomer);
    }

    @Test
    public void testGetFamilyDoctor() {
        // Create a FamilyDoctor object
        FamilyDoctor expectedFamilyDoctor = new FamilyDoctor(); // You may need to initialize with required data

        // Create BodyTemperatureMonitor instance with null dependencies
        BodyTemperatureMonitor bodyTemperatureMonitor = new BodyTemperatureMonitor(null, null, null);

        // Call getFamilyDoctor() method
        FamilyDoctor actualFamilyDoctor = bodyTemperatureMonitor.getFamilyDoctor();

        // Assert that the returned FamilyDoctor object matches the expected one
        Assert.assertEquals("Returned FamilyDoctor object does not match expected", expectedFamilyDoctor, actualFamilyDoctor);
    }

	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testReportTemperatureReadingToCloud() {
//		// Mock reportTemperatureReadingToCloud() calls cloudService.sendTemperatureToCloud()
//		
//		Assert.assertTrue("Not tested", false);
//	}
	
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testInquireBodyStatusNormalNotification() {
//		Assert.assertTrue("Not tested", false);
//	}
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testInquireBodyStatusAbnormalNotification() {
//		Assert.assertTrue("Not tested", false);
//	}
}
