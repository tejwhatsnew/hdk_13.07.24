document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();
        
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

        const users = JSON.parse(sessionStorage.getItem("users")) || [];
        const user = users.find(user => user.username === username && user.password === password);

        if (user) {
            sessionStorage.setItem("loggedInUser", JSON.stringify(user));
            // document.querySelector('.login-success').textContent = 'You successfully logged in!';
            setTimeout(() => {
                window.location.href = "profile.html"; 
            }, 1000);
        } else {
            document.getElementById('passwordError').textContent = 'Invalid username or password';
        }
    });
});

