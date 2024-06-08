import Movie from "Frontend/generated/com/example/application/data/Movie";
import {TextField} from "@hilla/react-components/TextField.js";
import {useForm} from "@hilla/react-form";
import MovieModel from "Frontend/generated/com/example/application/data/MovieModel";
import {useEffect} from "react";
import {Button} from "@hilla/react-components/Button.js";
import {ComboBox} from "@hilla/react-components/ComboBox";

const ratingOptions = ["Amazing", "Good", "Meh", "Poor"];


interface MovieFormProps {
    movie: Movie;
    onSubmit: (movie: Movie) => Promise<void>;
}
export default function MovieForm({movie, onSubmit}: MovieFormProps) {
    const {field, model, submit, read, reset} = useForm(MovieModel, {onSubmit});
    useEffect(() => {
        read(movie)
    }, [movie]);
    return (
        <div className={"grid-cols-2 gap-s"}>
            <TextField label="Title" {...field(model.title)} />
            <TextField label="Director" {...field(model.director)} />
            <TextField label="Genre" {...field(model.genre)} />
            <TextField label="Release Year" {...field(model.releaseYear)} />
            <ComboBox label="Rating" item-label-path="name"
                      item-value-path="val" helper-text="How did the movie made you feel?"
                      items={ratingOptions} {...field(model.rating)}/>
            <Button onClick={submit}>Update</Button>
            <Button onClick={reset} theme="tertiary">Reset</Button>
        </div>
    )
}