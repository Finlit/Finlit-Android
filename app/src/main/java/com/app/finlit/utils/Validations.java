package com.app.finlit.utils;

import android.widget.EditText;
import android.widget.TextView;

public class Validations {

    public Validations() {

    }

    public boolean validateAddProfileForm(EditText etName,TextView txtDob, EditText etUserName, EditText etLocation,EditText etEmail) {

        String name = etName.getText().toString().trim();
        String dob = txtDob.getText().toString().trim();
        String username = etUserName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

        if (name.isEmpty()) {
            etName.setError("Please Enter Name");
            etName.requestFocus();
            return false;
        }
        if (name.length() < 2) {
            etName.setError("Please Enter Valid Name");
            etName.requestFocus();
            return false;
        }
        if (dob.isEmpty()) {
            txtDob.setError("Please Enter Date of Birth");
            txtDob.requestFocus();
            return false;
        }

        if (username.isEmpty()) {
            etUserName.setError("Please Enter UserName");
            etUserName.requestFocus();
            return false;
        }
        if (username.length() < 2) {
            etUserName.setError("Please Enter Valid UserName");
            etUserName.requestFocus();
            return false;
        }

        if (location.isEmpty()) {
            etLocation.setError("Please Enter Location");
            etLocation.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Please Enter Email");
            etEmail.requestFocus();
            return false;
        }
        if(!email.matches(regex)){
            etEmail.setError("Please Enter Valid Email");
            etEmail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateSignInForm(EditText etUserName, EditText etPassword) {

        if (etUserName.getText().toString().trim().isEmpty()) {
            etUserName.setError("Please Enter Email");
            etUserName.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("Password should be atleast of 6 charactors");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateSecuritySetup(EditText etUserName, EditText etPassword, EditText etRePassword) {

        if (etUserName.getText().toString().trim().isEmpty()) {
            etUserName.setError("Please Enter UserName");
            etUserName.requestFocus();
            return false;
        }
        if (etUserName.getText().toString().trim().length() < 2) {
            etUserName.setError("Please Enter Valid UserName");
            etUserName.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 8) {
            etPassword.setError("Password should be atleast of 8 charactors");
            etPassword.requestFocus();
            return false;
        }
        if (etRePassword.getText().toString().trim().isEmpty()) {
            etRePassword.setError("Please Enter Re-Password");
            etRePassword.requestFocus();
            return false;
        }
        if(!etRePassword.getText().toString().equals(etPassword.getText().toString())){
            etRePassword.setError("Re-Password doesn't match Password");
            etRePassword.requestFocus();
            return false;
        }
        return true;
    }

}
