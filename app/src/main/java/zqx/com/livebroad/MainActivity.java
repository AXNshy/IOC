package zqx.com.livebroad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zqx.com.ioc.ContentViewInject;
import zqx.com.ioc.OnClick;
import zqx.com.ioc.Shy;
import zqx.com.ioc.ViewInject;

@ContentViewInject(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.sample_text)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shy.bind(this);
        // Example of a call to a native method
        tv.setText(stringFromJNI());
    }
    @OnClick({R.id.sample_text})
    protected void onClick(View view){
        Toast.makeText(this, "点击text", Toast.LENGTH_SHORT).show();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
