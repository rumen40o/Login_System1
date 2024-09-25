document.getElementById('register-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const firstName = document.getElementById('register-first-name').value; // Make sure to use the correct ID
    const lastName = document.getElementById('register-last-name').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;

    try {
        const response = await fetch('http://localhost:8080/Login_System1/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ first_name: firstName, last_name: lastName, email, password }) // Corrected naming
        });

        if (response.ok) {
            alert('Registration successful!');
            window.location.href = 'login.html';
        } else {
            const errorText = await response.text();
            alert(`Registration failed: ${errorText}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while registering.');
    }
});
