package org.deng.fileupload.Service.IMP;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deng.fileupload.Service.HttpAPiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpApiServiceIMP implements HttpAPiService {
    /**
     * 一日一言API接口：
     * {
     * "code":200,
     * "result":{
     * "author":"弗朗西斯·培根",
     * "content":"我们几乎生活在各种各样的幕中，成为一种被遮挡的存在。",
     * "date":"20240322",
     * "from":"哲学家",
     * "pic_url":"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp",
     * "thumb":"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp?imageView2/1/w/300/h/300/format/webp"
     * },
     * "msg":"success"
     * }
     */
    @Override
    public Map<String, Object> OneDayOneSay() throws JsonProcessingException {
        try {
            // 创建一个URL对象
            URL obj = new URL("https://api.oioweb.cn/api/SoulWords");
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            // 设置请求方法为GET
            connection.setRequestMethod("GET");
            // 获取响应代码
            int responseCode = connection.getResponseCode();
            System.out.println("响应代码: " + responseCode);

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //转为Map对象--->
            // 创建ObjectMapper实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 将JSON字符串转换为Map
            return objectMapper.readValue(response.toString(), Map.class);

        } catch (Exception e) {
            e.printStackTrace();

            String errorReteurnStr=" {\n" +
                    "         \"code\":200,\n" +
                    "         \"result\":{\n" +
                    "             \"author\":\"弗朗西斯·培根\",\n" +
                    "             \"content\":\"我们几乎生活在各种各样的幕中，成为一种被遮挡的存在。\",\n" +
                    "             \"date\":\"20240322\",\n" +
                    "             \"from\":\"哲学家\",\n" +
                    "             \"pic_url\":\"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp\",\n" +
                    "             \"thumb\":\"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp?imageView2/1/w/300/h/300/format/webp\"\n" +
                    "         },\n" +
                    "         \"msg\":\"success\"\n" +
                    "     }";

            //转为Map对象--->
            ObjectMapper objectMapper = new ObjectMapper();
            // 将JSON字符串转换为Map
            return objectMapper.readValue(errorReteurnStr, Map.class);

        }
    }
}
