package com.suhail.Springsec;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>(List.of(
            new Student(1,"Suhail",98),
            new Student(2,"ms",100)
            ));

    @GetMapping("/stu")
    public List<Student> getStudents(){
        return students;
    }
    @GetMapping("/csrf_token")
    public CsrfToken getCsrf(HttpServletRequest request){  //HttpServletRequest have many functions to get the data
        return (CsrfToken) request.getAttribute("_csrf"); // the _csrf is name in the page soruce of logout
        // (CsrfToken) is because we want the return type as CsrfToken
    }
    @PostMapping("/stu")
    public Student add(@RequestBody Student stu){
        students.add(stu);
        return stu;
    }
}
