function togglePassword(password, eye_password) {
  if (password.type == "password") {
    password.type = "text";
    eye_password.src = "../icons/eye-off-outline.svg";
  } else {
    password.type = "password";
    eye_password.src = "../icons/eye-outline.svg";
  }
}

export default togglePassword;
