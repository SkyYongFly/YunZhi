package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Lucene问题数据相关处理
 *
 * User: zhuyong
 * Date: 2018/6/4 13:44
 */
@Service
public class LuceneQuestionServiceImpl extends LuceneServiceImpl{

    @Override
    public IResult addIndex(Object object) {
        if(null == object || !(object instanceof Question)){
            return super.addIndex(object);
        }

        Question question = (Question) object;

        //创建Document对象
        Document document = new Document();
        //添加域信息
        document.add(new TextField("title", question.getTitle(), Field.Store.YES));
        document.add(new TextField("text", question.getText(), Field.Store.YES));

        try {
            //添加文档对象
            super.getIndexWriter().addDocument(document);

            super.getIndexWriter().commit();

            //关闭索引处理对象
            // super.getIndexWriter().close();

            return BaseResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.FAILTURE;
        }
    }
}