package com.retrofit.htmlprocessing;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JscriptInterface {

    public static void getSources(Context context, String url, WebView wb) {
        WebViewClient yourWebClient = new WebViewClient() {
/*
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
           }
*/
            @Override
            public void onPageFinished(WebView view, String url) {
                wb.loadUrl("javascript:HtmlViewer.showHTML" +
                        "('<'+document.getElementsByTagName('body')[0].innerHTML+'<');");
            }
        };

        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setSupportZoom(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.setWebViewClient(yourWebClient);
        wb.loadUrl(url);
        wb.addJavascriptInterface(new MyJavaScriptInterface(context), "HtmlViewer"); // Tag of JsInterface class

    }

    static class MyJavaScriptInterface {

        static String htmlData;
        private final Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            Log.i("datas","Full HTML ="+html);
            htmlData = html;
            /*
            Document doc = Jsoup.parse(html);
            Elements title = doc.select("body");
            title = title.replaceAll("");
            Log.i("htmlx","HTML body ="+title.toString());
            //Log.i("htmlx","test1"+title.text());
            String body[] = title.text().split(",");
            String titleArray[] = {"USDTRY", "EURTRY", "AUDTRY", "GBPTRY", "JPYTRY", "CHFTRY", "CADTRY", "NOKTRY", "EURUSD", "EURGBP", "EURJPY", "GBPUSD", "USDAUD", "USDJPY", "USDCHF", "USDCAD", "USDCNY"};
            for(int i=0;i<body.length;i++){
                String newTitle = null; int j=i;
                if (j>9) { j=i%10;
                    newTitle = titleArray[j];
                }else {
                    newTitle = titleArray[j];
                }
                String newBody = body[i].replaceAll("\"","").replaceAll("]","").replace("[{","").replace("{","").replace("}","");
                String values = newBody.split(":")[1];
                Log.i("htmlx", newTitle +"----" + values);
                //Log.i("htmlx", body.length+"------"+j+ "----" + i);
            }
             */
        }


        public static String getWebData(String data){
            data = htmlData;
            Log.i("datas","Web Data MyJsInter : =\n"+data);
            return data;
        }

    }
}
