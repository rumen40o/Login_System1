document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/Login_System1/login', { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password }) // Send the data as JSON
        });

        if (response.ok) {
            // If login is successful, redirect to profile page
            window.location.href = 'profile.html';
        } else {
            const errorData = await response.json();
            alert(`Login failed: ${errorData.message}`); // Show error message
        }
    } catch (error) {
        console.error('Error:', error); // Log any error
        alert('An error occurred while logging in.'); // Show alert
    }
});
