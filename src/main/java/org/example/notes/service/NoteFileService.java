package org.example.notes.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.notes.entity.Note;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteFileService {

    private static final String FILE_PATH = "notes.txt";

    private final ApplicationContext context;

    public NoteFileService(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void loadNotes() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);

                NoteService noteService = context.getBean(NoteService.class);
                for (String line : lines) {
                    String[] parts = line.split("\\|", 4);
                    if (parts.length == 4) {
                        noteService.saveNote(new Note(Long.parseLong(parts[0]), convertToDate(parts[1]), parts[2], parts[3]));
                    }
                }
            } else {
                new File(FILE_PATH);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error while loading notes from file: " + e.getMessage());
        }
    }

    private Date convertToDate(String part) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.parse(part);
    }

    @PreDestroy
    public void saveNotes() {
        try {
            NoteService noteService = context.getBean(NoteService.class);
            List<Note> actualNotes = noteService.getAllNotes();

            List<String> lines = actualNotes.stream()
                    .map(
                            note ->
                                    note.getId() + "|"
                                            + convertToProperForm(note.getDate()) + "|"
                                            + note.getTitle() + "|"
                                            + note.getContent())
                    .collect(Collectors.toList());
            Files.write(Paths.get(FILE_PATH), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения заметок: " + e.getMessage());
        }
    }

    private String convertToProperForm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);
    }
}
