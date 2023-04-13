function validatePasswords() {
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const repeatPassword = document.getElementById('repeatPassword');
    const errorMessageDiv = document.getElementById('error-message');

    let isValid = true;

    if (!username.value) {
        username.classList.add('invalid');
        isValid = false;
    } else {
        username.classList.remove('invalid');
    }

    if (!password.value) {
        password.classList.add('invalid');
        isValid = false;
    } else {
        password.classList.remove('invalid');
    }

    if (!repeatPassword.value) {
        repeatPassword.classList.add('invalid');
        isValid = false;
    } else {
        repeatPassword.classList.remove('invalid');
    }

    if (!isValid) {
        displayError('All fields must be filled out');
        return false;
    }

    if (password.value !== repeatPassword.value) {
        password.classList.add('invalid');
        repeatPassword.classList.add('invalid');
        displayError('Provided passwords must be the same');
        return false;
    }

    errorMessageDiv.style.display = 'none';
    return true;
}

function validateLoginForm() {
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const errorMessageDiv = document.getElementById('error-message');

    let isValid = true;

    if (!username.value) {
        username.classList.add('invalid');
        isValid = false;
    } else {
        username.classList.remove('invalid');
    }

    if (!password.value) {
        password.classList.add('invalid');
        isValid = false;
    } else {
        password.classList.remove('invalid');
    }

    if (!isValid) {
        displayError('All fields must be filled out');
        return false;
    }

    errorMessageDiv.style.display = 'none';
    return true;
}

function displayError(message) {
    const errorDiv = document.getElementById('error-message');
    errorDiv.textContent = message;
    errorDiv.classList.add('fade-in');
    errorDiv.style.display = 'block';

    setTimeout(() => {
        errorDiv.classList.remove('fade-in');
        errorDiv.classList.add('fade-out');
        setTimeout(() => {
            errorDiv.style.display = 'none';
            errorDiv.classList.remove('fade-out');
        }, 1000);
    }, 3000);
}

function displayLoginError() {
    const errorMessage = document.getElementById('error-message').getAttribute('data-error-message');
    if (errorMessage) {
        displayError(errorMessage);
    }
}

document.addEventListener('DOMContentLoaded', displayLoginError);
