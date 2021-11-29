import {useNavigate, useParams} from "react-router-dom";
import {deleteJokeById, editJokeById, getJokeById} from "../../../api/JokeService";
import {useEffect, useState} from "react";
import './jokeByIdStyle.css';

const JokeById = () => {
    const id = useParams();
    let [joke, setJoke] = useState([]);
    let [fieldsCheck, seTFieldsCheck] = useState({allFields: false});
    let [dbError, setDbError] = useState([]);
    let navigate = useNavigate();
    useEffect(() => {
        getJokeById(id.id)
            .then(res => res.json())
            .then(data => {

                setJoke(data);
            })
            .catch(err => err);
    }, [id.id]);

    function onEdit(e) {
        e.preventDefault();
        let form = new FormData(e.target);
        let title = form.get('title');
        let content = form.get('content');
        let keyword = form.get('keyword');

        if (!title || !content || !keyword) {
            seTFieldsCheck({allFields: true});
            return;
        }
        seTFieldsCheck({allFields: false});


        editJokeById({title, keyword, content, id: id.id})
            .then(res => res.json())
            .then(data => {
                if (data.cause) {
                    let errors = data.cause.split(', ');
                    setDbError(errors);
                    return;
                }
                navigate('/joke-manage')
            }).catch(err => err);

    }

    function onDelete(e) {
        e.preventDefault();

        deleteJokeById({id: id.id})
            .then(res => res.json())
            .catch(err => err);
        navigate('/');
    }

    return (
        <>
            <h1>Edit joke</h1>
            <form onSubmit={onEdit}>
                {fieldsCheck.allFields ? <div className="warning-edit">Fill all fields!</div> : ''}
                {dbError ? dbError
                    .map(x => <div key={x} className="warnings-edit">{x.replaceAll(/[\\[\]]/g, '')}</div>) : ''}

                <div className={'wrap'}>
                    <label>Title</label>
                    <input className={'edit-input title-edit'} name='title' defaultValue={joke.title}/>
                    <label>Keyword</label>
                    <input className={'edit-input keyword'} name='keyword' defaultValue={joke.keyword}/>
                    <label>Content</label>
                    <textarea className={'edit-input content-edit'} name='content' defaultValue={joke.content}/>
                    <button className={'button-joke-edit'}>Save changes</button>
                    <button onClick={onDelete} className={'button-joke-edit'}>Delete joke immediately</button>
                </div>
            </form>
        </>
    );
}

export default JokeById;