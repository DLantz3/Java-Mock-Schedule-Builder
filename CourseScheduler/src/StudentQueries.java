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

public class StudentQueries 
{
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentID, firstName, lastName from app.student order by studentID");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry temp=new StudentEntry(resultSet.getString("studentID"), resultSet.getString("firstName"), resultSet.getString("LastName"));
                students.add(temp);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return students;
        
    }
    
    public static StudentEntry getStudent(String studentID)
    {
        connection=DBConnection.getConnection();
        StudentEntry student=null;
        try
        {
            getStudent=connection.prepareStatement("select studentID, firstName, lastName from app.student where studentID = ?");
            getStudent.setString(1, studentID);
            resultSet=getStudent.executeQuery();

            if (resultSet.next())
            {
                student=new StudentEntry(resultSet.getString("studentID"), resultSet.getString("firstName"), resultSet.getString("lastName"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return student;
    }
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try 
        {
            dropStudent=connection.prepareStatement("delete from app.student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
            
        } 
        catch (SQLException sqlException) 
        {
            sqlException.printStackTrace();
                
            
        }
            
    }

    
}
