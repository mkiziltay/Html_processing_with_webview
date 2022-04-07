package com.retrofit.htmlprocessing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import com.github.mikephil.charting.data.BarEntry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class MainActivity extends AppCompatActivity {
    WebView wb;
    Button drawChart;
    static ArrayList<BarEntry> barEntries;
    String url = "https://mkiziltay.epizy.com/tradingdata.php?date=07.03.2022";
    static String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        define();
        listeners();
        //Load data from JsInterface class
        JscriptInterface.getSources(this,url,wb);
        callService();
        Log.i("datas","Web Data Html : =\n"+data);
        //callBack();
    }

    private void define() {
        drawChart = findViewById(R.id.barButton);
        wb = findViewById(R.id.webv);
        barEntries = new ArrayList<>();
    }

    private void listeners() {
        drawChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BarChartActivity.class));
            }
        });
    }

    public static void callBack(){
        //data = JscriptInterface.MyJavaScriptInterface.getWebData(data);
        Log.i("datas","Web Data callBack : =\n"+data);
        data = data.replaceAll("[\\[\\]\\<\\\"\\{\\}]", "");
        String array [] = data.split(",");
        List<String> titles = Arrays.asList("USDTRY", "EURTRY", "AUDTRY", "GBPTRY", "JPYTRY", "CHFTRY", "CADTRY", "NOKTRY", "EURUSD", "EURGBP", "EURJPY", "GBPUSD", "USDAUD", "USDJPY", "USDCHF", "USDCAD", "USDCNY");
        ArrayList<Double> currencies = new ArrayList<>(); // contains currencies
        ArrayList<Double> balance = new ArrayList<>(); // contains remains each other currencies
        ArrayList<Double> percentages = new ArrayList<>(); // contains percentages which added minimum value.
        DecimalFormat decimalFormat =  new DecimalFormat("#0.0000");
        double minimum=0.0;

        // split titles and add Json data list to list
        for(String data : array){
             String current = data.split(":")[1];
             currencies.add(Double.valueOf(current));
        }
        // remove currency values each other and add list
        for(int i=0;i<currencies.size()/2;i++){
            // remove next value from prev value
            double rem = currencies.get(i+17)-currencies.get(i);
            // format double values 0,0000
            String temp = decimalFormat.format(rem);
            rem = Double.valueOf(temp);
            rem = 100 * rem / currencies.get(i);
            temp = decimalFormat.format(rem);
            balance.add(Double.valueOf(temp));

            if(balance.get(i)<minimum){
                minimum =balance.get(i);
            }
        }
        //modify values bigger than zero
        for(double val : balance){
            String temp = decimalFormat.format(val+(0 - minimum));
            percentages.add(Double.valueOf(temp));
        }
        //add titles and values to barEnry list
        for(int i=0;i<titles.size();i++){
            //System.out.println("**"+titles.get(i)+" : "+percentages.get(i));
            barEntries.add(new BarEntry( 5+i ,percentages.get(i).floatValue() ));
        }
        Log.i("datas","Data per="+currencies.toString());
        Log.i("datas","Data rem="+balance.toString());
        Log.i("datas","Data per="+percentages.toString());
    }

    public void callService(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data = JscriptInterface.MyJavaScriptInterface.getWebData(data);
                        callBack();
                        startActivity(new Intent(getApplicationContext(),BarChartActivity.class));
                    }
                },5000);
            }
        });
    }

}
