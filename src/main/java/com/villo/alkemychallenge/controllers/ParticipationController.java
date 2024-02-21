package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.services.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService participationService;

    @PostMapping("/{idFilm}/character/{idCharacter}")
    public ResponseEntity<Void> add(@PathVariable final Long idFilm, @PathVariable final Long idCharacter) {
        return participationService.add(idFilm, idCharacter);
    }

    @DeleteMapping("/{idFilm}/character/{idCharacter}")
    public ResponseEntity<Void> remove(@PathVariable final Long idFilm, @PathVariable final Long idCharacter) {
        return participationService.remove(idFilm, idCharacter);
    }
}
