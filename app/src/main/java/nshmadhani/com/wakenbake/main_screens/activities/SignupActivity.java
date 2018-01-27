package nshmadhani.com.wakenbake.main_screens.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nispok.snackbar.Snackbar;

import mehdi.sakout.fancybuttons.FancyButton;
import nshmadhani.com.wakenbake.R;


public class SignupActivity extends AppCompatActivity {

    public static final String TAG = SignupActivity.class.getSimpleName();
    public static final int RC_SIGN_IN = 2;
    public EditText mSignupNameEditText;
    public EditText mSignupEmailEditText;
    public EditText mSignupPasswordEditText;
    public FancyButton mSignupSignupButton;
    public TextView mSignupLoginLinkTextView;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Checking if the user is connected to internet.
        try {

            initialLayout(); // Setting the layout of the signup screen

            if (isNetworkConnected()) {
                mAuth = FirebaseAuth.getInstance(); // Creating a instance of Firebase object

                //Clicking on Signup Button
                mSignupSignupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignupEmailEditText.setEnabled(false);
                        mSignupPasswordEditText.setEnabled(false);
                        //Getting values of the email and password fields
                        String email = mSignupEmailEditText.getText().toString();
                        String password = mSignupPasswordEditText.getText().toString();

                        //Checking the fields
                        if (!email.equals("") && !password.equals("")) {
                            try {
                                createAccount(email, password); // Creating an account on the Firebase
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (email.equals("") && !password.equals("")) {
                            Toast.makeText(SignupActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                        } else if (!email.equals("") && password.equals("")) {
                            Toast.makeText(SignupActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Enter both email and password", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //Clicking on the Signup Link
                mSignupLoginLinkTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Redirecting to the Signup Activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                mSignupEmailEditText.setEnabled(true);
                mSignupPasswordEditText.setEnabled(true);

                Snackbar.with(getApplicationContext()).text("Please check your internet connection").show(SignupActivity.this);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void createAccount(String email, String password) throws Exception{
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "createUserWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void initialLayout () throws Exception {
        mSignupNameEditText = findViewById(R.id.mSignupNameEditText);
        mSignupEmailEditText = findViewById(R.id.mSignupEmailEditText);
        mSignupPasswordEditText = findViewById(R.id.mSignupPasswordEditText);
        mSignupSignupButton = findViewById(R.id.mSignupSignupButton);
        mSignupLoginLinkTextView = findViewById(R.id.mSignupLoginLinkTextView);
    }

    private boolean isNetworkConnected() throws Exception {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}