package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.service.ILuceneService;
import com.skylaker.yunzhi.utils.BaseUtil;
import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinygroup.chineseanalyzer.ChineseAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 *     Lucene处理逻辑类 <br/>
 *
 *     1、主要处理数据库相关数据索引，例如问题数据、回答数据
 * </p>
 *
 * User: zhuyong
 * Date: 2018/6/4 13:39
 */
@Service("luceneServiceImpl")
public class LuceneServiceImpl implements ILuceneService {
    @Autowired
    private LuceneQuestionServiceImpl luceneQuestionService;

    //指定索引目录
    private static Directory directory = null;
    //分词对象
    private static Analyzer analyzer = null;
    //指定索引处理IndexWriter对象
    private static IndexWriter indexWriter = null;
    //索引检索对象
    private static IndexSearcher indexSearcher = null;

    static {
        if(null == directory){
            try {
                directory = FSDirectory.open(Paths.get("D:\\YunZhi\\Temp"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(null == analyzer){
            //指定分词器
            analyzer = new StandardAnalyzer();
        }
    }


    public  IndexWriter getIndexWriter(){
        if(null == indexWriter || !indexWriter.isOpen()){
            try {
                IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);

                //指定索引处理IndexWriter对象
                indexWriter = new IndexWriter(directory, writerConfig);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexWriter;
    }

    public IndexSearcher getIndexSearcher(){
        if(null == indexSearcher){
            try {
                IndexReader indexReader = DirectoryReader.open(directory);
                indexSearcher = new IndexSearcher(indexReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexSearcher;
    }

    /**
     * 添加对象索引
     *
     * @param   object
     * @return
     */
    @Override
    public IResult addIndex(Object object){
        if(null == object){
            return BaseResult.FAILTURE;
        }

        if(object instanceof Question){
            return luceneQuestionService.addIndex(object);
        }else if(object instanceof Answer){

        }

        return null;
    }

    @Override
    public List<Object> getSearchResult(String words) {
        if(BaseUtil.isNullOrEmpty(words)){
            return null;
        }

        //TODO 需要检索多个字段
        try {
            Query query = new TermQuery(new Term("title", words));
            IndexSearcher indexSearcher = this.getIndexSearcher();

            TopDocs topDocs = indexSearcher.search(query, 100);

            for (int i = 0; i < topDocs.scoreDocs.length; i++) {
                ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                Document document = indexSearcher.doc(scoreDoc.doc);

                String title = document.get("title");

                System.out.println("检索到数据：" + title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}