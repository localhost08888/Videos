Assignment 4: Servlets and JSP

Set A

a) Design a servlet that provides information about a HTTP request from a client, such as IP address and browser type. The servlet also provides information about the server on which the servlet is running, such as the operating system type, and the names of currently loaded servlets.

index.html

<html>    

<body>        

<form action="lmn" method="get">      

Username:

<input type="text" name="t1">                      

<input type="submit" >        

</form>    </body></html>



web.xml

<web-app>

<servlet>

<servlet-name>abc</servlet-name>

<servlet-class>serverInfo</servlet-class>

</servlet>

<servlet-mapping>

<servlet-name>abc</servlet-name>

<url-pattern>/lmn</url-pattern>

</servlet-mapping>

</web-app>



serverInfo.java

serverInfo.java

import java.io.*;

        import javax.servlet.*;

        import javax.servlet.http.*;

public class serverInfo extends HttpServlet implements Servlet

{

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws 

IOException,ServletException

    {

        res.setContentType("text/html");

        PrintWriter pw=res.getWriter();

        pw.println("<html><body><h2>Information about Http Request</h2>");

        pw.println("<br>Server Name: "+req.getServerName());


        pw.println("<br>Server Port: "+req.getServerPort());

        pw.println("<br>Ip Address: "+req.getRemoteAddr());

//pw.println("<br>Server Path: "+req.getServerPath());        pw.println("<br>Client Browser: 

"+req.getHeader("User-Agent"));

        pw.println("</body></html>");

        pw.close();

    }

}

//pw.println("<br>Server Path: "+req.getServerPath());        pw.println("<br>Client Browser: 

"+req.getHeader("User-Agent"));

        pw.println("</body></html>");

        pw.close();

    }

}




b) Write a Program to make use of following JSP implicit objects:

i. out: To display current Date and Time.

ii. request: To get header information.

iii. response: To Add Cookie

iv. config: get the parameters value defined in <init-param>

v. application: get the parameter value defined in <context-param>

vi. session: Display Current Session ID

vii. pageContext: To set and get the attributes.

viii. page: get the name of Generated Servlet

index.jsp

<%@ page language="java"%>

<%@ page import="java.util.Date" %>

<!DOCTYPE html>

<head>

<title>Index JSP Page</title>

</head>

<body>

<%-- out object example --%>

<table border="1"; style="background-color:#ffffcc; width:30%" >

<caption><h3>JSP Implicit object</h3></caption>

<tr><td><b>Current Time</b></td>

<td><% out.print(new Date()); %></td></tr>

<%-- response object example --%>

<%response.addCookie(new Cookie("Test","Value")); %>

<%-- application object example --%>

<tr><td><b>User context param value</b></td>

<td><%=application.getInitParameter("User") %></td></tr>

<%-- session object example --%>

<tr><td><b>User Session ID</b></td>

<td><%=session.getId() %></td></tr>

<%-- pageContext object example --%>

<% pageContext.setAttribute("Test", "Test Value"); %>

<tr><td><b>PageContext attribute</b></td>

<td>{Name="Test",Value="<%=pageContext.getAttribute("Test") %>"}</td></tr>

<%-- page object example --%>

<tr><td><b>Generated Servlet Name</b>:</td>

<td><%=page.getClass().getName() %></td></tr>

<%-- request object example --%>

<tr><td><b>Request User-Agent</b></td>

<td> <%=request.getHeader("User-Agent") %></td></tr>

</table>

</body>

</html>

<tr><td><b>Generated Servlet Name</b>:</td>

<td><%=page.getClass().getName() %></td></tr>

<%-- request object example --%>

<tr><td><b>Request User-Agent</b></td>

<td> <%=request.getHeader("User-Agent") %></td></tr>

</table>

</body>

</html>









web.xml

<web-app>

<welcome-file-list>

,,,,,,,,<welcome-file>index.jsp</welcome-file>

,,,,</welcome-file-list>,,

,,,,<context-param>

,,,,,,,,<param-name>User</param-name>

,,,,,,,,<param-value>Archana</param-value>

,,,,</context-param>

,,,,<servlet>

,,,,,,,,<servlet-name>xyz</servlet-name>

,,,,,,,,<jsp-file>/index.jsp</jsp-file>

,,,,</servlet>

,,,,<servlet-mapping>

,,,,,,,,<servlet-name>xyz</servlet-name>

,,,,,,,,<url-pattern>/test</url-pattern>

,,,,</servlet-mapping>

</web-app>

web.xml

<web-app>

<welcome-file-list>

,,,,,,,,<welcome-file>index.jsp</welcome-file>

,,,,</welcome-file-list>,,

,,,,<context-param>

,,,,,,,,<param-name>User</param-name>

,,,,,,,,<param-value>Archana</param-value>

,,,,</context-param>

,,,,<servlet>

,,,,,,,,<servlet-name>xyz</servlet-name>

,,,,,,,,<jsp-file>/index.jsp</jsp-file>

,,,,</servlet>

,,,,<servlet-mapping>

,,,,,,,,<servlet-name>xyz</servlet-name>

,,,,,,,,<url-pattern>/test</url-pattern>

,,,,</servlet-mapping>

</web-app>






c) Write a program to create a Online Book purchase. User must be login and then purchase the book. Each page should have a page total. The last page should display a total book and bill, which consists of a page total of what ever the purchase has been done and print the total. (Use HttpSession)





Set B

a) Design an HTML page which passes customer number to a search servlet. The servlet searches for the customer number in a database (customer table) and returns customer details if found the number otherwise display error message.

PGSQL FIle :-

create table customer(name char(20),address char(20),id int);

select * from customer;

import java.io.*;

import javax.servlet.*;

import javax.servlet.http.*;

import java.sql.*;

import java.util.*;



public class customer extends HttpServlet {



    public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException, ServletException {



        responce.setContentType("text/html");

        PrintWriter out = responce.getWriter();



        try {



            Scanner sc = new Scanner(System.in);

            Connection con = null;



            Statement st = null;

            ResultSet rs = null;



            // load driver

            Class.forName("org.postgresql.Driver");



            // establish a conn

            con = DriverManager.getConnection("jdbc:postgresql://localhost/bcs", "postgres", "");



            int cnum = Integer.parseInt(request.getParameter("num"));



            st = con.createStatement();

            rs = st.executeQuery("select * from cust where id=" + cnum);



            while (rs.next()) {

                out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3));



                out.println("<br>");

            }



        } catch (Exception e) {

            out.println(e);

        }



        out.close();

    }



}

Customer.jsp

<html>
  <body>
    <title>Customer Table</title>
    <form method="get" action="http://localhost:8080/servlets/customer">
      Enter Cust No:<input type="text" name="num" />
      <br />
      <input type="submit" name="submit" value="submit" />
    </form>
  </body>
</html>

b) Design an HTML page containing option buttons (Maths, Physics, Chemistry and Biology) and buttons submit and reset. When the user clicks submit, the server responds by adding a cookie containing the selected subject and sends a message back to the client. Program should not allow duplicate cookies to be written.

B2.jsp

<html>

  <head>

    <title>cookie</title>

  </head>

  <body>

    <form method="get" action="http://localhost:8080/Varient/B2">

      <input type="checkbox" name="sub" value="phy" />Physics<br />


      <input type="checkbox" name="sub" value="chem" />Chemistry<br />

      <input type="checkbox" name="sub" value="bio" />Bio<br />

      <input type="checkbox" name="sub" value="math" />Math <br />

      <br />

      <input type="Submit" value="sub" />

      <input type="Reset" value="clear" />

    </form>

  </body>

</html>


B2.java

import java.io.*;

import javax.servlet.*;

import javax.servlet.http.*;

import java.util.*;





public class B2 extends HttpServlet {



    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        String allsub = "";

        String lang[] = req.getParameterValues("sub");

        for (int i = 0; i < lang.length; i++) {



            allsub = allsub + lang[i];



        }



        Cookie c1 = new Cookie("sub1", allsub);

        res.addCookie(c1);

        out.println("cookie added with value: " + allsub);

        out.println("<br>");



        out.close();

    }



}




c) Write a JSP program to display the details of PATIENT (PatientNo, PatientName, PatientAddress, Patientage,PatientDiease) in tabular form on browser



<!DOCTYPE html>

<html>



<body>

    <%@ page import="java.sql.*;" %>

        <%! int pno,age; 

        String pname,address,diease; %>

            <% try {

                Class.forName("org.postgresql.Driver"); 

                Connection con = DriverManager.getConnection("jdbc:postgresql:postgres","postgres", " ");

                Statement st=cn.createStatement();

                ResultSetrs=st.executeQuery("select * from Patient");

                 %>

                <table border="1" width="40%">

                    <tr>

                        <td>Patient No</td>

                        <td>Name</td>

                        <td>Age</td>

                        <td>Address</td

                        <td>Diease</td>

                    </tr>

                    <% while(rs.next()) { %>

                        <tr>

                            <td>

                                <%= rs.getInt("pno") %>

                            </td>

                            <td>

                                <%= rs.getString("pname") %>

                            </td>

                            <%= rs.getInt("age") %>

                             </td>

                            

                            <td>

                                <%= rs.getString("address") %>                          

                             <td>

                                <%= rs.getString("diease") %>

                             </td>

                             

                            </tr>

                        <% 

                        }

                         cn.close(); 

                        }

                        catch(Exception e) {

                         out.println(e); 

                        }

                         %>

</body>



</html>


Set C

a) Create a JSP page for an online multiple choice test. The questions are randomly selected from a database and displayed on the screen. The choices are displayed using radio buttons. When the user clicks on next, the next question is displayed.When the user clicks on submit, display the total score on the screen.





b) Consider the following entities and their relationships Movie (movie_no, movie_name, release_year) Actor(actor_no, name) Relationship between movie and actor is many – many with attribute rate in Rs. Create a RDB in 3 NF answer the following: 

a) Accept an actor name and display all movie names in which he has acted along with his name on top. b) Accept a movie name and list all actors in that movie along with the movie name on top.







at July 31, 2024 
Email This
BlogThis!
Share to X
Share to Facebook
Share to Pinterest
No comments:

Post a Comment


Newer PostOlder PostHome
Subscribe to: Post Comments (Atom)
Total Pageviews
0	26
1	19
2	29
3	23
4	15
5	16
6	33
7	46
8	74
9	56
10	64
11	43
12	28
13	32
14	38
15	49
16	43
17	59
18	68
19	21
20	57
21	50
22	50
23	36
24	98
25	43
26	20
27	42
28	29
29	8
 64,616
Search This Blog
Pages
Home
FYBCS- C programming
FYBCS-DBMS
DSA & SE -I
DSA -II
Python Programming
TYBCS-System Programming
Operating Systems Practicals
Computer Notes
Video Lectures
Java Programming
WEB Technologies-I Practicals
About Me
studycsnotes
View my complete profile
Awesome Inc. theme. Powered by Blogger.
