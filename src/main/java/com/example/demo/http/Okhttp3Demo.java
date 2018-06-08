/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.http;

/**
 * @Author: wangyp
 * @Description:
 * @Date: Created in 10:07 2018/6/7
 * @Modified By:
 */

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyp
 * @version Okhttp3Demo.java, v 0.1 2018-06-07 10:07
 */
@Slf4j
public class Okhttp3Demo {

    /**
     * Request(请求)
     * 每一个Http请求中都应该包含一个URL，一个GET或POST方法以及header或其他参数，当然还可以含特定内容类型的数据流。
     *
     * Responses(响应)
     * 响应则包含一个回复代码（200代表成功，404代表未找到），header和定制可选的body
     * @param args
     * @throws IOException
     */

    /**
     * MediaType用于描述Http请求和响应体的内容类型，也就是Content-Type。
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final static OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws IOException {
        /** 创建OkHttpClient实例  创建一个默认配置OkHttpClient,可以使用默认的构造函数*/
        /** 也可以采用 new OKHttpClient.Builder()方法来一步步实例化okHttpClinet */
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(3000, TimeUnit.SECONDS).build();
        /** 也可以使用 newBuilder()来实例化一个okHttpClient*/
        OkHttpClient okHttpClient1 = client.newBuilder().build();


        /**
         * GET请求
         */
        String url = "https://www.baidu.com";
        /**
         * Request类，可以发现它代表一个Http请求，需要注意的是Request一旦build()之后，便不可修改。
         * 底层实现上默认是get请求，
         */
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().toString());
        System.out.println();
        System.out.println(post(url, "OKHttp3"));
        System.out.println();
        System.out.println(postKeyValue(url, "OKHttp3"));
        System.out.println();

    }


    /**
     * POST请求
     */
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().toString();
    }


    /**
     * 利用post请求发送键值对的给服务器
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postKeyValue(String url, String json) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("planform", "java")
                .add("name", "wangyp")
                .add("grader", "max")
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().toString();
    }

    /**
     * get方式异步请求
     */
    public static String GetRsy(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = client.newCall(request);
        /**
         * 通过 call.enqueue(Callback)方法来提交异步请求
         * 异步发起的请求会被加入到 Dispatcher 中的 runningAsyncCalls双端队列中通过线程池来执行。
         */
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info("onResponse: " + response.body().string());
            }
        });
        Response response = call.execute();
        return response.body().toString();

    }

    /**
     * 同步请求  方法里面的第二个参数是一个接口，
     * 可以自行写一个接口目的是实现接口直接获取请求后的数据
     */
    public void getOkHttp(String url, String imp) {
        //调用ok的get请求
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        final Call call = client.newCall(request);
        //同步execute
        //同步请求
        //同步是耗时的
        //同步execute需要开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        //调用者只需要实现provide方法就能拿到这个String了
                        System.out.println(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get拼接传值方法
     * @param url ?username=xxxx&password=xxx
     */
    public void getOkHttp(String url, Map<String, String> map, Callback callback) {
        StringBuffer sb = new StringBuffer();
        String string = "";
        String result = "";
        //当用户传入null或者传了一个空的map
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (sb == null) {
//                    sb = new StringBuffer ();
                    sb.append("?");
                } else {
                    //拼接好的网站去掉最后一个“&”符号
                    sb.append("&");
                }
                sb.append(entry.getKey() + "=" + entry.getValue());
            }
        }
        if (sb.toString() != null) {
            string = sb.toString();
            log.info("", string);
            result = url + string;
            log.info("", result);
        }
        Request request = new Request.Builder()
                .get() //声明我是get请求,如果不写默认就是get
                .url(string == "" ? url : result)//声明网站访问的网址
                .build();//创建Request
        Call call = client.newCall(request);
        //同步execute,异步enqueue
        //这里的同步是耗时的
        //而且OK 也没有为我们开启子线程‘
        // 如果你用同步方法的话，需要开启子线程
        call.enqueue(callback);
    }

    public void postOkhttp(String url, Map<String, String> map, Callback callBack) {
        //上传文字格式 数据的传输，区别于多媒体输出
        FormBody.Builder formbody = new FormBody.Builder();
        if (map != null && !map.isEmpty()) {
            //上传参数
            for (String key : map.keySet()) {
                formbody.add(key, map.get(key));
            }
            //创建请求体
            FormBody body = formbody.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)//请求体
                    .build();
            Call call = client.newCall(request);
            //异步请求方式
            call.enqueue(callBack);
        } else {
            //创建请求体
            FormBody body = formbody.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            //异步请求方式
            call.enqueue(callBack);
        }
    }

    /**
     * 文件和参数请求方法
     * MultipartBody：用来提交包涵文件的参数
     *
     * @param path    :路径
     * @param map     ：普通参数
     * @param img     ：提交文件的关键字
     * @param imgPath ：提交文件的路径
     */
    public void postFileOkhttp(String path, HashMap<String, String> map, String img, String imgPath, Callback callBack) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        if (map != null && !map.isEmpty()) {
            //上传参数
            for (String key : map.keySet()) {
                requestBody.addFormDataPart(key, map.get(key));
            }
            File file = new File(imgPath);
            requestBody.addFormDataPart(img, file.getPath()
                    , RequestBody.create(MediaType.parse("image/png"), file));
            Request request = new Request.Builder()
                    .post(requestBody.build())
                    .url(path)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        } else {
            File file = new File(imgPath);
            requestBody.addFormDataPart(img, file.getPath()
                    , RequestBody.create(MediaType.parse("image/png"), file));
            Request request = new Request.Builder()
                    .post(requestBody.build())
                    .url(path)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        }
    }

    /**
     * 多文件和参数的请求方法
     * MultipartBody：用来提交包涵文件的参数
     * 多文件上传
     */
    public void postFileOkhttp2(String path, HashMap<String, String> map, String img, List<String> imgPths, Callback callBack) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        if (map != null && !map.isEmpty()) {

            //上传参数
            for (String key : map.keySet()) {
                requestBody.addFormDataPart(key, map.get(key));
            }
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            if (imgPths != null) {
                for (String string : imgPths) {
                    File file = new File(string);
                    requestBody.addFormDataPart(img, file.getName(), RequestBody.create(JSON, file));
                }
            }

            Request request = new Request.Builder()
                    .post(requestBody.build())
                    .url(path)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        } else {
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            if (imgPths != null) {
                for (String string : imgPths) {
                    File file = new File(string);
                    requestBody.addFormDataPart(img, file.getName(), RequestBody.create(JSON, file));
                }
            }

            Request request = new Request.Builder()
                    .post(requestBody.build())
                    .url(path)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        }
    }

}

