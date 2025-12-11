package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {
        var books = bookService.getAllBooks();

        // Statistics
        long totalBooks = books.size();
        long borrowedBooks = books.stream().filter(b -> !b.isAvailable()).count();
        long availableBooks = books.stream().filter(Book::isAvailable).count();
        long activeMembers = memberService.countMembers();

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("availableBooks", availableBooks);
        model.addAttribute("activeMembers", activeMembers);

        // Top 5 Books for Dashboard
        List<Book> recentBooks = books.stream().limit(5).toList();
        model.addAttribute("books", recentBooks);
        model.addAttribute("book", new Book()); // For the form

        return "index";
    }

    @GetMapping("/books")
    public String manageBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new Book());
        return "books";
    }

    @PostMapping("/addBook")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("error", "Title and Author are required!");
            return "books";
        }
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        return "redirect:/books";
    }

    @GetMapping("/borrow/{id}")
    public String borrowBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookService.borrowBook(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book borrowed successfully!");
        return "redirect:/books";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookService.returnBook(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book returned successfully!");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        return "redirect:/books";
    }

    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("member", new Member());
        return "members";
    }

    @PostMapping("/addMember")
    public String addMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        memberService.saveMember(member);
        redirectAttributes.addFlashAttribute("successMessage", "Member registered successfully!");
        return "redirect:/members";
    }

    @GetMapping("/deleteMember/{id}")
    public String deleteMember(@PathVariable String id, RedirectAttributes redirectAttributes) {
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("successMessage", "Member removed successfully!");
        return "redirect:/members";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}
