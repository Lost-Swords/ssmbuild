package com.yeding.controller;


import com.yeding.dto.PaginationDTO;
import com.yeding.pojo.Books;
import com.yeding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;


    @RequestMapping("/allBook")
    public String list(Model model,
                       @RequestParam(name="curPage",defaultValue = "1") Integer curPage,
                       @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PaginationDTO paginationDTO = bookService.queryAllBook(curPage,pageSize);
        model.addAttribute("list",paginationDTO);
        return "allBook";

    }

    @RequestMapping("/toAddBook")
    public String toAddPaper() {
        return "addBook";
    }

    @RequestMapping("/addBook")
    public String addPaper(Books books) {
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }


    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(Model model, int id) {
        Books books = bookService.queryBookById(id);
        System.out.println(books);
        model.addAttribute("book",books );
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book) {
        System.out.println(book);
        bookService.updateBook(book);
        Books books = bookService.queryBookById(book.getBookID());
        model.addAttribute("books", books);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/del/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/queryBook")
    public String queryBookByName(@RequestParam(name = "bookName",required = false)String bookName,
                                  @RequestParam(name="curPage",defaultValue = "1") Integer curPage,
                                  @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize,
                                  Model model){
        if(bookName != "") {
            int count = bookService.queryBookCountByName(bookName);
        if (count == 0){
            model.addAttribute("error","未查到相关书籍");
            model.addAttribute("list",null);
            return "allBook";
        }
        PaginationDTO paginationDTO = bookService.queryBookByName(bookName, curPage, pageSize);
        model.addAttribute("bookName",bookName);
        model.addAttribute("list",paginationDTO);
        return "allBook";
        }
        else
        {
            PaginationDTO paginationDTO = bookService.queryAllBook(curPage,pageSize);
            model.addAttribute("list",paginationDTO);
            return "allBook";
        }
    }

}