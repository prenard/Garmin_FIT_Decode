import com.garmin.fit.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.Timestamp;

public class Garmin_FIT_Decode
{
	   public static PrintWriter FIT_Decode_Log;

	   public static String get_Manufacturer_Name(int Manufacturer_ID)
	   {
		   String Manufacturer_Name;
		   switch(Manufacturer_ID)
		   {
			   case 1: Manufacturer_Name = "Garmin"; break;
			   default: Manufacturer_Name = "Unknown"; break;
		   }
		   return Manufacturer_Name;
	   }

	   public static String get_Product_Name(int Manufacturer_ID, int Product)
	   {
		   String Product_Name;
		   switch(Manufacturer_ID)
		   {
			   case 1:
			    switch(Product)
			    {
					case 8: Product_Name = "HRM Run (Single byte)"; break;
					case 1623: Product_Name = "FR620"; break;
					case 1752: Product_Name = "HRM Run"; break;
					case 10007: Product_Name = "Foot Pod"; break;
					default: Product_Name = "Unknown"; break;
				}
				break;
			   default: Product_Name = "Unknown"; break;
		   }
		   return Product_Name;
	   }
	/*
	    public static String get_Device_Type(int DeviceType) {
		   String Device_Type;
		   switch(DeviceType) {
			   case 120: Device_Type = "HRM"; break;
			   default: Device_Type = "Unknown"; break;
		   }
		   return Device_Type;
	   }
	*/
	   private static class Listener
	   						implements 	MesgListener,
	   									FileIdMesgListener,
	   									UserProfileMesgListener,
	   									DeviceInfoMesgListener,
	   									MonitoringMesgListener,
	   									EventMesgListener,
	   									ActivityMesgListener,
	   									RecordMesgListener,
	   									SessionMesgListener,
	   									LapMesgListener,
	   									FileCreatorMesgListener,
	   									CourseMesgListener
	   {

	      public String Mesg_Type;

	      public void onMesg(Mesg mesg)
	      {
	         System.out.println("Mesg: ");

	         if (mesg.getName() != null)
	         {
	            System.out.print("   Name: ");
	            System.out.println(mesg.getName());
	            WriteLog(FIT_Decode_Log, "Mesg - Name: " + mesg.getName());
	         }
	      }


	      public void onMesg(CourseMesg mesg)
	      {
	         Mesg_Type = "Course";
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - Name: " + mesg.getName());
	      }

	      public void onMesg(EventMesg mesg)
	      {
	         Mesg_Type = "Event";
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Event Type: " + mesg.getEventType());
	      }

		  public void onMesg(FileCreatorMesg mesg)
		  {
			 Mesg_Type = "FileCreator";
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - HardwareVersion: " + mesg.getHardwareVersion());
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - SoftwareVersion: " + mesg.getSoftwareVersion());
		  }


	      public void onMesg(LapMesg mesg)
	      {
	         Mesg_Type = "Lap";
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Event Type: " + mesg.getEventType());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - StartTime: " + mesg.getStartTime());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - TotalTimerTime: " + mesg.getTotalTimerTime());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - AvgSpeed: " + mesg.getAvgSpeed());
	      }

	      public void onMesg(SessionMesg mesg)
	      {
	         Mesg_Type = "Session";
			 WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Event Type: " + mesg.getEventType());
	      }

	      public void onMesg(FileIdMesg mesg)
	      {

	         Mesg_Type = "File ID";

	         System.out.println("File ID:");

	         if (mesg.getType() != null)
	         {
	            System.out.print("   Type: ");
	            System.out.println(mesg.getType().getValue());
	         }

	         if (mesg.getManufacturer() != null)
	         {
	            System.out.print("   Manufacturer: ");
	            System.out.println(mesg.getManufacturer() + " - " + get_Manufacturer_Name(mesg.getManufacturer()));
	         }

	         if (mesg.getProduct() != null)
	         {
	            System.out.print("   Product: ");
	            System.out.println(mesg.getProduct());
	         }

	         if (mesg.getSerialNumber() != null)
	         {
	            System.out.print("   Serial Number: ");
	            System.out.println(mesg.getSerialNumber());
	         }

	         if (mesg.getNumber() != null)
	         {
	            System.out.print("   Number: ");
	            System.out.println(mesg.getNumber());
	         }

	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Type: " + mesg.getType());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Manufacturer: " + mesg.getManufacturer() + " - " + get_Manufacturer_Name(mesg.getManufacturer()));
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Garmin Product: " + mesg.getGarminProduct() + " - " + get_Product_Name(mesg.getManufacturer(),mesg.getGarminProduct()));
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - Serial Number: " + mesg.getSerialNumber());
	         WriteLog(FIT_Decode_Log, Mesg_Type + " - TimeCreated: " + mesg.getTimeCreated());
	      }

	      public void onMesg(UserProfileMesg mesg)
	      {
	         System.out.println("User profile:");

	         if ((mesg.getFriendlyName() != null) )
	         {
	            System.out.print("   Friendly Name: ");
	            System.out.println(mesg.getFriendlyName());
	         }

	         if (mesg.getGender() != null)
	         {
	            if (mesg.getGender() == Gender.MALE)
	               System.out.println("   Gender: Male");
	            else if (mesg.getGender() == Gender.FEMALE)
	               System.out.println("   Gender: Female");
	         }

	         if (mesg.getAge() != null)
	         {
	            System.out.print("   Age [years]: ");
	            System.out.println(mesg.getAge());
	         }

	         if (mesg.getWeight() != null)
	         {
	            System.out.print("   Weight [kg]: ");
	            System.out.println(mesg.getWeight());
	         }
	      }

	      public void onMesg(DeviceInfoMesg mesg)
	      {

	         Mesg_Type = "Device Info";

	         System.out.println("Device info:");

	         if(mesg.getTimestamp() != null)
	         {
	            System.out.print("   Timestamp: ");
	            System.out.println(mesg.getTimestamp());
	            WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
	            WriteLog(FIT_Decode_Log, Mesg_Type + " - Manufacturer: " + mesg.getManufacturer() + " - " + get_Manufacturer_Name(mesg.getManufacturer()));
	/*            WriteLog(FIT_Decode_Log, Mesg_Type + " - Device Type: " + mesg.getDeviceType() + " - " + get_Device_Type(mesg.getDeviceType()));*/
				WriteLog(FIT_Decode_Log, Mesg_Type + " - Product Name: " + mesg.getProductName());
				WriteLog(FIT_Decode_Log, Mesg_Type + " - Product: " + mesg.getProduct() + " - " + get_Product_Name(mesg.getManufacturer(),mesg.getProduct()));
	            WriteLog(FIT_Decode_Log, Mesg_Type + " - Serial Number: " + mesg.getSerialNumber());
	            WriteLog(FIT_Decode_Log, Mesg_Type + " - ANT Device Number: " + mesg.getAntDeviceNumber());
	            WriteLog(FIT_Decode_Log, Mesg_Type + " - Battery Voltage: " + mesg.getBatteryVoltage());
	         }

	         if(mesg.getBatteryStatus() != null)
	         {
	            System.out.print("   Battery status: ");

	            switch(mesg.getBatteryStatus())
	            {

	            case BatteryStatus.CRITICAL:
	               System.out.println("Critical");
	               break;
	            case BatteryStatus.GOOD:
	               System.out.println("Good");
	               break;
	            case BatteryStatus.LOW:
	               System.out.println("Low");
	               break;
	            case BatteryStatus.NEW:
	               System.out.println("New");
	               break;
	            case BatteryStatus.OK:
	               System.out.println("OK");
	               break;
	            default:
	               System.out.println("Invalid");
	            }
	         }
	      }

	      public void onMesg(MonitoringMesg mesg)
	      {
	         System.out.println("Monitoring:");

	         if(mesg.getTimestamp() != null)
	         {
	            System.out.print("   Timestamp: ");
	            System.out.println(mesg.getTimestamp());
	         }

	         if(mesg.getActivityType() != null)
	         {
	            System.out.print("   Activity Type: ");
	            System.out.println(mesg.getActivityType());
	         }

	         // Depending on the ActivityType, there may be Steps, Strokes, or Cycles present in the file
	         if(mesg.getSteps() != null)
	         {
	            System.out.print("   Steps: ");
	            System.out.println(mesg.getSteps());
	         }
	         else if(mesg.getStrokes() != null)
	         {
	            System.out.print("   Strokes: ");
	            System.out.println(mesg.getStrokes());
	         }
	         else if (mesg.getCycles() != null)
	         {
	            System.out.print("   Cycles: ");
	            System.out.println(mesg.getCycles());
	         }
	      }

	      public void onMesg(ActivityMesg mesg)
		  {
			  System.out.println("Activity:");
			  Mesg_Type = "Activity";
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Event: " + mesg.getEvent());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - EventGroup: " + mesg.getEventGroup());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - EventType: " + mesg.getEventType());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - LocalTimestamp: " + mesg.getLocalTimestamp());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - NumSessions: " + mesg.getNumSessions());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - TotalTimerTime: " + mesg.getTotalTimerTime());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Type: " + mesg.getType());

			  
			  // Offset between Garmin 
              // (FIT) time and Unix 
              // time in ms (Dec 31, 
              // 1989 - 00:00:00 
              // January 1, 1970). 			  
			  // https://www.programcreek.com/java-api-examples/index.php?source_dir=wattzap-ce-master/src/com/wattzap/utils/FitImporter.java
			  
			  
			  long OFFSET = 631065600000l;

			  //Unix seconds
			  long Garmin_epoch_seconds = mesg.getLocalTimestamp();
			  //convert seconds to milliseconds
			  Date date = new Date(Garmin_epoch_seconds * 1000l + OFFSET);
			  // format of the date
			  SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			  jdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));
			  String java_date = jdf.format(date);
			  System.out.println("\n"+java_date+"\n");

		  }

	      public void onMesg(RecordMesg mesg)
		  {
			  System.out.println("Record:");
			  Mesg_Type = "Record";
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Timestamp: " + mesg.getTimestamp());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - Distance: " + mesg.getDistance());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - PositionLat: " + mesg.getPositionLat());
			  WriteLog(FIT_Decode_Log, Mesg_Type + " - PositionLong: " + mesg.getPositionLong());
		  }

	   }
	   

	  public static void WriteLog(PrintWriter Log, String Line)
	  {
	   try
	   {
		   // Get date and time
		   Date today = new Date();
		   Locale currentLocale = new Locale("fr","FR");
		   SimpleDateFormat MyFormatter = new SimpleDateFormat ("yyyy'-'MM'-'dd HH:mm:ss");
		   //DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,currentLocale);
		   String DatePrefix = MyFormatter.format(today);
		   Log.println(DatePrefix + " - " + Line);
	   }
	   catch (Exception err)
	   {
		   err.printStackTrace();
	   }
	  }

	  public static void main(String[] args)
	  {
	   try
	   {
		   String[] fit_filename_tokens = args[0].split("\\.(?=[^\\.]+$)");
		   
		   // Open log file
	       FIT_Decode_Log = new PrintWriter(new BufferedWriter(new FileWriter(fit_filename_tokens[0] + "_FIT_Decode_Log.txt")));

	       Decode decode = new Decode();
	       //decode.skipHeader();        // Use on streams with no header and footer (stream contains FIT defn and data messages only)
	       //decode.incompleteStream();  // This suppresses exceptions with unexpected eof (also incorrect crc)
	       MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
	       Listener listener = new Listener();
	       FileInputStream in;

	       System.out.printf("FIT Decode Example Application - Protocol %d.%d Profile %.2f %s\n", Fit.PROTOCOL_VERSION_MAJOR, Fit.PROTOCOL_VERSION_MINOR, Fit.PROFILE_VERSION / 100.0, Fit.PROFILE_TYPE);

	       WriteLog(FIT_Decode_Log, "FIT Decode Example Application - Protocol");

	       in = new FileInputStream(args[0]);

	       mesgBroadcaster.addListener((MesgListener) listener);
	       mesgBroadcaster.addListener((FileIdMesgListener) listener);
	       mesgBroadcaster.addListener((UserProfileMesgListener) listener);
	       mesgBroadcaster.addListener((DeviceInfoMesgListener) listener);
	       mesgBroadcaster.addListener((MonitoringMesgListener) listener);
	       mesgBroadcaster.addListener((EventMesgListener) listener);
	       mesgBroadcaster.addListener((ActivityMesgListener) listener);
	       mesgBroadcaster.addListener((RecordMesgListener) listener);
	       mesgBroadcaster.addListener((SessionMesgListener) listener);
	       mesgBroadcaster.addListener((LapMesgListener) listener);
	       mesgBroadcaster.addListener((FileCreatorMesgListener) listener);
	       mesgBroadcaster.addListener((CourseMesgListener) listener);
	       
	       decode.read(in, mesgBroadcaster, mesgBroadcaster);

	       in.close();

	       System.out.println("Decoded FIT file " + args[0] + ".");
	       FIT_Decode_Log.close();

	   }
	   catch(Exception err)
	   {
		   err.printStackTrace();
	   }
	  }
}