package org.deng.fileupload.Service;

import java.util.List;

public interface ImgService {
    Boolean deleteAllImg();
    <T> List<T> paginate(List<T> items, int currentPage, int pageSize);
}
