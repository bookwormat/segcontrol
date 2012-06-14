package at.bookworm.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        RadioGroup group1 = (RadioGroup) this.findViewById(R.id.buttongroup1);
        group1.check(R.id.option1);

        RadioGroup group2 = (RadioGroup) this.findViewById(R.id.buttongroup2);
        group2.check(R.id.option2);
    }
}