package zm.knoxdev.buzzbuzzstore.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import zm.knoxdev.buzzbuzzstore.R;



public class VerifyPhoneNumberActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    private EditText mOTPText;
    private Button mVerifyBtn;

    private ProgressBar mOTPProgress;

    private TextView mOTPFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mOTPFeedback = findViewById(R.id.verify_phone_number_textview);

        mVerifyBtn = findViewById(R.id.verify_button);

        mOTPProgress = findViewById(R.id.sending_otp_progress_bar);

        mOTPText = findViewById(R.id.verify_phone_number_edittext);

        TextView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> startActivity(new Intent(VerifyPhoneNumberActivity.this, PhoneNumberActivity.class)));

    }
}