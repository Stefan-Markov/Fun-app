import {useNavigate, useParams} from "react-router-dom";
import {editJokeById, getJokeById} from "../../../api/service/JokeService";
import {useEffect, useState} from "react";
import './jokeByIdStyle.css';
import ResetScroll from "../../../api/ResetScroll/ResetScroll";
import JokeDeleteModal from "./JokeDeleteModal/JokeDeleteModal";

const JokeById = () => {
    ResetScroll();
    const id = useParams();
    let [joke, setJoke] = useState([]);
    let [fieldsCheck, seTFieldsCheck] = useState({allFields: false});
    let [dbError, setDbError] = useState([]);
    let navigate = useNavigate();
    let [names, setNames] = useState({username: '', user: ''})
    useEffect(() => {
        getJokeById(id.id)
            .then(res => res.json())
            .then(data => {
                setNames({username: sessionStorage.getItem('authenticatedUser'), user: data.creator})
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
            })
            .catch(err => err);

    }

    useEffect(() => {
        let owner = names.user === names.username;
        if (!owner) {
            navigate('/');
        }
    }, [names, navigate])


    return (
        <>
            <h1>Edit joke</h1>
            <form onSubmit={onEdit}>
                {fieldsCheck.allFields ? <div className={"warnings-edit"}>Fill all fields!</div> : ''}
                {dbError ? dbError
                    .map(x => <div key={x} className={"warnings-edit"}>{x.replaceAll(/[\\[\]]/g, '')}</div>) : ''}

                <div className={'wrap'}>
                    <label className={'read-head'}>Title</label>
                    <input className={'edit-input title-edit'} name='title' defaultValue={joke.title}/>
                    <label className={'read-head'}>Keyword</label>
                    <input className={'edit-input keyword'} name='keyword' defaultValue={joke.keyword}/>
                    <label className={'read-head'}>Content</label>
                    <textarea className={'edit-input content-edit'} name='content' defaultValue={joke.content}/>
                    <button className={'button-joke-edit'}>Save changes</button>
                    <JokeDeleteModal id={joke.id}/>
                </div>
            </form>
        </>
    );
}

export default JokeById;