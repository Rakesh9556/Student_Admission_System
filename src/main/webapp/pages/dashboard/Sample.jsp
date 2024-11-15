<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <style>
    .body {
      font-family: "Poppins", sans-serif;
      background-color: var(--app-bg);
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
    }
  </style>
</head>

<body class="flex">

  <!-- Navbar Component -->
  <jsp:include page="/components/Navbar.jsp" />

  <!-- Main Content Area -->
  <div class="flex-1 h-screen flex flex-col">
    
    <!-- Header Component -->
    <jsp:include page="/components/Header.jsp" />

    <!-- Dashboard Component -->
    <jsp:include page="/components/Dashboard.jsp" />
    
  </div>

</body>
</html>
