package com.paul_nikki.cse5236.appointmentpal;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.paul_nikki.cse5236.appointmentpal.Models.Appointment;

import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAppointment1(){
        Date d = new Date(2015,11,28);
        Appointment a = new Appointment(d,"steven","Columbus");
        assertEquals(new Appointment(new Date(2015, 11, 28), "steven", "Columbus"), a);
    }

    public void testAppointment2(){
        Date d = new Date(2002,10,10);
        Appointment a = new Appointment(d,"Tom","New York");
        assertEquals(new Appointment(new Date(2002, 10, 10), "Tom", "New York"), a);
    }

    public void testAppointment3(){
        Date d = new Date(2009,04,8);
        Appointment a = new Appointment(d,"Ben","Detroit");
        assertEquals(new Appointment(new Date(2009, 04, 8), "Ben", "Detroit"), a);
    }

    public void testAppointmentGetDate1(){
        Appointment a = new Appointment(new Date(2009,10,21),"Ben","Detroit");
        Date d = a.getDate();
        assertEquals(new Date(2009, 10, 21), d);
    }

    public void testAppointmentGetDate2(){
        Appointment a = new Appointment(new Date(1990,02,9),"Tom","Boston");
        Date d = a.getDate();
        assertEquals(new Date(1990, 02, 9), d);
    }

    public void testAppointmentGetDate3(){
        Appointment a = new Appointment(new Date(2012,04,22),"Mike","Columbus");
        Date d = a.getDate();
        assertEquals(new Date(2012,04,22),d);
    }

    public void testAppointmentGetDoctor1(){
        Appointment a = new Appointment(new Date(2011,10,14),"Jason","New York");
        String doctor = a.getDoctor();
        assertEquals("Jason",doctor);
    }

    public void testAppointmentGetDoctor2(){
        Appointment a = new Appointment(new Date(1998,7,25),"Michael","Boston");
        String doctor = a.getDoctor();
        assertEquals("Micheal",doctor);
    }

    public void testAppointmentGetDoctor3(){
        Appointment a = new Appointment(new Date(2001,03,4),"Jack","Columbus");
        String doctor = a.getDoctor();
        assertEquals("Columbus",doctor);
    }

    public void testAppointmentGetLocation1(){
        Appointment a = new Appointment(new Date(2002,11,27),"Tom","Los Angeles");
        String location = a.getLocation();
        assertEquals("Los Angeles",location);
    }

    public void testAppointmentGetLocation2(){
        Appointment a = new Appointment(new Date(2013,8,22),"Peter","Houston");
        String location = a.getLocation();
        assertEquals("Houston",location);
    }

    public void testAppointmentGetLocation3(){
        Appointment a = new Appointment(new Date(1990,03,4),"Greg","Washington");
        String location = a.getLocation();
        assertEquals("Washington",location);
    }

}





