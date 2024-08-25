// Get elements from the DOM
const loginForm = document.getElementById('loginForm');
const alertDiv = document.getElementById('alert');
const showHidePwIcons = document.querySelectorAll('.showHidePw');
const signupLink = document.querySelector('.signup-link');
const loginLink = document.querySelector('.login-link');
const loginFormDiv = document.querySelector('.form.login');
const signupFormDiv = document.querySelector('.form.signup');

// Event listener for form submission
loginForm.addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent the default form submission

    // Get the values from the input fields
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Check if the entered username and password are correct
    if (username === 'User' && password === '1482') {
        // Save the username to local storage
        localStorage.setItem('username', username);
        
        // Redirect to the Home page
        window.location.href = 'Home.html';
    } else {
        // Show the alert if the credentials are wrong
        alertDiv.classList.remove('hidden');
    }
});

// Toggle password visibility
showHidePwIcons.forEach(icon => {
    icon.addEventListener('click', function() {
        // Get the associated password input field
        const passwordField = this.previousElementSibling;

        // Toggle the password visibility
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            this.classList.remove('uil-eye-slash');
            this.classList.add('uil-eye');
        } else {
            passwordField.type = 'password';
            this.classList.remove('uil-eye');
            this.classList.add('uil-eye-slash');
        }
    });
});

// Switch to Signup Form
signupLink.addEventListener('click', function() {
    loginFormDiv.classList.remove('active');
    signupFormDiv.classList.add('active');
});

// Switch to Login Form
loginLink.addEventListener('click', function() {
    signupFormDiv.classList.remove('active');
    loginFormDiv.classList.add('active');
});
