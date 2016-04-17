package at.mkritzl.dezsys11.dezsys11.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import at.mkritzl.dezsys11.dezsys11.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirect(LoginActivity.class);
            }
        });
    }

    private void redirect(Class<? extends Activity> clazz) {
        finish();
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}