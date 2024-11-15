<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.tailwindcss.com"></script>
<script type="text/javascript" src="js/index.js"></script>

</head>

<body class="flex justify-end items-center h-screen">
 <div
      class="flex flex-col justify-start items-center w-2/5 h-full bg-orange-300 rounded-lg"
    >
      <h1
        id="title"
        class="text-5xl mt-20 font-extrabold text-gray-900 dark:text-white"
      >
        Welcome to Studify
      </h1>

      <!-- Role selection section -->
      <div id="roleSection" class="block w-1/2 my-auto">
        <select
          id="role"
          onchange="showCategory()"
          class="block bg-gray-50 border border-orange-700 text-gray-900 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full h-12 p-2.5"
        >
          <option selected>Select your role</option>
          <option value="ADMIN">Admin</option>
          <option value="FACULTY">Faculty</option>
          <option value="STUDENT">Student</option>
        </select>
      </div>

      <!-- Student category section -->
      <div id="studentCategorySection" class="hidden w-1/2 my-auto">
        <select
          id="studentCategory"
          onchange="showPersonalInfoForm()"
          class="block bg-gray-50 border border-orange-700 text-gray-900 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full h-12 p-2.5"
        >
          <option selected>Select your Category</option>
          <option value="INDIVIDUAL">Individual Student</option>
          <option value="UNIVERSITY">University Student</option>
        </select>
      </div>

      <!-- Personal info form -->
      <form
        id="personalInfoForm"
        class="hidden w-4/5 my-auto"
        onsubmit="showNextForm(event)"
      >
        <h2 class="text-3xl text-center font-bold mb-12">
          Enter your Personal Details
        </h2>
        <div class="grid gap-6 mb-6 md:grid-cols-2">
          <div>
            <label for="first_name" class="block mb-2 text-lg font-bold"
              >First name</label
            >
            <input
              type="text"
              id="first_name"
              class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
              placeholder="John"
              required
            />
          </div>
          <div>
            <label for="last_name" class="block mb-2 text-lg font-bold"
              >Last name</label
            >
            <input
              type="text"
              id="last_name"
              class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
              placeholder="Doe"
              required
            />
          </div>
        </div>
        <div class="mb-6">
          <label for="email" class="block mb-2 text-lg font-bold"
            >Email address</label
          >
          <input
            type="email"
            id="email"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            placeholder="john.doe@company.com"
            required
          />
        </div>
        <div class="mb-6">
          <label for="password" class="block mb-2 text-lg font-bold"
            >Password</label
          >
          <input
            type="password"
            id="password"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            placeholder="•••••••••"
            required
          />
        </div>
        <div class="mb-6">
          <label for="confirm_password" class="block mb-2 text-lg font-bold"
            >Confirm password</label
          >
          <input
            type="password"
            id="confirm_password"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            placeholder="•••••••••"
            required
          />
        </div>
        <div class="flex justify-around items-center">
          <button
            type="submit"
            class="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-lg px-4 py-2.5"
          >
            Next
          </button>
        </div>
      </form>

      <!-- University registration form -->
      <form
        id="universityInfoForm"
        class="hidden w-4/5 my-auto"
        action="universityStudentRegister"
        method="post"
        onsubmit="handleSubmit(event)"
      >
        <!-- Hidden fields to store form data -->
        <input type="hidden" id="hidden_role" name="role" />
        <input type="hidden" id="hidden_studentType" name="studentType" />
        <input type="hidden" id="hidden_fullname" name="fullname" />
        <input type="hidden" id="hidden_email" name="email" />
        <input type="hidden" id="hidden_password" name="password" />

        <h2 class="text-3xl text-center font-bold mb-12">
          Enter your University Details
        </h2>
        <div class="mb-6">
          <label for="universityName" class="block mb-2 text-lg font-bold"
            >University Name</label
          >
          <input
            type="text"
            id="universityName"
            name="universityName"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            placeholder="Boston University"
            required
          />
        </div>
        <div class="mb-6">
          <label for="studentId" class="block mb-2 text-lg font-bold"
            >Student Id</label
          >
          <input
            type="text"
            id="studentId"
            name="studentId"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            placeholder="23XXXXXXXXXX"
            required
          />
        </div>
        <div class="mb-6">
          <label for="department" class="block mb-2 text-lg font-bold"
            >Department</label
          >
          <select
            id="department"
            name="department"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            required
          >
            <option value="" disabled selected>Select your department</option>
            <option value="Computer Science">Computer Science</option>
            <option value="Information Technology">
              Information Technology
            </option>
            <option value="Electrical Engineering">
              Electrical Engineering
            </option>
            <option value="Mechanical Engineering">
              Mechanical Engineering
            </option>
            <option value="Civil Engineering">Civil Engineering</option>
          </select>
        </div>
        <div class="mb-6">
          <label for="specialization" class="block mb-2 text-lg font-bold"
            >Specialization</label
          >
          <select
            id="specialization"
            name="specialization"
            class="bg-gray-50 border rounded-lg text-gray-900 text-lg p-2.5"
            required
          >
            <option value="" disabled selected>
              Select your specialization
            </option>
            <option value="Artificial Intelligence">
              Artificial Intelligence
            </option>
            <option value="Data Science">Data Science</option>
            <option value="Cybersecurity">Cybersecurity</option>
            <option value="Software Engineering">Software Engineering</option>
            <option value="Robotics">Robotics</option>
            <option value="Cloud Computing">Cloud Computing</option>
          </select>
        </div>
        <div class="flex justify-center items-center">
          <button
            type="submit"
            class="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-lg px-4 py-2.5"
          >
            Submit
          </button>
        </div>
      </form>
    </div>
</body>
</html>