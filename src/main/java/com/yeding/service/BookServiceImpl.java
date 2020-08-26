package com.yeding.service;

import com.yeding.dao.BookMapper;
import com.yeding.dto.PaginationDTO;
import com.yeding.pojo.Books;

import java.util.List;

public class BookServiceImpl implements BookService{


    //调用dao层的操作，设置一个set接口，方便Spring管理
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Books book) {
        return bookMapper.addBook(book);
    }

    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }


    public int queryBookCountByName(String bookName) {
        return bookMapper.queryBookCountByName(bookName);
    }

    @Override
    public PaginationDTO queryAllBook(Integer curPage, Integer pageSize) {
        PaginationDTO<Object> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        Integer totalCount = bookMapper.queryBookCount();
        if(totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize + 1;
        }
        if (curPage < 1){
            curPage = 1;
        }
        if (curPage > totalPage){
            curPage = totalPage;
        }

        paginationDTO.setPagination(totalPage,curPage);
        Integer offset = pageSize * (curPage - 1);

        List<Books> books = bookMapper.queryAllBook(offset, pageSize);
        paginationDTO.setData(books);

        return paginationDTO;
    }

    @Override
    public PaginationDTO queryBookByName(String bookName, Integer curPage, Integer pageSize) {
        PaginationDTO<Object> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        Integer totalCount = bookMapper.queryBookCountByName(bookName);
        if (totalCount.equals(0)) {
            return paginationDTO;
        }
        if(totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize + 1;
        }
        if (curPage < 1){
            curPage = 1;
        }
        if (curPage > totalPage){
            curPage = totalPage;
        }

        paginationDTO.setPagination(totalPage,curPage);
        Integer offset = pageSize * (curPage - 1);

        List<Books> books = bookMapper.queryBookByName(bookName,offset,pageSize);
        paginationDTO.setData(books);

        return paginationDTO;

    }



}
