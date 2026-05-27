/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dillonlantz
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


public class ScheduleQueries 
{
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, courseCode, studentID, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester=? and studentID=?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry temp=new ScheduleEntry(resultSet.getString("semester"), resultSet.getString("courseCode"), resultSet.getString("studentID"), resultSet.getString("status"), resultSet.getTimestamp("timestamp"));
                schedules.add(temp);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;
        
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int studentCount=0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            
            
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                studentCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentCount;
        
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules=new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester = ? and courseCode = ? and status = 'W'");

            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);

            resultSet=getWaitlistedStudentsByClass.executeQuery();

            while (resultSet.next())
            {
                ScheduleEntry temp=new ScheduleEntry(resultSet.getString("semester"), resultSet.getString("courseCode"), resultSet.getString("studentID"), resultSet.getString("status"), resultSet.getTimestamp("timestamp"));
                schedules.add(temp);
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return schedules;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        
        connection = DBConnection.getConnection();
        try {
            dropStudentScheduleByCourse=connection.prepareStatement("delete from app.schedule where semester = ? and studentID = ? and courseCode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        } 
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
        }


    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse=connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        } 
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry)
    {
        
    }


    
}



