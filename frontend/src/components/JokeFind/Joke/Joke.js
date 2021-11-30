import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getJokeById} from "../../../api/JokeService";
import './jokeStyle.css'

const Joke = () => {
    const id = useParams();
    let [joke, setJoke] = useState([]);
    useEffect(() => {
        getJokeById(id.id)
            .then(res => res.json())
            .then(data => {

                setJoke(data);
            })
            .catch(err => err);
    }, [id.id]);

    return (
        <>
            <h1>Single Joke</h1>
            <div className={'wrap'}>
                <label>Title</label>
                <input className={'edit-input title-edit'} name='title' defaultValue={joke.title}/>
                <label>Keyword</label>
                <input className={'edit-input keyword'} name='keyword' defaultValue={joke.keyword}/>
                <label>Content</label>
                <textarea className={'edit-input content-edit'} name='content' defaultValue={joke.content}/>
            </div>
            <div className={'add-comment-wrap'}>
                <label>Add comment</label>
                <textarea name={'comment'}/>
                <button type='submit'>Add</button>
            </div>
        </>


    );

}
export default Joke;