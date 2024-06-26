package org.deng.fileupload.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.Map;

@Service
public interface HttpAPiService {

    /**
       * 一日一言API接口：
     {
         "code":200,
         "result":{
             "author":"弗朗西斯·培根",
             "content":"我们几乎生活在各种各样的幕中，成为一种被遮挡的存在。",
             "date":"20240322",
             "from":"哲学家",
             "pic_url":"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp",
             "thumb":"https://pics.tide.moreless.io/dailypics/Fl03PpkYRelUMeUHR90rGei3Epy-?imageView2/1/w/1366/h/768/format/webp?imageView2/1/w/300/h/300/format/webp"
         },
         "msg":"success"
     }
     * */
     Map<String, Object> OneDayOneSay() throws IOException;
}
