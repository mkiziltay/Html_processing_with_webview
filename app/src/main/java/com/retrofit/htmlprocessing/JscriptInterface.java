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
        }


        public static String getWebData(String data){
            data = htmlData;
            Log.i("datas","Web Data MyJsInter : =\n"+data);
            return data;
        }

    }
}
