package util;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Toast;

public class WebDownLoadListener {
    public static Context mContext;
    public static Activity mActivity;
    public WebDownLoadListener(Context context,Activity activity){
        mContext=context;
        mActivity=activity;
    }
    public DownloadListener mDownloadListener=new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            try {
                //String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                //fileName = URLEncoder.encode(fileName, "EUC-KR").replace("+", "%20");
                //fileName = URLDecoder.decode(fileName, "UTF-8");


                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimetype);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Do wnloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));

                DownloadManager dm = (DownloadManager) mActivity.getSystemService(mActivity.DOWNLOAD_SERVICE);
                dm.enqueue(request);

                Toast.makeText(mContext, "다운로드 시작..", Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                if (ContextCompat.checkSelfPermission(mContext,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(mContext, "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                110);
                    } else {
                        Toast.makeText(mContext, "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                110);
                    }
                }
            }
        }
    };


}
