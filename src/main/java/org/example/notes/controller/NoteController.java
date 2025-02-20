package org.example.notes.controller;

import org.example.notes.entity.Note;
import org.example.notes.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getAllNotes(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        return "notes-list"; // name of html file
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute Note note) {
        note.setDate(new Date());
        noteService.saveNote(note);
        return "redirect:/notes";
    }

    @GetMapping("/edit/{id}")
    public String editNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "edit";
    }

    @PostMapping("/update")
    public String updateNote(@ModelAttribute Note note) {
        note.setDate(new Date());
        noteService.saveNote(note);
        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}
