import './adminPanelStyle.css'
import {useState} from "react";
import {
    deleteUser,
    getAllUsersBySimilarUsername,
    removeRoleOnUser,
    upgradeRoleOnUser
} from "../../api/service/UserService";
import {isAdmin} from "../../api/hoc/isAdmin";

const AdminPanel = () => {

    let [users, setUsers] = useState([]);
    let [error, setError] = useState([]);

    function fetchUsers(e) {
        let username = e.currentTarget.value;
        if (username.length > 2 || username) {
            getAllUsersBySimilarUsername(username)
                .then(res => res.json())
                .then(data => {
                    setUsers(data)
                });
        } else {
            setUsers([])
        }
    }


    function onDeleteUser(e) {
        e.preventDefault();
        let formData = new FormData(e.target);
        let username = formData.get('username');

        deleteUser(username)
            .then(res => res.json())
            .then(data => {
                e.target.username.value = '';
                setError(data.message);
                setUsers([])
            })
            .catch(err => err);
    }


    function onRemoveRole(e) {
        e.preventDefault();
        let formData = new FormData(e.target);
        let username = formData.get('username');

        removeRoleOnUser(username)
            .then(res => res.json())
            .then(data => {
                setError(data.message);
            })
            .catch(err => err);
    }

    function onUpgradeRole(e) {
        e.preventDefault();
        let formData = new FormData(e.target);
        let username = formData.get('username');

        upgradeRoleOnUser(username)
            .then(res => res.json())
            .then(data => {
                // e.target.username.value = '';
                setError(data.message);
            })
            .catch(err => err);
    }


    return (
        <>
            <h1>Manage very important things</h1>
            {error ? <p className={'error-admin'}>{error}</p> : ''}
            <div className={'wrap-role-manage'}>
                <form onSubmit={onUpgradeRole} className={'role-manage'}>
                    <p>Add Admin role to a user</p>
                    <label>Enter username (min 1 symbol)</label>
                    <input name='username' onKeyUp={fetchUsers}/> <i className="fas fa-search"></i>
                    <button type="submit"><i className="fas fa-plus"></i> Add</button>
                </form>
                <form onSubmit={onDeleteUser} className={'role-manage'}>
                    <p>Delete user immediately</p>
                    <label>Enter username (min 1 symbol)</label>
                    <input name='username' onKeyUp={fetchUsers}/> <i className="fas fa-search"></i>
                    <button type="submit"><i className="fas fa-eraser"></i> Delete</button>
                </form>
                <form onSubmit={onRemoveRole} className={'role-manage'}>

                    <p>Remove Admin role of a user</p>
                    <label>Enter username (min 1 symbol)</label>
                    <input name='username' onKeyUp={fetchUsers}/> <i className="fas fa-search"></i>
                    <button type="submit"><i className="fas fa-minus"></i> Remove</button>
                </form>
            </div>
            {
                users.length >= 1 ?
                    <div>
                        <p className={'p-fetch-usernames'}>Usernames from database</p>
                        <ul className={'ul-usernames'}>{users.map(x =>
                            < li key={x} className={'li-usernames'}>{x}</li>)}</ul>
                    </div>
                    : ''
            }
        </>
    );
}

export default isAdmin(AdminPanel);