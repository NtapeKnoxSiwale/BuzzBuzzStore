package zm.knoxdev.buzzbuzzstore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import zm.knoxdev.buzzbuzzstore.authentication.PhoneNumberActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, PhoneNumberActivity.class));
        finish();

    }
}
