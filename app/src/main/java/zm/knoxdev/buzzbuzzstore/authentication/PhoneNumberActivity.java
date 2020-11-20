package zm.knoxdev.buzzbuzzstore.authentication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import zm.knoxdev.buzzbuzzstore.MainActivity;
import zm.knoxdev.buzzbuzzstore.R;

public class PhoneNumberActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mCountryCode;

    private Button mSendPhoneNumberButton;

    private TextView mPhoneNumber;
    private TextView mPhoneNumberText;
    private TextView mPhoneNumberFeedbackText;
    private TextView mSkipButton;

    private ProgressBar mSendPhoneNumberProgressbar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        /*We use the following for the firebase phone number authentication.
         * mCountryCode which is for our Zambian Country code which should be +260.
         * mPhoneNumber which is for our Zambian Phone number.
         * mPhoneNumberFeedbackText is a text Returned when an error has occurred while entering the phone number.
         * we use mAuth and mCurrentUser to check if the user is logged in.
         * */
        mCountryCode = "26";
        //Text views.
        mPhoneNumberFeedbackText = findViewById(R.id.phone_number_error_text);
        mPhoneNumberText = findViewById(R.id.phone_number_textview);

        //Edit text field
        mPhoneNumber = findViewById(R.id.phone_number_edittext);
        //Send Text Message Button
        mSendPhoneNumberButton = findViewById(R.id.send_text_message);
        mSendPhoneNumberButton.setOnClickListener(v -> {
            String country_code = mCountryCode;
            String phone_number = mPhoneNumber.getText().toString();

            String complete_phone_number = "+" + country_code + phone_number;

            if (phone_number.isEmpty()) {

                //Show message on screen when input is empty.
                mPhoneNumberFeedbackText.setText(R.string.phone_number_error_text);
                mPhoneNumberFeedbackText.setVisibility(View.VISIBLE);
                mPhoneNumberText.setVisibility(View.INVISIBLE);

            } else {

                mSendPhoneNumberProgressbar.setVisibility(View.VISIBLE);
                mPhoneNumberFeedbackText.setVisibility(View.INVISIBLE);
                mSendPhoneNumberButton.setEnabled(false);
                mSkipButton.setEnabled(false);

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(complete_phone_number)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(PhoneNumberActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Intent optIntents = new Intent(PhoneNumberActivity.this, VerifyPhoneNumberActivity.class);
                optIntents.putExtra("AuthCredentials", s);
                startActivity(optIntents);

            }
        };

        mSendPhoneNumberProgressbar = findViewById(R.id.sending_phone_number_progress_bar);

        //Checking if a user is logged in.
        mAuth = FirebaseAuth.getInstance();
        //get current user;
        mCurrentUser = mAuth.getCurrentUser();


        /*This is a skip button that will redirect users to the MainActivity where the products are located.*/
        mSkipButton = findViewById(R.id.skip_button);
        mSkipButton.setOnClickListener(v ->

        {
            startActivity(new Intent(PhoneNumberActivity.this, MainActivity.class));
            finish();

        });
    }

    //check if user is logged in so we do not have users login in without authentication.
    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(PhoneNumberActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}