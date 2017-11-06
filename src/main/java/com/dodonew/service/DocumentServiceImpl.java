package com.dodonew.service;

import com.dodonew.dao.DocumentDao;
import com.dodonew.domain.Document;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/11/3.
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentDao documentDao;

    @Override
    public List<Document> getDocumentList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = documentDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return documentDao.selectByPage(params);
    }

    @Override
    public Document getDocument(Integer id) {
        return documentDao.selectById(id);
    }

    @Override
    public boolean addDocument(Document document) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = documentDao.save(document);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeDocument(Integer id) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = documentDao.deleteById(id);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean modifyDocument(Document document) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = documentDao.update(document);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
