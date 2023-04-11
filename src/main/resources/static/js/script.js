function validateInput(inputElement) {
    const errorMessageDiv = document.getElementById('error-message');

    if (!inputElement.value) {
        inputElement.classList.add('invalid');
        errorMessageDiv.textContent = 'All fields must be filled out';
        errorMessageDiv.classList.add('show');
        errorMessageDiv.style.display = 'block';
        return false;
    } else {
        inputElement.classList.remove('invalid');
        errorMessageDiv.classList.remove('show');
        errorMessageDiv.style.display = 'none';
        return true;
    }
}


function validatePasswords() {
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const repeatPassword = document.getElementById('repeatPassword');

    const isUsernameValid = validateInput(username);
    const isPasswordValid = validateInput(password);
    const isRepeatPasswordValid = validateInput(repeatPassword);

    if (!isUsernameValid || !isPasswordValid || !isRepeatPasswordValid) {
        return false;
    }

    if (password.value !== repeatPassword.value) {
        password.classList.add('invalid');
        repeatPassword.classList.add('invalid');
        const errorMessageDiv = document.getElementById('error-message');
        errorMessageDiv.textContent = 'Provided passwords must be the same';
        errorMessageDiv.classList.add('show');
        return false;
    }

    const errorMessageDiv = document.getElementById('error-message');
    errorMessageDiv.classList.remove('show');
    return true;
}

function validateLoginForm() {
    const username = document.getElementById('username');
    const password = document.getElementById('password');

    const isUsernameValid = validateInput(username);
    const isPasswordValid = validateInput(password);

    if (!isUsernameValid || !isPasswordValid) {
        return false;
    }

    const errorMessageDiv = document.getElementById('error-message');
    errorMessageDiv.classList.remove('show');
    return true;
}