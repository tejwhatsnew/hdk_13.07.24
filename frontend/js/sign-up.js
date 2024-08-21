document.addEventListener("DOMContentLoaded", () => {
    const registrationForm = document.getElementById("registrationForm");
    const sendOtpBtn = document.getElementById("sendOtpBtn");
    let generatedOtp = null;

    sendOtpBtn.addEventListener("click", () => {
        const email = document.getElementById("email").value;
        if (!validateEmail(email)) {
            alert("Please enter a valid email address.");
            return;
        }
        
        generatedOtp = generateOtp();
        alert(`OTP sent to ${email}: ${generatedOtp}`); // Simulate sending OTP via email
    });

    registrationForm.addEventListener("submit", function(event) {
        event.preventDefault();
        
        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const address = document.getElementById("address").value;
        const phoneNumber = document.getElementById("phone_number").value;
        const otp = document.getElementById("otp").value;
        
        if (!validateFields(username, email, password, address, phoneNumber)) {
            return;
        }

        if (otp !== generatedOtp) {
            alert("Invalid OTP. Please try again.");
            return;
        }

        saveRegistrationData(username, email, password, address, phoneNumber);
        alert("Registration successful!");
        window.location.href = "login.html";
    });

    function validateFields(username, email, password, address, phoneNumber) {
        if (!username || !email || !password || !address || !phoneNumber) {
            alert("All fields are required.");
            return false;
        }

        if (!validateEmail(email)) {
            alert("Please enter a valid email address.");
            return false;
        }

        if (!validatePhoneNumber(phoneNumber)) {
            alert("Please enter a valid phone number.");
            return false;
        }

        return true;
    }

    function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
        return emailPattern.test(email);
    }

    function validatePhoneNumber(phoneNumber) {
        const phonePattern = /^[0-9]{10}$/;
        return phonePattern.test(phoneNumber);
    }

    function generateOtp() {
        return Math.floor(100000 + Math.random() * 900000).toString();
    }

    function saveRegistrationData(username, email, password, address, phoneNumber) {
        const registrationData = {
            username: username,
            email: email,
            password: password,
            address: address,
            phone_number: phoneNumber
        };

        let users = JSON.parse(sessionStorage.getItem("users")) || [];
        users.push(registrationData);
        sessionStorage.setItem("users", JSON.stringify(users));
    }
});
