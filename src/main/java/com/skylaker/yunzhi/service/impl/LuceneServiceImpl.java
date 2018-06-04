package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.service.ILuceneService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Logger logger = LoggerFactory.getLogger(this.getClass());


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
                directory = FSDirectory.open(Paths.get(GlobalConstant.LUCENE_INDEX_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(null == analyzer){
            //指定分词器
            analyzer = new StandardAnalyzer();
        }
    }

    /**
     * 获取写索引操作IndewWriter对象
     *
     * @return
     */
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

    /**
     * 获取读取索引信息IndexSearch对象
     *
     * @return
     */
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

        try {
            //创建Document对象
            Document document = getDocumentByJavaBean(object);
            //添加文档对象
            getIndexWriter().addDocument(document);
            //提交
            getIndexWriter().commit();

            //关闭索引处理对象
            // super.getIndexWriter().close();

            return BaseResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.FAILTURE;
        }
    }


    /**
     * 解析JavaBean对象获取对应的索引文档对象
     *
     * @param object   要被索引的Java Bean对象
     *
     * @return {Document}
     */
    protected Document getDocumentByJavaBean(Object object){
        if(null == object){
            return null;
        }

        Document document = new Document();
        Class objClass = object.getClass();
        String type = "";

        //判断对象类型，决定给所添加的索引域名称前缀
        if(object instanceof Question){
            type = "q_";
        }else if(object instanceof Answer){
            type = "a_";
        }

        //获取对象属性名称以及值
        Map<Object, Object> objFieldsMap = BaseUtil.getObjFieldMap(object);
        if(null == objFieldsMap || 0 == objFieldsMap.size()){
            return null;
        }

        //将title和text以及问题、回答ID属性及值索引
        Set<Map.Entry<Object, Object>> entrySet = objFieldsMap.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet){
            if(entry.getKey().toString().contains("qid") || entry.getKey().toString().contains("title") ||
                    entry.getKey().toString().contains("aid") || entry.getKey().toString().contains("text")){
                document.add(new TextField(type + entry.getKey().toString(), entry.getValue().toString(), Field.Store.YES));
            }
        }

        return document;
    }

    /**
     * 检索索引获取结果
     *
     * @param words 检索关键词
     * @return
     */
    @Override
    public List<Object> getSearchResult(String words) {
        if(BaseUtil.isNullOrEmpty(words)){
            return null;
        }

        try {
            //检索单个字段
            //Query query = new TermQuery(new Term("q_title", words));

            //检索多个字段
            String[] queryWords = new String[]{words, words, words};
            String[] fields = {"q_title", "q_text", "a_text"};
            Query query = MultiFieldQueryParser.parse(queryWords, fields, analyzer);

            IndexSearcher indexSearcher = this.getIndexSearcher();
            TopDocs topDocs = indexSearcher.search(query, 100);

            logger.debug("共检索到" + topDocs.totalHits + "条数据！");

            for (int i = 0; i < topDocs.scoreDocs.length; i++) {
                ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                Document document = indexSearcher.doc(scoreDoc.doc);

                String title = document.get("q_title");
                logger.debug("检索到数据：" + title);

                if(!BaseUtil.isNullOrEmpty(title)){
                    Integer qid = Integer.parseInt(document.get("q_id"));
                    //TODO 可获取问题对象
                }
            }

            //TODO 可以分别解析问题、回答数据索引
            //TODO 根据检索结果得到相关的问题、回答，然后查询问题、回答信息，封装成集合返回到前台展示
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}