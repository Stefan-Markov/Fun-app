import './jokeAddStyle.css';
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {onCreate} from "../../../api/service/JokeService";

const JokeAdd = () => {
    let navigate = useNavigate();
    let [fieldsCheck, seTFieldsCheck] = useState({allFields: false});
    let [dbError, setDbError] = useState([]);

    const onSubmitCreate = (e) => {
        e.preventDefault();
        let form = new FormData(e.target);

        let title = form.get('title').trim();
        let content = form.get('content').trim();
        let keyword = form.get('keyword').trim();


        if (!title || !content || !keyword) {
            seTFieldsCheck({allFields: true});
            return;
        }

        seTFieldsCheck({allFields: false});

        onCreate({
                title, keyword, content,
                username: sessionStorage.getItem('authenticatedUser')
            }
        ).then(res => res.json())
            .then(data => {
                if (data.cause) {
                    let errors = data.cause.split(', ');
                    setDbError(errors);
                    return;
                }
                navigate('/joke-manage');
            }).catch(err => err);
    }
    return (
        <>
            <h1 className={'add'}>Joke Add</h1>

            <div>
                <p className={'info'}>To create joke think about cool title. After that think about keyword, that
                    associate with the joke.
                    Finally fill the content, click create and let's the fun begin!</p>
                <div>
                    {fieldsCheck.allFields ? <div className={'warnings'}>Fill all fields!</div> : ''}
                    {dbError ? dbError
                        .map(x => <div key={x} className="warnings">{x.replaceAll(/[\\[\]]/g, '')}</div>) : ''}
                </div>
                <form onSubmit={onSubmitCreate}>
                    <div className={'inputs'}>

                        <label>Title</label>
                        <textarea className={'joke-add-name'} name='title'/>

                        <label>Keyword</label>
                        <textarea className={'joke-add-name'} name='keyword'/>

                        <label>Content </label>
                        <textarea className={'joke-add-intro'} name='content'/>

                        <button className={'create-joke-button'} type='submit'>Create</button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default JokeAdd;