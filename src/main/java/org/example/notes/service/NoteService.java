package org.example.notes.service;

import org.example.notes.entity.Note;
import org.example.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    public void saveNote(Note note) {
        repository.save(note);
    }

    public void deleteNote(Long id) {
        repository.deleteById(id);
    }

    public Note getNoteById(long id) {
        return repository.getReferenceById(id);
    }

}
