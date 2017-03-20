package com.example.loginandpaytools.Utils;

import android.content.Context;
import android.widget.Toast;

import com.example.loginandpaytools.Callback.JsonCallback;
import com.example.loginandpaytools.R;
import com.example.loginandpaytools.bean.JsonBean;
import com.example.loginandpaytools.bean.SimpleResponse;
import com.example.loginandpaytools.ui.CustomProgressDialog;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.loginandpaytools.Support.Configuration.API_HOST;
import static com.example.loginandpaytools.Support.Configuration.CHANNEL_ID;
import static com.example.loginandpaytools.Support.Configuration.DEVICE_ID;
import static com.example.loginandpaytools.Support.Configuration.GAME_ID;
import static com.example.loginandpaytools.Support.Configuration.PACKAGE_NAME;
import static com.example.loginandpaytools.Support.Configuration.ROLE_ID;
import static com.example.loginandpaytools.Support.Configuration.ROLE_LEVEL;
import static com.example.loginandpaytools.Support.Configuration.ROLE_NAME;
import static com.example.loginandpaytools.Support.Configuration.SERVER_ID;
import static com.example.loginandpaytools.Support.Configuration.TOKEN;
import static com.example.loginandpaytools.Support.Configuration.UID;
import static com.example.loginandpaytools.Utils.SignUtil.getSign;



/**
 * Created by jerry on 17-2-19.
 */

public class HttpUtil {

    private static CustomProgressDialog mCustomProgressDialog;

    /**
     * 激活接口
     */
    public static void activeInterface(Context context, final HttpCallbackListener listener) {
        if(!NetworkUtil.isNetworkConnected(context)){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        SortedMap<String, Object> parameters = new TreeMap<>();
        parameters.put("device_id", DEVICE_ID);
        parameters.put("game_id", GAME_ID);
        parameters.put("channel_id", CHANNEL_ID);
        parameters.put("package_name", PACKAGE_NAME);
        String sign = getSign(parameters);

        HttpParams params = new HttpParams();
        params.put("device_id", DEVICE_ID);
        params.put("game_id", GAME_ID);
        params.put("channel_id", CHANNEL_ID);
        params.put("package_name", PACKAGE_NAME);
        params.put("sign", sign);

        OkGo.post(API_HOST + "/?ct=user&ac=active")
                .tag(context)
                .params(params)
                .execute(new JsonCallback<JsonBean<Void>>(context) {
                    @Override
                    public JsonBean<Void> convertSuccess(Response response) throws Exception {

                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];


                        if (!(type instanceof ParameterizedType))
                            throw new IllegalStateException("没有填写泛型参数");
                        Type rawType = ((ParameterizedType) type).getRawType();
                        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        if (typeArgument == Void.class) {
                            //无数据类型,表示没有data数据的情况
                            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                            response.close();
                            //noinspection unchecked
                            return (JsonBean<Void>) simpleResponse.toJsonBean();
                        } else if (rawType == JsonBean.class) {
                            //有数据类型，表示有data
                            JsonBean jsonBean = Convert.fromJson(jsonReader, type);
                            response.close();
                            int state = jsonBean.state;
                            if (state == 1) {
                                //noinspection unchecked
                                return (JsonBean<Void>) jsonBean;
                            } else if (state == 0) {
                                throw new IllegalStateException(jsonBean.msg);
                            }
                        } else {
                            response.close();
                            throw new IllegalStateException("基类错误无法解析!");
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(JsonBean<Void> voidJsonBean, Call call, Response response) {
                        super.onSuccess(voidJsonBean, call, response);
                        listener.onFinish();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.onError(e);
                    }

                });
    }

    /**
     * 获取随机用户名密码
     */
    public static void getRandomNameAndPassword(final Context context, final HttpCallbackListener listener) {
        if(!NetworkUtil.isNetworkConnected(context)){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        SortedMap<String, Object> parameters = new TreeMap<>();
        parameters.put("device_id", DEVICE_ID);
        String sign = getSign(parameters);

        HttpParams params = new HttpParams();
        params.put("device_id", DEVICE_ID);
        params.put("sign", sign);
        OkGo.post(API_HOST + "/?ct=user&ac=init")
                .tag(context)
                .params(params)
                .execute(new JsonCallback<JsonBean<JsonBean.RandomNameAndPassword>>(context) {
                    @Override
                    public JsonBean<JsonBean.RandomNameAndPassword> convertSuccess(Response response) throws Exception {
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];

                        if (!(type instanceof ParameterizedType))
                            throw new IllegalStateException("没有填写泛型参数");
                        Type rawType = ((ParameterizedType) type).getRawType();
                        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        if (typeArgument == Void.class) {
                            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                            response.close();
                            //noinspection unchecked
                            return (JsonBean<JsonBean.RandomNameAndPassword>) simpleResponse.toJsonBean();
                        } else if (rawType == JsonBean.class) {
                            //有data
                            JsonBean jsonBean = Convert.fromJson(jsonReader, type);
                            response.close();
                            int state = jsonBean.state;

                            if (state == 1) {
                                //noinspection unchecked
                                return (JsonBean<JsonBean.RandomNameAndPassword>) jsonBean;
                            } else if (state == 0) {
                                throw new IllegalStateException(jsonBean.msg);
                            }
                        } else {
                            response.close();
                            throw new IllegalStateException("基类错误无法解析!");
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(JsonBean<JsonBean.RandomNameAndPassword> randomNameAndPasswordJsonBean, Call call, Response response) {
                        super.onSuccess(randomNameAndPasswordJsonBean, call, response);
                        listener.onFinish(randomNameAndPasswordJsonBean.data.username, randomNameAndPasswordJsonBean.data.password);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.onError(e);
                    }
                });
    }

    /**
     * 发送登陆请求
     */
    public static void Login(final String user, final String password, final Context context, final HttpCallbackListener listener) {
        if(!NetworkUtil.isNetworkConnected(context)){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        SortedMap<String, Object> parameters = new TreeMap<>();
        parameters.put("device_id", DEVICE_ID);
        parameters.put("game_id", GAME_ID);
        parameters.put("channel_id", CHANNEL_ID);
        parameters.put("package_name", PACKAGE_NAME);
        parameters.put("user", user);
        parameters.put("password", password);
        String sign = getSign(parameters);

        HttpParams params = new HttpParams();
        params.put("device_id", DEVICE_ID);
        params.put("game_id", GAME_ID);
        params.put("channel_id", CHANNEL_ID);
        params.put("package_name", PACKAGE_NAME);
        params.put("user", user);
        params.put("password", password);
        params.put("sign", sign);
        OkGo.post(API_HOST + "/?ct=user&ac=login")
                .tag(context)
                .params(params)
                .execute(new JsonCallback<JsonBean<JsonBean.Login>>(context) {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mCustomProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
                        mCustomProgressDialog.show();
                    }

                    @Override
                    public JsonBean<JsonBean.Login> convertSuccess(Response response) throws Exception {
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];


                        if (!(type instanceof ParameterizedType))
                            throw new IllegalStateException("没有填写泛型参数");
                        Type rawType = ((ParameterizedType) type).getRawType();
                        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        if (typeArgument == Void.class) {
                            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                            response.close();
                            //noinspection unchecked
                            return (JsonBean<JsonBean.Login>) simpleResponse.toJsonBean();
                        } else if (rawType == JsonBean.class) {
                            //有数据类型，表示有data
                            JsonBean jsonBean = Convert.fromJson(jsonReader, type);
                            response.close();
                            int state = jsonBean.state;

                            if (state == 1) {
                                //noinspection unchecked
                                return (JsonBean<JsonBean.Login>) jsonBean;
                            } else if (state == 0) {
                                throw new IllegalStateException(jsonBean.msg);
                            }
                        } else {
                            response.close();
                            throw new IllegalStateException("基类错误无法解析!");
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(JsonBean<JsonBean.Login> loginJsonBean, Call call, Response response) {
                        super.onSuccess(loginJsonBean, call, response);
                        listener.onFinish(user, password);
                        listener.onFinish(loginJsonBean.data.code);


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.onError(e);
                    }

                    @Override
                    public void onAfter(JsonBean<JsonBean.Login> loginJsonBean, Exception e) {
                        super.onAfter(loginJsonBean, e);
                        mCustomProgressDialog.dismiss();
                    }
                });

    }

    /**
     * 发送注册请求
     */
    public static void Register(final String user, final String password, Context context, final HttpCallbackListener listener) {
        if(!NetworkUtil.isNetworkConnected(context)){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        SortedMap<String, Object> parameters = new TreeMap<>();
        parameters.put("device_id", DEVICE_ID);
        parameters.put("game_id", GAME_ID);
        parameters.put("channel_id", CHANNEL_ID);
        parameters.put("package_name", PACKAGE_NAME);
        parameters.put("user", user);
        parameters.put("password", password);
        String sign = getSign(parameters);

        HttpParams params = new HttpParams();
        params.put("device_id", DEVICE_ID);
        params.put("game_id", GAME_ID);
        params.put("channel_id", CHANNEL_ID);
        params.put("package_name", PACKAGE_NAME);
        params.put("user", user);
        params.put("password", password);
        params.put("sign", sign);
        OkGo.post(API_HOST + "/?ct=user&ac=reg")
                .tag(context)
                .params(params)
                .execute(new JsonCallback<JsonBean<List<JsonBean.Register>>>(context) {
                    @Override
                    public void onSuccess(JsonBean<List<JsonBean.Register>> listJsonBean, Call call, Response response) {
                        super.onSuccess(listJsonBean, call, response);
                        listener.onFinish(user ,password);
                    }

                    @Override
                    public JsonBean<List<JsonBean.Register>> convertSuccess(Response response) throws Exception {
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];


                        if (!(type instanceof ParameterizedType))
                            throw new IllegalStateException("没有填写泛型参数");
                        Type rawType = ((ParameterizedType) type).getRawType();
                        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        if (typeArgument == Void.class) {
                            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                            response.close();
                            //noinspection unchecked
                            return (JsonBean<List<JsonBean.Register>>) simpleResponse.toJsonBean();
                        } else if (rawType == JsonBean.class) {
                            //有数据类型，表示有data
                            JsonBean jsonBean = Convert.fromJson(jsonReader, type);
                            response.close();
                            int state = jsonBean.state;

                            if (state == 1) {
                                //noinspection unchecked
                                return (JsonBean<List<JsonBean.Register>>) jsonBean;
                            } else if (state == 0) {
                                throw new IllegalStateException(jsonBean.msg);
                            }
                        } else {
                            response.close();
                            throw new IllegalStateException("基类错误无法解析!");
                        }
                        return null;
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.onError(e);
                    }
                });
    }

    /**
     * 角色登陆接口
     */
    public static void roleLogin(final Context context, final HttpCallbackListener listener) {
        if(!NetworkUtil.isNetworkConnected(context)){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        SortedMap<String, Object> parameters = new TreeMap<>();
        parameters.put("uid", UID);
        parameters.put("token", TOKEN);
        parameters.put("game_id", GAME_ID);
        parameters.put("server_id", SERVER_ID);
        parameters.put("role_id", ROLE_ID);
        parameters.put("package_name", PACKAGE_NAME);
        parameters.put("role_name", ROLE_NAME);
        parameters.put("role_level", ROLE_LEVEL);
        String sign = getSign(parameters);

        HttpParams params = new HttpParams();
        params.put("uid", UID);
        params.put("token", TOKEN);
        params.put("game_id", GAME_ID);
        params.put("server_id", SERVER_ID);
        params.put("role_id", ROLE_ID);
        params.put("package_name", PACKAGE_NAME);
        params.put("role_name", ROLE_NAME);
        params.put("role_level", ROLE_LEVEL);
        params.put("sign", sign);
        OkGo.post(API_HOST + "/?ct=user&ac=reg")
                .tag(context)
                .params(params)
                .execute(new JsonCallback<JsonBean<List<JsonBean.RoleLogin>>>(context) {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mCustomProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
                        mCustomProgressDialog.show();
                    }

                    @Override
                    public JsonBean<List<JsonBean.RoleLogin>> convertSuccess(Response response) throws Exception {
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];


                        if (!(type instanceof ParameterizedType))
                            throw new IllegalStateException("没有填写泛型参数");
                        Type rawType = ((ParameterizedType) type).getRawType();
                        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        if (typeArgument == Void.class) {
                            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                            response.close();
                            //noinspection unchecked
                            return (JsonBean<List<JsonBean.RoleLogin>>) simpleResponse.toJsonBean();
                        } else if (rawType == JsonBean.class) {
                            //有数据类型，表示有data
                            JsonBean jsonBean = Convert.fromJson(jsonReader, type);
                            response.close();
                            int state = jsonBean.state;

                            if (state == 1) {
                                //noinspection unchecked
                                return (JsonBean<List<JsonBean.RoleLogin>>) jsonBean;
                            } else if (state == 0) {
                                throw new IllegalStateException(jsonBean.msg);
                            }
                        } else {
                            response.close();
                            throw new IllegalStateException("基类错误无法解析!");
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(JsonBean<List<JsonBean.RoleLogin>> listJsonBean, Call call, Response response) {
                        super.onSuccess(listJsonBean, call, response);
                        listener.onFinish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.onError(e);
                    }

                    @Override
                    public void onAfter(JsonBean<List<JsonBean.RoleLogin>> listJsonBean, Exception e) {
                        super.onAfter(listJsonBean, e);
                        mCustomProgressDialog.dismiss();
                    }
                });
    }

}
