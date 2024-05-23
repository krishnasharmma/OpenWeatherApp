#include <jni.h>
#include <string>

extern "C" jstring
Java_com_openweather_app_utils_Keys_apiKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "MTE4MDQ2ZWQyM2QzOTE3ZmI4NjYyOGMwZTQ3YzdhODU=";
    return env->NewStringUTF(api_key.c_str());
}

extern "C" jstring
Java_com_openweather_app_utils_Constants_getBaseUrl(JNIEnv *env, jobject thiz) {
    std::string api_key = "https://api.openweathermap.org/data/2.5/";
    return env->NewStringUTF(api_key.c_str());
}

extern "C" jstring
Java_com_openweather_app_utils_Constants_getImageUrl(JNIEnv *env, jobject thiz) {
    std::string api_key = "https://openweathermap.org/img/wn/";
    return env->NewStringUTF(api_key.c_str());
}

extern "C" jstring
Java_com_openweather_app_utils_Keys_sqlKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "bXlTcWxDaXBoZXJLZXk=";
    return env->NewStringUTF(api_key.c_str());
}