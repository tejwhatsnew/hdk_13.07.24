document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();
        
        // Clear previous error messages
        document.getElementById('usernameError').textContent = '';
        document.getElementById('passwordError').textContent = '';

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        if (!username || !password) {
            if (!username) {
                document.getElementById('usernameError').textContent = 'Username is required';
            }
            if (!password) {
                document.getElementById('passwordError').textContent = 'Password is required';
            }
            return;
        }

        // Retrieve the stored data from sessionStorage
        const users = JSON.parse(sessionStorage.getItem("users")) || [];

        // Check if the user exists
        const user = users.find(user => user.username === username && user.password === password);

        if (user) {
            alert('Login successful!');
            // Redirect to a different page if needed
            window.location.href = "index.html"; // Example redirection
        } else {
            document.getElementById('passwordError').textContent = 'Invalid username or password';
        }
    });
});
