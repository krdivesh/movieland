import {useEffect, useState} from "react";
import Movie from "Frontend/generated/com/example/application/data/Movie";
import {MovieRatingService} from "Frontend/generated/endpoints";
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import MovieForm from "Frontend/views/movieland/MovieForm";

export default function MovieRating() {
    const [movies, setMovies] = useState<Movie[]>([])
    const [selectedMovies, setSelectedMovies] = useState<Movie | undefined>(undefined);

    useEffect( () => {
            MovieRatingService.getMovies().then(setMovies);
        }, []);

    async function saveMovie(movie: Movie) {
        const saveMovie = await MovieRatingService.saveMovie(movie);
        setMovies(movies.map(e => e.id === saveMovie.id?saveMovie:e));
        setSelectedMovies(saveMovie)
    }
    return (
        <div className="p-m">
            <h1>MovieLand</h1>
            <Grid items={movies}
                  onActiveItemChanged = {e => {
                      // @ts-ignore
                      setSelectedMovies(e.detail.value);
                  }}
                  selectedItems={[selectedMovies]}
            >
                <GridColumn path="title"/>
                <GridColumn path="director"/>
                <GridColumn path="releaseYear"/>
                <GridColumn path="genre"/>
            </Grid>
            {selectedMovies && <MovieForm movie={selectedMovies} onSubmit={saveMovie}/>}
        </div>
    )
}