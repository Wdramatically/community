package life.majiang.community.provider;

import com.alibaba.fastjson2.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUserInfo;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Component
public class GithubProvider {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final MediaType TYPE_JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {

        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(json, TYPE_JSON);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            logger.info("response: " + string);
            return string.split("&")[0].split("=")[1];
        }
    }

    public GithubUserInfo getUserInfo(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "Bearer " + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            logger.info("user:" + string);
            GithubUserInfo githubUserInfo = JSON.parseObject(string, GithubUserInfo.class);
            return githubUserInfo;
        }
    }
}
