package nwnu.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import nwnu.word.WordCount;

/**
 * Servlet implementation class WordCountServlet
 */
@WebServlet("/WordCountServlet")
public class WordCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static TreeMap<String,Integer> map;
	public static String[] str;
	public static String fileName;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//请求与响应的编码格式
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		String id=null;
		//接受页面数据
		fileName = request.getParameter("fileName");//文件名
		String  words = request.getParameter("word");//指定单词
		String wordnum = request.getParameter("wordnum");//高频数
		String wordlines = request.getParameter("wordlines");//行数
		String result = request.getParameter("result");//存放
		id=request.getParameter("id");//功能编号的获取
		
		if(id.equals("0")){
			//文件的上传
			WordCount wordCount = new WordCount();
			map=wordCount.count(fileName);
			out.print("<script>alert('上传成功！！');location.href='html/WordCount.jsp'</script>");	
		}else if(id.equals("1")){
			//指定单词的查找
			//开始时间
			long startTime=System.currentTimeMillis();
			TreeMap<String,Integer> map1 = new TreeMap<String, Integer>(); 
			WordCount wordCount = new WordCount();
		    map1= wordCount.bijiao(map, words);
		    //结束时间
			long endTime=System.currentTimeMillis();
			float excTime=(float)((endTime-startTime)/1000)*1000;
			//向jsp页面传参数
			request.setAttribute("map1", map1);
			request.setAttribute("excTime", excTime);
			System.out.println("执行时间："+excTime+"ms"); 
            request.getRequestDispatcher("html/WordCount.jsp").forward(request, response);
		}else if(id.equals("2")){
			//高频词的统计
			//开始时间
			long startTime=System.currentTimeMillis();
			WordCount wordCount = new WordCount();
			int k=Integer.parseInt(wordnum);
			ArrayList gaopin=wordCount.gaopin(map, k);
			//结束时间
			long endTime=System.currentTimeMillis();
			float excTime2=(float)((endTime-startTime)/1000)*1000;
			request.setAttribute("gaopin", gaopin);
			request.setAttribute("excTime2", excTime2);
	        request.getRequestDispatcher("html/WordCount.jsp").forward(request, response);
			
		}else if(id.equals("3")){
			//行数统计
			//开始时间
			long startTime=System.currentTimeMillis();
			WordCount wordCount = new WordCount();
			List list=wordCount.statistics(fileName);
			//结束时间
			long endTime=System.currentTimeMillis();
			float excTime3=(float)((endTime-startTime)/1000)*1000;
			request.setAttribute("excTime3", excTime3);
			request.setAttribute("list", list);
			request.getRequestDispatcher("html/WordCount.jsp").forward(request, response);
		}else if(id.equals("4")){
			//写入文件
			//开始时间
			long startTime=System.currentTimeMillis();
			WordCount cunfang = new WordCount();
			cunfang.cunfang(map);
			//结束时间
			long endTime=System.currentTimeMillis();
			float excTime1=(float)((endTime-startTime)/1000)*1000;
			request.setAttribute("excTime1", excTime1);
			out.print("<script>alert('存储成功！！');location.href='html/WordCount.jsp'</script>");	
			   
			request.getRequestDispatcher("html/WordCount.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	
	

}
