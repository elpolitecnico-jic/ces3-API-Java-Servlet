package com.courses.courses.servlet;

import com.courses.courses.entities.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "CourseServlet", value = "/CourseServlet")
public class CourseServlet extends HttpServlet {


    public static ArrayList<Course> listCourses = new ArrayList<>(Arrays.asList(
            new Course(1,"Bases de datos","Conceptos Basico","",30),
            new Course(2,"Programacion orientada a objetos","Como utilizar este paradigma de programacion","",15),
            new Course(3,".Net MVC","CRUD con MVC","",30),
            new Course(4,"Angular 13","Servicios y Pipes","",20),
            new Course(5,"HTML y CSS","Resposive design","",50)
    ));

    private String message;

    public void init() {
        message = "Courses API";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        setAccessControlHeaders(response);

        if(request.getParameter("id") != null){
            int idParameter = Integer.parseInt(request.getParameter("id"));
            Course result = searchCourseById(idParameter);
            out.print(gson.toJson(result));
        }else{
            out.print(gson.toJson(listCourses));
        }

        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        setAccessControlHeaders(response);

        StringBuffer stringBuffer = getStringFromBody(request);
        Course newCourse = getCourseFromBody(stringBuffer);

        listCourses.add(newCourse);
        out.print(gson.toJson(newCourse));
        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        setAccessControlHeaders(response);

        if(request.getParameter("id") != null){

            int idParameter = Integer.parseInt(request.getParameter("id"));
            StringBuffer stringBuffer = getStringFromBody(request);
            Course result = updateCourse(idParameter, stringBuffer);
            out.print(gson.toJson(result));

        }else{
            out.print(gson.toJson("Debe ingresar el Id del registro que va ser editado"));
        }

        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        setAccessControlHeaders(response);

        if(request.getParameter("id") != null){

            int idParameter = Integer.parseInt(request.getParameter("id"));

            for (Course course: listCourses) {
                if (idParameter == course.getId()){
                    listCourses.remove(course);
                    break;
                }
            }

            out.print(gson.toJson(listCourses));

        }else{
            out.print(gson.toJson("Debe ingresar el Id del registro que va ser eliminado"));
        }

        out.flush();

    }

    private Course searchCourseById(int Id){

        Course result = new Course();

        for (Course course: listCourses) {
            if (Id == course.getId()){
                result = course;
                break;
            }
        }

        return  result;
    }

    private StringBuffer getStringFromBody(HttpServletRequest request) throws IOException{

        BufferedReader reader = request.getReader();
        StringBuffer jb = new StringBuffer();
        String line = null;

        while ((line = reader.readLine()) != null)
            jb.append(line);
        reader.close();

        return jb;
    }

    private Course getCourseFromBody(StringBuffer stringBuffer){

        Course result = new Course();
        JsonObject obj = JsonParser.parseString(stringBuffer.toString()).getAsJsonObject();

        result.setId(obj.get("Id").getAsInt());
        result.setName(obj.get("Name").getAsString());
        result.setUrlResource(obj.get("UrlResource").getAsString());
        result.setDescription(obj.get("Description").getAsString());
        result.setDurationTime(obj.get("DurationTime").getAsInt());

        return result;

    }

    private Course updateCourse(int idCourse, StringBuffer stringBuffer){

        Course result = new Course();
        JsonObject obj = JsonParser.parseString(stringBuffer.toString()).getAsJsonObject();

        for (Course course: listCourses) {

            if (idCourse == course.getId()){

                if(obj.get("Name").getAsString() != null && obj.get("Name").getAsString() != ""){
                    course.setName(obj.get("Name").getAsString());
                }

                if(obj.get("UrlResource").getAsString() != null && obj.get("UrlResource").getAsString() != ""){
                    course.setUrlResource(obj.get("UrlResource").getAsString());
                }

                if(obj.get("Description").getAsString() != null && obj.get("Description").getAsString() != ""){
                    course.setDescription(obj.get("Description").getAsString());
                }

                if(obj.get("DurationTime").getAsString() != null && obj.get("DurationTime").getAsString() != ""){
                    course.setDurationTime(obj.get("DurationTime").getAsInt());
                }

                result = course;

                break;
            }
        }



        return result;

    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
    }
}
