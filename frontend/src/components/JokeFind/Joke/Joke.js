import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getJokeById, onAddComment} from "../../../api/JokeService";
import './jokeStyle.css'

const Joke = () => {
    const id = useParams();
    let [joke, setJoke] = useState([]);
    let [dbError, setDbError] = useState([]);
    let [fieldsCheck, seTFieldsCheck] = useState({allFields: false});
    let [comments, setComments] = useState([]);
    useEffect(() => {
        getJokeById(id.id)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                setJoke(data);
                setComments(data.comments);
            })
            .catch(err => err);
    }, [id.id]);


    function addCommentSubmit(e) {
        e.preventDefault();

        let form = new FormData(e.target);

        let content = form.get('content').trim();

        if (!content || content.length < 2) {
            seTFieldsCheck({allFields: true});
            return;
        }
        seTFieldsCheck({allFields: false});

        onAddComment({
            content, jokeId: joke.id,
            ownerOfComment: sessionStorage.getItem('authenticatedUser')
        }).then(res => res.json())
            .then(data => {
                console.log(data)

                if (data.cause) {
                    let errors = data.cause.split(', ');
                    setDbError(errors);
                    return;
                }

                setComments(oldState => [
                    ...oldState, data
                ]);

            }).catch(err => err);

        e.target.content.value = '';
        setDbError([]);
    }


    return (
        <>
            <h1>Single Joke</h1>
            <div className={'wrap'}>
                <p className={'creator'}>Creator: {joke.creator}</p>
                <div>
                    <div className={'read-head heading'}><i className="fas fa-text-height"></i> Title</div>
                    <p className={'read-title'}>{joke.title}</p>
                </div>
                <div>
                    <div className={'read-head heading'}><i className="fas fa-key"></i> Keyword</div>
                    <p className={'read-keyword'}>{joke.keyword}</p>
                </div>

                <div className={'read-head heading'}><i className="fas fa-file-alt"></i> Content</div>
                <p className={'read-content'}>{joke.content}</p>
            </div>
            {fieldsCheck.allFields ? <div className={"warnings-edit"}>Add at least 2 symbols to comment!!!</div> : ''}
            {dbError ? dbError
                .map(x => <div key={x} className={"warnings-edit"}>{x.replaceAll(/[\\[\]]/g, '')}</div>) : ''}
            <form onSubmit={addCommentSubmit}>
                <div className={'add-comment-wrap'}>
                    <label>Add comment</label>
                    <textarea name={'content'}/>
                    <button type='submit'>Add</button>
                </div>
            </form>
            <div className={'comments'}>
                {comments.length > 0 ?
                    comments.map((x, id) =>
                        <div key={x.id} className={'read-content'}>
                            <p>{x.ownerOfComment} wrote: </p>
                            <p>{++id}. {x.content}</p>
                        </div>)
                    : ''}
            </div>
        </>
    );

}
export default Joke;