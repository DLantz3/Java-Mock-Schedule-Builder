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

public class MultiTableQueries 
{
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> allClassDescriptions = new ArrayList<ClassDescription>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                ClassDescription temp=new ClassDescription(resultSet.getString("courseCode"), resultSet.getString("description"), resultSet.getInt("seats"));
                allClassDescriptions.add(temp);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return allClassDescriptions;
        
    }
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students=new ArrayList<StudentEntry>();

        try 
        {
            getScheduledStudentsByClass=connection.prepareStatement("select app.student.studentID, app.student.firstName, app.student.lastName from app.schedule, app.student where app.schedule.semester = ? and app.schedule.courseCode = ? and app.schedule.status = 'S' and app.schedule.studentID = app.student.studentID order by app.student.lastName, app.student.firstName");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);

            resultSet = getScheduledStudentsByClass.executeQuery();

            while (resultSet.next()) 
            {
                StudentEntry student=new StudentEntry(resultSet.getString("studentID"),resultSet.getString("firstName"),resultSet.getString("lastName"));
                students.add(student);
            }
        } 
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
        } 
        

        return students;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students=new ArrayList<StudentEntry>();

        try 
        {
            getWaitlistedStudentsByClass=connection.prepareStatement("select app.student.studentID, app.student.firstName, app.student.lastName from app.schedule, app.student where app.schedule.semester = ? and app.schedule.courseCode = ? and app.schedule.status = 'W' and app.schedule.studentID = app.student.studentID order by app.student.lastName, app.student.firstName");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);

            resultSet = getWaitlistedStudentsByClass.executeQuery();

            while (resultSet.next()) 
            {
                StudentEntry student=new StudentEntry(resultSet.getString("studentID"),resultSet.getString("firstName"),resultSet.getString("lastName"));
                students.add(student);
            }
        } 
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
        } 
        

        return students;
    }


    
}
