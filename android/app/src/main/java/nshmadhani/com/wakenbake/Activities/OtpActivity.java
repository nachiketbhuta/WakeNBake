package nshmadhani.com.wakenbake.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import nshmadhani.com.wakenbake.Interfaces.IConnectivityReceiver;
import nshmadhani.com.wakenbake.R;

import static java.util.concurrent.TimeUnit.SECONDS;

public class OtpActivity extends AppCompatActivity implements IConnectivityReceiver {

    private static final String TAG = OtpActivity.class.getSimpleName();
    private Pinview pinView;
    private FirebaseAuth mAuth;
    private EditText phoneNumberEditText;
    private TextView otpTextView;
    private Button confirmButton;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public ProgressDialog progressDialog;
    public String phoneNumber;
    public PhoneAuthProvider.ForceResendingToken mResendToken;
    public String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

       if (isNetworkAvailable()) {
           //Phone Number
           phoneNumberEditText = findViewById(R.id.otpPhoneNumber);
           progressDialog = new ProgressDialog(this);
           //OTP
           otpTextView = findViewById(R.id.otpTextView);

           mAuth = FirebaseAuth.getInstance();

           pinView = findViewById(R.id.otpPinView);
           pinView.setInputType(Pinview.InputType.NUMBER);

           //Initializing the Confirm Button
           confirmButton = findViewById(R.id.confirmButton);

           confirmButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   progressDialog.dismiss();
                   progressDialog.setMessage("Sending OTP.....");
                   progressDialog.show();
                   phoneNumber = phoneNumberEditText.getText().toString();
                   PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           "+91" + phoneNumber,             // Phone number to verify
                           60,                     // Timeout duration
                           SECONDS,                 // Unit of timeout
                           OtpActivity.this, // Activity (for callback binding)
                           mCallbacks);
               }
           });

           mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

               @Override
               public void onVerificationCompleted(PhoneAuthCredential credential) {

                   progressDialog.dismiss();
                   Toast.makeText(OtpActivity.this, "OTP Verified.", Toast.LENGTH_SHORT).show();
                   signInWithPhoneAuthCredential(credential);
               }

               @Override
               public void onVerificationFailed(FirebaseException e) {
                   progressDialog.dismiss();
                   Log.d(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
                   Toast.makeText(OtpActivity.this, "Error in Verification", Toast.LENGTH_SHORT).show();

                   if (e instanceof FirebaseAuthInvalidCredentialsException) {
                       Toast.makeText(OtpActivity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                   } else if (e instanceof FirebaseTooManyRequestsException) {
                       Toast.makeText(OtpActivity.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onCodeSent(String verificationId,
                                      PhoneAuthProvider.ForceResendingToken token) {
                   progressDialog.dismiss();
                   Toast.makeText(OtpActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                   phoneNumberEditText.setVisibility(View.INVISIBLE);
                   otpTextView.setVisibility(View.VISIBLE);
                   pinView.setVisibility(View.VISIBLE);
                   confirmButton.setText("Verify OTP");
                   mVerificationId = verificationId;
                   mResendToken = token;
               }
           };
       }
       else {
            new AlertDialog.Builder(this)
                   .setTitle("No Internet Connection")
                   .setMessage("Please check your internet connection")
                   .setPositiveButton("Retry",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   isNetworkAvailable();
                               }
                           })

                   .setNegativeButton("Cancel",
                           new android.content.DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                    finish();
                               }
                           }).create();
       }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        progressDialog.dismiss();
        progressDialog.setMessage("Signing you in...");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(OtpActivity.this, "Signing you in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OtpActivity.this, LocationActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OtpActivity.this, "Error in signing you!", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpActivity.this, "Firebase Exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        boolean networkAvailable = false;

        if (networkInfo != null && networkInfo.isConnected())
            networkAvailable = true;

        return networkAvailable;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
