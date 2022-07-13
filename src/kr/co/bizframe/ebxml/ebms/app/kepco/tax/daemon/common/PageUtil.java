/*
 * Created on 2005. 6. 10.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.util.ArrayList;

/**
 * @author shin sung uk
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PageUtil {
    public static ArrayList getList(ArrayList data, String pageIndex) {
        ArrayList pageData = new ArrayList();
        int index = 0;
        if (pageIndex == null || pageIndex.equals(""))
            return data;
        else
            index = Integer.parseInt(pageIndex) * 10;

        int i = index - 10;
        while (i < index && i < data.size()) {
            Object obj = data.get(i);
            pageData.add(obj);
            i++;
        }
        return pageData;
    }

    public static String displayLink(String pageUrl, int size, String curPage) {
        ArrayList pageData = new ArrayList();
        String appendParam = "?pageCnt=";
        String html = "";
        
        int index = 0;
        int nCpage = Integer.parseInt(curPage);

        int maxPage = ((Integer.parseInt(curPage) - 1) / 10 + 1) * 100;
        int endPage = 0;
        
        if (size - maxPage <= 0) endPage = size;
        else endPage = maxPage;
        
        int startPage = maxPage - 100;
        int moveIndex = startPage;
        
        String tmp = "";

        while (moveIndex < endPage) {
            System.out.println("moveIndex:"+moveIndex);
            System.out.println("endPage:"+endPage);
            System.out.println("startPage:"+startPage);
            if (moveIndex == startPage && startPage != 0) {
                int page = moveIndex / 10;
                html = html + "<a href='javascript:selectPage(" + page + ");'>" + 
                       "<img src=\"../images/b_p01.gif\" border=0></a>&nbsp;"+
                       "<img src=\"../images/b_p03.gif\"> &nbsp;&nbsp;";
            }else if (moveIndex == startPage && endPage <= 100) {
                int page = moveIndex / 10;
                html = html + "<img src=\"../images/b_p01.gif\" border=0></a>&nbsp;"
                + "<img src=\"../images/b_p03.gif\"> &nbsp;&nbsp;";
            }
            
            if (moveIndex < endPage ) {
                int page = moveIndex / 10 + 1;
                if(nCpage==page) tmp = "<b>"+page+"</b>";
                else tmp = page+"";
                
                html = html + "<a href='javascript:selectPage(" + page + ");'>" + tmp + "&nbsp;</a>&nbsp;";
                //+ "<img src=\"../images/b_p03.gif\"> &nbsp;";
            }
            if (moveIndex + 11 > endPage && moveIndex + 10 < size) {
                int page = moveIndex / 10 + 1;
                html = html + "&nbsp;&nbsp;<a href='javascript:selectPage(" + (page + 1) + ");'><img src=\"../images/b_n01.gif\" border=0></a>&nbsp;"
                +"<a href='javascript:selectPage(" + (endPage) + ");'><img src=\"../images/b_n03.gif\" border=0></a>&nbsp;";
            }else if (moveIndex + 11 > endPage && endPage <= 100) {
                int page = moveIndex / 10;
                html = html + "&nbsp;&nbsp;<img src=\"../images/b_n01.gif\" border=0>&nbsp;<img src=\"../images/b_n03.gif\" border=0> &nbsp;";
            }
            
            
            moveIndex = moveIndex + 10;
        }
        return html;
    }

    public static void main(String args[]){
        System.out.println(PageUtil.displayLink("t.jsp", 50, "2"));
    }
}
