
async function fetchUserData() {
    try {
        const response = await fetch('/user');
        if (response.ok) {
            const user = await response.json();
            document.getElementById('first-name').value = user.firstName;
            document.getElementById('last-name').value = user.lastName;
        } else {
            alert('Failed to load user data.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching user data.');
    }
}

document.getElementById('change-name-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;

    try {
        const response = await fetch('/user/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ firstName, lastName })
        });

        if (response.ok) {
            alert('Profile updated successfully!');
            fetchUserData(); 
        } else {
            alert('Failed to update profile.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while updating the profile.');
    }
});


document.addEventListener('DOMContentLoaded', fetchUserData);
