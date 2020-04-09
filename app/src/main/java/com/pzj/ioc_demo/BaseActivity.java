package com.pzj.ioc_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.pzj.ioc.library.InjectManager;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 17:49
 * @Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 帮助子类进行：布局、控件、事件的注入
    InjectManager.inject(this);
  }

}
