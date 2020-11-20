package zm.knoxdev.buzzbuzzstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import zm.knoxdev.buzzbuzzstore.authentication.PhoneNumberActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //get current user;
        mCurrentUser = mAuth.getCurrentUser();

    }

    //Current user intent code.
    /*//check if user is logged in so we do not have users login in without authentication.
    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrentUser == null){

            Intent loginIntent =  new Intent(MainActivity.this, PhoneNumberActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();

        }

    }*/

    @Override
    public void onBackPressed() {
        //This line promotes the user to exit the App by viewing an alert dialog on the screen.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Exit?");
        alertDialogBuilder.setMessage("Are you sure you want to exit Buzz Buzz Store?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> finish());
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> Toast.makeText(MainActivity.this, "Thanks for staying.", Toast.LENGTH_SHORT).show());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        //Exit alert Dialog Button colors
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.exitButtonColor));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.exitButtonColor));

    }
}