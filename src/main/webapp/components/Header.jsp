<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="flex items-center justify-between px-8 w-full h-fit py-3 bg-green-600 ">
    <h1 class="text-4xl font-bold">Dashboard</h1>
    <div class="flex items-center">
        <p class="text-xl font-bold mr-3">Profile</p>
        <div class="w-14 h-14 bg-blue-200 rounded-full mr-2 overflow-hidden relative">
            <button id="profileBtn">
          		<img src="<%= request.getContextPath() %>/resources/images/user.svg" width="48" height="48" alt="Profile logo"/>
            </button>
        </div>
    </div>
</div>

<div id="profileComponent" class="hidden absolute right-2 top-14 grid gap-y-6 mt-6 bg-zinc-700 shadow-lg rounded-lg p-4 text-white">
    <h1 class="text-xl font-bold">Name: John Doe</h1>
    <p>Email: john.doe@example.com</p>
</div>
    
<%--     <h1 class="text-xl font-bold">Name: <c:out value="${userName}" /></h1> --%>
<%--     <p>Email: <c:out value="${userEmail}" /></p> --%>


<script>
    const profileBtn = document.getElementById("profileBtn");
    const profileComponent = document.getElementById("profileComponent");

    profileBtn.addEventListener("click", () => {
        profileComponent.classList.toggle("hidden");
    });
</script>