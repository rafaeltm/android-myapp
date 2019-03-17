package com.comov.myapp.apiTools;

public class APIUtils {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
