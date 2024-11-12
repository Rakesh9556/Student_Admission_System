<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="flex items-center justify-between px-8 w-full h-full py-3 bg-green-600 drop-shadow-[0_4px_10px_rgba(0,0,0,0.6)]">
    <h1 class="text-4xl font-bold">Dashboard</h1>
    <div class="flex items-center">
        <p class="text-xl font-bold mr-3">Profile</p>
        <div class="w-14 h-14 bg-blue-200 rounded-full mr-2 overflow-hidden relative">
            <button id="profileBtn">
                <img src="img/user.svg" alt="Profile" class="w-full h-full object-fit p-1">
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