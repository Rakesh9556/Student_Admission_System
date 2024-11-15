<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="flex flex-col items-center gap-y-12 w-36 h-screen mx-0 bg-green-600 drop-shadow-[4px_0_10px_rgba(0,0,0,0.4)]">
    <!-- College Logo -->
    <a href="/" class="mt-6">
        <img src="<%= request.getContextPath() %>/resources/images/college.png" width="94" height="94" alt="College logo">
    </a>
    
    <ul class="grid gap-y-12">
      <!-- Application -->
      <li>
          <a href="/application" class="flex flex-col items-center justify-center text-center">
             <img src="/Learning_Management_System/resources/images/application.svg" width="48" height="48" alt="Application img" />
             <span class="text-xl mt-3 font-bold">Application</span>
          </a>
      </li>
      
      <!-- Admission -->
      <li>
        <a href="/admission" class="flex flex-col items-center justify-center text-center">
          <img src="<%= request.getContextPath() %>/resources/images/admission.svg" width="48" height="48" alt="Admission img"/>
          <span class="text-xl mt-3 font-bold">Admission</span>
        </a>
      </li>
      
      <!-- Course -->
      <li>
        <a href="/course" class="flex flex-col items-center justify-center text-center">
          <img src="<%= request.getContextPath() %>/resources/images/course.svg" width="48" height="48" alt="Course img"/>
          <span class="text-xl mt-3 font-bold">Course</span>
        </a>
      </li>
      
      <!-- Students -->
      <li>
        <a href="/students" class="flex flex-col items-center justify-center text-center">
          <img src="<%= request.getContextPath() %>/resources/images/student.svg" width="48" height="48" alt="Students img"/>
          <span class="text-xl mt-3 font-bold">Students</span>
        </a>
      </li>
      
      <!-- Faculty -->
      <li>
        <a href="/faculty" class="flex flex-col items-center justify-center text-center">
          <img src="<%= request.getContextPath() %>/resources/images/faculty.svg" width="48" height="48" alt="Faculty img"/>
          <span class="text-xl mt-3 font-bold">Faculty</span>
        </a>
      </li>
      
      <!-- Admin -->
      <li>
        <a href="/admin" class="flex flex-col items-center justify-center text-center">
          <img src="<%= request.getContextPath() %>/resources/images/administrator.svg" width="48" height="48" alt="Admin img"/>
          <span class="text-xl mt-3 font-bold">Admin</span>
        </a>
      </li>
    </ul>
</div>
