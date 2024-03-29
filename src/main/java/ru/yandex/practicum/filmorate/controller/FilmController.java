package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    final ValidateService validateService;
    final FilmService filmService;

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film get(@PathVariable Long filmId) {
        return filmService.get(filmId);
    }

    @DeleteMapping("/{filmId}")
    public void delete(@PathVariable Long filmId) {
        filmService.delete(filmId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        return filmService.update(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addLike(filmId, userId);
        return filmService.get(filmId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.deleteLike(filmId, userId);
        return filmService.get(filmId);
    }

    @GetMapping("/{filmId}/likes")
    public List<User> getFIlmLikes(@PathVariable long filmId) {
        return filmService.getFIlmLikes(filmId);
    }

    @GetMapping("/{filmId}/genres")
    public List<Genre> getFilmGenres(@PathVariable long filmId) {
        return filmService.getFilmGenres(filmId);
    }

    @GetMapping("/director/{directorId}")
    public List<Film>  getFilmDirectorsSortedList(@PathVariable int directorId, @RequestParam String sortBy) {
        return filmService.getFilmDirectorsSortedList(directorId, sortBy);
    }

    @GetMapping("/search")
    public List<Film> searchFilm(@RequestParam String query, @RequestParam List<String> by) {
        return filmService.searchFilm(query, by);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(value = "count", defaultValue = "10") Integer count,
                                          @RequestParam(value = "genreId", required = false) Long genreId,
                                          @RequestParam(value = "year", required = false) Integer year) {
        return filmService.getMostPopularFilms(genreId, year, count);
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam("userId") int userId, @RequestParam("friendId") int friendId) {
        return filmService.getCommonFilms(userId,friendId);
    }
}