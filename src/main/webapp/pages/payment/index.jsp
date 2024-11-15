<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Razorpay</title>
<script src="https://cdn.tailwindcss.com"></script>

<script>
<script src="https://checkout.razorpay.com/v1/checkout.js">
</script>

<script>
	var xhhtp = new XMLHttpRequest();
	var RazorPayOrderId;
	function createOrderId() {
		xhttp.open("GET", "http://localhost:8080/Learning_Management_System/RazorPayOrderServlet", false);
		xhttp.send();
		RazorpayOrderId = xhttp.responseText;
		OpenCheckout();
	}
	

</script>

<script>
	var options = {
	    "key": "rzp_test_6RUhQmBt3iCH1Y", // Enter the Key ID generated from the Dashboard
	    "amount": "50", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
	    "currency": "INR",
	    "name": "Acme Corp",
	    "description": "Test Transaction",
	    "image": "https://example.com/your_logo",
	    "order_id": "order_IluGWxBm9U8zJ8", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
	    "handler": function (response){
	        alert(response.razorpay_payment_id);
	        alert(response.razorpay_order_id);
	        alert(response.razorpay_signature)
	    },
	    "prefill": {
	        "name": "Gaurav Kumar",
	        "email": "gaurav.kumar@example.com",
	        "contact": "9000090000"
	    },
	    "notes": {
	        "address": "Razorpay Corporate Office"
	    },
	    "theme": {
	        "color": "#3399cc"
	    }
	};
	var rzp1 = new Razorpay(options);
	rzp1.on('payment.failed', function (response){
	        alert(response.error.code);
	        alert(response.error.description);
	        alert(response.error.source);
	        alert(response.error.step);
	        alert(response.error.reason);
	        alert(response.error.metadata.order_id);
	        alert(response.error.metadata.payment_id);
	});
	document.getElementById('rzp-button1').onclick = function(e){
	    rzp1.open();
	    e.preventDefault();
	}
</script>

</head>

<body class="flex justify-end items-center h-screen">
	<div id="order_id"></div>
	<button id="payBtn" onclick="createorderId()"></button>
</body>
</html>