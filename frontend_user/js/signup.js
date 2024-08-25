const container = document.querySelector(".container"),
    pwShowHide = document.querySelectorAll(".showHidePw"),
    pwFields = document.querySelectorAll(".password"),
    signUp = document.querySelector(".signup-link"),
    login = document.querySelector(".login-link"),
    loginForm = document.querySelector('.login form'),
    loginAlertDiv = document.getElementById('loginAlert');

// JS code to show/hide password and change icon
pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        pwFields.forEach(pwField => {
            if (pwField.type === "password") {
                pwField.type = "text";

                pwShowHide.forEach(icon => {
                    icon.classList.replace("uil-eye-slash", "uil-eye");
                })
            } else {
                pwField.type = "password";

                pwShowHide.forEach(icon => {
                    icon.classList.replace("uil-eye", "uil-eye-slash");
                })
            }
        })
    })
})

// JS code to appear signup and login form
signUp.addEventListener("click", () => {
    container.classList.add("active");
});
login.addEventListener("click", () => {
    container.classList.remove("active");
});

// JS code to handle login functionality
loginForm.addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent the default form submission

    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    // Validate input fields
    if (username === '' || password === '') {
        showLoginAlert('All fields are required!', 'red');
        return;
    }

    // Check if the username and password match the predefined values
    if (username === 'User' && password === '1482') {
        // Save user to localStorage
        const userData = { username, password };
        localStorage.setItem('currentUser', JSON.stringify(userData));

        // Redirect to the Home page
        window.location.href = 'Home.html';
    } else {
        showLoginAlert('Wrong username or password!', 'red');
    }
});

// Function to show alerts for the login form
function showLoginAlert(message, color) {
    loginAlertDiv.textContent = message;
    loginAlertDiv.classList.remove('hidden');
    loginAlertDiv.classList.remove('bg-red-100', 'bg-green-100');
    
    if (color === 'red') {
        loginAlertDiv.classList.add('bg-red-100', 'border-red-400', 'text-red-700');
    } else if (color === 'green') {
        loginAlertDiv.classList.add('bg-green-100', 'border-green-400', 'text-green-700');
    }
}