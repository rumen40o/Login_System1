document.getElementById('register-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Fetch input values from the form
    const firstName = document.getElementById('register-first-name').value;
    const lastName = document.getElementById('register-last-name').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;

    try {
        const response = await fetch('http://localhost:8080/Login_System1/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded' 
            },
            body: new URLSearchParams({
                first_name: firstName,
                last_name: lastName,
                email: email,
                password: password
            })
        });

        if (response.ok) {
            alert('Registration successful!');
            window.location.href = 'login.html'; 
        } else {
            const errorData = await response.text();
            alert(`Registration failed: ${errorData}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while registering.');
    }
});
