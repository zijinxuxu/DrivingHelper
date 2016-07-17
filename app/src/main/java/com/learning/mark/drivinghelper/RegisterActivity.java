package com.learning.mark.drivinghelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

    private EditText edname1;
    private EditText edpassword1;
    private Button btregister1;
    SQLiteDatabase db;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edname1 = (EditText) findViewById(R.id.edname1);
        edpassword1 = (EditText) findViewById(R.id.edpassword1);
        btregister1 = (Button) findViewById(R.id.btregister1);
        btregister1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = edname1.getText().toString();
                String password = edpassword1.getText().toString();
                if (!(name.length()<4||password.length()<4)) {
                    if (addUser(name, password)) {  //判断是否重复！！失败。
                        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                // 跳转到登录界面
                                Intent in = new Intent();
                                in.setClass(RegisterActivity.this,
                                        MainActivity.class);
                                startActivity(in);
                                // 销毁当前activity
                                RegisterActivity.this.onDestroy();
                            }
                        };
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("    注册成功").setMessage("点击确定转到登录页面")
                                .setPositiveButton("确定", ss).show();

                    } else {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("    注册失败").setMessage("数据库写入帐号密码失败")
                                .setPositiveButton("确定", null).show();
                    }
                } else {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("帐号密码格式不符").setMessage("帐号密码长度不能小于4")
                            .setPositiveButton("确定", null).show();
                }

            }
        });

    }

    // 添加用户
    public Boolean addUser(String name, String password) {
        String str = "insert into tb_user values(?,?) ";
        MainActivity main = new MainActivity();
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
                + "/test.dbs", null);
        main.db = db;
        try {
            db.execSQL(str, new String[] { name, password });
            return true;
        } catch (Exception e) {
            main.createDb();
        }
        return false;
    }

}
