import './jokeBaseCardStyle.css'
import {Link} from "react-router-dom";
import {useState} from "react";
import Modal from "react-modal";

const JokeBaseCard = ({joke, id}) => {
    let [comments, setComments] = useState({
        commentsData: joke.comments,
        display: false
    });

    const changeR = (e) => e.target.style.color = 'red';
    const changeL = (e) => e.target.style.color = 'lightblue';

    const hide = () => setComments({display: false});
    const show = () => setComments({display: true});

    const [isOpen, setIsOpen] = useState(false);

    function toggleModal(e) {
        e.preventDefault();
        setIsOpen(!isOpen);
    }

    return (
        <>
            <Modal className={'modal-style-likes'}
                   isOpen={isOpen}
                   onRequestClose={toggleModal}
                   contentLabel="Confirm deleting user"
                   ariaHideApp={false}>
                <p className={'title-modal-likes'}>Users who liked this joke:</p>

                {joke.likes.length > 0 ? <p>{joke?.likes.map(x => x.ownerOfComment).join(', ')}</p> :
                    <p>No likes</p>}
                <button onClick={toggleModal} className={'button-joke-edit'}>Back</button>
            </Modal>
            <section className={'joke-card'}>
                <div onMouseOver={changeR} onMouseOut={changeL}>
                    <p className={'joke-card-data'}>No: {id}</p>
                    <p className={'joke-card-data'}>Title: {joke.title}</p>
                    <p className={'joke-card-data'}>Keyword: {joke.keyword}</p>
                    <p className={'joke-card-data hover-data'}
                       onClick={toggleModal}
                    >Total likes: {joke.likes ? joke.likes.length : 0} (show)</p>
                    <p onMouseEnter={show}
                       onMouseLeave={hide}
                       className={'joke-card-data show-comments'}
                    >Total comments: {joke.comments ? joke.comments.length : 0} </p>
                </div>
                {comments.display ?
                    joke.comments.map((x, id) =>
                        <p key={id} className={'comment'}>{++id}. {x.content}</p>) : ''}

                <Link className={'joke-card-link'} to={`/joke-read/${joke.id}`}>View joke</Link>
            </section>
        </>
    );
}

export default JokeBaseCard;