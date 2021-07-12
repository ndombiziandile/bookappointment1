package com.example.bookappointment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton book_here;
    TextInputLayout nameTextInput;
    TextInputEditText nameEditText;

    TextInputLayout surnameTextInput;
    TextInputEditText surnameEditText;

    TextInputLayout emailTextInput;
    TextInputEditText emailEditText;

    TextInputLayout genderTextInput;
    TextInputEditText genderEditText;

    TextInputLayout ageTextInput;
    TextInputEditText ageEditText;

    TextInputLayout apdateTextInput;
    TextInputEditText apdateEditText;


    FirebaseAuth mAuth;
    private static String TAG="com.blogspot.priyabratanaskar.firebaselogin.ui.LoginActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("bookingActivity","OnStart");
        //Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        if(mAuth.getCurrentUser() != null){
            Log.e("bookingActivity","OnStart inside if");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mAuth = FirebaseAuth.getInstance();
        book_here = findViewById(R.id.booking);
        book_here.setOnClickListener(this);



        nameTextInput = findViewById(R.id.booking_name_text_input);
        nameEditText = findViewById(R.id.booking_name_edit_text);

        emailTextInput = findViewById(R.id.booking_email_text_input);
        emailEditText = findViewById(R.id.booking_email_edit_text);

        surnameTextInput = findViewById(R.id.booking_surname_text_input);
        surnameEditText = findViewById(R.id.booking_surname_edit_text);

        ageTextInput = findViewById(R.id.booking_age_text_input);
        ageEditText = findViewById(R.id.booking_age_edit_text);

        genderTextInput = findViewById(R.id.booking_gender_text_input);
        genderEditText = findViewById(R.id.booking_gender_edit_text);

        apdateTextInput = findViewById(R.id.booking_apdate_text_input);
        apdateEditText = findViewById(R.id.booking_apdate_edit_text);


    }

    public void bookingWithEmail(String email,String name,String surname,String gender,String apdate,String age){
        //Invalid email will show Error
        boolean validEmail = isEmailValid(email);
        boolean validapdate = isapdateValid(apdate);
        //If any thing is Invalid it will return
        if(!(validEmail && validapdate)){
            return;
        }
        mAuth.bookWithEmailAndapdate(email, apdate)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                            //updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booking:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.booking:
                signInWithEmail(emailEditText.getText().toString(), passwordEditText.getText().toString());
                break;
            case R.id.login_forget_password:
                break;
        }
    }

    /**
     * Checks if password is valid or not
     *
     * @param text
     * @return boolean
     */
    private boolean isPasswordValid(@NonNull String text) {
        if (text.length() > 7 && text.length() < 14) {
            passwordTextInput.setError(null);
            return true;
        } else {
            passwordTextInput.setError(getText(R.string.dr_error_length_password));
            return false;
        }
    }

    /**
     * Checks if Email Valid or Not
     *
     * @param text
     * @return boolean
     */
    private boolean isEmailValid(@NonNull String text) {
        if (text.toString().isEmpty()) {
            //error
            emailTextInput.setError("Email Can't be Empty");
            return false;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            //success
            return true;
        } else {
            //error enter valid email
            emailTextInput.setError("Invalid Email!");
            return false;
        }
    }

}
