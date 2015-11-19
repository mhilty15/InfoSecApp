package com.paul_nikki.cse5236.appointmentpal;
 
public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://192.168.1.60:7654/login";
 
    // Server user register url
    public static String URL_REGISTER = "http://192.168.1.60:7654/register";

    public static String URL_LOCATIONS = "http://192.168.1.60:7654/locations";

    public static String URL_APPOINTMENTS = "http://192.168.1.60:7654/appointments";

    // sms add
    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://192.168.0.101/android_sms/msg91/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://192.168.0.101/android_sms/msg91/verify_otp.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "ANHIVE";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}