package com.pzj.ioc_demo;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.pzj.ioc.library.annotations.InjectLayout;
import com.pzj.ioc.library.annotations.InjectView;
import com.pzj.ioc.library.annotations.OnClick;
import com.pzj.ioc.library.annotations.OnLongClick;

@InjectLayout(R.layout.activity_main) // 注入布局，代替setContentView(R.layout.activity_main);
public class MainActivity extends BaseActivity {

  @InjectView(R.id.btn) // 注入控件，代替findViewById(R.id.btn);
  private Button btn;

  @OnClick(R.id.btn)
  public void btnClick(View view) {
    Toast.makeText(this, "按钮点击了", Toast.LENGTH_SHORT).show();
  }

  @OnLongClick(R.id.btn)
  public boolean btnLongClick(View view) {
    Toast.makeText(this, "按钮长按了", Toast.LENGTH_SHORT).show();
    return true; // 如果return false，则还会执行按钮的点击事件
  }
}
