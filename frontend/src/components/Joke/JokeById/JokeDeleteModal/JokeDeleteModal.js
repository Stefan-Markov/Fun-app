import Modal from "react-modal";
import {useState} from "react";
import {deleteJokeById} from "../../../../api/service/JokeService";
import {useNavigate} from "react-router-dom";

const JokeDeleteModal = (id) => {

    const [isOpen, setIsOpen] = useState(false);
    let navigate = useNavigate();

    function toggleModal(e) {
        e.preventDefault();
        setIsOpen(!isOpen);
    }


    function onDelete() {

        deleteJokeById(id)
            .then(res => res.json())
            .catch(err => err);
        navigate('/');
    }

    return (
        <>
            <Modal className={'modal-style-likes'}
                   isOpen={isOpen}
                   onRequestClose={toggleModal}
                   contentLabel="Update role"
                   ariaHideApp={false}>
                <p className={'title-modal-likes'}>Delete joke</p>
                <button onClick={onDelete} className={'button-joke-edit'}>Delete</button>
                <button onClick={toggleModal} className={'button-joke-edit'}>Back</button>
            </Modal>
            <button onClick={toggleModal} className={'button-joke-edit'}>Delete joke immediately</button>
        </>
    )
}

export default JokeDeleteModal;