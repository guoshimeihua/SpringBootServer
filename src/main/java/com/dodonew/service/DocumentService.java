package com.dodonew.service;

import com.dodonew.domain.Document;

import java.util.List;

/**
 * Created by Bruce on 2017/11/3.
 */
public interface DocumentService {
    public List<Document> getDocumentList(Integer pageIndex, Integer pageSize);

    public Document getDocument(Integer id);

    public boolean addDocument(Document document);

    public boolean removeDocument(Integer id);

    public boolean modifyDocument(Document document);
}
