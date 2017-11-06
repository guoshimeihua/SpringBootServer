package com.dodonew.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodonew.annotation.DataValidate;
import com.dodonew.domain.Document;
import com.dodonew.domain.User;
import com.dodonew.service.DocumentService;
import com.dodonew.service.SaveFileService;
import com.dodonew.service.UserService;
import com.dodonew.util.common.BootConstants;
import com.dodonew.util.common.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Bruce on 2017/11/3.
 */
@RestController
public class DocumentController {
    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;
    @Autowired
    @Qualifier("saveFileService")
    private SaveFileService saveFileService;
    @Autowired
    @Qualifier("userSevice")
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @RequestMapping(value = "/hrm/api/documents", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"pageIndex"})
    public void selectDocumentList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String pageIndex = requestJson.getString("pageIndex");
            String pageSize = requestJson.getString("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            List<Document> documentList = documentService.getDocumentList(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");
            if (documentList != null && documentList.size() > 0) {
                String deptStr = JSON.toJSONString(documentList, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONArray deptJsonArray = JSONArray.parseArray(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJsonArray);
            } else {
                JSONArray emptyJsonArray = new JSONArray();
                resultJson.put(BootConstants.DATA_KEY, emptyJsonArray);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/documents/id", method = RequestMethod.GET)
    @DataValidate(requiredParams = {"documentId"})
    public void selectDocument(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        // 为空的情况，在afterCompletion统一做了处理，返回数据为空。
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
            resultJson.put(BootConstants.MESSAGE_KEY, "请求成功");

            String documentId = requestJson.getString("documentId");
            Document document = documentService.getDocument(Integer.parseInt(documentId));
            if (document == null) {
                JSONObject emptyJson = new JSONObject();
                resultJson.put(BootConstants.DATA_KEY, emptyJson);
            } else {
                logger.info("document === " + document.toString());
                String deptStr = JSON.toJSONString(document, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
            }

            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/documents/id", method = RequestMethod.PATCH)
    @DataValidate(requiredParams = {"documentId", "title"})
    public void updateDocument(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            String documentId = requestJson.getString("documentId");
            String title = requestJson.getString("title");
            Document document = new Document();
            document.setId(Integer.parseInt(documentId));
            document.setTitle(title);

            boolean isSuccess = documentService.modifyDocument(document);
            if (isSuccess) {
                Document modifyedDocument = documentService.getDocument(Integer.parseInt(documentId));
                String deptStr = JSON.toJSONString(modifyedDocument, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject deptJson = JSONObject.parseObject(deptStr);

                resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                resultJson.put(BootConstants.DATA_KEY, deptJson);
                resultJson.put(BootConstants.MESSAGE_KEY, "文件信息修改成功");
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "文件信息修改失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    @RequestMapping(value = "/hrm/api/documents/id", method = RequestMethod.DELETE)
    @DataValidate(requiredParams = {"documentId"})
    public void deleteDocument(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();

            String documentId = requestJson.getString("documentId");
            // 删除一个文件之前，先查询下该文件是否存在
            Document document = documentService.getDocument(Integer.parseInt(documentId));
            if (document == null) {
                // 要删除的文件不存在
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "要删除的文件不存在");
            } else {
                boolean isSuccess = documentService.removeDocument(Integer.parseInt(documentId));
                if (isSuccess) {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                    resultJson.put(BootConstants.MESSAGE_KEY, "文件删除成功");
                    JSONObject emptyJson = new JSONObject();
                    resultJson.put(BootConstants.DATA_KEY, emptyJson);
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "文件删除失败");
                }
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 接收上传的图片，把图片存储在工程中是不好的，最后也是存储到另外一个工程，因为当前工程是加密的，需要
     * 经过拦截器的。而返回的图片链接地址是不需要加密可以直接访问的，大部分的平台存储也是这样做的。否则的话，
     * 拦截器这块就要重新修改适应了。
     * 先解决上传一张图片的问题，然后再解决上传多张图片的问题，多张图片也是同样的道理，多张图片就不用测试了。
     * 多张图片上传也是同样的道理。用单元测试图片上传是很方便的。
     */
    @RequestMapping(value = "/hrm/api/documents", method = RequestMethod.POST)
    @DataValidate(requiredParams = {"ownerId", "title", "remark"})
    public void receiveDocument(HttpServletRequest request, HttpServletResponse response) {
        JSONObject requestJson = (JSONObject) request.getAttribute(BootConstants.REQUESTDATA);
        if (!requestJson.isEmpty()) {
            JSONObject resultJson = new JSONObject();
            MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
            if (!file.isEmpty()) {
                boolean isSuccess = saveFileService.store(file);
                if (isSuccess) {
                    String ownerId = requestJson.getString("ownerId");
                    String title = requestJson.getString("title");
                    String remark = requestJson.getString("remark");
                    String fileName = file.getOriginalFilename();
                    Document document = new Document();
                    User user = userService.getUser(Integer.parseInt(ownerId));
                    document.setUser(user);
                    document.setFileName(fileName);
                    document.setTitle(title);
                    document.setRemark(remark);
                    document.setFile(file);
                    boolean isSave = documentService.addDocument(document);
                    if (isSave) {
                        resultJson.put(BootConstants.CODE_KEY, StatusCode.SUCCESS);
                        resultJson.put(BootConstants.MESSAGE_KEY, "图片上传成功");
                        JSONObject emptyJson = new JSONObject();
                        resultJson.put(BootConstants.DATA_KEY, emptyJson);
                    } else {
                        resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                        resultJson.put(BootConstants.MESSAGE_KEY, "图片上传失败");
                    }
                } else {
                    resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                    resultJson.put(BootConstants.MESSAGE_KEY, "图片上传失败");
                }
            } else {
                resultJson.put(BootConstants.CODE_KEY, StatusCode.ERROR);
                resultJson.put(BootConstants.MESSAGE_KEY, "图片上传失败");
            }
            request.setAttribute(BootConstants.REQUESTAFTERDATA, resultJson);
        }
    }

    /**
     * 图片下载
     */
    @RequestMapping(value = "/hrm/api/documents/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadDocument(HttpServletRequest request, HttpServletResponse response) {
        String downloadFileName = "userAvatar.jpeg";
        Resource file = saveFileService.loadFile(downloadFileName);
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        // application/octet-stream ：二进制数据流（最常见的文件下载）
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(file);
    }
}
