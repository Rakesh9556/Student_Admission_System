// object to store form data
const formData = {};

function showCategory() {
  const role = document.getElementById("role").value.toUpperCase();
  const roleSection = document.getElementById("roleSection");
  const studentCategorySection = document.getElementById(
    "studentCategorySection"
  );

  console.log(role);

  if (role === "STUDENT") {
    formData.role = role;
    roleSection.style.display = "none";
    studentCategorySection.style.display = "block";
  } else {
    console.error("Role doesn't exist");
  }
}

function showPersonalInfoForm() {
  const title = document.getElementById("title");
  const studentCategory = document
    .getElementById("studentCategory")
    .value.toUpperCase();
  const studentCategorySection = document.getElementById(
    "studentCategorySection"
  );
  const personalInfoForm = document.getElementById("personalInfoForm");

  if (studentCategory === "UNIVERSITY") {
    formData.studentCategory = studentCategory;
    title.style.display = "none";
    studentCategorySection.style.display = "none";
    personalInfoForm.style.display = "block";
  } else {
    console.error("Student category is required");
  }
}

function validateEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}

function validatePassword(password) {
  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
  return regex.test(password);
}

function showNextForm(event) {
  event.preventDefault();

  const personalInfoForm = document.getElementById("personalInfoForm");
  const universityInfoForm = document.getElementById("universityInfoForm");

  // Validate personal information form fields
  const firstName = document.getElementById("first_name").value.trim();
  const lastName = document.getElementById("last_name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const confirmPassword = document.getElementById("confirm_password").value;

  if (!firstName || !lastName || !email || !password || !confirmPassword) {
    alert("Please fill in all fields.");
    return;
  }

  if (!validateEmail(email)) {
    alert("Please enter a valid email address.");
    return;
  }

  if (!validatePassword(password)) {
    alert(
      "Password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character."
    );
    return;
  }

  if (password !== confirmPassword) {
    alert("Passwords do not match.");
    return;
  }

  const fullname = firstName + " " + lastName;
  document.getElementById("fullname").value = fullname;

  // store these info in formData object
  formData.fullname = fullname;
  formData.email = email;
  formData.password = password;

  personalInfoForm.style.display = "none";
  universityInfoForm.style.display = "block";
}

function handleSubmit(event) {
  event.preventDefault();

  document.getElementById("hidden_role").value = formData.role;
  document.getElementById("hidden_studentType").value = formData.studentCategory;
  document.getElementById("hidden_fullname").value = formData.fullname;
  document.getElementById("hidden_email").value = formData.email;
  document.getElementById("hidden_password").value = formData.password;

  const universityName = document.getElementById("universityName").value.trim();
  const studentId = document.getElementById("studentId").value.trim();
  const department = document.getElementById("department").value.trim();
  const specialization = document.getElementById("specialization").value.trim();

  if (!universityName || !studentId || !department || !specialization) {
    alert("Please fil in all fields!");
    return;
  }

  formData.universityName = universityName;
  formData.studentId = studentId;
  formData.department = department;
  formData.specialization = specialization;

  // Optionally: Log formData to console for debugging
  console.log(formData);

  // Submit the form after populating hidden fields
  const form = document.getElementById("universityInfoForm");
  form.submit();
}
