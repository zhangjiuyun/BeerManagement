package com.example.beermanagement;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int max = 10;//Max数
    int count = max;//今の数
    private Vibrator mVibrator;//振動プロジェクター作る

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        いろいろの初期化？
        main関数みたいな感じ？
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);//振動サービスをもらえる
        Toast.makeText(MainActivity.this, "data read successful", Toast.LENGTH_LONG).show();//アンドロイドのi/oちょっとわからないなんですが、

        TextView txv = findViewById(R.id.max);
        txv.setText(String.valueOf(max));
    }

    public void setCount(int a) {//右上の只今在庫数
        TextView maax = findViewById(R.id.count);
        maax.setText(String.valueOf(a));
    }


    public void setMax(View v) {//最大値をセットする
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("input Beer Max Value").setView(editText);
        inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                try {
                    max = Integer.parseInt(editText.getText().toString());
                    TextView txv = findViewById(R.id.max);
                    txv.setText(String.valueOf(max));
                    count = max;
                    setCount(max);
                    int temp = max - count;
                    TextView txv1 = findViewById(R.id.number);
                    txv1.setText(String.valueOf(temp));
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "input error", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    public void drink(View v) {
        if (count != 0) {
            mVibrator.vibrate(new long[]{100, 200, 100, 400}, -1);//振動する 200 400
            TextView txv;
            txv = findViewById(R.id.number);

            count--;
            int temp = max - count;
            txv.setText(String.valueOf(temp));

            setCount(count);

            //     Toast.makeText(MainActivity.this, "beer ++ successful", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(MainActivity.this, "ビールもう一杯もうない！", Toast.LENGTH_SHORT).show();
            hajyu();
        }
    }

    public void hajyu() {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.drawable.beer).setTitle("OneClick order").setMessage("ワンクリックで発注しますか。").setPositiveButton(
                "発注する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reset(); //to--do
                        Toast.makeText(MainActivity.this, "発注成功しました！", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        normalDialog.setNegativeButton("キャンセル",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //to--do
                    }
                });
        normalDialog.show();
    }

    public void reset(View v) {
        hajyu();
    }

    public void reset() {
        mVibrator.vibrate(new long[]{100, 200, 100, 1000}, -1);//振動する 200 1000
        TextView txv;
        txv = findViewById(R.id.number);
        count = max;
        setCount(count);
        txv.setText("0");
    }

   /* public void reflash(){
        TextView txv1 = findViewById(R.id.number);

        txv1.setText(String.valueOf(max-count));
        TextView txv2 = findViewById(R.id.count);
        txv2.setText(String.valueOf(count));
    }
    */
}
